package com.yuanqichufang.web.controller;

import com.yuanqichufang.model.Passport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-24
 * Time: 下午10:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping (value = "/attation/")
public class AttationController {

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public ModelAndView index (HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("attation");

        Passport passport = null;
        if (session.getAttribute ("yuanqichufangpassport") != null)
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        return mav;
    }
}
