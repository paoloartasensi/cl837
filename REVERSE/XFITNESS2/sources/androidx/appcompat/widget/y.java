package androidx.appcompat.widget;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.io.InputStream;

/* compiled from: ResourcesWrapper */
class y extends Resources {
    private final Resources a;

    public y(Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.a = resources;
    }

    public XmlResourceParser getAnimation(int i2) {
        return this.a.getAnimation(i2);
    }

    public boolean getBoolean(int i2) {
        return this.a.getBoolean(i2);
    }

    public int getColor(int i2) {
        return this.a.getColor(i2);
    }

    public ColorStateList getColorStateList(int i2) {
        return this.a.getColorStateList(i2);
    }

    public Configuration getConfiguration() {
        return this.a.getConfiguration();
    }

    public float getDimension(int i2) {
        return this.a.getDimension(i2);
    }

    public int getDimensionPixelOffset(int i2) {
        return this.a.getDimensionPixelOffset(i2);
    }

    public int getDimensionPixelSize(int i2) {
        return this.a.getDimensionPixelSize(i2);
    }

    public DisplayMetrics getDisplayMetrics() {
        return this.a.getDisplayMetrics();
    }

    public Drawable getDrawable(int i2) {
        return this.a.getDrawable(i2);
    }

    public Drawable getDrawableForDensity(int i2, int i3) {
        return this.a.getDrawableForDensity(i2, i3);
    }

    public float getFraction(int i2, int i3, int i4) {
        return this.a.getFraction(i2, i3, i4);
    }

    public int getIdentifier(String str, String str2, String str3) {
        return this.a.getIdentifier(str, str2, str3);
    }

    public int[] getIntArray(int i2) {
        return this.a.getIntArray(i2);
    }

    public int getInteger(int i2) {
        return this.a.getInteger(i2);
    }

    public XmlResourceParser getLayout(int i2) {
        return this.a.getLayout(i2);
    }

    public Movie getMovie(int i2) {
        return this.a.getMovie(i2);
    }

    public String getQuantityString(int i2, int i3, Object... objArr) {
        return this.a.getQuantityString(i2, i3, objArr);
    }

    public CharSequence getQuantityText(int i2, int i3) {
        return this.a.getQuantityText(i2, i3);
    }

    public String getResourceEntryName(int i2) {
        return this.a.getResourceEntryName(i2);
    }

    public String getResourceName(int i2) {
        return this.a.getResourceName(i2);
    }

    public String getResourcePackageName(int i2) {
        return this.a.getResourcePackageName(i2);
    }

    public String getResourceTypeName(int i2) {
        return this.a.getResourceTypeName(i2);
    }

    public String getString(int i2) {
        return this.a.getString(i2);
    }

    public String[] getStringArray(int i2) {
        return this.a.getStringArray(i2);
    }

    public CharSequence getText(int i2) {
        return this.a.getText(i2);
    }

    public CharSequence[] getTextArray(int i2) {
        return this.a.getTextArray(i2);
    }

    public void getValue(int i2, TypedValue typedValue, boolean z) {
        this.a.getValue(i2, typedValue, z);
    }

    public void getValueForDensity(int i2, int i3, TypedValue typedValue, boolean z) {
        this.a.getValueForDensity(i2, i3, typedValue, z);
    }

    public XmlResourceParser getXml(int i2) {
        return this.a.getXml(i2);
    }

    public TypedArray obtainAttributes(AttributeSet attributeSet, int[] iArr) {
        return this.a.obtainAttributes(attributeSet, iArr);
    }

    public TypedArray obtainTypedArray(int i2) {
        return this.a.obtainTypedArray(i2);
    }

    public InputStream openRawResource(int i2) {
        return this.a.openRawResource(i2);
    }

    public AssetFileDescriptor openRawResourceFd(int i2) {
        return this.a.openRawResourceFd(i2);
    }

    public void parseBundleExtra(String str, AttributeSet attributeSet, Bundle bundle) {
        this.a.parseBundleExtra(str, attributeSet, bundle);
    }

    public void parseBundleExtras(XmlResourceParser xmlResourceParser, Bundle bundle) {
        this.a.parseBundleExtras(xmlResourceParser, bundle);
    }

    public void updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics) {
        super.updateConfiguration(configuration, displayMetrics);
        Resources resources = this.a;
        if (resources != null) {
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }

    public Drawable getDrawable(int i2, Resources.Theme theme) {
        return this.a.getDrawable(i2, theme);
    }

    public Drawable getDrawableForDensity(int i2, int i3, Resources.Theme theme) {
        return this.a.getDrawableForDensity(i2, i3, theme);
    }

    public String getQuantityString(int i2, int i3) {
        return this.a.getQuantityString(i2, i3);
    }

    public String getString(int i2, Object... objArr) {
        return this.a.getString(i2, objArr);
    }

    public CharSequence getText(int i2, CharSequence charSequence) {
        return this.a.getText(i2, charSequence);
    }

    public void getValue(String str, TypedValue typedValue, boolean z) {
        this.a.getValue(str, typedValue, z);
    }

    public InputStream openRawResource(int i2, TypedValue typedValue) {
        return this.a.openRawResource(i2, typedValue);
    }
}
