package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestSentinel {
/**
 * 程序连接哨兵入门案例
 * masterName:mymaster
 * sentinels:哨兵集合信息
 * 
 * 端口说明
 * 默认端口:6379
 * 通用端口:16379   PING-PONG 心跳健康监测
 * 哨兵端口:26379
 */
	@Test
	public void test01() {
		
		Set<String> sentinels =new HashSet<>();
		sentinels.add("192.168.111.128:26379");
		JedisSentinelPool pool= new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis =pool.getResource();
		jedis.set("1907", "redis哨兵测试成功!!!");
		jedis.set("1908", "java真他妈难学");
		System.out.println(jedis.get("1907"));
		System.out.println(jedis.get("1908"));
	}
}
