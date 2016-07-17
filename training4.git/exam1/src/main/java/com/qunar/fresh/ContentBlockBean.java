package com.qunar.fresh;

import java.util.List;

/**
 * 将每条聊天记录当做一个对象。
 * 包含 头信息和聊天记录
 * Created by hengyudai on 16-7-13.
 */
public class ContentBlockBean {
    private HeadBean head; //block的头部对象
    private List<String> statements; //聊天记录list

    public ContentBlockBean(HeadBean head, List<String> statements) {
        this.head = head;
        this.statements = statements;
    }

    public HeadBean getHead() {
        return head;
    }

    public List<String> getStatements() {
        return statements;
    }

}
