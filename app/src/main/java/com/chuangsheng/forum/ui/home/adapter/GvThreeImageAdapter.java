package com.chuangsheng.forum.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuangsheng.forum.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GvThreeImageAdapter extends BaseAdapter {
    private Context context;
    private List<String> imageList;
    public GvThreeImageAdapter(Context context, List<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }
    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.pic);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gv_muti_picture,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(imageList.get(position)).apply(options).into(viewHolder.imageview);
        return convertView;
    }
    static class ViewHolder{
        @BindView(R.id.imageview)
        ImageView imageview;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
