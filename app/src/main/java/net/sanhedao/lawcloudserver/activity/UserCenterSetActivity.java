package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.bean.UserDataBean;
import net.sanhedao.lawcloudserver.http.HttpClient;
import net.sanhedao.lawcloudserver.http.HttpUrls;
import net.sanhedao.lawcloudserver.http.RequestParameters;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;

/**
 * Created by Administrator on 2018/3/7 0007.
 */

public class UserCenterSetActivity extends Activity implements View.OnClickListener {

    private ImageView ivBack;
    private TextView tvSetUserData,tvAccountAndPassword,tvCrlData,tvLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_set);
        initView();
        initClick();

        httpGetUserData();

    }

    private void initView() {
        ivBack=findViewById(R.id.iv_back);
        tvSetUserData=findViewById(R.id.tv_set_user_data);
        tvAccountAndPassword=findViewById(R.id.tv_account_and_password);
        tvCrlData=findViewById(R.id.tv_crl_data);
        tvLogout=findViewById(R.id.tv_logout);
    }



    private void initClick(){
        ivBack.setOnClickListener(this);
        tvSetUserData.setOnClickListener(this);
        tvAccountAndPassword.setOnClickListener(this);
        tvCrlData.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_set_user_data:

                Intent intent=new Intent(this,SetUserDataActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_account_and_password:

                Intent intent1=new Intent(this,AccountPasswordActivity.class);
                startActivity(intent1);

                break;
            case R.id.tv_crl_data:

                break;
            case R.id.tv_logout:

                Intent intent2=new Intent(this,LoginActivity.class);
                startActivity(intent2);

                break;
        }

    }


    //获取个人信息网络访问
    private RequestParameters userData(){
        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");

        RequestParameters parameters=new RequestParameters();
        parameters.put("token",token);
        parameters.put("uid",uid);

        return parameters;

    }
    private void httpGetUserData(){
        final String USER_DATA_URL= HttpUrls.HTTP_URL+"User.getByid";

        HttpClient httpClient=new HttpClient();
        httpClient.post("获取用户信息", USER_DATA_URL, userData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {
                if(!result.equals("Error")){

                    Gson gson=new Gson();
                    UserDataBean bean=gson.fromJson(result,UserDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==11300){
                            keepData(bean.getData());
                        }
                    }

                }
            }
        });

    }


    //保存信息
    private void keepData(UserDataBean.DataBean bean){
        SharedUtils.setStringPrefs(this,"username",bean.getUsername());
        SharedUtils.setStringPrefs(this,"address",bean.getAddress());
        SharedUtils.setStringPrefs(this,"introduction",bean.getIntroduction());
        SharedUtils.setStringPrefs(this,"sex",bean.getSex());

    }

}
