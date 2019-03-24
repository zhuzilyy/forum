package com.chuangsheng.forum.ui.forum.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.forum.bean.DetailForumInfo;
import com.chuangsheng.forum.ui.forum.bean.EaluationPicBean;
import com.chuangsheng.forum.ui.forum.bean.ForumParent;
import com.chuangsheng.forum.ui.forum.ui.ForumDetailActivity;
import com.chuangsheng.forum.ui.forum.ui.LookBigPicActivity;
import com.chuangsheng.forum.ui.home.adapter.GvImageAdapter;
import com.chuangsheng.forum.ui.mine.ui.WebviewActivity;
import com.chuangsheng.forum.util.LevelUtil;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.CircleImageView;
import com.chuangsheng.forum.view.LinkClickListener;
import com.chuangsheng.forum.view.LinkMovementMethodEx;
import com.chuangsheng.forum.view.MyGridView;
import com.chuangsheng.forum.view.MyListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumDetailAdapter extends BaseAdapter{
    private Context context;
    private List<DetailForumInfo> infoList;
    private DianZanListener dianZanListener;
    private adLinkClickListener adLinkClickListener;
    private nameClickListener nameClickListener;
    private picClickListener picClickListener;
    private gvClickListener gvClickListener;
    private pingLunClickListener pingLunClickListener;
    private int showAdPic;
    public ForumDetailAdapter(Context context, List<DetailForumInfo> infoList,int showAdPic) {
        this.context = context;
        this.infoList = infoList;
        this.showAdPic = showAdPic;
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

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        ForumParent forumParent = detailForumInfo.getParent();
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
        if (forumParent!=null){
            if (forumParent.getUser_id()!=null){
                viewHolder.tv_replyComment.setVisibility(View.VISIBLE);
                viewHolder.tv_replyComment.setText("引用:"+forumParent.getUser_username()+"发表于"+forumParent.getCreated()+"\n"+forumParent.getContent());
                viewHolder.tv_content.setText(detailForumInfo.getContent());
            }else{
                viewHolder.tv_replyComment.setVisibility(View.GONE);
                viewHolder.tv_content.setText(detailForumInfo.getContent());
            }
        }
        viewHolder.tv_name.setText(detailForumInfo.getUser_username());
        Glide.with(context).load(detailForumInfo.getUser_img()).into(viewHolder.iv_head);
        viewHolder.tv_time.setText(detailForumInfo.getCreated());
        //viewHolder.tv_content.setText(detailForumInfo.getContent());
        viewHolder.tv_floor.setText(position+1+"楼");
        viewHolder.tv_countZan.setText(detailForumInfo.getLikes());
        String like_status = detailForumInfo.getLike_status();
        //viewHolder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        if (like_status.equals("True")){
            viewHolder.iv_likeStatus.setImageResource(R.mipmap.dianzan_xuanzhong);
        }else{
            viewHolder.iv_likeStatus.setImageResource(R.mipmap.dianzan_hui);
        }
        if (position == 0 && showAdPic == 1){
            viewHolder.iv_advertisement.setVisibility(View.VISIBLE);
            Glide.with(context).load(infoList.get(position).getAdImg()).into(viewHolder.iv_advertisement);
        }else{
            viewHolder.iv_advertisement.setVisibility(View.GONE);
        }
        viewHolder.iv_level.setImageResource(LevelUtil.userLevel(detailForumInfo.getUser_points()));
        viewHolder.ll_dianzan.setTag(position);
        viewHolder.ll_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dianZanListener.click((int)v.getTag());
            }
        });
        viewHolder.iv_advertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adLinkClickListener.click();
            }
        });
        viewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameClickListener.click(position);
            }
        });
        viewHolder.iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameClickListener.click(position);
            }
        });
        viewHolder.iv_singlePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //picClickListener.click(position);
                List<String> attachment = infoList.get(position).getAttachment();
                Intent intent = new Intent(context, LookBigPicActivity.class);
                List<EaluationPicBean> list = new ArrayList<>();
                EaluationPicBean ealuationPicBean = new EaluationPicBean();
                ealuationPicBean.imageUrl =attachment.get(0);
                list.add(ealuationPicBean);
                Bundle bundle = new Bundle();
                bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable)list);
                intent.putExtra("CURRENTITEM",0);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        viewHolder.gv_image.setTag(position);
        viewHolder.gv_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int selectTag, long id) {
                int tag = (int)parent.getTag();
                gvClickListener.click(tag,selectTag);
            }
        });
        viewHolder.iv_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pingLunClickListener.click(position);
            }
        });
        CharSequence content = viewHolder.tv_content.getText();
        if (content instanceof Spannable) {
            int end = content.length();
            Spannable sp = (Spannable) content;
            URLSpan urls[] = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(content);
            style.clearSpans();
            for (URLSpan urlSpan : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(urlSpan.getURL());
                style.setSpan(myURLSpan, sp.getSpanStart(urlSpan),
                        sp.getSpanEnd(urlSpan),
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

            }
            viewHolder.tv_content.setText(style);
        }
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
        @BindView(R.id.tv_replyComment)
        TextView tv_replyComment;
        @BindView(R.id.gv_image)
        MyGridView gv_image;
        @BindView(R.id.iv_singlePic)
        ImageView iv_singlePic;
        @BindView(R.id.iv_likeStatus)
        ImageView iv_likeStatus;
        @BindView(R.id.iv_advertisement)
        ImageView iv_advertisement;
        @BindView(R.id.iv_pinglun)
        ImageView iv_pinglun;
        @BindView(R.id.iv_level)
        ImageView iv_level;
        @BindView(R.id.ll_dianzan)
        LinearLayout ll_dianzan;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    public class MyURLSpan extends ClickableSpan {
        private String url;
        public MyURLSpan(String url) {
            this.url = url;
        }

        /**
         * @param arg0
         */
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context,WebviewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("url",url);
            bundle.putString("title","详情");
            intent.putExtras(bundle);
            context.startActivity(intent,bundle);
        }
    }
    public interface DianZanListener{
        void click(int position);
    }
    public void setDianZanClickListener(DianZanListener dianZanClickListener){
        this.dianZanListener = dianZanClickListener;
    }
    public interface adLinkClickListener{
        void click();
    }
    public void setAdLinkClickListener(adLinkClickListener adLinkClickListener){
        this.adLinkClickListener = adLinkClickListener;
    }
    public interface nameClickListener{
        void click(int position);
    }
    public void setNameClickListener(nameClickListener nameClickListener){
        this.nameClickListener = nameClickListener;
    }
    public interface picClickListener{
        void click(int position);
    }
    public void setPicClickListener(picClickListener picClickListener){
        this.picClickListener = picClickListener;
    }
    public interface gvClickListener{
        void click(int position,int tag);
    }
    public void setGvClickListener(gvClickListener gvClickListener){
        this.gvClickListener = gvClickListener;
    }
    public interface pingLunClickListener{
        void click(int position);
    }
    public void setPingLunClickListener(pingLunClickListener pingLunClickListener){
        this.pingLunClickListener = pingLunClickListener;
    }


}
