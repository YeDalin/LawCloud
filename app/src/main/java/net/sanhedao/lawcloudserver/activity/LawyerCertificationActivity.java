package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.makeramen.roundedimageview.RoundedImageView;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.log.Logger;
import net.sanhedao.lawcloudserver.uitls.PhotoUtils;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Administrator on 2018/1/31 0031.
 *
 * 律师认证
 *
 * @author ye
 *
 */


public class LawyerCertificationActivity extends Activity implements View.OnClickListener{

    private RelativeLayout rlUploadPhoto,rlPhotoName,rlPhotoYear;
    private CircleImageView civPhoto;
    private RoundedImageView rivPhotoName,rivPhotoYear;
    private EditText etName,etWorkTime,etWorkLocation;
    private TextView tvNext;
    private ImageView ivBack;
    private LinearLayout llMain;
    private CheckBox sChoose;


    private String userPath="",lawNamePath="",lawPhotoPath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_certification);
        initView();
        initClick();
    }

    private void initView() {
        rlUploadPhoto=findViewById(R.id.rl_upload_photo);
        rlPhotoName=findViewById(R.id.rl_photo_name);
        rlPhotoYear=findViewById(R.id.rl_photo_year);
        civPhoto=findViewById(R.id.civ_photo);
        rivPhotoName=findViewById(R.id.riv_photo_name);
        rivPhotoYear=findViewById(R.id.riv_photo_year);
        etName=findViewById(R.id.et_name);
        etWorkTime=findViewById(R.id.et_work_time);
        etWorkLocation=findViewById(R.id.et_work_location);
        tvNext=findViewById(R.id.tv_next);
        ivBack=findViewById(R.id.iv_back);

        llMain=findViewById(R.id.ll_main);
        sChoose=findViewById(R.id.s_choose);

        String type = SharedUtils.getStringPrefs(this, "type", "");
        if(type.equals("1")){
            llMain.setVisibility(View.VISIBLE);
        }else {
            llMain.setVisibility(View.GONE);
        }

    }


    private void initClick(){
        rlUploadPhoto.setOnClickListener(this);
        rlPhotoName.setOnClickListener(this);
        rlPhotoYear.setOnClickListener(this);

        etWorkTime.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_upload_photo:
                getPhoto(1001);
                break;
            case R.id.rl_photo_name:
                getPhoto(1002);
                break;
            case R.id.rl_photo_year:
                getPhoto(1003);
                break;
            case R.id.et_work_time:

                //关闭键盘
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                chooseYeas();

                break;
            case R.id.tv_next:

                if(isNext()){
                    layData();
                    Intent intent=new Intent(this,SetActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this,R.string.law_data_no,Toast.LENGTH_SHORT).show();
                }



                break;
        }
    }



    //调取本地图片
    private void getPhoto(int requestCode){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null){
            if(requestCode==1001){
                userPath = photoPath(data);
                setPhoto(civPhoto,userPath);
            }else if(requestCode==1002){
                lawNamePath = photoPath(data);
                setPhoto(rivPhotoName,lawNamePath);
            }else if(requestCode==1003){
                lawPhotoPath = photoPath(data);
                setPhoto(rivPhotoYear,lawPhotoPath);
            }
        }


    }

    //获取选择图片的路径
    private String photoPath(Intent data){

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
    private void setPhoto(ImageView imageView,String photoPath){

        int width = imageView.getWidth();
        int height = imageView.getHeight();

        Logger.e("图片控件的宽高： w= "+width+"  h= "+height);

        Bitmap bitmap = PhotoUtils.getFitSampleBitmap(photoPath, width, height);
        imageView.setImageBitmap(bitmap);

    }

    //选择工作年限
    private OptionsPickerView pickerView;
    private void chooseYeas() {

        pickerView=new OptionsPickerView(this);
        pickerView.setTitle("年份");

        final ArrayList<String> list=new ArrayList<>();
        list.add("0~1");
        list.add("1~3");
        list.add("3~5");
        list.add("5~10");
        list.add("10年以上");

        pickerView.setPicker(list);
        pickerView.setCyclic(false);

        pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String s = list.get(options1);
                etWorkTime.setText(s);
            }
        });

        pickerView.show();

    }

    //判断信息是否完善
    private boolean isNext(){

        if(userPath.length()==0){
            return false;
        }
        if(etName.getText().toString().length()==0){
            return false;
        }

        if(etWorkTime.getText().toString().length()==0){
            return false;
        }

        if(etWorkLocation.getText().toString().length()==0){
            return false;
        }

        if(lawNamePath.length()==0){
            return false;
        }

        if(lawPhotoPath.length()==0){
            return false;
        }

        return true;
    }

    //保存信息
    private void layData(){
        SharedUtils.setStringPrefs(this,"practicestartime",etWorkTime.getText().toString());
        SharedUtils.setStringPrefs(this,"practicelaw",etWorkLocation.getText().toString());
        SharedUtils.setStringPrefs(this,"username",etName.getText().toString());
        SharedUtils.setStringPrefs(this,"face",userPath);
        SharedUtils.setStringPrefs(this,"certificatephoto",lawNamePath);
        SharedUtils.setStringPrefs(this,"certificateinspection",lawPhotoPath);

        boolean checked = sChoose.isChecked();
        Logger.e(" 主页 "+checked);
        SharedUtils.setBooleanPrefs(this,"checked",checked);

    }

}
