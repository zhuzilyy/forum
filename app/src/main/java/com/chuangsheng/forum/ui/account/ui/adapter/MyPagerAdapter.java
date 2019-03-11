package com.chuangsheng.forum.ui.account.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chuangsheng.forum.MainActivity;
import com.chuangsheng.forum.ui.account.ui.LoginActivity;
import com.chuangsheng.forum.ui.account.ui.WelcomeActiity;
import com.chuangsheng.forum.util.SPUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/5/9.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<ImageView> imageViews;
    private Context context;
    public MyPagerAdapter(Context context, List<ImageView> imageViews) {
        this.context=context;
        this.imageViews = imageViews;
    }
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(imageViews.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }
    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        //将xml布局转换为view对象
        container.addView(imageViews.get(position));
        imageViews.get(imageViews.size()-1).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Bundle bundle = new Bundle();
                bundle.putString("intentFrom","welcome");
                Intent intent=new Intent(context, LoginActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((WelcomeActiity)context).finish();
                SPUtils.put(context, "isFirst", false);
                return false;
            }
        });
        return imageViews.get(position);
    }
}
