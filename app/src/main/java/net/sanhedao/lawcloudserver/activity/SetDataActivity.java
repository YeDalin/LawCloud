package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
 * Created by Administrator on 2018/3/7 0007.
 */

public class SetDataActivity extends Activity implements View.OnClickListener {

    private EditText etData;
    private TextView tvTitle,tvCancel,tvFinish,tvNum;

    private boolean n;//判断是编辑名字还是简介

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_data);
        initView();
        initIntent();
        initClick();
    }

    private void initView() {
        tvTitle=findViewById(R.id.tv_title);
        tvCancel=findViewById(R.id.tv_cancel);
        tvFinish=findViewById(R.id.tv_finish);
        tvNum=findViewById(R.id.tv_num);
        etData=findViewById(R.id.et_data);
    }


    private int max;
    private void initIntent(){
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        if(title!=null){

            tvTitle.setText(title);
            if(title.equals("编辑昵称")){

                String username = SharedUtils.getStringPrefs(this, "username", "");
                etData.setText(username);

                tvNum.setText(etData.getText().toString().length()+"/8");
                n=true;
                max=8;
            }else {
                String introduction = SharedUtils.getStringPrefs(this, "introduction", "");
                etData.setText(introduction);

                tvNum.setText(etData.getText().toString().length()+"/140");
                n=false;
                max=140;
            }
        }


        etData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etData.getText().toString().length()>max){
                    etData.setText(etData.getText().toString().substring(0,max));
                    etData.setSelection(etData.getText().toString().length());
                }
                tvNum.setText(etData.getText().toString().length()+"/"+max);
            }
        });


    }

    private void initClick(){
        tvCancel.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_finish:
                httpSetName();
                break;
        }
    }

    //修改昵称或简介网络访问
    private RequestParameters nameData(){

        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");

        RequestParameters parameters=new RequestParameters();
        parameters.put("token",token);
        parameters.put("uid",uid);
        if(n){
            parameters.put("tag","userName");
            parameters.put("userName",etData.getText().toString());
        }else {
            parameters.put("tag","introduction");
            parameters.put("introduction",etData.getText().toString());
        }
        return parameters;
    }
    private void httpSetName(){

        final LoadingDialog dialog=new LoadingDialog(this);
        dialog.show();

        final String SET_USER_DATA_URL= HttpUrls.HTTP_URL+"User.editByid";

        HttpClient httpClient=new HttpClient();
        httpClient.post("修改昵称或简介", SET_USER_DATA_URL, nameData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                dialog.dismiss();

                if(!result.equals("Error")){
                    Gson gson=new Gson();
                    SetUserDataBean bean=gson.fromJson(result,SetUserDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==11300){
                            if(n){
                                SharedUtils.setStringPrefs(SetDataActivity.this,"username",etData.getText().toString());
                            }else {
                                SharedUtils.setStringPrefs(SetDataActivity.this,"introduction",etData.getText().toString());
                            }
                            Toast.makeText(SetDataActivity.this,R.string.set_ok,Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }else {
                    Toast.makeText(SetDataActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
