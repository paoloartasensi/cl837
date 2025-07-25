package androidx.navigation;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.navigation.r;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@r.b("activity")
/* compiled from: ActivityNavigator */
public class b extends r<a> {
    private Context a;
    private Activity b;

    /* renamed from: androidx.navigation.b$b  reason: collision with other inner class name */
    /* compiled from: ActivityNavigator */
    public static final class C0039b implements r.a {
        private final int a;
        private final androidx.core.app.b b;

        public androidx.core.app.b a() {
            return this.b;
        }

        public int b() {
            return this.a;
        }
    }

    public b(Context context) {
        this.a = context;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                this.b = (Activity) context;
                return;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
    }

    public boolean c() {
        Activity activity = this.b;
        if (activity == null) {
            return false;
        }
        activity.finish();
        return true;
    }

    /* compiled from: ActivityNavigator */
    public static class a extends j {
        private Intent m;
        private String n;

        public a(r<? extends a> rVar) {
            super((r<? extends j>) rVar);
        }

        public void a(Context context, AttributeSet attributeSet) {
            super.a(context, attributeSet);
            TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R$styleable.ActivityNavigator);
            String string = obtainAttributes.getString(R$styleable.ActivityNavigator_targetPackage);
            if (string != null) {
                string = string.replace("${applicationId}", context.getPackageName());
            }
            d(string);
            String string2 = obtainAttributes.getString(R$styleable.ActivityNavigator_android_name);
            if (string2 != null) {
                if (string2.charAt(0) == '.') {
                    string2 = context.getPackageName() + string2;
                }
                a(new ComponentName(context, string2));
            }
            b(obtainAttributes.getString(R$styleable.ActivityNavigator_action));
            String string3 = obtainAttributes.getString(R$styleable.ActivityNavigator_data);
            if (string3 != null) {
                b(Uri.parse(string3));
            }
            c(obtainAttributes.getString(R$styleable.ActivityNavigator_dataPattern));
            obtainAttributes.recycle();
        }

        public final a b(String str) {
            if (this.m == null) {
                this.m = new Intent();
            }
            this.m.setAction(str);
            return this;
        }

        public final a c(String str) {
            this.n = str;
            return this;
        }

        public final a d(String str) {
            if (this.m == null) {
                this.m = new Intent();
            }
            this.m.setPackage(str);
            return this;
        }

        /* access modifiers changed from: package-private */
        public boolean h() {
            return false;
        }

        public final String i() {
            Intent intent = this.m;
            if (intent == null) {
                return null;
            }
            return intent.getAction();
        }

        public final ComponentName j() {
            Intent intent = this.m;
            if (intent == null) {
                return null;
            }
            return intent.getComponent();
        }

        public final String k() {
            return this.n;
        }

        public final Intent l() {
            return this.m;
        }

        public String toString() {
            ComponentName j2 = j();
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            if (j2 != null) {
                sb.append(" class=");
                sb.append(j2.getClassName());
            } else {
                String i2 = i();
                if (i2 != null) {
                    sb.append(" action=");
                    sb.append(i2);
                }
            }
            return sb.toString();
        }

        public final a b(Uri uri) {
            if (this.m == null) {
                this.m = new Intent();
            }
            this.m.setData(uri);
            return this;
        }

        public final a a(ComponentName componentName) {
            if (this.m == null) {
                this.m = new Intent();
            }
            this.m.setComponent(componentName);
            return this;
        }
    }

    public a a() {
        return new a(this);
    }

    public j a(a aVar, Bundle bundle, o oVar, r.a aVar2) {
        Intent intent;
        int intExtra;
        if (aVar.l() != null) {
            Intent intent2 = new Intent(aVar.l());
            if (bundle != null) {
                intent2.putExtras(bundle);
                String k = aVar.k();
                if (!TextUtils.isEmpty(k)) {
                    StringBuffer stringBuffer = new StringBuffer();
                    Matcher matcher = Pattern.compile("\\{(.+?)\\}").matcher(k);
                    while (matcher.find()) {
                        String group = matcher.group(1);
                        if (bundle.containsKey(group)) {
                            matcher.appendReplacement(stringBuffer, BuildConfig.FLAVOR);
                            stringBuffer.append(Uri.encode(bundle.get(group).toString()));
                        } else {
                            throw new IllegalArgumentException("Could not find " + group + " in " + bundle + " to fill data pattern " + k);
                        }
                    }
                    matcher.appendTail(stringBuffer);
                    intent2.setData(Uri.parse(stringBuffer.toString()));
                }
            }
            boolean z = aVar2 instanceof C0039b;
            if (z) {
                intent2.addFlags(((C0039b) aVar2).b());
            }
            if (!(this.a instanceof Activity)) {
                intent2.addFlags(268435456);
            }
            if (oVar != null && oVar.g()) {
                intent2.addFlags(536870912);
            }
            Activity activity = this.b;
            int i2 = 0;
            if (!(activity == null || (intent = activity.getIntent()) == null || (intExtra = intent.getIntExtra("android-support-navigation:ActivityNavigator:current", 0)) == 0)) {
                intent2.putExtra("android-support-navigation:ActivityNavigator:source", intExtra);
            }
            intent2.putExtra("android-support-navigation:ActivityNavigator:current", aVar.d());
            if (oVar != null) {
                intent2.putExtra("android-support-navigation:ActivityNavigator:popEnterAnim", oVar.c());
                intent2.putExtra("android-support-navigation:ActivityNavigator:popExitAnim", oVar.d());
            }
            if (z) {
                androidx.core.app.b a2 = ((C0039b) aVar2).a();
                if (a2 == null) {
                    this.a.startActivity(intent2);
                } else {
                    a2.a();
                    throw null;
                }
            } else {
                this.a.startActivity(intent2);
            }
            if (!(oVar == null || this.b == null)) {
                int a3 = oVar.a();
                int b2 = oVar.b();
                if (!(a3 == -1 && b2 == -1)) {
                    if (a3 == -1) {
                        a3 = 0;
                    }
                    if (b2 != -1) {
                        i2 = b2;
                    }
                    this.b.overridePendingTransition(a3, i2);
                }
            }
            return null;
        }
        throw new IllegalStateException("Destination " + aVar.d() + " does not have an Intent set.");
    }
}
