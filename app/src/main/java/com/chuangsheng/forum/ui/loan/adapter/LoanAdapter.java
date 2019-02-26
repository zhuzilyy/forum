package com.chuangsheng.forum.ui.loan.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.loan.bean.LoanInfo;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoanAdapter extends BaseAdapter {
    private Context context;
    private List<LoanInfo> infoList;
    public LoanAdapter(Context context, List<LoanInfo> infoList) {
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
        if(convertView == null){
           convertView = LayoutInflater.from(context).inflate(R.layout.item_loan,null);
           viewHolder = new ViewHolder(convertView);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        LoanInfo loanInfo = infoList.get(position);
        Glide.with(context).load(loanInfo.getImg()).into(viewHolder.iv_icon);
        viewHolder.tv_name.setText(loanInfo.getName());
        viewHolder.tv_limit.setText(loanInfo.getLimit());
        viewHolder.tv_desc.setText(loanInfo.getDesc());
        viewHolder.tv_status.setText(loanInfo.getCategory());
        return convertView;
    }
    static  class ViewHolder{
        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.tv_limit)
        TextView tv_limit;
        @BindView(R.id.tv_desc)
        TextView tv_desc;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
