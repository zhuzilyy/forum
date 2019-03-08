package com.chuangsheng.forum.ui.account.ui;

import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.chuangsheng.forum.MainActivity;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.util.SPUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/9.
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_splash)
    ImageView iv_splash;
    @Override
    protected void initViews() {
        startAnim();
    }

    private void startAnim() {
        // 动画集合
        AnimationSet set = new AnimationSet(false);

       /* // 旋转动画
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(1000);// 动画时间
        rotate.setFillAfter(true);// 保持动画状态

        // 缩放动画
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scale.setDuration(1000);// 动画时间
        scale.setFillAfter(true);// 保持动画状态*/

        // 渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);// 动画时间
        alpha.setFillAfter(true);// 保持动画状态
       /* set.addAnimation(rotate);
        set.addAnimation(scale);*/
        set.addAnimation(alpha);

        // 设置动画监听
        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画执行结束
            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextPage();
            }
        });
        iv_splash.startAnimation(set);
    }
    /**
     * 跳转下一个页面
     */
    private void jumpNextPage() {
        //jumpActivity(SplashActivity.this,WelcomeActiity.class);
        // 判断之前有没有显示过新手引导
        boolean isFirst = (boolean) SPUtils.get(this, "isFirst", true);
        if (isFirst){
            jumpActivity(SplashActivity.this,WelcomeActiity.class);
        }else{
            String userId = (String) SPUtils.get(this,"user_id","");
            if (!TextUtils.isEmpty(userId)){
                jumpActivity(this, MainActivity.class);
                finish();
            }else{
                jumpActivity(SplashActivity.this,LoginActivity.class);
            }
        }
        finish();
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
    }
    @Override
    protected void initListener() {

    }
    @Override
    protected void setStatusBarColor() {

    }
}
