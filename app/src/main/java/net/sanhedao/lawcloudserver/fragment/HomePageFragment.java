package net.sanhedao.lawcloudserver.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.activity.GetOrderActivity;
import net.sanhedao.lawcloudserver.adapter.HomePageOrderAdapter;
import net.sanhedao.lawcloudserver.bean.HomePageDataBean;
import net.sanhedao.lawcloudserver.http.HttpClient;
import net.sanhedao.lawcloudserver.http.HttpUrls;
import net.sanhedao.lawcloudserver.http.RequestParameters;
import net.sanhedao.lawcloudserver.log.Logger;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/2 0002.
 */

public class HomePageFragment extends Fragment implements View.OnClickListener{

    private View view;

    private TextView tvMoney,tvOrderTime,tvOrderCount,tvUserRobOrder,tvMore;
    private RecyclerView recycler;

    private HomePageOrderAdapter adapter;
    private List<HomePageDataBean.DataBean.MyorderBean> list=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home_page,null);

        initView();
        initAdapter();
        initClick();
        httpHomePage();
        return view;
    }

    private void initView() {
        tvMoney=view.findViewById(R.id.tv_money);
        tvOrderTime=view.findViewById(R.id.tv_order_time);
        tvOrderCount=view.findViewById(R.id.tv_order_count);
        tvUserRobOrder=view.findViewById(R.id.tv_user_rob_order);
        tvMore=view.findViewById(R.id.tv_more);
        recycler=view.findViewById(R.id.recycler);
    }

    private void initAdapter(){
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);



        Logger.e("********开始测试********");

        adapter=new HomePageOrderAdapter(list,getActivity());
        recycler.setAdapter(adapter);


        adapter.setListener(new HomePageOrderAdapter.HomePageAdapterListener() {
            @Override
            public void setRecyclerHeight(int height) {

                Logger.e(" height=  "+height);

                ViewGroup.LayoutParams params= recycler.getLayoutParams();

                params.height=height;
                recycler.setLayoutParams(params);
            }
        });

        adapter.notifyDataSetChanged();


    }

    private void initClick(){
        tvUserRobOrder.setOnClickListener(this);
        tvMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_user_rob_order:
                Intent intent=new Intent(getActivity(), GetOrderActivity.class);
                startActivity(intent);
                break;
        }

    }


    //首页网络访问
    private RequestParameters homePageData(){

        String token = SharedUtils.getStringPrefs(getActivity(), "token", "");
        String uid = SharedUtils.getStringPrefs(getActivity(), "uid", "");

        RequestParameters parameters=new RequestParameters();
        parameters.put("token",token);
        parameters.put("uid",uid);
        return parameters;

    }
    private void httpHomePage(){
        final String HOME_PAGE_URL= HttpUrls.HTTP_URL+"Office.showByid";

        HttpClient httpClient=new HttpClient();
        httpClient.post("首页", HOME_PAGE_URL, homePageData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                if(!result.equals("Error")){

                    Gson gson=new Gson();
                    HomePageDataBean bean=gson.fromJson(result,HomePageDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==12300){
                            tvMoney.setText(bean.getData().getWallet());
                            tvOrderTime.setText(bean.getData().getTime()+"");
                            tvOrderCount.setText(bean.getData().getNumber());
                            list.clear();
                            list.addAll(bean.getData().getMyorder());
                            adapter.notifyDataSetChanged();
                        }else if(bean.getCode()==12301){
                            tvMoney.setText(bean.getData().getWallet());
                            tvOrderTime.setText(bean.getData().getTime()+"");
                            tvOrderCount.setText(bean.getData().getNumber());
                        }
                    }

                }else {
                    Toast.makeText(getActivity(),R.string.network_error,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void onClickRobOrder(){
        tvUserRobOrder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {



                return false;
            }
        });
    }

}
