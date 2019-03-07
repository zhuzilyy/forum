package com.chuangsheng.forum.ui.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.home.adapter.GvImageAdapter;
import com.chuangsheng.forum.ui.home.adapter.HomeAdapter;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFroumAdapter extends BaseAdapter {
    private final int NO_PICTURE = 0;
    private final int SINGLE_PICTURE = 1;
    private final int MUTI_PICTURE = 2;
    private Context context;
    private List<HomeFroumInfo> infoList;
    private String showStatus;
    private deleteCheckedListener deleteCheckedListener;
    // 存储勾选框状态的map集合
    private List<Boolean> selectList;
    public MyFroumAdapter(Context context, List<HomeFroumInfo> infoList,List<Boolean> selectList,String showStatus) {
        this.context = context;
        this.infoList = infoList;
        this.showStatus = showStatus;
        this.selectList = selectList;
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
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.pic);
        int itemViewType = getItemViewType(position);
        //没有图片
        if (itemViewType == NO_PICTURE){
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_froums,null);
                noPictureViewHolder = new NoPictureViewHolder(convertView);
                convertView.setTag(noPictureViewHolder);
            }else{
                noPictureViewHolder = (NoPictureViewHolder) convertView.getTag();
            }
            HomeFroumInfo homeFroumInfo = infoList.get(position);
            noPictureViewHolder.tv_title.setText(homeFroumInfo.getSubject());
            noPictureViewHolder.tv_content.setText(homeFroumInfo.getContent());
            noPictureViewHolder.tv_time.setText(homeFroumInfo.getCreated());
            noPictureViewHolder.tv_message.setText(homeFroumInfo.getComments());
            noPictureViewHolder.cb_delete.setChecked(selectList.get(position));
            noPictureViewHolder.cb_delete.setTag(position);
            if (showStatus.equals("show")) {
                noPictureViewHolder.cb_delete.setVisibility(View.VISIBLE);
            } else {
                noPictureViewHolder.cb_delete.setVisibility(View.INVISIBLE);
            }
            noPictureViewHolder.cb_delete.setChecked(selectList.get(position));
            noPictureViewHolder.cb_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int)v.getTag();
                    selectList.set(tag, !selectList.get(tag));
                    notifyDataSetChanged();
                    getSelectedCount();
                }
            });
            //一张图片
        }else if(itemViewType == SINGLE_PICTURE){
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_froums_one_pic,null);
                onePictureViewHolder = new OnePictureViewHolder(convertView);
                convertView.setTag(onePictureViewHolder);
            }else{
                onePictureViewHolder = (OnePictureViewHolder) convertView.getTag();
            }
            HomeFroumInfo homeFroumInfo = infoList.get(position);
            Glide.with(context).load(homeFroumInfo.getAttachment().get(0)).apply(options).into(onePictureViewHolder.iv_commentImg);
            onePictureViewHolder.tv_title.setText(homeFroumInfo.getSubject());
            onePictureViewHolder.tv_content.setText(homeFroumInfo.getContent());
            onePictureViewHolder.tv_time.setText(homeFroumInfo.getCreated());
            onePictureViewHolder.tv_message.setText(homeFroumInfo.getComments());
            onePictureViewHolder.cb_delete.setTag(position);
            onePictureViewHolder.cb_delete.setChecked(selectList.get(position));
            if (showStatus.equals("show")) {
                onePictureViewHolder.cb_delete.setVisibility(View.VISIBLE);
            } else {
                onePictureViewHolder.cb_delete.setVisibility(View.INVISIBLE);
            }
            onePictureViewHolder.cb_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int)v.getTag();
                    selectList.set(tag, !selectList.get(tag));
                    notifyDataSetChanged();
                    getSelectedCount();
                }
            });
            //多张图片
        }else if(itemViewType == MUTI_PICTURE){
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_froums_muti_pic,null);
                mutiPictureViewHolder = new MutiPictureViewHolder(convertView);
                convertView.setTag(mutiPictureViewHolder);
            }else{
                mutiPictureViewHolder = (MutiPictureViewHolder) convertView.getTag();
            }
            HomeFroumInfo homeFroumInfo = infoList.get(position);
            mutiPictureViewHolder.tv_title.setText(homeFroumInfo.getSubject());
            mutiPictureViewHolder.tv_content.setText(homeFroumInfo.getContent());
            mutiPictureViewHolder.tv_time.setText(homeFroumInfo.getCreated());
            mutiPictureViewHolder.tv_message.setText(homeFroumInfo.getComments());
            mutiPictureViewHolder.cb_delete.setChecked(selectList.get(position));
            mutiPictureViewHolder.cb_delete.setTag(position);
            if (showStatus.equals("show")) {
                mutiPictureViewHolder.cb_delete.setVisibility(View.VISIBLE);
            } else {
                mutiPictureViewHolder.cb_delete.setVisibility(View.INVISIBLE);
            }
            mutiPictureViewHolder.cb_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int)v.getTag();
                    selectList.set(tag, !selectList.get(tag));
                    notifyDataSetChanged();
                    getSelectedCount();
                }
            });
            GvImageAdapter adapter = new GvImageAdapter(context,infoList.get(position).getAttachment());
            mutiPictureViewHolder.gv_image.setAdapter(adapter);
        }
        return convertView;
    }
    //获取选中的删除的个数
    private void getSelectedCount() {
        int count=0;
        for (int i = 0; i <selectList.size() ; i++) {
            if (selectList.get(i)){
                count++;
            }
        }
        deleteCheckedListener.click(count);
    }
    static class NoPictureViewHolder{
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_message)
        TextView tv_message;
        @BindView(R.id.cb_delete)
        CheckBox cb_delete;
        public NoPictureViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    static class OnePictureViewHolder{
        @BindView(R.id.iv_commentImg)
        ImageView iv_commentImg;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_message)
        TextView tv_message;
        @BindView(R.id.cb_delete)
        CheckBox cb_delete;
        public OnePictureViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    static class MutiPictureViewHolder{
        @BindView(R.id.gv_image)
        GridView gv_image;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_message)
        TextView tv_message;
        @BindView(R.id.cb_delete)
        CheckBox cb_delete;
        public MutiPictureViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    public void setCheckListShow(String showStatus) {
        this.showStatus = showStatus;
        notifyDataSetChanged();
    }
    public interface deleteCheckedListener {
        void click(int count);
    }
    public void setDeleteCheckedListener(deleteCheckedListener deleteCheckedListener) {
        this.deleteCheckedListener = deleteCheckedListener;
    }
}
