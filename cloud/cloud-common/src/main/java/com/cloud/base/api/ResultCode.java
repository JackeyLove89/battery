package com.cloud.base.api;

/**
 * 枚举了一些常用API操作码
 * <p>该枚举类实现了 {@link IErrorCode} 接口，用于定义标准化的API操作码和对应的提示信息。</p>
 *
 * <p>使用场景包括：在API响应中返回统一的状态码和提示信息，便于前后端交互。</p>
 *
 * @author DING
 */
public enum ResultCode implements IErrorCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 操作失败
     */
    FAILED(500, "操作失败"),

    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(404, "参数检验失败"),

    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),

    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限");

    /**
     * 操作码
     */
    private long code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 构造方法
     *
     * @param code 操作码
     * @param msg 提示信息
     */
    private ResultCode(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取操作码
     *
     * @return 操作码
     */
    public long getCode() {
        return code;
    }

    /**
     * 获取提示信息
     *
     * @return 提示信息
     */
    public String getMsg() {
        return msg;
    }
}