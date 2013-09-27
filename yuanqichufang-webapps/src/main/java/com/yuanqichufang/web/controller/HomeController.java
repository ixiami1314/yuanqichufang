package com.yuanqichufang.web.controller;

import com.yuanqichufang.model.Passport;
import com.yuanqichufang.web.intercepter.IUserAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
@RequestMapping (value = "/")
public class HomeController  {

    private static Logger logger = Logger.getLogger ("UserInterceptor");

	/**
     * Selects the home page and populates the model with a message
     */
    @RequestMapping (value = "/", method = RequestMethod.GET)
    public ModelAndView home (HttpSession session) {
        logger.info("- controller:/");
        ModelAndView mav = new ModelAndView();

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);


        mav.setViewName ("index");
        mav.addObject ("controllerMessage", "controllerMessage , This is the message from the controller!");
        return mav;
    }

    @RequestMapping (value = "/index", method = RequestMethod.GET)
    public ModelAndView homeIndex (HttpSession session) {
        logger.info("- controller:/");
        ModelAndView mav = new ModelAndView();

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        mav.setViewName ("index");
        mav.addObject ("controllerMessage", "controllerMessage , This is the message from the controller!");
        return mav;
    }

    /**
     * Selects the home page and populates the model with a message
     */
    @RequestMapping (value = "/about", method = RequestMethod.GET)
    public ModelAndView about (HttpSession session) {
        logger.info("- controller:/about");
        ModelAndView mav = new ModelAndView();

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        mav.setViewName ("about");
        return mav;
    }

    @RequestMapping (value = "/attention", method = RequestMethod.GET)
    public ModelAndView index (HttpSession session) {
        logger.info("- controller:/attention");
        ModelAndView mav = new ModelAndView();

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        mav.setViewName ("attention");
        return mav;
    }
}
