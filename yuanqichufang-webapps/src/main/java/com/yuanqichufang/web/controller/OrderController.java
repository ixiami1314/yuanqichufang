package com.yuanqichufang.web.controller;

import com.yuanqichufang.dao.OrderDao;
import com.yuanqichufang.dao.ProductDao;
import com.yuanqichufang.model.Cart;
import com.yuanqichufang.model.OrderBook;
import com.yuanqichufang.model.Passport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-4
 * Time: 下午11:28
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping (value = "/orderBook/")
public class OrderController {

    private static Logger logger = Logger.getLogger ("OrderController");

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

        mav.setViewName("order/myOrders");
        return mav;
    }


    @RequestMapping (value = "myorders", method = RequestMethod.GET)
    public ModelAndView myOrders (HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("redirect:/passport/login");

        if (null == session.getAttribute ("yuanqichufangpassport")) {
            mav.addObject ("errorMessageNullSession", "暂未登陆，不能进行该项操作");
            return mav;
        }
        Passport passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        //List <OrderBook> orders = orderDao.getOrdersByPassportId (globalPassportObject.getId ());
        List <OrderBook> orders = orderDao.findOrderByPassportId (passport.getId());

        mav.addObject ("orders", orders);

        // 获取我的订单
        mav.setViewName ("order/myOrders");
        return mav;
    }

    /**
     * 购物车添加
     * @return
     */
    @RequestMapping (value = "fillorder", method = RequestMethod.GET)
    public ModelAndView fillorderGet (HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("redirect:/passport/login");

        if (null == session.getAttribute ("yuanqichufangpassport")) {
            mav.addObject ("errorMessageNullSession", "暂未登陆，不能进行该项操作");
            return mav;
        }
        Passport passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Cart cart = orderDao.findCartByPassportId (passport.getId());
        if (cart == null) {
            mav.setViewName ("redirect:/product/");
            return mav;
        }

        mav.addObject ("cart", cart);

        mav.setViewName ("order/fillOrders");
        return mav;
    }

    /**
     * 通过传入的orderItems来创建订单
     * orderItems=12,34,111,35
     * 以,隔开各个订单细目项
     *
     * cargoDateArea 1/2/3
     * @param
     * @return
     */
    @RequestMapping (value = "fillorder", method = RequestMethod.POST)
    public ModelAndView fillorderPost (@RequestParam (value = "cargoName", required = false) String cargoName,
                                       @RequestParam (value = "cargoPhone", required = false) String cargoPhone,
                                       @RequestParam (value = "cargoAddress", required = false) String cargoAddress,
                                       @RequestParam (value = "cargoDate", required = false) Date cargoDate,
                                       @RequestParam (value = "cargoDateArea", required = false) int cargoDateArea,
                                       HttpSession session) {

        logger.info ("- fill orderBook POST ");
        logger.info ("- cargoName " + cargoName);
        logger.info ("- cargoPhone " + cargoPhone);
        logger.info ("- cargoAddress " + cargoAddress);
        logger.info ("- cargoDate " + cargoDate);
        logger.info ("- cargoDateArea " + cargoDateArea);


        ModelAndView mav = new ModelAndView();
        mav.setViewName ("redirect:/passport/login");

        if (null == session.getAttribute ("yuanqichufangpassport")) {
            mav.addObject ("errorMessageNullSession", "暂未登陆，不能进行该项操作");
            return mav;
        }
        Passport passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Cart cart = orderDao.findCartByPassportId (passport.getId());

        // 开发把购物车的东西结算
        OrderBook orderBook = new OrderBook();

        // 填写订单
        orderBook.setPassportId (passport.getId ());
        orderBook.setStatus(1);
        orderBook.setCargoName(cargoName);
        orderBook.setCargoPhone(cargoPhone);
        orderBook.setCargoAddress(cargoAddress);
        // 处理日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        orderBook.setCargoDate (cargoDate);
        orderBook.setCargoDateArea (cargoDateArea);
        orderBook.setStatus (1); // 当前订单状态为未支付


        orderBook = orderDao.buyOrder (orderBook);

        mav.addObject ("orderBook", orderBook);
        mav.setViewName ("order/completeOrders");
        return mav;
    }
}
