package com.qunar.fresh.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by dhy on 16-7-16.
 */
public class User implements Comparable{
    private String id;
    private Set<String> nickNameSet;
    private int chatCnt;
    private String latestNickName;
    private Date latestNickNameDate;

    public User() {
        this.nickNameSet = new HashSet<String>();
        this.chatCnt = 0;
    }

    @Override
    public boolean equals(Object obj) {
        boolean flag = false;
        if (obj instanceof User) {
            if (this.id == ((User) obj).getId()) {
                flag = true;
            }
        }
        return flag;
    }


    public int compareTo(Object o) {
        User user = (User)o;
        Long l1 = Long.valueOf(this.id);
        Long l2 = Long.valueOf(user.getId());

        return (l1 < l2) ? 1 : ((l1 > l2) ? -1 : 0);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getNickNameSet() {
        return nickNameSet;
    }

    public void setNickNameSet(Set<String> nickNameSet) {
        this.nickNameSet = nickNameSet;
    }

    public int getChatCnt() {
        return chatCnt;
    }

    public void setChatCnt(int chatCnt) {
        this.chatCnt = chatCnt;
    }

    public String getLatestNickName() {
        return latestNickName;
    }

    public void setLatestNickName(String latestNickName) {
        this.latestNickName = latestNickName;
    }

    public Date getLatestNickNameDate() {
        return latestNickNameDate;
    }

    public void setLatestNickNameDate(Date latestNickNameDate) {
        this.latestNickNameDate = latestNickNameDate;
    }
}
