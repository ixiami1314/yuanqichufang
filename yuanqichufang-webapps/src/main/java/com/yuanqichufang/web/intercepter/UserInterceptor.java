package com.yuanqichufang.web.intercepter;

import com.yuanqichufang.model.Passport;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;


/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-12
 * Time: 下午10:58
 * To change this template use File | Settings | File Templates.
 */
public class UserInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = Logger.getLogger ("UserInterceptor");

    /**
     * 在处理之前，注入需要的用户对象
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info ("- user interceptor for object" + handler.getClass());

        //if (IUserAware.class.isAssignableFrom (handler.getClass())) {

        if (handler.getClass().isAssignableFrom (IUserAware.class)) {
            Passport passport = (Passport) request.getSession().getAttribute ("yuanqichufangpassport");
            logger.info ("- process interceptor on " + passport.getPassport ());
            IUserAware userAware = (IUserAware) handler;
            userAware.setUser(passport);
        }
        return super.preHandle(request, response, handler);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
