package com.github.mikephil.charting.components;

import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import h.a.a.a.d.d;
import h.a.a.a.i.e;
import java.lang.ref.WeakReference;

public class MarkerView extends RelativeLayout implements d {
    private e e;

    /* renamed from: f  reason: collision with root package name */
    private e f1339f;

    /* renamed from: g  reason: collision with root package name */
    private WeakReference<Chart> f1340g;

    private void setupLayoutResource(int i2) {
        View inflate = LayoutInflater.from(getContext()).inflate(i2, this);
        inflate.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        inflate.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        inflate.layout(0, 0, inflate.getMeasuredWidth(), inflate.getMeasuredHeight());
    }

    public e a(float f2, float f3) {
        e offset = getOffset();
        e eVar = this.f1339f;
        eVar.f1727g = offset.f1727g;
        eVar.f1728h = offset.f1728h;
        Chart chartView = getChartView();
        float width = (float) getWidth();
        float height = (float) getHeight();
        e eVar2 = this.f1339f;
        float f4 = eVar2.f1727g;
        if (f2 + f4 < 0.0f) {
            eVar2.f1727g = -f2;
        } else if (chartView != null && f2 + width + f4 > ((float) chartView.getWidth())) {
            this.f1339f.f1727g = (((float) chartView.getWidth()) - f2) - width;
        }
        e eVar3 = this.f1339f;
        float f5 = eVar3.f1728h;
        if (f3 + f5 < 0.0f) {
            eVar3.f1728h = -f3;
        } else if (chartView != null && f3 + height + f5 > ((float) chartView.getHeight())) {
            this.f1339f.f1728h = (((float) chartView.getHeight()) - f3) - height;
        }
        return this.f1339f;
    }

    public Chart getChartView() {
        WeakReference<Chart> weakReference = this.f1340g;
        if (weakReference == null) {
            return null;
        }
        return (Chart) weakReference.get();
    }

    public e getOffset() {
        return this.e;
    }

    public void setChartView(Chart chart) {
        this.f1340g = new WeakReference<>(chart);
    }

    public void setOffset(e eVar) {
        this.e = eVar;
        if (eVar == null) {
            this.e = new e();
        }
    }

    public void a(Entry entry, d dVar) {
        measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    public void a(Canvas canvas, float f2, float f3) {
        e a = a(f2, f3);
        int save = canvas.save();
        canvas.translate(f2 + a.f1727g, f3 + a.f1728h);
        draw(canvas);
        canvas.restoreToCount(save);
    }
}
