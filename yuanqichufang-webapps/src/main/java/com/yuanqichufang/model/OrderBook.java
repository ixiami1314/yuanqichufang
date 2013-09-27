package com.yuanqichufang.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-4
 * Time: 下午11:24
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class OrderBook implements Serializable {

    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 下单用户ID
    @Column
    private Long passportId;

    // 货主名称
    @Column
    private String cargoName;

    // 货主电话
    @Column
    private String cargoPhone;

    // 货主地址
    @Column
    private String cargoAddress;

    // 送货日期
    @Column
    private Date cargoDate;

    // 预计时间段
    // 1: 10-11
    // 2: 13-14
    // 3: 15-16
    @Column
    private int cargoDateArea;

    /**
     * 非常重要字段
     * 用于标识当前订单的流程号
     * 1 创建，未处理
     * 2 配货中
     * 3 送货中
     * 4 签收
     * 10 退单
     */
    @Column
    private int status;



    @OneToMany (cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "orderBook")
    private Set<OrderItem> items = new HashSet<OrderItem>();


    /**
     * 添加订单项
     * @param item
     */
    public void addOrderItem (OrderItem item) {
        item.setOrderBook (this);
        items.add (item);
    }

    /**
     * 算出订单总价
     * @return
     */
    public double totalPrice () {
        double total = 0;
        for (OrderItem item : items) {
            total = total + item.getProduct().getPrice() * item.getTotalCount();
        }
        return total;
    }

    /**
     * 删除订单项
     * @param item
     */
    public void removeOrderItem (OrderItem item) {
        if (item.getId() != null) {
            item.setOrderBook (null);
            items.remove (item);
        }
    }


    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPassportId() {
        return passportId;
    }

    public void setPassportId(Long passportId) {
        this.passportId = passportId;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getCargoPhone() {
        return cargoPhone;
    }

    public void setCargoPhone(String cargoPhone) {
        this.cargoPhone = cargoPhone;
    }

    public String getCargoAddress() {
        return cargoAddress;
    }

    public void setCargoAddress(String cargoAddress) {
        this.cargoAddress = cargoAddress;
    }

    public Date getCargoDate() {
        return cargoDate;
    }

    public void setCargoDate(Date cargoDate) {
        this.cargoDate = cargoDate;
    }

    public int getCargoDateArea() {
        return cargoDateArea;
    }

    public void setCargoDateArea(int cargoDateArea) {
        this.cargoDateArea = cargoDateArea;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
