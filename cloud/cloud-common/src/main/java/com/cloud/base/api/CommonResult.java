package com.cloud.base.api;

/**
 * 通用返回对象
 * <p>该类用于封装接口的响应结果，提供统一的返回格式，便于前后端交互。</p>
 *
 * @param <T> 返回数据的类型
 * @author DING
 */
public class CommonResult<T> {
    /**
     * 状态码
     */
    private long code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的数据内容
     */
    private T data;

    /**
     * 无参构造方法
     * <p>用于框架或工具类实例化。</p>
     */
    protected CommonResult() {
    }

    /**
     * 全参构造方法
     *
     * @param code 状态码
     * @param msg 提示信息
     * @param data 返回的数据内容
     */
    protected CommonResult(long code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param <T> 数据的类型
     * @return 封装后的成功结果
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param msg 提示信息
     * @param <T> 数据的类型
     * @return 封装后的成功结果
     */
    public static <T> CommonResult<T> success(T data, String msg) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param <T> 数据的类型
     * @return 封装后的失败结果
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMsg(), null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param msg 错误信息
     * @param <T> 数据的类型
     * @return 封装后的失败结果
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode, String msg) {
        return new CommonResult<T>(errorCode.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     *
     * @param msg 提示信息
     * @param <T> 数据的类型
     * @return 封装后的失败结果
     */
    public static <T> CommonResult<T> failed(String msg) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     *
     * @param <T> 数据的类型
     * @return 封装后的失败结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param <T> 数据的类型
     * @return 封装后的验证失败结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param msg 提示信息
     * @param <T> 数据的类型
     * @return 封装后的验证失败结果
     */
    public static <T> CommonResult<T> validateFailed(String msg) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), msg, null);
    }

    /**
     * 未登录返回结果
     *
     * @param data 返回的数据内容
     * @param <T> 数据的类型
     * @return 封装后的未登录结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg(), data);
    }

    /**
     * 未授权返回结果
     *
     * @param data 返回的数据内容
     * @param <T> 数据的类型
     * @return 封装后的未授权结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg(), data);
    }

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    public long getCode() {
        return code;
    }

    /**
     * 设置状态码
     *
     * @param code 状态码
     */
    public void setCode(long code) {
        this.code = code;
    }

    /**
     * 获取提示信息
     *
     * @return 提示信息
     */
    public String getmsg() {
        return msg;
    }

    /**
     * 设置提示信息
     *
     * @param msg 提示信息
     */
    public void setmsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取返回的数据内容
     *
     * @return 返回的数据内容
     */
    public T getData() {
        return data;
    }

    /**
     * 设置返回的数据内容
     *
     * @param data 返回的数据内容
     */
    public void setData(T data) {
        this.data = data;
    }
}