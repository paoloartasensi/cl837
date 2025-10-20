# Analisi WearManager - Gestione HR History e Sincronizzazione UTC

## 📋 Panoramica

Questo documento analizza il funzionamento del sistema di gestione della cronologia della frequenza cardiaca (HR) e la sincronizzazione UTC nel WearManager per dispositivi fitness Bluetooth.

## 🫀 Metodi per il recupero della cronologia HR

### 1. Metodi per aggiungere callback HR

#### `addHistoryOfHRDataCallback`
```java
public void addHistoryOfHRDataCallback(HistoryOfHRDataCallback callback) {
    this.mHistoryOfHRDataCallback = callback;
}
```

#### `addHistoryOfHRRecordCallback`
```java
public void addHistoryOfHRRecordCallback(HistoryOfHRRecordCallback callback) {
    this.mHistoryOfHRRecordCallback = callback;
}
```

### 2. Metodi che ricevono i dati HR

#### Callback per dati HR nel costruttore di `WearManager`
```java
public void onHistoryOfHRDataReceived(@NonNull BluetoothDevice device, List<HistoryOfHeartRate> heartRates) {
    if (WearManager.this.mHistoryOfHRDataCallback != null) {
        WearManager.this.mHistoryOfHRDataCallback.onHistoryOfHRDataReceived(device, heartRates);
    }
}
```

#### Callback per record HR
```java
public void onHistoryOfHRRecordReceived(@NonNull BluetoothDevice device, List<HistoryOfRecord> records) {
    if (WearManager.this.mHistoryOfHRRecordCallback != null) {
        WearManager.this.mHistoryOfHRRecordCallback.onHistoryOfHRRecordReceived(device, records);
    }
}
```

### 3. Elaborazione dei dati nel `WearReceivedDataCallback`

Il metodo che elabora i dati grezzi della frequenza cardiaca:
```java
// Elaborazione dei dati HR grezzi
for (int index = 0; index < this.mPackages.size(); index++) {
    byte[] slice = subSlice(3, ((Data)this.mPackages.get(index)).getValue());
    for (int i = 4; i < slice.length; i++) {
        int heart = getIntParse(slice, i, 1);
        long stamp = DateUtil.restoreZoneUTC(this.mStamp);
        HistoryOfHeartRate heartRate = new HistoryOfHeartRate(stamp, heart);
        this.mHistoryOfHeartRates.add(heartRate);
        this.mStamp++;
    }
}
onHistoryOfHRDataReceived(device, this.mHistoryOfHeartRates);
```

### 4. Modello dati

Il modello `HistoryOfHeartRate` contiene:
- `long stamp` - timestamp
- `int heartRate` - valore della frequenza cardiaca

## 📡 Comandi inviati al dispositivo per ottenere HR History

### 1. **`getHistoryOfHRRecord()`** - Comando `(byte)33`
```java
public void getHistoryOfHRRecord() {
    this.mReceivedDataCallback.clearType(4);
    sendCommand((byte)33, new int[] { 0 });
}
```
- **Comando inviato**: `0x21` (33 in decimale)
- **Parametri**: `{ 0 }` 
- **Scopo**: Ottiene i record della frequenza cardiaca (timestamp + valori aggregati)

#### **Formato dettagliato dei record HR (modalità 0x21)**
```java
// Struttura del record HR
public class HistoryOfRecord {
    /**
     * Device original query time (UTC timestamp dal dispositivo)
     */
    public long stamp;
    /**
     * Display time in local zone (timestamp convertito per visualizzazione)
     */
    public long record;
}
```

#### **Elaborazione pacchetti modalità 0x21**
```java
if (mode == 0x21) {// HR history record 0x21
    final long utcTag = getLongParse(value, 3, 4);
    
    if (utcTag != END_TAG) {
        // Accumula pacchetti fino al tag di fine (0xFFFFFFFF)
        mPackages.add(data);
    } else {
        // Elabora tutti i pacchetti ricevuti
        for (int index = 0; index < mPackages.size(); index++) {
            slice = subSlice(3, mPackages.get(index).getValue()); // Skip 3 bytes header
            
            // Ogni record HR è esattamente 4 bytes (timestamp UTC)
            for (int i = 0; i < slice.length / 4; i++) {
                offset = i * 4;
                long stamp = getLongParse(slice, offset, 4);    // Big-endian parsing
                long record = restoreZoneUTC(stamp);            // UTC → Local time
                mHistoryOfRecords.add(new HistoryOfRecord(stamp, record));
            }
        }
        
        onHistoryOfHRRecordReceived(device, mHistoryOfRecords);
        mHistoryOfRecords.clear();
        mPackages.clear();
    }
}
```

#### **Dettagli tecnici parsing**
```java
// Parsing big-endian di long da bytes
private long getLongParse(byte[] bytes, int pos, int len) {
    long val = 0;
    len += pos;
    for (int i = pos; i < len; i++) {
        val <<= 8;                        // Shift a sinistra di 8 bit
        val |= (long) bytes[i] & 0xFF;    // OR con maschera per byte unsigned
    }
    return val;
}

// Estrazione payload saltando header
private byte[] subSlice(final int start, final byte[] value) {
    return subByte(value, start, value.length - 1); // Skip primi 3 bytes
}
```

#### **Specifiche formato dati 0x21**
- **📦 Struttura pacchetto**: `[3 bytes header] + [payload records]`
- **📊 Formato record**: `4 bytes per timestamp UTC` (big-endian)
- **🔚 Tag di fine**: `0xFFFFFFFF` indica fine trasmissione
- **🔄 Multi-pacchetto**: Dati possono arrivare in più pacchetti
- **⏰ Timestamp**: UTC seconds dal dispositivo

### 2. **`getHistoryOfHRData(long stamp)`** - Comando `(byte)34`
```java
public void getHistoryOfHRData(long stamp) {
    this.mReceivedDataCallback.clearType(6);
    sendCommand((byte)34, HexUtil.append(1, utc2Bytes(stamp)));
}
```
- **Comando inviato**: `0x22` (34 in decimale)
- **Parametri**: `1` + timestamp convertito in bytes UTC
- **Scopo**: Ottiene dati dettagliati della frequenza cardiaca da un timestamp specifico

