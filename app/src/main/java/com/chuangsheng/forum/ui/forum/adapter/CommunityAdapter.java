package com.chuangsheng.forum.ui.forum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.home.adapter.GvImageAdapter;
import com.chuangsheng.forum.view.CircleImageView;
import com.chuangsheng.forum.view.MyGridView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityAdapter extends BaseAdapter {
    private Context context;
    public CommunityAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_forum_community,null);
        GridView gv_image = convertView.findViewById(R.id.gv_image);
        GvImageAdapter adapter = new GvImageAdapter(context,null);
        gv_image.setAdapter(adapter);
        return convertView;
    }
    static class ViewHolder {
        @BindView(R.id.iv_head)
        CircleImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.gv_image)
        MyGridView gv_image;
        @BindView(R.id.iv_singlePic)
        ImageView iv_singlePic;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
