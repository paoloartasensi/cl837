package androidx.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ContentLoadingProgressBar extends ProgressBar {
    long e;

    /* renamed from: f  reason: collision with root package name */
    boolean f523f;

    /* renamed from: g  reason: collision with root package name */
    boolean f524g;

    /* renamed from: h  reason: collision with root package name */
    boolean f525h;

    /* renamed from: i  reason: collision with root package name */
    private final Runnable f526i;

    /* renamed from: j  reason: collision with root package name */
    private final Runnable f527j;

    class a implements Runnable {
        a() {
        }

        public void run() {
            ContentLoadingProgressBar contentLoadingProgressBar = ContentLoadingProgressBar.this;
            contentLoadingProgressBar.f523f = false;
            contentLoadingProgressBar.e = -1;
            contentLoadingProgressBar.setVisibility(8);
        }
    }

    class b implements Runnable {
        b() {
        }

        public void run() {
            ContentLoadingProgressBar contentLoadingProgressBar = ContentLoadingProgressBar.this;
            contentLoadingProgressBar.f524g = false;
            if (!contentLoadingProgressBar.f525h) {
                contentLoadingProgressBar.e = System.currentTimeMillis();
                ContentLoadingProgressBar.this.setVisibility(0);
            }
        }
    }

    public ContentLoadingProgressBar(Context context) {
        this(context, (AttributeSet) null);
    }

    private void a() {
        removeCallbacks(this.f526i);
        removeCallbacks(this.f527j);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        a();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a();
    }

    public ContentLoadingProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.f525h = false;
        this.f526i = new a();
        this.f527j = new b();
    }
}
