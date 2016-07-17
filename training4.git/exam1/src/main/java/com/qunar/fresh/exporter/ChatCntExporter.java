package com.qunar.fresh.exporter;

import com.qunar.fresh.entity.User;
import com.qunar.fresh.interfaces.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by dhy on 16-7-16.
 */
public class ChatCntExporter implements Exporter {
    private static final Logger logger = LoggerFactory.getLogger(ChatCntExporter.class);
    private static Object lock = new Object();
    private static ChatCntExporter chatCntExporter = null;

    public static ChatCntExporter getChatCntExporter() {
        if (chatCntExporter == null) {
            synchronized (lock) {
                if (chatCntExporter == null) {
                    chatCntExporter = new ChatCntExporter();
                }
            }
        }
        return chatCntExporter;
    }

    public void export(Object o, String path) {
        List<User> userList = (List<User>) o;

        FileWriter writer = null;

        System.out.println(userList.size() + "-----");

        try {
            writer = new FileWriter(path);
            for (User user : userList) {
                System.out.println(user.getId());
                writer.write(user.getId() + ":" + user.getChatCnt());
                writer.write(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            logger.error("exception" + e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.error("exception" + e);
                }
            }
        }

    }
}
