package com.ayusch.wunderassignment.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ayusch.wunderassignment.R;

import butterknife.Unbinder;

public abstract class BaseBottomNavigationActivity extends AppCompatActivity {

    public BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    onCarsFragmentSelected(item);
                    return true;
                case R.id.navigation_map:
                    onMapsFragmentSelected(item);
                    return true;

            }
            return false;
        }
    };

    Unbinder unbinder;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
         navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public abstract int getLayoutId();

    protected abstract void onMapsFragmentSelected(MenuItem item);

    protected abstract void onCarsFragmentSelected(MenuItem item);

}
