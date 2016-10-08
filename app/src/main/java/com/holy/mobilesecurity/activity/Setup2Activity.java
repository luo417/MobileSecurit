package com.holy.mobilesecurity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;

import com.holy.mobilesecurity.R;
import com.holy.mobilesecurity.utils.ConstantValue;
import com.holy.mobilesecurity.utils.SpUtil;
import com.holy.mobilesecurity.utils.ToastUtil;
import com.holy.mobilesecurity.view.SettingItemView;

public class Setup2Activity extends SetupBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        initUI();

    }

    @Override
    void showNextPage() {
        boolean isSaveSIM = SpUtil.getBoolean(getApplicationContext(), ConstantValue.IS_SAVE_SIM, false);
        if(isSaveSIM){
            startActivity(new Intent(Setup2Activity.this, Setup3Activity.class));
            finish();

            //平移动画
            overridePendingTransition(R.anim.next_in, R.anim.next_out);
        }else{
            ToastUtil.show(getApplicationContext(), "必须绑定SIM卡！");
        }
    }

    @Override
    void showPreviousPage() {
        startActivity(new Intent(Setup2Activity.this, Setup1Activity.class));
        finish();

        //平移动画
        overridePendingTransition(R.anim.previous_in, R.anim.previous_out);
    }

    private void initUI() {
        final SettingItemView sivBindSIM = (SettingItemView) findViewById(R.id.siv_bind_SIM);

        boolean isSaveSIM = SpUtil.getBoolean(getApplicationContext(), ConstantValue.IS_SAVE_SIM, false);
        if(isSaveSIM){
            sivBindSIM.setChecked(true);
        }else{
            sivBindSIM.setChecked(false);
        }

        sivBindSIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSaveSIM = SpUtil.getBoolean(getApplicationContext(), ConstantValue.IS_SAVE_SIM, false);
                if(isSaveSIM){
                    sivBindSIM.setChecked(false);
                    SpUtil.putBoolean(getApplicationContext(), ConstantValue.IS_SAVE_SIM, false);
                    SpUtil.remove(getApplicationContext(), ConstantValue.SIM_NUMBER);
                }else{
                    sivBindSIM.setChecked(true);
                    String simSerialNumber = getSimNumber();
                    SpUtil.putBoolean(getApplicationContext(), ConstantValue.IS_SAVE_SIM, true);
                    SpUtil.putString(getApplicationContext(), ConstantValue.SIM_NUMBER, simSerialNumber);
                }
            }
        });
    }

    private String getSimNumber() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getSimSerialNumber();
    }

}