#### **Elaborazione pacchetti modalità 0x22**
```java
if (mode == 0x22) {// HR history data 0x22
    final long utcTag = getLongParse(value, 3, 4);
    
    if (utcTag != END_TAG) {
        mPackages.add(data);
    } else {
        // Elabora tutti i pacchetti per dati HR dettagliati
        for (int index = 0; index < mPackages.size(); index++) {
            slice = subSlice(3, mPackages.get(index).getValue());
            
            // Parsing specifico per dati HR (formato diverso da 0x21)
            // Skip 4 bytes iniziali, poi 1 byte per valore HR
            for (int i = 4; i < slice.length; i++) {
                int heart = getIntParse(slice, i, 1);     // 1 byte per valore HR
                long stamp = DateUtil.restoreZoneUTC(mStamp);  // Timestamp incrementale
                HistoryOfHeartRate heartRate = new HistoryOfHeartRate(stamp, heart);
                mHistoryOfHeartRates.add(heartRate);
                mStamp++;  // Incrementa timestamp per record successivo
            }
        }
        
        onHistoryOfHRDataReceived(device, mHistoryOfHeartRates);
        mHistoryOfHeartRates.clear();
        mPackages.clear();
    }
}
```

#### **Differenze tra modalità 0x21 e 0x22**
| Aspetto | 0x21 (Record) | 0x22 (Data) |
|---------|---------------|-------------|
| **Formato** | 4 bytes timestamp UTC | 1 byte valore HR + timestamp incrementale |
| **Header skip** | 3 bytes | 3 bytes + 4 bytes aggiuntivi |
| **Contenuto** | Solo timestamp registrazione | Valori frequenza cardiaca misurati |
| **Uso** | Timeline eventi HR | Dati dettagliati per analisi |
| **Parsing** | Big-endian 4-byte records | Sequential 1-byte HR values |
| **Timestamp** | UTC assoluto dal dispositivo | Incrementale con `mStamp++` |
| **Callback** | `onHistoryOfHRRecordReceived` | `onHistoryOfHRDataReceived` |

### 3. **`getHistoryOfHRData()`** - Comando `(byte)34` (senza parametri)  
```java
public void getHistoryOfHRData() {
    this.mReceivedDataCallback.clearType(2);
    sendCommand((byte)34, new int[] { 0 });
}
```
- **Comando inviato**: `0x22` (34 in decimale)
- **Parametri**: `{ 0 }` (tutti i dati disponibili)
- **Scopo**: Ottiene tutti i dati dettagliati della frequenza cardiaca

#### **Parsing implementazione semplificata (Test_FROM_ESTHER.java)**
```java
// Esempio di parsing diretto mode 0x21 dal file di test
if (mode == 0x21) {
    for (int i = 3; i < value.length; i += 4) {  // Skip 3-byte header, 4-byte records
        if (i + 4 <= value.length) {
            // Parse big-endian 4-byte timestamp
            long stamp = ((long)(value[i] & 0xFF) << 24) |
                        ((long)(value[i+1] & 0xFF) << 16) |
                        ((long)(value[i+2] & 0xFF) << 8) |
                        ((long)(value[i+3] & 0xFF));
            
            // Converti UTC → Local time per display
            long record = DateUtil.restoreZoneUTC(stamp);
            
            System.out.println("HR Record - Original: " + stamp + ", Display: " + record);
        }
    }
}
```

#### **Implementazione completa vs Test**
| Componente | Implementazione completa | Test semplificato |
|------------|-------------------------|-------------------|
| **Multi-packet** | Gestione con `mPackages` | Singolo pacchetto |
| **END_TAG check** | Verifica `0xFFFFFFFF` | Skip verifica |
| **Data structures** | `HistoryOfRecord` objects | Direct parsing |
| **Callbacks** | `onHistoryOfHRRecordReceived` | Console output |
| **Error handling** | Completa gestione errori | Parsing diretto |

### 4. **Formato del comando**

Il metodo `sendCommand` costruisce il comando nel formato:
```java
private void sendCommand(byte cmd, int... values) {
    // Formato: [0xFF, length, cmd, ...values, checksum]
    byte[] header = HexUtil.compose(new int[] { 255, len, cmd });
    byte[] bytes = HexUtil.compose(values);
    result = HexUtil.append(header, bytes);
    byte check = checkSum(result);
    byte[] command = HexUtil.append(result, check);
    writeTxCharacteristic(command);
}
```

#### **Struttura dettagliata del protocollo di comando**
```
📦 Pacchetto comando completo:
┌─────────┬────────┬─────────┬──────────┬──────────┐
│  0xFF   │ Length │   CMD   │  Values  │ Checksum │
│(1 byte) │(1 byte)│(1 byte) │(n bytes) │ (1 byte) │
└─────────┴────────┴─────────┴──────────┴──────────┘

🔍 Esempi specifici:
• HR Record (0x21): [0xFF, 0x03, 0x21, 0x00, checksum]
• HR Data (0x22):   [0xFF, 0x03, 0x22, 0x00, checksum]
• UTC Sync:         [0xFF, 0x09, 0x01, utc_bytes(8), checksum]
```

### 5. **Protocollo completo**

1. **HR Record**: `[0xFF, 0x04, 0x21, 0x00, checksum]`
2. **HR Data**: `[0xFF, length, 0x22, 0x01, timestamp_bytes..., checksum]`

I dati vengono poi ricevuti attraverso le modalità `mode == 33` (per i record) e `mode == 34/35` (per i dati dettagliati) nel callback `WearReceivedDataCallback`.

## 🕒 Gestione UTC e Sincronizzazione

### 1. Classe `DateUtil` - Gestione principale UTC

```java
public class DateUtil {
    // Ottiene il timestamp UTC corrente
    public static long getZoneUTC() {
        Calendar calendar = Calendar.getInstance();
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);      // 15
        int dstOffset = calendar.get(Calendar.DST_OFFSET);        // 16
        calendar.add(Calendar.MILLISECOND, zoneOffset + dstOffset);
        return calendar.getTimeInMillis() / 1000L;  // Ritorna in secondi
    }
    
    // Converte timestamp UTC in timestamp locale
    public static long restoreZoneUTC(long stamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(stamp * 1000L);  // Da secondi a millisecondi
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return calendar.getTimeInMillis();  // Ritorna in millisecondi
    }
    
    // Versione per millisecondi
    public static long restoreZoneUTCTimeInMillis(long stamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(stamp);
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return calendar.getTimeInMillis();
    }
}
```

### 2. Conversione per invio al dispositivo - `utc2Bytes()`

```java
protected int[] utc2Bytes(long stamp) {
    int[] utcArray = new int[4];
    utcArray[0] = (int)(stamp >> 24L);  // Byte più significativo
    utcArray[1] = (int)(stamp >> 16L);
    utcArray[2] = (int)(stamp >> 8L);
    utcArray[3] = (int)stamp;           // Byte meno significativo
    return utcArray;
}
```

### 3. Impostazione UTC sul dispositivo

```java
public void setUTCTime() {
    setUTCTime(DateUtil.getZoneUTC());
}

public void setUTCTime(long stamp) {
    sendCommand((byte)8, utc2Bytes(stamp));
}
```

### 4. Utilizzo nei dati ricevuti

Nei callback di ricezione dati:

