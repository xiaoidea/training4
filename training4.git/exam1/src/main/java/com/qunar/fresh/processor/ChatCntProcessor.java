package com.qunar.fresh.processor;

import com.qunar.fresh.entity.User;
import com.qunar.fresh.interfaces.Processer;

import java.util.Collections;
import java.util.List;

/**
 * Created by dhy on 16-7-16.
 */
public class ChatCntProcessor implements Processer {
    private static Object lock = new Object();
    private static ChatCntProcessor chatCntProcessor = null;

    public static ChatCntProcessor getChatCntProcessor() {
        if (chatCntProcessor == null) {
            synchronized (lock) {
                if (chatCntProcessor == null) {
                    chatCntProcessor = new ChatCntProcessor();
                }
            }
        }
        return chatCntProcessor;
    }

    public Object process(Object o) {
        List<User> userList = (List<User>)o;
        Collections.sort(userList);
        return userList;
    }
}
