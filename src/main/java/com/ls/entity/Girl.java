package com.ls.entity;

import javax.validation.constraints.Min;
/**
 * Created by keke on 2017/10/18.
 * 创建一个与数据库表对应的实体类
 */
public class Girl {

    private Integer id;
    private String cupSize;

    @Min(value = 18, message = "未成年禁止入内！")
    private int age;

    public Girl(){}
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCupSize() {
        return cupSize;
    }
    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Girl{" +
                "id=" + id +
                ", cupSize='" + cupSize + '\'' +
                ", age=" + age +
                '}';
    }
}
