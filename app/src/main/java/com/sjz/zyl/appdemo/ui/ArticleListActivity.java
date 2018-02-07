package com.sjz.zyl.appdemo.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
                .setNoMoreDataText("学习不可贪多哦~");

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                UIHelper.toBrowser(aty, adapter.getItem(position).getUrl());
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

}
