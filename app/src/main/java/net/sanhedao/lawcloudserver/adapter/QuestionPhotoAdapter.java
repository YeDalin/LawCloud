package net.sanhedao.lawcloudserver.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.bean.QuestionPhotoDataBean;
import net.sanhedao.lawcloudserver.log.Logger;
import net.sanhedao.lawcloudserver.uitls.PhotoUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class QuestionPhotoAdapter extends RecyclerView.Adapter<QuestionPhotoAdapter.QuestionPhotoViewHolder> {

    private Context context;
    private List<QuestionPhotoDataBean> list;


    private int width;

    public QuestionPhotoAdapter(Context context, List<QuestionPhotoDataBean> list) {
        this.context = context;
        this.list = list;

        WindowManager wm = ((Activity)context).getWindowManager();
        width = wm.getDefaultDisplay().getWidth();

        Logger.e(" 宽度1= "+width);

    }

    @Override
    public QuestionPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        QuestionPhotoViewHolder holder=new QuestionPhotoViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(QuestionPhotoViewHolder holder, int position) {

        initView(holder, position);
        initClick(holder, position);

    }


    private void initView(QuestionPhotoViewHolder holder, int position){

        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) holder.image.getLayoutParams();
        params.weight=width/3;
        params.height=width/3;

        holder.itemView.setLayoutParams(params);

        Log.e("xxxx","屏幕宽度"+width);



        if(list.get(position).getPath()!=null){


            Logger.e("path  "+list.get(position).getPath());

            Bitmap bitmap = PhotoUtils.getFitSampleBitmap(list.get(position).getPath(), width / 3, width / 3);

            Logger.e("bitmap = "+String.valueOf(bitmap));


            holder.image.setImageBitmap(bitmap);

        }




    }

    private void initClick(QuestionPhotoViewHolder holder, int position){
        holder.image.setOnClickListener(addPhotoListener(holder,position));
    }

    //点击图片
    private View.OnClickListener addPhotoListener(QuestionPhotoViewHolder holder, final int position){
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.image:
                        if(position==list.size()-1  && list.get(position).getPath()==null){

                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    "image/*");
                            ((Activity)context).startActivityForResult(intent, 0x1);

                        }else {

                        }
                        break;
                }
            }
        };
        return listener;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class QuestionPhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public QuestionPhotoViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);

        }


    }

}
