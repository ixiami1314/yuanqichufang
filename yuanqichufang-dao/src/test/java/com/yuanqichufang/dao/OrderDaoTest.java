package com.yuanqichufang.dao;

import com.yuanqichufang.model.Cart;
import com.yuanqichufang.model.OrderItem;
import com.yuanqichufang.model.Passport;
import com.yuanqichufang.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-21
 * Time: 下午8:51
 * To change this template use File | Settings | File Templates.
 */

@ContextConfiguration ("/test-context.xml")
@RunWith (SpringJUnit4ClassRunner.class)
@Transactional
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PassportDao passportDao;

    @Before
    public void prepareData () {
        Product product = new Product ();
        product.setName ("产品A");
        product.setPrice (12);
        product.setShortChar ("m");
        product.setDescription ("一些简介");
        productDao.save (product);

        Passport passport = new Passport();
        passport.setPassport ("KILLERFBI");
        passport.setPassword ("123455");
        passportDao.save (passport);
    }

    @Test
    public void addOrderItemTest () {

        OrderItem item = new OrderItem();
        item.setProduct (productDao.find (1L));
        item.setTotalCount (2);

        // 存进第1个人的车子
        Cart cart = orderDao.addOrderItemToCart (1L, item);
        System.out.println (cart);
    }
}
