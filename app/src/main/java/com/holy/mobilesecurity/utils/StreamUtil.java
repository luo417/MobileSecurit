package com.holy.mobilesecurity.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Holy on 2016/4/30.
 */
public class StreamUtil {

    public static String streamToString(InputStream is) {
        //1.在读取的过程中，将读取的内容储存在缓存中，然后一次性装换成String
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //2.读取流
        int len = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((len=is.read(buffer)) != -1){
                bos.write(buffer, 0, len);
            }
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
