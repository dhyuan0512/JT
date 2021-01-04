package com.jt;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

public class TestCluster {
    /**
     * 利用spring整合redis集群
     * 和分片几乎相同!!!
     */
    @Test
    public void testCluster() {
        Set<HostAndPort> node = new HashSet<HostAndPort>();
        // Jedis连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(100);
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(500);
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);
        node.add(new HostAndPort("47.116.140.127", 7000));
        node.add(new HostAndPort("47.116.140.127", 7001));
        node.add(new HostAndPort("47.116.140.127", 7002));
        node.add(new HostAndPort("47.116.140.127", 7003));
        node.add(new HostAndPort("47.116.140.127", 7004));
        node.add(new HostAndPort("47.116.140.127", 7005));
        JedisCluster cluster = new JedisCluster(node, 5000, 3000, 10, "abcdefg", jedisPoolConfig);
        cluster.set("1907", "redis集群测试成功!!!!");
        System.out.println(cluster.get("1907"));
    }
}
