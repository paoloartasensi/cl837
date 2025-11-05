/// Test UTC conversion for sleep timestamp
/// Verifica la conversione corretta UTC → Local time

void main() {
  // Timestamp dal CSV: 2025-11-04 14:30:41
  // Supponiamo che questo sia il valore UTC dal device
  
  // Esempio 1: Timestamp UTC in secondi (come dal device)
  int utcSeconds = 1730731841; // 2024-11-04 14:30:41 UTC
  
  print('═══════════════════════════════════════');
  print('UTC TIMESTAMP CONVERSION TEST');
  print('═══════════════════════════════════════');
  print('');
  print('Device sends: $utcSeconds (UTC seconds)');
  print('');
  
  // ❌ SBAGLIATO: Interpreta come local
  DateTime wrongTimestamp = DateTime.fromMillisecondsSinceEpoch(utcSeconds * 1000);
  print('❌ WRONG (as local):');
  print('   DateTime.fromMillisecondsSinceEpoch($utcSeconds * 1000)');
  print('   = $wrongTimestamp');
  print('');
  
  // ✅ CORRETTO: Converti UTC → Local
  DateTime correctTimestamp = DateTime.fromMillisecondsSinceEpoch(
    utcSeconds * 1000,
    isUtc: true
  ).toLocal();
  print('✅ CORRECT (UTC → Local):');
  print('   DateTime.fromMillisecondsSinceEpoch($utcSeconds * 1000, isUtc: true).toLocal()');
  print('   = $correctTimestamp');
  print('');
  
  // Differenza
  Duration diff = wrongTimestamp.difference(correctTimestamp);
  print('⚠️  DIFFERENCE: ${diff.inHours}h ${diff.inMinutes.abs() % 60}min');
  print('   (This is your timezone offset!)');
  print('');
  
  // Verifica timezone
  DateTime now = DateTime.now();
  DateTime nowUtc = now.toUtc();
  Duration offset = now.difference(nowUtc);
  print('🌍 Your timezone: UTC${offset.isNegative ? '' : '+'}${offset.inHours}');
  print('');
  
  // Esempio reale dal log
  print('═══════════════════════════════════════');
  print('EXAMPLE FROM LOGS:');
  print('═══════════════════════════════════════');
  print('');
  
  // Se vedi nel CSV "14:30:41" ma hai dormito alle "22:30"
  // Il problema è la conversione!
  int realSleepUtc = 1730750400; // 2024-11-04 21:00:00 UTC (= 22:00 CET)
  
  DateTime wrongTime = DateTime.fromMillisecondsSinceEpoch(realSleepUtc * 1000);
  DateTime correctTime = DateTime.fromMillisecondsSinceEpoch(
    realSleepUtc * 1000,
    isUtc: true
  ).toLocal();
  
  print('You slept at: 22:00 local time');
  print('Device saved: ${realSleepUtc} (UTC)');
  print('');
  print('Without conversion: ${wrongTime} ❌');
  print('With conversion:    ${correctTime} ✅');
  print('');
  print('═══════════════════════════════════════');
}
