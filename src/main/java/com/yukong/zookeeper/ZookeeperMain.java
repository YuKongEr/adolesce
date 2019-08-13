package com.yukong.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * @author yukong
 * @date 2019-02-27 09:58
 */
public class ZookeeperMain {

    /**
     * 集群用,分开 中间不需要空哥
     */
    private String connectString = "193.112.100.103:2181";

    /**
     * 会话超时时间 单位是毫秒
     */
    private Integer sessionTimeOut = 2000;

    private ZooKeeper zkClient = null;

    ZookeeperMain() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {

            @Override
            public void process(WatchedEvent event) {

            }
        });
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZookeeperMain zooKeeperMain = new ZookeeperMain();
        zooKeeperMain.createElement();
    }

    /**
     * 创建节点
     */
    public void createElement() throws KeeperException, InterruptedException {
        String path = zkClient.create("/yukong", "kong".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println(path);
    }

    /**
     * 监听节点
     */
    public void watchNode() throws KeeperException, InterruptedException {
        List<String> childrens = zkClient.getChildren("/", true);
        for (String name: childrens) {
            System.out.println(name);
        }

        Thread.sleep(Long.MAX_VALUE);
    }

}
