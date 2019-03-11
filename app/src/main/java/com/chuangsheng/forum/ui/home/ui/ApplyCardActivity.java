package com.chuangsheng.forum.ui.home.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiLoan;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.loan.adapter.CardAdapter;
import com.chuangsheng.forum.ui.loan.adapter.LoanAdapter;
import com.chuangsheng.forum.ui.loan.bean.CardBean;
import com.chuangsheng.forum.ui.loan.bean.CardInfo;
import com.chuangsheng.forum.ui.loan.bean.LoanBean;
import com.chuangsheng.forum.ui.loan.bean.LoanInfo;
import com.chuangsheng.forum.ui.mine.ui.WebviewActivity;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ApplyCardActivity extends BaseActivity {
    @BindView(R.id.gv_loan)
    GridView gv_loan;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    private View view_loan;
    private CardAdapter cardAdapter;
    private int page=1;
    private List<CardInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        tv_title.setText("申卡");
        customLoadingDialog = new CustomLoadingDialog(this);
        customLoadingDialog.show();
        BaseActivity.activityList.add(this);
    }
    @Override
    protected void initData() {
        infoList = new ArrayList<>();
        getData();
        cardAdapter = new CardAdapter(this,infoList);
        gv_loan.setAdapter(cardAdapter);
    }
    //获取数据
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiLoan.getCarsList(ApiConstant.APPLY_CARD, page + "", new RequestCallBack<CardBean>() {
            @Override
            public void onSuccess(Call call, Response response, CardBean cardBean) {
                int code = cardBean.getCode();
                List<CardInfo> list = cardBean.getResult().getCards();
                if (code == ApiConstant.SUCCESS_CODE){
                    customLoadingDialog.dismiss();
                    if (list!=null && list.size()>0){
                        pulltorefreshView.setVisibility(View.VISIBLE);
                        no_data_rl.setVisibility(View.GONE);
                        pulltorefreshView.onHeaderRefreshComplete();
                        infoList.addAll(list);
                        cardAdapter.notifyDataSetChanged();
                        //判断是不是没有更多数据了
                        if (list.size() < ApiConstant.PAGE_SIZE) {
                            pulltorefreshView.onFooterRefreshComplete(true);
                        }else{
                            pulltorefreshView.onFooterRefreshComplete(false);
                        }
                    }else{
                        pulltorefreshView.setVisibility(View.GONE);
                        no_data_rl.setVisibility(View.VISIBLE);
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

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_apply_card);
    }
    @Override
    protected void initListener() {
        gv_loan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("title","详情");
                bundle.putString("url",infoList.get(position).getLimit());
                jumpActivity(ApplyCardActivity.this, WebviewActivity.class,bundle);
            }
        });

        //刷新记载事件
        pulltorefreshView.setmOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                page =1;
                infoList.clear();
                getData();
            }
        });
        pulltorefreshView.setmOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                loadMore();
            }
        });
    }
    @Override
    protected void setStatusBarColor() {

    }
    //加载更多事件
    private void loadMore() {
        page++;
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiLoan.getCarsList(ApiConstant.APPLY_CARD, page + "", new RequestCallBack<CardBean>() {
            @Override
            public void onSuccess(Call call, Response response, CardBean cardBean) {
                List<CardInfo> list = cardBean.getResult().getCards();
                if (list!=null && list.size()>0){
                    infoList.addAll(list);
                    pulltorefreshView.onHeaderRefreshComplete();
                    cardAdapter.notifyDataSetChanged();
                    //判断是不是没有更多数据了
                    if (list.size() < ApiConstant.PAGE_SIZE) {
                        pulltorefreshView.onFooterRefreshComplete(true);
                    }else{
                        pulltorefreshView.onFooterRefreshComplete(false);
                    }
                }else{
                    //已经加载到最后一条
                    pulltorefreshView.onFooterRefreshComplete(true);
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag",e.getMessage());
            }
        });
    }
    @OnClick({R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
