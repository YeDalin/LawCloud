package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.bean.SetUserDataBean;
import net.sanhedao.lawcloudserver.dialog.LoadingDialog;
import net.sanhedao.lawcloudserver.http.HttpClient;
import net.sanhedao.lawcloudserver.http.HttpUrls;
import net.sanhedao.lawcloudserver.http.RequestParameters;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class SetPasswordActivity extends Activity implements View.OnClickListener{


    private ImageView ivBack;
    private EditText etOldPassword,etNewPassword,etSurePassword;
    private TextView tvFinish;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        initVew();
        initClick();
    }

    private void initVew() {
        ivBack=findViewById(R.id.iv_back);
        etOldPassword=findViewById(R.id.et_old_password);
        etNewPassword=findViewById(R.id.et_new_password);
        etSurePassword=findViewById(R.id.et_sure_password);
        tvFinish=findViewById(R.id.tv_finish);
    }

    private void initClick(){
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                if(isSetOk()){

                    httpSetPassword();
                }
                break;
        }
    }


    private boolean isSetOk(){

        if(etOldPassword.getText().toString().length()<6){
            Toast.makeText(this,R.string.password_is_6,Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etNewPassword.getText().toString().length()<6){
            Toast.makeText(this,R.string.password_is_6,Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!etSurePassword.getText().toString().equals(etNewPassword.getText().toString())){
            Toast.makeText(this,R.string.password_new_sure_error,Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etSurePassword.getText().toString().equals(etOldPassword.getText().toString())){
            Toast.makeText(this,R.string.password_new_old,Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private RequestParameters setPasswordData(){

        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");

        RequestParameters parameters=new RequestParameters();
        parameters.put("token",token);
        parameters.put("uid",uid);
        parameters.put("tag","passWord");
        parameters.put("oldpassword",etOldPassword.getText().toString());
        parameters.put("newpassword",etNewPassword.getText().toString());
        return parameters;

    }
    private void httpSetPassword(){
        final String SET_PASSWORD_URL= HttpUrls.HTTP_URL+"User.editByid";

        final LoadingDialog dialog=new LoadingDialog(this);
        dialog.dismiss();

        HttpClient httpClient=new HttpClient();
        httpClient.post("修改密码", SET_PASSWORD_URL, setPasswordData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                dialog.dismiss();
                if(!result.equals("Error")){

                    Gson gson=new Gson();
                    SetUserDataBean bean=gson.fromJson(result,SetUserDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==11300){
                            finish();
                            Toast.makeText(SetPasswordActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SetPasswordActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    Toast.makeText(SetPasswordActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}
