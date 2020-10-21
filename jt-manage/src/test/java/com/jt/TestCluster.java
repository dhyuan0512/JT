package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestCluster {
	/**
	 * 利用spring整合redis集群
	 * 和分片几乎相同!!!
	 */
	@Test
	public void testCluster() {
		Set<HostAndPort> node = new HashSet<HostAndPort>();
		node.add(new HostAndPort("192.168.111.128",7000));
		node.add(new HostAndPort("192.168.111.128",7001));
		node.add(new HostAndPort("192.168.111.128",7002));
		node.add(new HostAndPort("192.168.111.128",7003));
		node.add(new HostAndPort("192.168.111.128",7004));
		node.add(new HostAndPort("192.168.111.128",7005));
		JedisCluster cluster = new JedisCluster(node);
		cluster.set("1907", "redis集群测试成功!!!!");
		System.out.println(cluster.get("1907"));
	}
}
