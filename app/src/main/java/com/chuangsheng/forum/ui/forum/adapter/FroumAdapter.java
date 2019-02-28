package com.chuangsheng.forum.ui.forum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.forum.bean.CommunityInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FroumAdapter extends BaseAdapter {
    private Context context;
    private List<CommunityInfo> infoList;
    public FroumAdapter(Context context, List<CommunityInfo> infoList) {
        this.context = context;
        this.infoList = infoList;
    }
    @Override
    public int getCount() {
        return infoList.size();
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_froum,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommunityInfo communityInfo = infoList.get(position);
        Glide.with(context).load(communityInfo.getImg()).into(viewHolder.iv_forum);
        viewHolder.tv_title.setText(communityInfo.getName());
        viewHolder.tv_desc.setText(communityInfo.getDesc());
        viewHolder.tv_count.setText(" 主题: "+communityInfo.getComment()+" 帖子 :"+communityInfo.getDiscussion());
        return convertView;
    }
    static class ViewHolder {
        @BindView(R.id.iv_forum)
        ImageView iv_forum;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_count)
        TextView tv_count;
        @BindView(R.id.tv_desc)
        TextView tv_desc;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
