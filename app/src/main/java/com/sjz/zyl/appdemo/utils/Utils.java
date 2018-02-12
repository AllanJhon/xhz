package com.sjz.zyl.appdemo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.domain.ArticleType;
import com.sjz.zyl.appdemo.domain.Categories;
import com.sjz.zyl.appdemo.domain.News;
import com.sjz.zyl.appdemo.utils.entity.BaseData;
import com.sjz.zyl.appdemo.utils.entity.GongZhong;
import com.sjz.zyl.appdemo.utils.entity.XhzCategories;
import com.sjz.zyl.appdemo.utils.entity.ZhaoPin;

import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2015/6/16.
 */
public class Utils {
    public static List<XhzCategories> getCategories(Context context) {
        KJDB kjdb=KJDB.create(true);
//        kjdb.deleteById(Categories.class,27);
        List<XhzCategories> xhzCategoriesArrayList=new ArrayList<XhzCategories>();
        XhzCategories XhzCategories=new XhzCategories(0,"类别");
        List<ArticleType> articleTypeList=kjdb.findAll(ArticleType.class," Sequence");
        for (int i=0;i<articleTypeList.size();i++){
            ArticleType articleType=articleTypeList.get(i);
            List<Categories> categoriesList=kjdb.findAllByWhere(Categories.class,"ArticleTypeID = "+articleType.getArticleTypeID()," Sequence");
            articleType.setCategoriesList(categoriesList);
        }
        XhzCategories.setArticleType(articleTypeList);
        xhzCategoriesArrayList.add(XhzCategories);
        KJLoger.state("db", "getJobType: "+ xhzCategoriesArrayList.size());
        return xhzCategoriesArrayList;
    }


    public static List<News> getNews(Context context) {
        KJDB kjdb = KJDB.create(true);
        List<News> newsList = new ArrayList<News>();
        List<News> newsList_return=new ArrayList<News>() ;
        newsList = kjdb.findAll(News.class);
        if (newsList.size() >= 3){
            newsList_return = newsList.subList(0, 3);
        }else if(newsList.size()>0){
            newsList_return = newsList.subList(0, newsList.size());
        }
        return newsList_return;
    }

    /**
     * 读取Raw文件夹下文本类型文件
     *
     * @param context    上下文
     * @param resourceId 资源id
     * @return 返回的读取完成的数据 string格式
     */
    public static String readFileFromRaw(Context context, int resourceId) {
        if (null == context || resourceId < 0) {
            return null;
        }

        String result = null;
        try {
            InputStream input = context.getResources().openRawResource(resourceId);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
            output.close();
            input.close();
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
// Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
// We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
