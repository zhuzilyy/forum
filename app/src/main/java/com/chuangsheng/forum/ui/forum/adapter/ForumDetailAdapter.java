package com.chuangsheng.forum.ui.forum.adapter;

import android.content.Context;
import android.util.Log;
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
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.ui.mine.adapter.MyReplyFroumAdapter;
import com.chuangsheng.forum.ui.mine.bean.MyReplyFroumInfo;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.CircleImageView;
import com.chuangsheng.forum.view.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForumDetailAdapter extends BaseAdapter{
    private Context context;
    private List<DetailForumInfo> infoList;
    private DianZanListener dianZanListener;
    public ForumDetailAdapter(Context context, List<DetailForumInfo> infoList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_forum_detail,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DetailForumInfo detailForumInfo = infoList.get(position);
        //帖子的图片的数量
        List<String> attachmentForum = detailForumInfo.getAttachment();
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
        viewHolder.tv_name.setText(detailForumInfo.getUser_username());
        Glide.with(context).load(detailForumInfo.getUser_img()).into(viewHolder.iv_head);
        viewHolder.tv_time.setText(detailForumInfo.getCreated());
        viewHolder.tv_content.setText(detailForumInfo.getContent());
        viewHolder.tv_floor.setText(position+1+"楼");
        viewHolder.tv_countZan.setText(detailForumInfo.getLikes());
        String like_status = detailForumInfo.getLike_status();
        if (like_status.equals("True")){
            viewHolder.iv_likeStatus.setImageResource(R.mipmap.dianzan_xuanzhong);
        }else{
            viewHolder.iv_likeStatus.setImageResource(R.mipmap.dianzan_hui);
        }
        if (position == 0){
            viewHolder.iv_advertisement.setVisibility(View.VISIBLE);
            Glide.with(context).load(infoList.get(position).getAdImg()).into(viewHolder.iv_advertisement);
        }else{
            viewHolder.iv_advertisement.setVisibility(View.GONE);
        }
        viewHolder.ll_dianzan.setTag(position);
        viewHolder.ll_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dianZanListener.click((int)v.getTag());
            }
        });
        return convertView;
    }
    static class ViewHolder {
        @BindView(R.id.iv_head)
        CircleImageView iv_head;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_countZan)
        TextView tv_countZan;
        @BindView(R.id.tv_floor)
        TextView tv_floor;
        @BindView(R.id.gv_image)
        MyGridView gv_image;
        @BindView(R.id.iv_singlePic)
        ImageView iv_singlePic;
        @BindView(R.id.iv_likeStatus)
        ImageView iv_likeStatus;
        @BindView(R.id.iv_advertisement)
        ImageView iv_advertisement;
        @BindView(R.id.ll_dianzan)
        LinearLayout ll_dianzan;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    public interface DianZanListener{
        void click(int position);
    }
    public void setDianZanClickListener(DianZanListener dianZanClickListener){
        this.dianZanListener = dianZanClickListener;
    }
}
