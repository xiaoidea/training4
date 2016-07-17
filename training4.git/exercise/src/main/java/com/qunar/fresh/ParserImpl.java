package com.qunar.fresh;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现Parser接口
 * Created by hengyudai on 16-7-11.
 */
public class ParserImpl implements Parser{

    /* 使用map存储读取到的properties内字段值 */
    Map<String, String> propertyVal = new HashMap<String, String>();

    /* 将基础类型的class映射为对应封装类，用于反射时处理基础类型class没有valueOf方法问题 */
    public final static Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();

    static {
        map.put(boolean.class, Boolean.class);
        map.put(byte.class, Byte.class);
        map.put(short.class, Short.class);
        map.put(char.class, Character.class);
        map.put(int.class, Integer.class);
        map.put(long.class, Long.class);
        map.put(float.class, Float.class);
        map.put(double.class, Double.class);
        map.put(java.util.Date.class, java.sql.Date.class);
    }

    /**
     * 使用JAVA反射机制，从一个类的类名获取一个类对象
     * 该对象的字段需要使用配置文件中对应的字段值
     * @param className 类名
     * @return 类对象
     */
    public Object generateObj(String className) {
        try {
            Class cls = Class.forName(className);
            /* 新建一个该类的对象 */
            Object obj = cls.newInstance();
            /* 获取类的所有字段 */
            Field[] fields = cls.getDeclaredFields();

            /*
             *逐个遍历field，给每个field赋予配置文件中的值
             */
            for (Field field : fields) {
                field.setAccessible(true);
                Class fieldClass = field.getType();
                String value = propertyVal.get(field.getName());

                if (fieldClass == String.class) {
                    field.set(obj, value);
                }
                else {
                    if (map.containsKey(fieldClass))
                        fieldClass = map.get(fieldClass);
                    /* 使用基础类型的valueOf方法，将字符串转换为字段类型 */
                    Method method = fieldClass.getMethod("valueOf", String.class);

                    /* 使用set方法，并调用Method.invoke，将对象字段设置为从配置文件获得的value */
                    field.set(obj, method.invoke(fieldClass, value));
                }
            }
            return obj;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析XML文件，从propertyPath中读取配置文件，取得各个字段的值
     * 并将读取到的字段值写入XML
     * @param propertyPath properties文件路径
     * @param xmlPath xml文件路径
     */
    public void parseXML(String propertyPath, String xmlPath) {
        /* 调用readProperty方法，读取properties文件 */
        readProperty(propertyPath);
        try {
            Document doc = new SAXReader().read(new File(xmlPath));
            Element root = doc.getRootElement();
            List<Element> children = root.elements();

            /* 设置各个property的值 */
            for (Element child : children) {
                String propName = child.attribute("name").getValue();
                String value = propertyVal.get(propName);
                child.element("value").setText(value);
            }

            /* 将结果写回XML */
            XMLWriter writer = new XMLWriter(new FileWriter(new File(xmlPath)));
            writer.write(doc);
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置文件的字段值，这里采用逐行读取的方式而不是使用属性名获取属性值的方式，增加可扩展性
     * 将结果写入map中
     * @param propertyPath 配置文件路径
     *
     */
    public void readProperty(String propertyPath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(propertyPath));
            String line;
            while ((line = reader.readLine()) != null) {
                /* 将使用等号分割的属性名和属性值分别取出 */
                int split = line.indexOf("=");
                String name = line.substring(0, split);
                String value = line.substring(split + 1);
                propertyVal.put(name, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
