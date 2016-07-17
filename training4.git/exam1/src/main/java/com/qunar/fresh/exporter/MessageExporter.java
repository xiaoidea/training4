package com.qunar.fresh.exporter;

import com.qunar.fresh.entity.Message;
import com.qunar.fresh.interfaces.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by dhy on 16-7-16.
 */
public class MessageExporter implements Exporter{
    private static final Logger logger = LoggerFactory.getLogger(Message.class);
    private static Object lock = new Object();
    private static MessageExporter messageExporter = null;

    public static MessageExporter getMessageExporter() {
        if (messageExporter == null) {
            synchronized (lock) {
                if (messageExporter == null) {
                    messageExporter = new MessageExporter();
                }
            }
        }
        return messageExporter;
    }

    public void export(Object o, String path) {
        List<Message> messageList = (List<Message>)o;
        System.out.println("length : ==== " + messageList.size());
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            for (Message message : messageList) {
                String head = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message.getDate()) +
                        " " + message.getNickName() +
                        "(" + message.getId() +
                        ")";
                //System.out.println(message);
                writer.write(head);
                writer.write(System.getProperty("line.separator"));
                for (String line : message.getChatLists()) {
                    writer.write(line);
                    writer.write(System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
           // logger.error("exception" + e);
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    //logger.error("exception" + e);
                    e.printStackTrace();
                }
            }
        }
    }
}
