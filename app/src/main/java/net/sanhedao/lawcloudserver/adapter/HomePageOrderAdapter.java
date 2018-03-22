package net.sanhedao.lawcloudserver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.bean.HomePageDataBean;
import net.sanhedao.lawcloudserver.log.Logger;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/2/2 0002.
 */

public class HomePageOrderAdapter extends RecyclerView.Adapter<HomePageOrderAdapter.HomePageViewHolder>{


    private List<HomePageDataBean.DataBean.MyorderBean> list;
    private Context context;
    private int height;

    public HomePageOrderAdapter(List<HomePageDataBean.DataBean.MyorderBean> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @Override
    public HomePageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        height=parent.getHeight();

        Logger.e("  height= "+height);

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_home_page_order,parent,false);
        HomePageViewHolder holder=new HomePageViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(HomePageViewHolder holder, int position) {

//        if(listener!=null){
//
////            holder.tvNickname.setText(""+position);
//            listener.setRecyclerHeight(height*list.size());
//        }


        initView(holder,position);



    }



    private void initView(HomePageViewHolder holder, int position){
        holder.tvNickname.setText(list.get(position).getNickname());
        holder.tvTime.setText(list.get(position).getCreatetime());

        Picasso.with(context).load(list.get(position).getHeadportrait()).into(holder.civPhoto);

    }

    @Override
    public int getItemCount() {

        return list.size();
    }


    //适配器监听接口
    public interface HomePageAdapterListener{
        void setRecyclerHeight(int height);
    }
    private HomePageAdapterListener listener;
    public void setListener(HomePageAdapterListener listener) {
        this.listener = listener;
    }



    public class HomePageViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView civPhoto;
        private TextView tvNickname,tvOrderStatus,tvMassage,tvTime;

        public HomePageViewHolder(View itemView) {
            super(itemView);

            civPhoto=itemView.findViewById(R.id.civ_photo);
            tvNickname=itemView.findViewById(R.id.tv_nickname);
            tvOrderStatus=itemView.findViewById(R.id.tv_order_status);
//            tvMassage=itemView.findViewById(R.id.tv_massage);
            tvTime=itemView.findViewById(R.id.tv_time);

        }
    }

}
