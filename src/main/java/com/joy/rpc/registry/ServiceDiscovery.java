package com.joy.rpc.registry;

import lombok.Data;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ai Lun on 2020-08-16.
 */
@Component
@ConfigurationProperties(prefix = "zookeeper")
public class ServiceDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDiscovery.class);

    private String zkAddress;

    public String discover(String serviceName) {
        ZkClient zkClient = new ZkClient(zkAddress, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
        LOGGER.debug("connect zookeeper");
        try {
            // 获取 service 节点
            String servicePath = Constant.ZK_REGISTRY_PATH + "/" + serviceName;
            if (!zkClient.exists(servicePath)) {
                throw new RuntimeException(String.format("can not find any service node on path: %s", servicePath));
            }
            List<String> addressList = zkClient.getChildren(servicePath);
            if (CollectionUtils.isEmpty(addressList)) {
                throw new RuntimeException(String.format("can not find any address node on path: %s", servicePath));
            }
            // 获取 address 节点
            String address;
            int size = addressList.size();
            if (size == 1) {
                address = addressList.get(0);
                LOGGER.debug("get only address node: {}", address);
            } else {
                address = addressList.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.debug("get random address node: {}", address);
            }

            // 获取 address 节点的值
            String addressPath = servicePath + "/" + address;
            return zkClient.readData(addressPath);
        } catch (Exception e) {
            LOGGER.info("exception: {}", e);
            return "";
        } finally {
            zkClient.close();
        }
    }

    public static void main(String[] args) {
        //ServiceDiscovery serviceDiscovery = new ServiceDiscovery("127.0.0.1:2181");
        //String discovery = serviceDiscovery.discovery("com.joy.rpc.service.Query");
        //ServiceRegistry registry = new ServiceRegistry("127.0.0.1:2181");
        //registry.register("com.joy.rpc.service.PointService", "113.234.124.891", 8881);
        //registry.register("com.joy.rpc.service.PointService", "113.234.124.892", 8884);
        //registry.register("com.joy.rpc.service.PointService", "113.234.124.891", 8886);
        //registry.register("com.joy.rpc.service.PointService", "113.234.124.841", 8821);
        //registry.register("com.joy.rpc.service.PointService", "113.234.124.191", 8883);
        //registry.register("com.joy.rpc.service.PointService", "127.0.0.1", 8000);
        // zookeeper

        //ServiceDiscovery discovery = new ServiceDiscovery("127.0.0.1:2181");
        //String serviceAddress = discovery.discovery("com.joy.rpc.service.PointService");
        ////String serviceAddress = discovery.discovery("com.xxx.rpc.sample.api.HelloService");
        //LOGGER.info("find service address: {}" , serviceAddress);

    }

}
