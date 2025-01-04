package com.android.chileaf;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.ParcelUuid;
import android.text.TextUtils;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.chileaf.fitness.FitnessManager;
import com.android.chileaf.fitness.callback.AccelerometerCallback;
import com.android.chileaf.fitness.callback.BloodOxygenCallback;
import com.android.chileaf.fitness.callback.BluetoothStatusCallback;
import com.android.chileaf.fitness.callback.BodyHealthCallback;
import com.android.chileaf.fitness.callback.BodySportCallback;
import com.android.chileaf.fitness.callback.BodySportHealthCallback;
import com.android.chileaf.fitness.callback.CustomDataReceivedCallback;
import com.android.chileaf.fitness.callback.HeartRateAlarmCallback;
import com.android.chileaf.fitness.callback.HeartRateMaxCallback;
import com.android.chileaf.fitness.callback.HeartRateStatusCallback;
import com.android.chileaf.fitness.callback.HistoryOf3DDataCallback;
import com.android.chileaf.fitness.callback.HistoryOfHRDataCallback;
import com.android.chileaf.fitness.callback.HistoryOfHRRecordCallback;
import com.android.chileaf.fitness.callback.HistoryOfRRDataCallback;
import com.android.chileaf.fitness.callback.HistoryOfRRRecordCallback;
import com.android.chileaf.fitness.callback.HistoryOfSingleRecordCallback;
import com.android.chileaf.fitness.callback.HistoryOfSleepCallback;
import com.android.chileaf.fitness.callback.HistoryOfSportCallback;
import com.android.chileaf.fitness.callback.IntervalStepCallback;
import com.android.chileaf.fitness.callback.Sensor3DFrequencyCallback;
import com.android.chileaf.fitness.callback.Sensor3DStatusCallback;
import com.android.chileaf.fitness.callback.Sensor6DFrequencyCallback;
import com.android.chileaf.fitness.callback.Sensor6DRawDataCallback;
import com.android.chileaf.fitness.callback.SingleTapRecordCallback;
import com.android.chileaf.fitness.callback.TemperatureCallback;
import com.android.chileaf.fitness.callback.UserInfoCallback;
import com.android.chileaf.fitness.callback.WearManagerCallbacks;
import com.android.chileaf.fitness.callback.WearReceivedDataCallback;
import com.android.chileaf.fitness.common.FilterScanCallback;
import com.android.chileaf.fitness.common.heart.BodySensorLocationDataCallback;
import com.android.chileaf.fitness.common.heart.HeartRateMeasurementCallback;
import com.android.chileaf.fitness.common.heart.HeartRateMeasurementDataCallback;
import com.android.chileaf.fitness.common.parser.BodySensorLocationParser;
import com.android.chileaf.fitness.common.parser.HeartRateMeasurementParser;
import com.android.chileaf.model.HistoryOf3D;
import com.android.chileaf.model.HistoryOfHeartRate;
import com.android.chileaf.model.HistoryOfRecord;
import com.android.chileaf.model.HistoryOfRespiratoryRate;
import com.android.chileaf.model.HistoryOfSport;
import com.android.chileaf.model.HistorySleep;
import com.android.chileaf.model.IntervalStep;
import com.android.chileaf.util.DateUtil;
import com.android.chileaf.util.HexUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import no.nordicsemi.android.ble.BleManager;
import no.nordicsemi.android.ble.callback.DataReceivedCallback;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

