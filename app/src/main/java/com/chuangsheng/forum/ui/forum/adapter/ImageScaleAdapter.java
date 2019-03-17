package com.chuangsheng.forum.ui.forum.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.ui.forum.bean.EaluationPicBean;
import com.chuangsheng.forum.ui.forum.ui.LookBigPicActivity;
import com.chuangsheng.forum.view.PinchImageView;

import java.util.List;


/**
 * Created by mabeijianxi on 2015/1/5.
 */
public class ImageScaleAdapter extends PagerAdapter {
    private List<EaluationPicBean> mPicData;
    private Context mContext;
    private PinchImageView imageView;
    private View mCurrentView;
    private Bitmap bitmap;

    public ImageScaleAdapter(Context mContext, List<EaluationPicBean> data) {
        super();
        this.mPicData = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (mPicData != null) {
            return mPicData.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_photoview, container, false);
        imageView = (PinchImageView) inflate.findViewById(R.id.pv);

        // imageView.enable();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  ((LookBigPicActivity) mContext).finish();
            }
        });

        final ProgressBar pb = (ProgressBar) inflate.findViewById(R.id.pb);
        final EaluationPicBean ealuationPicBean = mPicData.get(position);

        if (ealuationPicBean != null && ealuationPicBean.imageUrl != null && !"null".equals(ealuationPicBean.imageUrl)) {
            setupNetImage(pb, ealuationPicBean);
            savaPic(ealuationPicBean);
        } else {
            imageView.setImageResource(R.mipmap.logo);
        }

        container.addView(inflate);//将ImageView加入到ViewPager中
        return inflate;
    }

    private void savaPic(final EaluationPicBean ealuationPicBean) {


    }

    /**
     * 设置网络图片加载规则
     *
     * @param pb
     * @param ealuationPicBean
     */
    private void setupNetImage(final ProgressBar pb, final EaluationPicBean ealuationPicBean) {
        startLoad(pb);
        showExcessPic(ealuationPicBean, imageView);
        Glide.with(mContext).load(ealuationPicBean.imageUrl).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                overLoad(pb);
                return false;
            }
        }).into(imageView);
    }

    /**
     * 展示过度图片
     *
     * @param ealuationPicBean
     * @param imageView
     */

    private void showExcessPic(final EaluationPicBean ealuationPicBean, final PinchImageView imageView) {
        imageView.setImageResource(R.mipmap.logo);
    }

    /**
     * 显示进度条
     *
     * @param pb
     */
    private void startLoad(ProgressBar pb) {
        pb.setVisibility(View.VISIBLE);
    }

    /**
     * 结束进度条
     *
     * @param pb
     */
    private void overLoad(ProgressBar pb) {
        pb.setVisibility(View.GONE);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
