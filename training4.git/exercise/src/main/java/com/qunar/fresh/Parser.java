package com.qunar.fresh;

/**解析器接口，可用于扩展不同的类
 * Created by hengyudai on 16-7-11.
 */
public interface Parser {
    /**
     * 使用DOM4J解析XML，完成两个功能：
     * 1. 从propertyPath中读取配置文件，取得各个字段的值
     * 2. 写XML，将读取到的字段值写入XML
     * @param propertyPath properties文件路径
     * @param xmlPath xml文件路径
     */
    void parseXML(String propertyPath, String xmlPath);

    /**
     * 使用JAVA反射机制，从一个类的类名获取一个类对象
     * 该对象的字段值由配置文件提供，并需要从XML文件读取出来
     * @param className 类名
     * @return 该类的一个对象
     */
    Object generateObj(String className);

}
