package com.chuangsheng.forum.ui.loan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.loan.bean.CardInfo;
import com.chuangsheng.forum.ui.loan.bean.LoanInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardAdapter extends BaseAdapter {
    private Context context;
    private List<CardInfo> infoList;
    public CardAdapter(Context context, List<CardInfo> infoList) {
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
           convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        CardInfo cardInfo = infoList.get(position);
        Glide.with(context).load(cardInfo.getImg()).into(viewHolder.iv_icon);
        viewHolder.tv_name.setText(cardInfo.getName());
        viewHolder.tv_limit.setText(cardInfo.getLimit());
        viewHolder.tv_desc.setText(cardInfo.getDesc());
        viewHolder.tv_status.setText(cardInfo.getCategory());
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
