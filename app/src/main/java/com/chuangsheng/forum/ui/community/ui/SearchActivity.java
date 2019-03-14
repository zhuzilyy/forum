package com.chuangsheng.forum.ui.community.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiForum;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.community.bean.HotWordBean;
import com.chuangsheng.forum.ui.mine.adapter.SearchAdapter;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.FlowGroupView;
import com.chuangsheng.forum.view.FlowLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.internal.operators.completable.CompletableFromUnsafeSource;
import okhttp3.Call;
import okhttp3.Response;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.flowLayout_hot)
    FlowGroupView flowLayout_hot;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.flowLayout_history)
    FlowGroupView  flowLayout_history;
    private CustomLoadingDialog customLoadingDialog;
    private String searchList;
    private List<String> historyList;
    @Override
    protected void initViews() {
        BaseActivity.activityList.add(this);
        customLoadingDialog = new CustomLoadingDialog(this);
        customLoadingDialog.show();
    }
    @Override
    protected void initData() {
        getHotSearch();
    }
    private void getHistoryData() {
        historyList = new ArrayList<>();
        searchList = (String)SPUtils.get(SearchActivity.this,"searchHistory","");
        Gson gson = new Gson();
        List<String> list = gson.fromJson(searchList, List.class);
        if (list!=null && list.size()>0){
            for (int i =list.size()-1; i>=0; i--) {
                TextView child = new TextView(SearchActivity.this);
                ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                params.setMargins(0, 5, 35, 5);
                child.setLayoutParams(params);
                child.setBackgroundResource(R.drawable.shape_hot_word);
                child.setText(list.get(i));
                //child.setTextColor(Color.WHITE);
                //initEvents(child);//监听
                flowLayout_history.addView(child);
                initEventListener(child);
            }
            for (int i = 0; i <list.size() ; i++) {
                historyList.add(list.get(i));
            }

        }
    }
    //获取热门搜索的数据
    private void getHotSearch() {
        ApiForum.getHotSearch(ApiConstant.HOT_SEARCH, new RequestCallBack<HotWordBean>() {
            @Override
            public void onSuccess(Call call, Response response, HotWordBean hotWordBean) {
                customLoadingDialog.dismiss();
                int code = hotWordBean.getCode();
                if (code == ApiConstant.SUCCESS_CODE){
                    List<String> result =  hotWordBean.getResult();
                    for (int i = 0; i <result.size() ; i++) {
                        if (!TextUtils.isEmpty(result.get(i))){
                            TextView child = new TextView(SearchActivity.this);
                            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                            params.setMargins(0, 5, 35, 5);
                            child.setLayoutParams(params);
                            child.setBackgroundResource(R.drawable.shape_hot_word);
                            child.setText(result.get(i));
                            //child.setTextColor(Color.WHITE);
                            //initEvents(child);//监听
                            flowLayout_hot.addView(child);
                            initEventListener(child);
                        }
                    }
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag",e.getMessage());
                customLoadingDialog.dismiss();
            }
        });
    }
    //点击事件
    private void initEventListener(final TextView child) {
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!historyList.contains(child.getText().toString().trim())){
                    historyList.set(0,child.getText().toString().trim());
                }
                Gson gson = new Gson();
                Log.i("tag",gson.toJson(historyList)+"======historyList=======");
                SPUtils.put(SearchActivity.this,"searchHistory",gson.toJson(historyList));
                Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                intent.putExtra("keyWord",child.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_community);
    }
    @Override
    protected void initListener() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!TextUtils.isEmpty(v.getText().toString())){
                    if (!historyList.contains(et_search.getText().toString().trim())){
                        historyList.add(et_search.getText().toString().trim());
                    }
                  /*  List<String> list = new ArrayList<>();
                    if (list.size()%2!=0){
                        for (int i = 0; i <list.size() ; i++) {
                            historyList.add(list.get(i));
                        }
                    }else{
                        for (int i = list.size()-1; i >=0 ; i--) {
                            historyList.add(list.get(i));
                        }
                    }*/
                    Gson gson = new Gson();
                    SPUtils.put(SearchActivity.this,"searchHistory",gson.toJson(historyList));
                    Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                    intent.putExtra("keyWord",et_search.getText().toString().trim());
                    startActivity(intent);
                }else {
                    ToastUtils.show(SearchActivity.this,"搜索内容不能为空");
                }
                return false;
            }
        });
    }
    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.tv_cancel,R.id.ll_search})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.ll_search:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (flowLayout_history!=null && flowLayout_history.getChildCount()>0){
            flowLayout_history.removeAllViews();
        }
        if (historyList!=null){
            historyList.clear();
        }
        getHistoryData();
    }
}
