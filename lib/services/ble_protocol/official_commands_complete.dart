/// Implementazione dei comandi ufficiali basati sul SDK Android Chileaf
/// Basato sull'analisi di WearManager.java v3.0.4 + XFITNESS2 App
/// 
/// FONTI REVERSE ENGINEERING:
/// - CL831SE_Android_SDK_V3.0.4 (SDK ufficiale)
/// - XFITNESS2 (App ufficiale Chileaf decompilata)
/// 
/// Tutti i comandi sono verificati e confermati da entrambe le fonti

/// Classe contenente tutti i comandi ufficiali del protocollo Chileaf
/// Estratti dal reverse engineering degli SDK ufficiali Android
class OfficialChileafCommands {
  
  // ===== PROTOCOL CONSTANTS =====
  static const int _frameStart = 0xFF;
  static const int _baseLength = 4;
  
  // ===== CORE DEVICE COMMANDS =====
  
  /// Comando reset/ripristino ufficiale (0xF3)
  /// Equivalente al metodo restoration() del SDK Android
  static List<int> deviceReset() {
    return _buildCommand(0xF3, [0]);
  }
  
  /// Comando shutdown dispositivo (0xF1) 
  /// Equivalente al metodo shutdown() del SDK Android
  static List<int> deviceShutdown() {
    return _buildCommand(0xF1, [0]);
  }
  
  /// Imposta timestamp UTC (0x08)
  /// Equivalente al metodo setUTCTime(long stamp) del SDK Android
  static List<int> setUTCTime(int utcTimestamp) {
    List<int> utcBytes = _utc2Bytes(utcTimestamp);
    return _buildCommand(0x08, utcBytes);
  }
  
  /// Disabilita Bluetooth (0x3F)
  /// Equivalente al metodo setBluetoothDisabled() del SDK Android
  static List<int> setBluetoothDisabled() {
    return _buildCommand(0x3F, [2]);
  }
  
  // ===== SPO2 COMMANDS =====
  
  /// Controllo SpO2 (0x37) - COMANDO UFFICIALE CONFERMATO
  /// Equivalente al metodo setBloodOxygen(int mode) del SDK Android
  /// mode: 0 = stop, 1 = start measurement
  static List<int> setBloodOxygen(int mode) {
    return _buildCommand(0x37, [mode, 0]);
  }
  
  // ===== USER INFO COMMANDS =====
  
  /// Richiede informazioni utente (0x03)
  /// Equivalente al metodo getUserInfo() del SDK Android
  static List<int> getUserInfo() {
    return _buildCommand(0x03, [0]);
  }
  
  /// Imposta informazioni utente (0x04)
  /// Equivalente al metodo setUserInfo(...) del SDK Android
  static List<int> setUserInfo(int age, int sex, int weight, int height, int userId) {
    List<int> userBytes = [
      age & 0xFF,
      sex & 0xFF, 
      weight & 0xFF,
      height & 0xFF,
      (userId >> 32) & 0xFF,
      (userId >> 24) & 0xFF,
      (userId >> 16) & 0xFF,
      (userId >> 8) & 0xFF,
      userId & 0xFF
    ];
    return _buildCommand(0x04, userBytes);
  }
  
  // ===== HISTORICAL DATA COMMANDS =====
  
  /// Storia esercizi (0x16) - COMANDO UFFICIALE CONFERMATO
  /// Equivalente al metodo getHistoryOfSport() del SDK Android
  static List<int> getHistoryOfSport() {
    return _buildCommand(0x16, [0]);
  }
  
  /// Lista record HR (0x21)
  /// Equivalente al metodo getHistoryOfHRRecord() del SDK Android
  static List<int> getHistoryOfHRRecord() {
    return _buildCommand(0x21, [0]);
  }
  
  /// Dati HR dettagliati (0x22)
  /// Equivalente al metodo getHistoryOfHRData(long stamp) del SDK Android
  static List<int> getHistoryOfHRData(int utcTimestamp) {
    List<int> params = [1]; // prefix
    params.addAll(_utc2Bytes(utcTimestamp));
    return _buildCommand(0x22, params);
  }
  
  /// Lista record RR (0x24)
  /// Equivalente al metodo getHistoryOfRRRecord() del SDK Android
  static List<int> getHistoryOfRRRecord() {
    return _buildCommand(0x24, []);
  }
  
  /// Dati RR dettagliati (0x25)
  /// Equivalente al metodo getHistoryOfRRData(long stamp) del SDK Android
  static List<int> getHistoryOfRRData(int utcTimestamp) {
    List<int> params = [1]; // prefix
    params.addAll(_utc2Bytes(utcTimestamp));
    return _buildCommand(0x25, params);
  }
  
  /// Passi intervallari (0x40)
  /// Equivalente al metodo getIntervalSteps() del SDK Android
  static List<int> getIntervalSteps() {
    return _buildCommand(0x40, [0]);
  }
  
  /// Record singoli tap (0x42)
  /// Equivalente al metodo getSingleTapRecords() del SDK Android
  static List<int> getSingleTapRecords() {
    return _buildCommand(0x42, [0]);
  }
  
  /// Storia del sonno (0x05)
  /// Equivalente al metodo getHistoryOfSleep() del SDK Android
  static List<int> getHistoryOfSleep() {
    return _buildCommand(0x05, [2]);
  }
  
  /// Record singolo per timestamp (0x49)
  /// Equivalente al metodo getHistoryOfSingleRecord(long stamp) del SDK Android
  static List<int> getHistoryOfSingleRecord(int utcTimestamp) {
    return _buildCommand(0x49, _utc2Bytes(utcTimestamp));
  }
  
