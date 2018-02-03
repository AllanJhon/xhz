package com.sjz.zyl.appdemo.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.sjz.zyl.appdemo.R;
import com.sjz.zyl.appdemo.domain.ArticleType;
import com.sjz.zyl.appdemo.domain.Categories;
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
        List<XhzCategories> xhzCategoriesArrayList=new ArrayList<XhzCategories>();
        XhzCategories XhzCategories=new XhzCategories(0,"类别");
        List<ArticleType> articleTypeList=kjdb.findAll(ArticleType.class,"ArticleTypeID");
//        List<GongZhong> gongZhongList=new ArrayList<GongZhong>();
        for (int i=0;i<articleTypeList.size();i++){
            ArticleType articleType=articleTypeList.get(i);
//            GongZhong gongZhong=new GongZhong(Integer.parseInt(articleType.getArticleTypeID()),articleType.getArticleType());
            List<Categories> categoriesList=kjdb.findAllByWhere(Categories.class,"ArticleTypeID = "+articleType.getArticleTypeID());
//            List<BaseData> baseDataList=new ArrayList<BaseData>();
//            for (int j=0;j<categoriesList.size();j++){
//                Categories categories=categoriesList.get(j);
//                BaseData baseData=new BaseData(Integer.parseInt(categories.getArticleCategoryID()),categories.getArticleCategory());
//                baseDataList.add(baseData);
//            }
            articleType.setCategoriesList(categoriesList);
//            gongZhong.setJobtype(baseDataList);
//            gongZhongList.add(gongZhong);
        }
        XhzCategories.setArticleType(articleTypeList);
        xhzCategoriesArrayList.add(XhzCategories);
        KJLoger.state("db", "getJobType: "+ xhzCategoriesArrayList.size());
//        String result = readFileFromRaw(context, R.raw.zhaopin);
//        if (result != null) {
//            Gson gson = new Gson();
//            return gson.fromJson(result, new TypeToken<List<ZhaoPin>>() {
//            }.getType());
//        }
        return xhzCategoriesArrayList;
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
}
