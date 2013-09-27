package com.yuanqichufang.web.intercepter;


import com.yuanqichufang.model.Passport;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-12
 * Time: 下午11:01
 * To change this template use File | Settings | File Templates.
 */
public interface IUserAware {
    public void setUser (Passport passport);
}
