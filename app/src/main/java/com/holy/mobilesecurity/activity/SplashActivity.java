package com.holy.mobilesecurity.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.holy.mobilesecurity.R;
import com.holy.mobilesecurity.utils.ConstantValue;
import com.holy.mobilesecurity.utils.SpUtil;
import com.holy.mobilesecurity.utils.StreamUtil;
import com.holy.mobilesecurity.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {

    public static final String path = "http://172.18.75.176:8080/update.json";

    public static final int UPDATE_VERSION = -1;
    public static final int ENTER_HOME = 0;
    public static final int ERROR_URL = 101;
    public static final int ERROR_IO = 102;
    public static final int ERROR_JSON = 103;

    public static final String tag = "SplashActivity";

    private TextView mTv_VersionName;
    private String mVersionName;
    private String mVersionDes;
    private int mVersionCode;
    private String mDownloadUrl;

    private Message mMessage = null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VERSION:
                    updateVersion();
                    break;
                case ENTER_HOME:
                    enterHome();
                    break;
                case ERROR_IO:
                    ToastUtil.show(SplashActivity.this, "ERROR_IO");
                    enterHome();
                    break;
                case ERROR_JSON:
                    ToastUtil.show(SplashActivity.this, "ERROR_JSON");
                    enterHome();
                    break;
                case ERROR_URL:
                    ToastUtil.show(SplashActivity.this, "ERROR_URL");
                    enterHome();
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initUI(); //初始化UI
        initData();  //初始化数据
    }

    /**
     *初始化UI
     */
    private void initUI() {
        mTv_VersionName = (TextView) findViewById(R.id.tv_version);
    }

    /**
     *  初始化数据
     */
    private void initData() {
        mTv_VersionName.setText("版本号："+getVersionName());
        mMessage = handler.obtainMessage(); //初始胡mMessage对象

        //拷贝归属地数据库
        copyDB("address.db");

        boolean isUpdate = SpUtil.getBoolean(this, ConstantValue.IS_UPDATE, true);
        if(isUpdate){
            //检测是否有可更新版本
            checkVersion();
        }else{

        }

    }

    private void copyDB(String dbName) {
        File file = new File(getFilesDir(), dbName);
        FileOutputStream out = null;
        InputStream in = null;
        try {
            in = getAssets().open(dbName);
            out = new FileOutputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            while((len=in.read(buffer))!=-1){
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 更新版本
     */
    private void updateVersion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("版本更新");
        builder.setMessage(mVersionDes);
        builder.setMessage("哈哈哈，这是最新版本！快更新吧...");
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadNewVersion();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        builder.show();
    }

    /**
     * 使用xUtils下载新版本
     */
    private void downloadNewVersion() {
        //SD卡可用
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            ToastUtil.show(this, "SD卡可用");
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+
                    File.separator + "mobileSecurity.apk";
        }
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                ToastUtil.show(SplashActivity.this, "下载成功");
                enterHome();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                ToastUtil.show(SplashActivity.this, "下载失败");
                enterHome();
            }

            @Override
            public void onStart() {
                ToastUtil.show(SplashActivity.this, "开始下载");
                super.onStart();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                ToastUtil.show(SplashActivity.this, "下载中");
                super.onLoading(total, current, isUploading);
            }
        });
    }

    /**
     * 进入主界面
     */
    private void enterHome() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }

    /**
     * 检测是否有可更新版本
     */
    private void checkVersion() {
        new Thread(){
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                try {
                    URL url = new URL(path);  //1.封装URL地址
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //2.开启链接
                    //3.基本参数设置
                    conn.setConnectTimeout(2000);
                    conn.setReadTimeout(2000);
                    conn.setRequestMethod("GET");
                    //开始请求数据
                    if(conn.getResponseCode()==200){
                        InputStream is = conn.getInputStream(); //获取网络请求到的输入流
                        String jsonString = StreamUtil.streamToString(is); //将获取到的流转换成字符串
                        //json解析
                        JSONObject jsonObject = new JSONObject(jsonString);
                        mVersionName = jsonObject.getString("versionName");
                        mVersionDes = jsonObject.getString("versionDes");
                        mVersionCode = jsonObject.getInt("versionCode");
                        mDownloadUrl = jsonObject.getString("downloadUrl");

                        if(mVersionCode>getVersionCode()){
                            mMessage.what = UPDATE_VERSION;
                        }else{
                            mMessage.what = ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    mMessage.what = ERROR_URL;
                } catch (IOException e) {
                    e.printStackTrace();
                    mMessage.what = ERROR_IO;
                } catch (JSONException e) {
                    e.printStackTrace();
                    mMessage.what = ERROR_JSON;
                } finally {
                    final long duringTime = System.currentTimeMillis()-startTime;
                    if (duringTime < 2000) {
                        try {
                            Thread.sleep(2000 - duringTime);
                            handler.sendMessage(mMessage);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

    }

    /**
     * 获取当前apk版本名
     * @return 当前版本名
     */
    private String getVersionName() {
        PackageManager packageManager = getPackageManager();  //获取包管理器对象
        try {
            //得到apk的功能清单文件:为了防止出错直接使用getPackageName()方法获得包名
            PackageInfo mPackageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return mPackageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前apk版本号
     * @return 当前版本号
     */
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();  //获取包管理器对象
        try {
            //得到apk的功能清单文件:为了防止出错直接使用getPackageName()方法获得包名
            PackageInfo mPackageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return mPackageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
