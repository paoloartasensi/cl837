# Chileaf BLE Protocol v0.6 - Documentazione Completa

## Informazioni Documento
- **Preparato da**: Rickon
- **Data emissione**: 2022-6-8
- **Revisione**: 0.6

## Storico Revisioni
| Versione | Data | Editore | Revisore | Descrizione |
|----------|------|---------|----------|-------------|
| V0.1 | 2019.10.09 | jzhou | - | First release |
| V0.2 | 2019.10.14 | jzhou | - | Bluetooth broadcast data |
| V0.3 | 2020.3.20 | Rickon | - | - |
| V0.4 | 2021.9.10 | Rickon | - | - |

## Indice Completo
1. Definition of Bluetooth UUID
   - 1.1. Bluetooth broadcast name
   - 1.2. Bluetooth broadcast Manufacturer data
   - 1.3. Service and characteristics
2. Definition of data format
   - 2.1. Data format
   - 2.2. Frame field description
   - 2.3. Checksum calculation method
3. Definition of command
   - 3.1. System commands
     - 3.1.1. Get function list and protocol version-0x01(reserve)
     - 3.1.2. Get user info and status information-0x03
     - 3.1.3. Configure user information-0x04
     - 3.1.4. Set UTC time (with time zone)-0x08
   - 3.2. Heart rate and step command
     - 3.2.1. Real-time sports data notification-0x15
     - 3.2.2. Get 7days exercise data-0x16
     - 3.2.3. Heart rate history data list request-0x21
     - 3.2.4. Heart rate history data request-0x22
     - 3.2.5. Acceleration 3D raw data-0x0C(Optional)
     - 3.2.6. SPO2 Mode-0x37
     - 3.2.7. Temperature
   - 3.3. Rope skipping
     - 3.3.1. Rope status-0x40
     - 3.3.2. Realtime rope notify-0x41
     - 3.3.3. Set mode-0x42
     - 3.3.4. Clear-0x45

---

## 1. Definition of Bluetooth UUID

### 1.1. Bluetooth Broadcast Name
**Formato**: `[Model name]-xxxxxxx`
- `xxxxxxx` è un codice ID a 7 cifre

### 1.2. Bluetooth Broadcast Manufacturer Data

| Campo | Valore/Descrizione |
|-------|-------------------|
| **Manufacturer identification ID** | `0xff04` |
| **Byte 0** | Reserve |
| **Byte 1** | Battery level (0~100%) |
| **Byte 2** | Reserve |
| **Byte 3** | Heart rate data |

### 1.3. Service and Characteristics

#### Custom Service UUID
**UUID**: `aae28f00-71b5-42a1-8c3c-f9cf6ac969d0`

| Type | Attribute UUID | Description | Attribute |
|------|---------------|-------------|-----------|
| service | 8f00 | - | - |
| TX characteristics | 8f01 | Read data from device | NOTIFY |
| RX characteristics | 8f02 | Write data to device | WRITE |

#### Heart Rate Service

| Type | Attribute UUID | Description | Attribute |
|------|---------------|-------------|-----------|
| Heart rate service | 180d | - | - |
| Heart rate data | 2a37 | Device notification | NOTIFY |
| Wear position | 2a38 | Read data from device | READ |

#### Device Information Service

| Type | Attribute UUID | Description | Attribute |
|------|---------------|-------------|-----------|
| The DEVICE service | 180a | - | - |
| Name of manufacturer | 2a29 | Read data from device | READ |
| The Model name | 2a24 | Read data from device | READ |
| Serial number | 2a25 | Read data from device | READ |
| Hardware version | 2a27 | Read data from the device | READ |
| The Firmware version | 2a26 | Read data from the device | READ |
| Software version | 2a28 | Read data from the device | READ |

#### Battery Service

| Type | Attribute UUID | Description | Attribute |
|------|---------------|-------------|-----------|
| The DEVICE service | 180f | - | - |
| battery | 2a19 | Read and notify data | READ, NOTIFY |

**Nota**: L'APP può leggere il livello della batteria direttamente, inoltre il dispositivo può notificarlo.

