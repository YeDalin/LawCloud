package net.sanhedao.lawcloudserver.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.activity.RobOrderActivity;
import net.sanhedao.lawcloudserver.bean.OrderListDataBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class UserRobOrderAdapter extends RecyclerView.Adapter<UserRobOrderAdapter.RobOrderViewHolder> {


    private Context context;
    private List<OrderListDataBean.DataBean> list;

    public UserRobOrderAdapter(Context context,List<OrderListDataBean.DataBean> list) {
        this.context = context;
        this.list=list;
    }

    @Override
    public RobOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rob_order,parent,false);
        RobOrderViewHolder holder=new RobOrderViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RobOrderViewHolder holder, int position) {


        initView(holder, position);
        initClick(holder, position);

    }

    private void initView(RobOrderViewHolder holder, int position){

        holder.tvNickname.setText(list.get(position).getNickname());
        holder.tvTime.setText(list.get(position).getCreatetime());
        holder.tvQuestion.setText(list.get(position).getDescribe());

        Picasso.with(context).load(list.get(position).getHeadportrait()).into(holder.civPhoto);

        if(list.get(position).getUrgent().equals("0")){
            holder.ivJi.setVisibility(View.INVISIBLE);
        }else {
            holder.ivJi.setVisibility(View.VISIBLE);
        }

    }


    private void initClick(RobOrderViewHolder holder, final int position){

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, RobOrderActivity.class);
                intent.putExtra("data",list.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RobOrderViewHolder extends RecyclerView.ViewHolder{


        private CircleImageView civPhoto;
        private ImageView ivJi;
        private TextView tvNickname,tvOnline,tvTime,tvQuestion;
        private View vOnline;

        public RobOrderViewHolder(View itemView) {
            super(itemView);

            civPhoto=itemView.findViewById(R.id.civ_photo);
            ivJi=itemView.findViewById(R.id.iv_ji);
            tvNickname=itemView.findViewById(R.id.tv_nickname);
            tvOnline=itemView.findViewById(R.id.tv_online);
            tvTime=itemView.findViewById(R.id.tv_time);
            tvQuestion=itemView.findViewById(R.id.tv_question);
            vOnline=itemView.findViewById(R.id.v_online);

        }
    }

}
