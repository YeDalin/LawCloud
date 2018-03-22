package net.sanhedao.lawcloudserver.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.uitls.SharedUtils;

/**
 * Created by Administrator on 2018/1/31 0031.
 *
 * 资格认证
 *
 * @author ye
 *
 */

public class QualificationCertificationActivity extends Activity implements View.OnClickListener{


    private ImageView ivBack;
    private LinearLayout llLawyer,llAdviser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification_certification);

        initView();
        initClick();
    }

    private void initView() {
        ivBack=findViewById(R.id.iv_back);
        llLawyer=findViewById(R.id.ll_lawyer);
        llAdviser=findViewById(R.id.ll_adviser);
    }

    private void initClick(){
        ivBack.setOnClickListener(this);
        llLawyer.setOnClickListener(this);
        llAdviser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_lawyer:
                SharedUtils.setStringPrefs(this,"type","2");
                Intent intent=new Intent(this,LawyerCertificationActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_adviser:
                SharedUtils.setStringPrefs(this,"type","1");
                Intent intent1=new Intent(this,LawyerCertificationActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
