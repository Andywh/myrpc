package com.joy.rpc.common;

import lombok.Data;

/**
 * Created by Ai Lun on 2020-08-17.
 */
public class RpcResponse {

    private String requestId;

    private Exception exception;

    private Object result;

    public boolean hasException() {
        return exception != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