---

## 2. Definition of Data Format

### 2.1. Data Format

| Head | Length | Command | Data | Checksum |
|------|--------|---------|------|----------|
| 0xFF | N + 4 | 0x** | -- | checksum |
| 1 byte | 1 byte | 1 byte | N bytes | 1 byte |

### 2.2. Frame Field Description

| Item | Byte | Type | Description | Instance |
|------|------|------|-------------|----------|
| **Head** | 1 | HEX | Pack start byte | 0xFF |
| **Length** | 1 | HEX | From head to checksum | 0x04 + N |
| **Command** | 1 | HEX | Function of the command | 0x01 |
| **Data** | N | HEX | The data content | -- |
| **Checksum** | 1 | HEX | The sum of whole pack except checksum | checksum |

### 2.3. Checksum Calculation Method

**Procedura**:
1. Prima calcola la somma di head/length/command/data
2. Poi sottrai la somma da 0
3. Infine XOR con 0x3a, e prendi il valore a 8-bit basso come valore del checksum

**Codice di riferimento**:
```java
Public byte calcChecksum(byte[] dat) {
    Int i = 0;
    Byte res = 0;
    Int len = dat.length-1;
    For (i = 0; i < len; i++) {
        Res += dat[i];
    }
    Int temp = (int) res;
    Temp &= 0xFF;
    Temp = (0 - temp);
    Temp &= 0xFF;
    Temp ^= 0x3a;
    Res = (byte) (temp & 0xff);
    Return res;
}
```

---

## 3. Definition of Command

### 3.1. System Commands

#### 3.1.1. Get Function List and Protocol Version - 0x01 (reserve)

| Data bits | Command from APP | Reply from device | Functional description |
|-----------|------------------|-------------------|------------------------|
| Command | 0x01 | 0x01 | Get the feature list and protocol version |
| | | | reserve |

#### 3.1.2. Get User Info and Status Information - 0x03

| Data bits | APP request | Device reply | Functional description |
|-----------|-------------|--------------|------------------------|
| Command | 0x03 | 0x03 | Gets user and status information |
| Data segment | None | | |
| | | 0x** | ECG is open: 0 is not open, 1 is open |
| | | 0x** | Device charging information:<br>0: not charged<br>1: in charge<br>2: The battery is fully charged while charging |
| | | 0x** | The percentage of battery power is 0~100% |
| | | 0x** | User age |
| | | 0x** | User gender: 0-female, 1-male |
| | | 0x** | User weight: unit: kg |
| | | 0x** | User height: unit: cm |
| | | 0x** | User phone number: high byte |
| | | 0x** | User phone number: |
| | | 0x** | User phone number: |
| | | 0x** | User phone number: |
| | | 0x** | User phone number: low byte |

#### 3.1.3. Configure User Information - 0x04

| Data bits | APP send | Device reply | Functional description |
|-----------|----------|--------------|------------------------|
| Command | 0x04 | 0x04 | Configure user information |
| | 0x** | NA | User age |
| | 0x** | | User gender: 0-female, 1-male |
| | 0x** | | User weight: unit: kg |
| | 0x** | | User height: unit cm |
| | 0x** | | User phone number: high byte |
| | 0x** | | User phone number: |
| | 0x** | | User phone number: |
| | 0x** | | User phone number: |
| | 0x** | | User phone number: low byte |

#### 3.1.4. Set UTC Time (with time zone) - 0x08

| Data bits | APP send | Device reply | Functional description |
|-----------|----------|--------------|------------------------|
| Command | 0x08 | 0x08 | Configure user information |
| | 0x** | | UTC: high byte |
| | 0x** | | UTC: |
| | 0x** | | UTC: |
| | 0x** | | UTC: low byte |

### 3.2. Heart Rate and Step Command

#### 3.2.1. Real-time Sports Data Notification - 0x15

