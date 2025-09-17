/*      */ package com.android.chileaf;
/*      */ 
/*      */ import android.annotation.SuppressLint;
/*      */ import android.bluetooth.BluetoothDevice;
/*      */ import android.bluetooth.BluetoothGatt;
/*      */ import android.bluetooth.BluetoothGattCharacteristic;
/*      */ import android.bluetooth.BluetoothGattService;
/*      */ import android.content.Context;
/*      */ import android.os.ParcelUuid;
/*      */ import android.text.TextUtils;
/*      */ import androidx.annotation.IntRange;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import com.android.chileaf.fitness.FitnessManager;
/*      */ import com.android.chileaf.fitness.callback.AccelerometerCallback;
/*      */ import com.android.chileaf.fitness.callback.BloodOxygenCallback;
/*      */ import com.android.chileaf.fitness.callback.BluetoothStatusCallback;
/*      */ import com.android.chileaf.fitness.callback.BodyHealthCallback;
/*      */ import com.android.chileaf.fitness.callback.BodySportCallback;
/*      */ import com.android.chileaf.fitness.callback.BodySportHealthCallback;
/*      */ import com.android.chileaf.fitness.callback.CustomDataReceivedCallback;
/*      */ import com.android.chileaf.fitness.callback.HeartRateAlarmCallback;
/*      */ import com.android.chileaf.fitness.callback.HeartRateMaxCallback;
/*      */ import com.android.chileaf.fitness.callback.HeartRateStatusCallback;
/*      */ import com.android.chileaf.fitness.callback.HistoryOf3DDataCallback;
/*      */ import com.android.chileaf.fitness.callback.HistoryOfHRDataCallback;
/*      */ import com.android.chileaf.fitness.callback.HistoryOfHRRecordCallback;
/*      */ import com.android.chileaf.fitness.callback.HistoryOfRRDataCallback;
/*      */ import com.android.chileaf.fitness.callback.HistoryOfRRRecordCallback;
/*      */ import com.android.chileaf.fitness.callback.HistoryOfSingleRecordCallback;
/*      */ import com.android.chileaf.fitness.callback.HistoryOfSleepCallback;
/*      */ import com.android.chileaf.fitness.callback.HistoryOfSportCallback;
/*      */ import com.android.chileaf.fitness.callback.IntervalStepCallback;
/*      */ import com.android.chileaf.fitness.callback.Sensor3DFrequencyCallback;
/*      */ import com.android.chileaf.fitness.callback.Sensor3DStatusCallback;
/*      */ import com.android.chileaf.fitness.callback.Sensor6DFrequencyCallback;
/*      */ import com.android.chileaf.fitness.callback.Sensor6DRawDataCallback;
/*      */ import com.android.chileaf.fitness.callback.SingleTapRecordCallback;
/*      */ import com.android.chileaf.fitness.callback.TemperatureCallback;
/*      */ import com.android.chileaf.fitness.callback.UserInfoCallback;
/*      */ import com.android.chileaf.fitness.callback.WearManagerCallbacks;
/*      */ import com.android.chileaf.fitness.callback.WearReceivedDataCallback;
/*      */ import com.android.chileaf.fitness.common.FilterScanCallback;
/*      */ import com.android.chileaf.fitness.common.heart.BodySensorLocationDataCallback;
/*      */ import com.android.chileaf.fitness.common.heart.HeartRateMeasurementCallback;
/*      */ import com.android.chileaf.fitness.common.heart.HeartRateMeasurementDataCallback;
/*      */ import com.android.chileaf.fitness.common.parser.BodySensorLocationParser;
/*      */ import com.android.chileaf.fitness.common.parser.HeartRateMeasurementParser;
/*      */ import com.android.chileaf.model.HistoryOf3D;
/*      */ import com.android.chileaf.model.HistoryOfHeartRate;
/*      */ import com.android.chileaf.model.HistoryOfRecord;
/*      */ import com.android.chileaf.model.HistoryOfRespiratoryRate;
/*      */ import com.android.chileaf.model.HistoryOfSport;
/*      */ import com.android.chileaf.model.HistorySleep;
/*      */ import com.android.chileaf.model.IntervalStep;
/*      */ import com.android.chileaf.util.DateUtil;
/*      */ import com.android.chileaf.util.HexUtil;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.UUID;
/*      */ import no.nordicsemi.android.ble.BleManager;
/*      */ import no.nordicsemi.android.ble.BleManagerCallbacks;
/*      */ import no.nordicsemi.android.ble.ReadRequest;
/*      */ import no.nordicsemi.android.ble.ValueChangedCallback;
/*      */ import no.nordicsemi.android.ble.WriteRequest;
/*      */ import no.nordicsemi.android.ble.callback.DataReceivedCallback;
/*      */ import no.nordicsemi.android.ble.data.Data;
/*      */ import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
/*      */ import no.nordicsemi.android.support.v18.scanner.ScanCallback;
/*      */ import no.nordicsemi.android.support.v18.scanner.ScanFilter;
/*      */ import no.nordicsemi.android.support.v18.scanner.ScanResult;
/*      */ import no.nordicsemi.android.support.v18.scanner.ScanSettings;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @SuppressLint({"MissingPermission"})
/*      */ public class WearManager
/*      */   extends FitnessManager<WearManagerCallbacks>
/*      */ {
/*   84 */   private static final UUID HR_SERVICE_UUID = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
/*   85 */   private static final UUID BODY_SENSOR_LOCATION_CHARACTERISTIC_UUID = UUID.fromString("00002A38-0000-1000-8000-00805f9b34fb");
/*   86 */   private static final UUID HEART_RATE_MEASUREMENT_CHARACTERISTIC_UUID = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");
/*      */   
/*   88 */   private static final String[] MODE_NAMES = new String[] { "CL831", "CL880N" };
/*      */   
/*      */   private WearScanCallback mScanCallback;
/*   91 */   private static WearManager managerInstance = null;
/*   92 */   private String[] mFilterNames = null;
/*      */   
/*      */   private BluetoothGattCharacteristic mBodySensorLocationCharacteristic;
/*      */   
/*      */   private BluetoothGattCharacteristic mHeartRateCharacteristic;
/*      */   
/*      */   private UserInfoCallback mUserInfoCallback;
/*      */   private AccelerometerCallback mAccelerometerCallback;
/*      */   private BodySportCallback mBodySportCallback;
/*      */   private BodyHealthCallback mBodyHealthCallback;
/*      */   private HeartRateMeasurementCallback mHeartRateMeasurementCallback;
/*      */   private HistoryOfSportCallback mHistoryOfSportCallback;
/*      */   private HistoryOfHRRecordCallback mHistoryOfHRRecordCallback;
/*      */   private HistoryOfHRDataCallback mHistoryOfHRDataCallback;
/*      */   private HistoryOfRRRecordCallback mHistoryOfRRRecordCallback;
/*      */   private HistoryOfRRDataCallback mHistoryOfRRDataCallback;
/*      */   private IntervalStepCallback mIntervalStepsCallback;
/*      */   private SingleTapRecordCallback mSingleTapRecordCallback;
/*      */   private BluetoothStatusCallback mBluetoothStatusCallback;
/*      */   private CustomDataReceivedCallback mCustomDataReceivedCallback;
/*      */   private HeartRateStatusCallback mHeartRateStatusCallback;
/*      */   private BloodOxygenCallback mBloodOxygenCallback;
/*      */   private TemperatureCallback mTemperatureCallback;
/*      */   private HistoryOfSingleRecordCallback mHistoryOfSingleRecordCallback;
/*      */   private HeartRateAlarmCallback mHeartRateAlarmCallback;
/*      */   private HeartRateMaxCallback mHeartRateMaxCallback;
/*      */   private HistoryOfSleepCallback mHistoryOfSleepCallback;
/*      */   private Sensor3DFrequencyCallback mSensor3DFrequencyCallback;
/*      */   private Sensor3DStatusCallback mSensor3DStatusCallback;
/*      */   private Sensor6DFrequencyCallback mSensor6DFrequencyCallback;
/*      */   private Sensor6DRawDataCallback mSensor6DRawDataCallback;
/*      */   private BodySportHealthCallback mBodySportHealthCallback;
/*      */   private HistoryOf3DDataCallback mHistoryOf3DDataCallback;
/*      */   private final BodySensorLocationDataCallback mBodySensorLocationDataCallback;
/*      */   private final HeartRateMeasurementDataCallback mHeartRateMeasureDataCallback;
/*      */   private final WearReceivedDataCallback mReceivedDataCallback;
/*      */   
/*      */   public static synchronized WearManager getInstance(Context context) {
/*  130 */     if (managerInstance == null) {
/*  131 */       managerInstance = new WearManager(context);
/*      */     }
/*  133 */     return managerInstance;
/*      */   }
/*      */   
/*      */   public WearManager(Context context) {
/*  137 */     super(context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  162 */     this.mBodySensorLocationDataCallback = new BodySensorLocationDataCallback()
/*      */       {
/*      */         public void onBodySensorLocationReceived(@NonNull BluetoothDevice device, int sensorLocation)
/*      */         {
/*  166 */           ((WearManagerCallbacks)WearManager.this.mCallbacks).onBodySensorLocationReceived(device, sensorLocation);
/*      */         }
/*      */ 
/*      */         
/*      */         public void onDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
/*  171 */           WearManager.this.log(3, String.format("%s received", new Object[] { BodySensorLocationParser.parse(data) }));
/*  172 */           super.onDataReceived(device, data);
/*      */         }
/*      */       };
/*      */     
/*  176 */     this.mHeartRateMeasureDataCallback = new HeartRateMeasurementDataCallback()
/*      */       {
/*      */         public void onHeartRateMeasurementReceived(@NonNull BluetoothDevice device, int heartRate, @Nullable Boolean contactDetected, @Nullable Integer energyExpanded, @Nullable List<Integer> rrIntervals)
/*      */         {
/*  180 */           ((WearManagerCallbacks)WearManager.this.mCallbacks).onHeartRateMeasurementReceived(device, heartRate, contactDetected, energyExpanded, rrIntervals);
/*  181 */           if (WearManager.this.mHeartRateMeasurementCallback != null) {
/*  182 */             WearManager.this.mHeartRateMeasurementCallback.onHeartRateMeasurementReceived(device, heartRate, contactDetected, energyExpanded, rrIntervals);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
/*  188 */           WearManager.this.log(3, HeartRateMeasurementParser.parse(data) + " received");
/*  189 */           super.onDataReceived(device, data);
/*      */         }
/*      */       };
/*      */     
/*  193 */     this.mReceivedDataCallback = new WearReceivedDataCallback()
/*      */       {
/*      */         public void onSportReceived(@NonNull BluetoothDevice device, int step, int distance, int calorie)
/*      */         {
/*  197 */           ((WearManagerCallbacks)WearManager.this.mCallbacks).onSportReceived(device, step, distance, calorie);
/*  198 */           if (WearManager.this.mBodySportCallback != null) {
/*  199 */             WearManager.this.mBodySportCallback.onSportReceived(device, step, distance, calorie);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHealthReceived(@NonNull BluetoothDevice device, int vo2Max, int breathRate, int emotionLevel, int stressPercent, int stamina, float tp, float lf, float hf) {
/*  205 */           if (WearManager.this.mBodyHealthCallback != null) {
/*  206 */             WearManager.this.mBodyHealthCallback.onHealthReceived(device, vo2Max, breathRate, emotionLevel, stressPercent, stamina, tp, lf, hf);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onSportHealthReceived(@NonNull BluetoothDevice device, int vo2Max, int breathRate, int emotion, int pressure, int stamina) {
/*  212 */           if (WearManager.this.mBodySportHealthCallback != null) {
/*  213 */             WearManager.this.mBodySportHealthCallback.onSportHealthReceived(device, vo2Max, breathRate, emotion, pressure, stamina);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onBluetoothStatusReceived(@NonNull BluetoothDevice device, boolean enabled) {
/*  219 */           ((WearManagerCallbacks)WearManager.this.mCallbacks).onBluetoothStatusReceived(device, enabled);
/*  220 */           if (WearManager.this.mBluetoothStatusCallback != null) {
/*  221 */             WearManager.this.mBluetoothStatusCallback.onBluetoothStatusReceived(device, enabled);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onUserInfoReceived(@NonNull BluetoothDevice device, int age, int sex, int weight, int height, long userId) {
/*  227 */           if (WearManager.this.mUserInfoCallback != null) {
/*  228 */             WearManager.this.mUserInfoCallback.onUserInfoReceived(device, age, sex, weight, height, userId);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHistoryOfSportReceived(@NonNull BluetoothDevice device, List<HistoryOfSport> sports) {
/*  234 */           if (WearManager.this.mHistoryOfSportCallback != null) {
/*  235 */             WearManager.this.mHistoryOfSportCallback.onHistoryOfSportReceived(device, sports);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHistoryOfHRRecordReceived(@NonNull BluetoothDevice device, List<HistoryOfRecord> records) {
/*  241 */           if (WearManager.this.mHistoryOfHRRecordCallback != null) {
/*  242 */             WearManager.this.mHistoryOfHRRecordCallback.onHistoryOfHRRecordReceived(device, records);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHistoryOfHRDataReceived(@NonNull BluetoothDevice device, List<HistoryOfHeartRate> heartRates) {
/*  248 */           if (WearManager.this.mHistoryOfHRDataCallback != null) {
/*  249 */             WearManager.this.mHistoryOfHRDataCallback.onHistoryOfHRDataReceived(device, heartRates);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHistoryOfRRRecordReceived(@NonNull BluetoothDevice device, List<HistoryOfRecord> records) {
/*  255 */           if (WearManager.this.mHistoryOfRRRecordCallback != null) {
/*  256 */             WearManager.this.mHistoryOfRRRecordCallback.onHistoryOfRRRecordReceived(device, records);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHistoryOfRRDataReceived(@NonNull BluetoothDevice device, List<HistoryOfRespiratoryRate> respiratoryRates) {
/*  262 */           if (WearManager.this.mHistoryOfRRDataCallback != null) {
/*  263 */             WearManager.this.mHistoryOfRRDataCallback.onHistoryOfRRDataReceived(device, respiratoryRates);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onIntervalStepReceived(@NonNull BluetoothDevice device, List<IntervalStep> steps) {
/*  269 */           if (WearManager.this.mIntervalStepsCallback != null) {
/*  270 */             WearManager.this.mIntervalStepsCallback.onIntervalStepReceived(device, steps);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onSingleTapRecordReceived(@NonNull BluetoothDevice device, List<HistoryOfRecord> records) {
/*  276 */           if (WearManager.this.mSingleTapRecordCallback != null) {
/*  277 */             WearManager.this.mSingleTapRecordCallback.onSingleTapRecordReceived(device, records);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHeartRateStatusReceived(@NonNull BluetoothDevice device, int min, int max, int goal) {
/*  283 */           if (WearManager.this.mHeartRateStatusCallback != null) {
/*  284 */             WearManager.this.mHeartRateStatusCallback.onHeartRateStatusReceived(device, min, max, goal);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHistoryOf3DDataReceived(@NonNull BluetoothDevice device, HistoryOf3D history, boolean finish) {
/*  290 */           if (WearManager.this.mHistoryOf3DDataCallback != null) {
/*  291 */             WearManager.this.mHistoryOf3DDataCallback.onHistoryOf3DDataReceived(device, history, finish);
/*      */           }
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void onBloodOxygenReceived(@NonNull BluetoothDevice device, int bSwitch, String value, int gesture, int piValue, int onwrist) {
/*  298 */           if (WearManager.this.mBloodOxygenCallback != null) {
/*  299 */             WearManager.this.mBloodOxygenCallback.onBloodOxygenReceived(device, bSwitch, value, gesture, piValue, onwrist);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onTemperatureReceived(@NonNull BluetoothDevice device, float environment, float wrist, float body) {
/*  305 */           if (WearManager.this.mTemperatureCallback != null) {
/*  306 */             WearManager.this.mTemperatureCallback.onTemperatureReceived(device, environment, wrist, body);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHistorySingleRecordReceived(@NonNull BluetoothDevice device, long stamp, long step, long distance, long calorie) {
/*  312 */           if (WearManager.this.mHistoryOfSingleRecordCallback != null) {
/*  313 */             WearManager.this.mHistoryOfSingleRecordCallback.onHistorySingleRecordReceived(device, stamp, step, distance, calorie);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHeartRateAlarmReceived(@NonNull BluetoothDevice device, long stamp, boolean enabled) {
/*  319 */           if (WearManager.this.mHeartRateAlarmCallback != null) {
/*  320 */             WearManager.this.mHeartRateAlarmCallback.onHeartRateAlarmReceived(device, stamp, enabled);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onAccelerometerReceived(@NonNull BluetoothDevice device, int x, int y, int z) {
/*  326 */           if (WearManager.this.mAccelerometerCallback != null) {
/*  327 */             WearManager.this.mAccelerometerCallback.onAccelerometerReceived(device, x, y, z);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHeartRateMaxReceived(@NonNull BluetoothDevice device, int max) {
/*  333 */           if (WearManager.this.mHeartRateMaxCallback != null) {
/*  334 */             WearManager.this.mHeartRateMaxCallback.onHeartRateMaxReceived(device, max);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onHistoryOfSleepReceived(@NonNull BluetoothDevice device, List<HistorySleep> sleeps) {
/*  340 */           if (WearManager.this.mHistoryOfSleepCallback != null) {
/*  341 */             WearManager.this.mHistoryOfSleepCallback.onHistoryOfSleepReceived(device, sleeps);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onSensor3DFrequencyReceived(@NonNull BluetoothDevice device, int frequency) {
/*  347 */           if (WearManager.this.mSensor3DFrequencyCallback != null) {
/*  348 */             WearManager.this.mSensor3DFrequencyCallback.onSensor3DFrequencyReceived(device, frequency);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onSensor3DStatusReceived(@NonNull BluetoothDevice device, boolean enabled) {
/*  354 */           if (WearManager.this.mSensor3DStatusCallback != null) {
/*  355 */             WearManager.this.mSensor3DStatusCallback.onSensor3DStatusReceived(device, enabled);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onSensor6DFrequencyReceived(@NonNull BluetoothDevice device, int sensor) {
/*  361 */           if (WearManager.this.mSensor6DFrequencyCallback != null) {
/*  362 */             WearManager.this.mSensor6DFrequencyCallback.onSensor6DFrequencyReceived(device, sensor);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void onSensor6DRawDataReceived(@NonNull BluetoothDevice device, long utc, int sequence, int gyroscopeX, int gyroscopeY, int gyroscopeZ, int accelerometerX, int accelerometerY, int accelerometerZ) {
/*  368 */           if (WearManager.this.mSensor6DRawDataCallback != null)
/*  369 */             WearManager.this.mSensor6DRawDataCallback.onSensor6DRawDataReceived(device, utc, sequence, gyroscopeX, gyroscopeY, gyroscopeZ, accelerometerX, accelerometerY, accelerometerZ);  }
/*      */       };
/*      */   } public void checkModel(String modelName, boolean isCL833) { boolean cl833 = checkMode(modelName);
/*      */     this.mReceivedDataCallback.setCL833((cl833 && isCL833));
/*      */     log(3, String.format("Check modelName:%s isCL833:%s", new Object[] { modelName, Boolean.valueOf(isCL833) })); } private boolean checkMode(String name) { for (String device : MODE_NAMES) {
/*      */       if (device.equalsIgnoreCase(name))
/*      */         return true; 
/*      */     } 
/*      */     return false; } @NonNull
/*      */   protected BleManager.BleManagerGattCallback getGattCallback() { return (BleManager.BleManagerGattCallback)new WearManagerGattCallback(); } private final class WearScanCallback extends ScanCallback
/*      */   {
/*  380 */     private WearScanCallback(FilterScanCallback callback) { this.mCallback = callback; }
/*      */     
/*      */     private final FilterScanCallback mCallback;
/*      */     private boolean matchDeviceName(BluetoothDevice device) {
/*  384 */       if (WearManager.this.mFilterNames != null) {
/*  385 */         if (device != null) {
/*  386 */           String name = device.getName();
/*  387 */           if (name != null && !TextUtils.isEmpty(name)) {
/*  388 */             for (String filterName : WearManager.this.mFilterNames) {
/*  389 */               if (name.contains(filterName)) {
/*  390 */                 return true;
/*      */               }
/*      */             } 
/*      */           }
/*      */         } 
/*  395 */         return false;
/*      */       } 
/*  397 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void onScanResult(int callbackType, @NonNull ScanResult result) {
/*  402 */       super.onScanResult(callbackType, result);
/*  403 */       if (this.mCallback != null) {
/*  404 */         this.mCallback.onScanResult(callbackType, result);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void onBatchScanResults(@NonNull List<ScanResult> results) {
/*  410 */       List<ScanResult> scanDevices = new ArrayList<>();
/*  411 */       for (ScanResult result : results) {
/*  412 */         BluetoothDevice device = result.getDevice();
/*  413 */         if (matchDeviceName(device)) {
/*  414 */           scanDevices.add(result);
/*      */         }
/*      */       } 
/*  417 */       if (this.mCallback != null) {
/*  418 */         this.mCallback.onBatchScanResults(results);
/*  419 */         this.mCallback.onFilterScanResults(scanDevices);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void onScanFailed(int errorCode) {
/*  425 */       super.onScanFailed(errorCode);
/*  426 */       if (this.mCallback != null)
/*  427 */         this.mCallback.onScanFailed(errorCode); 
/*      */     }
/*      */   }
/*      */   
/*      */   private final class WearManagerGattCallback
/*      */     extends FitnessManager<WearManagerCallbacks>.FitnessManagerGattCallback
/*      */   {
/*      */     private WearManagerGattCallback() {
/*  435 */       super(WearManager.this);
/*      */     }
/*      */     
/*      */     protected void initialize() {
/*  439 */       super.initialize();
/*  440 */       WearManager.this.readCharacteristic(WearManager.this.mBodySensorLocationCharacteristic)
/*  441 */         .with((DataReceivedCallback)WearManager.this.mBodySensorLocationDataCallback)
/*  442 */         .fail((device, status) -> WearManager.this.log(5, "Body Sensor Location characteristic not found"))
/*  443 */         .enqueue();
/*      */       
/*  445 */       WearManager.this.setNotificationCallback(WearManager.this.mHeartRateCharacteristic)
/*  446 */         .with((DataReceivedCallback)WearManager.this.mHeartRateMeasureDataCallback);
/*  447 */       WearManager.this.enableNotifications(WearManager.this.mHeartRateCharacteristic)
/*  448 */         .enqueue();
/*      */       
/*  450 */       WearManager.this.setNotificationCallback(WearManager.this.mRXCharacteristic)
/*  451 */         .with((DataReceivedCallback)WearManager.this.mReceivedDataCallback);
/*  452 */       WearManager.this.enableNotifications(WearManager.this.mRXCharacteristic)
/*  453 */         .done(device -> WearManager.this.log(3, "Rx notifications enabled"))
/*  454 */         .fail((device, status) -> WearManager.this.log(5, "Rx characteristic not found"))
/*  455 */         .enqueue();
/*  456 */       if (WearManager.this.mCustomRxCharacteristic != null) {
/*  457 */         WearManager.this.setNotificationCallback(WearManager.this.mCustomRxCharacteristic)
/*  458 */           .with((device, data) -> {
/*      */               if (WearManager.this.mCustomDataReceivedCallback != null) {
/*      */                 WearManager.this.mCustomDataReceivedCallback.onDataReceived(device, data.getValue());
/*      */               }
/*      */             });
/*  463 */         WearManager.this.enableNotifications(WearManager.this.mCustomRxCharacteristic)
/*  464 */           .enqueue();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
/*  470 */       super.isRequiredServiceSupported(gatt);
/*  471 */       BluetoothGattService hrService = gatt.getService(WearManager.HR_SERVICE_UUID);
/*  472 */       if (hrService != null) {
/*  473 */         WearManager.this.mHeartRateCharacteristic = hrService.getCharacteristic(WearManager.HEART_RATE_MEASUREMENT_CHARACTERISTIC_UUID);
/*      */       }
/*  475 */       return (WearManager.this.mHeartRateCharacteristic != null);
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean isOptionalServiceSupported(@NonNull BluetoothGatt gatt) {
/*  480 */       super.isOptionalServiceSupported(gatt);
/*  481 */       BluetoothGattService service = gatt.getService(WearManager.HR_SERVICE_UUID);
/*  482 */       if (service != null) {
/*  483 */         WearManager.this.mBodySensorLocationCharacteristic = service.getCharacteristic(WearManager.BODY_SENSOR_LOCATION_CHARACTERISTIC_UUID);
/*      */       }
/*  485 */       return (WearManager.this.mBodySensorLocationCharacteristic != null);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onDeviceReady() {
/*  490 */       super.onDeviceReady();
/*  491 */       WearManager.this.setUTCTime();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onDeviceDisconnected() {
/*  496 */       super.onDeviceDisconnected();
/*  497 */       WearManager.this.mBodySensorLocationCharacteristic = null;
/*  498 */       WearManager.this.mHeartRateCharacteristic = null;
/*  499 */       WearManager.this.mReceivedDataCallback.setCL833(false);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onServicesInvalidated() {
/*  504 */       super.onServicesInvalidated();
/*  505 */       WearManager.this.mBodySensorLocationCharacteristic = null;
/*  506 */       WearManager.this.mHeartRateCharacteristic = null;
/*  507 */       WearManager.this.mReceivedDataCallback.setCL833(false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBodySportCallback(BodySportCallback callback) {
/*  517 */     this.mBodySportCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBodyHealthCallback(BodyHealthCallback callback) {
/*  526 */     this.mBodyHealthCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHeartRateMeasurementCallback(HeartRateMeasurementCallback callback) {
/*  535 */     this.mHeartRateMeasurementCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBluetoothStatusCallback(BluetoothStatusCallback callback) {
/*  544 */     this.mBluetoothStatusCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addUserInfoCallback(UserInfoCallback callback) {
/*  553 */     this.mUserInfoCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHistoryOfSportCallback(HistoryOfSportCallback callback) {
/*  562 */     this.mHistoryOfSportCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHistoryOfHRRecordCallback(HistoryOfHRRecordCallback callback) {
/*  571 */     this.mHistoryOfHRRecordCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHistoryOfHRDataCallback(HistoryOfHRDataCallback callback) {
/*  580 */     this.mHistoryOfHRDataCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHistoryOfRRRecordCallback(HistoryOfRRRecordCallback callback) {
/*  589 */     this.mHistoryOfRRRecordCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHistoryOfRRDataCallback(HistoryOfRRDataCallback callback) {
/*  598 */     this.mHistoryOfRRDataCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addIntervalStepCallback(IntervalStepCallback callback) {
/*  607 */     this.mIntervalStepsCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSingleTapRecordCallback(SingleTapRecordCallback callback) {
/*  616 */     this.mSingleTapRecordCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHistoryOf3DDataCallback(HistoryOf3DDataCallback callback) {
/*  625 */     this.mHistoryOf3DDataCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHeartRateStatusCallback(HeartRateStatusCallback callback) {
/*  634 */     this.mHeartRateStatusCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBloodOxygenCallback(BloodOxygenCallback callback) {
/*  643 */     this.mBloodOxygenCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTemperatureCallback(TemperatureCallback callback) {
/*  652 */     this.mTemperatureCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHistoryOfSingleRecordCallback(HistoryOfSingleRecordCallback callback) {
/*  661 */     this.mHistoryOfSingleRecordCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHeartRateAlarmCallback(HeartRateAlarmCallback callback) {
/*  670 */     this.mHeartRateAlarmCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAccelerometerCallback(AccelerometerCallback callback) {
/*  679 */     this.mAccelerometerCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHeartRateMaxCallback(HeartRateMaxCallback callback) {
/*  688 */     this.mHeartRateMaxCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHistoryOfSleepCallback(HistoryOfSleepCallback callback) {
/*  697 */     this.mHistoryOfSleepCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSensor3DFrequencyCallback(Sensor3DFrequencyCallback callback) {
/*  706 */     this.mSensor3DFrequencyCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSensor3DStatusCallback(Sensor3DStatusCallback callback) {
/*  715 */     this.mSensor3DStatusCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSensor6DFrequencyCallback(Sensor6DFrequencyCallback callback) {
/*  724 */     this.mSensor6DFrequencyCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSensor6DRawDataCallback(Sensor6DRawDataCallback callback) {
/*  733 */     this.mSensor6DRawDataCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBodySportHealthCallback(BodySportHealthCallback callback) {
/*  742 */     this.mBodySportHealthCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFilterNames(String... filterNames) {
/*  751 */     this.mFilterNames = filterNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startScan(FilterScanCallback callback) {
/*  760 */     this.mScanCallback = new WearScanCallback(callback);
/*  761 */     BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  767 */     ScanSettings settings = (new ScanSettings.Builder()).setLegacy(false).setReportDelay(1000L).setUseHardwareBatchingIfSupported(false).setScanMode(2).build();
/*  768 */     List<ScanFilter> filters = new ArrayList<>();
/*  769 */     ParcelUuid uuid = new ParcelUuid(HR_SERVICE_UUID);
/*  770 */     filters.add((new ScanFilter.Builder())
/*  771 */         .setServiceUuid(uuid)
/*  772 */         .build());
/*  773 */     scanner.startScan(filters, settings, this.mScanCallback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stopScan() {
/*  780 */     if (this.mScanCallback != null) {
/*  781 */       BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
/*  782 */       scanner.stopScan(this.mScanCallback);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendCommand(byte cmd, int... values) {
/*      */     byte[] result;
/*  791 */     int unique = 4;
/*      */     
/*  793 */     if (values != null) {
/*  794 */       int len = values.length + 4;
/*  795 */       byte[] header = HexUtil.compose(new int[] { 255, len, cmd });
/*  796 */       byte[] bytes = HexUtil.compose(values);
/*  797 */       result = HexUtil.append(header, bytes);
/*      */     } else {
/*  799 */       result = HexUtil.compose(new int[] { 255, 4, cmd });
/*      */     } 
/*  801 */     byte check = checkSum(result);
/*  802 */     byte[] command = HexUtil.append(result, check);
/*  803 */     writeTxCharacteristic(command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int[] utc2Bytes(long stamp) {
/*  810 */     int[] utcArray = new int[4];
/*  811 */     utcArray[0] = (int)(stamp >> 24L);
/*  812 */     utcArray[1] = (int)(stamp >> 16L);
/*  813 */     utcArray[2] = (int)(stamp >> 8L);
/*  814 */     utcArray[3] = (int)stamp;
/*  815 */     return utcArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUTCTime() {
/*  831 */     setUTCTime(DateUtil.getZoneUTC());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUTCTime(long stamp) {
/*  838 */     sendCommand((byte)8, utc2Bytes(stamp));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdown() {
/*  845 */     sendCommand((byte)-15, new int[] { 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void restoration() {
/*  852 */     sendCommand((byte)-13, new int[] { 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBluetoothDisabled() {
/*  862 */     sendCommand((byte)63, new int[] { 2 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getHistoryOfSport() {
/*  869 */     this.mReceivedDataCallback.clearType(2);
/*  870 */     sendCommand((byte)22, new int[] { 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getHistoryOfHRRecord() {
/*  877 */     this.mReceivedDataCallback.clearType(4);
/*  878 */     sendCommand((byte)33, new int[] { 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getHistoryOfHRData(long stamp) {
/*  887 */     this.mReceivedDataCallback.clearType(6);
/*  888 */     sendCommand((byte)34, HexUtil.append(1, utc2Bytes(stamp)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getHistoryOfRRRecord() {
/*  895 */     this.mReceivedDataCallback.clearType(8);
/*  896 */     sendCommand((byte)36, new int[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getHistoryOfRRData(long stamp) {
/*  905 */     this.mReceivedDataCallback.clearType(16);
/*  906 */     sendCommand((byte)37, HexUtil.append(1, utc2Bytes(stamp)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getIntervalSteps() {
/*  913 */     this.mReceivedDataCallback.clearType(18);
/*  914 */     sendCommand((byte)64, new int[] { 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getSingleTapRecords() {
/*  921 */     this.mReceivedDataCallback.clearType(20);
/*  922 */     sendCommand((byte)66, new int[] { 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getHistoryOfSleep() {
/*  929 */     this.mReceivedDataCallback.clearType(22);
/*  930 */     sendCommand((byte)5, new int[] { 2 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getUserInfo() {
/*  937 */     sendCommand((byte)3, new int[] { 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserInfo(int age, int sex, int weight, int height, long userId) {
/*  950 */     int[] command = new int[9];
/*  951 */     command[0] = (byte)age;
/*  952 */     command[1] = (byte)sex;
/*  953 */     command[2] = (byte)weight;
/*  954 */     command[3] = (byte)height;
/*  955 */     command[4] = (byte)(int)(userId >> 32L & 0xFFL);
/*  956 */     command[5] = (byte)(int)(userId >> 24L & 0xFFL);
/*  957 */     command[6] = (byte)(int)(userId >> 16L & 0xFFL);
/*  958 */     command[7] = (byte)(int)(userId >> 8L & 0xFFL);
/*  959 */     command[8] = (byte)(int)(userId & 0xFFL);
/*  960 */     sendCommand((byte)4, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getHeartRateStatus() {
/*  967 */     sendCommand((byte)70, new int[] { 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHeartRateStatus(int min, int max, int goal) {
/*  978 */     sendCommand((byte)70, new int[] { 1, (byte)min, (byte)max, (byte)goal });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHeartRateMax(int max) {
/*  987 */     sendCommand((byte)116, new int[] { 0, 6, (byte)max });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getHeartRateMax() {
/*  994 */     sendCommand((byte)117, new int[] { 0, 6 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBloodOxygen(int mode) {
/* 1001 */     int[] command = new int[2];
/* 1002 */     command[0] = (byte)mode;
/* 1003 */     sendCommand((byte)55, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHeartRateAlarm(boolean alarm) {
/* 1010 */     sendCommand((byte)87, new int[] { alarm ? 1 : 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getHeartRateAlarm() {
/* 1017 */     sendCommand((byte)91, new int[] { 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getHistoryOfSingleRecord(long stamp) {
/* 1026 */     sendCommand((byte)73, utc2Bytes(stamp));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set3DFrequency(@IntRange(from = 0L, to = 4L) int frequency) {
/* 1039 */     sendCommand((byte)116, new int[] { 0, 11, (byte)frequency });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void get3DFrequency() {
/* 1046 */     sendCommand((byte)117, new int[] { 0, 11 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set3DEnabled(boolean enabled) {
/* 1053 */     sendCommand((byte)116, new int[] { 0, 12, enabled ? 1 : 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void get3DStatus() {
/* 1060 */     sendCommand((byte)117, new int[] { 0, 12 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void get6DFrequency() {
/* 1067 */     sendCommand((byte)97, new int[] { 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set6DFrequency(@IntRange(from = 0L, to = 3L) int frequency) {
/* 1078 */     sendCommand((byte)98, new int[] { frequency });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCustomDataReceivedCallback(CustomDataReceivedCallback customDataReceivedCallback) {
/* 1087 */     this.mCustomDataReceivedCallback = customDataReceivedCallback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getHistoryOf3D() {
/* 1094 */     sendCommand((byte)119, new int[] { 0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String dfuMode() {
/* 1101 */     String address = getDFUAddress();
/* 1102 */     byte[] command = new byte[4];
/* 1103 */     command[0] = -1;
/* 1104 */     command[1] = 4;
/* 1105 */     command[2] = 39;
/* 1106 */     command[3] = checkSum(command);
/* 1107 */     writeTxCharacteristic(command);
/* 1108 */     return address;
/*      */   }
/*      */   
/*      */   private String getDFUAddress() {
/* 1112 */     BluetoothDevice device = getBluetoothDevice();
/* 1113 */     if (device == null) {
/* 1114 */       return null;
/*      */     }
/* 1116 */     String deviceAddress = device.getAddress();
/* 1117 */     String firstBytes = deviceAddress.substring(0, 15);
/* 1118 */     String lastByte = deviceAddress.substring(15);
/* 1119 */     String lastByteIncremented = String.format(Locale.US, "%02X", new Object[] { Integer.valueOf(Integer.valueOf(lastByte, 16).intValue() + 1 & 0xFF) });
/* 1120 */     return firstBytes + lastByteIncremented;
/*      */   }
/*      */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\WearManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */