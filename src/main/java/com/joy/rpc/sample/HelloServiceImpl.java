package com.joy.rpc.sample;

import com.joy.rpc.server.RpcService;

/**
 * Created by Ai Lun on 2020-08-18.
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }
}
