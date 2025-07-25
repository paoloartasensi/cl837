package com.chileaf.fitness.ui.c;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chileaf.fitness.R$color;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import java.util.List;

/* compiled from: ActionDialog */
public class c extends com.google.android.material.bottomsheet.a {
    private b l = new b(this);

    /* compiled from: ActionDialog */
    public static class a implements C0069c {
        private int a;
        private String b;
        private View.OnClickListener c;

        public a(String str) {
            this.b = str;
        }

        public int a(Context context) {
            return this.a;
        }

        public String b(Context context) {
            return this.b;
        }

        public View.OnClickListener c(Context context) {
            return this.c;
        }
    }

    /* renamed from: com.chileaf.fitness.ui.c.c$c  reason: collision with other inner class name */
    /* compiled from: ActionDialog */
    public interface C0069c {
        int a(Context context);

        String b(Context context);

        View.OnClickListener c(Context context);
    }

    /* compiled from: ActionDialog */
    public interface d {
        void a(View view, int i2);
    }

    public c(Context context) {
        super(context);
        setContentView((int) R$layout.dialog_bottom_action);
        setCanceledOnTouchOutside(true);
        TextView textView = (TextView) findViewById(R$id.tv_title);
        TextView textView2 = (TextView) findViewById(R$id.tv_cancel);
        RecyclerView recyclerView = (RecyclerView) findViewById(R$id.rv_actions);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(this.l);
        }
        if (textView2 != null) {
            textView2.setOnClickListener(new b(this));
        }
    }

    public /* synthetic */ void a(View view) {
        cancel();
    }

    public c a(List<C0069c> list, d dVar) {
        if (!list.isEmpty()) {
            this.l.setNewData(list);
            this.l.setOnActionClickListener(dVar);
        }
        return this;
    }

    /* compiled from: ActionDialog */
    private static class b extends BaseQuickAdapter<C0069c, BaseViewHolder> {
        private Dialog a;
        private d b;

        b(Dialog dialog) {
            super((int) R$layout.item_dialog_action);
            this.a = dialog;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void convert(BaseViewHolder baseViewHolder, C0069c cVar) {
            TextView textView = (TextView) baseViewHolder.getView(R$id.tv_action);
            textView.setText(cVar.b(textView.getContext()));
            int a2 = cVar.a(textView.getContext());
            if (a2 == 0) {
                a2 = androidx.core.content.a.a(textView.getContext(), (int) R$color.colorPrimary);
            }
            textView.setTextColor(a2);
            textView.setOnClickListener(new a(this, cVar, textView, baseViewHolder));
        }

        public int getItemViewType(int i2) {
            return R$layout.item_dialog_action;
        }

        public void setOnActionClickListener(d dVar) {
            this.b = dVar;
        }

        public /* synthetic */ void a(C0069c cVar, TextView textView, BaseViewHolder baseViewHolder, View view) {
            View.OnClickListener c = cVar.c(textView.getContext());
            if (c != null) {
                c.onClick(view);
            }
            d dVar = this.b;
            if (dVar != null) {
                dVar.a(view, baseViewHolder.getAdapterPosition());
            }
            this.a.dismiss();
        }
    }
}
