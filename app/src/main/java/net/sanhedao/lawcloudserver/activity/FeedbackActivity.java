package net.sanhedao.lawcloudserver.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.adapter.QuestionPhotoAdapter;
import net.sanhedao.lawcloudserver.bean.QuestionPhotoDataBean;
import net.sanhedao.lawcloudserver.data.MyData;
import net.sanhedao.lawcloudserver.dialog.LoadingDialog;
import net.sanhedao.lawcloudserver.http.HttpUrls;
import net.sanhedao.lawcloudserver.log.Logger;
import net.sanhedao.lawcloudserver.uitls.PhotoUtils;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class FeedbackActivity extends Activity implements View.OnClickListener{

    private ImageView ivBack;
    private EditText etQuestion,etPhone;
    private TextView tvSubmit;
    private RecyclerView recycler;


    private QuestionPhotoAdapter adapter;
    private List<QuestionPhotoDataBean> list=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
        initAdapter();
        initClick();
    }

    private int width;
    private void initView() {
        ivBack=findViewById(R.id.iv_back);
        etQuestion=findViewById(R.id.et_question);
        etPhone=findViewById(R.id.et_phone);
        tvSubmit=findViewById(R.id.tv_submit);
        recycler=findViewById(R.id.recycler);

        WindowManager wm = this.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();

        String phone = SharedUtils.getStringPrefs(this, "phone", "");
        etPhone.setText(phone);

    }

    private void initAdapter(){
        GridLayoutManager manager=new GridLayoutManager(this,3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);

        list.add(new QuestionPhotoDataBean());

        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) recycler.getLayoutParams();
        params.height=width/3;
        recycler.setLayoutParams(params);

        adapter=new QuestionPhotoAdapter(this,list);
        recycler.setAdapter(adapter);
    }

    private void initClick(){
        ivBack.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                if(isSubmit()){
                    httpFeedback();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
//               setData(data);

                String imagePath;

                //判断手机系统版本号
                if (Build.VERSION.SDK_INT >= 19) {
                    //4.4及以上系统使用这个方法处理图片
                    imagePath = handleImageOnKitKat(data);
                } else {
                    //4.4以下系统使用这个方法处理图片
                    imagePath = handleImageBeforeKitKat(data);
                }

                setPath(imagePath);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //设置path路径
    private void setPath(String path){
        QuestionPhotoDataBean bean=new QuestionPhotoDataBean();
        bean.setPath(path);
        list.add(list.size()-1,bean);



        int h=list.size()/3;
        if(list.size()%3>0){
            h++;
        }

        if(h>3){
            h=3;
        }

        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) recycler.getLayoutParams();
        params.height=(width/3)*h;
        recycler.setLayoutParams(params);

        adapter.notifyDataSetChanged();
    }

    @TargetApi(19)
    private String handleImageBeforeKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析粗数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(uri, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private String handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        return imagePath;
    }
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri 和 selection 来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    private boolean isSubmit(){

        if(etQuestion.getText().toString().length()==0){
            Toast.makeText(this,R.string.question_is_0,Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etPhone.getText().toString().length()!=11){
            Toast.makeText(this,R.string.phone_no_11,Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void httpFeedback(){

        final LoadingDialog dialog=new LoadingDialog(this);
        dialog.show();

        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");



        List<File> pic0=new ArrayList<>();
        List<File> pic1=new ArrayList<>();
        List<File> pic2=new ArrayList<>();
        List<File> pic3=new ArrayList<>();
        List<File> pic4=new ArrayList<>();
        List<File> pic5=new ArrayList<>();
        List<File> pic6=new ArrayList<>();
        List<File> pic7=new ArrayList<>();
        List<File> pic8=new ArrayList<>();

        switch (list.size()){
            case 1:
                break;
            case 2:
                pic0.add(getFile(0));
                break;
            case 3:
                pic0.add(getFile(0));
                pic1.add(getFile(1));
                break;
            case 4:
                pic0.add(getFile(0));
                pic1.add(getFile(1));
                pic2.add(getFile(2));
                break;
            case 5:
                pic0.add(getFile(0));
                pic1.add(getFile(1));
                pic2.add(getFile(2));
                pic3.add(getFile(3));
                break;
            case 6:
                pic0.add(getFile(0));
                pic1.add(getFile(1));
                pic2.add(getFile(2));
                pic3.add(getFile(3));
                pic4.add(getFile(4));
                break;
            case 7:
                pic0.add(getFile(0));
                pic1.add(getFile(1));
                pic2.add(getFile(2));
                pic3.add(getFile(3));
                pic4.add(getFile(4));
                pic5.add(getFile(5));
                break;
            case 8:
                pic0.add(getFile(0));
                pic1.add(getFile(1));
                pic2.add(getFile(2));
                pic3.add(getFile(3));
                pic4.add(getFile(4));
                pic5.add(getFile(5));
                pic6.add(getFile(6));
                break;
            case 9:
                pic0.add(getFile(0));
                pic1.add(getFile(1));
                pic2.add(getFile(2));
                pic3.add(getFile(3));
                pic4.add(getFile(4));
                pic5.add(getFile(5));
                pic6.add(getFile(6));
                pic7.add(getFile(7));
                if(list.get(list.size()-1).getPath()!=null){
                    pic8.add(getFile(8));
                }
                break;
        }

        final String FEEDBACK_URL= HttpUrls.HTTP_USER_URL+"Complaint.add";

        OkGo.<String>post(FEEDBACK_URL)
                .tag(this)
                .isMultipart(true)
                .params("token", token)
                .params("uid", uid)
                .params("type", "1")
                .params("flag", "Android")

                .params("phone", etPhone.getText().toString())
                .params("content", etQuestion.getText().toString())
                .params("tag", "2")
                .addFileParams("pic0[]",pic0)
                .addFileParams("pic1[]",pic1)
                .addFileParams("pic2[]",pic2)
                .addFileParams("pic3[]",pic3)
                .addFileParams("pic4[]",pic4)
                .addFileParams("pic5[]",pic5)
                .addFileParams("pic6[]",pic6)
                .addFileParams("pic7[]",pic7)
                .addFileParams("pic8[]",pic8)

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Logger.e("成功");
                        Logger.e(response.body());

                        dialog.dismiss();

                        Logger.e(response.toString());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Logger.e("失败");
                        Logger.e("  "+response.body());

                        dialog.dismiss();

                    }
                });

    }


    private File getFile(int i){
        Bitmap bitmap= PhotoUtils.getFitSampleBitmap(list.get(i).getPath(), MyData.photoWidth, MyData.photoHeight);
        File file=new File(bitmapToFile(this,bitmap));
        return file;
    }

    /*
        * bitmap转file(原图转换)
        */
    public static String bitmapToFile(Context context, Bitmap bitmap) {
        String sdPath = getDiskCacheDir(context);
        String name = new DateFormat().format("yyyyMMddhhmmss",
                Calendar.getInstance(Locale.CHINA)) + ".jpg";
        String picPath = sdPath + "/"+num() + name;
        File outImage = new File(picPath);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        //返回一个图片路径
        Logger.e("压缩路径  "+picPath);
        return picPath;
    }

    /**
     * 获取缓存文件夹的相对路径
     */
    public static String getDiskCacheDir(Context ctx) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = ctx.getExternalCacheDir().getPath();
        } else {
            cachePath = ctx.getCacheDir().getPath();
        }
        return cachePath;
    }

    private static String num(){

        int x = (int) (Math.random() * 10);
        int y = (int) (Math.random() * 10);

        return x+""+y;
    }

}
