package io.github.grooters.libraryseat.model;

/**
 * Create by 李林浪 in 2018/11/11
 * Elegant Code...
 */
public class ChildPost {
    private int id;
    // 发表子帖者姓名
    private String userName;
    // 发表子帖者学号
    private String userNumber;
    // 子帖内容
    private String content;
    // 子帖发表日期
    private String date;
    // 子帖所属帖子
    private int mainPostId;
    private int praiseNum;

    public ChildPost() {
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


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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


    public int getMainPostId() {
        return mainPostId;
    }

    public void setMainPostId(int mainPostId) {
        this.mainPostId = mainPostId;
    }
}
