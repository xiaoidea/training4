package com.qunar.fresh;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: liuzz
 * Date: 13-11-2
 * Time: 下午11:53
 *
 */
public class Student {
    private String name;

    private int age;

    private Date birth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
