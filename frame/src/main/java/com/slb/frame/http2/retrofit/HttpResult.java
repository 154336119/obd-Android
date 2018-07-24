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

    private int status;
    private String message;
    private HttpDataResutl<T,A> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpDataResutl<T, A> getResult() {
        return result;
    }

    public void setResult(HttpDataResutl<T, A> result) {
        this.result = result;
    }
}
