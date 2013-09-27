package com.yuanqichufang.web.controller;

import com.yuanqichufang.dao.PassportDao;
import com.yuanqichufang.model.Passport;
import com.yuanqichufang.service.ISMSService;
import com.yuanqichufang.web.intercepter.IUserAware;
import com.yuanqichufang.web.json.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-8-23
 * Time: 下午6:30
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping ("/passport/")
public class PassportController {
    private static Logger logger = Logger.getLogger ("PassportController");

    private static final String SESSION_KEY_PHONE_VERIFY = "session_key_phone_verify";
    private static final String MD5_KEY = "yuanqichufang";
    private static final Md5PasswordEncoder MD5 = new Md5PasswordEncoder();

    @Autowired
    private PassportDao passportDao;

    @Autowired
    private ISMSService smsService;


    public PassportController() {
    }

    public boolean isEmailAddress (String email) {
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile (check);
            Matcher matcher = regex.matcher (email);
            flag = matcher.matches ();
        }catch(Exception e){
            logger.info ("- 验证邮箱地址错误");
            flag = false;
        }
        return flag;
    }

    /** Passport API part */
    @RequestMapping (value = "getPhoneVerifyByJSON", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getPhoneVerifyByJSON (@RequestParam (value = "phone") String phone, HttpSession session) {
        ResponseObject response = new ResponseObject();
        String v_code = "123456";
        // smsService.sendSMS (phone, message, "", "");
        session.setAttribute (SESSION_KEY_PHONE_VERIFY, v_code);
        response.setCode ("0");
        response.setResult ("success");
        response.setMessage ("验证码发送成功");
        logger.info ("- passport/registe api for phone verify code is " + v_code);
        return response;
    }

    /** Passport MVC part : register */
    @RequestMapping (value = "register", method = RequestMethod.GET)
    public ModelAndView register (HttpSession session) {
        ModelAndView mav = new ModelAndView();

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passport);

        mav.setViewName ("passport/register");
        return mav;
    }

    @RequestMapping (value = "register", method = RequestMethod.POST)
    public ModelAndView register (@RequestParam (value = "kaptchafield") String kaptchafield,
                           @RequestParam (value = "passport") String passport,
                           @RequestParam (value = "password") String password,
                           @RequestParam (value = "repassword") String password1, HttpSession session) {

        String code = (String) session.getAttribute (com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("passport/register");

        if (! isEmailAddress (passport)) {
            mav.addObject ("errorMessage_email", "请您输入邮箱地址");
            return mav;
        }

        if (! password.equals (password1)) {
            mav.addObject ("errorMessage_password", "您输入的两次密码不一致");
            return mav;
        }

        if (password.length () < 6 || password.length () > 25) {
            mav.addObject ("errorMessage_password_length", "请输入6-25位长密码");
            return mav;
        }

        if (! code.equals (kaptchafield)) {
            mav.addObject ("errorMessage_verifycode", "验证码不对");
            return mav;
        }

        Passport passportObject1 = null;
        if (null != session.getAttribute ("yuanqichufangpassport"))
            passportObject1 = (Passport) session.getAttribute ("yuanqichufangpassport");
        mav.addObject ("login_passport", passportObject1);


        Passport passportObject = new Passport ();
        passportObject.setPassport (passport);
        // 密码加密存储
        passportObject.setPassword (MD5.encodePassword (MD5_KEY, password));
        passportDao.save (passportObject);
        mav.addObject ("passportObj", passportObject);
        mav.setViewName("passport/registerSuccess");
        logger.info ("- registe passport on " + passportObject.getPassport ());

        return mav;
    }

    /** Passport MVC part : login */
    @RequestMapping (value = "login", method = RequestMethod.GET)
    public ModelAndView login (HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("passport/login");

        Passport passport = null;
        if (null != session.getAttribute ("yuanqichufangpassport")) {
            // 已经登陆过，直接打回首页
            passport = (Passport) session.getAttribute ("yuanqichufangpassport");
            mav.addObject ("login_passport", passport);

            mav.setViewName ("redirect:/");
            return mav;
        }

        return mav;
    }

    @RequestMapping (value = "login", method = RequestMethod.POST)
    public ModelAndView login (@RequestParam (value = "passport") String passport,
                         @RequestParam (value = "password") String password, HttpSession session) {

        ModelAndView mav = new ModelAndView();
        Passport passportObject = passportDao.findPassportByName (passport);
        mav.setViewName ("passport/login");

        if (passportObject ==  null) {
            mav.addObject ("errorMessageNullObject", "不存在该用户名");
            return mav;
        }

        if (! MD5.isPasswordValid (passportObject.getPassword(), MD5_KEY, password)) {
            mav.addObject ("errorMessagePasswordError", "错误的用户名密码");
            return mav;
        }

        logger.info ("- login verify db's password " + passportObject.getPassword ());
        //处理session
        /**
        Passport passportObjectSession = new Passport ();
        passportObjectSession.setId (passportObject.getId ());
        passportObjectSession.setPassword (passportObject.getPassword());
        String passportName = passportObject.getPassport (); */

        session.setAttribute ("yuanqichufangpassport", passportObject);
        mav.addObject ("login_passport", passportObject);


        mav.setViewName ("redirect:/");
        return mav;
    }

    @RequestMapping (value = "logout", method = RequestMethod.GET)
    public String logout (HttpSession session) {
        session.setAttribute ("yuanqichufangpassport", null);
        return "redirect:/";
    }
}
