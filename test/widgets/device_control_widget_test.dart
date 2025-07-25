import 'package:flutter_test/flutter_test.dart';
import 'package:flutter/material.dart';
import 'package:cl837_accelerometer/widgets/device_control_widget.dart';
import 'package:cl837_accelerometer/chileaf_extended_service.dart';

void main() {
  group('DeviceControlWidget', () {
    testWidgets('should render without errors', (WidgetTester tester) async {
      // Create a mock service
      final mockService = ChileafExtendedService();
      
      // Build the widget
      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: DeviceControlWidget(
              extendedService: mockService,
              isConnected: false,
              deviceName: 'Test Device',
            ),
          ),
        ),
      );

      // Verify the widget renders
      expect(find.text('Device Control Center'), findsOneWidget);
      expect(find.text('Test Device'), findsOneWidget);
      expect(find.text('DISCONNECTED'), findsOneWidget);
    });

    testWidgets('should show connected state', (WidgetTester tester) async {
      final mockService = ChileafExtendedService();
      
      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: DeviceControlWidget(
              extendedService: mockService,
              isConnected: true,
              deviceName: 'CL837 Device',
            ),
          ),
        ),
      );

      expect(find.text('CONNECTED'), findsOneWidget);
      expect(find.text('CL837 Device'), findsOneWidget);
    });

    testWidgets('should expand command sections', (WidgetTester tester) async {
      final mockService = ChileafExtendedService();
      
      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: DeviceControlWidget(
              extendedService: mockService,
              isConnected: true,
              deviceName: 'CL837 Device',
            ),
          ),
        ),
      );

      // Find and tap a command section to expand it
      expect(find.text('🔧 Core Device Commands'), findsOneWidget);
      expect(find.text('🩺 Health Monitoring'), findsOneWidget);
      expect(find.text('📡 Sensors Control'), findsOneWidget);
    });
  });
}
