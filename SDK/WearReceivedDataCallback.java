package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import com.android.chileaf.model.HistoryOf3D;
import com.android.chileaf.model.HistoryOfHeartRate;
import com.android.chileaf.model.HistoryOfRecord;
import com.android.chileaf.model.HistoryOfRespiratoryRate;
import com.android.chileaf.model.HistoryOfSport;
import com.android.chileaf.model.HistorySleep;
import com.android.chileaf.model.IntervalStep;
import com.android.chileaf.util.DateUtil;
import com.android.chileaf.util.HexUtil;
import com.android.chileaf.util.LogUtil;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.UByte;
import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.utils.ParserUtils;

/* loaded from: c:\Users\Admin\Documents\visualstudiocode\cl837\SDK\NUOVO\classes.dex */
public abstract class WearReceivedDataCallback extends ProfileReadResponse implements UserInfoCallback, BodySportCallback, BluetoothStatusCallback, HistoryOfSportCallback, HistoryOfHRRecordCallback, HistoryOfHRDataCallback, HistoryOfRRRecordCallback, HistoryOfRRDataCallback, IntervalStepCallback, SingleTapRecordCallback, HeartRateStatusCallback, BloodOxygenCallback, TemperatureCallback, HistoryOfSingleRecordCallback, HeartRateAlarmCallback, AccelerometerCallback, HeartRateMaxCallback, HistoryOfSleepCallback, Sensor3DFrequencyCallback, Sensor3DStatusCallback, HistoryOf3DDataCallback, BodyHealthCallback, Sensor6DFrequencyCallback, Sensor6DRawDataCallback, BodySportHealthCallback {
    private static final long END_TAG = 4294967295L;
    public static final int TYPE_HEART = 4;
    public static final int TYPE_HEARTS = 6;
    public static final int TYPE_HEART_RR = 8;
    public static final int TYPE_HEART_RRS = 16;
    public static final int TYPE_HISTORY_3D = 24;
    public static final int TYPE_INTERVAL = 18;
    public static final int TYPE_SINGLE_TAP = 20;
    public static final int TYPE_SLEEP = 22;
    public static final int TYPE_SPORT = 2;
    private boolean isCL833;
    private boolean isStamp;
    private List<HistoryOfHeartRate> mHistoryOfHeartRates;
    private List<HistoryOfRecord> mHistoryOfRecords;
    private List<HistoryOfRespiratoryRate> mHistoryOfRespiratoryRates;
    private List<HistorySleep> mHistoryOfSleeps;
    private List<HistoryOfSport> mHistoryOfSports;
    private List<IntervalStep> mIntervalSteps;
    private List<Data> mPackages;
    private List<HistoryOfRecord> mRespiratoryRatesRecords;
    private List<HistoryOfRecord> mSingleTapRecords;
    private long mStamp;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: c:\Users\Admin\Documents\visualstudiocode\cl837\SDK\NUOVO\classes.dex */
    public @interface DataType {
    }

    public WearReceivedDataCallback() {
        this.mStamp = 0L;
        this.isCL833 = false;
        this.isStamp = false;
        this.mPackages = new ArrayList();
    }

    protected WearReceivedDataCallback(final Parcel in) {
        super(in);
        this.mStamp = 0L;
        this.isCL833 = false;
        this.isStamp = false;
        this.mPackages = new ArrayList();
    }

