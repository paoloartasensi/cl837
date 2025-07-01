package com.chileaf.cl831.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;


public class LoadingDialog extends AppCompatDialog {

    private View mRoot;

    public LoadingDialog(Builder builder) {
        this(builder, builder.context, R.style.DialogStyle);
    }

    public LoadingDialog(Builder builder, Context context, int theme) {
        super(context, theme);
        mRoot = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView tvLoading = mRoot.findViewById(R.id.tv_loading);
        if (!TextUtils.isEmpty(builder.message)) {
            tvLoading.setVisibility(View.VISIBLE);
            tvLoading.setText(builder.message);
        } else {
            tvLoading.setVisibility(View.GONE);
        }
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mRoot);
    }

    public static Builder Builder(Activity activity) {
        return new Builder(activity);
    }

    public static final class Builder {
        private Context context;
        private String message;

        private Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public LoadingDialog build() {
            return new LoadingDialog(this);
        }
    }
}
