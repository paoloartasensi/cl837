package com.chileaf.fitness.ui.fragment;

import com.chileaf.fitness.model.Component;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: WearFragment.kt */
final class WearFragment$mFragments$2 extends Lambda implements a<WearProductFragment[]> {
    final /* synthetic */ WearFragment this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    WearFragment$mFragments$2(WearFragment wearFragment) {
        super(0);
        this.this$0 = wearFragment;
    }

    public final WearProductFragment[] invoke() {
        return new WearProductFragment[]{WearProductFragment.h0.a(new Component(this.this$0.v0()[0], this.this$0.u0()[0].intValue(), this.this$0.s0()[0].intValue())), WearProductFragment.h0.a(new Component(this.this$0.v0()[1], this.this$0.u0()[1].intValue(), this.this$0.s0()[1].intValue())), WearProductFragment.h0.a(new Component(this.this$0.v0()[2], this.this$0.u0()[2].intValue(), this.this$0.s0()[2].intValue())), WearProductFragment.h0.a(new Component(this.this$0.v0()[3], this.this$0.u0()[3].intValue(), this.this$0.s0()[3].intValue()))};
    }
}
