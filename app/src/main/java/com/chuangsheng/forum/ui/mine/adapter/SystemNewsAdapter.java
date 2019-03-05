package com.chuangsheng.forum.ui.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.mine.bean.SystemNewsInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemNewsAdapter extends BaseAdapter {
    private Context context;
    private List<SystemNewsInfo> infoList;
    public SystemNewsAdapter(Context context, List<SystemNewsInfo> infoList) {
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
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.pic);
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_system_news,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SystemNewsInfo systemNewsInfo = infoList.get(position);
        viewHolder.tv_content.setText(systemNewsInfo.getContent());
        viewHolder.tv_time.setText(systemNewsInfo.getCreate_date());
        if (systemNewsInfo.getAttachment().size()>0){
            viewHolder.iv_pic.setVisibility(View.VISIBLE);
            Glide.with(context).applyDefaultRequestOptions(options).load(systemNewsInfo.getAttachment().get(0)).into(viewHolder.iv_pic);
        }else{
            viewHolder.iv_pic.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.iv_pic)
        ImageView iv_pic;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
