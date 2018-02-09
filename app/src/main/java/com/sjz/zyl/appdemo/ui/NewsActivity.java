package com.sjz.zyl.appdemo.ui;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.domain.Article;
import com.sjz.zyl.appdemo.domain.News;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJDB;


/**
 * @author 张迎乐
 * 自定义标题栏
 */
public class NewsActivity extends KJActivity implements OnClickListener{

    //private RelativeLayout mLayoutTitleBar;
    private TextView mTitleTextView;
    private Button mBackwardbButton;
    private TextView textView;
    private EditText search;
    private int id;
    private KJDB kjdb;
    private News news;
    private Button mForwardButton;
    private FrameLayout mContentLayout;

    private void setupViews() {
        super.setContentView(R.layout.detail);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0); // 没有输入值默认为0
        String b = intent.getStringExtra("value"); // 没有输入值默认为0
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        textView=(TextView) findViewById(R.id.article_content);
        search=(EditText)findViewById(R.id.search);
        search.setVisibility(View.GONE);
        mTitleTextView.setText(b);
        //mContentLayout = (FrameLayout) findViewById(R.id.layout_content);
        mBackwardbButton = (Button) findViewById(R.id.button_backward);
        mBackwardbButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackward(v);
            }
        });
    }


    @Override
    public void initData() {
        super.initData();
        kjdb = KJDB.create();
        news= kjdb.findById(id,News.class);
        textView.setText(Html.fromHtml(news.getNewsContent()));
    }
    /**
     * 是否显示返回按钮
     * @param backwardResid  文字
     * @param show  true则显示
     */
    protected void showBackwardView(int backwardResid, boolean show) {
        if (mBackwardbButton != null) {
            if (show) {
                mBackwardbButton.setText(backwardResid);
                mBackwardbButton.setVisibility(View.VISIBLE);
            } else {
                mBackwardbButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 提供是否显示提交按钮
     * @param forwardResId  文字
     * @param show  true则显示
     */
    protected void showForwardView(int forwardResId, boolean show) {
        if (mForwardButton != null) {
            if (show) {
                mForwardButton.setVisibility(View.VISIBLE);
                mForwardButton.setText(forwardResId);
            } else {
                mForwardButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 返回按钮点击后触发
     * @param backwardView
     */
    protected void onBackward(View backwardView) {
//        Toast.makeText(this, "点击返回，可在此处调用finish()", Toast.LENGTH_LONG).show();
        finish();
    }

    /**
     * 提交按钮点击后触发
     * @param forwardView
     */
    protected void onForward(View forwardView) {
        Toast.makeText(this, "点击提交", Toast.LENGTH_LONG).show();
    }


    //设置标题内容
    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
    }

    //设置标题内容
    @Override
    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    //设置标题文字颜色
    @Override
    public void setTitleColor(int textColor) {
        mTitleTextView.setTextColor(textColor);
    }


    //取出FrameLayout并调用父类removeAllViews()方法
    @Override
    public void setContentView(int layoutResID) {
        //mContentLayout.removeAllViews();
        View.inflate(this, layoutResID, mContentLayout);
        onContentChanged();
    }

    @Override
    public void setContentView(View view) {
       // mContentLayout.removeAllViews();
       // mContentLayout.addView(view);
        onContentChanged();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void setContentView(View view, LayoutParams params) {
       // mContentLayout.removeAllViews();
        //mContentLayout.addView(view, params);
        onContentChanged();
    }


    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     * 按钮点击调用的方法
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_backward:
                onBackward(v);
                break;
            default:
                break;
        }
    }

    @Override
    public void setRootView() {
        setupViews();   //加载 custom_title_bar 布局 ，并获取标题及两侧按钮
    }
}
