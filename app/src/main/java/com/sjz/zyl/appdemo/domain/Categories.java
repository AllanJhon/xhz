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
package com.sjz.zyl.appdemo.domain;

import org.kymjs.kjframe.database.annotate.Id;

import java.io.Serializable;

/**
 * Description
 *
 * @author by 张迎乐(yingle1991@gmail.com)
 * @since 2018-01-25.
 */
public class Categories {

    @Id()
    private int ArticleCategoryID; //
    private String ArticleCategory; //
    private String Sequence; //
    private String ArticleCategoryIcon; //
    private String DisplayType; //
    private int ArticleTypeID; //
    private String ArticleType; //
    private String CategoryIconURL; //
    private String TypeIconURL; //

    public int getArticleCategoryID() {
        return ArticleCategoryID;
    }

    public int getArticleTypeID() {
        return ArticleTypeID;
    }

    public void setArticleCategoryID(int articleCategoryID) {
        ArticleCategoryID = articleCategoryID;
    }

    public void setArticleTypeID(int articleTypeID) {
        ArticleTypeID = articleTypeID;
    }

    public String getArticleCategory() {
        return ArticleCategory;
    }

    public void setArticleCategory(String articleCategory) {
        ArticleCategory = articleCategory;
    }

    public String getSequence() {
        return Sequence;
    }

    public void setSequence(String sequence) {
        Sequence = sequence;
    }

    public String getDisplayType() {
        return DisplayType;
    }

    public void setDisplayType(String displayType) {
        DisplayType = displayType;
    }

    public String getArticleType() {
        return ArticleType;
    }

    public void setArticleType(String articleType) {
        ArticleType = articleType;
    }

    public String getCategoryIconURL() {
        return CategoryIconURL;
    }

    public void setCategoryIconURL(String categoryIconURL) {
        CategoryIconURL = categoryIconURL;
    }

    public String getTypeIconURL() {
        return TypeIconURL;
    }

    public void setTypeIconURL(String typeIconURL) {
        TypeIconURL = typeIconURL;
    }

    public String getArticleCategoryIcon() {
        return ArticleCategoryIcon;
    }

    public void setArticleCategoryIcon(String articleCategoryIcon) {
        ArticleCategoryIcon = articleCategoryIcon;
    }
}
