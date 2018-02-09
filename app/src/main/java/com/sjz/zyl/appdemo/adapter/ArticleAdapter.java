package com.sjz.zyl.appdemo.adapter;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.domain.Article;
import com.sjz.zyl.appdemo.ui.DetailActivity;
import com.sjz.zyl.appdemo.utils.Parser;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.utils.StringUtils;
import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import java.util.List;

/**
 * 主界面博客模块列表适配器
 * 
 * @author kymjs (https://www.kymjs.com/)
 * @since 2015-3
 */
public class ArticleAdapter extends KJAdapter<Article> {
    private final KJBitmap kjb = new KJBitmap();

    public ArticleAdapter(AbsListView view, List<Article> mDatas) {
        super(view, mDatas, R.layout.list_view_item);
    }

    @Override
    public void convert(AdapterHolder helper, Article item, boolean isScrolling) {

        ImageView image = helper.getView(R.id.article_image);
        String url = Parser.getUrl(item.getArticleLogo());
        if (StringUtils.isEmpty(url)) {
            image.setVisibility(View.GONE);
        } else {
            image.setVisibility(View.VISIBLE);
            onPicClick(image, item);
            if (isScrolling) {
                kjb.displayCacheOrDefult(image, url, R.drawable.main_1);
            } else {
                kjb.display(image, url, 480, 420, R.drawable.main_1);
            }
        }
        helper.setText(R.id.article_phoneTv, item.getArticleTitle());
    }

    private void onPicClick(View view, final Article item) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCxt, DetailActivity.class);
                intent.putExtra("article",item);
                intent.putExtra("id", item.getArticleID());
                intent.putExtra("value", item.getArticleTitle());
//                mCxt.setResult(RESULT_OK, intent);
                mCxt.startActivity(intent);
            }
        });
    }

}
