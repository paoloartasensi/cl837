package com.afollestad.materialdialogs.internal.message;

import com.afollestad.materialdialogs.R$dimen;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: DialogContentLayout.kt */
final class DialogContentLayout$frameHorizontalMargin$2 extends Lambda implements a<Integer> {
    final /* synthetic */ DialogContentLayout this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DialogContentLayout$frameHorizontalMargin$2(DialogContentLayout dialogContentLayout) {
        super(0);
        this.this$0 = dialogContentLayout;
    }

    public final int invoke() {
        return this.this$0.getResources().getDimensionPixelSize(R$dimen.md_dialog_frame_margin_horizontal);
    }
}
