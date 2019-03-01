package com.chuangsheng.forum.ui.mine.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiMine;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.forum.ui.PostForumActivity;
import com.chuangsheng.forum.ui.mine.adapter.FullyGridLayoutManager;
import com.chuangsheng.forum.ui.mine.adapter.GridImageAdapter;
import com.chuangsheng.forum.ui.mine.bean.CollectionBean;
import com.chuangsheng.forum.util.BitmapToBase64;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.util.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.et_contact)
    EditText et_contact;
    private GridImageAdapter adapter;
    private List<LocalMedia> selectList;
    private String userId;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        selectList = new ArrayList<>();
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(6);
        recyclerView.setAdapter(adapter);
        tv_title.setText("意见反馈");
        customLoadingDialog = new CustomLoadingDialog(this);
    }
    @Override
    protected void initData() {
        userId = (String) SPUtils.get(FeedBackActivity.this,"user_id","");
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_feedback);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back,R.id.btn_commit})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_commit:
                String content = et_content.getText().toString();
                String contact = et_contact.getText().toString();
                if (TextUtils.isEmpty(content)){
                    ToastUtils.show(FeedBackActivity.this,"反馈内容不能为空");
                    return;
                }
                if (TextUtils.isEmpty(contact)){
                    ToastUtils.show(FeedBackActivity.this,"联系方式不能为空");
                    return;
                }
                feedback(content,contact);
                break;
        }
    }
    //意见反馈
    private void feedback(String content, String contact) {
        customLoadingDialog.show();
        String imgs = getpicData_base64();
        ApiMine.feedback(ApiConstant.FEEDBACK, userId, imgs, content, contact, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String result) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("error_code");
                    if (code == ApiConstant.SUCCESS_CODE){
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
            }
        });
    }
    /***
     * 获取上传的图片
     * @return
     */
    private String getpicData_base64() {
        List<Bitmap> bitmaps=new ArrayList<>();
        String basePic="";
        for (int i = 0; i <selectList.size() ; i++) {
            Bitmap bitmap= BitmapFactory.decodeFile(selectList.get(i).getCompressPath());
            bitmaps.add(bitmap);
        }
        if(bitmaps.size()>0){
            for (int i = 0; i <bitmaps.size() ; i++) {
                String baseStr= BitmapToBase64.bitmapToBase64(bitmaps.get(i));
                basePic+=baseStr+";";

            }
            return basePic;
        }
        return basePic;
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {

            if (true) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(FeedBackActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(6)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        //.previewVideo(true)// 是否可预览视频
                        // .enablePreviewAudio(true) // 是否可播放音频
                        // .isCamera(cb_isCamera.isChecked())// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        //.enableCrop(cb_crop.isChecked())// 是否裁剪
                        .compress(true)// 是否压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        //.compressSavePath(getPath())//压缩图片保存地址
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        //.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        // .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                        // .isGif(cb_isGif.isChecked())// 是否显示gif图片
                        // .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
                        // .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                        // .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        //.showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        //  .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        .cropCompressQuality(90)// 裁剪压缩质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
                        //.recordVideoSecond()//录制视频秒数 默认60s
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        }

    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getCompressPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
