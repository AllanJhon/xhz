package com.sjz.zyl.appdemo.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.adapter.ArticleAdapter;
import com.sjz.zyl.appdemo.domain.Article;
import com.sjz.zyl.appdemo.widget.EmptyLayout;
import com.sjz.zyl.appdemo.widget.listview.FooterLoadingLayout;
import com.sjz.zyl.appdemo.widget.listview.PullToRefreshBase;
import com.sjz.zyl.appdemo.widget.listview.PullToRefreshList;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.StringUtils;

import java.util.List;

/**
 * Description
 *
 * @author by 张迎乐(yingle1991@gmail.com)
 * @since 2018-02-07.
 */
public class ArticleListActivity extends KJActivity{

    private int id;
    private ListView mList;
    private List<Article> articleList;
    private ArticleAdapter adapter;
    @BindView(id = R.id.listView)
    private PullToRefreshList mRefreshLayout;
    @BindView(id = R.id.empty_layout)
    private EmptyLayout mEmptyLayout;
    @BindView(id=R.id.search)
    private EditText search;
    @BindView(id=R.id.button_backward)
    private Button mBackwardbButton;
    @Override
    public void setRootView() {
        setupViews();
    }

    private void setupViews() {
        super.setContentView(R.layout.list_view);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0); // 没有输入值默认为0


    }

    @Override
    public void initWidget() {
        super.initWidget();
        listViewPreference();
        fillUI();
    }

    /**
     * 初始化ListView样式
     */
    private void listViewPreference() {
        mList = mRefreshLayout.getRefreshView();
        mList.setDivider(new ColorDrawable(0x00000000));
        mList.setOverscrollFooter(null);
        mList.setOverscrollHeader(null);
        mList.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        mRefreshLayout.setPullLoadEnabled(true);
        ((FooterLoadingLayout) mRefreshLayout.getFooterLoadingLayout())
                .setNoMoreDataText("已经显示全部了哦~");

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                UIHelper.toBrowser(aty, adapter.getItem(position).getUrl());
                Intent intent = new Intent(ArticleListActivity.this, DetailActivity.class);
                intent.putExtra("article",adapter.getItem(position));
                intent.putExtra("id", adapter.getItem(position).getArticleID());
                intent.putExtra("value", adapter.getItem(position).getArticleTitle());
                intent.putExtra("location", adapter.getItem(position).getLocation());
                setResult(RESULT_OK, intent);
                startActivity(intent);
            }
        });

        mRefreshLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                refresh();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                mRefreshLayout.setHasMoreData(false);
            }
        });
        mBackwardbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackward(view);
            }
        });
    }


    private void fillUI() {
        KJDB kjdb=KJDB.create();
        List<Article> datas= kjdb.findAllByWhere(Article.class,"ArticleCategoryID = "+id);
        if(datas.size()>0){

            adapter = new ArticleAdapter(mList, datas);
            mList.setAdapter(adapter);
            mEmptyLayout.dismiss();
        }
        refresh();
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    ViewInject.toast(search.getText()+"");
                    KJDB kjdb=KJDB.create();
                    List<Article> datas= kjdb.findAllByWhere(Article.class,"ArticleTitle like '%"+search.getText()+"%' and  ArticleCategoryID = "+id);
                    adapter.refresh(datas);

                }
                return false;
            }
        });
    }
    private void refresh() {
        KJDB kjdb=KJDB.create();
       List<Article> datas= kjdb.findAllByWhere(Article.class,"ArticleCategoryID = "+id);
        if(datas.size()>0){

            adapter = new ArticleAdapter(mList, datas);
            mList.setAdapter(adapter);
            mEmptyLayout.dismiss();
        }else{
            if (adapter != null && adapter.getCount() > 0) {
                return;
            } else {
                mEmptyLayout.setErrorType(EmptyLayout.NODATA);
            }
        }
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

//    /**
//     * 提供是否显示提交按钮
//     * @param forwardResId  文字
//     * @param show  true则显示
//     */
//    protected void showForwardView(int forwardResId, boolean show) {
//        if (mForwardButton != null) {
//            if (show) {
//                mForwardButton.setVisibility(View.VISIBLE);
//                mForwardButton.setText(forwardResId);
//            } else {
//                mForwardButton.setVisibility(View.INVISIBLE);
//            }
//        } // else ignored
//    }

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


}
