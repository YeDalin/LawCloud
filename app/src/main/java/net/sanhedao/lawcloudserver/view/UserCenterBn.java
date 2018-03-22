package net.sanhedao.lawcloudserver.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class UserCenterBn extends View{


    public UserCenterBn(Context context) {
        super(context);
    }

    public UserCenterBn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UserCenterBn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int height = getHeight();
        int width = getWidth();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#004297"));
//        抗锯齿
        paint.setAntiAlias(true);

        Path path = new Path();
        path.moveTo(0,0);
        path.quadTo(width/2,height,width,0);


        canvas.drawPath(path,paint);

    }
}
