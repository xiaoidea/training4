package com.qunar.fresh.fileInput;

import com.qunar.fresh.entity.Message;
import com.qunar.fresh.interfaces.POGenerater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dhy on 16-7-16.
 */
public class MessageGenerater implements POGenerater {
    private static final Logger logger = LoggerFactory.getLogger(MessageGenerater.class);
    private static Object lock = new Object();
    private static MessageGenerater messageGenerater = null;

    public static MessageGenerater getMessageGenerater() {
        if (messageGenerater == null) {
            synchronized (lock) {
                if (messageGenerater == null) {
                    messageGenerater = new MessageGenerater();
                }
            }
        }
        return messageGenerater;
    }

    public Object generate(Object o) {
        List<String> list = (List<String>)o;
        Map<Integer, Message> map = new HashMap<Integer, Message>();
        List<Message> messageList = new ArrayList<Message>();

        int count = 0;
        for (String line : list) {
            String regex = "(20\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])\\s([0-2][0-9]:[0-5][0-9]:[0-5][0-9])";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(line);

            Date date;
            String nickName;
            String id;

            if(m.find()) {
                count++;
                Message message = new Message();
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(m.group(0));
                    message.setDate(date);
                } catch (ParseException e) {
                    logger.error("exception: " + e);
                    e.printStackTrace();
                }
                //nickName = m.group(2).trim();
                //id = m.group(4).trim();

                int a = line.indexOf(m.group(0));
                int b = line.indexOf("(");
                nickName = line.substring(a + m.group(0).length() + 1, b);
                id = line.substring(b + 1, line.length() - 1);

                message.setNickName(nickName);
                message.setId(id);

                //System.out.println(date + "  " + nickName + "  " + id);
                map.put(count, message);
            } else {
                Message message = map.get(count);

                if (message != null) {
                    List<String> chatLists = message.getChatLists();
                    chatLists.add(line);
                    message.setChatLists(chatLists);
                }
            }
        }

        for (Map.Entry<Integer, Message> entry : map.entrySet()){
            messageList.add(entry.getValue());
        }

        return messageList;
    }
}
