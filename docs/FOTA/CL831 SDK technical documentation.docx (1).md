**CL831 SDK technical documentation**

**Version**

| Version | Editor | Date | Type |
| :---: | :---: | :---: | ----- |
| V1.0 | Guo | 2019.11.1 | Init version |
| V1.1 | Guo | 2019.11.15 | Add device data callback length judgment |
| V1.2 | Guo | 2019.11.23 | 1.Get the number of steps in the interval 2.Get historical data for a single key press |
| V1.3 | Guo | 2019.12.13 | Add Device reset |
| V1.4 | Guo | 2020.4.1 | Add setting user information |
| V2.0 | Guo | 2020.4.8 | Compatible with the old and new equipment of CL833 |
| V2.2 | Sylvia | 2022.1.5 | Add command to get UTC and age heart rate alert switch,  set the zone heart rate and  target heart rate |
| V2.3 | Sylvia | 2022.7.11 | Add 3D raw data |
| V2.4 | Sylvia | 2022.7.22 | enter DFU Mode Connect to the specified device and call back |
| V2.5 | Sylvia | 2022.10.14 | Set and get maximum heart rate |
| V2.6 | Sylvia | 2022.10.28 | Get sleep data |
| V2.7 | Sylvia | 2023.4.8 | Set and get 3D frequency |
| V2.8 | Zhang | 2023.9.8 | Add health related data Increase real-time temperature acquisition and blood oxygen acquisition Add a new interface to existing 3D data Optimize the main interface |
| V3.1 | Zhang | 2024.8.13 | 1.Increase the acquisition of step UTC and step history data |
| V3.2 | Zhang | 2024.10.22 | Add the option to select phone files for device upgrade |

list

