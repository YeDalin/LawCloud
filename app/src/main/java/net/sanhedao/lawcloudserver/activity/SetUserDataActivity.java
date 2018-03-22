package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.bean.Citys;
import net.sanhedao.lawcloudserver.bean.SetUserDataBean;
import net.sanhedao.lawcloudserver.data.MyData;
import net.sanhedao.lawcloudserver.dialog.LoadingDialog;
import net.sanhedao.lawcloudserver.http.HttpClient;
import net.sanhedao.lawcloudserver.http.HttpUrls;
import net.sanhedao.lawcloudserver.http.RequestParameters;
import net.sanhedao.lawcloudserver.log.Logger;
import net.sanhedao.lawcloudserver.uitls.PhotoUtils;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018/3/7 0007.
 */

public class SetUserDataActivity extends Activity implements View.OnClickListener {

    private ImageView ivBack;
    private TextView tvPhoto, tvNickName, tvUserData, tvSex, tvLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_data);
        initView();
        initClick();
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        tvPhoto = findViewById(R.id.tv_photo);
        tvNickName = findViewById(R.id.tv_nickname);
        tvUserData = findViewById(R.id.tv_user_data);
        tvSex = findViewById(R.id.tv_sex);
        tvLocation = findViewById(R.id.tv_location);


        String username = SharedUtils.getStringPrefs(this, "username", "");
        tvNickName.setText(username);

    }


    private void initClick() {
        ivBack.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        tvNickName.setOnClickListener(this);
        tvUserData.setOnClickListener(this);
        tvSex.setOnClickListener(this);
        tvLocation.setOnClickListener(this);


        String sex = SharedUtils.getStringPrefs(this, "sex", "0");
        String address = SharedUtils.getStringPrefs(this, "address", "");

        tvLocation.setText(address);

        if(sex.equals("0")){
            tvSex.setText("男");
        }else {
            tvSex.setText("女");
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_photo:
                getPhoto();
                break;
            case R.id.tv_nickname:
                Intent intent = new Intent(this, SetDataActivity.class);
                intent.putExtra("title", "编辑昵称");
                startActivityForResult(intent,2000);
                break;
            case R.id.tv_user_data:
                Intent intent1 = new Intent(this, SetDataActivity.class);
                intent1.putExtra("title", "编辑简介");
                startActivity(intent1);
                break;
            case R.id.tv_sex:

                onClickSex();
                break;
            case R.id.tv_location:
                onClickLocation();
                break;
        }

    }


    //调取本地图片
    private void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, 1001);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 1001) {
                String path = photoPath(data);
                setPhoto(path);

            }
        }else if(requestCode==2000){

            String username = SharedUtils.getStringPrefs(this, "username", "");

            tvNickName.setText(username);
        }


    }

    //获取选择图片的路径
    private String photoPath(Intent data) {

        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);

        return imagePath;
    }

    //将uri转为路径
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

    //将选择的图片加载到控件中
    private void setPhoto(String photoPath) {

        Bitmap bitmap = PhotoUtils.getFitSampleBitmap(photoPath, MyData.photoWidth, MyData.photoHeight);

        List<File> photoFile = new ArrayList<>();
        File file = new File(bitmapToFile(this, bitmap));
//        File file=new File(photoPath);
        photoFile.add(file);

        httpSetPhoto(photoFile);

    }

    /*
     * bitmap转file(原图转换)
     */
    public static String bitmapToFile(Context context, Bitmap bitmap) {
        String sdPath = getDiskCacheDir(context);
        String name = new DateFormat().format("yyyyMMddhhmmss",
                Calendar.getInstance(Locale.CHINA)) + ".jpg";
        String picPath = sdPath + "/" + name;
        File outImage = new File(picPath);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        //返回一个图片路径
        Logger.e("压缩路径  " + picPath);
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


    private final String SET_USER_DATA_URL = HttpUrls.HTTP_URL + "User.editByid";

    //修改头像网络访问
    private void httpSetPhoto(List<File> photoFile) {

        final LoadingDialog dialog=new LoadingDialog(this);
        dialog.show();

        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");

        OkGo.<String>post(SET_USER_DATA_URL)
                .tag(this)
                .isMultipart(true)
                .params("token", token)
                .params("uid", uid)
                .params("tag", "face")
                .addFileParams("face", photoFile)
                .params("ostype", "Android")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Logger.e("成功");
                        Logger.e(response.body());

                        dialog.dismiss();

                        if(response.body()!=null){
                            Gson gson=new Gson();
                            SetUserDataBean bean=gson.fromJson(response.body(),SetUserDataBean.class);
                            if(bean.getCode()==11300){
                                Toast.makeText(SetUserDataActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
                            }else {

                            }
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Logger.e("失败");
                        Logger.e("  " + response.body());

                        dialog.dismiss();

                    }
                });
    }



    private OptionsPickerView pickerView;
    //点击选择性别
    private void onClickSex() {

        pickerView = new OptionsPickerView(this);
        pickerView.setTitle("性别");

        final ArrayList<String> list = new ArrayList<>();
        list.add("男");
        list.add("女");

        pickerView.setPicker(list);
        pickerView.setCyclic(false);

        pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String s = list.get(options1);

                httpSetSex(s);
            }
        });

        pickerView.show();

    }

    //修改性别网络访问
    private RequestParameters sexData(String sex){

        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");

        RequestParameters parameters=new RequestParameters();
        parameters.put("token",token);
        parameters.put("uid",uid);
        parameters.put("tag","sex");
        if(sex.equals("男")){

            parameters.put("sex","0");
        }else {
            parameters.put("sex","1");
        }

        return parameters;
    }
    private void httpSetSex(final String sex){

        final LoadingDialog dialog=new LoadingDialog(this);
        dialog.show();

        HttpClient httpClient=new HttpClient();
        httpClient.post("修改性别", SET_USER_DATA_URL, sexData(sex), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                dialog.dismiss();

                if(!result.equals("Error")){

                    Gson gson=new Gson();
                    SetUserDataBean bean=gson.fromJson(result,SetUserDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==11300){
                            Toast.makeText(SetUserDataActivity.this,R.string.set_ok,Toast.LENGTH_SHORT).show();
                            tvSex.setText(sex);
                            if(sex.equals("男")){
                                SharedUtils.setStringPrefs(SetUserDataActivity.this,"sex","0");
                            }else {
                                SharedUtils.setStringPrefs(SetUserDataActivity.this,"sex","1");
                            }

                        }else {

                        }
                    }

                }else {
                    Toast.makeText(SetUserDataActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private int location1, location2, location3;

    //点击选择所在地
    private void onClickLocation() {

        setLocationData();

        pickerView = new OptionsPickerView(this);
        pickerView.setTitle("所在地");


        pickerView.setPicker(provinces, citys, areass, true);
        pickerView.setCyclic(false);

        // 设置默认选中的三级项目
        pickerView.setSelectOptions(22, 3, 0);


        pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {

                location1 = options1;
                location2 = option2;
                location3 = options3;

                httpSetLocation();
            }
        });

        pickerView.show();

    }

    //省级集合
    private ArrayList<String> provinces = new ArrayList<>();

    //市级所属的省级集合
    private ArrayList<String> city = new ArrayList<>();
    //市级集合
    private ArrayList<List<String>> citys = new ArrayList<>();

    //区级所属市级所属省级的集合
    private ArrayList<String> area = new ArrayList<>();
    //区级所属市级的集合
    private ArrayList<List<String>> areas = new ArrayList<>();
    //区级集合
    private ArrayList<List<List<String>>> areass = new ArrayList<>();

    private Citys list;

    //初始化地址信息
    private void setLocationData() {
        Gson gson = new Gson();
        String city = getJson(this, "citys.json");

        city = "{\"list\":" + city + "}";

        Citys citys = gson.fromJson(city, Citys.class);
        list = citys;

        Logger.e("  看看 " + citys.getList().toString());
        Logger.e(" 市 " + citys.getList().get(0).getSub().toString());
        Logger.e(" 区 " + citys.getList().get(0).getSub().get(0).getSub().toString());
        Logger.e("  地区结果 " + city);


        for (int i = 0; i < citys.getList().size(); i++) {


            this.city = new ArrayList<>();
            areas = new ArrayList<>();
            for (int j = 0; j < citys.getList().get(i).getSub().size(); j++) {


                area = new ArrayList<>();

                if (citys.getList().get(i).getSub().get(j).getSub() != null) {
                    for (int k = 0; k < citys.getList().get(i).getSub().get(j).getSub().size(); k++) {

                        area.add(citys.getList().get(i).getSub().get(j).getSub().get(k).getRegionname());
                    }
                } else {
                    area.add("");
                }


                areas.add(area);
                this.city.add(citys.getList().get(i).getSub().get(j).getRegionname());

            }
            areass.add(areas);
            this.citys.add(this.city);
            provinces.add(citys.getList().get(i).getRegionname());

        }

    }

    //解析数据
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    //修改地址网络访问
    private RequestParameters locationData(){

        String province = list.getList().get(location1).getId();
        String city = list.getList().get(location1).getSub().get(location2).getId();
        String area = "";
        if (list.getList().get(location1).getSub().get(location2).getSub() != null) {
            area = list.getList().get(location1).getSub().get(location2).getSub().get(location3).getId();
        }

        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");

        RequestParameters parameters=new RequestParameters();
        parameters.put("token",token);
        parameters.put("uid",uid);
        parameters.put("tag","address");
        parameters.put("province",province);
        parameters.put("city",city);
        parameters.put("area",area);

        return parameters;
    }
    private void httpSetLocation(){

        final LoadingDialog dialog=new LoadingDialog(this);
        dialog.show();

        HttpClient httpClient=new HttpClient();
        httpClient.post("修改地址", SET_USER_DATA_URL, locationData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {

                dialog.dismiss();

                if(!result.equals("Error")){

                    Gson gson=new Gson();
                    SetUserDataBean bean=gson.fromJson(result,SetUserDataBean.class);
                    if(bean!=null){
                        if(bean.getCode()==11300){
                            Toast.makeText(SetUserDataActivity.this,R.string.set_ok,Toast.LENGTH_SHORT).show();
                            String s="";
                            if( areass.get(location1).get(location2).get(location3).length()==0){
                                s = provinces.get(location1)+"-" + citys.get(location1).get(location2);
                            }else {
                                s = provinces.get(location1)+"-" + citys.get(location1).get(location2)+"-" + areass.get(location1).get(location2).get(location3);
                            }
                            tvLocation.setText(s);
                            SharedUtils.setStringPrefs(SetUserDataActivity.this,"address",s);
                        }
                    }

                }else {
                    Toast.makeText(SetUserDataActivity.this,R.string.network_error,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
