package com.holy.mobilesecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.holy.mobilesecurity.R;
import com.holy.mobilesecurity.utils.ConstantValue;
import com.holy.mobilesecurity.utils.SpUtil;
import com.holy.mobilesecurity.utils.ToastUtil;

public class Setup3Activity extends SetupBaseActivity {

    private String mSafeNumber;
    private EditText mEtSafeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        initUI();
    }

    @Override
    void showNextPage() {
        if(!TextUtils.isEmpty(mEtSafeNumber.getText().toString())){
            startActivity(new Intent(Setup3Activity.this, Setup4Activity.class));
            SpUtil.putString(getApplicationContext(), ConstantValue.SAFE_NUMBER, mSafeNumber);
            finish();

            //平移动画
            overridePendingTransition(R.anim.next_in, R.anim.next_out);
        }else{
            ToastUtil.show(getApplicationContext(), "请输入安全号码！");
        }
    }

    @Override
    void showPreviousPage() {
        startActivity(new Intent(Setup3Activity.this, Setup2Activity.class));
        finish();

        //平移动画
        overridePendingTransition(R.anim.previous_in, R.anim.previous_out);
    }

    private void initUI() {
        mEtSafeNumber = (EditText) findViewById(R.id.et_safe_number);

        String safeNumber = SpUtil.getString(getApplicationContext(), ConstantValue.SAFE_NUMBER, "");
        mEtSafeNumber.setText(safeNumber);

        Button btnChooseContact = (Button) findViewById(R.id.btn_choose_contacts);
        btnChooseContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Setup3Activity.this, ContactsActivity.class), 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSafeNumber = data.getStringExtra("number");
        mEtSafeNumber.setText(mSafeNumber);
    }

}
