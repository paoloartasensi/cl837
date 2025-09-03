import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'screens/grok_hr_screen.dart';

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
            title: 'CL837 GROK HR Test',
            theme: ThemeData(
                primarySwatch: Colors.blue,
                visualDensity: VisualDensity.adaptivePlatformDensity,
            ),
            home: const GrokHrScreen(),
        );
    }
}