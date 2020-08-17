package com.joy.rpc.controller;

import com.joy.rpc.registry.ServiceDiscovery;
import com.joy.rpc.server.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ai Lun on 2020-08-17.
 */
@RestController
public class HelloController {

    @Autowired
    private ServiceDiscovery serviceDiscovery;

    @Autowired
    private RpcServer rpcServer;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        //return rpcServer.getServiceAddress();
        return "hi";
    }

    //public String echo(String echoStr) {
    //    return "1111"+ echoStr;
    //}

    public static void main(String[] args) {
        HelloController controller = new HelloController();
        controller.hello();
    }

}
