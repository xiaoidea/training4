package com.qunar.fresh;

import java.util.Date;

/**
 * 将每个昵称当做一个对象，昵称应包含 使用时间/用户ID/昵称名 信息
 * Created by hengyudai on 16-7-13.
 */
public class NickNameBean {
    private Date date; //次昵称使用时间

    private String id; //用户ID

    private String nickName; //昵称名

    public NickNameBean(Date date, String id, String nickName) {
        this.date = date;
        this.id = id;
        this.nickName = nickName;
    }

    public Date getDate() {
        return date;
    }

    public String getNickName() {
        return nickName;
    }

    public String getId() {
        return id;
    }
}
