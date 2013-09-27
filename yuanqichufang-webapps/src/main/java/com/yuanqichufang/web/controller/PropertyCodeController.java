package com.yuanqichufang.web.controller;

import com.yuanqichufang.dao.PassportDao;
import com.yuanqichufang.dao.PropertyCodeDao;
import com.yuanqichufang.model.Passport;
import com.yuanqichufang.model.PropertyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-22
 * Time: 下午9:50
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping (value = "/code/")
public class PropertyCodeController {

    @Autowired
    private PropertyCodeDao propertyCodeDao;

    @Autowired
    private PassportDao passportDao;

    /**
     * 首页
     * @return
     */
    @RequestMapping (value = "/", method = RequestMethod.GET)
    public ModelAndView index (HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("redirect:/passport/login");

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        if (passport == null)
            return mav;

        mav.setViewName ("code/index");
        return mav;
    }

    @RequestMapping (value = "asdfa7sd65f67a5sd67f456a4sdf", method = RequestMethod.GET)
    public ModelAndView buildCode (@RequestParam (value = "count") int count) {
        propertyCodeDao.generatePropertyCode (count);
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("code/success");
        return mav;
    }

    /**
     * 执行兑换
     * @param code
     * @param session
     * @return
     */
    @RequestMapping (value = "/", method = RequestMethod.POST)
    public ModelAndView processCode (@RequestParam (value = "code") String code,
                                     HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("redirect:/passport/login");

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        if (passport == null)
            return mav;

        PropertyCode pCode = propertyCodeDao.findByCode (code);
        Passport p =  passportDao.find (passport.getId ());
        if (pCode == null) {
            mav.setViewName ("redirect:/code/");
            mav.addObject ("errorMessage", "验证码不存在！");
            return mav;
        }

        propertyCodeDao.tobeUse (pCode, p);
        // 存在
        mav.addObject ("propertyCode", pCode);
        mav.setViewName ("code/success");
        return mav;
    }
}
