package com.holy.mobilesecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.holy.mobilesecurity.R;
import com.holy.mobilesecurity.utils.ConstantValue;
import com.holy.mobilesecurity.utils.SpUtil;

/**
 * Created by Holy on 2016/5/1.
 */
public class SafeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
    }

    private void initUI() {
        boolean isSetuped = SpUtil.getBoolean(this, ConstantValue.IS_SETUPED, false);
        if(isSetuped){
            setContentView(R.layout.activity_safe);

            TextView safeNumber = (TextView) findViewById(R.id.tv_safe_number);
            ImageView isLocked = (ImageView) findViewById(R.id.iv_isLocked);

            String number = SpUtil.getString(this, ConstantValue.SAFE_NUMBER, "10086");
            safeNumber.setText(number);

            boolean safeSwitch = SpUtil.getBoolean(this, ConstantValue.SAFE_SWITCH, false);
            if (safeSwitch) {
                isLocked.setImageResource(R.drawable.lock);
            }else{
                isLocked.setImageResource(R.drawable.unlock);
            }

            TextView returnToSetup1 = (TextView) findViewById(R.id.tv_return_setup);
            returnToSetup1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SafeActivity.this, Setup1Activity.class));
                    finish();
                }
            });
        }else {
            startActivity(new Intent(SafeActivity.this, Setup1Activity.class));
            finish();
        }

    }

}
