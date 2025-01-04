package com.chileaf.cl831.sample;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.android.chileaf.fitness.callback.UserInfoCallback;

/**
 * User information
 */
public class UserInfoActivity extends BaseActivity implements UserInfoCallback {

    private EditText mEtAge;
    private EditText mEtHeight;
    private EditText mEtWeight;
    private EditText mEtUserId;
    private RadioGroup mRgSex;
    private RadioButton mRbMale;
    private RadioButton mRbFemale;

    private int mAge;//age
    private int mSex;//sex
    private int mHeight;//height
    private int mWeight;//weigh
    private long mUserId;//user id (phone number)


    @Override
    protected int layoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        mEtAge = findViewById(R.id.et_age);
        mEtHeight = findViewById(R.id.et_height);
        mEtWeight = findViewById(R.id.et_weight);
        mEtUserId = findViewById(R.id.et_user_id);
        mRgSex = findViewById(R.id.rg_sex);
        mRbMale = findViewById(R.id.rb_male);
        mRbFemale = findViewById(R.id.rb_female);

        mEtAge.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mAge = Integer.parseInt(s.toString());
                }
            }
        });
        mRgSex.setOnCheckedChangeListener((group, checkedId) -> mSex = checkedId == R.id.rb_male ? 1 : 0);
        mEtHeight.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mHeight = Integer.parseInt(s.toString());
                }
            }
        });

        mEtWeight.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mWeight = Integer.parseInt(s.toString());
                }
            }
        });

        mEtUserId.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mUserId = Long.parseLong(s.toString());
                }
            }
        });
        //get user information
        findViewById(R.id.btn_get_user).setOnClickListener(view -> mManager.getUserInfo());
        //set user information
        findViewById(R.id.btn_set_user).setOnClickListener(view -> {
            if (isEmpty(mEtAge, mAge)) {
                showToast("Please input the correct age");
            } else if (isEmpty(mEtHeight, mHeight)) {
                showToast("Please input the correct height");
            } else if (isEmpty(mEtWeight, mWeight)) {
                showToast("Please input the correct weigh");
            } else if (isEmpty(mEtUserId, mUserId)) {
                showToast("Please input the correct user id");
            } else {
                mManager.setUserInfo(mAge, mSex, mWeight, mHeight, mUserId);
                showToast("Set success");
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle("User information");
        mManager.addUserInfoCallback(this);
    }

    private boolean isEmpty(EditText view, long value) {
        return view.getText().toString().isEmpty() || value == 0;
    }

    @Override
    public void onUserInfoReceived(@NonNull BluetoothDevice device, int age, int sex, int weight, int height, long userId) {
        runOnUiThread(() -> {
            mEtAge.setText(String.valueOf(age));
            mEtHeight.setText(String.valueOf(height));
            mEtWeight.setText(String.valueOf(weight));
            mEtUserId.setText(String.valueOf(userId));
            mRbMale.setChecked(sex == 1);
            mRbFemale.setChecked(sex == 0);
        });
    }

    private abstract static class SimpleTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
