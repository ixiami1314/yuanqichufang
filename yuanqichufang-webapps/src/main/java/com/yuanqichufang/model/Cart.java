package com.yuanqichufang.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-16
 * Time: 下午11:52
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long passportId;

    @OneToMany (cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "cart")
    Set <OrderItem> items = new HashSet<OrderItem>();

    public double totalPrice () {
        double result = 0;
        for (OrderItem item : items) {
            result = result + item.getProduct().getPrice() * item.getTotalCount();
        }
        return result;
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

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString () {
        StringBuffer sb = new StringBuffer();
        sb.append ("- cart id is : ").append (this.getId()).append ("\n")
                .append ("- cart's person is : ").append (this.getPassportId()).append ("\n")
                .append ("- cart's item lengh is : ").append (this.getItems().size()).append ("\n");
        return sb.toString();
    }
}
