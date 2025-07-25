package h.a.a.a.c;

import java.text.DecimalFormat;

/* compiled from: DefaultValueFormatter */
public class c extends e {
    protected DecimalFormat a;

    public c(int i2) {
        a(i2);
    }

    public void a(int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 == 0) {
                stringBuffer.append(".");
            }
            stringBuffer.append("0");
        }
        this.a = new DecimalFormat("###,###,###,##0" + stringBuffer.toString());
    }

    public String a(float f2) {
        return this.a.format((double) f2);
    }
}
