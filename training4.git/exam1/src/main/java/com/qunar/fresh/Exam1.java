package com.qunar.fresh;

import com.qunar.fresh.entity.Message;
import com.qunar.fresh.exporter.ChatCntExporter;
import com.qunar.fresh.exporter.MessageExporter;
import com.qunar.fresh.fileInput.FileInputParser;
import com.qunar.fresh.fileInput.MessageGenerater;
import com.qunar.fresh.fileInput.UserGenerater;
import com.qunar.fresh.interfaces.Exporter;
import com.qunar.fresh.interfaces.POGenerater;
import com.qunar.fresh.interfaces.Processer;
import com.qunar.fresh.processor.ChatCntProcessor;
import com.qunar.fresh.processor.MessageProcessor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dhy on 16-7-16.
 */
public class Exam1 {
    /* chat.txt 的文件路径 */
    public static final String FILE_PATH = Exam1.class.getResource("/").getPath() + "chat.txt";

    /* 聊天记录保存路径 */
    public static final String FILE_SAVE_PATH = "chat_sorted_new.txt";
    /* 发言次数保存路径 */
    public static final String CHAT_CNT_SAVE_PATH = "count_new.txt";
    /* 昵称记录保存路径 */
    public static final String NICKNAME_SAVE_PATH = "nickname_new.txt";

    public static void main(String[] args) {
        List<String> stringList = FileInputParser.getFileInputParser(FILE_PATH).getStringList();

        POGenerater[] poGeneraters = {MessageGenerater.getMessageGenerater(), UserGenerater.getUserGenerater()};

        Processer[] processers = {MessageProcessor.getMessageProcessor(), ChatCntProcessor.getChatCntProcessor()};

        Exporter[] exporters = {MessageExporter.getMessageExporter(), ChatCntExporter.getChatCntExporter()};

        String[] paths = {FILE_SAVE_PATH, CHAT_CNT_SAVE_PATH};

        for (int i = 0; i < poGeneraters.length; i++) {
            Object o = poGeneraters[i].generate(stringList);

            List<Message> list = (List<Message>)o;
            //System.out.printf(" " + list.size());


            o = processers[i].process(o);

            list = (List<Message>)o;

            exporters[i].export(o, paths[i]);
        }
    }


}
