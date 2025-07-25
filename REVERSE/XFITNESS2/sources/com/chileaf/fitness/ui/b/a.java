package com.chileaf.fitness.ui.b;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.g;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.f;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.b.u1;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.ui.activity.DevicesActivity;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.i;

/* compiled from: DevicesAdapter.kt */
public final class a extends RecyclerView.g<d> {
    /* access modifiers changed from: private */
    public List<? extends DiscoveredDevice> a = new ArrayList();
    /* access modifiers changed from: private */
    public c b;
    private final DevicesActivity c;

    /* renamed from: com.chileaf.fitness.ui.b.a$a  reason: collision with other inner class name */
    /* compiled from: DevicesAdapter.kt */
    static final class C0067a<T> implements Observer<List<DiscoveredDevice>> {
        final /* synthetic */ a a;

        C0067a(a aVar) {
            this.a = aVar;
        }

        /* renamed from: a */
        public final void onChanged(List<DiscoveredDevice> list) {
            List a2 = this.a.a;
            i.a((Object) list, "devices");
            f.c a3 = f.a(new b(a2, list), false);
            i.a((Object) a3, "DiffUtil.calculateDiff(D…k(mData, devices), false)");
            this.a.a = list;
            a3.a((RecyclerView.g) this.a);
        }
    }

    /* compiled from: DevicesAdapter.kt */
    private static final class b extends f.b {
        private final List<DiscoveredDevice> a;
        private final List<DiscoveredDevice> b;

        public b(List<? extends DiscoveredDevice> list, List<? extends DiscoveredDevice> list2) {
            i.b(list, "oldList");
            i.b(list2, "newList");
            this.a = list;
            this.b = list2;
        }

        public boolean areContentsTheSame(int i2, int i3) {
            return this.a.get(i2).hasRssiLevelChanged();
        }

        public boolean areItemsTheSame(int i2, int i3) {
            return this.a.get(i2) == this.b.get(i3);
        }

        public int getNewListSize() {
            return this.b.size();
        }

        public int getOldListSize() {
            return this.a.size();
        }
    }

    /* compiled from: DevicesAdapter.kt */
    public interface c {
        void a(DiscoveredDevice discoveredDevice);
    }

    /* compiled from: DevicesAdapter.kt */
    public final class d extends RecyclerView.c0 {
        private final u1 a;
        final /* synthetic */ a b;

        /* renamed from: com.chileaf.fitness.ui.b.a$d$a  reason: collision with other inner class name */
        /* compiled from: DevicesAdapter.kt */
        static final class C0068a implements View.OnClickListener {
            final /* synthetic */ d e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ int f1227f;

            C0068a(d dVar, int i2) {
                this.e = dVar;
                this.f1227f = i2;
            }

            public final void onClick(View view) {
                c b = this.e.b.b;
                if (b != null) {
                    b.a((DiscoveredDevice) this.e.b.a.get(this.f1227f));
                }
            }
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(a aVar, u1 u1Var) {
            super(u1Var.c());
            i.b(u1Var, "mBinding");
            this.b = aVar;
            this.a = u1Var;
        }

        public final void a(int i2, DiscoveredDevice discoveredDevice) {
            i.b(discoveredDevice, "device");
            BluetoothDevice device = discoveredDevice.getDevice();
            i.a((Object) device, "device.device");
            int i3 = 0;
            boolean z = device.getBondState() == 12;
            AppCompatTextView appCompatTextView = this.a.B;
            i.a((Object) appCompatTextView, "mBinding.deviceName");
            appCompatTextView.setText(discoveredDevice.getName());
            AppCompatTextView appCompatTextView2 = this.a.z;
            i.a((Object) appCompatTextView2, "mBinding.deviceAddress");
            appCompatTextView2.setText(discoveredDevice.getAddress());
            AppCompatImageView appCompatImageView = this.a.A;
            i.a((Object) appCompatImageView, "mBinding.deviceBonded");
            if (!z) {
                i3 = 8;
            }
            appCompatImageView.setVisibility(i3);
            this.a.D.setImageLevel((int) (((((float) discoveredDevice.getRssi()) + 127.0f) * 100.0f) / 147.0f));
            AppCompatTextView appCompatTextView3 = this.a.C;
            i.a((Object) appCompatTextView3, "mBinding.deviceRssi");
            appCompatTextView3.setText(String.valueOf(discoveredDevice.getRssi()));
            if (this.b.b != null) {
                this.a.c().setOnClickListener(new C0068a(this, i2));
            }
            this.a.b();
        }
    }

    public a(DevicesActivity devicesActivity, com.chileaf.fitness.model.a.c cVar) {
        i.b(devicesActivity, "activity");
        i.b(cVar, "devicesLiveData");
        this.c = devicesActivity;
        cVar.observe(this.c, new C0067a(this));
    }

    public int getItemCount() {
        return this.a.size();
    }

    public final void setOnItemClickListener(c cVar) {
        i.b(cVar, "onItemClickListener");
        this.b = cVar;
    }

    public d onCreateViewHolder(ViewGroup viewGroup, int i2) {
        i.b(viewGroup, "parent");
        u1 u1Var = (u1) g.a(LayoutInflater.from(viewGroup.getContext()), (int) R$layout.item_device, viewGroup, false);
        i.a((Object) u1Var, "binding");
        return new d(this, u1Var);
    }

    /* renamed from: a */
    public void onBindViewHolder(d dVar, int i2) {
        i.b(dVar, "holder");
        dVar.a(i2, (DiscoveredDevice) this.a.get(i2));
    }
}
