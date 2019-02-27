package com.chuangsheng.forum.ui.mine.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chuangsheng.forum.MainActivity;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiMine;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.dialog.PhotoChioceDialog;
import com.chuangsheng.forum.dialog.datepicker.CustomDatePicker;
import com.chuangsheng.forum.dialog.datepicker.DateFormatUtils;
import com.chuangsheng.forum.ui.account.ui.SetNameActivity;
import com.chuangsheng.forum.ui.mine.bean.MyReplyFroumBean;
import com.chuangsheng.forum.ui.mine.bean.UserBean;
import com.chuangsheng.forum.ui.mine.bean.UserResult;
import com.chuangsheng.forum.util.BitmapToBase64;
import com.chuangsheng.forum.util.PhotoUtils;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class PersonInfoActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.tv_bithday)
    TextView tv_bithday;
    @BindView(R.id.tv_nickName)
    TextView tv_nickName;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.iv_head)
    CircleImageView iv_head;
    //***************************************************************7.0拍照相册
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CODE_REQUEST = 0xa3;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/avatar.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_avatarPhoto.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private Bitmap cropBitmap;
    private CustomDatePicker mDatePicker;
    private String userId,gender,base64;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        tv_title.setText("个人中心");
        tv_right.setText("保存");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextColor(Color.parseColor("#77a0fe"));
        userId = (String) SPUtils.get(this, "user_id", "");
        customLoadingDialog = new CustomLoadingDialog(this);
    }
    @Override
    protected void initData() {
        //初始化选择时间的控件
        initDatePicker();
        getData();
    }

    //获取个人信息
    private void getData() {
        ApiMine.getUserInfo(ApiConstant.GET_USER_INFO, userId, new RequestCallBack<UserBean>() {
            @Override
            public void onSuccess(Call call, Response response, UserBean userBean) {
                int code = userBean.getCode();
                if (code == ApiConstant.SUCCESS_CODE) {
                    UserResult result = userBean.getResult();
                    Glide.with(PersonInfoActivity.this).load(result.getImg()).into(iv_head);
                    tv_nickName.setText(result.getUsername());
                    gender = result.getSex().equals("1") ? "男" : "女";
                    tv_sex.setText(gender);
                    if (TextUtils.isEmpty(result.getBrith())){
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH)+1;
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        tv_bithday.setText(year+"-"+month+"-"+day);
                    }else{
                        tv_bithday.setText(result.getBrith());
                    }
                    tv_desc.setText(result.getDescription());
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
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

    @OnClick({R.id.rl_avatar, R.id.rl_birthday, R.id.rl_sign, R.id.rl_level, R.id.iv_back, R.id.rl_sex,R.id.tv_right})
    public void click(View view) {
        Bundle bundle =null;
        switch (view.getId()) {
            case R.id.rl_avatar:
                showPhotoDialog();
                break;
            case R.id.rl_birthday:
                mDatePicker.show(tv_bithday.getText().toString());
                break;
            case R.id.rl_sign:
                Intent intent = new Intent(this, SignActivity.class);
                intent.putExtra("sign",tv_desc.getText().toString());
                startActivityForResult(intent,CODE_REQUEST);
                break;
            case R.id.rl_level:
                bundle = new Bundle();
                bundle.putString("title", "等级介绍");
                bundle.putString("url", "https://www.baidu.com");
                jumpActivity(PersonInfoActivity.this, WebviewActivity.class, bundle);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_sex:
                showSexChooser();
                break;
            case R.id.tv_right:
                String desc = tv_desc.getText().toString();
                String birth = tv_bithday.getText().toString();
                gender = gender.equals("男")?"1":"0";
                saveInfo(desc,birth,gender);
                break;
        }
    }
    //保存个人信息
    private void saveInfo(String desc, String birth, String gender) {
        customLoadingDialog.show();
        ApiMine.changeUserInfo(ApiConstant.CHANGE_USER_INFO, userId, base64, gender, birth, desc, new RequestCallBack<UserBean>() {
            @Override
            public void onSuccess(Call call, Response response, UserBean userBean) {
                customLoadingDialog.dismiss();
                int code = userBean.getCode();
                String reason = userBean.getReason();
                ToastUtils.show(PersonInfoActivity.this,reason);
                if (code == ApiConstant.SUCCESS_CODE){
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    String username = userBean.getResult().getUsername();
                    String headAvatar = userBean.getResult().getImg();
                    SPUtils.put(PersonInfoActivity.this,"username",username);
                    SPUtils.put(PersonInfoActivity.this,"headAvatar",headAvatar);
                    finish();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
            }
        });
    }
    //性别选择器
    private void showSexChooser() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(PersonInfoActivity.this);
        builder.setTitle("请选择性别");
        final String[] sex = {"男", "女"};
        int checkedItem = gender.equals("男")?0:1;
        builder.setSingleChoiceItems(sex, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gender =  sex[which];
                //Toast.makeText(PersonInfoActivity.this, "性别为：" + sex[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_sex.setText(gender);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
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
            public void doAlbum() {
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
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), cropImageUri);
                        String base64 = BitmapToBase64.bitmapToBase64(bitmap);
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
                        base64 = BitmapToBase64.Bitmap2StrByBase64(cropBitmap);
                        Log.i("tag", base64);
                    }
                    break;
                case CODE_REQUEST:
                    String sign = data.getStringExtra("sign");
                    tv_desc.setText(sign);
                    break;
            }
        }
    }
}
