package net.sanhedao.lawcloudserver.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class JiaJiTextView extends TextView {


    public JiaJiTextView(Context context) {
        super(context);
    }

    public JiaJiTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JiaJiTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#3CB2EB"));
//        抗锯齿
        paint.setAntiAlias(true);

        //        前面两个参数表示左上角的坐标，后面两个参数表示右下角的坐标,在android当中，是通过左上角和右下角来确定矩形的
        RectF rectF = new RectF(0, 0, width * 2, height * 2);
        canvas.drawArc(rectF, 180, 270, true, paint);
    }
}