| Data bits | APP reply | Device notify | Functional description |
|-----------|-----------|---------------|------------------------|
| Command | | 0x15 | |
| | | 0x** | Step high byte |
| | | 0x** | Step middle byte |
| | | 0x** | Step low byte |
| | | 0x** | Distance high byte – unit: cm |
| | | 0x** | Distance middle byte |
| | | 0x** | Distance low byte |
| | | 0x** | Calorie high – unit: 0.1 Kcal |
| | | 0x** | Calorie middle |
| | | 0x** | Calorie low |

#### 3.2.2. Get 7days Exercise Data - 0x16

| Data bits | APP request device | Device to the APP | Functional description |
|-----------|-------------------|-------------------|------------------------|
| Command | 0x16 | 0x16 | Seven days of historical data |
| | NA | 0x** | UTC: high |
| | | 0x** | UTC: |
| | | 0x** | UTC: |
| | | 0x** | UTC: low |
| | | 0x** | Step of one day(n) high |
| | | 0x** | Step of one day: |
| | | 0x** | Step of one day: low |
| | | 0x** | Calories of one day (n) : high |
| | | 0x** | Calories of one day: |
| | | 0x** | Calories of one day: low |
| | | … | … |

#### 3.2.3. Heart Rate History Data List Request - 0x21

| Data bits | The APP requests | Device reply APP | Functional description |
|-----------|------------------|------------------|------------------------|
| Command | 0x21 | 0x21 | Request a list of heart rate history data |
| | | 0x** | UTC 0: high |
| | | 0x** | UTC 0: |
| | | 0x** | UTC 0: |
| | | 0x** | UTC 0: low |
| | | ... | |
| | | 0x** | UTC n: |
| | | 0x** | UTC n: |
| | | 0x** | UTC n: |
| | | 0x** | UTC n: |

**Note**: UTC time means that there is a pack of heart rate data at the beginning of this time. If there is no historical data, a UTC time 0xffffffff reply.

#### 3.2.4. Heart Rate History Data Request - 0x22

| Data bits | The APP requests | Device reply APP | Functional description |
|-----------|------------------|------------------|------------------------|
| Command | 0x22 | 0x22/0x23 | Request heart rate history |
| | 1- Request single data<br>2- Request all data(reserve)<br>3- Request all data after utc(reserve) | | |
| | 0x** | | UTC or package number: high |
| | 0x** | | UTC or package number: |
| | 0x** | | UTC or package number: |
| | 0x** | | UTC or package number: low |
| | | 0x** | UTC n: high |
| | | 0x** | UTC n: |
| | | 0x** | UTC n: |
| | | 0x** | UTC n: low |
| | | 0x** | Heart rate data 0 |
| | | 0x** | Heart rate data 1 |
| | | 0x** | Heart rate data n |

**Note**:
1. APP needs to request the heart rate list(0x21) first and get all the UTC time of records, and then use the UTC time to request data
2. If package end or no history, device will send 0x23 command to indicate it
3. The first package of each history data will include UTC time, from the second package it send package number instead of UTC

**Struttura aggiuntiva per dati con ACT**:

| Data bits | Description |
|-----------|-------------|
| 0x** | UTC or package number: |
| 0x** | UTC or package number: low |
| 0x** | ACT-0: 5 minute action index |
| 0x** | ACT-1: 5-minute action index |
| 0x** | ACT-2: 5 minute action index |
| 0x** | ACT-n: 5 minute action index |

#### 3.2.5. Acceleration 3D Raw Data - 0x0C (Optional)

| Data bits | The APP requests | Device reply APP | Functional description |
|-----------|------------------|------------------|------------------------|
| | | 0x0C | 3D raw data |
| | | 0x** | Xl1: low byte |
| | | 0x** | Xh1: hight byte |
| | | 0x** | Yl1 |
| | | 0x** | Yh1 |
| | | 0x** | Zl1 |
| | | 0x** | Zh1 |
| | | 0x** | Xl2 |
| | | 0x** | Xh2 |
| | | 0x** | ……….. |
| | | 0x** | zln |
| | | 0x** | zhn |

**Note**: Send every 250ms

#### 3.2.6. SPO2 Mode - 0x37

