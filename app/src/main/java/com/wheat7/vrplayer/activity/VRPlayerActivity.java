package com.wheat7.vrplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.wheat7.vrplayer.R;
import com.wheat7.vrplayer.base.BaseActivity;
import com.wheat7.vrplayer.databinding.ActivityVrPlayerBinding;

/**
 * Created by wheat7 on 2017/7/18.
 */

public class VRPlayerActivity extends BaseActivity<ActivityVrPlayerBinding> {


    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_vr_player;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getBinding().setActivity(this);
        Intent intent = getIntent();
        String urlStr = intent.getStringExtra("path");
        getBinding().player.setVideoPath(urlStr);
        getBinding().player.setMediaControllerTitle(getBinding().mediacontrollerTitle);
    }

    public void onBackClick() {
        this.finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getBinding().player.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getBinding().player.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBinding().player.onResume();
    }


}
