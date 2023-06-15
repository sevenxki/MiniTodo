package edu.scau.misp.todo.util;

/**
 * 响应常量
 */
public interface ResponseConstant {

    interface key {
        String code = "code";
        String message = "message";
        String data = "data";
    }

    /**
     * 响应码
     */
    interface code {
        //成功
        Integer success = 200;
        //失败
        int failure = 400;
        //错误
        int error = 500;
        //登录状态异常
        int loginStatusError = 999;
    }
}
