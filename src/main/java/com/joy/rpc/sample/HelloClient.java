package com.joy.rpc.sample;

import com.joy.rpc.client.RpcProxy;

/**
 * Created by Ai Lun on 2020-08-18.
 */
public class HelloClient {

    public static void main(String[] args) {

        RpcProxy rpcProxy = new RpcProxy("127.0.0.1:8000");

        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.hello("World");
        System.out.println(result);
    }
}