```java
// Per i dati HR
long stamp = DateUtil.restoreZoneUTC(this.mStamp);
HistoryOfHeartRate heartRate = new HistoryOfHeartRate(stamp, heart);

// Per i dati respiratori
long stamp = DateUtil.restoreZoneUTC(this.mStamp);
HistoryOfRespiratoryRate respiratoryRate = new HistoryOfRespiratoryRate(stamp, respiratory);

// Per dati sport
stamp = DateUtil.restoreZoneUTC(stamp);
this.mHistoryOfSports.add(new HistoryOfSport(stamp, step, calorie));
```

## 🔄 Sincronizzazione UTC Automatica

### 1. Opzioni di sincronizzazione UTC

#### **Opzione A: Sincronizzazione automatica alla connessione (ATTUALE)**
```java
private final class WearManagerGattCallback extends FitnessManager<WearManagerCallbacks>.FitnessManagerGattCallback {
    
    protected void onDeviceReady() {
        super.onDeviceReady();
        WearManager.this.setUTCTime();  // 👈 SINCRONIZZAZIONE AUTOMATICA!
    }
}
```

**Flusso:**
1. Il Bluetooth si connette
2. I servizi vengono scoperti
3. Il dispositivo diventa "pronto" (`onDeviceReady()`)
4. **AUTOMATICAMENTE** viene chiamato `setUTCTime()`

#### **Opzione B: Sincronizzazione prima della richiesta HR History**
```java
public void getHistoryOfHRRecord() {
    // Sincronizza UTC prima della richiesta
    setUTCTime();
    
    // Breve pausa per assicurare che la sincronizzazione sia completata
    // (opzionale, dipende dalla latenza del dispositivo)
    
    this.mReceivedDataCallback.clearType(4);
    sendCommand((byte)33, new int[] { 0 });
}

public void getHistoryOfHRData(long stamp) {
    // Sincronizza UTC prima della richiesta
    setUTCTime();
    
    this.mReceivedDataCallback.clearType(6);
    sendCommand((byte)34, HexUtil.append(1, utc2Bytes(stamp)));
}
```

#### **Opzione C: Sincronizzazione subito dopo connessione GATT**
```java
private final class WearManagerGattCallback extends FitnessManager<WearManagerCallbacks>.FitnessManagerGattCallback {
    
    protected void initialize() {
        // Sincronizza UTC come prima operazione
        WearManager.this.setUTCTime();
        
        // Poi continua con l'inizializzazione normale
        super.initialize();
        
        // Setup notifiche HR, RX, ecc.
        WearManager.this.setNotificationCallback(WearManager.this.mHeartRateCharacteristic)
            .with((DataReceivedCallback)WearManager.this.mHeartRateMeasureDataCallback);
        // ...
    }
}
```

#### **Opzione C: Sincronizzazione subito dopo connessione GATT**
```java
private final class WearManagerGattCallback extends FitnessManager<WearManagerCallbacks>.FitnessManagerGattCallback {
    
    protected void initialize() {
        // Sincronizza UTC come prima operazione
        WearManager.this.setUTCTime();
        
        // Poi continua con l'inizializzazione normale
        super.initialize();
        
        // Setup notifiche HR, RX, ecc.
        WearManager.this.setNotificationCallback(WearManager.this.mHeartRateCharacteristic)
            .with((DataReceivedCallback)WearManager.this.mHeartRateMeasureDataCallback);
        // ...
    }
}
```

### 2. **Vantaggi e svantaggi delle opzioni**

| Opzione | Vantaggi ✅ | Svantaggi ❌ | Quando usare |
|---------|-------------|---------------|---------------|
| **A - onDeviceReady()** (Attuale) | • Automatica<br>• Una sola volta per sessione<br>• Garantisce servizi pronti | • Potrebbe essere "troppo tardi"<br>• Dipende dal timing interno | Connessioni lunghe con più richieste |
| **B - Prima di ogni richiesta** | • Timestamp sempre aggiornato<br>• Massima precisione<br>• Controllo completo | • Overhead su ogni chiamata<br>• Latenza aggiuntiva<br>• Comando extra | Richieste sporadiche o alta precisione |
| **C - initialize()** | • Molto precoce<br>• Prima delle notifiche<br>• Setup più logico | • Servizi potrebbero non essere pronti<br>• Potrebbe fallire | Quando serve subito la sincronizzazione |

### 3. **Raccomandazioni d'uso**

#### **🎯 Per la massima precisione temporale:**
```java
// Opzione B modificata - con verifica se necessario
public void getHistoryOfHRData(long stamp) {
    // Sincronizza solo se è passato troppo tempo dall'ultima sync
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastSyncTime > SYNC_INTERVAL_MS) {
        setUTCTime();
        lastSyncTime = currentTime;
    }
    
    this.mReceivedDataCallback.clearType(6);
    sendCommand((byte)34, HexUtil.append(1, utc2Bytes(stamp)));
}
```

#### **🔄 Per connessioni multiple veloci:**
```java
// Opzione A + B combinata
protected void onDeviceReady() {
    super.onDeviceReady();
    WearManager.this.setUTCTime();  // Sync iniziale
    this.isInitialSyncDone = true;
}

public void getHistoryOfHRData(long stamp) {
    // Re-sync solo se necessario
    if (!isInitialSyncDone || isSignificantTimeDrift()) {
        setUTCTime();
    }
    
    this.mReceivedDataCallback.clearType(6);
    sendCommand((byte)34, HexUtil.append(1, utc2Bytes(stamp)));
}
```

### 4. **Considerazioni tecniche**

#### **⏱️ Timing di sincronizzazione:**
- **initialize()**: ~100-200ms dopo connessione GATT
- **onDeviceReady()**: ~500-1000ms dopo connessione GATT (più sicuro)
- **Prima richiesta**: Momento esatto prima del comando

#### **🔄 Frequenza di ri-sincronizzazione:**
```java
// Esempio: ri-sincronizza ogni 30 minuti o ad ogni connessione
private static final long SYNC_INTERVAL_MS = 30 * 60 * 1000; // 30 minuti

private boolean needsResync() {
    return (System.currentTimeMillis() - lastSyncTime) > SYNC_INTERVAL_MS;
}
```

#### **🛡️ Gestione errori di sincronizzazione:**
```java
public void setUTCTimeWithRetry() {
    setUTCTime(DateUtil.getZoneUTC());
    
    // Verifica che la sincronizzazione sia andata a buon fine
    // (implementazione dipende dalle specifiche del dispositivo)
}
```

### 5. **Implementazione raccomandata**

Per il tuo caso d'uso, suggerirei una **combinazione di A + B condizionale**:

