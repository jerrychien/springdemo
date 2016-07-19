package com.jerry.model;

/**
 * 结果对象
 *
 * @author jerrychien
 * @create 2016-07-13 15:36
 */
public class ResponseResult<T> extends BaseObject {

    private static final long serialVersionUID = 2768383983700875569L;

    public int code;

    public String message;

    public T result;

    public ResponseResult() {
    }

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