@SuppressLint({"MissingPermission"})
public class WearManager extends FitnessManager<WearManagerCallbacks> {
   private static final UUID HR_SERVICE_UUID = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
   private static final UUID BODY_SENSOR_LOCATION_CHARACTERISTIC_UUID = UUID.fromString("00002A38-0000-1000-8000-00805f9b34fb");
   private static final UUID HEART_RATE_MEASUREMENT_CHARACTERISTIC_UUID = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");
   private static final String[] MODE_NAMES = new String[]{"CL831", "CL880N"};
   private WearManager.WearScanCallback mScanCallback;
   private static WearManager managerInstance = null;
   private String[] mFilterNames = null;
   private BluetoothGattCharacteristic mBodySensorLocationCharacteristic;
   private BluetoothGattCharacteristic mHeartRateCharacteristic;
   private UserInfoCallback mUserInfoCallback;
   private AccelerometerCallback mAccelerometerCallback;
   private BodySportCallback mBodySportCallback;
   private BodyHealthCallback mBodyHealthCallback;
   private HeartRateMeasurementCallback mHeartRateMeasurementCallback;
   private HistoryOfSportCallback mHistoryOfSportCallback;
   private HistoryOfHRRecordCallback mHistoryOfHRRecordCallback;
   private HistoryOfHRDataCallback mHistoryOfHRDataCallback;
   private HistoryOfRRRecordCallback mHistoryOfRRRecordCallback;
   private HistoryOfRRDataCallback mHistoryOfRRDataCallback;
   private IntervalStepCallback mIntervalStepsCallback;
   private SingleTapRecordCallback mSingleTapRecordCallback;
   private BluetoothStatusCallback mBluetoothStatusCallback;
   private CustomDataReceivedCallback mCustomDataReceivedCallback;
   private HeartRateStatusCallback mHeartRateStatusCallback;
   private BloodOxygenCallback mBloodOxygenCallback;
   private TemperatureCallback mTemperatureCallback;
   private HistoryOfSingleRecordCallback mHistoryOfSingleRecordCallback;
   private HeartRateAlarmCallback mHeartRateAlarmCallback;
   private HeartRateMaxCallback mHeartRateMaxCallback;
   private HistoryOfSleepCallback mHistoryOfSleepCallback;
   private Sensor3DFrequencyCallback mSensor3DFrequencyCallback;
   private Sensor3DStatusCallback mSensor3DStatusCallback;
   private Sensor6DFrequencyCallback mSensor6DFrequencyCallback;
   private Sensor6DRawDataCallback mSensor6DRawDataCallback;
   private BodySportHealthCallback mBodySportHealthCallback;
   private HistoryOf3DDataCallback mHistoryOf3DDataCallback;
   private final BodySensorLocationDataCallback mBodySensorLocationDataCallback = new BodySensorLocationDataCallback() {
      public void onBodySensorLocationReceived(@NonNull final BluetoothDevice device, final int sensorLocation) {
         ((WearManagerCallbacks)WearManager.this.mCallbacks).onBodySensorLocationReceived(device, sensorLocation);
      }

      public void onDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {
         WearManager.this.log(3, String.format("%s received", BodySensorLocationParser.parse(data)));
         super.onDataReceived(device, data);
      }
   };
   private final HeartRateMeasurementDataCallback mHeartRateMeasureDataCallback = new HeartRateMeasurementDataCallback() {
      public void onHeartRateMeasurementReceived(@NonNull BluetoothDevice device, int heartRate, @Nullable Boolean contactDetected, @Nullable Integer energyExpanded, @Nullable List<Integer> rrIntervals) {
         ((WearManagerCallbacks)WearManager.this.mCallbacks).onHeartRateMeasurementReceived(device, heartRate, contactDetected, energyExpanded, rrIntervals);
         if (WearManager.this.mHeartRateMeasurementCallback != null) {
            WearManager.this.mHeartRateMeasurementCallback.onHeartRateMeasurementReceived(device, heartRate, contactDetected, energyExpanded, rrIntervals);
         }

      }

      public void onDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {
         WearManager.this.log(3, HeartRateMeasurementParser.parse(data) + " received");
         super.onDataReceived(device, data);
      }
   };
   private final WearReceivedDataCallback mReceivedDataCallback = new WearReceivedDataCallback() {
      public void onSportReceived(@NonNull BluetoothDevice device, int step, int distance, int calorie) {
         ((WearManagerCallbacks)WearManager.this.mCallbacks).onSportReceived(device, step, distance, calorie);
         if (WearManager.this.mBodySportCallback != null) {
            WearManager.this.mBodySportCallback.onSportReceived(device, step, distance, calorie);
         }

      }

      public void onHealthReceived(@NonNull BluetoothDevice device, int vo2Max, int breathRate, int emotionLevel, int stressPercent, int stamina, float tp, float lf, float hf) {
         if (WearManager.this.mBodyHealthCallback != null) {
            WearManager.this.mBodyHealthCallback.onHealthReceived(device, vo2Max, breathRate, emotionLevel, stressPercent, stamina, tp, lf, hf);
         }

      }

      public void onSportHealthReceived(@NonNull BluetoothDevice device, int vo2Max, int breathRate, int emotion, int pressure, int stamina) {
         if (WearManager.this.mBodySportHealthCallback != null) {
            WearManager.this.mBodySportHealthCallback.onSportHealthReceived(device, vo2Max, breathRate, emotion, pressure, stamina);
         }

      }

      public void onBluetoothStatusReceived(@NonNull BluetoothDevice device, boolean enabled) {
         ((WearManagerCallbacks)WearManager.this.mCallbacks).onBluetoothStatusReceived(device, enabled);
         if (WearManager.this.mBluetoothStatusCallback != null) {
            WearManager.this.mBluetoothStatusCallback.onBluetoothStatusReceived(device, enabled);
         }

      }

      public void onUserInfoReceived(@NonNull BluetoothDevice device, int age, int sex, int weight, int height, long userId) {
         if (WearManager.this.mUserInfoCallback != null) {
            WearManager.this.mUserInfoCallback.onUserInfoReceived(device, age, sex, weight, height, userId);
         }

      }

      public void onHistoryOfSportReceived(@NonNull BluetoothDevice device, List<HistoryOfSport> sports) {
         if (WearManager.this.mHistoryOfSportCallback != null) {
            WearManager.this.mHistoryOfSportCallback.onHistoryOfSportReceived(device, sports);
         }

      }

      public void onHistoryOfHRRecordReceived(@NonNull BluetoothDevice device, List<HistoryOfRecord> records) {
         if (WearManager.this.mHistoryOfHRRecordCallback != null) {
            WearManager.this.mHistoryOfHRRecordCallback.onHistoryOfHRRecordReceived(device, records);
         }

      }

      public void onHistoryOfHRDataReceived(@NonNull BluetoothDevice device, List<HistoryOfHeartRate> heartRates) {
         if (WearManager.this.mHistoryOfHRDataCallback != null) {
            WearManager.this.mHistoryOfHRDataCallback.onHistoryOfHRDataReceived(device, heartRates);
         }

      }

      public void onHistoryOfRRRecordReceived(@NonNull BluetoothDevice device, List<HistoryOfRecord> records) {
         if (WearManager.this.mHistoryOfRRRecordCallback != null) {
            WearManager.this.mHistoryOfRRRecordCallback.onHistoryOfRRRecordReceived(device, records);
         }

      }

      public void onHistoryOfRRDataReceived(@NonNull BluetoothDevice device, List<HistoryOfRespiratoryRate> respiratoryRates) {
         if (WearManager.this.mHistoryOfRRDataCallback != null) {
            WearManager.this.mHistoryOfRRDataCallback.onHistoryOfRRDataReceived(device, respiratoryRates);
         }

      }

      public void onIntervalStepReceived(@NonNull BluetoothDevice device, List<IntervalStep> steps) {
         if (WearManager.this.mIntervalStepsCallback != null) {
            WearManager.this.mIntervalStepsCallback.onIntervalStepReceived(device, steps);
         }

      }

      public void onSingleTapRecordReceived(@NonNull BluetoothDevice device, List<HistoryOfRecord> records) {
         if (WearManager.this.mSingleTapRecordCallback != null) {
            WearManager.this.mSingleTapRecordCallback.onSingleTapRecordReceived(device, records);
         }

      }

      public void onHeartRateStatusReceived(@NonNull BluetoothDevice device, int min, int max, int goal) {
         if (WearManager.this.mHeartRateStatusCallback != null) {
            WearManager.this.mHeartRateStatusCallback.onHeartRateStatusReceived(device, min, max, goal);
         }

      }

      public void onHistoryOf3DDataReceived(@NonNull BluetoothDevice device, HistoryOf3D history, boolean finish) {
         if (WearManager.this.mHistoryOf3DDataCallback != null) {
            WearManager.this.mHistoryOf3DDataCallback.onHistoryOf3DDataReceived(device, history, finish);
         }

      }

      public void onBloodOxygenReceived(@NonNull BluetoothDevice device, int bSwitch, String value, int gesture, int piValue, int onwrist) {
         if (WearManager.this.mBloodOxygenCallback != null) {
            WearManager.this.mBloodOxygenCallback.onBloodOxygenReceived(device, bSwitch, value, gesture, piValue, onwrist);
         }

      }

      public void onTemperatureReceived(@NonNull BluetoothDevice device, float environment, float wrist, float body) {
         if (WearManager.this.mTemperatureCallback != null) {
            WearManager.this.mTemperatureCallback.onTemperatureReceived(device, environment, wrist, body);
         }

      }

      public void onHistorySingleRecordReceived(@NonNull BluetoothDevice device, long stamp, long step, long distance, long calorie) {
         if (WearManager.this.mHistoryOfSingleRecordCallback != null) {
            WearManager.this.mHistoryOfSingleRecordCallback.onHistorySingleRecordReceived(device, stamp, step, distance, calorie);
         }

      }

      public void onHeartRateAlarmReceived(@NonNull BluetoothDevice device, long stamp, boolean enabled) {
         if (WearManager.this.mHeartRateAlarmCallback != null) {
            WearManager.this.mHeartRateAlarmCallback.onHeartRateAlarmReceived(device, stamp, enabled);
         }

      }

      public void onAccelerometerReceived(@NonNull BluetoothDevice device, int x, int y, int z) {
         if (WearManager.this.mAccelerometerCallback != null) {
            WearManager.this.mAccelerometerCallback.onAccelerometerReceived(device, x, y, z);
         }

      }

      public void onHeartRateMaxReceived(@NonNull BluetoothDevice device, int max) {
         if (WearManager.this.mHeartRateMaxCallback != null) {
            WearManager.this.mHeartRateMaxCallback.onHeartRateMaxReceived(device, max);
         }

      }

      public void onHistoryOfSleepReceived(@NonNull BluetoothDevice device, List<HistorySleep> sleeps) {
         if (WearManager.this.mHistoryOfSleepCallback != null) {
            WearManager.this.mHistoryOfSleepCallback.onHistoryOfSleepReceived(device, sleeps);
         }

      }

      public void onSensor3DFrequencyReceived(@NonNull BluetoothDevice device, int frequency) {
         if (WearManager.this.mSensor3DFrequencyCallback != null) {
            WearManager.this.mSensor3DFrequencyCallback.onSensor3DFrequencyReceived(device, frequency);
         }

      }

      public void onSensor3DStatusReceived(@NonNull BluetoothDevice device, boolean enabled) {
         if (WearManager.this.mSensor3DStatusCallback != null) {
            WearManager.this.mSensor3DStatusCallback.onSensor3DStatusReceived(device, enabled);
         }

      }

      public void onSensor6DFrequencyReceived(@NonNull BluetoothDevice device, int sensor) {
         if (WearManager.this.mSensor6DFrequencyCallback != null) {
            WearManager.this.mSensor6DFrequencyCallback.onSensor6DFrequencyReceived(device, sensor);
         }

      }

      public void onSensor6DRawDataReceived(@NonNull BluetoothDevice device, long utc, int sequence, int gyroscopeX, int gyroscopeY, int gyroscopeZ, int accelerometerX, int accelerometerY, int accelerometerZ) {
         if (WearManager.this.mSensor6DRawDataCallback != null) {
            WearManager.this.mSensor6DRawDataCallback.onSensor6DRawDataReceived(device, utc, sequence, gyroscopeX, gyroscopeY, gyroscopeZ, accelerometerX, accelerometerY, accelerometerZ);
         }

      }
   };

