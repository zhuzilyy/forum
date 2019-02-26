package com.chuangsheng.forum.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiFroum;
import com.chuangsheng.forum.api.ApiHome;
import com.chuangsheng.forum.api.ApiLoan;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.ui.froum.bean.CommunityBean;
import com.chuangsheng.forum.ui.froum.bean.CommunityInfo;
import com.chuangsheng.forum.ui.home.bean.HomeFroumBean;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.ui.loan.adapter.LoanAdapter;
import com.chuangsheng.forum.ui.loan.bean.LoanBean;
import com.chuangsheng.forum.ui.loan.bean.LoanInfo;
import com.chuangsheng.forum.ui.loan.bean.LoanResult;
import com.chuangsheng.forum.ui.mine.ui.WebviewActivity;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class LoanFragment extends BaseFragment {
    @BindView(R.id.gv_loan)
    GridView gv_loan;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    private View view_loan;
    private LoanAdapter loanAdapter;
    private int page=1;
    private List<LoanInfo> infoList;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_loan = inflater.inflate(R.layout.fragment_loan,null);
        return view_loan;
    }
    @Override
    protected void initViews() {
        tv_title.setText("贷款");
        iv_back.setVisibility(View.GONE);
    }
    @Override
    protected void initData() {
        infoList = new ArrayList<>();
        getData();
        loanAdapter = new LoanAdapter(getActivity(),infoList);
        gv_loan.setAdapter(loanAdapter);
    }
    //获取数据
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiLoan.getLoanList(ApiConstant.LOAN_LIST, page + "", new RequestCallBack<LoanBean>() {
            @Override
            public void onSuccess(Call call, Response response, LoanBean loanBean) {
                int code = loanBean.getCode();
                List<LoanInfo> list = loanBean.getResult().getLoans();
                if (code == ApiConstant.SUCCESS_CODE){
                    if (list!=null && list.size()>0){
                        pulltorefreshView.onHeaderRefreshComplete();
                        infoList.addAll(list);
                        loanAdapter.notifyDataSetChanged();
                        //判断是不是没有更多数据了
                        if (list.size() < ApiConstant.PAGE_SIZE) {
                            pulltorefreshView.onFooterRefreshComplete(true);
                        }else{
                            pulltorefreshView.onFooterRefreshComplete(false);
                        }
                    }
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
        ApiLoan.getLoanList(ApiConstant.LOAN_LIST,page+"" ,new RequestCallBack<LoanBean>() {
            @Override
            public void onSuccess(Call call, Response response, LoanBean loanBean) {
                int code = loanBean.getCode();
                List<LoanInfo> list = loanBean.getResult().getLoans();
                if (code == ApiConstant.SUCCESS_CODE){
                    if (list!=null && list.size()>0){
                        pulltorefreshView.onHeaderRefreshComplete();
                        infoList.addAll(list);
                        loanAdapter.notifyDataSetChanged();
                        //判断是不是没有更多数据了
                        if (list.size() < ApiConstant.PAGE_SIZE) {
                            pulltorefreshView.onFooterRefreshComplete(true);
                        }else{
                            pulltorefreshView.onFooterRefreshComplete(false);
                        }
                    }
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag",e.getMessage());
            }
        });
    }

    @Override
    protected void initListener() {
        gv_loan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("title","详情");
                bundle.putString("url","https://www.baidu.com/baidu?tn=monline_3_dg&ie=utf-8&wd=%E7%99%BE%E5%BA%A6");
                jumpActivity(getActivity(), WebviewActivity.class,bundle);
            }
        });
    }
}
