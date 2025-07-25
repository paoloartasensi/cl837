package aicare.net.cn.iweightlibrary.bleprofile;

import aicare.net.cn.iweightlibrary.bleprofile.b;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

/* compiled from: BleManager */
public interface a<E extends b> {
    void a();

    void a(E e);

    void a(Context context, BluetoothDevice bluetoothDevice);

    void disconnect();
}
