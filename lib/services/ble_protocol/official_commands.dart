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

  /// Calcola checksum (implementazione da verificare - nel SDK usa checkSum())
  static int calculateChecksum(List<int> data) {
    int sum = 0;
    for (int byte in data) {
      sum += byte;
    }
    return (~sum + 1) & 0xFF; // Two's complement
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

  /// Richiede storico sleep (0x05 nel SDK)
  /// Equivalente a getHistoryOfSleep() nel WearManager.java
  static List<int> getHistoryOfSleep() {
    return buildOfficialCommand(0x05, [2]);
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
