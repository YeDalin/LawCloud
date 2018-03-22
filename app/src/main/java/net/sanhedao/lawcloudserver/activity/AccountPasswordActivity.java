package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.sanhedao.lawcloudserver.R;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class AccountPasswordActivity extends Activity implements View.OnClickListener {

    private ImageView ivBack;
    private TextView tvPhone,tvSetPassword,tvQQ,tvWeiXin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_password);
        initView();
        initClick();
    }

    private void initView() {
        ivBack=findViewById(R.id.iv_back);
        tvPhone=findViewById(R.id.tv_phone);
        tvSetPassword=findViewById(R.id.tv_set_password);
        tvQQ=findViewById(R.id.tv_qq);
        tvWeiXin=findViewById(R.id.tv_weixin);
    }


    private void initClick(){
        ivBack.setOnClickListener(this);
        tvSetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_set_password:
                Intent intent=new Intent(this,SetPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
