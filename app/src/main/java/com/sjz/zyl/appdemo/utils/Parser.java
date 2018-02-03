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

import com.sjz.zyl.appdemo.domain.Categories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJDB;

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
                data.setSequence(obj.optString("Sequence","测试"));
                data.setDisplayType(obj.optString("DisplayType","测试"));
                data.setArticleTypeID(obj.optInt("ArticleTypeID",1));
                data.setArticleType(obj.optString("ArticleType","测试"));
                data.setCategoryIconURL(obj.optString("CategoryIconURL","测试"));
                data.setTypeIconURL(obj.optString("TypeIconURL","测试"));

                if(jkdb.findAllByWhere(Categories.class,"ArticleCategoryID = "+obj.optString("ArticleCategoryID")).size()!=0)
                    jkdb.update(data);
                else
                    jkdb.save(data);
                datas.add(data);
            }
        } catch (JSONException e) {
            Log.e("xhz", "getCategories()解析异常");
        }

        return datas;
    }
}
