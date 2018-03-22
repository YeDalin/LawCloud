package net.sanhedao.lawcloudserver.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.activity.FeedbackActivity;
import net.sanhedao.lawcloudserver.activity.UserCenterSetActivity;
import net.sanhedao.lawcloudserver.bean.PersonalCenterDataBean;
import net.sanhedao.lawcloudserver.http.HttpClient;
import net.sanhedao.lawcloudserver.http.HttpUrls;
import net.sanhedao.lawcloudserver.http.RequestParameters;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/2/2 0002.
 */

public class PersonalCenterFragment extends Fragment implements View.OnClickListener{

    private View view;

    private ImageView ivSet;
    private CircleImageView civPhoto;
    private TextView tvNickName,tvMoney,tvCount;
    private TextView tvFeedBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_personal_center,null);

        initView();
        initClick();
        getDataHttp();
        return view;
    }

    private void initView() {
        ivSet=view.findViewById(R.id.iv_set);
        tvNickName=view.findViewById(R.id.tv_nickname);
        tvMoney=view.findViewById(R.id.tv_money);
        tvCount=view.findViewById(R.id.tv_count);

        civPhoto=view.findViewById(R.id.civ_photo);

        tvFeedBack=view.findViewById(R.id.tv_feedback);
    }

    private void initClick(){
        ivSet.setOnClickListener(this);
        tvFeedBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_set:

                Intent intent=new Intent(getActivity(), UserCenterSetActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_feedback:
                Intent intent1=new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent1);
                break;
        }
    }

    //获取个人中心数据网络访问
    private RequestParameters userData(){
        String token = SharedUtils.getStringPrefs(getActivity(), "token", "");
        String uid = SharedUtils.getStringPrefs(getActivity(), "uid", "");

        RequestParameters parameters=new RequestParameters();
        parameters.put("token",token);
        parameters.put("uid",uid);
        return parameters;

    }
    private void getDataHttp(){

        final String USER_DATA_URL= HttpUrls.HTTP_URL+"User.showByid";
        HttpClient httpClient=new HttpClient();
        httpClient.post("获取个人中心数据", USER_DATA_URL, userData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                if(!result.equals("Error")){

                    Gson gson=new Gson();
                    PersonalCenterDataBean bean=gson.fromJson(result,PersonalCenterDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==11300){

                            SharedUtils.setStringPrefs(getActivity(),"username",bean.getData().getName());

                            tvNickName.setText(bean.getData().getName());
                            tvMoney.setText(""+bean.getData().getMoney());
                            tvCount.setText(bean.getData().getLawyerspot());
                            Picasso.with(getActivity())
                                    .load(bean.getData().getFace())
                                    .into(civPhoto);

                        }else {

                        }
                    }

                }else {
                    Toast.makeText(getActivity(),R.string.network_error,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
