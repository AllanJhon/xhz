package com.sjz.zyl.appdemo.utils.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.sjz.zyl.appdemo.AppConfig;
import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.domain.Article;
import com.sjz.zyl.appdemo.domain.Categories;

import org.kymjs.kjframe.KJBitmap;

import java.util.List;

/**
 * Created by AAA on 2015/6/16.
 */
public class ExpandRowGridAdapter extends CommonAdapter<Categories> {
    private OnClick listener;

    public ExpandRowGridAdapter(Context context, List<Categories> mDatas,
                                int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }


    @Override
    public void convert(ViewHolder helper, final Categories item) {
        helper.setText(R.id.text, item.getArticleCategory());
        View view = helper.getView(R.id.rowLiner);
        view.setBackgroundColor(view.getResources().getColor(R.color.click));
        ImageView imageView=(ImageView) view.findViewById(R.id.img);
        KJBitmap kjb = new KJBitmap();
        kjb.displayCacheOrDefult(imageView,
                AppConfig.image_path+item.getCategoryIconURL(),R.drawable.finance);
//        imageView.setImageResource(R.drawable.economic);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item.getArticleCategoryID(), item.getArticleCategory(),item.getDisplayType());
                }

            }
        });
    }

    public interface OnClick {
        void onClick(int dataId, String str);
        void onClick(int dataId, String str,String displayType);
    }

    public void setOnClick(OnClick listener) {
        this.listener = listener;
    }
}
