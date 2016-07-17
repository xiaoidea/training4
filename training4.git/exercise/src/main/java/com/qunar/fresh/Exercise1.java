package com.qunar.fresh;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 题目：
 * 1)查找一个目录下，所有文件中数字、字母(大小写不区分)、汉字、空格的个数、行数。
 * 2)将结果数据写入到文件中。
 * Created by hengyudai on 16-7-11.
 */
public class Exercise1 {
    private int intCnt = 0; //数字个数
    private int alphCnt = 0; //字母个数
    private int chnCnt = 0; //汉字个数
    private int spaceCnt = 0; //空格个数
    private int lineCnt = 0; //行数

    private Map<Character, Integer> data2cnt = new HashMap<Character, Integer>(); //数字和字母与个数之间的映射


    public static void main(String[] args) {
        String path = Exercise1.class.getResource("/").getPath() + "test2";
        System.out.println("设置的根目录为：" + path);

        Exercise1 exercise1 = new Exercise1();
        exercise1.recursiveTraverse(path);
        exercise1.writeResult();
    }

    /**
     * 递归遍历根目录下的所有文件
     * @param path 根目录的路径
     */
    public void recursiveTraverse(String path) {
        File root = new File(path);

        if (!root.isDirectory())
            return;

        for (File file : root.listFiles()) {
            if (file.isFile()) {
                System.out.println("开始遍历文件" + file.getPath());
                readFile(file);
            }
            else if (file.isDirectory()) {
                recursiveTraverse(file.getPath());
            }
        }
    }

    /**
     * 逐行读取file文件，遍历每个字符，更新数字/字母/汉字/空格/行数
     * 将对应的数字和字母更新到map中
     * @param file
     */
    private void readFile(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    int v = (int)c;

                    if (Character.isDigit(c)) {   //判断是否是数字
                        intCnt += 1;
                        if (data2cnt.containsKey(c)) {
                            data2cnt.put(c, data2cnt.get(c) + 1);
                        }
                        else {
                            data2cnt.put(c, 0);
                        }
                    }
                    else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {  //判断是否是字母
                        alphCnt += 1;
                        c = Character.toUpperCase(c);
                        if (data2cnt.containsKey(c)) {
                            data2cnt.put(c, data2cnt.get(c) + 1);
                        }
                        else {
                            data2cnt.put(c, 0);
                        }
                    }
                    else if (v >= 19968 && v <= 171941) {  //判断是否是汉字
                        chnCnt += 1;
                    }
                    else if (c == ' ') {  //判断是够是空格
                        spaceCnt += 1;
                    }
                    lineCnt += 1;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("没有找到文件：" + file.getPath());

        } catch (IOException e) {
            System.out.println("无法打开文件: " + file.getPath());

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将统计结果写入classes目录下的result.txt文件中
     */
    public void writeResult() {
        String path = Exercise1.class.getResource("/").getPath() + "result.txt";
        try {
            FileWriter writer = new FileWriter(path);
            writer.write("数字：" + intCnt + "个\n");
            writer.write("字母：" + alphCnt + "个\n");
            writer.write("汉字：" + chnCnt + "个\n");
            writer.write("空格：" + spaceCnt + "个\n");
            writer.write("行数：" + lineCnt + "行\n");

            for (char c = '0'; c <= '9'; c++) {
                writer.write("数字" + c + ":" + (data2cnt.containsKey(c) ? data2cnt.get(c) : 0) + "个\n");
            }

            for (char c = 'A'; c <= 'Z'; c++) {
                writer.write("字母" + c + ":" + (data2cnt.containsKey(c) ? data2cnt.get(c) : 0) + "个\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("写入文件时发生错误：" + path);
        }

        System.out.println("已将统计结果写入result.txt！");
    }
}