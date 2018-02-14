/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sjz.zyl.appdemo.utils;


import android.util.Log;

import com.sjz.zyl.appdemo.domain.Article;
import com.sjz.zyl.appdemo.domain.ArticleType;
import com.sjz.zyl.appdemo.domain.Categories;
import com.sjz.zyl.appdemo.domain.Documents;
import com.sjz.zyl.appdemo.domain.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.database.utils.TableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析工具类
 *
 * @author
 */
public class Parser {

    /**
     * 类别列表
     *
     * @param json
     * @return
     */
    public static List<Categories> getCategories(String json) {
        List<Categories> datas = new ArrayList<Categories>();
        KJDB jkdb= KJDB.create();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Categories data = new Categories();
                JSONObject obj = jsonArray.getJSONObject(i);
                data.setArticleCategoryID(obj.optInt("ArticleCategoryID",1));
                data.setArticleCategory(obj.optString("ArticleCategory","测试"));
                data.setSequence(obj.optInt("Sequence",1));
                data.setArticleCategoryIcon(obj.optString("ArticleCategoryIcon","测试"));
                data.setDisplayType(obj.optString("DisplayType","测试"));
                data.setArticleTypeID(obj.optInt("ArticleTypeID",1));
                data.setArticleType(obj.optString("ArticleType","测试"));
                data.setCategoryIconURL(obj.optString("CategoryIconURL","测试"));
                data.setTypeIconURL(obj.optString("TypeIconURL","测试"));

                List<Categories> datas1 = jkdb.findAllByWhere(Categories.class,"ArticleCategoryID = "+obj.optInt("ArticleCategoryID"));
                if(jkdb.findAllByWhere(Categories.class,"ArticleCategoryID = "+obj.optInt("ArticleCategoryID")).size()>0)
//                    jkdb.update(data);
                jkdb.update(data,"ArticleCategoryID = "+obj.optInt("ArticleCategoryID"));
                else
                    jkdb.save(data);
                datas.add(data);
            }
        } catch (JSONException e) {
            Log.e("xhz", "getCategories()解析异常");
        }

        return datas;
    }

    /**
     * 类别类型列表
     *
     * @param json
     * @return
     */
    public static List<ArticleType> getArriticleTypes(String json) {
        List<ArticleType> datas = new ArrayList<ArticleType>();
        KJDB jkdb= KJDB.create();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                ArticleType data = new ArticleType();
                JSONObject obj = jsonArray.getJSONObject(i);
                data.setSequence(obj.optInt("Sequence",1));
                data.setArticleTypeID(obj.optInt("ArticleTypeID",1));
                data.setArticleType(obj.optString("ArticleType","测试"));
                data.setTypeIconURL(obj.optString("TypeIconURL","测试"));
                data.setArticleTypeIcon(obj.optString("ArticleTypeIcon","测试"));
                if(jkdb.findAllByWhere(ArticleType.class,"ArticleTypeID = "+obj.optString("ArticleTypeID")).size()!=0)
                    jkdb.update(data);
                else
                    jkdb.save(data);
                datas.add(data);
            }
        } catch (JSONException e) {
            Log.e("xhz", "getArriticleTypes()解析异常");
        }

        return datas;
    }

    /**
     * 文章列表
     *
     * @param json
     * @return
     */
    public static List<Article> getArticles(String json) {
        List<Article> datas = new ArrayList<Article>();
        KJDB jkdb= KJDB.create();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Article data = new Article();
                JSONObject obj = jsonArray.getJSONObject(i);
                data.setArticleID(obj.optInt("ArticleID"));
                data.setArticleLogo(obj.optString("ArticleLogo"));
                data.setArticleIcon(obj.optString("ArticleIcon"));
                data.setArticleTitle(obj.optString("ArticleTitle"));
                data.setArticleTitle(obj.optString("ArticleTitle"));
                data.setArticle(obj.optString("Article"));
                data.setMobile(obj.optString("Mobile"));
                data.setLocation(obj.optString("Location"));
                data.setLocationLongitude(obj.optString("LocationLongitude"));
                data.setLocationLatitude(obj.optString("LocationLatitude"));
                data.setSequence(obj.optString("Sequence","测试"));
                data.setArticleCategoryID(obj.optInt("ArticleCategoryID"));

                if(jkdb.findAllByWhere(Article.class,"ArticleID = "+obj.optString("ArticleID")).size()!=0)
                    jkdb.update(data);
                else
                    jkdb.save(data);
                datas.add(data);
            }
        } catch (JSONException e) {
            Log.e("xhz", "getArticles()解析异常");
        }

        return datas;
    }


    /**
     * 首页新闻列表
     *
     * @param json
     * @return
     */
    public static List<News> getNews(String json) {
        List<News> datas = new ArrayList<News>();
        KJDB jkdb= KJDB.create();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                News data = new News();
                JSONObject obj = jsonArray.getJSONObject(i);
                data.setNewsID(obj.getInt("NewsID"));
                data.setNewsLogo(obj.optString("NewsLogo"));
                data.setNewsTitle(obj.optString("NewsTitle"));
                data.setNewsContent(obj.optString("NewsContent"));
                data.setAuthor(obj.optString("Author"));
                data.setReleaseDate(obj.optString("ReleaseDate"));
                if(jkdb.findAllByWhere(News.class,"NewsID = "+obj.optString("NewsID")).size()!=0)
                    jkdb.update(data);
                else
                    jkdb.save(data);
                datas.add(data);
            }
        } catch (JSONException e) {
            Log.e("xhz", "getNews()解析异常");
        }

        return datas;
    }

    /**
     * Document列表
     *
     * @param json
     * @return
     */
    public static List<Documents> getDocuments(String json) {
        List<Documents> datas = new ArrayList<Documents>();
        KJDB jkdb= KJDB.create();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Documents data = new Documents();
                JSONObject obj = jsonArray.getJSONObject(i);
                data.setDocumentID(obj.optInt("DocumentID"));
                data.setDocumentTitle(obj.optString("DocumentTitle"));
                data.setDocumentURL(obj.optString("DocumentURL"));
                if(jkdb.findAllByWhere(Documents.class,"DocumentID = "+obj.optString("DocumentID")).size()!=0)
                    jkdb.update(data);
                else
                    jkdb.save(data);
                datas.add(data);
            }
        } catch (JSONException e) {
            Log.e("xhz", "getDocuments()解析异常");
        }

        return datas;
    }

    /**
     * Document列表
     *
     * @param DocumentId
     * @return
     */
    public static String getUrl(String DocumentId) {
        KJDB jkdb= KJDB.create();
        String url="";
        try {
            TableInfo table = TableInfo.get(Documents.class);
            url=jkdb.findDbModelBySQL("select * from "+table.getTableName()+" where DocumentID= "+DocumentId).getString("DocumentURL");
            Log.e("xhz", "url: "+url);
        } catch (Exception e) {
            Log.e("xhz", "getUrl()解析异常");
        }

        return url;
    }
}
