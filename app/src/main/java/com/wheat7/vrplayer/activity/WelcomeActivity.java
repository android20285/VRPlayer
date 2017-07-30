package com.wheat7.vrplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wheat7.vrplayer.R;
import com.wheat7.vrplayer.base.BaseActivity;
import com.wheat7.vrplayer.databinding.ActivityWelcomeBinding;
import com.wheat7.vrplayer.utils.StatusBarUtil;

/**
 * Created by wheat7 on 2017/7/30.
 */

public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jump();
            }
        }, 3000);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    private void jump() {
        Intent intent = new Intent(this, VRListActivity.class);
        startActivity(intent);
        this.finish();;
    }
}
