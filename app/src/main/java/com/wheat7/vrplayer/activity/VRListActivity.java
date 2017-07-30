package com.wheat7.vrplayer.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.wheat7.vrplayer.R;
import com.wheat7.vrplayer.adapter.VRAdapter;
import com.wheat7.vrplayer.base.BaseActivity;
import com.wheat7.vrplayer.databinding.ActivityVrListBinding;
import com.wheat7.vrplayer.model.VRVideo;
import com.wheat7.vrplayer.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wheat7 on 05/07/2017.
 */

public class VRListActivity extends BaseActivity<ActivityVrListBinding> implements SwipeRefreshLayout.OnRefreshListener {
    private List<VRVideo> mVRVideoList;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int REQUEST_PERMISSIONS = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vr_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        checkPermission();
        getBinding().swipeRefresh.setOnRefreshListener(this);
    }


    @Override
    protected void setStatusBar() {
    }

    @Override
    public void onRefresh() {
        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    REQUEST_PERMISSIONS);
        }else {
            scanVideo();
        }
    }

        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
            switch (requestCode) {
                case REQUEST_PERMISSIONS: {
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        scanVideo();
                    }
                    }
                    return;
                }
            }
    /**
     * 遍历视频文件
     */
    private void scanVideo() {
        ContentResolver contentResolver = getContentResolver();
        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DATE_MODIFIED,
               MediaStore.Video.Media.SIZE,
        };

        Cursor cursor = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);

        //int allVideo = cursor.getCount();
        mVRVideoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            VRVideo mVRVideo = new VRVideo();
            mVRVideo.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
            mVRVideo.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)));
            mVRVideo.setDurationShow(StringUtils.millisToTime(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))));
            mVRVideo.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
            mVRVideo.setDateModified(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED)));
            long len = new File((cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)))).length();
            mVRVideo.setFileLength(StringUtils.convertFileSize(len));
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
            mVRVideoList.add(mVRVideo);
        }
        cursor.close();
        VRAdapter adapter = new VRAdapter(mVRVideoList);
        getBinding().recycler.setLayoutManager(new LinearLayoutManager(this));
        getBinding().recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getBinding().swipeRefresh.setRefreshing(false);
    }
}
