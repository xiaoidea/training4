package com.qunar.fresh.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dhy on 16-7-16.
 */
public class Message implements Comparable{
    private String id;
    private String nickName;
    private Date date;
    private List<String> chatLists;

    public Message() {
        this.chatLists = new ArrayList<String>();
    }

    public int compareTo(Object o) {
        Message m = (Message)o;

        if (this.date.after(m.getDate())) {
            return 1;
        } else if (this.date.before(m.getDate())) {
            return -1;
        } else {
            Long l1 = Long.valueOf(this.id);
            Long l2 = Long.valueOf(m.getId());

            return (l1 < l2) ? 1 : ((l1 > l2) ? -1 : 0);
            //return 0;
        }
    }

    @Override
    public String toString() {
        String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) +
                " " + nickName +
                "(" + id +
                ")";
        str += System.getProperty("line.separator");

        for (String string : chatLists){
            str += string + System.getProperty("line.separator");
        }

        return str;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getChatLists() {
        return chatLists;
    }

    public void setChatLists(List<String> chatLists) {
        this.chatLists = chatLists;
    }
}