| Data bits | The APP requests | Device reply APP | Functional description |
|-----------|------------------|------------------|------------------------|
| Command | 0x37 | 0x37 | |
| | 0x** | 0x** | 0: exit spo2 mode<br>1: enter spo2 mode<br>2: inquire |
| | | 0x** | SPO2 value |
| | | 0x** | 0: wrist posture wrong<br>1: wrist posture correct(face up) |
| | | 0x** | 0: no signal<br><8: signal week<br>>15: signal good |
| | | 0x** | 0: not wear<br>1: wear |

#### 3.2.7. Temperature - 0x38

| Data bits | The APP requests | Device reply APP | Functional description |
|-----------|------------------|------------------|------------------------|
| Command | 0x38 | 0x38 | Real time temperature (unit: *10℃) |
| | | 0x** | Ambient temperature (MSB) |
| | | 0x** | |
| | | 0x** | Wrist temperature (MSB) |
| | | 0x** | |
| | | 0x** | body temperature (MSB) |
| | | 0x** | |

### 3.3. Rope Skipping

#### 3.3.1. Rope Status - 0x40

| Data bits | The APP requests | Device reply APP | Functional description |
|-----------|------------------|------------------|------------------------|
| command | 0x40 | 0x40 | |
| 1 | | | UTC MSB |
| 2 | | | |
| 3 | | | |
| 4 | | | |
| 5 | | | MODE: 0:FREE; 1:COUNTER; 2:TIMER |
| 6 | | | COUNTER: target < 9999(MSB) |
| 7 | | | |
| 8 | | | COUNTER: jumps MSB |
| 9 | | | |
| 10 | | | COUNTER: TIME MINUTE |
| 11 | | | SECOND |
| 12 | | | COUNTER: Calorie MSB |
| 13 | | | |
| 14 | | | TIMER: Target time Minute(<99) |
| 15 | | | second |
| 16 | | | TIMER: Current time Minute |
| 17 | | | second |
| 18 | | | TIMER: Jumps |
| 19 | | | |
| 20 | | | TIMER: Calorie MSB |
| 21 | | | |
| 22 | | | FREE: Jumps |
| 23 | | | |
| 24 | | | FREE: TIME MINUTE |
| 25 | | | SECOND |
| 26 | | | FREE: Calorie MSB |
| 27 | | | |
| 28 | | | Jumps of day |
| 29 | | | |
| 30 | | | Time of day |
| 31 | | | |
| 32 | | | Calorie of day |
| 33 | | | |

#### 3.3.2. Realtime Rope Notify - 0x41

| Data bits | The APP requests | Device reply APP | Functional description |
|-----------|------------------|------------------|------------------------|
| command | | 0x41 | |
| 1 | | | Current MODE |
| 2 | | | Current jumps |
| 3 | | | |
| 6 | | | Time |
| 7 | | | ms |
| 8 | | | Calorie |
| 9 | | | |
| 10 | | | elapsed time Minute |
| 11 | | | Second |
| 12 | | | Current count down time Minute |
| 13 | | | Second |
| 14 | | | If restart 1: restart. 0: not restart |
| 15 | | | Trip msb |
| 16 | | | |

#### 3.3.3. Set Mode - 0x42

| Data bits | The APP requests | Device reply APP | Functional description |
|-----------|------------------|------------------|------------------------|
| command | 0x42 | 0x42 | 0x42 |
| 1 | | | mode 0,1,2 FREE/COUNTER/TIMER |
| 2 | | | Target counter |
| 3 | | | |
| 4 | | | Timer Minute |
| 5 | | | second |

#### 3.3.4. Clear - 0x45

| Data bits | The APP requests | Device reply APP | Functional description |
|-----------|------------------|------------------|------------------------|
| command | 0x45 | 0x45 | clear |
| 1 | | | |

---

## Note Finali

Questo documento rappresenta la specifica completa del protocollo BLE Chileaf v0.6. Tutti i comandi, formati dati e procedure di comunicazione devono seguire esattamente queste specifiche per garantire la corretta comunicazione con il dispositivo Chileaf CL 831.