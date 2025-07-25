package com.chileaf.fitness.ui.activity;

import com.chileaf.fitness.config.permission.PermissionsCallbackImpl;
import java.util.List;
import kotlin.jvm.b.a;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: DevicesActivity.kt */
final class DevicesActivity$onRequestLocationPermission$2 extends Lambda implements l<PermissionsCallbackImpl, kotlin.l> {
    final /* synthetic */ DevicesActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DevicesActivity$onRequestLocationPermission$2(DevicesActivity devicesActivity) {
        super(1);
        this.this$0 = devicesActivity;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((PermissionsCallbackImpl) obj);
        return kotlin.l.a;
    }

    public final void invoke(PermissionsCallbackImpl permissionsCallbackImpl) {
        i.b(permissionsCallbackImpl, "$receiver");
        permissionsCallbackImpl.a((a<kotlin.l>) new a<kotlin.l>(this) {
            final /* synthetic */ DevicesActivity$onRequestLocationPermission$2 this$0;

            {
                this.this$0 = r1;
            }

            public final void invoke() {
                j.a.a.c("permission granted", new Object[0]);
                this.this$0.this$0.r().j();
            }
        });
        permissionsCallbackImpl.a((l<? super List<String>, kotlin.l>) AnonymousClass2.INSTANCE);
        permissionsCallbackImpl.b((l<? super List<String>, kotlin.l>) new l<List<? extends String>, kotlin.l>(this) {
            final /* synthetic */ DevicesActivity$onRequestLocationPermission$2 this$0;

            {
                this.this$0 = r1;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((List<String>) (List) obj);
                return kotlin.l.a;
            }

            public final void invoke(List<String> list) {
                i.b(list, "it");
                this.this$0.this$0.s();
            }
        });
    }
}
