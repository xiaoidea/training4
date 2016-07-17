package com.qunar.fresh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对原始的聊天文件进行解析
 * Created by hengyudai on 16-7-13.
 */
public class ParseFile {

    private static Logger logger = LoggerFactory.getLogger(ParseFile.class);

    /* 存储聊天记录块的block */
    private static List<ContentBlockBean> blockContainer = new ArrayList<ContentBlockBean>();

    /* 存储用户ID到用户实体的映射 */
    private static Map<String, UserBean> userContainer = new HashMap<String, UserBean>();

    /**
     * 获取聊天记录块，存入一个list中
     * @param filePath 文件路径
     * @return 聊天记录块列表
     */
    public static List<ContentBlockBean> getContentBlocks(String filePath) {
        if (blockContainer.size() == 0) { //如果还没有被解析过，调用init方法解析，将读文件抽取出来避免重复读
            init(filePath);
        }
        return blockContainer;
    }

    /**
     * 获取用户ID到用户实体的映射
     * @param filePath 文件路径
     * @return 用户ID到用户的映射
     */
    public static Map<String, UserBean> getUsers(String filePath) {
        if (userContainer.size() == 0) { //如果还没有被解析过，调用init方法解析
            init(filePath);
        }
        return userContainer;
    }

    /**
     * 读文件，将blockContainer和userContainer初始化
     * @param filePath 文件路径
     */
    private static void init(String filePath) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            List<String> statements = new ArrayList<String>();
            HeadBean head = new HeadBean();
            while ((line = reader.readLine()) != null) {
                String time = time(line);
                if (!time.equals("")) {
                    if (statements.size() != 0) {
                        ContentBlockBean contentBlockBean = new ContentBlockBean(head, statements);
                        blockContainer.add(contentBlockBean);
                        statements = new ArrayList<String>();
                    }
                    head = getHead(line, time);
                    addToMap(head, userContainer);

                } else {
                    statements.add(line);
                }
            }
            if (statements.size() != 0) {
                ContentBlockBean contentBlockBean = new ContentBlockBean(head, statements);
                blockContainer.add(contentBlockBean);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 判断当前行是否是聊天头部，使用正则表达式匹配时间
     * @param line 行
     * @return 如果是头部，返回匹配的时间，如果不是头部，返回空字符串
     */
    private static String time(String line) {
        String regex = "(20\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])\\s([0-2][0-9]:[0-5][0-9]:[0-5][0-9])";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);

        if (m.find()) {
            return m.group(0);
        }
        return "";
    }

    /**
     * 对于一个聊天头部，将其解析为head对象
     * @param line 聊天头部
     * @param time 聊天时间
     * @return 头部对象
     */
    private static HeadBean getHead(String line, String time) {
        int a = line.indexOf(time);
        int b = line.indexOf("(");
        String nickName = line.substring(a + time.length() + 1, b);
        String id = line.substring(b + 1, line.length() - 1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }

        HeadBean head = new HeadBean(date, nickName, id);

        return head;
    }

    /**
     * init方法中需要调用该方法，将一个新的“用户ID-用户实体”映射加入到map中
     * @param head 聊天头
     * @param userBeanMap “用户ID-用户实体”映射
     */
    private static void addToMap(HeadBean head, Map<String, UserBean> userBeanMap) {
        if (userBeanMap.containsKey(head.getId())) {
            UserBean userBean = userBeanMap.get(head.getId());
            userBean.setChatCnt(userBean.getChatCnt() + 1);
            userBean.getNameList().add(new NickNameBean(head.getDate(), head.getId(), head.getNickName()));
            userBeanMap.put(head.getId(), userBean);
        } else {
            UserBean userBean = new UserBean();
            userBean.setChatCnt(1);
            userBean.setId(head.getId());
            List<NickNameBean> nameList = userBean.getNameList();
            nameList.add(new NickNameBean(head.getDate(), head.getId(), head.getNickName()));

            userBean.setNameList(nameList);
            userBeanMap.put(head.getId(), userBean);
        }
    }

    /**
     * 从“用户ID-用户实体”映射中，根据某一用户ID，取出该用户最后使用的昵称
     * @param id 用户ID
     * @return 用户最后使用的昵称
     */
    public static String getLastNickName(String id) {
        String nick = userContainer.get(id).getNameList().get(0).getNickName();
        Date latest = userContainer.get(id).getNameList().get(0).getDate();

        for (NickNameBean nickNameBean : userContainer.get(id).getNameList()){
            if (nickNameBean.getDate().getTime() > latest.getTime())
                nick = nickNameBean.getNickName();
        }

        return nick;
    }
}
