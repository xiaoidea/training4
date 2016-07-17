package com.qunar.fresh;

import java.util.*;

/**
 * 排序工具类
 * Created by hengyudai on 16-7-13.
 */
public class SortUtil {

    /**
     * 对聊天记录块进行排序，优先使用靠前的参数排序
     * @param list 聊天记录块的list
     * @param args 可变参数，控制按照哪一项进行排序
     * @return 排序好的block list
     */
    public static List<ContentBlockBean> sortContentBlock(List<ContentBlockBean> list, String... args) {
        final List<String> variables = new ArrayList<String>(Arrays.asList(args));

        Collections.sort(list, new Comparator<ContentBlockBean>() {
            public int compare(ContentBlockBean o1, ContentBlockBean o2) {
                for (String var : variables) {
                    int res = compareResult(o1, o2, var);
                    if (res != 0)  //如果两者不等，返回排序结果，否则使用下一参数排序
                        return res;
                }
                return 0;
            }
        });
        return list;
    }

    /**
     * 对用户曾经使用过的昵称进行排序
     * @param map 用户ID到该用户的映射
     * @param args 可变参数，控制按照哪一项进行排序
     * @return 排序好的map
     */
    public static Map<String, UserBean> sortUserNickName(Map<String, UserBean> map, String... args) {
        final List<String> variables = new ArrayList<String>(Arrays.asList(args));

        for (Map.Entry<String, UserBean> entry : map.entrySet()) {
            UserBean userBean = entry.getValue();

            List<NickNameBean> nickNameBeanList = userBean.getNameList();

            Collections.sort(nickNameBeanList, new Comparator<NickNameBean>() {
                public int compare(NickNameBean o1, NickNameBean o2) {
                    for (String var : variables) {
                        int res = compareResult(o1, o2, var);
                        if (res != 0)   //如果两者不等，返回排序结果，否则使用下一参数排序
                            return res;
                    }
                    return 0;
                }
            });

            userBean.setNameList(nickNameBeanList);

            map.put(entry.getKey(), userBean);
        }

        return map;
    }

    /**
     * 重载函数，使其能对不同对象排序
     * @param o1 对象1
     * @param o2 对象2
     * @param args 排序参数，可选 日期/号码/昵称
     * @return 比较结果
     */
    public static int compareResult(NickNameBean o1, NickNameBean o2, String args) {
        if (args.equals(SortVariable.DATE)) {
            if (o1.getDate().getTime() > o2.getDate().getTime())
                return 1;
            else if (o1.getDate().getTime() < o2.getDate().getTime())
                return -1;
            else
                return 0;
        }
        else if (args.equals(SortVariable.ID)) {
            return o1.getId().compareTo(o2.getId());
        }
        else if (args.equals(SortVariable.NICKNAME)) {
            return o1.getNickName().compareTo(o2.getNickName());
        }
        return 0;
    }

    /**
     * 重载函数，使其能对不同对象排序
     * @param o1 对象1
     * @param o2 对象2
     * @param args 排序参数 可选 日期/号码/昵称/发言记录
     * @return 比较结果
     */
    public static int compareResult(ContentBlockBean o1, ContentBlockBean o2, String args) {
        if (args.equals(SortVariable.DATE)) {
            if (o1.getHead().getDate().getTime() > o2.getHead().getDate().getTime())
                return 1;
            else if (o1.getHead().getDate().getTime() < o2.getHead().getDate().getTime())
                return -1;
            else
                return 0;
        }
        else if (args.equals(SortVariable.ID)) {
            return o1.getHead().getId().compareTo(o2.getHead().getId());
        }
        else if (args.equals(SortVariable.NICKNAME)) {
            return o1.getHead().getNickName().compareTo(o2.getHead().getNickName());
        }
        else if (args.equals(SortVariable.STATEMENT)) {
            String s1 = "", s2 = "";
            for (String str : o1.getStatements()) {
                s1 = s1 + str;
            }
            for (String str : o2.getStatements()) {
                s2 = s2 + str;
            }
            return s1.compareTo(s2);
        }
        return 0;
    }
}
