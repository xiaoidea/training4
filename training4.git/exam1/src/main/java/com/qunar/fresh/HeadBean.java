package com.qunar.fresh;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将每条聊天记录的头（包含 时间/昵称/ID）当做一个对象
 * Created by hengyudai on 16-7-13.
 */
public class HeadBean {
    private Date date;
    private String nickName;
    private String id;

    public HeadBean(Date date, String nickName, String id) {
        this.date = date;
        this.nickName = nickName;
        this.id = id;
    }

    public HeadBean() {
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

    public String headLine(boolean isLatestNickName) {

        if (isLatestNickName) {
            nickName = ParseFile.getLastNickName(id);
        }

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return fmt.format(date) + " " + nickName + "(" + id + ")";
    }
}
