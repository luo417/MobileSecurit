package com.holy.mobilesecurity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.holy.mobilesecurity.utils.ConstantValue;
import com.holy.mobilesecurity.utils.SpUtil;

/**
 * Created by holy on 2016/5/7.
 */
public class BootCompletedReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //获取当前SIM卡序列号
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String recentSimSerialNumber = manager.getSimSerialNumber();

        //获取之前存储在SharedPreference中的SIM卡序列号
        String simSerialNumber = SpUtil.getString(context, ConstantValue.SIM_NUMBER, "");
        if(!recentSimSerialNumber.equals(simSerialNumber)){
            //获取安全号码
            String address = SpUtil.getString(context, ConstantValue.SAFE_NUMBER, "10086");

            //发送报警短信
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(address, null, "SIM卡改变！", null, null);
        }
    }
}
