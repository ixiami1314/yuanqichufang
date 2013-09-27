package com.yuanqichufang.dao;

import com.yuanqichufang.model.Cart;
import com.yuanqichufang.model.OrderBook;
import com.yuanqichufang.model.OrderItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-21
 * Time: 下午7:34
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    private String query_find_order_by_passportid = "SELECT o FROM OrderBook o WHERE o.passportId = :passportId";
    private String query_find_cart_by_passportid = "SELECT c FROM Cart c WHERE c.passportId = :passportId";

    /**
     * 通过订单ID找
     * @param id
     * @return
     */
    public OrderBook findOrder (Long id) {
        return this.entityManager.find (OrderBook.class, id);
    }

    /**
     * 查询某人的订单列表
     * @param passportId
     * @return
     */
    public List <OrderBook> findOrderByPassportId (Long passportId) {
        Query query = this.entityManager.createQuery (query_find_order_by_passportid);
        query.setParameter ("passportId", passportId);
        return query.getResultList ();
    }

    /**
     * 通过购物车ID查找
     * @param cartId
     * @return
     */
    public Cart findCart (Long cartId) {
        return this.entityManager.find (Cart.class, cartId);
    }

    /**
     * 查找某人的购物车信息
     * 一个人只能有一个购物车
     * @param passportId
     * @return
     */
    public Cart findCartByPassportId (Long passportId) {
        Cart cart = null;
        Query query = this.entityManager.createQuery (query_find_cart_by_passportid);
        query.setParameter ("passportId", passportId);
        List <Cart> carts = query.getResultList();
        if (carts.size() > 0)
            cart = carts.get (0);
        return cart;
    }

    /**
     * 给某人的购物车里添加一样商品
     * @param item
     * @return
     */
    @Transactional
    public Cart addOrderItemToCart (Long passportId, OrderItem item) {
        Cart cart = findCartByPassportId (passportId);

        if (cart == null) {
            // 如果该人没有购物车，则生成个
            this.entityManager.persist (item);
            cart = item.getCart ();
            cart.setPassportId (passportId);
        } else {
            item.setCart (cart);
        }
        cart = this.entityManager.merge (cart);

        return cart;
    }

    /**
     * 通过订单ID删除购物车选项
     * @param orderItemId
     * @return
     */
    @Transactional
    public Cart removeOrderItemFromCart (Long orderItemId) {
        OrderItem item = this.entityManager.find (OrderItem.class, orderItemId);
        if (item == null) return null;

        // 如果在订单当中，就不允许删除
        if (item.getOrderBook() != null) return null;

        this.entityManager.remove (item);
        return item.getCart();
    }

    /**
     * 更新订单数量
     * @param orderItemId
     * @param addCount
     * @return
     */
    @Transactional
    public Cart updateOrderitemCount (Long orderItemId, int addCount) {
        OrderItem item = this.entityManager.find (OrderItem.class, orderItemId);
        if (item == null) return null;

        item.setTotalCount (item.getTotalCount() + addCount);
        this.entityManager.merge (item);
        return item.getCart();
    }
}
