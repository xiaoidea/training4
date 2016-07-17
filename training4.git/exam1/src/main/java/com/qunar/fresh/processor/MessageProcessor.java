package com.qunar.fresh.processor;

import com.qunar.fresh.entity.Message;
import com.qunar.fresh.interfaces.Processer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dhy on 16-7-16.
 */
public class MessageProcessor implements Processer {
    private static Object lock = new Object();
    private static MessageProcessor messageProcessor = null;

    public static MessageProcessor getMessageProcessor() {
        if (messageProcessor == null) {
            synchronized (lock) {
                if (messageProcessor == null) {
                    messageProcessor = new MessageProcessor();
                }
            }
        }
        return messageProcessor;
    }

    public Object process(Object o) {
        List<Message> messageList = (List<Message>)o;
        //Collections.sort(messageList);
        Collections.sort(messageList, new Comparator<Message>() {
            public int compare(Message o1, Message o2) {
                if (o1.getDate().getTime() > o2.getDate().getTime())
                    return 1;
                else if (o1.getDate().getTime() < o2.getDate().getTime())
                    return -1;
                else
                    return 0;
            }
        });
        return messageList;
    }
}
