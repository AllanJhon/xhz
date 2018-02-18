package com.sjz.zyl.appdemo.ui;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
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
import org.kymjs.kjframe.http.Cache;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.DensityUtils;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AppStartActivity extends KJActivity {

    public static String TAG = "appstart";
//    public static String getCategories_path="api/v1/categories";
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
                //startLogin();
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

    private void startLogin() {
        if(hasLocationPermission()){
            //  如果有这个权限  就去登陆
            jumpTo();
        }else{
            //  否则去申请权限
            applyPermission();
        }

        //  安卓6以后  都需要用户自己开启权限

    }

    private boolean hasLocationPermission() {
        //  检查是否有写入权限
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PermissionChecker.PERMISSION_GRANTED;
    }
    private void applyPermission() {
        //  申请权限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

    }

    /**
     * 申请权限的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED&&grantResults[1] == PermissionChecker.PERMISSION_GRANTED) {
                    jumpTo();
                } else {
                    ViewInject.toast("获取权限失败！");
                }
                break;
        }
    }
    @Override
    public void initData(){
        kjdb= KJDB.create();
        HttpConfig config = new HttpConfig();
        int hour = StringUtils.toInt(StringUtils.getDataTime("HH"), 0);
        if (hour > 12 && hour < 22) {
            config.cacheTime = 10;
        } else {
            config.cacheTime = 300;
        }
        config.useDelayCache = true;
       KJHttp kjh = new KJHttp(config);
      String cache = kjh.getStringCache(AppConfig.root_path+getAllDatas_path);
        if (StringUtils.isEmpty(cache))
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
                                Parser.getNews(data.getString("News"));
                                Parser.getDocuments(data.getString("Documents"));
                                Parser.getArticles(data.getString("Articles"));

                            }
//                           list= Parser.getCategories(json.optString("data"));
                            startLogin();
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
