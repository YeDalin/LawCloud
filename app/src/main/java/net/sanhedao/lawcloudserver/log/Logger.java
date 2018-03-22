package net.sanhedao.lawcloudserver.log;

import android.util.Log;

/**
 * Created by Administrator on 2018/1/31 0031.
 */

public class Logger {

    public static void e(String msg){
        Log.e("xxxx",msg);
    }

    public static void e(String tag,String msg){
        Log.e(tag,msg);
    }

}
