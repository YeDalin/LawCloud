package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.bean.OrderListDataBean;
import net.sanhedao.lawcloudserver.bean.RobOrdersDataBean;
import net.sanhedao.lawcloudserver.dialog.LoadingDialog;
import net.sanhedao.lawcloudserver.http.HttpClient;
import net.sanhedao.lawcloudserver.http.HttpUrls;
import net.sanhedao.lawcloudserver.http.RequestParameters;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class RobOrderActivity extends Activity implements View.OnClickListener{

    private ImageView ivBack;
    private CircleImageView civPhoto;
    private TextView tvNickname,tvTime,tvOnline,tvOrderStatus,tvQuestion,tvOrders;
    private View vOnline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rob_order);
        initView();
        initIntent();
        initClick();
    }

    private void initView() {
        ivBack=findViewById(R.id.iv_back);
        civPhoto=findViewById(R.id.civ_photo);
        tvNickname=findViewById(R.id.tv_nickname);
        tvTime=findViewById(R.id.tv_time);
        tvOnline=findViewById(R.id.tv_online);
        tvOrderStatus=findViewById(R.id.tv_order_status);
        tvQuestion=findViewById(R.id.tv_question);
        tvOrders=findViewById(R.id.tv_orders);
        vOnline=findViewById(R.id.v_online);
    }

    private OrderListDataBean.DataBean data;
    private void initIntent(){
        Intent intent = getIntent();
        data = (OrderListDataBean.DataBean) intent.getSerializableExtra("data");
        if(data!=null){
            Picasso.with(this).load(data.getHeadportrait()).into(civPhoto);
            tvNickname.setText(data.getNickname());
            tvTime.setText(data.getCreatetime());
            tvQuestion.setText(data.getDescribe());
        }
    }

    private void initClick(){
        ivBack.setOnClickListener(this);
        tvOrders.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_orders:
                httpOrders();
                break;
        }
    }


    private RequestParameters ordersData(){
        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");

        RequestParameters parameters=new RequestParameters();
        parameters.put("token",token);
        parameters.put("uid",uid);

        if(data!=null){
            parameters.put("ordernumber",data.getOrdernumber());
        }

        return parameters;
    }
    private void httpOrders(){
        final String ORDERS_URL= HttpUrls.HTTP_URL+"Acceptorder.showAll";

        final LoadingDialog dialog=new LoadingDialog(this);
        dialog.show();

        HttpClient httpClient=new HttpClient();
        httpClient.post("抢单", ORDERS_URL, ordersData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                dialog.dismiss();

                if(!result.equals("Error")){

                    Gson gson=new Gson();
                    RobOrdersDataBean bean=gson.fromJson(result,RobOrdersDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==12300){
                            Toast.makeText(RobOrderActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RobOrderActivity.this,HomePageActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(RobOrderActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }


                }else {
                    Toast.makeText(RobOrderActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
