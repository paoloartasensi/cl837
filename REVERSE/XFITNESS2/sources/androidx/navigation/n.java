package androidx.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.navigation.e;
import androidx.navigation.o;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: NavInflater */
public final class n {
    private static final ThreadLocal<TypedValue> c = new ThreadLocal<>();
    private Context a;
    private s b;

    public n(Context context, s sVar) {
        this.a = context;
        this.b = sVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0049 A[Catch:{ Exception -> 0x0053, all -> 0x0051 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001b A[Catch:{ Exception -> 0x0053, all -> 0x0051 }] */
    @android.annotation.SuppressLint({"ResourceType"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.navigation.k a(int r7) {
        /*
            r6 = this;
            android.content.Context r0 = r6.a
            android.content.res.Resources r0 = r0.getResources()
            android.content.res.XmlResourceParser r1 = r0.getXml(r7)
            android.util.AttributeSet r2 = android.util.Xml.asAttributeSet(r1)
        L_0x000e:
            int r3 = r1.next()     // Catch:{ Exception -> 0x0053 }
            r4 = 2
            if (r3 == r4) goto L_0x0019
            r5 = 1
            if (r3 == r5) goto L_0x0019
            goto L_0x000e
        L_0x0019:
            if (r3 != r4) goto L_0x0049
            java.lang.String r3 = r1.getName()     // Catch:{ Exception -> 0x0053 }
            androidx.navigation.j r2 = r6.a((android.content.res.Resources) r0, (android.content.res.XmlResourceParser) r1, (android.util.AttributeSet) r2, (int) r7)     // Catch:{ Exception -> 0x0053 }
            boolean r4 = r2 instanceof androidx.navigation.k     // Catch:{ Exception -> 0x0053 }
            if (r4 == 0) goto L_0x002d
            androidx.navigation.k r2 = (androidx.navigation.k) r2     // Catch:{ Exception -> 0x0053 }
            r1.close()
            return r2
        L_0x002d:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x0053 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0053 }
            r4.<init>()     // Catch:{ Exception -> 0x0053 }
            java.lang.String r5 = "Root element <"
            r4.append(r5)     // Catch:{ Exception -> 0x0053 }
            r4.append(r3)     // Catch:{ Exception -> 0x0053 }
            java.lang.String r3 = "> did not inflate into a NavGraph"
            r4.append(r3)     // Catch:{ Exception -> 0x0053 }
            java.lang.String r3 = r4.toString()     // Catch:{ Exception -> 0x0053 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0053 }
            throw r2     // Catch:{ Exception -> 0x0053 }
        L_0x0049:
            org.xmlpull.v1.XmlPullParserException r2 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ Exception -> 0x0053 }
            java.lang.String r3 = "No start tag found"
            r2.<init>(r3)     // Catch:{ Exception -> 0x0053 }
            throw r2     // Catch:{ Exception -> 0x0053 }
        L_0x0051:
            r7 = move-exception
            goto L_0x007b
        L_0x0053:
            r2 = move-exception
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ all -> 0x0051 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0051 }
            r4.<init>()     // Catch:{ all -> 0x0051 }
            java.lang.String r5 = "Exception inflating "
            r4.append(r5)     // Catch:{ all -> 0x0051 }
            java.lang.String r7 = r0.getResourceName(r7)     // Catch:{ all -> 0x0051 }
            r4.append(r7)     // Catch:{ all -> 0x0051 }
            java.lang.String r7 = " line "
            r4.append(r7)     // Catch:{ all -> 0x0051 }
            int r7 = r1.getLineNumber()     // Catch:{ all -> 0x0051 }
            r4.append(r7)     // Catch:{ all -> 0x0051 }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x0051 }
            r3.<init>(r7, r2)     // Catch:{ all -> 0x0051 }
            throw r3     // Catch:{ all -> 0x0051 }
        L_0x007b:
            r1.close()
            goto L_0x0080
        L_0x007f:
            throw r7
        L_0x0080:
            goto L_0x007f
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.n.a(int):androidx.navigation.k");
    }

    private j a(Resources resources, XmlResourceParser xmlResourceParser, AttributeSet attributeSet, int i2) {
        int depth;
        j a2 = this.b.a(xmlResourceParser.getName()).a();
        a2.a(this.a, attributeSet);
        int depth2 = xmlResourceParser.getDepth() + 1;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || ((depth = xmlResourceParser.getDepth()) < depth2 && next == 3)) {
                return a2;
            }
            if (next == 2 && depth <= depth2) {
                String name = xmlResourceParser.getName();
                if ("argument".equals(name)) {
                    a(resources, a2, attributeSet, i2);
                } else if ("deepLink".equals(name)) {
                    a(resources, a2, attributeSet);
                } else if ("action".equals(name)) {
                    a(resources, a2, attributeSet, xmlResourceParser, i2);
                } else if ("include".equals(name) && (a2 instanceof k)) {
                    TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R$styleable.NavInclude);
                    ((k) a2).a((j) a(obtainAttributes.getResourceId(R$styleable.NavInclude_graph, 0)));
                    obtainAttributes.recycle();
                } else if (a2 instanceof k) {
                    ((k) a2).a(a(resources, xmlResourceParser, attributeSet, i2));
                }
            }
        }
        return a2;
    }

    private void a(Resources resources, j jVar, AttributeSet attributeSet, int i2) {
        TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R$styleable.NavArgument);
        String string = obtainAttributes.getString(R$styleable.NavArgument_android_name);
        if (string != null) {
            jVar.a(string, a(obtainAttributes, resources, i2));
            obtainAttributes.recycle();
            return;
        }
        throw new XmlPullParserException("Arguments must have a name");
    }

    private void a(Resources resources, Bundle bundle, AttributeSet attributeSet, int i2) {
        TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R$styleable.NavArgument);
        String string = obtainAttributes.getString(R$styleable.NavArgument_android_name);
        if (string != null) {
            e a2 = a(obtainAttributes, resources, i2);
            if (a2.c()) {
                a2.a(string, bundle);
            }
            obtainAttributes.recycle();
            return;
        }
        throw new XmlPullParserException("Arguments must have a name");
    }

    private e a(TypedArray typedArray, Resources resources, int i2) {
        e.a aVar = new e.a();
        boolean z = false;
        aVar.a(typedArray.getBoolean(R$styleable.NavArgument_nullable, false));
        TypedValue typedValue = c.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            c.set(typedValue);
        }
        String string = typedArray.getString(R$styleable.NavArgument_argType);
        int i3 = null;
        p<Integer> a2 = string != null ? p.a(string, resources.getResourcePackageName(i2)) : null;
        if (typedArray.getValue(R$styleable.NavArgument_android_defaultValue, typedValue)) {
            p<Integer> pVar = p.c;
            if (a2 == pVar) {
                int i4 = typedValue.resourceId;
                if (i4 != 0) {
                    i3 = Integer.valueOf(i4);
                } else if (typedValue.type == 16 && typedValue.data == 0) {
                    i3 = 0;
                } else {
                    throw new XmlPullParserException("unsupported value '" + typedValue.string + "' for " + a2.a() + ". Must be a reference to a resource.");
                }
            } else {
                int i5 = typedValue.resourceId;
                if (i5 != 0) {
                    if (a2 == null) {
                        a2 = pVar;
                        i3 = Integer.valueOf(i5);
                    } else {
                        throw new XmlPullParserException("unsupported value '" + typedValue.string + "' for " + a2.a() + ". You must use a \"" + p.c.a() + "\" type to reference other resources.");
                    }
                } else if (a2 == p.k) {
                    i3 = typedArray.getString(R$styleable.NavArgument_android_defaultValue);
                } else {
                    int i6 = typedValue.type;
                    if (i6 == 3) {
                        String charSequence = typedValue.string.toString();
                        if (a2 == null) {
                            a2 = p.b(charSequence);
                        }
                        i3 = a2.a(charSequence);
                    } else if (i6 == 4) {
                        a2 = a(typedValue, a2, (p) p.f747g, string, "float");
                        i3 = Float.valueOf(typedValue.getFloat());
                    } else if (i6 == 5) {
                        a2 = a(typedValue, a2, (p) p.b, string, "dimension");
                        i3 = Integer.valueOf((int) typedValue.getDimension(resources.getDisplayMetrics()));
                    } else if (i6 == 18) {
                        a2 = a(typedValue, a2, (p) p.f749i, string, "boolean");
                        if (typedValue.data != 0) {
                            z = true;
                        }
                        i3 = Boolean.valueOf(z);
                    } else if (i6 < 16 || i6 > 31) {
                        throw new XmlPullParserException("unsupported argument type " + typedValue.type);
                    } else {
                        a2 = a(typedValue, a2, (p) p.b, string, "integer");
                        i3 = Integer.valueOf(typedValue.data);
                    }
                }
            }
        }
        if (i3 != null) {
            aVar.a(i3);
        }
        if (a2 != null) {
            aVar.a((p<?>) a2);
        }
        return aVar.a();
    }

    private static p a(TypedValue typedValue, p pVar, p pVar2, String str, String str2) {
        if (pVar == null || pVar == pVar2) {
            return pVar != null ? pVar : pVar2;
        }
        throw new XmlPullParserException("Type is " + str + " but found " + str2 + ": " + typedValue.data);
    }

    private void a(Resources resources, j jVar, AttributeSet attributeSet) {
        TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R$styleable.NavDeepLink);
        String string = obtainAttributes.getString(R$styleable.NavDeepLink_uri);
        if (!TextUtils.isEmpty(string)) {
            jVar.a(string.replace("${applicationId}", this.a.getPackageName()));
            obtainAttributes.recycle();
            return;
        }
        throw new IllegalArgumentException("Every <deepLink> must include an app:uri");
    }

    private void a(Resources resources, j jVar, AttributeSet attributeSet, XmlResourceParser xmlResourceParser, int i2) {
        int depth;
        TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R$styleable.NavAction);
        int resourceId = obtainAttributes.getResourceId(R$styleable.NavAction_android_id, 0);
        d dVar = new d(obtainAttributes.getResourceId(R$styleable.NavAction_destination, 0));
        o.a aVar = new o.a();
        aVar.a(obtainAttributes.getBoolean(R$styleable.NavAction_launchSingleTop, false));
        aVar.a(obtainAttributes.getResourceId(R$styleable.NavAction_popUpTo, -1), obtainAttributes.getBoolean(R$styleable.NavAction_popUpToInclusive, false));
        aVar.a(obtainAttributes.getResourceId(R$styleable.NavAction_enterAnim, -1));
        aVar.b(obtainAttributes.getResourceId(R$styleable.NavAction_exitAnim, -1));
        aVar.c(obtainAttributes.getResourceId(R$styleable.NavAction_popEnterAnim, -1));
        aVar.d(obtainAttributes.getResourceId(R$styleable.NavAction_popExitAnim, -1));
        dVar.a(aVar.a());
        Bundle bundle = new Bundle();
        int depth2 = xmlResourceParser.getDepth() + 1;
        while (true) {
            int next = xmlResourceParser.next();
            if (next != 1 && ((depth = xmlResourceParser.getDepth()) >= depth2 || next != 3)) {
                if (next == 2 && depth <= depth2 && "argument".equals(xmlResourceParser.getName())) {
                    a(resources, bundle, attributeSet, i2);
                }
            }
        }
        if (!bundle.isEmpty()) {
            dVar.a(bundle);
        }
        jVar.a(resourceId, dVar);
        obtainAttributes.recycle();
    }
}
