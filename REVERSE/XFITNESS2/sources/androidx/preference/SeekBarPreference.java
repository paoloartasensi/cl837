package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.preference.Preference;

public class SeekBarPreference extends Preference {
    int T;
    int U;
    private int V;
    private int W;
    boolean X;
    SeekBar Y;
    private TextView Z;
    boolean a0;
    private boolean b0;
    boolean c0;
    private SeekBar.OnSeekBarChangeListener d0;
    private View.OnKeyListener e0;

    class a implements SeekBar.OnSeekBarChangeListener {
        a() {
        }

        public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
            if (z) {
                SeekBarPreference seekBarPreference = SeekBarPreference.this;
                if (seekBarPreference.c0 || !seekBarPreference.X) {
                    SeekBarPreference.this.a(seekBar);
                    return;
                }
            }
            SeekBarPreference seekBarPreference2 = SeekBarPreference.this;
            seekBarPreference2.j(i2 + seekBarPreference2.U);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            SeekBarPreference.this.X = true;
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            SeekBarPreference.this.X = false;
            int progress = seekBar.getProgress();
            SeekBarPreference seekBarPreference = SeekBarPreference.this;
            if (progress + seekBarPreference.U != seekBarPreference.T) {
                seekBarPreference.a(seekBar);
            }
        }
    }

    class b implements View.OnKeyListener {
        b() {
        }

        public boolean onKey(View view, int i2, KeyEvent keyEvent) {
            if (keyEvent.getAction() != 0) {
                return false;
            }
            if ((!SeekBarPreference.this.a0 && (i2 == 21 || i2 == 22)) || i2 == 23 || i2 == 66) {
                return false;
            }
            SeekBar seekBar = SeekBarPreference.this.Y;
            if (seekBar != null) {
                return seekBar.onKeyDown(i2, keyEvent);
            }
            Log.e("SeekBarPreference", "SeekBar view is null and hence cannot be adjusted.");
            return false;
        }
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.d0 = new a();
        this.e0 = new b();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SeekBarPreference, i2, i3);
        this.U = obtainStyledAttributes.getInt(R$styleable.SeekBarPreference_min, 0);
        g(obtainStyledAttributes.getInt(R$styleable.SeekBarPreference_android_max, 100));
        h(obtainStyledAttributes.getInt(R$styleable.SeekBarPreference_seekBarIncrement, 0));
        this.a0 = obtainStyledAttributes.getBoolean(R$styleable.SeekBarPreference_adjustable, true);
        this.b0 = obtainStyledAttributes.getBoolean(R$styleable.SeekBarPreference_showSeekBarValue, false);
        this.c0 = obtainStyledAttributes.getBoolean(R$styleable.SeekBarPreference_updatesContinuously, false);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public Parcelable D() {
        Parcelable D = super.D();
        if (v()) {
            return D;
        }
        SavedState savedState = new SavedState(D);
        savedState.e = this.T;
        savedState.f757f = this.U;
        savedState.f758g = this.V;
        return savedState;
    }

    public void a(l lVar) {
        super.a(lVar);
        lVar.itemView.setOnKeyListener(this.e0);
        this.Y = (SeekBar) lVar.a(R$id.seekbar);
        TextView textView = (TextView) lVar.a(R$id.seekbar_value);
        this.Z = textView;
        if (this.b0) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
            this.Z = null;
        }
        SeekBar seekBar = this.Y;
        if (seekBar == null) {
            Log.e("SeekBarPreference", "SeekBar view is null in onBindViewHolder.");
            return;
        }
        seekBar.setOnSeekBarChangeListener(this.d0);
        this.Y.setMax(this.V - this.U);
        int i2 = this.W;
        if (i2 != 0) {
            this.Y.setKeyProgressIncrement(i2);
        } else {
            this.W = this.Y.getKeyProgressIncrement();
        }
        this.Y.setProgress(this.T - this.U);
        j(this.T);
        this.Y.setEnabled(u());
    }

    /* access modifiers changed from: protected */
    public void b(Object obj) {
        if (obj == null) {
            obj = 0;
        }
        i(a(((Integer) obj).intValue()));
    }

    public final void g(int i2) {
        int i3 = this.U;
        if (i2 < i3) {
            i2 = i3;
        }
        if (i2 != this.V) {
            this.V = i2;
            y();
        }
    }

    public final void h(int i2) {
        if (i2 != this.W) {
            this.W = Math.min(this.V - this.U, Math.abs(i2));
            y();
        }
    }

    public void i(int i2) {
        a(i2, true);
    }

    /* access modifiers changed from: package-private */
    public void j(int i2) {
        TextView textView = this.Z;
        if (textView != null) {
            textView.setText(String.valueOf(i2));
        }
    }

    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        int e;

        /* renamed from: f  reason: collision with root package name */
        int f757f;

        /* renamed from: g  reason: collision with root package name */
        int f758g;

        static class a implements Parcelable.Creator<SavedState> {
            a() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        }

        SavedState(Parcel parcel) {
            super(parcel);
            this.e = parcel.readInt();
            this.f757f = parcel.readInt();
            this.f758g = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.e);
            parcel.writeInt(this.f757f);
            parcel.writeInt(this.f758g);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.seekBarPreferenceStyle);
    }

    /* access modifiers changed from: protected */
    public Object a(TypedArray typedArray, int i2) {
        return Integer.valueOf(typedArray.getInt(i2, 0));
    }

    private void a(int i2, boolean z) {
        int i3 = this.U;
        if (i2 < i3) {
            i2 = i3;
        }
        int i4 = this.V;
        if (i2 > i4) {
            i2 = i4;
        }
        if (i2 != this.T) {
            this.T = i2;
            j(i2);
            b(i2);
            if (z) {
                y();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(SeekBar seekBar) {
        int progress = this.U + seekBar.getProgress();
        if (progress == this.T) {
            return;
        }
        if (a((Object) Integer.valueOf(progress))) {
            a(progress, false);
            return;
        }
        seekBar.setProgress(this.T - this.U);
        j(this.T);
    }

    /* access modifiers changed from: protected */
    public void a(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.a(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.a(savedState.getSuperState());
        this.T = savedState.e;
        this.U = savedState.f757f;
        this.V = savedState.f758g;
        y();
    }
}
