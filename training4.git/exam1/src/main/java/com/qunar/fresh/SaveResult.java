package com.qunar.fresh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * 保存结果类
 * Created by hengyudai on 16-7-13.
 */
public class SaveResult {
    private static Logger logger = LoggerFactory.getLogger(SaveResult.class);

    /**
     * 将排序后的聊天记录写入文件
     * @param list 排序后的block块
     * @param sv 是否记录
     * @param path 保存路径
     * @param isLatestNickName 是否在聊天记录中保存最近使用的昵称
     */
    public static void saveFile(List<ContentBlockBean> list, boolean sv, String path, boolean isLatestNickName) {
        if (sv == false)
            return;

        FileWriter writer = null;
        try {
            writer = new FileWriter(path);

            for (ContentBlockBean content : list) {
                writer.write(content.getHead().headLine(isLatestNickName));
                writer.write(System.getProperty("line.separator"));
                for (String line : content.getStatements()) {
                    writer.write(line);
                    writer.write(System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将按照用户ID排序的用户发言数写入文件
     * @param map 按照用户ID排序好的treemap
     * @param sv 是否保存当前项
     * @param path 保存路径
     */
    public static void saveChatCnt(Map<String, UserBean> map, boolean sv, String path) {
        if (sv == false)
            return;

        FileWriter writer = null;

        try {
            writer = new FileWriter(path);
            for (Map.Entry<String, UserBean> entry : map.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue().getChatCnt());
                writer.write(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 将按照用户使用的昵称记录排序写入文件
     * @param map 包含用户对象的map
     * @param sv 是否保存当前项
     * @param path 保存路径
     */
    public static void saveNickName(Map<String, UserBean> map, boolean sv, String path) {
        if (sv == false)
            return;

        FileWriter writer = null;

        try {
            writer = new FileWriter(path);
            for (Map.Entry<String, UserBean> entry : map.entrySet()) {
                writer.write(entry.getKey() + ":");

                List<NickNameBean> list = entry.getValue().getNameList();

                Set<String> set = new HashSet<String>(); //从这里到下面第二个for循环之前是为了去掉昵称的重复记录

                List<String> uniqueNickName = new ArrayList<String>();

                for (NickNameBean bean : list) {
                    if (set.add(bean.getNickName())) {
                        uniqueNickName.add(bean.getNickName());
                    }
                }

                for (int i = 0; i < uniqueNickName.size(); i++) {
                    if (i != uniqueNickName.size() - 1) {
                        writer.write(uniqueNickName.get(i) + ",");
                    } else {
                        writer.write(uniqueNickName.get(i));
                    }
                }

                writer.write(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }
}
