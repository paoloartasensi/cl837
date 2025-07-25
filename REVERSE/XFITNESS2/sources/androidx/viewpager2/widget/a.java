package androidx.viewpager2.widget;

import android.animation.LayoutTransition;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/* compiled from: AnimateLayoutChangeDetector */
final class a {
    private static final ViewGroup.MarginLayoutParams b;
    private LinearLayoutManager a;

    /* renamed from: androidx.viewpager2.widget.a$a  reason: collision with other inner class name */
    /* compiled from: AnimateLayoutChangeDetector */
    class C0053a implements Comparator<int[]> {
        C0053a(a aVar) {
        }

        /* renamed from: a */
        public int compare(int[] iArr, int[] iArr2) {
            return iArr[0] - iArr2[0];
        }
    }

    static {
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-1, -1);
        b = marginLayoutParams;
        marginLayoutParams.setMargins(0, 0, 0, 0);
    }

    a(LinearLayoutManager linearLayoutManager) {
        this.a = linearLayoutManager;
    }

    private boolean b() {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        int i2;
        int i3;
        int i4;
        int i5;
        int e = this.a.e();
        if (e == 0) {
            return true;
        }
        boolean z = this.a.K() == 0;
        int[] iArr = new int[2];
        iArr[1] = 2;
        iArr[0] = e;
        int[][] iArr2 = (int[][]) Array.newInstance(int.class, iArr);
        int i6 = 0;
        while (i6 < e) {
            View d = this.a.d(i6);
            if (d != null) {
                ViewGroup.LayoutParams layoutParams = d.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                } else {
                    marginLayoutParams = b;
                }
                int[] iArr3 = iArr2[i6];
                if (z) {
                    i3 = d.getLeft();
                    i2 = marginLayoutParams.leftMargin;
                } else {
                    i3 = d.getTop();
                    i2 = marginLayoutParams.topMargin;
                }
                iArr3[0] = i3 - i2;
                int[] iArr4 = iArr2[i6];
                if (z) {
                    i5 = d.getRight();
                    i4 = marginLayoutParams.rightMargin;
                } else {
                    i5 = d.getBottom();
                    i4 = marginLayoutParams.bottomMargin;
                }
                iArr4[1] = i5 + i4;
                i6++;
            } else {
                throw new IllegalStateException("null view contained in the view hierarchy");
            }
        }
        Arrays.sort(iArr2, new C0053a(this));
        for (int i7 = 1; i7 < e; i7++) {
            if (iArr2[i7 - 1][1] != iArr2[i7][0]) {
                return false;
            }
        }
        int i8 = iArr2[0][1] - iArr2[0][0];
        if (iArr2[0][0] > 0 || iArr2[e - 1][1] < i8) {
            return false;
        }
        return true;
    }

    private boolean c() {
        int e = this.a.e();
        for (int i2 = 0; i2 < e; i2++) {
            if (a(this.a.d(i2))) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean a() {
        if ((!b() || this.a.e() <= 1) && c()) {
            return true;
        }
        return false;
    }

    private static boolean a(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            LayoutTransition layoutTransition = viewGroup.getLayoutTransition();
            if (layoutTransition != null && layoutTransition.isChangingLayout()) {
                return true;
            }
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                if (a(viewGroup.getChildAt(i2))) {
                    return true;
                }
            }
        }
        return false;
    }
}
