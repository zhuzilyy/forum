package com.chuangsheng.forum.ui.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.forum.bean.DetailForumInfo;
import com.chuangsheng.forum.ui.forum.ui.ReplyForumActivity;
import com.chuangsheng.forum.ui.home.adapter.GvImageAdapter;
import com.chuangsheng.forum.ui.mine.bean.CollectionInfo;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.CircleImageView;
import com.chuangsheng.forum.view.MyGridView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectionAdapter extends BaseAdapter {
    private Context context;
    private List<CollectionInfo> infoList;
    private String showStatus;
    private deleteCheckedListener deleteCheckedListener;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> isCheck = new HashMap<Integer, Boolean>();
    public MyCollectionAdapter(Context context, List<CollectionInfo> infoList,String showStatus) {
        this.context = context;
        this.infoList = infoList;
        this.showStatus = showStatus;
        // 默觉得不选中
        initCheck(false);
    }
    // 初始化map集合
    public void initCheck(boolean flag) {
        // map集合的数量和list的数量是一致的
        for (int i = 0; i < infoList.size(); i++) {
            // 设置默认的显示
            isCheck.put(i, flag);
        }
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
        viewHolder.tv_title.setText(collectionInfo.getSubject());
        if (showStatus.equals("show")){
            viewHolder.cb_delete.setVisibility(View.VISIBLE);
        }else{
            viewHolder.cb_delete.setVisibility(View.INVISIBLE);
        }
        if (infoList.get(position).isSelected()){
            viewHolder.cb_delete.setChecked(true);
        }else{
            viewHolder.cb_delete.setChecked(false);
        }
        // 设置状态
        if (isCheck.get(position) == null) {
            isCheck.put(position, false);
        }
        viewHolder.cb_delete.setChecked(isCheck.get(position));
       /* viewHolder.cb_delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck.put(position,isChecked);
                if (isChecked){
                    Intent intent = new Intent();
                    intent.setAction("com.action.notSelectAll");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
                //deleteCheckedListener.click(getSelectesCount());
                getSelectesCount();
            }
        });*/
        return convertView;
    }
    public void setCheckListShow(String showStatus){
        this.showStatus = showStatus;
        notifyDataSetChanged();
    }
    public interface deleteCheckedListener{
        void click(int count);
    }
    public void setDeleteCheckedListener(deleteCheckedListener deleteCheckedListener){
        this.deleteCheckedListener = deleteCheckedListener;
    }
    public int getSelectesCount(){
        int count=0;
        for (int i = 0; i <isCheck.size(); i++) {
            if (isCheck.get(i)){
                count++;
            }
        }
        if (count == infoList.size()){
            //发送广播 回帖成功
            Intent intent = new Intent();
            intent.setAction("com.action.selectAll");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
        return count;
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
        @BindView(R.id.cb_delete)
        CheckBox cb_delete;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
