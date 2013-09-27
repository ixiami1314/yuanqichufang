package com.yuanqichufang.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-8-28
 * Time: 下午10:56
 * To change this template use File | Settings | File Templates.
 */
public class SMSServiceImp1 implements ISMSService {

    private static final Logger logger = LoggerFactory.getLogger(SMSServiceImp1.class);

    private String accountId;
    private String accountKey;

    @Override
    public String sendSMS (String message, String target, String ext, String send_time) {
        StringBuffer sb = null;
        try {
            logger.debug ("- send SMS to "+ target +" on context :" + message);
            //sb = SmsClient.sendsms (accountId, accountKey, message, target, "", null);
        } catch (Exception ex) {
            ex.printStackTrace ();
        }
        return sb.toString ();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }
}
