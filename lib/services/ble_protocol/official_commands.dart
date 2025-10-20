/// Implementazione dei comandi ufficiali basati sul SDK Android Chileaf
/// Basato sull'analisi di WearManager.java v3.0.4

class OfficialChileafCommands {
  
  /// Converte un timestamp UTC in array di 4 bytes (come nel SDK ufficiale)
  /// Implementazione di utc2Bytes() dal WearManager.java
  static List<int> utcToBytes(int timestamp) {
    return [
      (timestamp >> 24) & 0xFF,
      (timestamp >> 16) & 0xFF, 
      (timestamp >> 8) & 0xFF,
      timestamp & 0xFF,
    ];
  }

  /// Calcola checksum con algoritmo Java corretto (dal WearManager.java decompilato)
  static int calculateChecksum(List<int> data) {
    int sum = 0;
    for (int byte in data) {
      sum += byte;
    }
    int checksum = (-sum) & 0xFF; // Negazione + mask 8-bit
    checksum ^= 0x3A;              // XOR con costante 0x3A
    return checksum & 0xFF;        // Final mask
  }

  /// Costruisce un comando secondo il formato ufficiale: [0xFF][length][command][parameters...][checksum]
  /// Basato su sendCommand() del WearManager.java
  static List<int> buildOfficialCommand(int command, [List<int>? parameters]) {
    List<int> result;
    
    if (parameters != null && parameters.isNotEmpty) {
      int length = parameters.length + 4; // +4 per 0xFF, length, command, checksum
      result = [0xFF, length, command];
      result.addAll(parameters);
    } else {
      result = [0xFF, 4, command]; // 4 = 0xFF + length + command + checksum
    }
    
    int checksum = calculateChecksum(result);
    result.add(checksum);
    
    return result;
  }

  // === COMANDI DISPOSITIVO === 
  
  /// Comando di reset/ripristino ufficiale (0xF3 = -13 nel SDK)
  /// Equivalente a restoration() nel WearManager.java
  static List<int> deviceReset() {
    return buildOfficialCommand(0xF3, [0]);
  }

  /// Comando shutdown (0xF1 = -15 nel SDK)
  /// Equivalente a shutdown() nel WearManager.java  
  static List<int> deviceShutdown() {
    return buildOfficialCommand(0xF1, [0]);
  }

  /// Disabilita Bluetooth (0x3F = 63 nel SDK)
  /// Equivalente a setBluetoothDisabled() nel WearManager.java
  static List<int> disableBluetooth() {
    return buildOfficialCommand(0x3F, [2]);
  }

  // === COMANDI TEMPO ===

  /// Imposta UTC Time (0x08 nel SDK)
  /// Equivalente a setUTCTime(long stamp) nel WearManager.java
  static List<int> setUTCTime([int? timestamp]) {
    timestamp ??= DateTime.now().millisecondsSinceEpoch ~/ 1000;
    return buildOfficialCommand(0x08, utcToBytes(timestamp));
  }

  // === COMANDI SpO2 ===

  /// Controllo SpO2 ufficiale (0x37 = 55 nel SDK)
  /// Equivalente a setBloodOxygen(int mode) nel WearManager.java
  /// mode: 0=off, 1=on, 2=continuous
  static List<int> setBloodOxygen(int mode) {
    return buildOfficialCommand(0x37, [mode, 0]);
  }

  // === COMANDI DATI STORICI ===

  /// Richiede storico sport (0x16 = 22 nel SDK)
  /// Equivalente a getHistoryOfSport() nel WearManager.java
  static List<int> getHistoryOfSport() {
    return buildOfficialCommand(0x16, [0]);
  }

  /// Richiede record HR (0x21 = 33 nel SDK)
  /// Equivalente a getHistoryOfHRRecord() nel WearManager.java
  static List<int> getHistoryOfHRRecord() {
    return buildOfficialCommand(0x21, [0]);
  }

  /// Richiede dati HR dettagliati (0x22 = 34 nel SDK)
  /// Equivalente a getHistoryOfHRData(long stamp) nel WearManager.java
  static List<int> getHistoryOfHRData(int timestamp) {
    List<int> params = [1]; // Sempre 1 come primo parametro
    params.addAll(utcToBytes(timestamp));
    return buildOfficialCommand(0x22, params);
  }

  /// MODALITÀ 2 dalla documentazione: Request all data (param 2)
  static List<int> getHistoryOfHRDataMode2() {
    List<int> params = [2]; // Parametro 2 = Request all data
    // Non serve timestamp per "all data"
    return buildOfficialCommand(0x22, params);
  }

  /// MODALITÀ 3 dalla documentazione: Request all data after UTC (param 3)
  static List<int> getHistoryOfHRDataMode3(int timestamp) {
    List<int> params = [3]; // Parametro 3 = Request all data after UTC
    params.addAll(utcToBytes(timestamp));
    return buildOfficialCommand(0x22, params);
  }

