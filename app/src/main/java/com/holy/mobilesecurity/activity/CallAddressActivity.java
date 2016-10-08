package com.holy.mobilesecurity.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.holy.mobilesecurity.R;

public class CallAddressActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_address);

        initUI();
    }

    private void initUI() {
        final EditText phoneNumber = (EditText) findViewById(R.id.et_phone_number);
        Button searchAddress = (Button) findViewById(R.id.btn_search);
        final TextView callAddress = (TextView) findViewById(R.id.tv_call_address);

        searchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! TextUtils.isEmpty(phoneNumber.getText().toString())){
                    String address = getAddress(phoneNumber.getText().toString());
                    callAddress.setText(address);
                }else{
                    Animation shake = AnimationUtils.loadAnimation(CallAddressActivity.this, R.anim.shake);
                    shake.setInterpolator(new Interpolator() {
                        @Override
                        public float getInterpolation(float x) {
                            return (float) Math.sin((double) x);
                        }
                    });
                    phoneNumber.startAnimation(shake);

                    //震动器
                    Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(new long[]{100, 200, 300, 400, 500, 600}, -1);
                }

            }
        });

        //为EditText设置文本框监听
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callAddress.setText(getAddress(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 获取来电归属地
     *
     * @param phoneNumber
     * @return
     */
    private String getAddress(String phoneNumber) {
        String address = "未知号码！";

        final String PATH = "data/data/com.holy.mobilesecurity/files/address.db";

        //匹配手机号码
        if(phoneNumber.matches("^1[3-8]\\d{9}$")){
            SQLiteDatabase database = SQLiteDatabase.openDatabase(PATH, null,
                    SQLiteDatabase.OPEN_READONLY);

            Cursor cursor = database.rawQuery(
                    "select location from data2 where area = (select outkey from data1 where id=?)",
                    new String[]{phoneNumber.substring(0, 7)});
            if(cursor.moveToNext()){ address = cursor.getString(0);}

            return address;
        }else if(phoneNumber.matches("^\\d+$")){
            switch (phoneNumber.length()){
                case 3:
                    return "报警电话！";
                case 4:
                    return "模拟器！";
                case 7:
                case 8:
                    return "本地号码！";
            }
        }
        return address;
    }
}
