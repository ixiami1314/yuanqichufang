package com.yuanqichufang.web.json;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-1
 * Time: 下午10:29
 * To change this template use File | Settings | File Templates.
 */
public class ResponseObject {
    private String code;
    private String result;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
