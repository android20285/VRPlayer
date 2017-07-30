package com.wheat7.vrplayer.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wheat7.vrplayer.BR;
import com.wheat7.vrplayer.R;
import com.wheat7.vrplayer.activity.VRPlayerActivity;
import com.wheat7.vrplayer.databinding.ItemVrListBinding;
import com.wheat7.vrplayer.model.VRVideo;

import java.io.File;
import java.util.List;

/**
 * Created by wheat7 on 05/07/2017.
 */

public class VRAdapter extends RecyclerView.Adapter<VRAdapter.ViewHolder> {

    private List<VRVideo> mVRVideoList;

    public VRAdapter(List<VRVideo> data) {
        this.mVRVideoList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVrListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_vr_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        binding.setViewHolder(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mVRVideoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mVRVideoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private VRVideo mData;
        private ItemVrListBinding viewDataBinding;

        public ViewHolder(ItemVrListBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        public void bindData(VRVideo vrVideo) {
            mData = vrVideo;
            viewDataBinding.setVariable(BR.data, vrVideo);
            Glide.with(itemView.getContext())
                    .load(Uri.fromFile(new File(mData.getPath())))
                    .into(viewDataBinding.vrImage);
            viewDataBinding.executePendingBindings();
        }

        public void onItemClick(View view) {
            Intent intent = new Intent(view.getContext(), VRPlayerActivity.class);
            intent.putExtra("path", mData.getPath());
            view.getContext().startActivity(intent);
        }

    }
}
