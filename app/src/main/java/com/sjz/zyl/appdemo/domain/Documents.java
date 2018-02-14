package com.sjz.zyl.appdemo.domain;

import org.kymjs.kjframe.database.annotate.Id;

/**
 * Description
 *
 * @author by 张迎乐(yingle1991@gmail.com)
 * @since 2018-02-04.
 */
public class Documents {

    @Id()
    private int id;
    private int DocumentID;
    private String DocumentURL;
    private String DocumentTitle;

    public int getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(int documentID) {
        DocumentID = documentID;
    }

    public String getDocumentURL() {
        return DocumentURL;
    }

    public void setDocumentURL(String documentURL) {
        DocumentURL = documentURL;
    }

    public String getDocumentTitle() {
        return DocumentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        DocumentTitle = documentTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
