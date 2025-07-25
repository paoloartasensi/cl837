package f.a.a.a.b;

import aicare.net.cn.iweightlibrary.entity.BleInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.Locale;

/* compiled from: BleUtils */
public class b {
    private static String a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean b(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected() || activeNetworkInfo.getState() != NetworkInfo.State.CONNECTED) {
            return false;
        }
        return true;
    }

    public static String a(Context context, BleInfo bleInfo) {
        String str;
        String str2;
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append("appName=");
        sb.append(context.getPackageName());
        sb.append("&appVersion=");
        sb.append(a(context));
        sb.append("&emailAddress=Visitor&phoneType=");
        sb.append(Build.MANUFACTURER);
        sb.append(" ");
        sb.append(Build.PRODUCT);
        sb.append("&phoneVersion=Android ");
        sb.append(Build.VERSION.RELEASE);
        sb.append(", API ");
        sb.append(Build.VERSION.SDK_INT);
        sb.append("&phoneLanguage=");
        sb.append(Locale.getDefault().getLanguage());
        sb.append("&btMacAddress=");
        String str4 = BuildConfig.FLAVOR;
        if (bleInfo == null) {
            str = str4;
        } else {
            str = bleInfo.getAddress();
        }
        sb.append(str);
        sb.append("&btVersion=");
        if (bleInfo == null) {
            str2 = str4;
        } else {
            str2 = bleInfo.getVersion();
        }
        sb.append(str2);
        sb.append("&btName=");
        if (bleInfo == null) {
            str3 = str4;
        } else {
            str3 = bleInfo.getName();
        }
        sb.append(str3);
        sb.append("&isCheck=");
        if (bleInfo != null) {
            str4 = String.valueOf(bleInfo.getIsCheck());
        }
        sb.append(str4);
        return sb.toString();
    }
}