  /// Versione alternativa del comando 0x22 per CL837 - prova senza parametro iniziale
  static List<int> getHistoryOfHRDataAlt(int timestamp) {
    // Prova senza il parametro '1' iniziale - potrebbe essere diverso per CL837
    return buildOfficialCommand(0x22, utcToBytes(timestamp));
  }

  /// Versione con parametro diverso per CL837
  static List<int> getHistoryOfHRDataCL837(int timestamp) {
    List<int> params = [0]; // Prova con 0 invece di 1
    params.addAll(utcToBytes(timestamp));
    return buildOfficialCommand(0x22, params);
  }

  /// Richiede dati HR estesi con intervalli RR (0x23 = 35 nel SDK)
  /// Equivalente a getHistoryOfHRDataExtended(long stamp) nel WearManager.java
  static List<int> getHistoryOfHRDataExtended(int timestamp) {
    List<int> params = [1]; // Sempre 1 come primo parametro
    params.addAll(utcToBytes(timestamp));
    return buildOfficialCommand(0x23, params);
  }

  /// Richiede record RR (0x24 = 36 nel SDK)
  /// Equivalente a getHistoryOfRRRecord() nel WearManager.java
  static List<int> getHistoryOfRRRecord() {
    return buildOfficialCommand(0x24);
  }

  /// Richiede dati RR dettagliati (0x25 = 37 nel SDK)
  /// Equivalente a getHistoryOfRRData(long stamp) nel WearManager.java
  static List<int> getHistoryOfRRData(int timestamp) {
    List<int> params = [1]; // Sempre 1 come primo parametro
    params.addAll(utcToBytes(timestamp));
    return buildOfficialCommand(0x25, params);
  }

  /// Richiede storico sleep (0x05 nel SDK) - LEGACY METHOD
  /// Equivalente a getHistoryOfSleep() nel WearManager.java
  /// ⚠️ NOTA: Usa comando 0x05 legacy, preferire getSleepData31() con comando 0x31
  static List<int> getHistoryOfSleep() {
    return buildOfficialCommand(0x05, [2]);
  }

  /// Richiede dati sleep con comando 0x31 (UFFICIALE da documentazione)
  /// Questo è il comando REALE usato dall'app ufficiale
  /// Formato risposta: 0x31 (dati) o 0x32 (fine/no data)
  /// Granularità: 1 byte = 5 minuti di activity index
  /// Activity index: >20 = sveglio, <20 = sonno leggero, 3x0 consecutivi = sonno profondo
  static List<int> getSleepData31() {
    // Comando 0x31 senza parametri per richiedere tutti i dati sleep
    return buildOfficialCommand(0x31, []);
  }

  /// Richiede dati sleep per uno specifico UTC timestamp (comando 0x31)
  /// @param utcTimestamp: timestamp UTC del periodo sleep da richiedere
  static List<int> getSleepDataForTimestamp(int utcTimestamp) {
    return buildOfficialCommand(0x31, utcToBytes(utcTimestamp));
  }

  /// Richiede record singolo (0x49 = 73 nel SDK)
  /// Equivalente a getHistoryOfSingleRecord(long stamp) nel WearManager.java
  static List<int> getHistoryOfSingleRecord(int timestamp) {
    return buildOfficialCommand(0x49, utcToBytes(timestamp));
  }

  // === COMANDI UTENTE ===

  /// Richiede info utente (0x03 nel SDK)
  /// Equivalente a getUserInfo() nel WearManager.java
  static List<int> getUserInfo() {
    return buildOfficialCommand(0x03, [0]);
  }

  /// Imposta info utente (0x04 nel SDK)
  /// Equivalente a setUserInfo(age, sex, weight, height, userId) nel WearManager.java
  static List<int> setUserInfo(int age, int sex, int weight, int height, int userId) {
    return buildOfficialCommand(0x04, [
      age & 0xFF,
      sex & 0xFF,
      weight & 0xFF,
      height & 0xFF,
      (userId >> 32) & 0xFF,
      (userId >> 24) & 0xFF,
      (userId >> 16) & 0xFF,
      (userId >> 8) & 0xFF,
      userId & 0xFF,
    ]);
  }

  // === COMANDI HEART RATE ===

  /// Richiede stato HR (0x46 = 70 nel SDK)
  /// Equivalente a getHeartRateStatus() nel WearManager.java
  static List<int> getHeartRateStatus() {
    return buildOfficialCommand(0x46, [0]);
  }

  /// Imposta stato HR (0x46 = 70 nel SDK)
  /// Equivalente a setHeartRateStatus(min, max, goal) nel WearManager.java
  static List<int> setHeartRateStatus(int min, int max, int goal) {
    return buildOfficialCommand(0x46, [1, min & 0xFF, max & 0xFF, goal & 0xFF]);
  }

