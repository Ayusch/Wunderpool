package com.ayusch.wunderassignment.fragments.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends Fragment {

    public Activity activity;
    public Context context;
    public Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activity=(Activity) context;

        }
        this.context = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }

    }
}
