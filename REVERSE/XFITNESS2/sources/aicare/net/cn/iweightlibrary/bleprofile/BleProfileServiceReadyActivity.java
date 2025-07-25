package aicare.net.cn.iweightlibrary.bleprofile;

import aicare.net.cn.iweightlibrary.entity.AlgorithmInfo;
import aicare.net.cn.iweightlibrary.entity.BodyFatData;
import aicare.net.cn.iweightlibrary.entity.BroadData;
import aicare.net.cn.iweightlibrary.entity.DecimalInfo;
import aicare.net.cn.iweightlibrary.entity.WeightData;
import aicare.net.cn.iweightlibrary.wby.WBYService;
import aicare.net.cn.iweightlibrary.wby.WBYService.a;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BleProfileServiceReadyActivity<E extends WBYService.a> extends AppCompatActivity {
    private BluetoothAdapter A = null;
    private BroadcastReceiver B = new a();
    private ServiceConnection C = new b();
    /* access modifiers changed from: private */
    public Handler D = new Handler();
    /* access modifiers changed from: private */
    public Runnable E = new c();
    private Runnable F = new d();
    private final BluetoothAdapter.LeScanCallback G = new e();
    /* access modifiers changed from: private */
    public E y;
    private boolean z = false;

    class a extends BroadcastReceiver {
        a() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                BleProfileServiceReadyActivity.this.d(intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1));
            } else if ("aicare.net.cn.fatscale.action.CONNECT_STATE_CHANGED".equals(action)) {
                int intExtra = intent.getIntExtra("aicare.net.cn.fatscale.extra.CONNECT_STATE", -1);
                BleProfileServiceReadyActivity.this.b(intent.getStringExtra("aicare.net.cn.fatscale.extra.DEVICE_ADDRESS"), intExtra);
            } else if ("aicare.net.cn.fatscale.action.CONNECT_ERROR".equals(action)) {
                BleProfileServiceReadyActivity.this.a(intent.getStringExtra("aicare.net.cn.fatscale.extra.ERROR_MSG"), intent.getIntExtra("aicare.net.cn.fatscale.extra.ERROR_CODE", -1));
            } else if ("aicare.net.cn.fatscale.action.WEIGHT_DATA".equals(action)) {
                BleProfileServiceReadyActivity.this.a((WeightData) intent.getSerializableExtra("aicare.net.cn.fatscale.extra.WEIGHT_DATA"));
            } else if ("aicare.net.cn.fatscale.action.SETTING_STATUS_CHANGED".equals(action)) {
                BleProfileServiceReadyActivity.this.f(intent.getIntExtra("aicare.net.cn.fatscale.extra.SETTING_STATUS", -1));
            } else if ("aicare.net.cn.fatscale.action.RESULT_CHANGED".equals(action)) {
                BleProfileServiceReadyActivity.this.a(intent.getIntExtra("aicare.net.cn.fatscale.extra.RESULT_INDEX", -1), intent.getStringExtra("aicare.net.cn.fatscale.extra.RESULT"));
            } else if ("aicare.net.cn.fatscale.action.FAT_DATA".equals(action)) {
                BleProfileServiceReadyActivity.this.a(intent.getBooleanExtra("aicare.net.cn.fatscale.extra.IS_HISTORY", false), (BodyFatData) intent.getSerializableExtra("aicare.net.cn.fatscale.extra.FAT_DATA"));
            } else if ("aicare.net.cn.fatscale.action.AUTH_DATA".equals(action)) {
                BleProfileServiceReadyActivity.this.a(intent.getByteArrayExtra("aicare.net.cn.fatscale.extra.SOURCE_DATA"), intent.getByteArrayExtra("aicare.net.cn.fatscale.extra.BLE_DATA"), intent.getByteArrayExtra("aicare.net.cn.fatscale.extra.ENCRYPT_DATA"), intent.getBooleanExtra("aicare.net.cn.fatscale.extra.IS_EQUALS", false));
            } else if ("aicare.net.cn.fatscale.action.DID".equals(action)) {
                BleProfileServiceReadyActivity.this.e(intent.getIntExtra("aicare.net.cn.fatscale.extra.DID", -1));
            } else if ("aicare.net.cn.fatscale.action.DECIMAL_INFO".equals(action)) {
                BleProfileServiceReadyActivity.this.a((DecimalInfo) intent.getSerializableExtra("aicare.net.cn.fatscale.extra.DECIMAL_INFO"));
            } else if ("aicare.net.cn.fatscale.action.CMD".equals(action)) {
                BleProfileServiceReadyActivity.this.b(intent.getStringExtra("aicare.net.cn.fatscale.extra.CMD"));
            } else if ("aicare.net.cn.fatscale.action.ALGORITHM_INFO".equals(action)) {
                BleProfileServiceReadyActivity.this.a((AlgorithmInfo) intent.getSerializableExtra("aicare.net.cn.fatscale.extra.ALGORITHM_INFO"));
            }
        }
    }

    class b implements ServiceConnection {
        b() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            WBYService.a aVar = (WBYService.a) iBinder;
            WBYService.a unused = BleProfileServiceReadyActivity.this.y = aVar;
            BleProfileServiceReadyActivity.this.a(aVar);
            if (aVar.c()) {
                BleProfileServiceReadyActivity.this.b(aVar.b(), 1);
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            WBYService.a unused = BleProfileServiceReadyActivity.this.y = null;
            BleProfileServiceReadyActivity.this.o();
        }
    }

    class c implements Runnable {
        c() {
        }

        public void run() {
            BleProfileServiceReadyActivity.this.q();
        }
    }

    class d implements Runnable {
        d() {
        }

        public void run() {
            BleProfileServiceReadyActivity.this.r();
            BleProfileServiceReadyActivity.this.D.post(BleProfileServiceReadyActivity.this.E);
        }
    }

    class e implements BluetoothAdapter.LeScanCallback {

        class a implements Runnable {
            final /* synthetic */ BroadData e;

            a(BroadData broadData) {
                this.e = broadData;
            }

            public void run() {
                BleProfileServiceReadyActivity.this.a(this.e);
            }
        }

        e() {
        }

        public void onLeScan(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
            f.a.a.a.b.c.b("BleProfileServiceReadyActivity", "onLeScan");
            if (bluetoothDevice != null) {
                f.a.a.a.b.c.b("BleProfileServiceReadyActivity", "address: " + bluetoothDevice.getAddress() + "; name: " + bluetoothDevice.getName());
                f.a.a.a.b.c.b("BleProfileServiceReadyActivity", f.a.a.a.b.d.a(bArr));
                BroadData a2 = f.a.a.a.b.a.a(bluetoothDevice, i2, bArr);
                if (a2 != null) {
                    BleProfileServiceReadyActivity.this.runOnUiThread(new a(a2));
                }
            }
        }
    }

    private static IntentFilter s() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("aicare.net.cn.fatscale.action.CONNECT_STATE_CHANGED");
        intentFilter.addAction("aicare.net.cn.fatscale.action.CONNECT_ERROR");
        intentFilter.addAction("aicare.net.cn.fatscale.action.WEIGHT_DATA");
        intentFilter.addAction("aicare.net.cn.fatscale.action.SETTING_STATUS_CHANGED");
        intentFilter.addAction("aicare.net.cn.fatscale.action.RESULT_CHANGED");
        intentFilter.addAction("aicare.net.cn.fatscale.action.FAT_DATA");
        intentFilter.addAction("aicare.net.cn.fatscale.action.AUTH_DATA");
        intentFilter.addAction("aicare.net.cn.fatscale.action.DID");
        intentFilter.addAction("aicare.net.cn.fatscale.action.DECIMAL_INFO");
        intentFilter.addAction("aicare.net.cn.fatscale.action.CMD");
        intentFilter.addAction("aicare.net.cn.fatscale.action.ALGORITHM_INFO");
        return intentFilter;
    }

    private void t() {
        try {
            unbindService(this.C);
            this.y = null;
            o();
        } catch (IllegalArgumentException unused) {
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(int i2, String str);

    /* access modifiers changed from: protected */
    public abstract void a(AlgorithmInfo algorithmInfo);

    /* access modifiers changed from: protected */
    public abstract void a(BroadData broadData);

    /* access modifiers changed from: protected */
    public abstract void a(DecimalInfo decimalInfo);

    /* access modifiers changed from: protected */
    public abstract void a(WeightData weightData);

    /* access modifiers changed from: protected */
    public abstract void a(E e2);

    /* access modifiers changed from: protected */
    public abstract void a(String str, int i2);

    /* access modifiers changed from: protected */
    public abstract void a(boolean z2, BodyFatData bodyFatData);

    /* access modifiers changed from: protected */
    public void a(byte[] bArr, byte[] bArr2, byte[] bArr3, boolean z2) {
    }

    /* access modifiers changed from: protected */
    public void b(String str) {
    }

    public void c(String str) {
        r();
        a(str);
    }

    /* access modifiers changed from: protected */
    public void d(int i2) {
        if (i2 == 13) {
            E e2 = this.y;
            if (e2 != null) {
                e2.a();
            }
            r();
        }
    }

    /* access modifiers changed from: protected */
    public void e(int i2) {
    }

    /* access modifiers changed from: protected */
    public abstract void f(int i2);

    /* access modifiers changed from: protected */
    public boolean m() {
        BluetoothAdapter adapter = ((BluetoothManager) getSystemService("bluetooth")).getAdapter();
        return adapter != null && adapter.isEnabled();
    }

    /* access modifiers changed from: protected */
    public void n() {
        this.A = ((BluetoothManager) getSystemService("bluetooth")).getAdapter();
    }

    /* access modifiers changed from: protected */
    public abstract void o();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        n();
        a((String) null);
        getApplication().registerReceiver(this.B, s());
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        getApplication().unregisterReceiver(this.B);
        t();
    }

    /* access modifiers changed from: protected */
    public void p() {
        startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 2);
    }

    /* access modifiers changed from: protected */
    public void q() {
        if (!m()) {
            p();
        } else if (!this.z) {
            this.A.startLeScan(this.G);
            this.z = true;
            this.D.postDelayed(this.F, 60000);
        }
    }

    /* access modifiers changed from: protected */
    public void r() {
        this.D.removeCallbacks(this.E);
        this.D.removeCallbacks(this.F);
        if (this.z) {
            BluetoothAdapter bluetoothAdapter = this.A;
            if (bluetoothAdapter != null) {
                bluetoothAdapter.stopLeScan(this.G);
            }
            this.z = false;
        }
    }

    /* access modifiers changed from: protected */
    public void b(String str, int i2) {
        if (i2 == 0) {
            t();
        }
    }

    /* access modifiers changed from: protected */
    public void a(String str) {
        Intent intent = new Intent(this, WBYService.class);
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("aicare.net.cn.fatscale.extra.DEVICE_ADDRESS", str);
            startService(intent);
        }
        bindService(intent, this.C, 0);
    }
}
