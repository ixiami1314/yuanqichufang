package com.yuanqichufang.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-7
 * Time: 下午11:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Product implements Serializable {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 产品名称
    @Column
    private String name;

    // 产品描述
    @Column
    private String description;

    // 价格
    @Column
    private double price;

    // 配方
    @Column
    private String list;

    // 产品图片
    @Column
    private String picUrl;

    // 简称
    @Column
    private String shortChar;

    public Long getId() {
        return id;
    }

    public void setId(Long ids) {
        id = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getShortChar() {
        return shortChar;
    }

    public void setShortChar(String shortChar) {
        this.shortChar = shortChar;
    }
}
