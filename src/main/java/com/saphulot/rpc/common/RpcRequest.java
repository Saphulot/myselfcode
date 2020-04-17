package com.saphulot.rpc.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

@Data
public class RpcRequest implements Serializable {
    private static final long serializableUID = 0L;

    /**
     * 请求接口名称
     */
    private String className;

    /**
     * 请求方法名称
     */
    private String methodName;

    /**
     * 参数列表
     */
    private Object[] params;

    /**
     * 版本号
     */
    private String version;

    public static long getSerializableUID() {
        return serializableUID;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                ", version='" + version + '\'' +
                '}';
    }
}
