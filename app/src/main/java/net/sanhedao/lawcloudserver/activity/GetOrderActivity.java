package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.adapter.UserRobOrderAdapter;
import net.sanhedao.lawcloudserver.bean.OrderListDataBean;
import net.sanhedao.lawcloudserver.http.HttpClient;
import net.sanhedao.lawcloudserver.http.HttpUrls;
import net.sanhedao.lawcloudserver.http.RequestParameters;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/10 0010.
 */

public class GetOrderActivity extends Activity implements View.OnClickListener{

    private ImageView ivBack;
    private RadioButton rbK,rbB;
    private RecyclerView recycler;

    private UserRobOrderAdapter adapter;
    private List<OrderListDataBean.DataBean> list=new ArrayList<>();

    private List<OrderListDataBean.DataBean> listQ=new ArrayList<>();//快速咨询订单
    private List<OrderListDataBean.DataBean> listT=new ArrayList<>();//图文咨询订单

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_order);
        initView();
        initAdapter();
        initClick();
        httpOrdersList();
        httpTextOrdersList();
    }

    private void initView(){
        ivBack=findViewById(R.id.iv_back);
        rbK=findViewById(R.id.rb_k);
        rbB=findViewById(R.id.rb_b);
        recycler=findViewById(R.id.recycler);
    }

    private void initAdapter(){
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);

        adapter=new UserRobOrderAdapter(this,list);
        recycler.setAdapter(adapter);
    }

    private void initClick(){
        ivBack.setOnClickListener(this);
        rbB.setOnClickListener(this);
        rbK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rb_b:
                list.clear();
                list.addAll(listT);
                adapter.notifyDataSetChanged();
                break;
            case R.id.rb_k:
                list.clear();
                list.addAll(listQ);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    //获取快速可抢订单网络访问
    private RequestParameters homePageData(){

        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");

        RequestParameters parameters=new RequestParameters();
        parameters.put("token",token);
        parameters.put("uid",uid);
        parameters.put("sort","quick");
        return parameters;

    }
    private void httpOrdersList(){

        final String ORDER_LIST_URL= HttpUrls.HTTP_URL+"Acceptorder.robblist";

        HttpClient httpClient=new HttpClient();
        httpClient.post("获取快速可抢订单", ORDER_LIST_URL, homePageData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                if(!result.equals("Error")){

                    Gson gson=new Gson();
                    OrderListDataBean bean=gson.fromJson(result,OrderListDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==12300){
                            listQ.clear();
                            list.clear();
                            listQ.addAll(bean.getData());
                            list.addAll(bean.getData());
                            adapter.notifyDataSetChanged();
                        }else {
//                            Toast.makeText(GetOrderActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }


                }else {
                    Toast.makeText(GetOrderActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    //获取图文可抢订单网络访问
    private RequestParameters homePageTextData(){

        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");

        RequestParameters parameters=new RequestParameters();
        parameters.put("token",token);
        parameters.put("uid",uid);
        parameters.put("sort","itext");
        return parameters;

    }
    private void httpTextOrdersList(){

        final String ORDER_LIST_URL= HttpUrls.HTTP_URL+"Acceptorder.robblist";

        HttpClient httpClient=new HttpClient();
        httpClient.post("获取图文可抢订单", ORDER_LIST_URL, homePageTextData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                if(!result.equals("Error")){

                    Gson gson=new Gson();
                    OrderListDataBean bean=gson.fromJson(result,OrderListDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==12300){
                            listT.clear();
                            listT.addAll(bean.getData());
                        }else {
//                            Toast.makeText(GetOrderActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }


                }else {
                    Toast.makeText(GetOrderActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
