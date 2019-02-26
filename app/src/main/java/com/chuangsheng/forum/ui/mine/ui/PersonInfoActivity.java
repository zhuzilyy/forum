package com.chuangsheng.forum.ui.mine.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.dialog.PhotoChioceDialog;
import com.chuangsheng.forum.dialog.datepicker.CustomDatePicker;
import com.chuangsheng.forum.dialog.datepicker.DateFormatUtils;
import com.chuangsheng.forum.util.BitmapToBase64;
import com.chuangsheng.forum.util.PhotoUtils;
import com.chuangsheng.forum.view.CircleImageView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonInfoActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.tv_bithday)
    TextView tv_bithday;
    @BindView(R.id.iv_head)
    CircleImageView iv_head;
    //***************************************************************7.0拍照相册
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/avatar.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_avatarPhoto.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private Bitmap cropBitmap;
    private CustomDatePicker mDatePicker;
    @Override
    protected void initViews() {
        tv_title.setText("个人中心");
        tv_right.setText("保存");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextColor(Color.parseColor("#77a0fe"));
    }
    @Override
    protected void initData() {
        //初始化选择时间的控件
        initDatePicker();
    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_person_info);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.rl_avatar,R.id.rl_birthday,R.id.rl_sign,R.id.rl_level,R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.rl_avatar:
                showPhotoDialog();
                break;
            case R.id.rl_birthday:
                mDatePicker.show(tv_bithday.getText().toString());
                break;
            case R.id.rl_sign:
                jumpActivity(this,SignActivity.class);
                break;
            case R.id.rl_level:
                Bundle bundle = new Bundle();
                bundle.putString("title","等级介绍");
                bundle.putString("url","https://www.baidu.com");
                jumpActivity(PersonInfoActivity.this, WebviewActivity.class,bundle);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
    //照片选择器
    private void showPhotoDialog() {
        PhotoChioceDialog photoChioceDialog = new PhotoChioceDialog(this);
        photoChioceDialog.show();
        photoChioceDialog.setClickCallback(new PhotoChioceDialog.ClickCallback() {
            @Override
            public void doCamera() {
                readPhotoAlbum();
            }
            @Override
            public void doCancel() {
            }
            @Override
            public void doAlbum () {
                takePhoto();
            }
        });
    }
    //读取相册
    private void readPhotoAlbum() {
        //指定action   [照相机]
        requestPermissions(PersonInfoActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestPermissionCallBack() {
            @Override
            public void granted() {
                if (hasSdcard()) {
                    imageUri = Uri.fromFile(fileUri);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        //通过FileProvider创建一个content类型的Uri
                        imageUri = FileProvider.getUriForFile(PersonInfoActivity.this, "com.chuangsheng.forum.fileprovider", fileUri);
                    PhotoUtils.takePicture(PersonInfoActivity.this, imageUri, CODE_CAMERA_REQUEST);
                } else {
                    Toast.makeText(PersonInfoActivity.this, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
                    Log.e("asd", "设备没有SD卡");
                }
            }

            @Override
            public void denied() {
                Toast.makeText(PersonInfoActivity.this, "部分权限获取失败，正常功能受到影响", Toast.LENGTH_LONG).show();
            }
        });
    }
    //拍照
    private void takePhoto() {
        requestPermissions(PersonInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestPermissionCallBack() {
            @Override
            public void granted() {
                PhotoUtils.openPic(PersonInfoActivity.this, CODE_GALLERY_REQUEST);
            }
            @Override
            public void denied() {
                Toast.makeText(PersonInfoActivity.this, "部分权限获取失败，正常功能受到影响", Toast.LENGTH_LONG).show();
            }
        });
    }
    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("1980-01-01", false);
        long endTimestamp = System.currentTimeMillis();
        //mTvSelectedDate.setText(DateFormatUtils.long2Str(endTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                tv_bithday.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int output_X = 120, output_Y = 120;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Bitmap bitmap = null;
                    try {
                        bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), cropImageUri);
                        //String base64 = BitmapToBase64.bitmapToBase64(bitmap);
                        //Log.i("TAG","11"+base64);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, "com.chuangsheng.forum.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        Toast.makeText(PersonInfoActivity.this, "设备没有SD卡!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    cropBitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (cropBitmap != null) {
                        iv_head.setImageBitmap(cropBitmap);
                        String base64 = BitmapToBase64.Bitmap2StrByBase64(cropBitmap);
                        Log.i("tag",base64);
                    }
                    break;
            }
        }
    }
}