```java
// Sincronizzazione sicura al primo collegamento
protected void onDeviceReady() {
    super.onDeviceReady();
    WearManager.this.setUTCTime();
    this.lastSyncTime = System.currentTimeMillis();
}

// Re-sincronizzazione intelligente prima delle richieste HR
public void getHistoryOfHRData(long stamp) {
    // Re-sync se è passato troppo tempo O se richiesto esplicitamente
    if (needsResync() || forceResync) {
        setUTCTime();
        this.lastSyncTime = System.currentTimeMillis();
        this.forceResync = false;
    }
    
    this.mReceivedDataCallback.clearType(6);
    sendCommand((byte)34, HexUtil.append(1, utc2Bytes(stamp)));
}
```

Questo approccio ti dà il **meglio di entrambi i mondi**: sincronizzazione automatica sicura + controllo manuale quando necessario! 🎯

```java
// Sincronizza con l'ora attuale del telefono
public void setUTCTime() {
    setUTCTime(DateUtil.getZoneUTC());
}

// Sincronizza con un timestamp specifico
public void setUTCTime(long stamp) {
    sendCommand((byte)8, utc2Bytes(stamp));  // Comando 0x08
}
```

### 3. Come funziona la sincronizzazione

**Processo automatico:**
1. **App si connette** → `onDeviceReady()` viene chiamato
2. **`DateUtil.getZoneUTC()`** → calcola il timestamp UTC attuale dal telefono
3. **`utc2Bytes(stamp)`** → converte il timestamp in 4 bytes
4. **`sendCommand((byte)8, ...)`** → invia comando 0x08 al dispositivo
5. **Il dispositivo** → aggiorna il suo RTC (Real Time Clock) interno

**Calcolo UTC:**
```java
public static long getZoneUTC() {
    Calendar calendar = Calendar.getInstance();
    int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);    // Fuso orario
    int dstOffset = calendar.get(Calendar.DST_OFFSET);      // Ora legale
    calendar.add(Calendar.MILLISECOND, zoneOffset + dstOffset);
    return calendar.getTimeInMillis() / 1000L;  // UTC in secondi
}
```

### 4. Come il dispositivo mantiene l'ora reale

Il dispositivo ha un **RTC interno** che:
- Viene sincronizzato automaticamente alla prima connessione
- Mantiene l'ora anche quando disconnesso
- Usa questo timestamp per marcare tutti i dati HR, sport, sonno, ecc.

### 5. Recupero dati storici accurati

Quando recuperi i dati HR con `getHistoryOfHRData(stamp)`:
- Il parametro `stamp` è il punto di partenza desiderato
- Il dispositivo usa il suo RTC sincronizzato per restituire dati dal timestamp corretto
- I dati hanno timestamp UTC precisi grazie alla sincronizzazione iniziale

## 📊 Flusso di gestione UTC

1. **Invio al dispositivo**: 
   - `getZoneUTC()` → converte tempo locale in UTC (secondi)
   - `utc2Bytes()` → converte in array di 4 byte
   - Inviato tramite comando 8

2. **Ricezione dal dispositivo**:
   - Il dispositivo invia timestamp UTC
   - `restoreZoneUTC()` → converte da UTC a tempo locale
   - Utilizzato per creare oggetti con timestamp corretto

## 🌍 Gestione Timezone

- **ZONE_OFFSET**: Offset del fuso orario (es. +1h per CET)
- **DST_OFFSET**: Offset per ora legale (es. +1h in estate)
- La somma dei due offset viene usata per le conversioni

## 🔄 Flusso completo del sistema

1. **Connessione** → Sincronizzazione UTC automatica
2. **Dispositivo registra dati** → con timestamp UTC precisi
3. **Richiesta cronologia** → `getHistoryOfHRData(stamp)`
4. **Ricezione dati** → con timestamp corretti
5. **Conversione locale** → `DateUtil.restoreZoneUTC()` per visualizzazione

**Questo sistema garantisce che i dati storici abbiano sempre timestamp precisi e coerenti!** 📊

## � Servizi e Caratteristiche Bluetooth

### 1. **Servizi principali utilizzati**

#### Servizio Custom Fitness (`AAE28F00-71B5-42A1-8C3C-F9CF6AC969D0`)
```java
protected static final UUID SERVICE_UUID = UUID.fromString("AAE28F00-71B5-42A1-8C3C-F9CF6AC969D0");
```
- **Scopo**: Servizio principale per comunicazione custom con dispositivo fitness
- **Caratteristiche**:
  - RX Characteristic (ricezione dati)
  - TX Characteristic (invio comandi)

#### Servizio Heart Rate (`0000180D-0000-1000-8000-00805f9b34fb`)
```java
private static final UUID HR_SERVICE_UUID = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
```
- **Scopo**: Servizio standard Bluetooth per misurazioni HR in tempo reale
- **Caratteristiche**:
  - Heart Rate Measurement (notifiche HR live)
  - Body Sensor Location

#### Servizio Battery (`0000180F-0000-1000-8000-00805f9b34fb`)
```java
private static final UUID BATTERY_SERVICE_UUID = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb");
```
- **Scopo**: Monitoraggio livello batteria del dispositivo

#### Servizio Device Information (`0000180A-0000-1000-8000-00805f9b34fb`)
```java
private static final UUID PROFILE_SERVICE_UUID = UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb");
```
- **Scopo**: Informazioni del dispositivo (modello, firmware, hardware, ecc.)

### 2. **Caratteristiche principali**

#### **RX Characteristic** - Ricezione dati (`AAE28F01-71B5-42A1-8C3C-F9CF6AC969D0`)
```java
protected static final UUID RX_CHAR_UUID = UUID.fromString("AAE28F01-71B5-42A1-8C3C-F9CF6AC969D0");
```
- **Funzione**: Riceve **tutti i dati** dal dispositivo (HR history, sport, sonno, ecc.)
- **Modalità**: Notifications abilitata
- **Callback**: `WearReceivedDataCallback` elabora tutti i dati in arrivo

#### **TX Characteristic** - Invio comandi (`AAE28F02-71B5-42A1-8C3C-F9CF6AC969D0`)
```java
protected static final UUID TX_CHAR_UUID = UUID.fromString("AAE28F02-71B5-42A1-8C3C-F9CF6AC969D0");
```
- **Funzione**: Invia **tutti i comandi** al dispositivo
- **Modalità**: Write (senza response per performance)
- **Utilizzo**: `sendCommand()` per tutti i comandi (HR history, sync UTC, configurazioni)

#### **Heart Rate Measurement** - HR live (`00002A37-0000-1000-8000-00805f9b34fb`)
```java
private static final UUID HEART_RATE_MEASUREMENT_CHARACTERISTIC_UUID = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");
```
- **Funzione**: Riceve misurazioni HR **in tempo reale**
- **Modalità**: Notifications abilitata
- **Callback**: `HeartRateMeasurementDataCallback`

