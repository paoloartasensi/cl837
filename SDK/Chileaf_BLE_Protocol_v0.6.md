# Chileaf BLE Protocol (V0.6)

**Prepared by:** Rickon  
**Issued date:** 2022-6-8  
**Revision:** 0.6  

---

## Table of Contents

1. [Definition of Bluetooth UUID](#1-definition-of-bluetooth-uuid)  
2. [Definition of Data Format](#2-definition-of-data-format)  
3. [Definition of Command](#3-definition-of-command)  

---

## 1. Definition of Bluetooth UUID

### 1.1 Bluetooth Broadcast Name

Format: `[Model name]-xxxxxxx`, where `xxxxxxx` is a 7-bit ID code

### 1.2 Bluetooth Broadcast Manufacturer Data

| Field | Description |
|-------|-------------|
| Byte 0 | Reserve |
| Byte 1 | Battery level (0–100%) |
| Byte 2 | Reserve |
| Byte 3 | Heart rate data |

### 1.3 Service and Characteristics

#### Custom Service UUID

`aae28f00-71b5-42a1-8c3c-f9cf6ac969d0`

| Type | UUID | Description | Attribute |
|------|------|-------------|-----------|
| Service | 8f00 | - | - |
| TX Characteristics | 8f01 | Read data from device | NOTIFY |
| RX Characteristics | 8f02 | Write data to device | WRITE |

#### Heart Rate Service

| Type | UUID | Description | Attribute |
|------|------|-------------|-----------|
| Service | 180d | Heart rate service | - |
| Characteristic | 2a37 | Heart rate data | NOTIFY |
| Characteristic | 2a38 | Wear position | READ |

#### Device Information Service

| Type | UUID | Description | Attribute |
|------|------|-------------|-----------|
| Service | 180a | DEVICE | - |
| Characteristic | 2a29 | Name of manufacturer | READ |
| Characteristic | 2a24 | Model name | READ |
| Characteristic | 2a25 | Serial number | READ |
| Characteristic | 2a27 | Hardware version | READ |
| Characteristic | 2a26 | Firmware version | READ |
| Characteristic | 2a28 | Software version | READ |

#### Battery Service

| Type | UUID | Description | Attribute |
|------|------|-------------|-----------|
| Service | 180f | DEVICE | - |
| Characteristic | 2a19 | Battery | READ, NOTIFY |

---

## 2. Definition of Data Format

### 2.1 Data Format

| Field | Length | Description |
|-------|--------|-------------|
| Head | 1 byte | 0xFF |
| Length | 1 byte | N + 4 |
| Command | 1 byte | 0x** |
| Data | N bytes | -- |
| Checksum | 1 byte | see method |

### 2.2 Frame Field Description

Checksum = XOR(0x3A, -sum(Head to Data))

```java
public byte calcChecksum(byte[] dat) {
    int temp = 0;
    for (int i = 0; i < dat.length - 1; i++) {
        temp += dat[i];
    }
    temp &= 0xFF;
    temp = (0 - temp) & 0xFF;
    temp ^= 0x3A;
    return (byte) (temp & 0xFF);
}
```

---

## 3. Definition of Command

### 3.1 System Commands

#### 0x01 Get function list (reserved)  
#### 0x03 Get user info and status  
- Includes ECG status, charging status, battery %, age, gender, weight, height, phone number

#### 0x04 Configure user info  
- Same fields as above

#### 0x08 Set UTC Time (with time zone)

---

### 3.2 Heart Rate and Step Commands

#### 0x15 Realtime sports data  
- Step count, distance (cm), calories (0.1 kcal units)

#### 0x16 Get 7 days exercise history  
- UTC + steps + calories (for each day)

#### 0x21 HR history list  
- List of UTC timestamps (0xffffffff = no data)

#### 0x22 HR history data  
- Uses UTC from 0x21; includes heart rate + activity index  
- 0x23 signals end

#### 0x0C Acceleration 3D raw data (optional)  
- Sent every 250ms, format: Xl, Xh, Yl, Yh, Zl, Zh, etc.

#### 0x37 SPO2 Mode  
- Modes: enter/exit/inquire  
- Returns SPO2 %, posture, signal quality, wear status

#### 0x38 Temperature  
- Ambient, wrist, and body temperature (×10°C)

---

### 3.3 Rope Skipping

#### 0x40 Rope status  
- Mode: Free, Counter, Timer  
- Contains stats: time, jumps, calorie, day total

#### 0x41 Realtime rope notify  
- Mode, jumps, time, calorie, countdown, restart flag

#### 0x42 Set Mode  
- 0: Free, 1: Counter, 2: Timer

#### 0x45 Clear rope data

---

*End of Protocol v0.6*
