package com.cloud.base.api;

/**
 * 封装API的错误码接口
 * <p>该接口定义了获取错误码和错误信息的方法，用于统一管理和封装API的错误码。</p>
 *
 * <p>实现该接口的类需要提供具体的错误码和对应的错误信息。</p>
 *
 * <p>使用场景包括：在API响应中返回标准化的错误信息，便于前后端统一处理。</p>
 *
 * @author DING
 */
public interface IErrorCode {
    /**
     * 获取错误码
     *
     * @return 错误码，类型为long
     */
    long getCode();

    /**
     * 获取错误信息
     *
     * @return 错误信息，类型为String
     */
    String getMsg();
}