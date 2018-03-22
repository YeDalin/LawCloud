package net.sanhedao.lawcloudserver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import net.sanhedao.lawcloudserver.R;


/**
 * 数据加载对话框
 */

public class LoadingDialog extends Dialog {

    private ImageView ivAnimation;
    private TextView tvContent;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading_v2);
        ivAnimation =  (ImageView) findViewById(R.id.iv_animation);
        tvContent = (TextView) findViewById(R.id.tv_content);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivAnimation.getDrawable();
        animationDrawable.start();
    }

    /**
     * 用这个方法必须是在Dialog已经show出来，否则会报空引用，找不到tvContent
     * @param str
     */
    public void setHintText(String str){
        tvContent.setText(str);
    }

}