  // ===== HEART RATE COMMANDS =====
  
  /// Status HR - lettura (0x46)
  /// Equivalente al metodo getHeartRateStatus() del SDK Android
  static List<int> getHeartRateStatus() {
    return _buildCommand(0x46, [0]);
  }
  
  /// Status HR - impostazione (0x46)
  /// Equivalente al metodo setHeartRateStatus(int min, int max, int goal) del SDK Android
  static List<int> setHeartRateStatus(int min, int max, int goal) {
    return _buildCommand(0x46, [1, min & 0xFF, max & 0xFF, goal & 0xFF]);
  }
  
  /// HR massima - impostazione (0x74)
  /// Equivalente al metodo setHeartRateMax(int max) del SDK Android
  static List<int> setHeartRateMax(int max) {
    return _buildCommand(0x74, [0, 6, max & 0xFF]);
  }
  
  /// HR massima - lettura (0x75)
  /// Equivalente al metodo getHeartRateMax() del SDK Android
  static List<int> getHeartRateMax() {
    return _buildCommand(0x75, [0, 6]);
  }
  
  /// Allarme HR - impostazione (0x57)
  /// Equivalente al metodo setHeartRateAlarm(boolean alarm) del SDK Android
  static List<int> setHeartRateAlarm(bool enabled) {
    return _buildCommand(0x57, [enabled ? 1 : 0]);
  }
  
  /// Allarme HR - lettura (0x5B)
  /// Equivalente al metodo getHeartRateAlarm() del SDK Android
  static List<int> getHeartRateAlarm() {
    return _buildCommand(0x5B, [0]);
  }
  
  // ===== 3D SENSOR COMMANDS =====
  
  /// Frequenza 3D - impostazione (0x74)
  /// Equivalente al metodo set3DFrequency(int frequency) del SDK Android
  static List<int> set3DFrequency(int frequency) {
    return _buildCommand(0x74, [0, 11, frequency & 0xFF]);
  }
  
  /// Frequenza 3D - lettura (0x75)
  /// Equivalente al metodo get3DFrequency() del SDK Android
  static List<int> get3DFrequency() {
    return _buildCommand(0x75, [0, 11]);
  }
  
  /// Status 3D - abilitazione (0x74)
  /// Equivalente al metodo set3DEnabled(boolean enabled) del SDK Android
  static List<int> set3DEnabled(bool enabled) {
    return _buildCommand(0x74, [0, 12, enabled ? 1 : 0]);
  }
  
  /// Status 3D - lettura (0x75)
  /// Equivalente al metodo get3DStatus() del SDK Android
  static List<int> get3DStatus() {
    return _buildCommand(0x75, [0, 12]);
  }
  
  /// Storia 3D (0x77)
  /// Equivalente al metodo getHistoryOf3D() del SDK Android
  static List<int> getHistoryOf3D() {
    return _buildCommand(0x77, [0]);
  }
  
  // ===== 6D SENSOR COMMANDS =====
  
  /// Frequenza 6D - lettura (0x61)
  /// Equivalente al metodo get6DFrequency() del SDK Android
  static List<int> get6DFrequency() {
    return _buildCommand(0x61, [0]);
  }
  
  /// Frequenza 6D - impostazione (0x62)
  /// Equivalente al metodo set6DFrequency(int frequency) del SDK Android
  static List<int> set6DFrequency(int frequency) {
    return _buildCommand(0x62, [frequency & 0xFF]);
  }
  
  // ===== DFU COMMANDS =====
  
  /// Modalità DFU (0x27)
  /// Equivalente al metodo dfuMode() del SDK Android
  static List<int> dfuMode() {
    return _buildCommand(0x27, [0]);
  }
  
  // ===== UTILITY METHODS =====
  
  /// Costruisce un comando nel formato ufficiale Chileaf
  /// [0xFF] [length] [command] [parameters...] [checksum]
  static List<int> _buildCommand(int command, List<int> parameters) {
    List<int> frame = [];
    
    // Start byte
    frame.add(_frameStart);
    
    // Length = base length + parameters length
    int length = _baseLength + parameters.length;
    frame.add(length);
    
    // Command
    frame.add(command & 0xFF);
    
    // Parameters
    frame.addAll(parameters);
    
    // Checksum (simple sum)
    int checksum = _calculateChecksum(frame);
    frame.add(checksum & 0xFF);
    
    return frame;
  }
  
  /// Converte timestamp UTC in array di 4 bytes
  /// Equivalente al metodo utc2Bytes(long stamp) del SDK Android
  static List<int> _utc2Bytes(int stamp) {
    return [
      (stamp >> 24) & 0xFF,
      (stamp >> 16) & 0xFF,
      (stamp >> 8) & 0xFF,
      stamp & 0xFF
    ];
  }
  
  /// Calcola checksum semplice (somma di tutti i bytes)
  static int _calculateChecksum(List<int> data) {
    int sum = 0;
    for (int byte in data) {
      sum += byte;
    }
    return sum & 0xFF;
  }
  
  /// Converte comando in stringa hex per debug
  static String commandToHexString(List<int> command) {
    return command.map((b) => '0x${b.toRadixString(16).padLeft(2, '0')}').join(' ');
  }
  
  /// Verifica se un comando è valido
  static bool isValidCommand(List<int> command) {
    if (command.length < 4) return false;
    if (command[0] != _frameStart) return false;
    
    int expectedLength = command[1];
    int actualLength = command.length;
    
    return actualLength == expectedLength + 1; // +1 per il checksum
  }
}
