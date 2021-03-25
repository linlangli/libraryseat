package io.github.grooters.libraryseat.model;

import java.io.Serializable;

/**
 * Create by 李林浪 in 2018/11/11
 * Elegant Code...
 */
public class MainPost implements Serializable {
    private int id;
    // 发帖人名称
    private String userName;
    // 发帖人学号
    private String userNumber;
    // 帖子标题
    private String title;
    // 帖子的点赞数
    private int praiseNum;
    // 帖子发表日期
    private String date;
    // 帖子内容
    private String content;

    public MainPost() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}
