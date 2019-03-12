package com.chuangsheng.forum.ui.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.home.adapter.GvImageAdapter;
import com.chuangsheng.forum.ui.home.adapter.GvThreeImageAdapter;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.view.CircleImageView;
import com.chuangsheng.forum.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends BaseAdapter {
    private final int NO_PICTURE = 0;
    private final int SINGLE_PICTURE = 1;
    private final int MUTI_PICTURE = 2;
    private Context context;
    private String tag;
    private List<HomeFroumInfo> infoList;
    private itemClickListener itemClickListener;
    public SearchAdapter(Context context, List<HomeFroumInfo> infoList, String tag) {
        this.context = context;
        this.infoList = infoList;
        this.tag = tag;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        MutiPictureViewHolder mutiPictureViewHolder = null;
        OnePictureViewHolder onePictureViewHolder = null;
        NoPictureViewHolder noPictureViewHolder = null;
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.pic);
        int itemViewType = getItemViewType(position);
        //没有图片
        if (itemViewType == NO_PICTURE){
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_search_no_picture,null);
                noPictureViewHolder = new NoPictureViewHolder(convertView);
                convertView.setTag(noPictureViewHolder);
            }else{
                noPictureViewHolder = (NoPictureViewHolder) convertView.getTag();
            }
            HomeFroumInfo homeFroumInfo = infoList.get(position);
            Glide.with(context).load(homeFroumInfo.getUser_img()).apply(options).into(noPictureViewHolder.iv_head);
            noPictureViewHolder.tv_name.setText(homeFroumInfo.getUser_username());
            noPictureViewHolder.tv_title.setText(homeFroumInfo.getSubject());
            noPictureViewHolder.tv_content.setText(homeFroumInfo.getContent());
            noPictureViewHolder.tv_time.setText(homeFroumInfo.getCreated());
            noPictureViewHolder.tv_browse.setText(homeFroumInfo.getClick());
            noPictureViewHolder.tv_message.setText(homeFroumInfo.getComments());
            if (tag.equals("精华")){
                noPictureViewHolder.iv_jinghua.setVisibility(View.VISIBLE);
            }else{
                noPictureViewHolder.iv_jinghua.setVisibility(View.GONE);
            }
        }else if(itemViewType == SINGLE_PICTURE){
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_search_one_picture,null);
                onePictureViewHolder = new OnePictureViewHolder(convertView);
                convertView.setTag(onePictureViewHolder);
            }else{
                onePictureViewHolder = (OnePictureViewHolder) convertView.getTag();
            }
            HomeFroumInfo homeFroumInfo = infoList.get(position);
            Glide.with(context).load(homeFroumInfo.getUser_img()).apply(options).into(onePictureViewHolder.iv_head);
            Glide.with(context).load(homeFroumInfo.getAttachment().get(0)).apply(options).into(onePictureViewHolder.iv_commentImg);
            onePictureViewHolder.tv_name.setText(homeFroumInfo.getUser_username());
            onePictureViewHolder.tv_title.setText(homeFroumInfo.getSubject());
            onePictureViewHolder.tv_content.setText(homeFroumInfo.getContent());
            onePictureViewHolder.tv_time.setText(homeFroumInfo.getCreated());
            onePictureViewHolder.tv_browse.setText(homeFroumInfo.getClick());
            onePictureViewHolder.tv_message.setText(homeFroumInfo.getComments());
            if (tag.equals("精华")){
                onePictureViewHolder.iv_jinghua.setVisibility(View.VISIBLE);
            }else{
                onePictureViewHolder.iv_jinghua.setVisibility(View.GONE);
            }
        }else if(itemViewType == MUTI_PICTURE){
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_search_muti_picture,null);
                mutiPictureViewHolder = new MutiPictureViewHolder(convertView);
                convertView.setTag(mutiPictureViewHolder);
            }else{
                mutiPictureViewHolder = (MutiPictureViewHolder) convertView.getTag();
            }
            HomeFroumInfo homeFroumInfo = infoList.get(position);
            Glide.with(context).load(homeFroumInfo.getUser_img()).apply(options).into(mutiPictureViewHolder.iv_head);
            mutiPictureViewHolder.tv_name.setText(homeFroumInfo.getUser_username());
            mutiPictureViewHolder.tv_title.setText(homeFroumInfo.getSubject());
            mutiPictureViewHolder.tv_content.setText(homeFroumInfo.getContent());
            mutiPictureViewHolder.tv_time.setText(homeFroumInfo.getCreated());
            mutiPictureViewHolder.tv_browse.setText(homeFroumInfo.getClick());
            mutiPictureViewHolder.tv_message.setText(homeFroumInfo.getComments());
            if (tag.equals("精华")){
                mutiPictureViewHolder.iv_jinghua.setVisibility(View.VISIBLE);
            }else{
                mutiPictureViewHolder.iv_jinghua.setVisibility(View.GONE);
            }
            List<String> attachment = infoList.get(position).getAttachment();
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
            GvThreeImageAdapter adapter = new GvThreeImageAdapter(context, imageList);
            mutiPictureViewHolder.gv_image.setAdapter(adapter);
            mutiPictureViewHolder.gv_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int tag, long id) {
                    itemClickListener.click(position);
                }
            });
        }
        return convertView;
    }
    static class NoPictureViewHolder{
        @BindView(R.id.iv_head)
        CircleImageView iv_head;
        @BindView(R.id.iv_jinghua)
        ImageView iv_jinghua;
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
        @BindView(R.id.iv_jinghua)
        ImageView iv_jinghua;
        public OnePictureViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    static class MutiPictureViewHolder{
        @BindView(R.id.gv_image)
        MyGridView gv_image;
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
        @BindView(R.id.iv_jinghua)
        ImageView iv_jinghua;
        public MutiPictureViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    public interface itemClickListener{
        void click(int position);
    }
    public void setItemClickListener(itemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
