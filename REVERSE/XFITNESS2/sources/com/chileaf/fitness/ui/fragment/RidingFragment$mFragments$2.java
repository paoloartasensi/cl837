package com.chileaf.fitness.ui.fragment;

import com.chileaf.fitness.model.Component;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: RidingFragment.kt */
final class RidingFragment$mFragments$2 extends Lambda implements a<RidingProductFragment[]> {
    final /* synthetic */ RidingFragment this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    RidingFragment$mFragments$2(RidingFragment ridingFragment) {
        super(0);
        this.this$0 = ridingFragment;
    }

    public final RidingProductFragment[] invoke() {
        return new RidingProductFragment[]{RidingProductFragment.h0.a(new Component(this.this$0.v0()[0], this.this$0.u0()[0].intValue(), this.this$0.s0()[0].intValue())), RidingProductFragment.h0.a(new Component(this.this$0.v0()[1], this.this$0.u0()[1].intValue(), this.this$0.s0()[1].intValue()))};
    }
}
