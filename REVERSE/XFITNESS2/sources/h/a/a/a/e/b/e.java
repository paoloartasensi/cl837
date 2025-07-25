package h.a.a.a.e.b;

import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import h.a.a.a.g.a;
import java.util.List;

/* compiled from: IDataSet */
public interface e<T extends Entry> {
    float A();

    boolean E();

    boolean G0();

    a L();

    YAxis.AxisDependency S();

    float T();

    h.a.a.a.c.e W();

    int X();

    h.a.a.a.i.e Y();

    T a(float f2, float f3);

    T a(float f2, float f3, DataSet.Rounding rounding);

    List<T> a(float f2);

    void a(h.a.a.a.c.e eVar);

    boolean a(T t);

    float a0();

    int b(int i2);

    int b(T t);

    void b(float f2, float f3);

    int b0();

    Legend.LegendForm c();

    T c(int i2);

    a d(int i2);

    int e(int i2);

    boolean e0();

    float g0();

    boolean isVisible();

    List<a> j();

    Typeface l();

    float l0();

    boolean p();

    List<Integer> p0();

    String q();

    float w();

    float y0();

    DashPathEffect z0();
}
