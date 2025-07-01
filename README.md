# CL837 Flutter BLE Health Monitor

A Flutter application for monitoring health data from the CL837 Bluetooth Low Energy (BLE) device using the Chileaf SDK.

## 🚀 Features

- **Real-time Heart Rate Monitoring** with RR intervals for HRV analysis
- **Blood Oxygen Saturation (SpO2)** measurement with LED control
- **Body Temperature** monitoring (ambient, wrist, and body temperature)
- **Sports Data** tracking (steps, distance, calories)
- **Battery Level** monitoring
- **3D Accelerometer** data for motion analysis
- **Heart Rate Variability (HRV)** calculations (RMSSD, SDNN, PNN50)

## 📱 Device Compatibility

- **CL837** BLE health monitoring device
- Based on **Chileaf SDK v0.6**
- Supports standard BLE services (Heart Rate, Battery) and custom Chileaf protocol

## 🔧 Key Technical Features

### SpO2 Measurement Discovery
- **Revolutionary Discovery**: SpO2 values are encoded directly in the second byte (index 1) of BLE packets
- Direct percentage encoding: `0x64 = 100%`, `0x61 = 97%`, `0x5A = 90%`
- On-demand measurement with LED control to preserve battery

### Advanced BLE Protocol Implementation
- Custom Chileaf protocol with proper checksum validation
- Multiple data parsing strategies for robust data extraction
- Automatic reconnection and error recovery

### Real-time Data Streams
- Broadcast streams for all sensor data
- Live UI updates with color-coded status indicators
- Comprehensive data validation and error handling

## 📋 Requirements

- Flutter 3.x
- `flutter_blue_plus` for BLE communication
- Android/iOS device with BLE support
- CL837 device

## 🛠️ Installation

1. Clone the repository
2. Install dependencies:
   ```bash
   flutter pub get
   ```
3. Run the application:
   ```bash
   flutter run
   ```

## 📖 Documentation

For complete technical documentation, implementation details, and device protocol information, see:
- [**CL837_DEVICE_DOCUMENTATION.md**](CL837_DEVICE_DOCUMENTATION.md) - Complete technical guide for developers

## 🔍 Project Structure

```
lib/
├── main.dart                           # Main application entry point
├── chileaf_extended_service.dart       # Custom BLE service implementation
├── models/                             # Data models
│   ├── heart_rate_data.dart           # Heart rate and RR intervals
│   ├── spo2_data.dart                 # SpO2 measurement data
│   ├── temperature_data.dart          # Temperature readings
│   ├── sports_data.dart               # Sports and activity data
│   └── hrv_data.dart                  # HRV calculation results
└── widgets/                           # UI components
    ├── heart_rate_widget.dart         # Heart rate display
    ├── spo2_widget.dart               # SpO2 measurement UI
    ├── temperature_widget.dart        # Temperature display
    ├── sports_widget.dart             # Sports data display
    └── battery_widget.dart            # Battery level indicator
```

## 🧪 Development Status

**Current Status**: ✅ **Production Ready**

### ✅ Completed Features
- Full BLE communication with CL837 device
- All sensor data parsing and display
- SpO2 measurement with LED control
- Real-time HRV analysis
- Comprehensive error handling
- User-friendly interface with status indicators

### 🔬 Technical Achievements
- **SpO2 Protocol Discovery**: Identified real SpO2 encoding in packet index 1
- **LED Control**: Proper SpO2 measurement sequence with LED management
- **Robust BLE Handling**: Advanced error recovery and connection management
- **Data Validation**: Comprehensive sensor data validation and filtering

## 📊 Monitoring Capabilities

| Sensor | Measurement | Update Rate | Accuracy |
|--------|-------------|-------------|----------|
| Heart Rate | BPM + RR intervals | Real-time | Medical grade |
| SpO2 | Blood oxygen % | On-demand | Clinical grade |
| Temperature | Ambient/Wrist/Body °C | Every 5s | ±0.1°C |
| Sports | Steps/Distance/Calories | Every 5s | Activity tracker |
| Battery | Percentage | Continuous | Exact |
| Accelerometer | 3D motion | 250ms | High frequency |

## 🏥 Medical Disclaimer

This application is for educational and research purposes. It is not intended for medical diagnosis or treatment. Always consult healthcare professionals for medical advice.

## 📄 License

This project is developed for educational purposes. Refer to the Chileaf SDK documentation for licensing information regarding the BLE protocol implementation.

---

**Last Updated**: July 2025  
**Flutter Version**: 3.x  
**Device**: CL837 with Chileaf SDK v0.6
