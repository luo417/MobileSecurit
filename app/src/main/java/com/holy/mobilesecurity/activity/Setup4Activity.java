package com.holy.mobilesecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.holy.mobilesecurity.R;
import com.holy.mobilesecurity.utils.ConstantValue;
import com.holy.mobilesecurity.utils.SpUtil;

public class Setup4Activity extends SetupBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        initUI();
    }

    @Override
    void showNextPage() {
        startActivity(new Intent(Setup4Activity.this, SafeActivity.class));
        SpUtil.putBoolean(this, ConstantValue.IS_SETUPED, true);
        finish();

        //平移动画
        overridePendingTransition(R.anim.next_in, R.anim.next_out);
    }

    @Override
    void showPreviousPage() {
        startActivity(new Intent(Setup4Activity.this, Setup3Activity.class));
        finish();

        //平移动画
        overridePendingTransition(R.anim.previous_in, R.anim.previous_out);
    }

    private void initUI() {
        LinearLayout safeSwitch = (LinearLayout) findViewById(R.id.ll_safe_switch);
        final CheckBox cbSwtich = (CheckBox) findViewById(R.id.cb_safe_switch);

        boolean isOpened = SpUtil.getBoolean(getApplicationContext(), ConstantValue.SAFE_SWITCH, false);
        if(isOpened){
            cbSwtich.setChecked(true);
        }else{
            cbSwtich.setChecked(false);
        }

        safeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpened = SpUtil.getBoolean(getApplicationContext(), ConstantValue.SAFE_SWITCH, false);
                if(isOpened){
                    cbSwtich.setChecked(false);
                    SpUtil.putBoolean(getApplicationContext(), ConstantValue.SAFE_SWITCH, false);
                }else{
                    cbSwtich.setChecked(true);
                    SpUtil.putBoolean(getApplicationContext(), ConstantValue.SAFE_SWITCH, true);

                }
            }
        });
    }

}
