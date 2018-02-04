package com.sjz.zyl.appdemo.ui;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sjz.zyl.appdemo.AppConfig;
import com.sjz.zyl.appdemo.AppContext;
import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.domain.Article;
import com.sjz.zyl.appdemo.domain.ArticleType;
import com.sjz.zyl.appdemo.domain.Categories;
import com.sjz.zyl.appdemo.utils.Parser;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.DensityUtils;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

public class AppStartActivity extends KJActivity {

    public static String TAG = "appstart";
    public static String getCategories_path="api/v1/categories";
    public static String getAllDatas_path="api/v1/syncAll";
    private KJDB kjdb;
    private List<Categories> list;
    private List<ArticleType> articleTypelist;

    @Override
    public void setRootView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        ImageView image = new ImageView(aty);
        image.setImageResource(R.drawable.splash_bg);
        Animation anim = AnimationUtils.loadAnimation(aty, R.anim.splash_start);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        image.setAnimation(anim);
        setContentView(image);
        AppContext.screenH = DensityUtils.getScreenH(aty);
        AppContext.screenW = DensityUtils.getScreenW(aty);
    }

    /**
     * 根据设置启动推送进程
     */
    private void configPush() {}

    private void jumpTo() {
//        startService(new Intent(aty, CommonService.class));
        configPush();
        boolean isFirst = PreferenceHelper.readBoolean(aty, TAG, "first_open",
                true);
        Intent jumpIntent = new Intent();
        if (true) {
            jumpIntent.setClass(aty, Main.class);
        }
        startActivity(jumpIntent);
        finish();
    }

    @Override
    public void initData(){
        kjdb= KJDB.create();
//        if(kjdb.findAllByWhere(ArticleType.class,"ArticleTypeID = 1").size()== 0) {
//            articleTypelist = new ArrayList<ArticleType>();
//            ArticleType type1 = new ArticleType();
//            type1.setArticleTypeID(1);
//            type1.setArticleType("小河庄概况");
//            articleTypelist.add(type1);
//            kjdb.save(type1);
//            ArticleType type2 = new ArticleType();
//            type2.setArticleTypeID(2);
//            type2.setArticleType("村民服务中心");
//            articleTypelist.add(type2);
//            kjdb.save(type2);
//            ArticleType type3 = new ArticleType();
//            type3.setArticleTypeID(3);
//            type3.setArticleType("党政建设");
//            articleTypelist.add(type3);
//            kjdb.save(type3);
//            ArticleType type4 = new ArticleType();
//            type4.setArticleTypeID(4);
//            type4.setArticleType("政策法规");
//            articleTypelist.add(type4);
//            kjdb.save(type4);
//            ArticleType type5 = new ArticleType();
//            type5.setArticleTypeID(5);
//            type5.setArticleType("工业");
//            articleTypelist.add(type5);
//            kjdb.save(type5);
//            ArticleType type6 = new ArticleType();
//            type6.setArticleTypeID(6);
//            type6.setArticleType("农业");
//            articleTypelist.add(type6);
//            kjdb.save(type6);
//            ArticleType type7 = new ArticleType();
//            type7.setArticleTypeID(7);
//            type7.setArticleType("畜牧业");
//            articleTypelist.add(type7);
//            kjdb.save(type7);
//            ArticleType type8 = new ArticleType();
//            type8.setArticleTypeID(8);
//            type8.setArticleType("教育");
//            articleTypelist.add(type8);
//            kjdb.save(type8);
//            ArticleType type9 = new ArticleType();
//            type9.setArticleTypeID(9);
//            type9.setArticleType("精品旅游");
//            articleTypelist.add(type9);
//            kjdb.save(type9);
//            ArticleType type10 = new ArticleType();
//            type10.setArticleTypeID(10);
//            type10.setArticleType("金融");
//            articleTypelist.add(type10);
//            kjdb.save(type10);
//            ArticleType type11 = new ArticleType();
//            type11.setArticleTypeID(11);
//            type11.setArticleType("特色经济");
//            articleTypelist.add(type11);
//            kjdb.save(type11);
//            ArticleType type12 = new ArticleType();
//            type12.setArticleTypeID(12);
//            type12.setArticleType("基础设施");
//            articleTypelist.add(type12);
//            kjdb.save(type12);
//        }
        KJHttp kjh = new KJHttp();

        kjh.get(AppConfig.root_path+getAllDatas_path, null,
                new HttpCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        try {
                            JSONObject json=new JSONObject(t);
                            if(json.optInt("code")==200){
                                JSONObject data=json.getJSONObject("data");
                                Parser.getArriticleTypes(data.getString("ArticleTypes"));
                                Parser.getCategories(data.getString("ArticleCategories"));
                                Parser.getArticles(data.getString("Articles"));
                                Parser.getNews(data.getString("News"));
                                Parser.getDocuments(data.getString("Documents"));
                            }
//                           list= Parser.getCategories(json.optString("data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        KJLoger.state(TAG, "onFailure: "+ errorNo+":"+strMsg);
                    }
                });
    }
}
