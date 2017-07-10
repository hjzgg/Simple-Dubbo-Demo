package com.hjzgg.simulation.common.response;

/**
 * Created by hujunzheng on 2017/7/9.
 */
public class SimpleResponse {
    private SimpleResponse() {

    }

    private static SimpleResponse getInstance() {
        return new SimpleResponse();
    }

    public static SimpleResponse success(Object data) {
        SimpleResponse instance = getInstance();
        instance.setCode(ReturnCode.SUCCESS);
        instance.setMsg("成功");
        instance.setData(data);
        return instance;
    }

    public static SimpleResponse error(String msg) {
        SimpleResponse instance = getInstance();
        instance.setCode(ReturnCode.FAILURE);
        instance.setMsg(msg);
        return instance;
    }

    private String msg;
    private Object data;
    private ReturnCode code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ReturnCode getCode() {
        return code;
    }

    public void setCode(ReturnCode code) {
        this.code = code;
    }
}
