package edu.scau.misp.todo.util;

/**
 * 业务异常处理
 *
 */
public class BusinessException extends RuntimeException {

    private int code=ResponseConstant.code.failure; //返回码(400)

    private String msg; //返回码对应的消息


    public BusinessException(Exception e){
        super(e);
    }

    public BusinessException(Exception e,String msg){
        super(e);
        this.msg = msg;
    }

    public BusinessException(String msg){
        super();
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
