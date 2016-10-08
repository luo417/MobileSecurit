package com.holy.mobilesecurity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.holy.mobilesecurity.R;
import com.holy.mobilesecurity.utils.ConstantValue;
import com.holy.mobilesecurity.utils.SpUtil;
import com.holy.mobilesecurity.view.SettingItemView;

/**
 * Created by Holy on 2016/5/1.
 */
public class SettingActivity extends Activity {

    private SettingItemView mSivUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUpdate();
    }

    private void initUpdate() {
        mSivUpdate = (SettingItemView) findViewById(R.id.siv_isUpdate);

        boolean isUpdate = SpUtil.getBoolean(this, ConstantValue.IS_UPDATE, true);
        if(isUpdate){
            mSivUpdate.setChecked(true);
        }else{
            mSivUpdate.setChecked(false);
        }

        mSivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSivUpdate.isChecked()){
                    mSivUpdate.setChecked(false);
                    SpUtil.putBoolean(SettingActivity.this, ConstantValue.IS_UPDATE, false);
                }else{
                    mSivUpdate.setChecked(true);
                    SpUtil.putBoolean(SettingActivity.this, ConstantValue.IS_UPDATE, true);
                }
            }
        });
    }

}
