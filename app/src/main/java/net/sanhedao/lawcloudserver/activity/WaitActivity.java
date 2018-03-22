package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import net.sanhedao.lawcloudserver.R;

/**
 * Created by Administrator on 2018/3/10 0010.
 */

public class WaitActivity extends Activity implements View.OnClickListener{

    private ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        initView();
        initClick();
    }

    private void initView() {
        ivBack=findViewById(R.id.iv_back);
    }


    private void initClick(){
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
