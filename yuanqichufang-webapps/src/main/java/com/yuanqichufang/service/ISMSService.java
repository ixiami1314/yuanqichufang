package com.yuanqichufang.service;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-8-28
 * Time: 下午10:52
 * To change this template use File | Settings | File Templates.
 */
public interface ISMSService {

    /**
     *
     * username	string	用户名	是
     * password	string	密码	是	 	密码为MD5加密后字符串
     * message	string	消息内容	是	 	中文编码采用UTF-8
     * target	string	手机号	是	 	通过英文分号隔开多个电话号码
     * ext	string	扩展码	否	 	目前不支持扩展码
     * send_time	string	发送时间	否	 	格式为yyyy-mm-dd 或 yyyy-mm-dd hh:mm:ss
     *
     * @param accountId
     * @param accountKey
     * @param message
     * @param target
     * @param ext
     * @param send_time
     * @return
     */
    public String sendSMS(String message, String target, String ext, String send_time);
}
