package com.joy.rpc.registry;

import lombok.Data;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.joy.rpc.registry.Constant.*;

/**
 * Created by Ai Lun on 2020-08-16.
 */
@Component
@ConfigurationProperties(prefix = "zookeeper")
@Data
public class ServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);

    private String zkAddress;

    //public ServiceRegistry(String zkAddress) {
    //    this.zkAddress = zkAddress;
    //}

    public void register(String serviceName, String serviceAddress) {
        ZkClient zkClient = new ZkClient(zkAddress, ZK_SESSION_TIMEOUT, ZK_CONNECTION_TIMEOUT);

        // 1. 创建 registry 节点（持久）
        String registryPath = ZK_REGISTRY_PATH;
        if (!zkClient.exists(registryPath)) {
            zkClient.createPersistent(registryPath);
            LOGGER.info("create registry node: {}", registryPath);
        }

        // 2. 创建 service 节点（持久）
        String servicePath = ZK_REGISTRY_PATH + "/" +serviceName;
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
            LOGGER.info("create service node: {}", registryPath);
        }
        String addressPath = servicePath + "/address-";
        zkClient.createEphemeralSequential(addressPath, serviceAddress);
        LOGGER.debug("create service node: {}", registryPath);

    }

}
