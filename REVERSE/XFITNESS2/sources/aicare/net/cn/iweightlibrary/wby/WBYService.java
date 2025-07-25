package aicare.net.cn.iweightlibrary.wby;

import aicare.net.cn.iweightlibrary.bleprofile.BleProfileService;
import aicare.net.cn.iweightlibrary.entity.AlgorithmInfo;
import aicare.net.cn.iweightlibrary.entity.BodyFatData;
import aicare.net.cn.iweightlibrary.entity.DecimalInfo;
import aicare.net.cn.iweightlibrary.entity.User;
import aicare.net.cn.iweightlibrary.entity.WeightData;
import android.content.Intent;
import android.os.IBinder;

public class WBYService extends BleProfileService implements b {
    /* access modifiers changed from: private */

    /* renamed from: i  reason: collision with root package name */
    public a f5i;

    /* renamed from: j  reason: collision with root package name */
    private final BleProfileService.c f6j = new a();

    public class a extends BleProfileService.c {
        public a() {
            super();
        }

        public void a(User user) {
            if (user != null) {
                WBYService.this.f5i.a(user);
            }
        }

        public void a(byte b2) {
            WBYService.this.f5i.a((byte) 6, b2);
        }
    }

    public void b(int i2) {
        Intent intent = new Intent("aicare.net.cn.fatscale.action.SETTING_STATUS_CHANGED");
        intent.putExtra("aicare.net.cn.fatscale.extra.SETTING_STATUS", i2);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public BleProfileService.c e() {
        return this.f6j;
    }

    /* access modifiers changed from: protected */
    public aicare.net.cn.iweightlibrary.bleprofile.a<b> f() {
        a m = a.m();
        this.f5i = m;
        return m;
    }

    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        this.f5i = null;
    }

    public void onRebind(Intent intent) {
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void a(WeightData weightData) {
        Intent intent = new Intent("aicare.net.cn.fatscale.action.WEIGHT_DATA");
        intent.putExtra("aicare.net.cn.fatscale.extra.WEIGHT_DATA", weightData);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    public void a(int i2, String str) {
        Intent intent = new Intent("aicare.net.cn.fatscale.action.RESULT_CHANGED");
        intent.putExtra("aicare.net.cn.fatscale.extra.RESULT_INDEX", i2);
        intent.putExtra("aicare.net.cn.fatscale.extra.RESULT", str);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    public void a(boolean z, BodyFatData bodyFatData) {
        Intent intent = new Intent("aicare.net.cn.fatscale.action.FAT_DATA");
        intent.putExtra("aicare.net.cn.fatscale.extra.IS_HISTORY", z);
        intent.putExtra("aicare.net.cn.fatscale.extra.FAT_DATA", bodyFatData);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    public void a(byte[] bArr, byte[] bArr2, byte[] bArr3, boolean z) {
        Intent intent = new Intent("aicare.net.cn.fatscale.action.AUTH_DATA");
        intent.putExtra("aicare.net.cn.fatscale.extra.SOURCE_DATA", bArr);
        intent.putExtra("aicare.net.cn.fatscale.extra.BLE_DATA", bArr2);
        intent.putExtra("aicare.net.cn.fatscale.extra.ENCRYPT_DATA", bArr3);
        intent.putExtra("aicare.net.cn.fatscale.extra.IS_EQUALS", z);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    public void a(DecimalInfo decimalInfo) {
        Intent intent = new Intent("aicare.net.cn.fatscale.action.DECIMAL_INFO");
        intent.putExtra("aicare.net.cn.fatscale.extra.DECIMAL_INFO", decimalInfo);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    public void a(int i2) {
        Intent intent = new Intent("aicare.net.cn.fatscale.action.DID");
        intent.putExtra("aicare.net.cn.fatscale.extra.DID", i2);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    public void a(String str) {
        Intent intent = new Intent("aicare.net.cn.fatscale.action.CMD");
        intent.putExtra("aicare.net.cn.fatscale.extra.CMD", str);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    public void a(AlgorithmInfo algorithmInfo) {
        Intent intent = new Intent("aicare.net.cn.fatscale.action.ALGORITHM_INFO");
        intent.putExtra("aicare.net.cn.fatscale.extra.ALGORITHM_INFO", algorithmInfo);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }
}
