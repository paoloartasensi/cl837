import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'screens/unified_home_screen.dart';

void main() async {
    WidgetsFlutterBinding.ensureInitialized();
    
    // Suppress BLE log spam - only show critical errors
    FlutterBluePlus.setLogLevel(LogLevel.none, color: false);
    runApp(const MyApp());
}

class MyApp extends StatelessWidget {
    const MyApp({super.key});

    @override
    Widget build(BuildContext context) {
        return MaterialApp(
            debugShowCheckedModeBanner: false,
            title: 'CL837 Health Hub',
            theme: ThemeData(
                primarySwatch: Colors.deepPurple,
                visualDensity: VisualDensity.adaptivePlatformDensity,
            ),
            home: const UnifiedHomeScreen(),
        );
    }
}