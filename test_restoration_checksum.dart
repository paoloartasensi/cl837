void main() {
  // Test comando Restoration secondo SDK iOS
  List<int> frame = [0xFF, 5, 0xF3, 0x00];
  
  int sum = frame.reduce((a, b) => a + b);
  int checksum = ((-sum) & 0xFF) ^ 0x3A;
  
  frame.add(checksum);
  
  print('Frame: ${frame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0').toUpperCase()}').join(' ')}');
  print('Sum: 0x${sum.toRadixString(16).toUpperCase()}');
  print('Checksum: 0x${checksum.toRadixString(16).padLeft(2, '0').toUpperCase()}');
  print('');
  print('✅ Final Restoration command: ${frame.map((b) => b.toRadixString(16).padLeft(2, '0')).join('')}');
  
  // Verifica anche shutdown per confronto
  print('');
  print('=== Comparison with Shutdown ===');
  List<int> shutdownFrame = [0xFF, 4, 0xF1];
  int shutdownSum = shutdownFrame.reduce((a, b) => a + b);
  int shutdownChecksum = ((-shutdownSum) & 0xFF) ^ 0x3A;
  shutdownFrame.add(shutdownChecksum);
  
  print('Shutdown command: ${shutdownFrame.map((b) => '0x${b.toRadixString(16).padLeft(2, '0').toUpperCase()}').join(' ')}');
}
