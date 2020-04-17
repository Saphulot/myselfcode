package com.saphulot.rpc.common;

import lombok.Data;

import java.io.Serializable;
@Data
public class RpcResponse<T> implements Serializable {
    private static final long serializableUID = 1L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应错误消息体
     */
    private String message;


    /**
     * 响应数据
     */
    private T data;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功响应
     *
     * @param data 数据
     * @param <T>  数据泛型
     *
     * @return RpcResponse
     */
    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(ResponseCode.SUCCESS.getValue());
        if (null != data) {
            response.setData(data);
        }
        return response;
    }

    /**
     * 失败响应
     * @param responseCode 响应码枚举
     * @param errorMessage 错误消息
     * @param <T> 泛型
     *
     * @return RpcResponse
     */
    public static <T> RpcResponse<T> fail(ResponseCode responseCode, String errorMessage) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(responseCode.getValue());
        response.setMessage(errorMessage);
        return response;
    }
}
