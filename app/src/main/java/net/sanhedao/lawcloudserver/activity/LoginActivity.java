package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.bean.LoginDataBean;
import net.sanhedao.lawcloudserver.dialog.LoadingDialog;
import net.sanhedao.lawcloudserver.http.HttpClient;
import net.sanhedao.lawcloudserver.http.HttpUrls;
import net.sanhedao.lawcloudserver.http.RequestParameters;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;

/**
 * Created by Administrator on 2018/1/31 0031.
 *
 * 登录
 *
 * @author ye
 *
 */

public class LoginActivity extends Activity implements View.OnClickListener{


    private EditText etAccount,etPassword;
    private TextView tvLogin,tvForgot,tvRegister;
    private ImageView ivWeixin,ivQQ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initClick();
    }

    private void initView() {
        etAccount=findViewById(R.id.et_account);
        etPassword=findViewById(R.id.et_password);
        tvLogin=findViewById(R.id.tv_login);
        tvForgot=findViewById(R.id.tv_forgot_password);
        tvRegister=findViewById(R.id.tv_register);
        ivWeixin=findViewById(R.id.iv_weixin);
        ivQQ=findViewById(R.id.iv_qq);
    }

    private void initClick(){
        tvForgot.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        ivWeixin.setClickable(true);
        ivQQ.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_forgot_password:
                Intent intent1=new Intent(this,ForgotPasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_register:
                Intent intent2=new Intent(this,RegisterActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_login:
                if(isLogin()){
                    loginHttp();
                }
                break;
            case R.id.iv_weixin:

                break;
            case R.id.iv_qq:

                break;
        }
    }


    //登录网络访问
    private RequestParameters loginData(){
        RequestParameters parameters=new RequestParameters();
        parameters.put("passWord",etPassword.getText().toString());
        parameters.put("userName",etAccount.getText().toString());
        return parameters;
    }
    private void loginHttp(){

        final LoadingDialog dialog=new LoadingDialog(this);
        dialog.show();


        final String LOGIN_URL= HttpUrls.HTTP_URL+"User.login";

        HttpClient httpClient=new HttpClient();
        httpClient.post("登录网络访问", LOGIN_URL, loginData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                dialog.dismiss();

                if(!result.equals("Error")){

                    Gson gson=new Gson();
                    LoginDataBean bean=gson.fromJson(result,LoginDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()== 11300){
                            keepUserData(bean.getData());
                            if(bean.getData().getStatus().equals("2")){


                                Toast.makeText(LoginActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();

                                Intent intent3=new Intent(LoginActivity.this,HomePageActivity.class);
                                startActivity(intent3);
                            }else if(bean.getData().getStatus().equals("0")){


                                Intent intent=new Intent(LoginActivity.this,QualificationCertificationActivity.class);
                                startActivity(intent);
                            }else if(bean.getData().getStatus().equals("1")){

                                Intent intent=new Intent(LoginActivity.this,WaitActivity.class);
                                startActivity(intent);

//                                Toast.makeText(LoginActivity.this,"您的认证正在审核中，请耐心等待",Toast.LENGTH_SHORT).show();
                            }else if(bean.getData().getStatus().equals("3")){
                                Toast.makeText(LoginActivity.this,"您的认证审核失败，请重新上传资料认证",Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(LoginActivity.this,QualificationCertificationActivity.class);
                                startActivity(intent);
                            }
                        }else if(bean.getCode()==11301){
                            Toast.makeText(LoginActivity.this,R.string.account_or_password_error,Toast.LENGTH_SHORT).show();
                        }else if(bean.getCode()==11304){
                            Toast.makeText(LoginActivity.this,R.string.account_error,Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this,R.string.login_fail,Toast.LENGTH_SHORT).show();
                        }
                    }



                }else {
                    Toast.makeText(LoginActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //判断是否可以登录
    private boolean isLogin(){

        if(etAccount.getText().toString().length()!=11){

            Toast.makeText(this,R.string.phone_no_11,Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etPassword.getText().toString().length()<6){
            Toast.makeText(this,R.string.password_is_6,Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    //保存用户信息
    private void keepUserData(LoginDataBean.LoginData data){
        SharedUtils.setStringPrefs(this,"token",data.getToken());
        SharedUtils.setStringPrefs(this,"uid",data.getUid());
        SharedUtils.setStringPrefs(this,"username",data.getUsername());
        SharedUtils.setStringPrefs(this,"star",data.getStar());

        SharedUtils.setStringPrefs(this,"phone",etAccount.getText().toString());
    }
}
