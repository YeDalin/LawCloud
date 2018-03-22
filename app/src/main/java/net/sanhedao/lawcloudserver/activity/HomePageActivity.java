package net.sanhedao.lawcloudserver.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import net.sanhedao.lawcloudserver.R;
import net.sanhedao.lawcloudserver.fragment.HomePageFragment;
import net.sanhedao.lawcloudserver.fragment.MassageFragment;
import net.sanhedao.lawcloudserver.fragment.PersonalCenterFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/1/31 0031.
 *
 * 首页
 *
 * @author ye
 *
 */

public class HomePageActivity extends FullScreenActivity implements View.OnClickListener{


    private HomePageFragment homePageFragment;
    private MassageFragment massageFragment;
    private PersonalCenterFragment personalCenterFragment;
    private RadioButton rbHomePage,rbMassage,rbPersonalCenter;
    private List<Fragment> fragments=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();
        initClick();
    }


    private void initView() {
        rbHomePage=findViewById(R.id.rb_home);
        rbMassage=findViewById(R.id.rb_massage);
        rbPersonalCenter=findViewById(R.id.rb_personal_center);

        homePageFragment=new HomePageFragment();
        massageFragment=new MassageFragment();
        personalCenterFragment=new PersonalCenterFragment();

        fragments.add(homePageFragment);
        fragments.add(massageFragment);
        fragments.add(personalCenterFragment);

        initFragment();
    }


    private void initFragment() {
        if (fragments != null) {
            android.app.FragmentManager manager = getFragmentManager();
            android.app.FragmentTransaction transaction = manager.beginTransaction();

            for (int i = 0; i < fragments.size(); i++) {

                transaction.add(R.id.fragment, fragments.get(i));

            }

            transaction.show(homePageFragment).commit();
            showFragment(homePageFragment);
        }
    }


    private void showFragment(Fragment fragment) {
        for (int i = 0; i < fragments.size(); i++) {
            getFragmentManager().beginTransaction().hide(fragments.get(i)).commit();
        }
        getFragmentManager().beginTransaction().show(fragment).commit();
    }

    private void initClick(){
        rbHomePage.setOnClickListener(this);
        rbMassage.setOnClickListener(this);
        rbPersonalCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_home:
                rbOnClick(rbHomePage);
                showFragment(homePageFragment);
                break;
            case R.id.rb_massage:
                rbOnClick(rbMassage);
                showFragment(massageFragment);
                break;
            case R.id.rb_personal_center:
                rbOnClick(rbPersonalCenter);
                showFragment(personalCenterFragment);
                break;
        }
    }

    private void rbOnClick(RadioButton radioButton){
        rbHomePage.setChecked(false);
        rbMassage.setChecked(false);
        rbPersonalCenter.setChecked(false);

        radioButton.setChecked(true);
    }
}
