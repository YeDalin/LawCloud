package net.sanhedao.lawcloudserver.view;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class RobOrderTextView extends View implements GestureDetector.OnGestureListener{


    private boolean isLong=false;

    public RobOrderTextView(Context context) {
        super(context);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        //        按下事件
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
//        down事件发生而move或则up还没发生前触发该事件；
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //        一次抬手事件
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //        拖动事件

        if(isLong){

        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
//       长按事件

        isLong=true;

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //      滑动手势
        return false;
    }


}
