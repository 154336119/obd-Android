package com.slb.frame.http2.retrofit;


/**
 * 描述：Entity - 网络请求统一返回格式
 * Created by Lee
 * on 2016/10/13.
 */
public class HttpResult<T,A> {

    /**
     * code : 0
     * msg : 成功
     */

    private int code;
    private String msg;

    public HttpDataResutl<T, A> getData() {
        return data;
    }

    public void setData(HttpDataResutl<T, A> data) {
        this.data = data;
    }

    private HttpDataResutl<T,A> data;
    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
