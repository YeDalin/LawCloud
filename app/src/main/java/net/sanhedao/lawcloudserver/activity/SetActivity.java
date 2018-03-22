package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.bean.BeGoodAtDataBean;
import net.sanhedao.lawcloudserver.bean.Citys;
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
 * Created by Administrator on 2018/1/31 0031.
 * <p>
 * 设置个人信息
 *
 * @author ye
 */


public class SetActivity extends Activity implements View.OnClickListener {


    private EditText etOncePrice, etBeGoodAt, etLocation, etSex, etEmail, etWeixin, etAlipay, etDetailed;
    private ImageView ivAgree, ivBack;
    private TextView tvUserAgreement, tvSubmit;

    private OptionsPickerView pickerView;


    private boolean isAgree = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();
        initClick();
    }

    private void initView() {

        etOncePrice = findViewById(R.id.et_once_price);
        etBeGoodAt = findViewById(R.id.et_be_good_at);
        etLocation = findViewById(R.id.et_location);
        etSex = findViewById(R.id.et_sex);
        etEmail = findViewById(R.id.et_email);
        etWeixin = findViewById(R.id.et_weixin);
        etAlipay = findViewById(R.id.et_alipay);
        etDetailed = findViewById(R.id.et_detailed);
        tvUserAgreement = findViewById(R.id.tv_user_agreement);
        ivAgree = findViewById(R.id.iv_agree);
        ivBack = findViewById(R.id.iv_back);

        tvSubmit = findViewById(R.id.tv_submit);

    }

    private void initClick() {
        ivBack.setOnClickListener(this);
        etBeGoodAt.setOnClickListener(this);
        etLocation.setOnClickListener(this);
        etSex.setOnClickListener(this);
        ivAgree.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.et_be_good_at:
                closeNum();
                onClickBeGoodAtHttp();
                break;
            case R.id.et_location:

                closeNum();

                onClickLocation();
                break;
            case R.id.et_sex:
                closeNum();
                onClickSex();
                break;
            case R.id.iv_agree:
                isAgree = !isAgree;
                if (isAgree) {
                    ivAgree.setImageResource(R.mipmap.gouxuan);
                } else {
                    ivAgree.setImageResource(R.mipmap.weigouxuan);
                }
                break;
            case R.id.tv_submit:

                if (isSubmit()) {
                    if (isAgree) {
                        onClickSubmitHttp();
                    } else {
                        Toast.makeText(SetActivity.this, R.string.please_agree_user, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SetActivity.this, R.string.law_data_no, Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }

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
                etSex.setText(s);
            }
        });

        pickerView.show();

    }

    //点击选择擅长领域网络访问
    private RequestParameters userTokenData() {
        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");
        RequestParameters parameters = new RequestParameters();
        parameters.put("token", token);
        parameters.put("uid", uid);
        return parameters;
    }

    private void onClickBeGoodAtHttp() {
        final String BE_GOOD_AT_DATA = HttpUrls.HTTP_URL + "User.getindustry";
        HttpClient httpClient = new HttpClient();
        httpClient.post("获取擅长领域网络访问", BE_GOOD_AT_DATA, userTokenData(), new HttpClient.OnResponseListener() {
            @Override
            public void onResponse(String result) {
                if (!result.equals("Error")) {

                    Gson gson = new Gson();
                    BeGoodAtDataBean bean = gson.fromJson(result, BeGoodAtDataBean.class);
                    if (bean != null) {
                        if (bean.getCode() == 11300) {

                            showBeGoodAt(bean);

                        } else if (bean.getCode() == 11006) {
                            Toast.makeText(SetActivity.this, R.string.login_ago, Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(SetActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String industryid = "";

    //展示擅长领域
    private void showBeGoodAt(final BeGoodAtDataBean bean) {
        pickerView = new OptionsPickerView(this);
        pickerView.setTitle("擅长领域");

        final ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < bean.getData().size(); i++) {
            list.add(bean.getData().get(i).getName());
        }


        pickerView.setPicker(list);
        pickerView.setCyclic(false);

        pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String s = list.get(options1);
                industryid = bean.getData().get(options1).getId();
                etBeGoodAt.setText(s);
            }
        });

        pickerView.show();
    }

    //关闭键盘
    private void closeNum(){
        //关闭键盘
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

                String s = provinces.get(options1) + citys.get(options1).get(option2) + areass.get(options1).get(option2).get(options3);
                etLocation.setText(s);
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


    //判断是否可以提交认证
    private boolean isSubmit() {

        if (etOncePrice.getText().toString().length() == 0) {
            return false;
        }

        if (etBeGoodAt.getText().toString().length() == 0) {
            return false;
        }

        if (etLocation.getText().toString().length() == 0) {
            return false;
        }

        if (etSex.getText().toString().length() == 0) {
            return false;
        }

        if (etEmail.getText().toString().length() == 0) {
            return false;
        }

        if (etWeixin.getText().toString().length() == 0) {
            return false;
        }

        if (etAlipay.getText().toString().length() == 0) {
            return false;
        }

        if (etDetailed.getText().toString().length() > 18) {
            return false;
        }

        return true;
    }

    //点击提交审核网络访问
    private void onClickSubmitHttp() {

        final LoadingDialog dialog=new LoadingDialog(this);
        dialog.show();

        final String SUBMIT_URL = HttpUrls.HTTP_URL + "Authentication.authentication";

        String token = SharedUtils.getStringPrefs(this, "token", "");
        String uid = SharedUtils.getStringPrefs(this, "uid", "");
        String type = SharedUtils.getStringPrefs(this, "type", "");
        boolean checked = SharedUtils.getBooleanPrefs(this, "checked", false);
        String isshowwork="";
        if(checked){
            isshowwork="1";
        }

        String practicestartime = SharedUtils.getStringPrefs(this, "practicestartime", "");
        String practicelaw = SharedUtils.getStringPrefs(this, "practicelaw", "");
        String username = SharedUtils.getStringPrefs(this, "username", "");
        String face = SharedUtils.getStringPrefs(this, "face", "");
        String certificatephoto = SharedUtils.getStringPrefs(this, "certificatephoto", "");
        String certificateinspection = SharedUtils.getStringPrefs(this, "certificateinspection", "");

        String sex="";
        if(etSex.getText().toString().equals("男")){
            sex="0";
        }else {
            sex="1";
        }

        String province = list.getList().get(location1).getId();
        String city = list.getList().get(location1).getSub().get(location2).getId();
        String area = "";
        if (list.getList().get(location1).getSub().get(location2).getSub() != null) {
            area = list.getList().get(location1).getSub().get(location2).getSub().get(location3).getId();
        }


//        List<File> pic = new ArrayList<>();
        List<File> certificatephotoF = new ArrayList<>(),certificateinspectionF = new ArrayList<>(),faceF = new ArrayList<>();

        Bitmap bitmap = PhotoUtils.getFitSampleBitmap(certificatephoto, MyData.photoWidthZ, MyData.photoHeightZ);
        File file = new File(bitmapToFile(this,bitmap));
        certificatephotoF.add(file);

        bitmap = PhotoUtils.getFitSampleBitmap(certificateinspection, MyData.photoWidthZ,  MyData.photoHeightZ);
        file = new File(bitmapToFile(this,bitmap));
        certificateinspectionF.add(file);

        bitmap = PhotoUtils.getFitSampleBitmap(face, MyData.photoWidth,  MyData.photoHeight);
        file = new File(bitmapToFile(this,bitmap));
        faceF.add(file);

        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Logger.e(" width= "+width+"  height= "+height);

//        File file = new File(certificatephoto);
//        certificatephotoF.add(file);
//
//
//        file = new File(certificateinspection);
//        certificateinspectionF.add(file);
//
//
//        file = new File(face);
//        faceF.add(file);

//        Logger.e(" 图片上传张数= "+pic.size());

        OkGo.<String>post(SUBMIT_URL)
                .tag(this)
                .isMultipart(true)
                .params("token", token)
                .params("uid", uid)
                .params("type", type)
                .params("ostype", "Android")

                .params("practicestartime", practicestartime)
                .params("practicelaw", practicelaw)

                .params("isshowwork", isshowwork)//个人主页是否显示工作单位
                .params("industryid", industryid)//行业ID


                .params("province", province)
                .params("city", city)
                .params("area", area)

                .params("sex", sex)
                .params("introduction", etDetailed.getText().toString())
                .params("alipaynumber", etAlipay.getText().toString())
                .params("weixin", etWeixin.getText().toString())
                .params("mail", etEmail.getText().toString())
                .params("price", etOncePrice.getText().toString())


                .addFileParams("certificatephoto", certificatephotoF)
                .addFileParams("certificateinspection", certificateinspectionF)
                .addFileParams("face",faceF)
//                .addFileParams("pic[]",pic)

                .params("username", username)

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


    /*
        * bitmap转file(原图转换)
        */
    public static String bitmapToFile(Context context,Bitmap bitmap) {
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

}
