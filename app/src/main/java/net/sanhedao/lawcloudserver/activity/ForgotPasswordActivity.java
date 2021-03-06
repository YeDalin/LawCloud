package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.bean.ForgotPasswordDataBean;
import net.sanhedao.lawcloudserver.bean.GetCodeDataBean;
import net.sanhedao.lawcloudserver.dialog.LoadingDialog;
import net.sanhedao.lawcloudserver.http.HttpClient;
import net.sanhedao.lawcloudserver.http.HttpUrls;
import net.sanhedao.lawcloudserver.http.RequestParameters;
import net.sanhedao.lawcloudserver.log.Logger;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;


/**
 * Created by Administrator on 2018/1/31 0031.
 *
 * 忘记密码
 *
 * @author ye
 *
 */


public class ForgotPasswordActivity extends Activity implements View.OnClickListener{


    private EditText etAccount,etPassword,etInputCode;
    private TextView tvFinish,tvGetCode;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
        initClick();
    }

    private void initView() {
        etAccount=findViewById(R.id.et_account);
        etPassword=findViewById(R.id.et_password);
        etInputCode=findViewById(R.id.et_input_code);
        tvFinish=findViewById(R.id.tv_finish);
        tvGetCode=findViewById(R.id.tv_get_code);
        ivBack=findViewById(R.id.iv_back);

        timeSet();
    }

    private void initClick(){
        tvGetCode.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_get_code:
                if(isGetCode()){
                    getCodeHttp();
                }
                break;
            case R.id.tv_finish:
                if(isRegister()){
                    registerHttp();
                }
                break;
        }
    }


    //判断是否满足重设密码条件
    private boolean isRegister(){

        if(etAccount.getText().toString().length()!=11){

            Toast.makeText(this,R.string.phone_no_11,Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etPassword.getText().toString().length()<6){
            Toast.makeText(this,R.string.password_is_6,Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etInputCode.getText().toString().length()==0){
            Toast.makeText(this,R.string.please_input_code,Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    //判断是否输入手机号获取验证码
    private boolean isGetCode(){

        if(etAccount.getText().toString().length()!=11){

            Toast.makeText(this,R.string.phone_no_11,Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    //获取验证码网络访问
    private RequestParameters accountData(){
        RequestParameters parameters=new RequestParameters();
        parameters.put("username",etAccount.getText().toString());
        return parameters;
    }
    private void getCodeHttp(){

        SharedUtils.setStringPrefs(this,"codeTime",getTime());
        Logger.e("验证码时间 "+getTime());

        timeSet();

        final String CODE_URL= HttpUrls.HTTP_URL+"User.getcode";

        HttpClient httpClient=new HttpClient();
        httpClient.post("获取验证码", CODE_URL, accountData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                if(!result.equals("Error")){
                    Gson gson=new Gson();
                    GetCodeDataBean bean=gson.fromJson(result,GetCodeDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==11000){
                            Toast.makeText(ForgotPasswordActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                        }else {
                            tvGetCode.setText("获取验证码");
                            tvGetCode.setClickable(true);
                            SharedUtils.setStringPrefs(ForgotPasswordActivity.this,"codeTime","0");
                            Toast.makeText(ForgotPasswordActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(ForgotPasswordActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
                    tvGetCode.setText("获取验证码");
                    tvGetCode.setClickable(true);
                    SharedUtils.setStringPrefs(ForgotPasswordActivity.this,"codeTime","0");
                }

            }
        });

    }


    //设置倒计时
    private long codeTimeCount=100;
    private void timeSet(){

        Logger.e("调取验证码");

        String codeTime = SharedUtils.getStringPrefs(this, "codeTime", "0");
        long time=Long.valueOf(codeTime);
        long timeNow=Long.valueOf(getTime());

        codeTimeCount=timeNow-time;


        Logger.e("  time= "+time+"     timeNow"+timeNow+"   codeTimeCount= "+codeTimeCount);


        if(codeTimeCount<60){
            tvGetCode.setClickable(false);
            handler.sendEmptyMessage(1001);
        }else {
            tvGetCode.setText("获取验证码");
            tvGetCode.setClickable(true);
        }


    }

    private Handler handler=new Handler(){

        int a;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1001){
                if(codeTimeCount>60){
                    tvGetCode.setText("获取验证码");
                    tvGetCode.setClickable(true);
                }else {
                    tvGetCode.setText(60-codeTimeCount+"s");
                    codeTimeCount++;
                    handler.sendEmptyMessageDelayed(1001, 1000);
                }
            }
        }
    };


    //    获取时间戳
    private String getTime() {
        long time = System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳(默认13位)
        String str = String.valueOf(time);
        return str;
    }



    //重设密码网络访问
    private RequestParameters registerData(){
        RequestParameters parameters=new RequestParameters();
        parameters.put("password",etPassword.getText().toString());
        parameters.put("username",etAccount.getText().toString());
        parameters.put("checkcode",etInputCode.getText().toString());
        return parameters;
    }
    private void registerHttp(){

        final LoadingDialog dialog=new LoadingDialog(this);
        dialog.show();

        final String REGISTER_URL=HttpUrls.HTTP_URL+"User.resetInfo";

        HttpClient httpClient=new HttpClient();
        httpClient.post("重设密码网络访问", REGISTER_URL, registerData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                dialog.dismiss();

                if(!result.equals("Error")){

                    Gson gson=new Gson();
                    ForgotPasswordDataBean bean=gson.fromJson(result,ForgotPasswordDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==11001){
                            Toast.makeText(ForgotPasswordActivity.this,R.string.ok,Toast.LENGTH_SHORT).show();
                            finish();
                        }else if(bean.getCode()==11004){
                            Toast.makeText(ForgotPasswordActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }


                }else {
                    Toast.makeText(ForgotPasswordActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
