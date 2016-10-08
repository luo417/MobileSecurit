package com.holy.mobilesecurity.activity;

import android.content.Intent;
import android.os.Bundle;

import com.holy.mobilesecurity.R;

public class Setup1Activity extends SetupBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);

    }

    @Override
    void showNextPage() {
        startActivity(new Intent(Setup1Activity.this, Setup2Activity.class));
        finish();

        //平移动画
        overridePendingTransition(R.anim.next_in, R.anim.next_out);
    }

    @Override
    void showPreviousPage() {
    }

}
