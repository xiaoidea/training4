package com.qunar.fresh;

/**
 * 题目：
 * 1 有一个(任意)对象，里面有N个properties以及getter和setter方法
 * 2 有一个properties文件，有N个key,value来描述对象中property的值
 * 3 有一个scheme固定的xml，用来描述这个对象
 *
 * 要求写一个解析器：
 * 1 将xml中的占位符，替换为properties文件中的value
 * 2 将xml解析成对象，调用getter方法的时候可以获得值
 * 3 用面向对象的思想，使该解析器有扩展性
 *
 * 例子见附件，注意：
 * 1 对象是任意对象，不是例子中的Student，对象中的property都是java中的原生类型
 * 2 xml和properties在使用的时候都是根据对象配置好的
 * 3 xml的scheme是固定的，就是附件中的scheme
 * Created by hengyudai on 16-7-11.
 */
public class Exercise2 extends ParserImpl{
    /* 目标类名 */
    public static final String CLASS_NAME = "com.qunar.fresh.Student";
    /* 配置文件路径，放在resources目录下 */
    public static final String PROPERTY_PATH = Exercise2.class.getResource("/").getPath() + "object.properties";
    /* XML文件路径，放在resources目录下*/
    public static final String XML_PATH = Exercise2.class.getResource("/").getPath() + "object.xml";

    public static void main(String[] args) {
        Exercise2 exercise2 = new Exercise2();
        exercise2.parseXML(PROPERTY_PATH, XML_PATH);
        Student student = (Student) exercise2.generateObj(CLASS_NAME);

        System.out.println("姓名： " + student.getName());
        System.out.println("年龄： " + student.getAge());
        System.out.println("生日： " + student.getBirth());
    }
}