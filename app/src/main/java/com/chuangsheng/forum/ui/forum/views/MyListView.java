package com.chuangsheng.forum.ui.forum.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by tianou on 2019/3/21.
 */

public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