#### **Custom RX Characteristic** - Dati speciali (`AAE21542-71B5-42A1-8C3C-F9CF6AC969D0`)
```java
protected static final String CUSTOM_CHAR_UUID = "AAE21542-71B5-42A1-8C3C-F9CF6AC969D0";
```
- **Funzione**: Caratteristica per dispositivi CL833 (dati custom aggiuntivi)
- **Modalità**: Notifications (se disponibile)

### 3. **Setup e inizializzazione delle caratteristiche**

#### **Scoperta servizi richiesti**
```java
public boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
    // Servizio Custom Fitness (OBBLIGATORIO)
    BluetoothGattService service = gatt.getService(FitnessManager.SERVICE_UUID);
    if (service != null) {
        this.mRXCharacteristic = service.getCharacteristic(FitnessManager.RX_CHAR_UUID);
        this.mTXCharacteristic = service.getCharacteristic(FitnessManager.TX_CHAR_UUID);
    }
    
    // Servizio Heart Rate (OBBLIGATORIO)
    BluetoothGattService hrService = gatt.getService(WearManager.HR_SERVICE_UUID);
    if (hrService != null) {
        this.mHeartRateCharacteristic = hrService.getCharacteristic(WearManager.HEART_RATE_MEASUREMENT_CHARACTERISTIC_UUID);
    }
    
    return (this.mRXCharacteristic != null && this.mTXCharacteristic != null && this.mHeartRateCharacteristic != null);
}
```

#### **Inizializzazione e abilitazione notifications**
```java
protected void initialize() {
    // 1. Abilita notifiche HR in tempo reale
    setNotificationCallback(this.mHeartRateCharacteristic)
        .with((DataReceivedCallback)this.mHeartRateMeasureDataCallback);
    enableNotifications(this.mHeartRateCharacteristic).enqueue();
    
    // 2. Abilita notifiche RX per tutti i dati del dispositivo
    setNotificationCallback(this.mRXCharacteristic)
        .with((DataReceivedCallback)this.mReceivedDataCallback);
    enableNotifications(this.mRXCharacteristic).enqueue();
    
    // 3. Abilita custom RX se disponibile (dispositivi CL833)
    if (this.mCustomRxCharacteristic != null) {
        setNotificationCallback(this.mCustomRxCharacteristic)
            .with((device, data) -> {
                if (this.mCustomDataReceivedCallback != null) {
                    this.mCustomDataReceivedCallback.onDataReceived(device, data.getValue());
                }
            });
        enableNotifications(this.mCustomRxCharacteristic).enqueue();
    }
    
    // 4. Setup batteria e profilo dispositivo
    enableBatteryLevelCharacteristicNotifications();
    readProfileCharacteristic();
}
```

### 4. **Flusso di comunicazione per HR History**

#### **Invio comando**
1. **App** → `getHistoryOfHRData(stamp)` 
2. **App** → `sendCommand((byte)34, HexUtil.append(1, utc2Bytes(stamp)))`
3. **App** → Scrive su **TX Characteristic** 
4. **Dispositivo** → Riceve comando e prepara dati

#### **Ricezione dati**
1. **Dispositivo** → Invia dati su **RX Characteristic**
2. **App** → Riceve notifica su `WearReceivedDataCallback`
3. **App** → Elabora dati modalità `mode == 34/35`
4. **App** → Converte timestamp UTC e crea oggetti `HistoryOfHeartRate`
5. **App** → Notifica callback `onHistoryOfHRDataReceived()`

### 5. **Sicurezza e affidabilità**

#### **Checksum nei comandi**
```java
private void sendCommand(byte cmd, int... values) {
    // Costruisce: [0xFF, length, cmd, ...values, checksum]
    byte check = checkSum(result);
    byte[] command = HexUtil.append(result, check);
    writeTxCharacteristic(command);
}
```

#### **Gestione errori di comunicazione**
- **TX write failures**: Log e retry automatico
- **RX notifications loss**: Timeout e re-richiesta dati
- **Service disconnection**: Riconnessione automatica

## �🔧 Comandi di controllo principali

| Comando | Byte | Descrizione |
|---------|------|-------------|
| `setUTCTime()` | `0x08` | Sincronizza l'ora UTC del dispositivo |
| `getHistoryOfHRRecord()` | `0x21` | Ottiene record aggregati HR |
| `getHistoryOfHRData(stamp)` | `0x22` | Ottiene dati dettagliati HR da timestamp |
| `getHistoryOfSport()` | `0x16` | Ottiene cronologia sport |
| `getHistoryOfSleep()` | `0x05` | Ottiene cronologia sonno |

---

## 🗂️ **Altri dati disponibili dal dispositivo**

Oltre ai dati HR, il WearManager può ottenere molti altri tipi di dati fitness e sensori:

### 📊 **Dati del sonno - Sleep History**
```java
public void getHistoryOfSleep() {
    this.mReceivedDataCallback.clearType(22);
    sendCommand((byte)5, new int[] { 2 });
}
```
- **Comando**: `0x05` (5)
- **Parametri**: `{ 2 }`
- **Modello dati**: `HistorySleep`
  ```java
  public class HistorySleep {
      public long utc;        // Timestamp UTC del periodo di sonno
      public int[] actions;   // Array di azioni/stati del sonno
  }
  ```
- **Callback**: `HistoryOfSleepCallback.onHistoryOfSleepReceived()`

### 🏃 **Dati sportivi - Sport History**
```java
public void getHistoryOfSport() {
    this.mReceivedDataCallback.clearType(2);
    sendCommand((byte)22, new int[] { 0 });
}
```
- **Comando**: `0x16` (22)
- **Parametri**: `{ 0 }`
- **Modello dati**: `HistoryOfSport`
  ```java
  public class HistoryOfSport {
      public long startTime;  // Tempo inizio attività
      public long endTime;    // Tempo fine attività
      public long step;       // Numero di passi
      public long calorie;    // Calorie bruciate
  }
  ```
- **Callback**: `HistoryOfSportCallback.onHistoryOfSportReceived()`

### 🫁 **Dati respiratori - Respiratory Rate (RR)**

#### **RR Record (timestamp)**
```java
public void getHistoryOfRRRecord() {
    this.mReceivedDataCallback.clearType(8);
    sendCommand((byte)36, new int[0]);
}
```
- **Comando**: `0x24` (36)
- **Parametri**: nessuno
- **Scopo**: Timeline degli eventi respiratori

#### **RR Data (valori dettagliati)**
```java
public void getHistoryOfRRData(long stamp) {
    this.mReceivedDataCallback.clearType(16);
    sendCommand((byte)37, HexUtil.append(1, utc2Bytes(stamp)));
}
```
- **Comando**: `0x25` (37)
- **Parametri**: `1` + timestamp UTC
- **Scopo**: Valori respiratori dettagliati

