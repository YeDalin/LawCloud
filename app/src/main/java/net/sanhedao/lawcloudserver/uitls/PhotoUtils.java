package net.sanhedao.lawcloudserver.uitls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.sanhedao.lawcloudserver.log.Logger;


/**
 * Created by Administrator on 2017/12/26 0026.
 *
 * 图片压缩
 *
 * @author 叶大林
 *
 */

public class PhotoUtils {


    public static Bitmap getFitSampleBitmap(String file_path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file_path, options);
        Logger.e("path "+file_path);
        Logger.e("111  width="+options.outWidth+" height="+options.outHeight);
        options.inSampleSize = getFitInSampleSize(width, height, options);
        Logger.e("222  width="+options.outWidth+" height="+options.outHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file_path, options);
    }

    private static int getFitInSampleSize(int reqWidth, int reqHeight, BitmapFactory.Options options) {
        int inSampleSize = 1;
        if(options.outHeight>options.outWidth){
            int a=reqWidth;
            reqWidth=reqHeight;
            reqHeight=a;
        }
        if (options.outWidth > reqWidth || options.outHeight > reqHeight) {
            int widthRatio = Math.round((float) options.outWidth / (float) reqWidth);
            int heightRatio = Math.round((float) options.outHeight / (float) reqHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }
        return inSampleSize;
    }


}
