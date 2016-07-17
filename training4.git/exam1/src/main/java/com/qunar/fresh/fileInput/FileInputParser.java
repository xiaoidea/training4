package com.qunar.fresh.fileInput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhy on 16-7-16.
 */
public class FileInputParser {
    private static final Logger logger = LoggerFactory.getLogger(FileInputParser.class);
    private static Object lock = new Object();
    private static FileInputParser fileInputParser = null;
    private String path;

    public FileInputParser(String path) {
        this.path = path;
    }

    public static FileInputParser getFileInputParser(String path) {
        if (fileInputParser == null) {
            synchronized (lock) {
                if (fileInputParser == null) {
                    fileInputParser = new FileInputParser(path);
                }
            }
        }
        return fileInputParser;
    }

    public List<String> getStringList() {
        List<String> list = new ArrayList<String>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(path));
            String line;

            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            logger.error("exception " + e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error("exception " + e);
                }
            }
        }

        return list;
    }
}
