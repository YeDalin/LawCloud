package net.sanhedao.lawcloudserver.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sanhedao.lawcloudserver.R;

/**
 * Created by Administrator on 2018/2/2 0002.
 */

public class MassageFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_massage,null);
        return view;
    }
}
