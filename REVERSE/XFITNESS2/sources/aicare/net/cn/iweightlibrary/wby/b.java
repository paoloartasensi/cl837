package aicare.net.cn.iweightlibrary.wby;

import aicare.net.cn.iweightlibrary.entity.AlgorithmInfo;
import aicare.net.cn.iweightlibrary.entity.BodyFatData;
import aicare.net.cn.iweightlibrary.entity.DecimalInfo;
import aicare.net.cn.iweightlibrary.entity.WeightData;

/* compiled from: WBYManagerCallbacks */
public interface b extends aicare.net.cn.iweightlibrary.bleprofile.b {
    void a(int i2);

    void a(int i2, String str);

    void a(AlgorithmInfo algorithmInfo);

    void a(DecimalInfo decimalInfo);

    void a(WeightData weightData);

    void a(String str);

    void a(boolean z, BodyFatData bodyFatData);

    void a(byte[] bArr, byte[] bArr2, byte[] bArr3, boolean z);

    void b(int i2);
}
