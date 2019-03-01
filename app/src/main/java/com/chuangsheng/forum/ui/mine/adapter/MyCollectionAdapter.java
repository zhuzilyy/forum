package com.chuangsheng.forum.ui.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.forum.bean.DetailForumInfo;
import com.chuangsheng.forum.ui.home.adapter.GvImageAdapter;
import com.chuangsheng.forum.ui.mine.bean.CollectionInfo;
import com.chuangsheng.forum.view.CircleImageView;
import com.chuangsheng.forum.view.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectionAdapter extends BaseAdapter {
    private Context context;
    private List<CollectionInfo> infoList;
    public MyCollectionAdapter(Context context, List<CollectionInfo> infoList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_forum_collection,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CollectionInfo collectionInfo = infoList.get(position);
        //帖子的图片的数量
        List<String> attachmentForum = collectionInfo.getAttachment();
        if (attachmentForum.size() == 0){
            viewHolder.iv_singlePic.setVisibility(View.GONE);
            viewHolder.gv_image.setVisibility(View.GONE);
        }else if(attachmentForum.size() == 1){
            viewHolder.iv_singlePic.setVisibility(View.VISIBLE);
            viewHolder.gv_image.setVisibility(View.GONE);
            Glide.with(context).applyDefaultRequestOptions(options).load(attachmentForum.get(0)).into(viewHolder.iv_singlePic);
        }else{
            viewHolder.iv_singlePic.setVisibility(View.GONE);
            viewHolder.gv_image.setVisibility(View.VISIBLE);
            GvImageAdapter adapter = new GvImageAdapter(context,attachmentForum);
            viewHolder.gv_image.setAdapter(adapter);
        }
        viewHolder.tv_name.setText(collectionInfo.getUser_username());
        Glide.with(context).load(collectionInfo.getUser_img()).into(viewHolder.iv_head);
        viewHolder.tv_time.setText(collectionInfo.getCreated());
        viewHolder.tv_content.setText(collectionInfo.getContent());
        viewHolder.tv_message.setText(collectionInfo.getComments());
        return convertView;
    }
    static class ViewHolder {
        @BindView(R.id.iv_head)
        CircleImageView iv_head;
        @BindView(R.id.iv_level)
        ImageView iv_level;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_message)
        TextView tv_message;
        @BindView(R.id.gv_image)
        MyGridView gv_image;
        @BindView(R.id.iv_singlePic)
        ImageView iv_singlePic;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
