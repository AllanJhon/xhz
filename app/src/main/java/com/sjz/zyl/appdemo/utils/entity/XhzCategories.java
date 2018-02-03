package com.sjz.zyl.appdemo.utils.entity;

import com.sjz.zyl.appdemo.domain.ArticleType;

import java.util.List;

/**
 * Created by Administrator on 2015/6/16.
 */
public class XhzCategories extends BaseData {

    public XhzCategories(int id, String name) {
        super(id, name);
    }

    private List<ArticleType> articleType;

    public List<ArticleType> getArticleType() {
        return articleType;
    }

    public void setArticleType(List<ArticleType> articleType) {
        this.articleType = articleType;
    }
}
