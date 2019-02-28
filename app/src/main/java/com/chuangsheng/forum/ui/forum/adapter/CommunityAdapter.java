package com.chuangsheng.forum.ui.forum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.home.adapter.GvImageAdapter;

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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_forum_community_muti_pic,null);
        GridView gv_image = convertView.findViewById(R.id.gv_image);
        GvImageAdapter adapter = new GvImageAdapter(context,null);
        gv_image.setAdapter(adapter);
        return convertView;
    }
}
