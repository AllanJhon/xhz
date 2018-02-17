package com.sjz.zyl.appdemo.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.domain.Article;
import com.sjz.zyl.appdemo.domain.Categories;
import com.sjz.zyl.appdemo.utils.MyImageCache;
import com.sjz.zyl.appdemo.utils.Parser;
import com.sjz.zyl.appdemo.widget.RowItem;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.bitmap.BitmapConfig;
import org.kymjs.kjframe.utils.KJConfig;
import org.kymjs.kjframe.utils.StringUtils;

import java.util.List;

/**
 * Created by AAA on 2015/6/16.
 */
public class ArticleAdapter extends BaseAdapter{
    private List<Article> list;//数据源
    private Context context;//上下文对象
    private LayoutInflater inflater;//布局加载器

    //有参的构造函数，为数据源，上下文对象复制，同时实例化布局加载器
    public ArticleAdapter(List<Article> list, Context context) {
        this.list=list;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    //有多少条数据，需要创建多少个item布局
    @Override
    public int getCount() {
        return list.size();
    }
    //返回position对应位置的数据
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    //返回position对应位置item的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 具体定义加载item布局，并将数据显示到item布局上的方法。
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_view_item, null);
        ImageView image = (ImageView) view.findViewById(R.id.article_image);
        String url = Parser.getUrl(list.get(position).getArticleIcon());
        KJBitmap kjb = new KJBitmap();
        if (StringUtils.isEmpty(url)) {
            image.setVisibility(View.GONE);
        } else {
            image.setVisibility(View.VISIBLE);
            kjb.display(image, url, 480, 420);
        }
        TextView text = (TextView) view.findViewById(R.id.article_phoneTv);
        text.setText(list.get(position).getArticleTitle());
//        helper.setText(R.id.article_phoneTv, item.getArticleTitle());
        return view;
    }
    private OnClick listener;
//
//    public ExpandRowGridAdapter(Context context, List<Categories> mDatas,
//                                int itemLayoutId) {
//        super(context, mDatas, itemLayoutId);
//    }
//
//
//
//    /**
//     * 具体定义加载item布局，并将数据显示到item布局上的方法。
//     * @param position
//     * @param convertView
//     * @param parent
//     * @return
//     */
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        //加载item布局 将xml布局加载到内存中，形成一个view
//        final ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
//                position);
//        //实例化item布局上的控件
//        View view = viewHolder.getView(R.id.rowLiner);
//        view.setBackgroundColor(view.getResources().getColor(R.color.click));
//        ImageView imageView=(ImageView) view.findViewById(R.id.img);
//        BitmapConfig.mMemoryCache=new MyImageCache();
//        KJBitmap kjb = new KJBitmap(new BitmapConfig());
////        kjb.displayLoadAndErrorBitmap(imageView,
////                Parser.getUrl(item.getArticleCategoryIcon()),R.drawable.loading,R.drawable.app_logo);
//        if(kjb.getMemoryCache(Parser.getUrl(getItem(position).getArticleCategoryIcon()))!=null){
//            ViewInject.toast(getItem(position).getArticleCategoryID()+"");
//            kjb.displayCacheOrDefult(imageView,
//                    Parser.getUrl(getItem(position).getArticleCategoryIcon()),R.drawable.finance);
//        }else {
//            ViewInject.toast(getItem(position).getArticleCategoryID()+"");
//            kjb.displayLoadAndErrorBitmap(imageView,
//                    Parser.getUrl(getItem(position).getArticleCategoryIcon()),R.drawable.loading,R.drawable.app_logo);
//        }
////        //往控件上显示数据
////        //获取position对应位置的数据
////        CarCompany company= (CarCompany) getItem(position);
////        iv.setImageResource(company.getImg());
////        tv.setText(company.getCompany());
//        return view;
//    }
//    @Override
//    public void convert(ViewHolder helper, final Categories item) {
////        ViewInject.toast("zhang");
//        helper.setText(R.id.text, item.getArticleCategory());
//        View view = helper.getView(R.id.rowLiner);
//        view.setBackgroundColor(view.getResources().getColor(R.color.click));
//        ImageView imageView=(ImageView) view.findViewById(R.id.img);
//        BitmapConfig.mMemoryCache=new MyImageCache();
//        KJBitmap kjb = new KJBitmap(new BitmapConfig());
////        kjb.displayLoadAndErrorBitmap(imageView,
////                Parser.getUrl(item.getArticleCategoryIcon()),R.drawable.loading,R.drawable.app_logo);
//        if(kjb.getMemoryCache(Parser.getUrl(item.getArticleCategoryIcon()))!=null){
//            ViewInject.toast(item.getArticleCategoryID()+"");
//            kjb.displayCacheOrDefult(imageView,
//                    Parser.getUrl(item.getArticleCategoryIcon()),R.drawable.finance);
//        }else {
//            ViewInject.toast(item.getArticleCategoryID()+"");
//            kjb.displayLoadAndErrorBitmap(imageView,
//                    Parser.getUrl(item.getArticleCategoryIcon()),R.drawable.loading,R.drawable.app_logo);
//        }
////        imageView.setImageResource(R.drawable.economic);
////        view.setOnClickListener(new OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (listener != null) {
////                    listener.onClick(item.getArticleCategoryID(), item.getArticleCategory(),item.getDisplayType());
////                }
////
////            }
////        });
//    }

    public interface OnClick {
        void onClick(int dataId, String str);
        void onClick(int dataId, String str, String displayType);
    }

    public void setOnClick(OnClick listener) {
        this.listener = listener;
    }
}