### 📱 **Dati sensori accelerometro - 3D Data**
```java
public void getHistoryOf3D() {
    sendCommand((byte)119, new int[] { 0 });
}
```
- **Comando**: `0x77` (119)
- **Parametri**: `{ 0 }`
- **Modello dati**: `HistoryOf3D`
  ```java
  public class HistoryOf3D {
      public int accX;    // Accelerazione asse X
      public int accY;    // Accelerazione asse Y  
      public int accZ;    // Accelerazione asse Z
  }
  ```
- **Callback**: `HistoryOf3DDataCallback.onHistoryOf3DDataReceived()`

### 👣 **Dati passi a intervalli - Interval Steps**
```java
public void getIntervalSteps() {
    this.mReceivedDataCallback.clearType(18);
    sendCommand((byte)64, new int[] { 0 });
}
```
- **Comando**: `0x40` (64)
- **Parametri**: `{ 0 }`
- **Modello dati**: `IntervalStep`
  ```java
  public class IntervalStep {
      public long stamp;   // Timestamp dell'intervallo
      public int steps;    // Numero di passi nell'intervallo
  }
  ```
- **Callback**: `IntervalStepCallback.onIntervalStepReceived()`

### 👆 **Dati tocco singolo - Single Tap Records**
```java
public void getSingleTapRecords() {
    this.mReceivedDataCallback.clearType(20);
    sendCommand((byte)66, new int[] { 0 });
}
```
- **Comando**: `0x42` (66)
- **Parametri**: `{ 0 }`
- **Scopo**: Eventi di tocco singolo sul dispositivo

### 📋 **Record singolo generico - Single Record**
```java
public void getHistoryOfSingleRecord(long stamp) {
    sendCommand((byte)73, utc2Bytes(stamp));
}
```
- **Comando**: `0x49` (73)
- **Parametri**: timestamp UTC (8 bytes)
- **Scopo**: Record generico da timestamp specifico

### 👤 **Informazioni utente - User Info**
```java
public void getUserInfo() {
    sendCommand((byte)3, new int[] { 0 });
}
```
- **Comando**: `0x03` (3)
- **Parametri**: `{ 0 }`
- **Callback**: `UserInfoCallback.onUserInfoReceived(int age, int sex, int weight, int height, long userId)`

### ⚙️ **Configurazione sensori 3D/6D**

#### **Frequenza sensore 3D**
```java
public void set3DFrequency(@IntRange(from = 0L, to = 4L) int frequency) {
    sendCommand((byte)116, new int[] { 0, 11, (byte)frequency });
}

public void get3DFrequency() {
    sendCommand((byte)117, new int[] { 0, 11 });
}
```

#### **Abilitazione sensore 3D**
```java
public void set3DEnabled(boolean enabled) {
    sendCommand((byte)116, new int[] { 0, 12, enabled ? 1 : 0 });
}

public void get3DStatus() {
    sendCommand((byte)117, new int[] { 0, 12 });
}
```

#### **Configurazione sensore 6D**
```java
public void set6DFrequency(@IntRange(from = 0L, to = 3L) int frequency) {
    sendCommand((byte)98, new int[] { frequency });
}

public void get6DFrequency() {
    sendCommand((byte)97, new int[] { 0 });
}
```

### 📊 **Tabella riassuntiva comandi**
| Tipo Dato | Comando | Hex | Parametri | ClearType | Descrizione |
|-----------|---------|-----|-----------|-----------|-------------|
| **Sleep** | 5 | 0x05 | `{2}` | 22 | Cronologia del sonno |
| **Sport** | 22 | 0x16 | `{0}` | 2 | Attività sportive |
| **HR Record** | 33 | 0x21 | `{0}` | 4 | Timeline frequenza cardiaca |
| **HR Data** | 34 | 0x22 | UTC stamp | 6 | Valori frequenza cardiaca |
| **RR Record** | 36 | 0x24 | none | 8 | Timeline respirazione |
| **RR Data** | 37 | 0x25 | UTC stamp | 16 | Valori respirazione |
| **Steps** | 64 | 0x40 | `{0}` | 18 | Passi a intervalli |
| **Tap** | 66 | 0x42 | `{0}` | 20 | Eventi tocco |
| **Single Record** | 73 | 0x49 | UTC stamp | - | Record singolo |
| **3D Data** | 119 | 0x77 | `{0}` | - | Dati accelerometro |
| **User Info** | 3 | 0x03 | `{0}` | - | Informazioni utente |

---

## 🔧 **Metodi di decodifica dati**

**Risposta alla domanda**: Ogni tipo di dato ha la **sua decodifica specifica** nel metodo `WearReceivedDataCallback.onDataReceived()`. Il protocollo base è comune, ma il parsing è personalizzato per tipo.

### 📋 **Struttura comune del protocollo**
```java
public void onDataReceived(BluetoothDevice device, Data data) {
    byte[] value = data.getValue();
    int mode = data.getIntValue(17, 2).intValue();  // Byte 2: tipo comando
    
    // Switch basato su 'mode' per parsing specifico
    if (mode == 3) {        // User Info
    } else if (mode == 5) { // Sleep History  
    } else if (mode == 22) { // Sport History
    } else if (mode == 33) { // HR Record
    } else if (mode == 34) { // HR Data
    // ... etc per ogni tipo
}
```

### 🎯 **Parsing specifico per tipo dato**

#### **1. Sleep History (mode == 5)**
```java
// Parsing con lunghezza variabile per azioni sonno
int cmd = getIntParse(value, 3, 1);
if (cmd == 3) {
    for (int j = 4; j < value.length; j++) {
        int len = value[j];           // Lunghezza array azioni
        j++;
        long utc = getLongParse(value, j, 4);
        utc *= 1000L;
        utc -= 28800000L;             // Correzione timezone
        int[] actions = new int[len]; // Array dinamico
        for (int i = 0; i < len; i++) {
            actions[i] = getIntParse(value, i + j, 1);
        }
        HistorySleep historySleep = new HistorySleep(utc, actions);
    }
}
```

#### **2. Sport History (mode == 22)**
```java
// Record fissi da 10 bytes
private void parseSportHistory(byte[] value) {
    for (int i = 0; i < value.length / 10; i++) {
        int offset = i * 10;
        long stamp = getLongParse(value, offset, 4);      // 4 bytes timestamp
        long step = getLongParse(value, offset + 4, 3);   // 3 bytes steps
        long calorie = getLongParse(value, offset + 7, 3); // 3 bytes calories
        stamp = DateUtil.restoreZoneUTC(stamp);
        mHistoryOfSports.add(new HistoryOfSport(stamp, step, calorie));
    }
}
```