   public static synchronized WearManager getInstance(final Context context) {
      if (managerInstance == null) {
         managerInstance = new WearManager(context);
      }

      return managerInstance;
   }

   public WearManager(final Context context) {
      super(context);
   }

   public void checkModel(String modelName, boolean isCL833) {
      boolean cl833 = this.checkMode(modelName);
      this.mReceivedDataCallback.setCL833(cl833 && isCL833);
      this.log(3, String.format("Check modelName:%s isCL833:%s", modelName, isCL833));
   }

   private boolean checkMode(String name) {
      String[] var2 = MODE_NAMES;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String device = var2[var4];
         if (device.equalsIgnoreCase(name)) {
            return true;
         }
      }

      return false;
   }

   @NonNull
   protected BleManager.BleManagerGattCallback getGattCallback() {
      return new WearManager.WearManagerGattCallback();
   }

   public void addBodySportCallback(final BodySportCallback callback) {
      this.mBodySportCallback = callback;
   }

   public void addBodyHealthCallback(final BodyHealthCallback callback) {
      this.mBodyHealthCallback = callback;
   }

   public void addHeartRateMeasurementCallback(final HeartRateMeasurementCallback callback) {
      this.mHeartRateMeasurementCallback = callback;
   }

   public void setBluetoothStatusCallback(BluetoothStatusCallback callback) {
      this.mBluetoothStatusCallback = callback;
   }

   public void addUserInfoCallback(final UserInfoCallback callback) {
      this.mUserInfoCallback = callback;
   }

   public void addHistoryOfSportCallback(final HistoryOfSportCallback callback) {
      this.mHistoryOfSportCallback = callback;
   }

   public void addHistoryOfHRRecordCallback(final HistoryOfHRRecordCallback callback) {
      this.mHistoryOfHRRecordCallback = callback;
   }

   public void addHistoryOfHRDataCallback(final HistoryOfHRDataCallback callback) {
      this.mHistoryOfHRDataCallback = callback;
   }

   public void addHistoryOfRRRecordCallback(final HistoryOfRRRecordCallback callback) {
      this.mHistoryOfRRRecordCallback = callback;
   }

   public void addHistoryOfRRDataCallback(final HistoryOfRRDataCallback callback) {
      this.mHistoryOfRRDataCallback = callback;
   }

   public void addIntervalStepCallback(final IntervalStepCallback callback) {
      this.mIntervalStepsCallback = callback;
   }

   public void addSingleTapRecordCallback(final SingleTapRecordCallback callback) {
      this.mSingleTapRecordCallback = callback;
   }

   public void addHistoryOf3DDataCallback(final HistoryOf3DDataCallback callback) {
      this.mHistoryOf3DDataCallback = callback;
   }

   public void addHeartRateStatusCallback(HeartRateStatusCallback callback) {
      this.mHeartRateStatusCallback = callback;
   }

   public void addBloodOxygenCallback(final BloodOxygenCallback callback) {
      this.mBloodOxygenCallback = callback;
   }

   public void addTemperatureCallback(final TemperatureCallback callback) {
      this.mTemperatureCallback = callback;
   }

   public void addHistoryOfSingleRecordCallback(final HistoryOfSingleRecordCallback callback) {
      this.mHistoryOfSingleRecordCallback = callback;
   }

   public void addHeartRateAlarmCallback(HeartRateAlarmCallback callback) {
      this.mHeartRateAlarmCallback = callback;
   }

   public void addAccelerometerCallback(final AccelerometerCallback callback) {
      this.mAccelerometerCallback = callback;
   }

   public void addHeartRateMaxCallback(HeartRateMaxCallback callback) {
      this.mHeartRateMaxCallback = callback;
   }

   public void addHistoryOfSleepCallback(HistoryOfSleepCallback callback) {
      this.mHistoryOfSleepCallback = callback;
   }

   public void addSensor3DFrequencyCallback(Sensor3DFrequencyCallback callback) {
      this.mSensor3DFrequencyCallback = callback;
   }

   public void addSensor3DStatusCallback(Sensor3DStatusCallback callback) {
      this.mSensor3DStatusCallback = callback;
   }

   public void addSensor6DFrequencyCallback(final Sensor6DFrequencyCallback callback) {
      this.mSensor6DFrequencyCallback = callback;
   }

   public void addSensor6DRawDataCallback(final Sensor6DRawDataCallback callback) {
      this.mSensor6DRawDataCallback = callback;
   }

   public void addBodySportHealthCallback(final BodySportHealthCallback callback) {
      this.mBodySportHealthCallback = callback;
   }

   public void setFilterNames(String... filterNames) {
      this.mFilterNames = filterNames;
   }

   public void startScan(final FilterScanCallback callback) {
      this.mScanCallback = new WearManager.WearScanCallback(callback);
      BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
      ScanSettings settings = (new ScanSettings.Builder()).setLegacy(false).setReportDelay(1000L).setUseHardwareBatchingIfSupported(false).setScanMode(2).build();
      List<ScanFilter> filters = new ArrayList();
      ParcelUuid uuid = new ParcelUuid(HR_SERVICE_UUID);
      filters.add((new ScanFilter.Builder()).setServiceUuid(uuid).build());
      scanner.startScan(filters, settings, this.mScanCallback);
   }

   public void stopScan() {
      if (this.mScanCallback != null) {
         BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
         scanner.stopScan(this.mScanCallback);
      }

   }

   private void sendCommand(final byte cmd, final int... values) {
      int unique = true;
      byte[] result;
      byte[] command;
      if (values != null) {
         int len = values.length + 4;
         command = HexUtil.compose(255, len, cmd);
         byte[] bytes = HexUtil.compose(values);
         result = HexUtil.append(command, bytes);
      } else {
         result = HexUtil.compose(255, 4, cmd);
      }

      byte check = this.checkSum(result);
      command = HexUtil.append(result, check);
      this.writeTxCharacteristic(command);
   }

   protected int[] utc2Bytes(final long stamp) {
      int[] utcArray = new int[]{(int)(stamp >> 24), (int)(stamp >> 16), (int)(stamp >> 8), (int)stamp};
      return utcArray;
   }

   public void setUTCTime() {
      this.setUTCTime(DateUtil.getZoneUTC());
   }

   public void setUTCTime(final long stamp) {
      this.sendCommand((byte)8, this.utc2Bytes(stamp));
   }

   public void shutdown() {
      this.sendCommand((byte)-15, 0);
   }

   public void restoration() {
      this.sendCommand((byte)-13, 0);
   }

   public void setBluetoothDisabled() {
      this.sendCommand((byte)63, 2);
   }

   public void getHistoryOfSport() {
      this.mReceivedDataCallback.clearType(2);
      this.sendCommand((byte)22, 0);
   }

   public void getHistoryOfHRRecord() {
      this.mReceivedDataCallback.clearType(4);
      this.sendCommand((byte)33, 0);
   }

   public void getHistoryOfHRData(final long stamp) {
      this.mReceivedDataCallback.clearType(6);
      this.sendCommand((byte)34, HexUtil.append((int)1, (int[])this.utc2Bytes(stamp)));
   }

   public void getHistoryOfRRRecord() {
      this.mReceivedDataCallback.clearType(8);
      this.sendCommand((byte)36);
   }

   public void getHistoryOfRRData(final long stamp) {
      this.mReceivedDataCallback.clearType(16);
      this.sendCommand((byte)37, HexUtil.append((int)1, (int[])this.utc2Bytes(stamp)));
   }

   public void getIntervalSteps() {
      this.mReceivedDataCallback.clearType(18);
      this.sendCommand((byte)64, 0);
   }

   public void getSingleTapRecords() {
      this.mReceivedDataCallback.clearType(20);
      this.sendCommand((byte)66, 0);
   }

   public void getHistoryOfSleep() {
      this.mReceivedDataCallback.clearType(22);
      this.sendCommand((byte)5, 2);
   }

   public void getUserInfo() {
      this.sendCommand((byte)3, 0);
   }

   public void setUserInfo(final int age, final int sex, final int weight, final int height, final long userId) {
      int[] command = new int[]{(byte)age, (byte)sex, (byte)weight, (byte)height, (byte)((int)(userId >> 32 & 255L)), (byte)((int)(userId >> 24 & 255L)), (byte)((int)(userId >> 16 & 255L)), (byte)((int)(userId >> 8 & 255L)), (byte)((int)(userId & 255L))};
      this.sendCommand((byte)4, command);
   }

   public void getHeartRateStatus() {
      this.sendCommand((byte)70, 0);
   }

   public void setHeartRateStatus(int min, int max, int goal) {
      this.sendCommand((byte)70, 1, (byte)min, (byte)max, (byte)goal);
   }

   public void setHeartRateMax(int max) {
      this.sendCommand((byte)116, 0, 6, (byte)max);
   }

   public void getHeartRateMax() {
      this.sendCommand((byte)117, 0, 6);
   }

   public void setBloodOxygen(final int mode) {
      int[] command = new int[]{(byte)mode, 0};
      this.sendCommand((byte)55, command);
   }

   public void setHeartRateAlarm(final boolean alarm) {
      this.sendCommand((byte)87, alarm ? 1 : 0);
   }

   public void getHeartRateAlarm() {
      this.sendCommand((byte)91, 0);
   }

   public void getHistoryOfSingleRecord(final long stamp) {
      this.sendCommand((byte)73, this.utc2Bytes(stamp));
   }

   public void set3DFrequency(@IntRange(from = 0L,to = 4L) int frequency) {
      this.sendCommand((byte)116, 0, 11, (byte)frequency);
   }

   public void get3DFrequency() {
      this.sendCommand((byte)117, 0, 11);
   }

   public void set3DEnabled(boolean enabled) {
      this.sendCommand((byte)116, 0, 12, enabled ? 1 : 0);
   }

   public void get3DStatus() {
      this.sendCommand((byte)117, 0, 12);
   }

   public void get6DFrequency() {
      this.sendCommand((byte)97, 0);
   }

   public void set6DFrequency(@IntRange(from = 0L,to = 3L) int frequency) {
      this.sendCommand((byte)98, frequency);
   }

   public void setCustomDataReceivedCallback(CustomDataReceivedCallback customDataReceivedCallback) {
      this.mCustomDataReceivedCallback = customDataReceivedCallback;
   }

   public void getHistoryOf3D() {
      this.sendCommand((byte)119, 0);
   }

   public String dfuMode() {
      String address = this.getDFUAddress();
      byte[] command = new byte[]{-1, 4, 39, 0};
      command[3] = this.checkSum(command);
      this.writeTxCharacteristic(command);
      return address;
   }

   private String getDFUAddress() {
      BluetoothDevice device = this.getBluetoothDevice();
      if (device == null) {
         return null;
      } else {
         String deviceAddress = device.getAddress();
         String firstBytes = deviceAddress.substring(0, 15);
         String lastByte = deviceAddress.substring(15);
         String lastByteIncremented = String.format(Locale.US, "%02X", Integer.valueOf(lastByte, 16) + 1 & 255);
         return firstBytes + lastByteIncremented;
      }
   }

   private final class WearManagerGattCallback extends FitnessManager<WearManagerCallbacks>.FitnessManagerGattCallback {
      private WearManagerGattCallback() {
         super();
      }

      protected void initialize() {
         super.initialize();
         WearManager.this.readCharacteristic(WearManager.this.mBodySensorLocationCharacteristic).with((DataReceivedCallback)WearManager.this.mBodySensorLocationDataCallback).fail((device, status) -> {
            WearManager.this.log(5, "Body Sensor Location characteristic not found");
         }).enqueue();
         WearManager.this.setNotificationCallback(WearManager.this.mHeartRateCharacteristic).with(WearManager.this.mHeartRateMeasureDataCallback);
         WearManager.this.enableNotifications(WearManager.this.mHeartRateCharacteristic).enqueue();
         WearManager.this.setNotificationCallback(WearManager.this.mRXCharacteristic).with(WearManager.this.mReceivedDataCallback);
         WearManager.this.enableNotifications(WearManager.this.mRXCharacteristic).done((device) -> {
            WearManager.this.log(3, "Rx notifications enabled");
         }).fail((device, status) -> {
            WearManager.this.log(5, "Rx characteristic not found");
         }).enqueue();
         if (WearManager.this.mCustomRxCharacteristic != null) {
            WearManager.this.setNotificationCallback(WearManager.this.mCustomRxCharacteristic).with((device, data) -> {
               if (WearManager.this.mCustomDataReceivedCallback != null) {
                  WearManager.this.mCustomDataReceivedCallback.onDataReceived(device, data.getValue());
               }

            });
            WearManager.this.enableNotifications(WearManager.this.mCustomRxCharacteristic).enqueue();
         }

      }

      public boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
         super.isRequiredServiceSupported(gatt);
         BluetoothGattService hrService = gatt.getService(WearManager.HR_SERVICE_UUID);
         if (hrService != null) {
            WearManager.this.mHeartRateCharacteristic = hrService.getCharacteristic(WearManager.HEART_RATE_MEASUREMENT_CHARACTERISTIC_UUID);
         }

         return WearManager.this.mHeartRateCharacteristic != null;
      }

      protected boolean isOptionalServiceSupported(@NonNull final BluetoothGatt gatt) {
         super.isOptionalServiceSupported(gatt);
         BluetoothGattService service = gatt.getService(WearManager.HR_SERVICE_UUID);
         if (service != null) {
            WearManager.this.mBodySensorLocationCharacteristic = service.getCharacteristic(WearManager.BODY_SENSOR_LOCATION_CHARACTERISTIC_UUID);
         }

         return WearManager.this.mBodySensorLocationCharacteristic != null;
      }

      protected void onDeviceReady() {
         super.onDeviceReady();
         WearManager.this.setUTCTime();
      }

      protected void onDeviceDisconnected() {
         super.onDeviceDisconnected();
         WearManager.this.mBodySensorLocationCharacteristic = null;
         WearManager.this.mHeartRateCharacteristic = null;
         WearManager.this.mReceivedDataCallback.setCL833(false);
      }

      protected void onServicesInvalidated() {
         super.onServicesInvalidated();
         WearManager.this.mBodySensorLocationCharacteristic = null;
         WearManager.this.mHeartRateCharacteristic = null;
         WearManager.this.mReceivedDataCallback.setCL833(false);
      }

      // $FF: synthetic method
      WearManagerGattCallback(Object x1) {
         this();
      }
   }

   private final class WearScanCallback extends ScanCallback {
      private final FilterScanCallback mCallback;

      private WearScanCallback(FilterScanCallback callback) {
         this.mCallback = callback;
      }

      private boolean matchDeviceName(BluetoothDevice device) {
         if (WearManager.this.mFilterNames == null) {
            return true;
         } else {
            if (device != null) {
               String name = device.getName();
               if (name != null && !TextUtils.isEmpty(name)) {
                  String[] var3 = WearManager.this.mFilterNames;
                  int var4 = var3.length;

                  for(int var5 = 0; var5 < var4; ++var5) {
                     String filterName = var3[var5];
                     if (name.contains(filterName)) {
                        return true;
                     }
                  }
               }
            }

            return false;
         }
      }

      public void onScanResult(int callbackType, @NonNull ScanResult result) {
         super.onScanResult(callbackType, result);
         if (this.mCallback != null) {
            this.mCallback.onScanResult(callbackType, result);
         }

      }

      public void onBatchScanResults(@NonNull List<ScanResult> results) {
         List<ScanResult> scanDevices = new ArrayList();
         Iterator var3 = results.iterator();

         while(var3.hasNext()) {
            ScanResult result = (ScanResult)var3.next();
            BluetoothDevice device = result.getDevice();
            if (this.matchDeviceName(device)) {
               scanDevices.add(result);
            }
         }

         if (this.mCallback != null) {
            this.mCallback.onBatchScanResults(results);
            this.mCallback.onFilterScanResults(scanDevices);
         }

      }

      public void onScanFailed(int errorCode) {
         super.onScanFailed(errorCode);
         if (this.mCallback != null) {
            this.mCallback.onScanFailed(errorCode);
         }

      }

      // $FF: synthetic method
      WearScanCallback(FilterScanCallback x1, Object x2) {
         this(x1);
      }
   }
}
