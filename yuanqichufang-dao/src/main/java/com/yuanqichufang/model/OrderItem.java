package com.yuanqichufang.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-7
 * Time: 下午11:32
 * To change this template use File | Settings | File Templates.
 *
 * 关系被维护端
 *
 *
 */
@Entity
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 产品ID
    @OneToOne
    @JoinColumns({
            @JoinColumn (name = "productId", referencedColumnName = "id"),
            @JoinColumn (name = "productName", referencedColumnName = "name"),
            @JoinColumn (name = "productShortChar", referencedColumnName = "shortChar")
    })
    private Product product;

    // 数量
    @Column
    private int totalCount;


    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "orderId")
    private OrderBook orderBook;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "cartId")
    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public void setOrderBook(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
