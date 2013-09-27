package com.yuanqichufang.web.controller;

import com.yuanqichufang.dao.OrderDao;
import com.yuanqichufang.dao.ProductDao;
import com.yuanqichufang.model.Cart;
import com.yuanqichufang.model.OrderItem;
import com.yuanqichufang.model.Passport;
import com.yuanqichufang.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-17
 * Time: 上午12:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping (value = "/cart/")
public class CartController {

    private static Logger logger = Logger.getLogger ("CartController");

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public ModelAndView index (HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("redirect:/passport/login");

        if (null == session.getAttribute ("yuanqichufangpassport")) {
            mav.addObject ("errorMessageNullSession", "暂未登陆，不能进行该项操作");
            return mav;
        }
        Passport passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Cart cart = orderDao.findCartByPassportId(passport.getId());
        if (cart != null)
            mav.addObject ("cart", cart);

        mav.setViewName ("order/cart");

        return mav;
    }


    /**
     * 处理结算
     * @param session
     * @return
     */
    @RequestMapping (value = "/", method = RequestMethod.POST)
    public ModelAndView indexPost (HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("redirect:/passport/login");

        if (null == session.getAttribute ("yuanqichufangpassport")) {
            mav.addObject ("errorMessageNullSession", "暂未登陆，不能进行该项操作");
            return mav;
        }
        Passport passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Cart cart = orderDao.findCartByPassportId(passport.getId());


        mav.addObject ("cart", cart);
        mav.setViewName ("redirect:/orderBook/fillorder");
        return mav;
    }

    /**
     * 删除购物车中一项商品
     * @param session
     * @param itemId
     * @return
     */
    @RequestMapping (value = "/item/remove", method = RequestMethod.GET)
    public ModelAndView removeItem (HttpSession session,
                                    @RequestParam (value = "itemId") Long itemId) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("redirect:/passport/login");

        if (null == session.getAttribute ("yuanqichufangpassport")) {
            mav.addObject ("errorMessageNullSession", "暂未登陆，不能进行该项操作");
            return mav;
        }
        Passport passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Cart cart = orderDao.removeOrderItemFromCart (itemId);

        if (cart == null) {
            mav.setViewName ("redirect:/cart/");
            mav.addObject ("errorMessage", "不存在的订单项！");
            return mav;
        }

        mav.addObject ("cart", cart);
        mav.setViewName ("redirect:/cart/");
        return mav;
    }

    /**
     * 修改购物车中的物品数量
     * @param session
     * @param itemId
     * @param addCount
     * @return
     */
    @RequestMapping (value = "/item/update", method = RequestMethod.GET)
    public ModelAndView updateCount (HttpSession session,
                                     @RequestParam (value = "itemId") Long itemId,
                                     @RequestParam (value = "count") int addCount) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("redirect:/passport/login");

        if (null == session.getAttribute ("yuanqichufangpassport")) {
            mav.addObject ("errorMessageNullSession", "暂未登陆，不能进行该项操作");
            return mav;
        }
        Passport passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Cart cart = orderDao.updateOrderitemCount(itemId, addCount);

        if (cart == null) {
            mav.setViewName ("redirect:/cart/");
            mav.addObject ("errorMessage", "不存在的订单项！");
            return mav;
        }

        mav.addObject ("cart", cart);
        mav.setViewName ("redirect:/cart/");
        return mav;
    }

    /**
     * 向购物车里添加一件新的货品
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping (value = "/item/add", method = RequestMethod.GET)
    public ModelAndView dispatch (HttpSession session,
                                  @RequestParam (value = "productId") Long productId) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("redirect:/passport/login");

        if (null == session.getAttribute ("yuanqichufangpassport")) {
            mav.addObject ("errorMessageNullSession", "暂未登陆，不能进行该项操作");
            return mav;
        }
        Passport passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Product product = productDao.find (productId);
        if (product == null) {
            mav.setViewName ("redirect:/cart/");
            mav.addObject ("errorMessage", "不存在的产品项！");
            return mav;
        }

        // 添加产品至购物车
        OrderItem item = new OrderItem();
        item.setTotalCount (1);
        item.setProduct (product);

        Cart cart = orderDao.addOrderItemToCart (passport.getId(), item);

        logger.info ("- controller save cart : " + cart.getId ());
        mav.addObject ("cart", cart);
        mav.setViewName ("redirect:/cart/");
        return mav;
    }

}
