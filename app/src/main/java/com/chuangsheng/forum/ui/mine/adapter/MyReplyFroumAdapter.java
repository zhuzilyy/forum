package com.chuangsheng.forum.ui.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.home.adapter.GvImageAdapter;
import com.chuangsheng.forum.ui.home.adapter.GvThreeImageAdapter;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.ui.mine.bean.MyReplyFroumInfo;
import com.chuangsheng.forum.util.LevelUtil;
import com.chuangsheng.forum.view.CircleImageView;
import com.chuangsheng.forum.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyReplyFroumAdapter extends BaseAdapter {
    private Context context;
    private List<MyReplyFroumInfo> infoList;
    private headClickListener headClickListener;
    private itemClickListener itemClickListener;
    public MyReplyFroumAdapter(Context context, List<MyReplyFroumInfo> infoList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.pic);
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_reply_froums,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyReplyFroumInfo myReplyFroumInfo = infoList.get(position);
        HomeFroumInfo discussion = myReplyFroumInfo.getDiscussion();
        //帖子的图片的数量
        List<String> attachmentForum = discussion.getAttachment();
        if (attachmentForum.size() == 0){
            viewHolder.iv_commentImgFroum.setVisibility(View.GONE);
            viewHolder.gv_imageFroum.setVisibility(View.GONE);
        }else if(attachmentForum.size() == 1){
            viewHolder.iv_commentImgFroum.setVisibility(View.VISIBLE);
            viewHolder.gv_imageFroum.setVisibility(View.GONE);
            //帖子一张图片显示的情况下
            Glide.with(context).load(discussion.getAttachment().get(0)).apply(options).into(viewHolder.iv_commentImgFroum);
        }else{
            viewHolder.iv_commentImgFroum.setVisibility(View.GONE);
            viewHolder.gv_imageFroum.setVisibility(View.VISIBLE);
            //帖子多图显示的情况下
            List<String> attachment = discussion.getAttachment();
            List<String> imageList = new ArrayList<>();
            if (attachment.size()>=3){
                imageList.add(attachment.get(0));
                imageList.add(attachment.get(1));
                imageList.add(attachment.get(2));
            }else{
                for (int i = 0; i <attachment.size() ; i++) {
                    imageList.add(attachment.get(i));
                }
            }
            GvThreeImageAdapter adapter = new GvThreeImageAdapter(context,imageList);
            viewHolder.gv_imageFroum.setAdapter(adapter);
        }
        //评论的图片的数量
        List<String> attachmentComment = myReplyFroumInfo.getAttachment();
        if (attachmentComment.size() == 0){
            viewHolder.iv_commentImg.setVisibility(View.GONE);
            viewHolder.gv_image.setVisibility(View.GONE);
        }else if(attachmentComment.size() == 1){
            viewHolder.iv_commentImg.setVisibility(View.VISIBLE);
            viewHolder.gv_image.setVisibility(View.GONE);
            //评论一张图片显示的情况下
            Glide.with(context).load(myReplyFroumInfo.getAttachment().get(0)).apply(options).into(viewHolder.iv_commentImg);
        }else{
            viewHolder.iv_commentImg.setVisibility(View.GONE);
            viewHolder.gv_image.setVisibility(View.VISIBLE);
            List<String> imageList = new ArrayList<>();
            if (attachmentForum.size()>=3){
                imageList.add(attachmentForum.get(0));
                imageList.add(attachmentForum.get(1));
                imageList.add(attachmentForum.get(2));
            }else{
                for (int i = 0; i <attachmentForum.size() ; i++) {
                    imageList.add(attachmentForum.get(i));
                }
            }
            //评论多图显示的情况下
            GvThreeImageAdapter adapter = new GvThreeImageAdapter(context, imageList);
            viewHolder.gv_image.setAdapter(adapter);
        }
        Glide.with(context).load(myReplyFroumInfo.getUser_img()).apply(options).into(viewHolder.iv_head);
        viewHolder.tv_name.setText(discussion.getUser_username());
        viewHolder.tv_time.setText(discussion.getCreated());
        viewHolder.tv_title.setText(discussion.getSubject());
        viewHolder.tv_content.setText(discussion.getContent());
        viewHolder.tv_comment.setText(myReplyFroumInfo.getContent());
        viewHolder.iv_level.setImageResource(LevelUtil.userLevel(myReplyFroumInfo.getUser_points()));
        viewHolder.tv_name.setTag(position);
        viewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int)v.getTag();
                if (headClickListener!=null){
                    headClickListener.headClick(tag);
                }
            }
        });
        viewHolder.gv_imageFroum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int tag, long id) {
                itemClickListener.click(position);
            }
        });

        return convertView;
    }
    static class ViewHolder{
        @BindView(R.id.iv_head)
        CircleImageView iv_head;
        @BindView(R.id.iv_level)
        ImageView iv_level;
        @BindView(R.id.iv_commentImgFroum)
        ImageView iv_commentImgFroum;
        @BindView(R.id.iv_commentImg)
        ImageView iv_commentImg;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_comment)
        TextView tv_comment;
        @BindView(R.id.gv_imageFroum)
        MyGridView gv_imageFroum;
        @BindView(R.id.gv_image)
        MyGridView gv_image;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    public interface  headClickListener{
        void headClick(int postion);
    }
    public void setHeadClickListener(headClickListener headClickListener){
        this.headClickListener = headClickListener;
    }
    public interface itemClickListener{
        void click(int position);
    }
    public void setItemClickListener(itemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
