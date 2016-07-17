package com.qunar.fresh.fileInput;

import com.qunar.fresh.entity.Message;
import com.qunar.fresh.entity.User;
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
public class UserGenerater implements POGenerater {
    private static final Logger logger = LoggerFactory.getLogger(UserGenerater.class);
    private static Object lock = new Object();
    private static UserGenerater userGenerater = null;

    public static UserGenerater getUserGenerater() {
        if (userGenerater == null) {
            synchronized (lock) {
                if (userGenerater == null) {
                    userGenerater = new UserGenerater();
                }
            }
        }
        return userGenerater;
    }

    public Object generate(Object o) {
        List<String> list = (List<String>)o;

        List<User> userList = new ArrayList<User>();
        Map<String, User> filter = new HashMap<String, User>();

        for (String line : list) {
            String regex = "(20\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])\\s([0-2][0-9]:[0-5][0-9]:[0-5][0-9])";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(line);

            Date date;
            String nickName;
            String id;

            if (m.find()) {
                //id = m.group(4).trim();
                //nickName = m.group(2).trim();
                int a = line.indexOf(m.group(0));
                int b = line.indexOf("(");
                nickName = line.substring(a + m.group(0).length() + 1, b);
                id = line.substring(b + 1, line.length() - 1);
                if (filter.containsKey(id)) {
                    User user = filter.get(id);
                    user.setChatCnt(user.getChatCnt() + 1);

                    Set<String> set = user.getNickNameSet();
                    set.add(nickName);
                    user.setNickNameSet(set);

                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(m.group(0));
                        //System.out.println("=========== " + date + "=========== " + user.getLatestNickNameDate());
                        if (user.getLatestNickNameDate().before(date)) {
                            user.setLatestNickName(nickName);
                            user.setLatestNickNameDate(date);
                        }
                    } catch (ParseException e) {
                        logger.error("exception: " + e);
                        e.printStackTrace();
                    }
                    filter.put(id, user);
                } else {
                    User user = new User();
                    user.setId(id);
                    user.setLatestNickName(nickName);
                    user.setChatCnt(1);
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(m.group(0).trim());
                        user.setLatestNickNameDate(date);
                    } catch (ParseException e) {
                        logger.error("exception: " + e);
                        e.printStackTrace();
                    }
                    filter.put(id, user);
                }
            }
        }

        for (Map.Entry<String, User> entry : filter.entrySet()) {
            userList.add(entry.getValue());
        }

        return userList;
    }
}
