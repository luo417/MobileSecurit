package com.holy.mobilesecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.holy.mobilesecurity.R;

public class AToolsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atools);

        initUI();
    }

    private void initUI() {
        TextView callAddress = (TextView) findViewById(R.id.tv_search_calladdress);
        callAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AToolsActivity.this, CallAddressActivity.class));
            }
        });
    }
}