    @Override // no.nordicsemi.android.ble.response.ReadResponse, no.nordicsemi.android.ble.callback.DataReceivedCallback
    public void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        super.onDataReceived(bluetoothDevice, data);
        char c = 1;
        boolean z = true;
        char c2 = 0;
        if (!(data.getIntValue(17, 1).intValue() == data.size())) {
            onInvalidDataReceived(bluetoothDevice, data);
            LogUtil.w("onDataReceived:length:%s", Integer.valueOf(data.size()));
        }
        byte[] value = data.getValue();
        int i11 = 2;
        int intValue = data.getIntValue(17, 2).intValue();
        int i12 = 3;
        try {
            if (intValue == 3) {
                onUserInfoReceived(bluetoothDevice, getIntParse(value, 5, 1), getIntParse(value, 6, 1), getIntParse(value, 7, 1), getIntParse(value, 8, 1), getLongParse(value, 9, 5));
                return;
            }
            int i13 = 4;
            if (intValue == 5) {
                if (getIntParse(value, 3, 1) == 3) {
                    if (this.mHistoryOfSleeps == null) {
                        this.mHistoryOfSleeps = new ArrayList();
                    }
                    int i14 = 4;
                    while (i14 < value.length) {
                        int i15 = value[i14];
                        if (i15 >= 1) {
                            int i16 = i14 + 1;
                            long longParse = getLongParse(value, i16, i13);
                            int i17 = i16 + 4;
                            long j = (longParse * 1000) - 28800000;
                            int[] iArr = new int[i15];
                            for (int i18 = 0; i18 < i15; i18++) {
                                iArr[i18] = getIntParse(value, i18 + i17, 1);
                            }
                            i14 = i17 + (i15 - 1);
                            this.mHistoryOfSleeps.add(new HistorySleep(j, iArr));
                            if (i14 == value.length - 2) {
                                break;
                            }
                        }
                        i14++;
                        i13 = 4;
                    }
                    onHistoryOfSleepReceived(bluetoothDevice, this.mHistoryOfSleeps);
                    this.mHistoryOfSleeps.clear();
                }
                return;
            }
            if (intValue == 12) {
                byte[] subSlice = subSlice(3, value);
                for (int i19 = 0; i19 < subSlice.length / 6; i19++) {
                    int i20 = i19 * 6;
                    onAccelerometerReceived(bluetoothDevice, data.getIntValue(34, i20 + 3).intValue(), data.getIntValue(34, i20 + 5).intValue(), data.getIntValue(34, i20 + 7).intValue());
                }
            } else if (intValue != 18) {
                if (intValue == 19) {
                    onSportHealthReceived(bluetoothDevice, getIntParse(value, 3, 1), getIntParse(value, 4, 1), getIntParse(value, 5, 1), getIntParse(value, 6, 1), getIntParse(value, 7, 1));
                    return;
                }
                int i21 = 4;
                if (intValue == 21) {
                    onSportReceived(bluetoothDevice, getIntParse(value, 3, 3), getIntParse(value, 6, 3), getIntParse(value, 9, 3));
                    return;
                }
                if (intValue == 63) {
                    if (getIntParse(value, 3, 1) != 1) {
                        z = false;
                    }
                    onBluetoothStatusReceived(bluetoothDevice, z);
                    return;
                }
                if (intValue == 22) {
                    if (this.mHistoryOfSports == null) {
                        this.mHistoryOfSports = new ArrayList();
                    }
                    long longParse2 = getLongParse(value, 3, 4);
                    if (this.isCL833) {
                        parseSportHistory(subSlice(3, value));
                        Collections.reverse(this.mHistoryOfSports);
                        onHistoryOfSportReceived(bluetoothDevice, this.mHistoryOfSports);
                        this.mHistoryOfSports.clear();
                    } else if (longParse2 != END_TAG) {
                        this.mPackages.add(data);
                    } else {
                        for (int i22 = 0; i22 < this.mPackages.size(); i22++) {
                            byte[] subSlice2 = subSlice(3, this.mPackages.get(i22).getValue());
                            parseSportHistory(subSlice2);
                            LogUtil.d("HistoryOfSport index:%d values:%s", Integer.valueOf(i22), ParserUtils.parse(subSlice2));
                        }
                        onHistoryOfSportReceived(bluetoothDevice, this.mHistoryOfSports);
                        this.mHistoryOfSports.clear();
                        this.mPackages.clear();
                    }
                    return;
                }
                if (intValue == 33) {
                    if (this.mHistoryOfRecords == null) {
                        this.mHistoryOfRecords = new ArrayList();
                    }
                    if (getLongParse(value, 3, 4) != END_TAG) {
                        this.mPackages.add(data);
                    } else {
                        int i23 = 0;
                        while (i23 < this.mPackages.size()) {
                            byte[] subSlice3 = subSlice(i12, this.mPackages.get(i23).getValue());
                            Object[] objArr = new Object[i11];
                            objArr[c2] = Integer.valueOf(i23);
                            objArr[c] = ParserUtils.parse(subSlice3);
                            LogUtil.d("mHistoryOfRecords index:%d values:%s", objArr);
                            int i24 = 0;
                            while (i24 < subSlice3.length / i21) {
                                long longParse3 = getLongParse(subSlice3, i24 * 4, i21);
                                long restoreZoneUTC = DateUtil.restoreZoneUTC(longParse3);
                                int i25 = i24;
                                this.mHistoryOfRecords.add(new HistoryOfRecord(longParse3, restoreZoneUTC));
                                LogUtil.d("mHistoryOfRecords index:%d record:%s", Integer.valueOf(i25), Long.valueOf(longParse3));
                                i24 = i25 + 1;
                                i21 = 4;
                            }
                            i23++;
                            c = 1;
                            i11 = 2;
                            i21 = 4;
                            c2 = 0;
                            i12 = 3;
                        }
                        onHistoryOfHRRecordReceived(bluetoothDevice, this.mHistoryOfRecords);
                        this.mHistoryOfRecords.clear();
                        this.mPackages.clear();
                    }
                    return;
                }
                try {
                    if (intValue == 34) {
                        i = 4;
                        i2 = 3;
                        i3 = intValue;
                    } else {
                        if (intValue != 35) {
                            if (intValue == 36) {
                                if (this.mRespiratoryRatesRecords == null) {
                                    this.mRespiratoryRatesRecords = new ArrayList();
                                }
                                long longParse4 = getLongParse(value, 3, 4);
                                if (longParse4 != END_TAG) {
                                    this.mPackages.add(data);
                                } else {
                                    for (int i26 = 0; i26 < this.mPackages.size(); i26++) {
                                        byte[] subSlice4 = subSlice(3, this.mPackages.get(i26).getValue());
                                        LogUtil.d("mRespiratoryRatesRecords index:%d values:%s", Integer.valueOf(i26), ParserUtils.parse(subSlice4));
                                        int i27 = 0;
                                        while (i27 < subSlice4.length / 4) {
                                            long longParse5 = getLongParse(subSlice4, i27 * 4, 4);
                                            this.mRespiratoryRatesRecords.add(new HistoryOfRecord(longParse5, DateUtil.restoreZoneUTC(longParse5)));
                                            i27++;
                                            longParse4 = longParse4;
                                        }
                                    }
                                    onHistoryOfRRRecordReceived(bluetoothDevice, this.mRespiratoryRatesRecords);
                                    LogUtil.d("onHistoryOfRRRecordReceived size:%d", Integer.valueOf(this.mRespiratoryRatesRecords.size()));
                                    this.mRespiratoryRatesRecords.clear();
                                    this.mPackages.clear();
                                }
                                return;
                            }
                            if (intValue == 37) {
                                i4 = 4;
                                i5 = 3;
                                i6 = intValue;
                            } else {
                                if (intValue != 38) {
                                    if (intValue == 55) {
                                        if (value[1] > 6) {
                                            int intParse = getIntParse(value, 3, 1);
                                            if (value[1] <= 8) {
                                                return;
                                            }
                                            onBloodOxygenReceived(bluetoothDevice, intParse, String.valueOf(getIntParse(value, 4, 1)), getIntParse(value, 5, 1), getIntParse(value, 6, 1), getIntParse(value, 7, 1));
                                            return;
                                        }
                                        return;
                                    }
                                    int i28 = 4;
                                    if (intValue == 56) {
                                        onTemperatureReceived(bluetoothDevice, getIntParse(value, 3, 2) / 10.0f, getIntParse(value, 5, 2) / 10.0f, getIntParse(value, 7, 2) / 10.0f);
                                        return;
                                    }
                                    if (intValue == 64) {
                                        i7 = 3;
                                        i8 = intValue;
                                    } else {
                                        if (intValue != 65) {
                                            if (intValue == 66) {
                                                i9 = 3;
                                                i10 = intValue;
                                            } else {
                                                if (intValue != 67) {
                                                    if (intValue == 70) {
                                                        onHeartRateStatusReceived(bluetoothDevice, getIntParse(value, 4, 1), getIntParse(value, 5, 1), getIntParse(value, 6, 1));
                                                        return;
                                                    }
                                                    if (intValue == 73) {
                                                        try {
                                                            onHistorySingleRecordReceived(bluetoothDevice, DateUtil.restoreZoneUTC(getLongParse(value, 3, 4)), getLongParse(value, 7, 3), getLongParse(value, 10, 3), getLongParse(value, 13, 3));
                                                            return;
                                                        } catch (Exception e) {
                                                            e = e;
                                                            e.printStackTrace();
                                                            return;
                                                        }
                                                    }
                                                    if (intValue == 91) {
                                                        onHeartRateAlarmReceived(bluetoothDevice, DateUtil.restoreZoneUTC(getLongParse(value, 3, 4)), getIntParse(value, 7, 1) == 1);
                                                        return;
                                                    }
                                                    if (intValue == 96) {
                                                        parseSensorRawList(bluetoothDevice, getIntParse(value, 3, 1), new Data(subSlice(4, value)));
                                                        return;
                                                    }
                                                    if (intValue == 97) {
                                                        onSensor6DFrequencyReceived(bluetoothDevice, getIntParse(value, 3, 1));
                                                        return;
                                                    }
                                                    if (intValue == 100) {
                                                        parseSensorRawList(bluetoothDevice, DateUtil.restoreZoneUTCTimeInMillis((1000 * getLongParse(value, 3, 4)) + getIntParse(value, 7, 2)), getIntParse(value, 9, 1), data);
                                                        return;
                                                    }
                                                    if (intValue == 117) {
                                                        int intParse2 = getIntParse(value, 4, 1);
                                                        if (intParse2 == 6) {
                                                            onHeartRateMaxReceived(bluetoothDevice, getIntParse(value, 5, 1));
                                                        } else if (intParse2 == 11) {
                                                            onSensor3DFrequencyReceived(bluetoothDevice, getIntParse(value, 5, 1));
                                                        } else if (intParse2 == 12) {
                                                            onSensor3DStatusReceived(bluetoothDevice, getIntParse(value, 5, 1) == 1);
                                                        } else if (intParse2 == 15) {
                                                            onHealthReceived(bluetoothDevice, getIntParse(value, 5, 1), getIntParse(value, 6, 1), getIntParse(value, 7, 1), getIntParse(value, 8, 1), getIntParse(value, 9, 1), ((float) getLongParse(value, 10, 4)) / 1000.0f, ((float) getLongParse(value, 14, 4)) / 1000.0f, ((float) getLongParse(value, 18, 4)) / 1000.0f);
                                                        }
                                                    } else {
                                                        if (intValue != 119) {
                                                            if (intValue == 120) {
                                                            }
                                                        }
                                                        if (intValue == 119 || intValue == 120) {
                                                            byte[] subSlice5 = subSlice(3, data.getValue());
                                                            for (int i29 = 0; i29 < subSlice5.length / 6; i29++) {
                                                                int i30 = i29 * 6;
                                                                onHistoryOf3DDataReceived(bluetoothDevice, new HistoryOf3D(getSInt16(subSlice5, i30), getSInt16(subSlice5, i30 + 2), getSInt16(subSlice5, i30 + 4)), intValue == 120);
                                                            }
                                                            return;
                                                        }
                                                        return;
                                                    }
                                                    return;
                                                }
                                                i9 = 3;
                                                i10 = intValue;
                                            }
                                            if (this.mSingleTapRecords == null) {
                                                this.mSingleTapRecords = new ArrayList();
                                            }
                                            if (i10 == 66) {
                                                this.mPackages.add(data);
                                            }
                                            if (i10 == 67) {
                                                int i31 = 0;
                                                while (i31 < this.mPackages.size()) {
                                                    byte[] subSlice6 = subSlice(i9, this.mPackages.get(i31).getValue());
                                                    LogUtil.d("mSingleTapRecords index:%d values:%s", Integer.valueOf(i31), ParserUtils.parse(subSlice6));
                                                    int i32 = 0;
                                                    while (i32 < subSlice6.length / i28) {
                                                        long longParse6 = getLongParse(subSlice6, i32 * 4, i28);
                                                        this.mSingleTapRecords.add(new HistoryOfRecord(longParse6, DateUtil.restoreZoneUTC(longParse6)));
                                                        i32++;
                                                        i28 = 4;
                                                    }
                                                    i31++;
                                                    i28 = 4;
                                                }
                                                onSingleTapRecordReceived(bluetoothDevice, this.mSingleTapRecords);
                                                this.mSingleTapRecords.clear();
                                                this.mPackages.clear();
                                                return;
                                            }
                                            return;
                                        }
                                        i7 = 3;
                                        i8 = intValue;
                                    }
                                    if (this.mIntervalSteps == null) {
                                        this.mIntervalSteps = new ArrayList();
                                    }
                                    if (i8 == 64) {
                                        this.mPackages.add(data);
                                    }
                                    if (i8 == 65) {
                                        int i33 = 0;
                                        while (i33 < this.mPackages.size()) {
                                            byte[] subSlice7 = subSlice(i7, this.mPackages.get(i33).getValue());
                                            LogUtil.d("mIntervalSteps index:%d values:%s", Integer.valueOf(i33), ParserUtils.parse(subSlice7));
                                            for (int i34 = 0; i34 < subSlice7.length / 8; i34++) {
                                                int i35 = i34 * 8;
                                                this.mIntervalSteps.add(new IntervalStep(DateUtil.restoreZoneUTC(getLongParse(subSlice7, i35, 4)), getIntParse(subSlice7, i35 + 4, 4)));
                                            }
                                            i33++;
                                            i7 = 3;
                                        }
                                        onIntervalStepReceived(bluetoothDevice, this.mIntervalSteps);
                                        this.mIntervalSteps.clear();
                                        this.mPackages.clear();
                                        return;
                                    }
                                    return;
                                }
                                i4 = 4;
                                i5 = 3;
                                i6 = intValue;
                            }
                            if (this.mHistoryOfRespiratoryRates == null) {
                                this.mHistoryOfRespiratoryRates = new ArrayList();
                            }
                            if (i6 == 37) {
                                if (!this.isStamp) {
                                    this.mStamp = getLongParse(value, i5, i4);
                                    this.isStamp = true;
                                }
                                this.mPackages.add(data);
                            }
                            if (i6 == 38) {
                                for (int i36 = 0; i36 < this.mPackages.size(); i36++) {
                                    byte[] subSlice8 = subSlice(7, this.mPackages.get(i36).getValue());
                                    LogUtil.d("index:%d HistoryOfRespiratoryRates mValues:%s", Integer.valueOf(i36), ParserUtils.parse(subSlice8));
                                    for (int i37 = 0; i37 < subSlice8.length / 2; i37++) {
                                        this.mHistoryOfRespiratoryRates.add(new HistoryOfRespiratoryRate(DateUtil.restoreZoneUTC(this.mStamp), getIntParse(subSlice8, i37 * 2, 2)));
                                        this.mStamp++;
                                    }
                                }
                                LogUtil.d("onHistoryOfRRDataReceived :%s", this.mHistoryOfRespiratoryRates.toString());
                                onHistoryOfRRDataReceived(bluetoothDevice, this.mHistoryOfRespiratoryRates);
                                this.mHistoryOfRespiratoryRates.clear();
                                this.mPackages.clear();
                                this.isStamp = false;
                                this.mStamp = 0L;
                                return;
                            }
                            return;
                        }
                        i = 4;
                        i2 = 3;
                        i3 = intValue;
                    }
                    if (this.mHistoryOfHeartRates == null) {
                        this.mHistoryOfHeartRates = new ArrayList();
                    }
                    if (i3 == 34) {
                        if (!this.isStamp) {
                            this.mStamp = getLongParse(value, i2, i);
                            this.isStamp = true;
                        }
                        this.mPackages.add(data);
                    }
                    if (i3 == 35) {
                        int i38 = 0;
                        while (i38 < this.mPackages.size()) {
                            byte[] subSlice9 = subSlice(i2, this.mPackages.get(i38).getValue());
                            LogUtil.d("mHistoryOfHeartRates index:%d values:%s", Integer.valueOf(i38), ParserUtils.parse(subSlice9));
                            for (int i39 = 4; i39 < subSlice9.length; i39++) {
                                this.mHistoryOfHeartRates.add(new HistoryOfHeartRate(DateUtil.restoreZoneUTC(this.mStamp), getIntParse(subSlice9, i39, 1)));
                                this.mStamp++;
                            }
                            i38++;
                            i2 = 3;
                        }
                        onHistoryOfHRDataReceived(bluetoothDevice, this.mHistoryOfHeartRates);
                        this.mHistoryOfHeartRates.clear();
                        this.mPackages.clear();
                        this.isStamp = false;
                        this.mStamp = 0L;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public void setCL833(boolean isCL833) {
        this.isCL833 = isCL833;
    }

    private int getSInt16(byte[] data, int offset) {
        return unsignedToSigned(unsignedBytesToInt(data[offset], data[offset + 1]), 16);
    }

    private int unsignedByteToInt(final byte b) {
        return b & UByte.MAX_VALUE;
    }

    private int unsignedBytesToInt(final byte b0, final byte b1) {
        return unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8);
    }

    private int unsignedToSigned(int unsigned, final int size) {
        if (((1 << (size - 1)) & unsigned) != 0) {
            return ((1 << (size - 1)) - (unsigned & ((1 << (size - 1)) - 1))) * (-1);
        }
        return unsigned;
    }

    public void clearType(int type) {
        switch (type) {
            case 2:
                List<HistoryOfSport> list = this.mHistoryOfSports;
                if (list != null) {
                    list.clear();
                    break;
                }
                break;
            case 4:
                List<HistoryOfRecord> list2 = this.mHistoryOfRecords;
                if (list2 != null) {
                    list2.clear();
                    break;
                }
                break;
            case 6:
                List<HistoryOfHeartRate> list3 = this.mHistoryOfHeartRates;
                if (list3 != null) {
                    list3.clear();
                    break;
                }
                break;
            case 8:
                List<HistoryOfRecord> list4 = this.mRespiratoryRatesRecords;
                if (list4 != null) {
                    list4.clear();
                    break;
                }
                break;
            case 16:
                List<HistoryOfRespiratoryRate> list5 = this.mHistoryOfRespiratoryRates;
                if (list5 != null) {
                    list5.clear();
                    break;
                }
                break;
            case 18:
                List<IntervalStep> list6 = this.mIntervalSteps;
                if (list6 != null) {
                    list6.clear();
                    break;
                }
                break;
            case 20:
                List<HistoryOfRecord> list7 = this.mSingleTapRecords;
                if (list7 != null) {
                    list7.clear();
                    break;
                }
                break;
            case 22:
                List<HistorySleep> list8 = this.mHistoryOfSleeps;
                if (list8 != null) {
                    list8.clear();
                    break;
                }
                break;
        }
        this.mPackages.clear();
    }

    private void parseSportHistory(final byte[] value) {
        WearReceivedDataCallback wearReceivedDataCallback = this;
        byte[] bArr = value;
        LogUtil.d("HistoryOfSport length:%d values:%s", Integer.valueOf(bArr.length), ParserUtils.parse(value));
        int i = 0;
        while (i < bArr.length / 10) {
            int offset = i * 10;
            long stamp = wearReceivedDataCallback.getLongParse(bArr, offset, 4);
            long step = wearReceivedDataCallback.getLongParse(bArr, offset + 4, 3);
            long calorie = wearReceivedDataCallback.getLongParse(bArr, offset + 7, 3);
            wearReceivedDataCallback.mHistoryOfSports.add(new HistoryOfSport(DateUtil.restoreZoneUTC(stamp), step, calorie));
            i++;
            wearReceivedDataCallback = this;
            bArr = value;
        }
    }

    private synchronized void parseSensorRawList(BluetoothDevice device, final int sequence, final Data data) {
        LogUtil.d("parseSensorRawList values:%s", ParserUtils.parse(data.getValue()));
        for (int i = 0; i < data.size() / 12; i++) {
            int offset = i * 12;
            int gyroscopeX = data.getIntValue(34, offset).intValue();
            int gyroscopeY = data.getIntValue(34, offset + 2).intValue();
            int gyroscopeZ = data.getIntValue(34, offset + 4).intValue();
            int accelerometerX = data.getIntValue(34, offset + 6).intValue();
            int accelerometerY = data.getIntValue(34, offset + 8).intValue();
            int accelerometerZ = data.getIntValue(34, offset + 10).intValue();
            onSensor6DRawDataReceived(device, 255L, sequence, gyroscopeX, gyroscopeY, gyroscopeZ, accelerometerX, accelerometerY, accelerometerZ);
        }
    }

    private synchronized void parseSensorRawList(BluetoothDevice device, final long utc, final int sequence, final Data data) {
        for (int i = 0; i < data.size() / 12; i++) {
            int offset = i * 12;
            int gyroscopeX = data.getIntValue(34, offset + 10).intValue();
            int gyroscopeY = data.getIntValue(34, offset + 12).intValue();
            int gyroscopeZ = data.getIntValue(34, offset + 14).intValue();
            int accelerometerX = data.getIntValue(34, offset + 16).intValue();
            int accelerometerY = data.getIntValue(34, offset + 18).intValue();
            int accelerometerZ = data.getIntValue(34, offset + 20).intValue();
            onSensor6DRawDataReceived(device, utc, sequence, gyroscopeX, gyroscopeY, gyroscopeZ, accelerometerX, accelerometerY, accelerometerZ);
        }
    }

    private synchronized byte[] subSlice(final int start, final byte[] value) {
        return HexUtil.subByte(value, start, value.length - 1);
    }

    private long getLongParse(byte[] bytes, int pos, int len) {
        long val = 0;
        for (int i = pos; i < len + pos; i++) {
            val = (val << 8) | (bytes[i] & 255);
        }
        return val;
    }

    private int getIntParse(byte[] bytes, int pos, int len) {
        int val = 0;
        int len2 = len + pos;
        for (int i = pos; i < len2; i++) {
            val = (val << 8) | (bytes[i] & UByte.MAX_VALUE);
        }
        return val;
    }
}
