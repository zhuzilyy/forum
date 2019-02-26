package com.chuangsheng.forum.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends BaseAdapter {
    private final int NO_PICTURE = 0;
    private final int SINGLE_PICTURE = 1;
    private final int MUTI_PICTURE = 2;
    private Context context;
    private List<HomeFroumInfo> infoList;
    public HomeAdapter(Context context, List<HomeFroumInfo> infoList) {
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
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        int picSize = infoList.get(position).getAttachment().size();
        if (picSize == 0){
            return NO_PICTURE;
        }else if(picSize == 1){
            return SINGLE_PICTURE;
        }else{
            return MUTI_PICTURE;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MutiPictureViewHolder mutiPictureViewHolder = null;
        OnePictureViewHolder onePictureViewHolder = null;
        NoPictureViewHolder noPictureViewHolder = null;
        int itemViewType = getItemViewType(position);
        //没有图片
        if (itemViewType == NO_PICTURE){
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_froum_no_picture,null);
                noPictureViewHolder = new NoPictureViewHolder(convertView);
                convertView.setTag(noPictureViewHolder);
            }else{
                noPictureViewHolder = (NoPictureViewHolder) convertView.getTag();
            }
            HomeFroumInfo homeFroumInfo = infoList.get(position);
            Glide.with(context).load(homeFroumInfo.getUser_img()).into(noPictureViewHolder.iv_head);
            noPictureViewHolder.tv_name.setText(homeFroumInfo.getUser_username());
            noPictureViewHolder.tv_title.setText(homeFroumInfo.getSubject());
            noPictureViewHolder.tv_content.setText(homeFroumInfo.getContent());
            noPictureViewHolder.tv_time.setText(homeFroumInfo.getCreated());
            noPictureViewHolder.tv_browse.setText(homeFroumInfo.getClick());
            noPictureViewHolder.tv_message.setText(homeFroumInfo.getComments());
        }else if(itemViewType == SINGLE_PICTURE){
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_froum_one_picture,null);
                onePictureViewHolder = new OnePictureViewHolder(convertView);
                convertView.setTag(onePictureViewHolder);
            }else{
                onePictureViewHolder = (OnePictureViewHolder) convertView.getTag();
            }
            HomeFroumInfo homeFroumInfo = infoList.get(position);
            Glide.with(context).load(homeFroumInfo.getUser_img()).into(onePictureViewHolder.iv_head);
            Glide.with(context).load(homeFroumInfo.getAttachment().get(0)).into(onePictureViewHolder.iv_commentImg);
            onePictureViewHolder.tv_name.setText(homeFroumInfo.getUser_username());
            onePictureViewHolder.tv_title.setText(homeFroumInfo.getSubject());
            onePictureViewHolder.tv_content.setText(homeFroumInfo.getContent());
            onePictureViewHolder.tv_time.setText(homeFroumInfo.getCreated());
            onePictureViewHolder.tv_browse.setText(homeFroumInfo.getClick());
            onePictureViewHolder.tv_message.setText(homeFroumInfo.getComments());
        }else if(itemViewType == MUTI_PICTURE){
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_froum_muti_picture,null);
                mutiPictureViewHolder = new MutiPictureViewHolder(convertView);
                convertView.setTag(mutiPictureViewHolder);
            }else{
                mutiPictureViewHolder = (MutiPictureViewHolder) convertView.getTag();
            }
            HomeFroumInfo homeFroumInfo = infoList.get(position);
            Glide.with(context).load(homeFroumInfo.getUser_img()).into(mutiPictureViewHolder.iv_head);
            mutiPictureViewHolder.tv_name.setText(homeFroumInfo.getUser_username());
            mutiPictureViewHolder.tv_title.setText(homeFroumInfo.getSubject());
            mutiPictureViewHolder.tv_content.setText(homeFroumInfo.getContent());
            mutiPictureViewHolder.tv_time.setText(homeFroumInfo.getCreated());
            mutiPictureViewHolder.tv_browse.setText(homeFroumInfo.getClick());
            mutiPictureViewHolder.tv_message.setText(homeFroumInfo.getComments());
            GvImageAdapter adapter = new GvImageAdapter(context,infoList.get(position).getAttachment());
            mutiPictureViewHolder.gv_image.setAdapter(adapter);
        }
        return convertView;
    }
    static class NoPictureViewHolder{
        @BindView(R.id.iv_head)
        CircleImageView iv_head;
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
        @BindView(R.id.tv_browse)
        TextView tv_browse;
        public NoPictureViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    static class OnePictureViewHolder{
        @BindView(R.id.iv_head)
        CircleImageView iv_head;
        @BindView(R.id.iv_commentImg)
        ImageView iv_commentImg;
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
        @BindView(R.id.tv_browse)
        TextView tv_browse;
        public OnePictureViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    static class MutiPictureViewHolder{
        @BindView(R.id.gv_image)
        GridView gv_image;
        @BindView(R.id.iv_head)
        CircleImageView iv_head;
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
        @BindView(R.id.tv_browse)
        TextView tv_browse;
        public MutiPictureViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}