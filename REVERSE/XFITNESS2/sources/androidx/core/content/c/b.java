package androidx.core.content.c;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: ComplexColorCompat */
public final class b {
    private final Shader a;
    private final ColorStateList b;
    private int c;

    private b(Shader shader, ColorStateList colorStateList, int i2) {
        this.a = shader;
        this.b = colorStateList;
        this.c = i2;
    }

    static b a(Shader shader) {
        return new b(shader, (ColorStateList) null, 0);
    }

    static b b(int i2) {
        return new b((Shader) null, (ColorStateList) null, i2);
    }

    public boolean c() {
        return this.a != null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r1.b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean d() {
        /*
            r1 = this;
            android.graphics.Shader r0 = r1.a
            if (r0 != 0) goto L_0x0010
            android.content.res.ColorStateList r0 = r1.b
            if (r0 == 0) goto L_0x0010
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.c.b.d():boolean");
    }

    public boolean e() {
        return c() || this.c != 0;
    }

    static b a(ColorStateList colorStateList) {
        return new b((Shader) null, colorStateList, colorStateList.getDefaultColor());
    }

    public Shader b() {
        return this.a;
    }

    public static b b(Resources resources, int i2, Resources.Theme theme) {
        try {
            return a(resources, i2, theme);
        } catch (Exception e) {
            Log.e("ComplexColorCompat", "Failed to inflate ComplexColor.", e);
            return null;
        }
    }

    public int a() {
        return this.c;
    }

    public void a(int i2) {
        this.c = i2;
    }

    public boolean a(int[] iArr) {
        if (d()) {
            ColorStateList colorStateList = this.b;
            int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
            if (colorForState != this.c) {
                this.c = colorForState;
                return true;
            }
        }
        return false;
    }

    private static b a(Resources resources, int i2, Resources.Theme theme) {
        int next;
        XmlResourceParser xml = resources.getXml(i2);
        AttributeSet asAttributeSet = Xml.asAttributeSet(xml);
        do {
            next = xml.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            String name = xml.getName();
            char c2 = 65535;
            int hashCode = name.hashCode();
            if (hashCode != 89650992) {
                if (hashCode == 1191572447 && name.equals("selector")) {
                    c2 = 0;
                }
            } else if (name.equals("gradient")) {
                c2 = 1;
            }
            if (c2 == 0) {
                return a(a.a(resources, (XmlPullParser) xml, asAttributeSet, theme));
            }
            if (c2 == 1) {
                return a(d.a(resources, xml, asAttributeSet, theme));
            }
            throw new XmlPullParserException(xml.getPositionDescription() + ": unsupported complex color tag " + name);
        }
        throw new XmlPullParserException("No start tag found");
    }
}
