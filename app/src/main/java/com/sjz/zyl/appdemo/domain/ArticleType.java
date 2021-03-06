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
import org.kymjs.kjframe.database.annotate.OneToMany;

import java.io.Serializable;
import java.util.List;

/**
 * 标题分类父级
 *
 * @author by 张迎乐(yingle1991@gmail.com)
 * @since 2018-01-25.
 */
public class ArticleType {

    @Id()
    private int id;//
    private int ArticleTypeID; //
    private String ArticleType; //
    private String ArticleTypeIcon;
    private int Sequence; //
    private String TypeIconURL; //
    @OneToMany(manyColumn = "ArticleTypeID")
    private List<Categories> categoriesList;

    public int getArticleTypeID() {
        return ArticleTypeID;
    }

    public void setArticleTypeID(int articleTypeID) {
        ArticleTypeID = articleTypeID;
    }

    public String getArticleType() {
        return ArticleType;
    }

    public void setArticleType(String articleType) {
        ArticleType = articleType;
    }

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int sequence) {
        Sequence = sequence;
    }

    public String getTypeIconURL() {
        return TypeIconURL;
    }

    public void setTypeIconURL(String typeIconURL) {
        TypeIconURL = typeIconURL;
    }

    public List<Categories> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Categories> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public String getArticleTypeIcon() {
        return ArticleTypeIcon;
    }

    public void setArticleTypeIcon(String articleTypeIcon) {
        ArticleTypeIcon = articleTypeIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
