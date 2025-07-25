package h.a.a.a.c;

import com.github.mikephil.charting.components.a;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarEntry;

/* compiled from: ValueFormatter */
public abstract class e {
    public abstract String a(float f2);

    public String a(float f2, a aVar) {
        return a(f2);
    }

    public String a(BarEntry barEntry) {
        return a(barEntry.c());
    }

    public String a(float f2, BarEntry barEntry) {
        return a(f2);
    }

    public String a(Entry entry) {
        return a(entry.c());
    }

    public String a(float f2, PieEntry pieEntry) {
        return a(f2);
    }

    public String a(RadarEntry radarEntry) {
        return a(radarEntry.c());
    }

    public String a(BubbleEntry bubbleEntry) {
        return a(bubbleEntry.e());
    }

    public String a(CandleEntry candleEntry) {
        return a(candleEntry.f());
    }
}
