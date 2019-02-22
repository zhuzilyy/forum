package com.chuangsheng.forum.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.chuangsheng.forum.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends BaseAdapter {
    private final int NO_PICTURE = 1;
    private final int SINGLE_PICTURE = 2;
    private final int MUTI_PICTURE = 3;
    private Context context;
    public HomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /*@Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return  position%4;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MutiPictureViewHolder mutiPictureViewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_froum_muti_picture,null);
            mutiPictureViewHolder = new MutiPictureViewHolder(convertView);
            convertView.setTag(mutiPictureViewHolder);
        }else{
            mutiPictureViewHolder = (MutiPictureViewHolder) convertView.getTag();
        }
        GvImageAdapter adapter = new GvImageAdapter(context);
        mutiPictureViewHolder.gv_image.setAdapter(adapter);
        return convertView;
    }
    static class NoPictureViewHolder{
        public NoPictureViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    static class MutiPictureViewHolder{
        @BindView(R.id.gv_image)
        GridView gv_image;
        public MutiPictureViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
