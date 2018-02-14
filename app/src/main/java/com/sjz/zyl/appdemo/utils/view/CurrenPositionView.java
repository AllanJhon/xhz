package com.sjz.zyl.appdemo.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.ui.Main;
import com.sjz.zyl.appdemo.utils.Parser;
import com.sjz.zyl.appdemo.utils.entity.XhzCategories;
import com.sjz.zyl.appdemo.utils.entity.ZhaoPin;


public class CurrenPositionView extends LinearLayout {
    //每行item个数
    private static final int ROW_NUM = 3;
    private TextView titleView;
    private XhzCategories data;
    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private int viewId;

    public CurrenPositionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CurrenPositionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrenPositionView(Context context) {
        super(context);
    }

    public XhzCategories getData() {
        return data;
    }

    public void  setData(XhzCategories data) {
        this.data = data;
    }

    private void initView() {
        layoutInflater = LayoutInflater.from(getContext());
        View rootView = layoutInflater.inflate(R.layout.pulldown_view, this);
        titleView = (TextView) rootView.findViewById(R.id.job_name_text);
        tableLayout = (TableLayout) rootView.findViewById(R.id.job_name_table);
        tableLayout.setStretchAllColumns(true);
        //初始化表格数据
        int h = data.getArticleType().size() / ROW_NUM;
        int len = data.getArticleType().size() % ROW_NUM;
        int location = 0;
        if (len != 0)
            h += 1;
        for (int n = 1; n <= h; n++) {
            TableRow row = new TableRow(getContext());
            int length = n == h && len!=0 ? len : ROW_NUM;
            for (int m = 0; m < length; m++) {
                MyRowItemView myRowItemView= (MyRowItemView) layoutInflater.inflate(R.layout.table_row_my, null);
                myRowItemView.setImageView(Parser.getUrl(data.getArticleType().get(location).getArticleTypeIcon()));
                myRowItemView.setMyTextView(data.getArticleType().get(location).getArticleType());
                myRowItemView.setTag("" + viewId + location);
                myRowItemView.setOnClickListener(((Main) getContext()).new OnRowItemClick(data, location, n, tableLayout, myRowItemView));
                row.addView(myRowItemView);
                location++;
            }
            // 每一列 tablerow 增加一个 下拉的layout
            View view = layoutInflater.inflate(R.layout.expand_item, null);
            view.setTag(n);
            view.setVisibility(View.GONE);
            tableLayout.addView(row);
            tableLayout.addView(view);
        }
    }

    public void init() {
        initView();
    }

    public void setTitleStr(CharSequence text) {
        titleView.setText(text);
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }
}
