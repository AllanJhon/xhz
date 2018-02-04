package com.sjz.zyl.appdemo.domain;

import org.kymjs.kjframe.database.annotate.Id;

/**
 * Description
 *
 * @author by 张迎乐(yingle1991@gmail.com)
 * @since 2018-02-04.
 */
public class News {

    @Id()
    private int NewsID;
    private String NewsLogo;
    private String NewsTitle;
    private String NewsContent;
    private String Author;
    private String ReleaseDate;

    public int getNewsID() {
        return NewsID;
    }

    public void setNewsID(int newsID) {
        NewsID = newsID;
    }

    public String getNewsLogo() {
        return NewsLogo;
    }

    public void setNewsLogo(String newsLogo) {
        NewsLogo = newsLogo;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsContent() {
        return NewsContent;
    }

    public void setNewsContent(String newsContent) {
        NewsContent = newsContent;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }
}
