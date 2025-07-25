package com.chileaf.fitness.ui.fragment;

import com.chileaf.fitness.R$array;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: MainFragment.kt */
final class MainFragment$mTitles$2 extends Lambda implements a<String[]> {
    final /* synthetic */ MainFragment this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    MainFragment$mTitles$2(MainFragment mainFragment) {
        super(0);
        this.this$0 = mainFragment;
    }

    public final String[] invoke() {
        return this.this$0.y().getStringArray(R$array.main_tab);
    }
}
