package com.holy.mobilesecurity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.holy.mobilesecurity.R;
import com.holy.mobilesecurity.service.LocationService;
import com.holy.mobilesecurity.utils.ConstantValue;
import com.holy.mobilesecurity.utils.SpUtil;
import com.holy.mobilesecurity.utils.ToastUtil;

/**
 * Created by holy on 2016/5/8.
 */
public class SmsReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] objects = (Object[]) intent.getExtras().get("pdus");
        for (Object object: objects) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
            //获取短信号码
            String messageAddress = message.getOriginatingAddress();
            //获取短信内容
            String messageBody = message.getMessageBody();

            if(messageBody.equals("#*alarm*#")){
                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.music);
                mediaPlayer.setVolume(1f, 1f);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();

                abortBroadcast();
            }else if(messageBody.equals("#*location*#")){
                context.startService(new Intent(context, LocationService.class));

                String locationInfo = SpUtil.getString(context, ConstantValue.LOCATION_INFO, "");

                ToastUtil.show(context, locationInfo);
            }else if(messageBody.equals("#*wipedata*#")){

            }else if(messageBody.equals("#*lockscreen*#")){

            }
        }
    }

}
