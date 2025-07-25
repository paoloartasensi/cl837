package h.a.a.a.c;

import java.text.DecimalFormat;

/* compiled from: DefaultAxisValueFormatter */
public class a extends e {
    protected DecimalFormat a;
    protected int b;

    public a(int i2) {
        this.b = i2;
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

    public int a() {
        return this.b;
    }
}
