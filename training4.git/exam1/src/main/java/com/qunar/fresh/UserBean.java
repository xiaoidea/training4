package com.qunar.fresh;

import java.util.ArrayList;
import java.util.List;

/**
 * 将每个用户作为一个对象，应该记录 用户发言数/使用过的昵称/用户ID 信息
 * Created by hengyudai on 16-7-13.
 */
public class UserBean {
    private int chatCnt; //用户发言数

    private List<NickNameBean> nameList; //用户使用的昵称对象记录

    String id; //用户ID


    public UserBean() {
        this.chatCnt = 0;
        this.nameList = new ArrayList<NickNameBean>();
        this.id = "";
    }

    public int getChatCnt() {
        return chatCnt;
    }

    public List<NickNameBean> getNameList() {
        return nameList;
    }

    public void setChatCnt(int chatCnt) {
        this.chatCnt = chatCnt;
    }

    public void setNameList(List<NickNameBean> nameList) {
        this.nameList = nameList;
    }

    public void setId(String id) {
        this.id = id;
    }

}
