package com.sjz.zyl.appdemo.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.domain.Article;
import com.sjz.zyl.appdemo.domain.Documents;
import com.sjz.zyl.appdemo.domain.News;
import com.sjz.zyl.appdemo.ui.amap.RouteActivity;
import com.sjz.zyl.appdemo.utils.Parser;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.database.DbModel;
import org.kymjs.kjframe.database.utils.TableInfo;
import org.kymjs.kjframe.ui.BindView;

import java.net.URL;


/**
 * @author 张迎乐
 * 自定义标题栏
 */
public class NewsActivity extends KJActivity implements OnClickListener{

    //private RelativeLayout mLayoutTitleBar;
    @BindView(id=R.id.text_title)
    private TextView mTitleTextView;
    private Button mBackwardbButton;
    //    @BindView(id=R.id.article_content)
//    private TextView textView;
    @BindView(id=R.id.search)
    private EditText search;
    @BindView(id=R.id.webView)
    private WebView webView;
    private int id;
    @BindView(id=R.id.article_title)
    private TextView article_title;
    @BindView(id=R.id.location)
    private TextView location;
    private KJDB kjdb;
    private Handler handler;
    private News news;
    private Button mForwardButton;
    @BindView(id=R.id.image1)
    private ImageView image_main;
    private String tp_title;
    private FrameLayout mContentLayout;

    private void setupViews() {
        Intent intent = getIntent();
//        article=(Article) intent.getSerializableExtra("article");
        id = intent.getIntExtra("id",0); // 没有输入值默认为0
        tp_title=intent.getStringExtra("value");
        handler=new Handler();

//        String title = intent.getStringExtra("value"); // 没有输入值默认为0

    }
    @Override
    public void initWidget() {
        super.initWidget();
        location.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        if (!"".equals(news.getNewsLogo())) {
            KJBitmap kjBitmap = new KJBitmap();
            kjBitmap.display(image_main, Parser.getUrl(news.getNewsLogo()));
        }else{
            image_main.setVisibility(View.GONE);
        }
//        if(!"".equals(news.getNewsTitle())&&news.getNewsTitle()!=null)
////        mTitleTextView.setText(article.getArticleTitle());
        article_title.setText(news.getNewsTitle());
//        else
//            article_title.setVisibility(View.GONE);

        mTitleTextView.setText(tp_title);
//        textView.setText(Html.fromHtml(news.getNewsContent()));
//        textView.setMovementMethod(LinkMovementMethod.getInstance());//设置可点击
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final Spanned text = Html.fromHtml(news.getNewsContent(), imgGetter, null);
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setText(text);
//                    }
//                });
//            }
//        }).start();
        //mContentLayout = (FrameLayout) findViewById(R.id.layout_content);
        mBackwardbButton = (Button) findViewById(R.id.button_backward);
        mBackwardbButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackward(v);
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.loadData(news.getNewsContent().replace("<img", "<img height=\"250px\"; width=\"100%\""), "text/html;charset=UTF-8",null);

    }

    @Override
    public void initData() {
        super.initData();
        kjdb = KJDB.create();
        if(news==null) {
            TableInfo table = TableInfo.get(News.class);
            news=new News();
            DbModel dbModel=kjdb.findDbModelBySQL("select * from "+table.getTableName()+" where NewsID= "+id);
            news.setNewsContent(dbModel.getString("NewsContent"));
            news.setNewsID(dbModel.getInt("NewsID"));
            news.setNewsLogo(dbModel.getString("NewsLogo"));
            news.setAuthor(dbModel.getString("Author"));
            news.setNewsTitle(dbModel.getString("NewsTitle"));
            news.setReleaseDate(dbModel.getString("ReleaseDate"));
        }
    }


    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url = null;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), "img");
            } catch (Exception e) {
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                    .getIntrinsicHeight());
            return drawable;
        }
    };
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
        super.setContentView(R.layout.detail);
        setupViews();   //加载 custom_title_bar 布局 ，并获取标题及两侧按钮
    }
}
