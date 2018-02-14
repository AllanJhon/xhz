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
public class Article implements Serializable {

    @Id()
    private int id;
    private int ArticleID; //
    private String ArticleLogo; //
    private String ArticleIcon; //
    private String ArticleTitle; //
    private String Article; //
    private String Mobile; //
    private String Location; //
    private String LocationLongitude; //
    private String LocationLatitude; //
//    private String IsActive; //
    private String Sequence; //
    private int ArticleCategoryID; //
//    private String created_at; //
//    private String updated_at; //
//    private String DocumentID; //
//    private String DocumentURL; //
//    private String DocumentTitle; //

    public int getArticleID() {
        return ArticleID;
    }

    public void setArticleID(int articleID) {
        ArticleID = articleID;
    }

    public int getArticleCategoryID() {
        return ArticleCategoryID;
    }

    public void setArticleCategoryID(int articleCategoryID) {
        ArticleCategoryID = articleCategoryID;
    }

    public String getArticleLogo() {
        return ArticleLogo;
    }

    public void setArticleLogo(String articleLogo) {
        ArticleLogo = articleLogo;
    }

    public String getArticleIcon() {
        return ArticleIcon;
    }

    public void setArticleIcon(String articleIcon) {
        ArticleIcon = articleIcon;
    }

    public String getArticleTitle() {
        return ArticleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        ArticleTitle = articleTitle;
    }

    public String getArticle() {
        return Article;
    }

    public void setArticle(String article) {
        Article = article;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLocationLongitude() {
        return LocationLongitude;
    }

    public void setLocationLongitude(String locationLongitude) {
        LocationLongitude = locationLongitude;
    }

    public String getLocationLatitude() {
        return LocationLatitude;
    }

    public void setLocationLatitude(String locationLatitude) {
        LocationLatitude = locationLatitude;
    }

//    public String getIsActive() {
//        return IsActive;
//    }
//
//    public void setIsActive(String isActive) {
//        IsActive = isActive;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSequence() {
        return Sequence;
    }

    public void setSequence(String sequence) {
        Sequence = sequence;
    }

//    public String getCreated_at() {
//        return created_at;
//    }
//
//    public void setCreated_at(String created_at) {
//        this.created_at = created_at;
//    }
//
//    public String getUpdated_at() {
//        return updated_at;
//    }
//
//    public void setUpdated_at(String updated_at) {
//        this.updated_at = updated_at;
//    }
//
//    public String getDocumentID() {
//        return DocumentID;
//    }
//
//    public void setDocumentID(String documentID) {
//        DocumentID = documentID;
//    }
//
//    public String getDocumentURL() {
//        return DocumentURL;
//    }
//
//    public void setDocumentURL(String documentURL) {
//        DocumentURL = documentURL;
//    }
//
//    public String getDocumentTitle() {
//        return DocumentTitle;
//    }
//
//    public void setDocumentTitle(String documentTitle) {
//        DocumentTitle = documentTitle;
//    }
}