#### **3. HR Record (mode == 33)** 
```java
// 4 bytes per record, parsing big-endian
long utcTag = getLongParse(value, 3, 4);
if (utcTag != END_TAG) {
    mPackages.add(data);  // Multi-packet accumulation
} else {
    for (int index = 0; index < mPackages.size(); index++) {
        slice = subSlice(3, mPackages.get(index).getValue());
        for (int i = 0; i < slice.length / 4; i++) {
            offset = i * 4;
            long stamp = getLongParse(slice, offset, 4);
            long record = DateUtil.restoreZoneUTC(stamp);
            mHistoryOfRecords.add(new HistoryOfRecord(stamp, record));
        }
    }
}
```

#### **4. HR Data (mode == 34)**
```java
// 1 byte per valore HR, timestamp incrementale
if (!isStamp) {
    mStamp = getLongParse(value, 3, 4);  // Timestamp iniziale
    isStamp = true;
}
for (int i = 4; i < slice.length; i++) {  // Skip 4 bytes header
    int heart = getIntParse(slice, i, 1); // 1 byte HR value
    long stamp = DateUtil.restoreZoneUTC(mStamp);
    HistoryOfHeartRate heartRate = new HistoryOfHeartRate(stamp, heart);
    mStamp++; // Incrementa timestamp
}
```

#### **5. Interval Steps (mode == 64)**
```java
// Record fissi da 8 bytes
for (int i = 0; i < slice.length / 8; i++) {
    int offset = i * 8;
    long stamp = DateUtil.restoreZoneUTC(getLongParse(slice, offset, 4));  // 4 bytes timestamp
    int step = getIntParse(slice, offset + 4, 4);                          // 4 bytes steps
    IntervalStep intervalStep = new IntervalStep(stamp, step);
}
```

#### **6. Single Tap Records (mode == 66)**
```java
// Record fissi da 4 bytes (solo timestamp)
for (int i = 0; i < slice.length / 4; i++) {
    int offset = i * 4;
    long stamp = getLongParse(slice, offset, 4);
    long record = DateUtil.restoreZoneUTC(stamp);
    mSingleTapRecords.add(new HistoryOfRecord(stamp, record));
}
```

#### **7. 3D Data (mode == 119/120)**
```java
// Record fissi da 6 bytes per coordinata XYZ
int length = 6;
byte[] slice = subSlice(3, data.getValue());
for (int i = 0; i < slice.length / length; i++) {
    int offset = i * length;
    int accelerometerX = getSInt16(slice, offset);      // 2 bytes signed
    int accelerometerY = getSInt16(slice, offset + 2);  // 2 bytes signed  
    int accelerometerZ = getSInt16(slice, offset + 4);  // 2 bytes signed
    HistoryOf3D history = new HistoryOf3D(accelerometerX, accelerometerY, accelerometerZ);
}
```

#### **8. 6D Sensor Raw (mode == 96)**
```java
// Record fissi da 12 bytes (giroscopio + accelerometro)
for (int i = 0; i < data.size() / 12; i++) {
    int offset = i * 12;
    int gyroscopeX = data.getIntValue(34, offset).intValue();      // 2 bytes
    int gyroscopeY = data.getIntValue(34, offset + 2).intValue();  // 2 bytes
    int gyroscopeZ = data.getIntValue(34, offset + 4).intValue();  // 2 bytes
    int accelerometerX = data.getIntValue(34, offset + 6).intValue();  // 2 bytes
    int accelerometerY = data.getIntValue(34, offset + 8).intValue();  // 2 bytes
    int accelerometerZ = data.getIntValue(34, offset + 10).intValue(); // 2 bytes
}
```

### 🛠️ **Utility methods comuni**
```java
// Parsing big-endian multi-byte
private long getLongParse(byte[] bytes, int pos, int len) {
    long val = 0;
    len += pos;
    for (int i = pos; i < len; i++) {
        val <<= 8;
        val |= (long) bytes[i] & 0xFF;
    }
    return val;
}

// Parsing signed 16-bit
private int getSInt16(byte[] data, int offset) {
    return (short)((data[offset] & 0xFF) | ((data[offset + 1] & 0xFF) << 8));
}

// Skip header bytes
private byte[] subSlice(final int start, final byte[] value) {
    return subByte(value, start, value.length - 1);
}
```

### 📊 **Tabella formati dati**
| Tipo | Mode | Record Size | Formato | Multi-packet | Note |
|------|------|-------------|---------|--------------|------|
| **Sleep** | 5 | Variabile | `len + utc + actions[]` | No | Lunghezza dinamica azioni |
| **Sport** | 22 | 10 bytes | `timestamp(4) + steps(3) + cal(3)` | Si | Record fissi |
| **HR Record** | 33 | 4 bytes | `timestamp(4)` | Si | Solo timestamp UTC |
| **HR Data** | 34 | 1 byte | `hr_value(1)` + timestamp incrementale | Si | Valori sequenziali |
| **Steps** | 64 | 8 bytes | `timestamp(4) + steps(4)` | Si | Intervalli temporali |
| **Tap** | 66 | 4 bytes | `timestamp(4)` | Si | Eventi tocco |
| **3D** | 119 | 6 bytes | `x(2) + y(2) + z(2)` signed | No | Accelerometro |
| **6D Raw** | 96 | 12 bytes | `gyro(6) + accel(6)` | No | Sensore completo |

---

## 📡 **Modalità di acquisizione dati: Storici vs Live**

**Risposta**: Il dispositivo supporta **ENTRAMBE le modalità** - dati storici localizzati nel device E dati live in tempo reale!

### 🗄️ **Dati storici (localizzati nel device)**
I metodi `getHistoryOf...()` recuperano dati **già memorizzati** nel dispositivo:

```java
// Esempi di dati storici
getHistoryOfHRRecord();    // Timeline HR registrata
getHistoryOfHRData();      // Valori HR misurati 
getHistoryOfSleep();       // Dati sonno memorizzati
getHistoryOfSport();       // Attività sportive registrate
getIntervalSteps();        // Passi a intervalli salvati
```

**Caratteristiche dati storici:**
- 💾 **Memorizzati localmente** nel dispositivo
- 📅 **Timeline completa** con timestamp UTC
- 🔄 **Multi-packet** per grandi volumi di dati
- 📊 **Batch processing** con END_TAG per fine trasmissione
- 🕒 **Recuperabili offline** (device disconnesso dalla app)

### 🔴 **Dati live (tempo reale)**
Il sistema abilita **notifiche Bluetooth** per dati in tempo reale:

#### **🫀 HR Live via servizio standard HR**
```java
// Inizializzazione notifiche HR live
setNotificationCallback(mHeartRateCharacteristic)
    .with(mHeartRateMeasureDataCallback);
enableNotifications(mHeartRateCharacteristic).enqueue();

// Callback per HR in tempo reale
public void onHeartRateMeasurementReceived(BluetoothDevice device, 
    int heartRate, Boolean contactDetected, Integer energyExpanded, 
    List<Integer> rrIntervals) {
    // HR istantanea ricevuta via notifica BLE
}
```