  /// Imposta HR massimo (0x74 = 116 nel SDK)
  /// Equivalente a setHeartRateMax(int max) nel WearManager.java
  static List<int> setHeartRateMax(int max) {
    return buildOfficialCommand(0x74, [0, 6, max & 0xFF]);
  }

  /// Richiede HR massimo (0x75 = 117 nel SDK)
  /// Equivalente a getHeartRateMax() nel WearManager.java
  static List<int> getHeartRateMax() {
    return buildOfficialCommand(0x75, [0, 6]);
  }

  // === COMANDI ALLARMI ===

  /// Imposta allarme HR (0x57 = 87 nel SDK)
  /// Equivalente a setHeartRateAlarm(boolean alarm) nel WearManager.java
  static List<int> setHeartRateAlarm(bool alarm) {
    return buildOfficialCommand(0x57, [alarm ? 1 : 0]);
  }

  /// Imposta modalità allarme HR (0x58 = 88 nel SDK)
  /// Equivalente a setHeartRateAlarmMode(boolean ageBasedMode) nel WearManager.java
  /// ageBasedMode: true = calcolo basato sull'età, false = limiti manuali
  static List<int> setHeartRateAlarmMode(bool ageBasedMode) {
    return buildOfficialCommand(0x58, [ageBasedMode ? 1 : 0]);
  }

  /// Richiede allarme HR (0x5B = 91 nel SDK)
  /// Equivalente a getHeartRateAlarm() nel WearManager.java
  static List<int> getHeartRateAlarm() {
    return buildOfficialCommand(0x5B, [0]);
  }

  // === COMANDI SENSORI 3D ===

  /// Imposta frequenza 3D (0x74 = 116 nel SDK)
  /// Equivalente a set3DFrequency(int frequency) nel WearManager.java
  /// frequency: 0-4
  static List<int> set3DFrequency(int frequency) {
    return buildOfficialCommand(0x74, [0, 11, frequency & 0xFF]);
  }

  /// Richiede frequenza 3D (0x75 = 117 nel SDK)
  /// Equivalente a get3DFrequency() nel WearManager.java
  static List<int> get3DFrequency() {
    return buildOfficialCommand(0x75, [0, 11]);
  }

  /// Abilita/disabilita 3D (0x74 = 116 nel SDK)
  /// Equivalente a set3DEnabled(boolean enabled) nel WearManager.java
  static List<int> set3DEnabled(bool enabled) {
    return buildOfficialCommand(0x74, [0, 12, enabled ? 1 : 0]);
  }

  /// Richiede stato 3D (0x75 = 117 nel SDK)
  /// Equivalente a get3DStatus() nel WearManager.java
  static List<int> get3DStatus() {
    return buildOfficialCommand(0x75, [0, 12]);
  }

  /// Richiede storico 3D (0x77 = 119 nel SDK)
  /// Equivalente a getHistoryOf3D() nel WearManager.java
  static List<int> getHistoryOf3D() {
    return buildOfficialCommand(0x77, [0]);
  }

  // === COMANDI SENSORI 6D ===

  /// Richiede frequenza 6D (0x61 = 97 nel SDK)
  /// Equivalente a get6DFrequency() nel WearManager.java
  static List<int> get6DFrequency() {
    return buildOfficialCommand(0x61, [0]);
  }

  /// Imposta frequenza 6D (0x62 = 98 nel SDK)
  /// Equivalente a set6DFrequency(int frequency) nel WearManager.java
  /// frequency: 0-3
  static List<int> set6DFrequency(int frequency) {
    return buildOfficialCommand(0x62, [frequency & 0xFF]);
  }

  // === COMANDI VARI ===

  /// Richiede interval steps (0x40 = 64 nel SDK)
  /// Equivalente a getIntervalSteps() nel WearManager.java
  static List<int> getIntervalSteps() {
    return buildOfficialCommand(0x40, [0]);
  }

  /// Richiede single tap records (0x42 = 66 nel SDK)
  /// Equivalente a getSingleTapRecords() nel WearManager.java
  static List<int> getSingleTapRecords() {
    return buildOfficialCommand(0x42, [0]);
  }

  /// Attiva modalità DFU (0x27 = 39 nel SDK)
  /// Equivalente a dfuMode() nel WearManager.java
  static List<int> dfuMode() {
    return [0xFF, 4, 0x27, 0]; // Comando special senza checksum standard
  }

  // === UTILITY ===

  /// Converte comando in stringa hex per debug
  static String commandToHexString(List<int> command) {
    return command.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ');
  }

  /// Verifica se un comando è valido
  static bool isValidCommand(List<int> command) {
    if (command.length < 4) return false;
    if (command[0] != 0xFF) return false;
    if (command[1] != command.length) return false;
    
    // Verifica checksum
    List<int> dataForChecksum = command.sublist(0, command.length - 1);
    int expectedChecksum = calculateChecksum(dataForChecksum);
    return command.last == expectedChecksum;
  }
}
