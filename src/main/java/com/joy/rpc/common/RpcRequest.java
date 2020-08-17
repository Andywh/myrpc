package com.joy.rpc.common;

import lombok.Data;

/**
 * Created by Ai Lun on 2020-08-17.
 */
@Data
public class RpcRequest {

    private String requestId;

    private String interfaceName;

    private String serviceVersion;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;

}
