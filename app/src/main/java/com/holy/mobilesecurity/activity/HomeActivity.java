package com.holy.mobilesecurity.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.holy.mobilesecurity.R;
import com.holy.mobilesecurity.utils.ConstantValue;
import com.holy.mobilesecurity.utils.Md5Util;
import com.holy.mobilesecurity.utils.SpUtil;
import com.holy.mobilesecurity.utils.ToastUtil;

public class HomeActivity extends Activity {

    private String[] titles = new String[]{"手机防盗","通讯卫士","软件管理",
            "进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"};
    private int[] images = new int[]{R.drawable.home_safe, R.drawable.home_callmsgsafe,
            R.drawable.home_apps, R.drawable.home_taskmanager,
            R.drawable.home_netmanager, R.drawable.home_trojan,
            R.drawable.home_sysoptimize, R.drawable.home_tools,
            R.drawable.home_settings};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        GridView functionItem = (GridView) findViewById(R.id.gv_function_item);
        functionItem.setAdapter(new MyAdapter());
        functionItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        showDialog();
                        break;
                    case 7:
                        startActivity(new Intent(HomeActivity.this, AToolsActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        break;
                }
            }
        });

    }

    private void showDialog() {
        String password = SpUtil.getString(this, ConstantValue.SAFE_PASSWORD, "");
        if(!TextUtils.isEmpty(password)){
            showInputPasswordDialog();
        }else{
            showSetPasswordDialog();
        }
    }

    /**
     * 设置密码对话框
     */
    private void showSetPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();

        //为对话框设置布局
        View view = View.inflate(this, R.layout.dialog_set_passored, null);
        alertDialog.setView(view, 0 , 0, 0, 0);
        alertDialog.show();

        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
        final EditText etConfirmPassword = (EditText) view.findViewById(R.id.et_confirm_password);

        final Button btnConfirm = (Button) view.findViewById(R.id.button_confirm);
        final Button btnCancel = (Button) view.findViewById(R.id.button_cancel);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                if(TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
                    ToastUtil.show(getApplicationContext(), "密码不能为空！");
                }else {
                    if(password.equals(confirmPassword)){
                        SpUtil.putString(getApplicationContext(), ConstantValue.SAFE_PASSWORD,
                                Md5Util.changeToMD5Code(password));
                        startActivity(new Intent(HomeActivity.this, SafeActivity.class));
                        alertDialog.dismiss();
                    }else {
                        ToastUtil.show(getApplicationContext(), "输入不一致！");
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    /**
     * 输入密码对话框
     */
    private void showInputPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();

        //为对话框设置布局
        View view = View.inflate(this, R.layout.dialog_input_passored, null);
        alertDialog.setView(view, 0 , 0, 0, 0);
        alertDialog.show();

        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);

        final Button btnConfirm = (Button) view.findViewById(R.id.button_confirm);
        final Button btnCancel = (Button) view.findViewById(R.id.button_cancel);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                if(TextUtils.isEmpty(password)){
                    ToastUtil.show(getApplicationContext(), "密码不能为空！");
                }else {
                    String initialPassword = SpUtil.getString(getApplicationContext(), ConstantValue.SAFE_PASSWORD, "");
                    if(Md5Util.changeToMD5Code(password).equals(initialPassword)){
                        startActivity(new Intent(HomeActivity.this, SafeActivity.class));
                        alertDialog.dismiss();
                    }else {
                        ToastUtil.show(getApplicationContext(), "密码错误！！");
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(HomeActivity.this, R.layout.view_home_item, null);
            ImageView image = (ImageView) view.findViewById(R.id.iv_image);
            TextView text = (TextView) view.findViewById(R.id.tv_text);
            image.setImageResource(images[position]);
            text.setText(titles[position]);
            return view;
        }
    }

}
