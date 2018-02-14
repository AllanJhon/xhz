package com.sjz.zyl.appdemo.widget;import android.content.Context;import android.util.AttributeSet;import android.util.Log;import android.view.View;import android.widget.GridView;import android.widget.ImageView;import android.widget.ProgressBar;import android.widget.TextView;import com.sjz.zyl.appdemo.R;/** * Description * * @author by 张迎乐(yingle1991@gmail.com) * @since 2018-02-14. */public class RowItem extends GridView{    private Boolean isOnMeasure;    public RowItem(Context context) {        super(context);    }    public RowItem(Context context, AttributeSet attrs) {        super(context, attrs);    }    @Override    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {        Log.d("onMeasure", "onMeasure");        isOnMeasure = true;        super.onMeasure(widthMeasureSpec, heightMeasureSpec);    }    public Boolean isOnMeasure(){        return isOnMeasure;    }    @Override    protected void onLayout(boolean changed, int l, int t, int r, int b) {        Log.d("onLayout", "onLayout");        isOnMeasure = false;        super.onLayout(changed, l, t, r, b);    }}