package com.yuanqichufang.web.controller;

import com.yuanqichufang.dao.ProductDao;
import com.yuanqichufang.model.Passport;
import com.yuanqichufang.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-7
 * Time: 下午7:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping (value = "/product/")
public class ProductController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ProductDao productDao;

    @RequestMapping (value = "/index", method = RequestMethod.GET)
    public ModelAndView index () {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("product/index");

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        List <Product> result = productDao.findProducts();
        mav.addObject ("products", result);

        return mav;
    }

    @RequestMapping (value = "/a", method = RequestMethod.GET)
    public ModelAndView a () {
        ModelAndView mav = new ModelAndView();

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Product product = productDao.findProductByShortChar ("a");
        if (product != null)
            mav.addObject ("product", product);

        mav.setViewName ("product/a");
        return mav;
    }

    @RequestMapping (value = "/j", method = RequestMethod.GET)
    public ModelAndView j () {
        ModelAndView mav = new ModelAndView();

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Product product = productDao.findProductByShortChar ("j");
        if (product != null)
            mav.addObject ("product", product);

        mav.setViewName ("product/j");
        return mav;
    }

    @RequestMapping (value = "/m", method = RequestMethod.GET)
    public ModelAndView m () {
        ModelAndView mav = new ModelAndView();

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Product product = productDao.findProductByShortChar ("m");
        if (product != null)
            mav.addObject ("product", product);

        mav.setViewName ("product/m");
        return mav;
    }

    @RequestMapping (value = "/q", method = RequestMethod.GET)
    public ModelAndView q () {
        ModelAndView mav = new ModelAndView();

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Product product = productDao.findProductByShortChar ("q");
        if (product != null)
            mav.addObject ("product", product);

        mav.setViewName ("product/q");
        return mav;
    }

    @RequestMapping (value = "/x", method = RequestMethod.GET)
    public ModelAndView x () {
        ModelAndView mav = new ModelAndView();

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        Product product = productDao.findProductByShortChar ("x");
        if (product != null)
            mav.addObject ("product", product);

        mav.setViewName ("product/x");
        return mav;
    }

}
