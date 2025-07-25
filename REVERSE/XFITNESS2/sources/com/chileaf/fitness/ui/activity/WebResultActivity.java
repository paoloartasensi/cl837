package com.chileaf.fitness.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.b.i0;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.widget.NestedScrollWebView;
import java.util.HashMap;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.internal.i;

/* compiled from: WebResultActivity.kt */
public final class WebResultActivity extends BaseActivity<i0> {
    public static final a J = new a((f) null);
    private WebView E;
    private WebSettings F;
    private final kotlin.d G = g.a(LazyThreadSafetyMode.NONE, new WebResultActivity$mTitle$2(this));
    private final kotlin.d H = g.a(LazyThreadSafetyMode.NONE, new WebResultActivity$mUrl$2(this));
    private HashMap I;

    /* compiled from: WebResultActivity.kt */
    public static final class a {
        private a() {
        }

        public final void a(Context context, String str, String str2) {
            i.b(context, "context");
            i.b(str, "title");
            i.b(str2, "url");
            Intent intent = new Intent(context, WebResultActivity.class);
            intent.putExtra("web_title", str);
            intent.putExtra("web_url", str2);
            context.startActivity(intent);
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: WebResultActivity.kt */
    public static final class b extends WebViewClient {
        b() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            i.b(webView, "view");
            if (str != null) {
                return super.shouldOverrideUrlLoading(webView, str);
            }
            return true;
        }
    }

    /* compiled from: WebResultActivity.kt */
    public static final class c extends WebChromeClient {
        final /* synthetic */ WebResultActivity a;

        c(WebResultActivity webResultActivity) {
            this.a = webResultActivity;
        }

        public void onProgressChanged(WebView webView, int i2) {
            if (i2 == 100) {
                ProgressBar progressBar = WebResultActivity.a(this.a).z;
                i.a((Object) progressBar, "mBinding.pbProgress");
                progressBar.setAlpha(0.0f);
                ProgressBar progressBar2 = WebResultActivity.a(this.a).z;
                i.a((Object) progressBar2, "mBinding.pbProgress");
                progressBar2.setVisibility(8);
                return;
            }
            ProgressBar progressBar3 = WebResultActivity.a(this.a).z;
            i.a((Object) progressBar3, "mBinding.pbProgress");
            progressBar3.setAlpha(1.0f);
            ProgressBar progressBar4 = WebResultActivity.a(this.a).z;
            i.a((Object) progressBar4, "mBinding.pbProgress");
            progressBar4.setProgress(i2);
            ProgressBar progressBar5 = WebResultActivity.a(this.a).z;
            i.a((Object) progressBar5, "mBinding.pbProgress");
            progressBar5.setVisibility(0);
        }

        public void onReceivedTitle(WebView webView, String str) {
            i.b(str, "title");
            this.a.a(str);
        }
    }

    /* compiled from: WebResultActivity.kt */
    static final class d implements View.OnLongClickListener {
        final /* synthetic */ WebResultActivity e;

        d(WebResultActivity webResultActivity) {
            this.e = webResultActivity;
        }

        public final boolean onLongClick(View view) {
            this.e.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.e.r())));
            return true;
        }
    }

    public static final /* synthetic */ i0 a(WebResultActivity webResultActivity) {
        return (i0) webResultActivity.m();
    }

    private final String q() {
        return (String) this.G.getValue();
    }

    /* access modifiers changed from: private */
    public final String r() {
        return (String) this.H.getValue();
    }

    public View d(int i2) {
        if (this.I == null) {
            this.I = new HashMap();
        }
        View view = (View) this.I.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.I.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public BaseViewModel o() {
        return null;
    }

    public void onBackPressed() {
        WebView webView = this.E;
        if (webView == null) {
            i.d("mWebView");
            throw null;
        } else if (webView.canGoBack()) {
            WebView webView2 = this.E;
            if (webView2 != null) {
                webView2.goBack();
            } else {
                i.d("mWebView");
                throw null;
            }
        } else {
            super.onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        WebView webView = this.E;
        if (webView != null) {
            ViewGroup viewGroup = (ViewGroup) webView.getParent();
            if (viewGroup != null) {
                WebView webView2 = this.E;
                if (webView2 != null) {
                    viewGroup.removeView(webView2);
                } else {
                    i.d("mWebView");
                    throw null;
                }
            }
            WebView webView3 = this.E;
            if (webView3 != null) {
                webView3.removeAllViews();
                WebView webView4 = this.E;
                if (webView4 != null) {
                    webView4.destroy();
                    super.onDestroy();
                    return;
                }
                i.d("mWebView");
                throw null;
            }
            i.d("mWebView");
            throw null;
        }
        i.d("mWebView");
        throw null;
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_web;
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "root");
        NestedScrollWebView nestedScrollWebView = ((i0) m()).A;
        i.a((Object) nestedScrollWebView, "mBinding.webResult");
        this.E = nestedScrollWebView;
        if (nestedScrollWebView != null) {
            nestedScrollWebView.removeJavascriptInterface("searchBoxJavaBridge_");
            nestedScrollWebView.removeJavascriptInterface("accessibilityTraversal");
            nestedScrollWebView.removeJavascriptInterface("accessibility");
            WebView webView = this.E;
            if (webView != null) {
                WebSettings settings = webView.getSettings();
                i.a((Object) settings, "mWebView.settings");
                this.F = settings;
                if (settings != null) {
                    settings.setUseWideViewPort(true);
                    settings.setDatabaseEnabled(true);
                    settings.setAllowFileAccess(true);
                    settings.setAppCacheEnabled(true);
                    settings.setJavaScriptEnabled(true);
                    settings.setDomStorageEnabled(true);
                    settings.setAllowContentAccess(true);
                    settings.setLoadWithOverviewMode(true);
                    settings.setLoadsImagesAutomatically(true);
                    settings.setDefaultTextEncodingName("UTF-8");
                    settings.setCacheMode(2);
                    settings.setJavaScriptCanOpenWindowsAutomatically(true);
                    if (Build.VERSION.SDK_INT >= 21) {
                        settings.setMixedContentMode(0);
                        return;
                    }
                    return;
                }
                i.d("mWebSetting");
                throw null;
            }
            i.d("mWebView");
            throw null;
        }
        i.d("mWebView");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        a(q());
        WebView webView = this.E;
        if (webView != null) {
            webView.loadUrl(r());
            WebView webView2 = this.E;
            if (webView2 != null) {
                webView2.setWebViewClient(new b());
                WebView webView3 = this.E;
                if (webView3 != null) {
                    webView3.setWebChromeClient(new c(this));
                    WebView webView4 = this.E;
                    if (webView4 != null) {
                        webView4.setOnLongClickListener(new d(this));
                    } else {
                        i.d("mWebView");
                        throw null;
                    }
                } else {
                    i.d("mWebView");
                    throw null;
                }
            } else {
                i.d("mWebView");
                throw null;
            }
        } else {
            i.d("mWebView");
            throw null;
        }
    }
}