#### **🔥 Altri sensori live via RX Characteristic**
```java
// Notifiche live per tutti gli altri sensori
setNotificationCallback(mRXCharacteristic)
    .with(mReceivedDataCallback);
enableNotifications(mRXCharacteristic).enqueue();

// Callbacks live automatiche per:
onSportReceived(device, step, distance, calorie);           // Sport live
onBloodOxygenReceived(device, bSwitch, value, gesture...);  // Ossigeno live
onTemperatureReceived(device, environment, wrist, body);    // Temperatura live
onAccelerometerReceived(device, x, y, z);                   // Accelerometro live
```

### 📊 **Tabella comparativa: Storico vs Live**

| Aspetto | **Dati Storici** | **Dati Live** |
|---------|------------------|---------------|
| **Triggering** | Comando `getHistoryOf...()` | Notifiche BLE automatiche |
| **Frequenza** | On-demand | Continua (quando sensore attivo) |
| **Volume** | Alto (batch multi-packet) | Basso (singoli valori) |
| **Latenza** | Media | Bassissima (real-time) |
| **Connessione** | Richiede connessione per recupero | Richiede connessione attiva |
| **Storage** | Device memory → App | Sensore → App direct |
| **Formato** | Multi-record con END_TAG | Single-value notifications |
| **Uso** | Analisi, grafici, trend | Monitoring live, alert |

### 🎯 **Casi d'uso specifici**

#### **📈 Per analisi e trend (Storici):**
```java
// Recupera tutta la cronologia HR per grafico
getHistoryOfHRData();  // Giorni/settimane di dati

// Analizza pattern del sonno
getHistoryOfSleep();   // Cicli sonno registrati

// Verifica attività sportive
getHistoryOfSport();   // Sessioni allenamento salvate
```

#### **⚡ Per monitoring real-time (Live):**
```java
// Monitoring continuo durante workout
onHeartRateMeasurementReceived() → Update UI live

// Alert ossigeno basso
onBloodOxygenReceived() → Trigger allarme

// Rilevamento movimento
onAccelerometerReceived() → Gesture detection
```

### 🔧 **Architettura tecnica**

#### **Canali di comunicazione:**
1. **`mHeartRateCharacteristic`** → HR live (standard BLE HR service)
2. **`mRXCharacteristic`** → Tutti gli altri dati live + comandi storici  
3. **`mTXCharacteristic`** → Invio comandi al device
4. **`mCustomRxCharacteristic`** → Dati custom (dispositivi CL833)

#### **Flusso dati ibrido:**
```
📱 App requests historical data
     ↓ sendCommand()
🏃 Device processes & sends multi-packet response
     ↓ RX Characteristic  
📱 App receives & parses historical batch

🔄 Parallel continuous flow:
⌚ Device sensors → Live notifications → 📱 App real-time updates
```

Il sistema è **estremamente sofisticato** - combina il meglio di entrambi i mondi: **storage completo** per analisi retrospettive e **streaming live** per interazioni immediate! 🚀

---

## �️ **Recupero Dati degli Ultimi 7 Giorni**

**IMPORTANTE**: Non esiste un metodo specifico "7days" nel WearManager, ma puoi ottenere i dati degli ultimi 7 giorni usando diversi approcci:

### Approccio 1: Filtraggio Client-Side (Consigliato)
```java
// Recupera tutti i record HR
getHistoryOfHRRecord();

// Nel callback, filtra per gli ultimi 7 giorni
public void onHistoryOfHRRecordReceived(BluetoothDevice device, List<HistoryOfRecord> records) {
    Calendar sevenDaysAgo = Calendar.getInstance();
    sevenDaysAgo.add(Calendar.DAY_OF_YEAR, -7);
    long sevenDaysAgoTimestamp = sevenDaysAgo.getTimeInMillis();
    
    List<HistoryOfRecord> last7DaysRecords = records.stream()
        .filter(record -> record.timestamp >= sevenDaysAgoTimestamp)
        .collect(Collectors.toList());
        
    // Utilizza last7DaysRecords per la tua analisi
}
```

### Approccio 2: Timestamp di Inizio Personalizzato
```java
// Calcola timestamp UTC 7 giorni fa
Calendar sevenDaysAgo = Calendar.getInstance();
sevenDaysAgo.add(Calendar.DAY_OF_YEAR, -7);
long stamp = sevenDaysAgo.getTimeInMillis() / 1000L; // Converti in secondi UTC

// Richiedi dati HR dettagliati da 7 giorni fa in poi
getHistoryOfHRData(stamp);

// Questo restituirà tutti i dati HR dalla data specificata
```

### Approccio 3: IntervalStep per Pattern Settimanali
```java
// Per dati a intervalli (potrebbero includere dati regolari/periodici)
getIntervalSteps();

// Nel callback filtra per timestamp degli ultimi 7 giorni
public void onIntervalStepReceived(BluetoothDevice device, List<IntervalStep> steps) {
    Calendar sevenDaysAgo = Calendar.getInstance();
    sevenDaysAgo.add(Calendar.DAY_OF_YEAR, -7);
    long cutoffTimestamp = sevenDaysAgo.getTimeInMillis();
    
    List<IntervalStep> last7DaysSteps = steps.stream()
        .filter(step -> step.stamp >= cutoffTimestamp)
        .collect(Collectors.toList());
}
```

### 📊 **Metodi con Supporto Timestamp**

I seguenti metodi accettano un parametro timestamp di inizio:

| Metodo | Comando | Descrizione |
|--------|---------|-------------|
| `getHistoryOfHRData(stamp)` | `0x22` | Dati HR dettagliati da timestamp |
| `getHistoryOfRRData(stamp)` | `0x25` | Dati respiratory da timestamp |
| `getHistoryOfSingleRecord(stamp)` | `0x49` | Record singolo da timestamp |

### ⚡ **Raccomandazione**

**Usa l'Approccio 1** (filtraggio client-side) perché:
- ✅ Più semplice da implementare
- ✅ Massima flessibilità (puoi cambiare periodo facilmente)  
- ✅ Non dipende dalla precisione del timestamp del dispositivo
- ✅ Funziona con tutti i tipi di dati storici

---

## �📝 Note importanti

- La sincronizzazione UTC avviene **automaticamente** ad ogni connessione
- I timestamp sono gestiti in **secondi UTC** per il dispositivo e **millisecondi locali** per l'app
- Il sistema gestisce automaticamente **fuso orario** e **ora legale**
- Il dispositivo mantiene l'ora tramite **RTC interno** anche quando disconnesso
- Tutti i dati storici utilizzano il sistema UTC sincronizzato per garantire coerenza temporale