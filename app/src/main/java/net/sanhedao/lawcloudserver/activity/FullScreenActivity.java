package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;

/**
 * 沉浸式Activity的父类
 *
 * @author NEGIER
 * @date 2017/12/22
 */

public class FullScreenActivity extends Activity{
    private ImmersionBar mImmersionBar;
    private String TAG =this.getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);//这是为了实现沉浸式状态栏
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null){
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        }
    }
}