1. HeartBLEManager Class	[4](#heartblemanager-class)

1.1 A singleton method that initializes Class	[4](#1.1-a-singleton-method-that-initializes-class)

1.2 To invoke its proxy method, follow the proxy	[4](#1.2-to-invoke-its-proxy-method,-follow-the-proxy)

1.3 Determine if your phone's bluetooth is enabled（YES or NO）	[4](#1.3-determine-if-your-phone's-bluetooth-is-enabled（yes-or-no）)

1.4 Start/Stop scan	[5](#1.4-start/stop-scan)

1.5 Disconnect the currently connected device	[5](#1.5-disconnect-the-currently-connected-device)

2. BLEManagerDelegate	[5](#blemanagerdelegate)

2.1 A callback to the CL831 device has been Scanned	[5](#2.1-a-callback-to-the-cl831-device-has-been-scanned)

2.2 The resulting callback to connect the device	[6](#2.2-the-resulting-callback-to-connect-the-device)

2.3 Callbacks to disconnected devices	[6](#2.3-callbacks-to-disconnected-devices)

3. HeartBLEDevice Class	[6](#heartbledevice-class)

3.1 Follow this proxy to invoke its callback method	[6](#3.1-follow-this-proxy-to-invoke-its-callback-method)

3.2 Connect/ Disonnect the Bluetooth	[7](#3.2-connect/-disonnect-the-bluetooth)

3.3 APP sends instruction parameters to the method of device	[7](#3.3-app-sends-instruction-parameters-to-the-method-of-device)

4. BLEDeviceDelegate	[7](#bledevicedelegate)

4.1 The device basic information that is called back	[7](#4.1-the-device-basic-information-that-is-called-back)

4.2 The real-time motion data of the device is called back	[8](#4.2-the-real-time-motion-data-of-the-device-is-called-back)

4.3 Callback of device power information	[8](#4.3-callback-of-device-power-information)

4.4  Callback to broadcast information on a device	[8](#4.4-callback-to-broadcast-information-on-a-device)

4.5 Callback to the 7-day historical data	[8](#4.5-callback-to-the-7-day-historical-data)

4.6 Interval time to get HR heart rate data for each group	[9](#4.6-interval-time-to-get-hr-heart-rate-data-for-each-group)

4.7 Get heart rate data at current UTC time	[9](#4.7-get-heart-rate-data-at-current-utc-time)

4.8 Value callback for heart rate standard service	[9](#4.8-value-callback-for-heart-rate-standard-service)

4.9 Get the number of steps in the interval	[10](#4.9-get-the-number-of-steps-in-the-interval)

4.10 Get historical data for a single key press	[10](#4.10-get-historical-data-for-a-single-key-press)

4.11 Get user information	[10](#4.11-get-user-information)

4.12 Sets a successful callback for the user information	[11](#4.12-sets-a-successful-callback-for-the-user-information)

4.13 APP read interval heart rate and target heart rate	[11](#4.13-app-read-interval-heart-rate-and-target-heart-rate)

4.14 Get UTC and age heart rate alert switch	[11](#4.14-get-utc-and-age-heart-rate-alert-switch)

5\. Instruction parameters sent by APP	[12](#4.16-get-callback-of-max-heart-rate)

5.1  Set UTC time	[12](#5.1-set-utc-time)

5.2  Get 7 days of historical data	[13](#5.2-get-7-days-of-historical-data)

5.3  HR heart rate history data list request	[13](#5.3-hr-heart-rate-history-data-list-request)

5.4  Request for historical heartbeat interval data (HR)	[13](#5.4-request-for-historical-heartbeat-interval-data-\(hr\))

5.5  Get Bluetooth status	[13](#5.5-get-bluetooth-status)

5.6  Get the number of steps in the interval	[14](#5.6-get-the-number-of-steps-in-the-interval)

5.7  Get historical data for a single key press	[14](#5.7-get-historical-data-for-a-single-key-press)

5.8  Device reset	[14](#5.8-device-reset)

5.9  set user information	[14](#5.9-set-user-information)

5.10 App sets interval heart rate and target heart rate	[15](#5.10-app-sets-interval-heart-rate-and-target-heart-rate)

5.11 App obtains interval heart rate and target heart rate	[15](#5.11-app-obtains-interval-heart-rate-and-target-heart-rate)

5.12 Get UTC and age heart rate alert switch	[15](#5.12-get-utc-and-age-heart-rate-alert-switch)

5.13 Set the zone heart rate alarm switch	[16](#5.13-set-the-zone-heart-rate-alarm-switch)

1. # **HeartBLEManager Class**  {#heartblemanager-class}

This class primarily provides a singleton method. Integrated device scanning, stop scanning, disconnect device and other functions, can directly call the corresponding API interface to use.

## **1.1 A singleton method that initializes Class** {#1.1-a-singleton-method-that-initializes-class}

Add the header files “\#import "HeartBLEManager.h"”in the class

HeartBLEManager \*bleManager;  // Executed in the calling interface

bleManager \= \[HeartBLEManager sharedInstance\];

## **1.2 To invoke its proxy method, follow the proxy** {#1.2-to-invoke-its-proxy-method,-follow-the-proxy}

HeartBLEManager \*bleManager; //Executed in the calling interface, Add a delegate ‘BLEManagerDelegate’

 \[bleManager setDelegate:self\];

## **1.3 Determine if your phone's bluetooth is enabled**（**YES or NO**） {#1.3-determine-if-your-phone's-bluetooth-is-enabled（yes-or-no）}

HeartBLEManager \*bleManager; // Executed in the calling interface

Ex: if (\!bleManager.isBLEPoweredOn)

## **1.4 Start/Stop scan** {#1.4-start/stop-scan}

 \[bleManager setDelegate:self\];

\[bleManager StartScanDevice\]; //Start scan

\[bleManager stopScan\]; //Stop scan

## **1.5 Disconnect the currently connected device** {#1.5-disconnect-the-currently-connected-device}

\- (void)scanDeviceName:(NSString \*)name callback:(Callback)result;

name: Enter the bluetooth name in DFU mode \-- the bluetooth name of the CL831 device is CL831U  
callback:Call back the CBPeripheral after the connection is successful;

For the upgrade operation and instructions after the connection, see demo, Libraries used for upgrade：https://github.com/NordicSemiconductor/IOS-DFU-Library

## **1.5 Connect the device in DFU mode with the specified name**

\[bleManager closeAllDevice\];

2. #  **BLEManagerDelegate** {#blemanagerdelegate}

## **2.1 A callback to the CL831 device has been Scanned** {#2.1-a-callback-to-the-cl831-device-has-been-scanned}

\- (void)onDeviceFound:(NSArray \*)deviceArray 

“deviceArray” // CL831 Broadcast Device

     // Information the device contains (HeartBLEDevice \*Device)

      1.Device.DeviceName 

2. Device.UUIDStr  
3. Device.RSSI

## **2.2 The resulting callback to connect the device**  {#2.2-the-resulting-callback-to-connect-the-device}

\- (void)isConnected:(BOOL)isConnected withDevice:(HeartBLEDevice \*)device

“isConnected ”//YES: Connect Successful  or NO: DISConnect 

## **2.3 Callbacks to disconnected devices**  {#2.3-callbacks-to-disconnected-devices}

\- (void)disconnected:(HeartBLEDevice \*)device

3. #  **HeartBLEDevice Class** {#heartbledevice-class}

This class mainly provides device connection, disconnect device connection, set proxy, write data and other interface method calls. It declares device information: device name, UUID, RSSI, and other variables

## **3.1 Follow this proxy to invoke its callback method** {#3.1-follow-this-proxy-to-invoke-its-callback-method}

Add the header files “\#import "HeartBLEManager.h"”in the class,Add a delegate “BLEDeviceDelegate”

HeartBLEDevice \*BLEdeivce;

\[BLEdeivce setDelegate:self\];

## **3.2 Connect/ Disonnect the Bluetooth** {#3.2-connect/-disonnect-the-bluetooth}

HeartBLEDevice \*BLEdeivce;

\[self.BLEdeivce setDelegate:self\];

\[self.BLEdeivce connect\];//connect

\[self.BLEdeivce disconncet\];//disconnect

## **3.3 APP sends instruction parameters to the method of device**  {#3.3-app-sends-instruction-parameters-to-the-method-of-device}

HeartBLEDevice \*BLEdeivce;

\[self.BLEdeivce BLEReadData:\<\#(nonnull NSString \*)\#\>\];

4. #  **BLEDeviceDelegate** {#bledevicedelegate}

## **4.1 The device basic information that is called back**  {#4.1-the-device-basic-information-that-is-called-back}

\-(void)SDKgetInfo:(NSData\*)info withDevice:(HeartBLEDevice \*)Device

**Info**: Data callback for the device

**Device**: device information

1) DeviceName  2)UUIDStr  3)RSSI

## **4.2 The real-time motion data of the device is called back** {#4.2-the-real-time-motion-data-of-the-device-is-called-back}

\-(void)SDKFitRunSParamter:(int)RunPara andFitKM:(float)FitKM andFitCalor :(float)FitCalor

**RunPara**：The number of steps in motion

**FitKM**: Total mileage

**FitCalor**：Calories consumed

## **4.3 Callback of device power information** {#4.3-callback-of-device-power-information}

\- (void)SDKDianciStr:(NSString \*\_Nullable)DianStr

**DianStr**：battery

## **4.4	 Callback to broadcast information on a device** {#4.4-callback-to-broadcast-information-on-a-device}

\- (void)SDKRealData:(NSData \*\_Nullable)data

**Data**：Broadcast information

## **4.5 Callback to the 7-day historical data** {#4.5-callback-to-the-7-day-historical-data}

\- (void)SDKGet7DaysHisParam:(NSMutableArray \*\_Nullable)ParamArr

Annotation：The minimum time is one day, that is, the same day, and the maximum time is 7 days. UTC time is included in the data. The APP should judge the time of 7 days from today to display the historical data meeting the time.

ParamArr of Included parameters：

  1）The total Steps count  2）The total Calorie

## **4.6 Interval time to get HR heart rate data for each group** {#4.6-interval-time-to-get-hr-heart-rate-data-for-each-group}

\- (void)SDKGetHisHRUTCArr:(NSMutableArray \*\_Nullable)UTCArr

Annotation：UTC time, every 4 bytes constitute a UTC time, which means that there will be a set of heart rate data at the beginning of this time point. At most, there will be 256 times. If there is no historical data, a UTC time 0xffffffff represents no historical data

## **4.7 Get heart rate data at current UTC time** {#4.7-get-heart-rate-data-at-current-utc-time}

\-(void)SDKGetHisHRParaArr:(NSMutableArray\*\_Nullable)UTCArr andHisHRArr:(NSMutableArray \*\_Nullable)HRArr

Annotation：APP needs to request the heart rate list first, get the UTC time, the number of all records and use this time to request data, after the command of a byte, the behavior of the request, in order to request data, equipment data will be sent to the APP, packets or no history, equipment life will become 0 x23 end command history data  
The first packet of each set of data identifies the starting point of the set of data as UTC time, and the number of packets is counted starting from the second packet instead of the UTC data bits to be sent

UTCArr: Stores all UTC times in the current UTC listing

HRArr：All the heart rate history data in the current UTC listing is stored

Example:

UTCArr \= @\[@“2019/09/27-11:40:37”,@“2019/09/27-11:40:38”\];

HRArr \= @\[@“120”，@“150”\]；

## **4.8 Value callback for heart rate standard service** {#4.8-value-callback-for-heart-rate-standard-service}

\- (void)SDKFitHeartParamter:(NSString \*\_Nonnull)HeartStr

HeartStr: Real-time heart rate value

## **4.9 Get the number of steps in the interval** {#4.9-get-the-number-of-steps-in-the-interval}

\- (void)SDKGetIntertTimeArr:(NSMutableArray \*\_Nullable)ParaArr

Fx:  

NSMutableArray \*dataArr \= ParaArr\[n\];  
 UTCTime:     HisArr\[0\]  
      Steps:       HisArr\[1\]

## **4.10 Get historical data for a single key press** {#4.10-get-historical-data-for-a-single-key-press}

\- (void)SDKGetSingleUTCArr:(NSMutableArray \*\_Nullable)UTCArrmode

HisArr:Callback total motion data

Fx:  

NSString\*UTCStr \= UTCArr\[n\];

## **4.11 Get user information** {#4.11-get-user-information}

\- (void)SDKUserInfo:(NSString \*\_Nullable)OLdStr andSex:(NSString \*\_Nullable)SexStr andWeight:(NSString \*\_Nullable)WeightStr andHeight:(NSString \*\_Nullable)HeightStr andPhoneNum:(NSString \*\_Nullable)PhNumStr

“OLdStr ”//age  
“SexStr ”//sex  
“WeightStr ”//weight  
“HeightStr ”//height  
“PhNumStr”//phone number

## **4.12 Sets a successful callback for the user information** {#4.12-sets-a-successful-callback-for-the-user-information}

**Describe**：When received data is returned, the setting is successful

- (void)SDKGetUserInfoState:(NSString \*\_Nullable)StateStr

## **4.13 APP read interval heart rate and target heart rate** {#4.13-app-read-interval-heart-rate-and-target-heart-rate}

**Describe**：APP read interval heart rate and target heart rate

\- (void)SDKGetHRGoal:(int)goal max:(int)max min:(int)min;

Fx：

		goal:  target heart rate  
		max: Upper limit of heart rate  
		min: Lower limit of heart rate

## **4.14 Get UTC and age heart rate alert switch** {#4.14-get-utc-and-age-heart-rate-alert-switch}

**Describe**：The callback to get the UTC and age heart rate alarm status，Get UTC and current alarm status

- (void)SDKGetUTC:(long)utc type:(int)alertType;	

	Fx：  
		utc: Time obtained  
		alertType \= 1: Heart Alert by Age。    
                alertType \= 0： Heart Alert by Hi-Lo setting

## **4.15 Get 3D raw data**

- (void)SDKGet3DData:(NSArray \*)rawData;	

	Data example：\[ {"x":546, "y": 213, "z": 90}\] 

		

## **4.16 Get callback of max heart rate** {#4.16-get-callback-of-max-heart-rate}

\- (**void**)SDKGetMaxHeartRate:(int)heartRate;

## **4.17 Get callback of sleep data**

 Description: The result callback obtained after calling the method to get sleep data

/// Get the return of sleep data  
/// @param sleepData  Sleep data Action index description: \> 20 no sleep, \< 20 light sleep, 3 consecutive 0s are deep sleep, it is necessary to count light sleep, deep sleep, wake up and other time periods according to the data  
\-(void)SDKGetSleepData:(NSArray \*)sleepData

说明：sleepData：   
@\[  
@{@"utcTime": @(utcTime), //time  
@"count": @(postCount), //Action index number  
@"sleep":arr}; //Action Index List  
\]

The calculation method can refer to the calculation in the demo

## **4.18 Get callback of 3D frequency**

\- (**void**)SDKGet3DFrequency:(int)frequency;

## **4.19 Get callback for 3D data switch**

\- (**void**)SDKGet3DDateState:(BOOL)isOpen;

## **4.20 The callback obtained by requesting the real-time temperature method**

- (**void**)SDKGetRealTimeTemp:(**float**)ambient wrist:(**float**)wrist body:(**float**)body

Annotation：ambient：Ambient temperature  
		wrist：Wrist temperature  
		body：Body temperature 

## **4.21 Callback obtained by App calling the method of entering blood oxygen mode**

- (**void**)SDKGetBloodOxygen:(**int**)BloodOxygen wristPosture:(**BOOL**)posture redPI:(**int**)PI onWrist:(**BOOL**)onWrist

Annotation：BloodOxygen：Blood oxygen value  
	posture：Is the wrist posture correct （ YES：correct,  NO：wrong）  
	PI： Red light PI value（0：No pulse detected，Less than 8 weak signal，More than 15 good signal）		  
	onWrist：Whether to take off the wrist （YES：Not off the wrist，NO：Off the wrist）

## **4.22 Callback of Health Related Data Obtained by the APP**

///Callback of health data

///@ param Vo2Max maximum oxygen uptake

///@ param breathRate Breath rate

///@ param emotionLevel Mood Level

///@ param stressPercentage Pressure percentage

///@ param stamina fatigue level

\- (void)SDKGetVo2Max:(int)Vo2Max breathRate:(int)breathRate emotionLevel:(int)emotionLevel stressPercent:(int)stressPercent stamina:(int)stamina;

## **4.23 The APP sends instructions to request a list of historical data to obtain the interval time of step counting data for each group**

/// Get step counting history UTC  
\- (void)SDKGetCalculateStepUTC:(NSMutableArray \*\_Nullable)UTCArr;

Comment: UTC time, every 4 bytes form a UTC time, which means there will be a set of step counting data at the beginning of that time point. There will be a maximum of 256 times. If there is no historical data, then UTC time 0xffffff means there is no historical data

## **4.24 Retrieve the step counting data for the UTC time by obtaining the step counting history list time**

\- (void)SDKGetHisStepParaArr:(NSMutableArray \*\_Nullable)UTCArr andHisStepArr:(NSMutableArray \*\_Nullable)StepArr;

Note: The APP needs to first request the pedometer to obtain UTC time, the number of all records, and use this time to request data. After a one byte command, the request behavior will request data, and the device data will be sent to the APP. The data packet or no historical record will be sent, and the device lifespan will become 0 x92. The command history data will end

The first packet of each set of data identifies the starting point of the dataset as UTC time, and calculates the number of packets starting from the second packet, rather than counting from the UTC data bits to be sent

UTCArr: Store all UTC times in the current UTC list

# **5\. Instruction parameters sent by APP**

## **5.1  Set UTC time** {#5.1-set-utc-time}

**Describe**: When the device is successfully connected, the current time needs to be synchronized first  
Example：  
NSString \*NowTimeStr \= \[**self** dateTransformToTimeSp\];*//Get current time*

        **int** NowActualTime \= \[NowTimeStr intValue\] ;*//Gets the current actual time* 

         NSString \*RealTimeStr \= \[**self** ToHex:NowActualTime\];

         NSString \*strLen \= @"ff0808";

         NSString \*strLen1 \= \[strLen stringByAppendingString:RealTimeStr\];

         \[**self**.BLEdeivce BLEReadData:strLen1\];

## **5.2  Get 7 days of historical data** {#5.2-get-7-days-of-historical-data}

**Describe:** Get 7 days of exercise history (ps:1. Steps 2\. Calories)  
Example：  
    			NSString \*StrLen \= @"ff051600";

   	   		\[**self**.BLEdeivce BLEReadData:StrLen\];

## **5.3  HR heart rate history data list request** {#5.3-hr-heart-rate-history-data-list-request}

**Describe:** Gets a list of UTC times stored by the device  
Example:  
    			NSString \*StrLen \= @"ff052100";

   			\[**self**.BLEdeivce BLEReadData:StrLen\];

## **5.4  Request for historical heartbeat interval data (HR)** {#5.4-request-for-historical-heartbeat-interval-data-(hr)}

**Describe:** Obtain the heart rate data for this UTC time by obtaining the HR heart rate history list time  
Example:  
NSString \*StrLen \= @"ff0922015d8df535";

\[**self**.BLEdeivce BLEReadData:StrLen\];

Remarks: “5d8df535”The UTC time in the listing

## **5.5  Get Bluetooth status** {#5.5-get-bluetooth-status}

**Describe:** App sends 0x3f instruction Get Device status

Example:

   			 NSString \*strLen \= @"ff053f00";

   			 \[**self**.BLEdeivce BLEReadData:strLen\];

## **5.6  Get the number of steps in the interval** {#5.6-get-the-number-of-steps-in-the-interval}

**Describe:** App sends 0x40 instruction get Device historical Data

Example:

   			 NSString \*strLen \= @"ff054000";

   			 \[**self**.BLEdeivce BLEReadData:strLen\];

## **5.7  Get historical data for a single key press** {#5.7-get-historical-data-for-a-single-key-press}

**Describe:** App sends 0x42 instruction get Device historical Data

Example:

   			 NSString \*strLen \= @"ff054200";

   			 \[**self**.BLEdeivce BLEReadData:strLen\];

## **5.8  Device reset** {#5.8-device-reset}

**Describe:** App sends 0xf3 instruction get Device historical Data

Example:

   			 NSString \*strLen \= @"ff05f300";

   			 \[**self**.BLEdeivce BLEReadData:strLen\];

## **5.9  set user information** {#5.9-set-user-information}

**Describe:** App sends 0x04 instruction get user information

**Example**:

NSString \*lenStr \= @"ff0d04\+age+sex+weight+height+phone number";

\[self.BLEdeivce BLEReadData:lenStr \];

## **5.10 App sets interval heart rate and target heart rate** {#5.10-app-sets-interval-heart-rate-and-target-heart-rate}

**Describe:** App sends commands to set interval heart rate and target heart rate

		\- (void)setHRGoal:(int)goal max:(int)max min:(int)min;

**Example**:

 	    \[self.BLEdeivce setHRGoal:120 max:180 min:70\];

## **5.11 App obtains interval heart rate and target heart rate** {#5.11-app-obtains-interval-heart-rate-and-target-heart-rate}

	**Describe:** App sends commands to obtain interval heart rate and target heart rate

	\- (void) getHRGoalAndRange；

## **5.12 Get UTC and age heart rate alert switch** {#5.12-get-utc-and-age-heart-rate-alert-switch}

**Describe:** The app sends instructions to get UTC and age heart rate alert switch

**Example**:

\[self.BLEdeivce getUTCAndHeartAlertSwitch\];

## **5.13 Set the zone heart rate alarm switch** {#5.13-set-the-zone-heart-rate-alarm-switch}

**Describe:** The app sends instructions to set the zone heart rate alarm switch

- (void)setHeartAlertSwitch:(BOOL)isOn;

**Annotation**：isOn \= YES: Using age calculation method (when the user does not set the upper and lower limits, the default upper limit is 140, and the default lower limit is 0\)  
isOn \= NO:   Upper and lower limits (when the user does not set the upper and lower limits, the default upper limit is 140, and the default limit is 100\)

**Example**:

\[self.BLEdeivce setHeartAlertSwitch:YES\];

## **5.14 Enter DFU Mode**

 \- (void)enterDFU;

## **5.15 Get and set maxmum heart rate**

Set:   \- (void)setMaxHeartRate:(int)maxHr;  
Get:   \- (void)getMaxHeartRate;

## **5.16 Get sleep data**

\-(void)getSleepData;

## **5.17 Get and set 3D frequency**

Set:    \- (void)set3DFrequency:(int)frequency;  
Get:    \- (void)get3DFrequency;

## **5.18 Set the switch status of 3D raw data**

**Description:** The app sends instructions to open or close 3D raw data

   \- (void)openOrClose3DData:(BOOL)isOpen;  
**Annotation:**  
isOpen \= YES :  Open  
isOpen \= NO:  Close

## **5.19 Request real-time temperature**

**Describe:** App sends instructions to get real-time ambient temperature, wrist temperature, body temperature

Example:

\[self.BLEdeivce requestRealTimeTemperature\];

## **5.20 Enter blood oxygen mode**

**Describe:** Enter blood oxygen mode

Example:

\[self.BLEdeivce enterOxygenMode\];

## **5.21 Exit blood oxygen mode**

**Describe:** Exit blood oxygen mode

Example:

\[self.BLEdeivce quitOxygenMode\];

## **5.22  Step counting history data list request**

Description: Retrieve the UTC time list for step counting stored on the device

example:  
    		 NSString \*StrLen \= @"ff059000";

   		 \[self.BLEdeivce BLEReadData:StrLen\];

## **5.23 Request step counting historical interval data**

描述: 通过获取计步历史记录列表时间来获取该UTC时间的心率数据

Description: Obtain heart rate data for the UTC time by retrieving the step counting history list time  
example:  
NSString \*StrLen \= @"ff0991015d8df535";  
\[self.BLEdeivce BLEReadData:StrLen\];  
Note: UTC time in the "5d8df535" list

