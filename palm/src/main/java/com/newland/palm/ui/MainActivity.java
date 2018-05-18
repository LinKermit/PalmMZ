package com.newland.palm.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.newland.palm.R;
import com.newland.palm.ui.nav.NavFragment;
import com.newland.palm.ui.nav.NavigationButton;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements NavFragment.OnNavigationReselectListener {

    private static final String TAG = "MainActivity";
    NavFragment navFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        navFragment = (NavFragment) manager.findFragmentById(R.id.main_nav);
        navFragment.init(this,manager,R.id.main_container , this);
    }


    @Override
    public void onReselect(NavigationButton navigationButton) {
        Log.e(TAG, "onReselect: " + navigationButton.getFragment());
    }
}
