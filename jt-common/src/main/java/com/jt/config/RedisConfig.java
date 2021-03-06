package com.jt.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * 实现spring容器管理redis配置类
 *
 * @author Administrator
 */
@Configuration // 标识配置类
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

    @Value("${redis.nodes}")
    private String redisNodes; //node,node,node

    //将Set集合交给容器管理 不与其他代码产生干扰
    @Bean("redisSet")
    public Set<HostAndPort> redisSet() {
        Set<HostAndPort> redisSet = new HashSet<HostAndPort>();
        String[] arrayNodes = redisNodes.split(",");
        for (String node : arrayNodes) { //ip:port
            String host = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            HostAndPort hostAndPort = new HostAndPort(host, port);
            redisSet.add(hostAndPort);
        }
        return redisSet;
    }

    //qualifier  指定引用容器位置
    @Bean
    @Scope("prototype")
    public JedisCluster jedisCluster(@Qualifier("redisSet") Set<HostAndPort> redisSet) {
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
        return new JedisCluster(redisSet, 5000, 3000, 10, "abcdefg", jedisPoolConfig);
    }
}

/*
 * @Value("${redis.sentinels}") private String sentinels;
 *
 * 实现哨兵的配置
 *
 * @Bean //定义哨兵池对象 public JedisSentinelPool pool() { Set<String> set = new
 * HashSet<>(); set.add(sentinels); return new
 * JedisSentinelPool("mymaster",set); }
 *
 * //规则:可以为bean的方法自动的注入参数对象 //方法2:指定ID进行注入
 * //@Qualifier("jedisSentinelPool")JedisSentinelPool pool
 *
 * @Bean
 *
 * @Scope("prototype") public Jedis jedis(JedisSentinelPool pool) {
 *
 * return pool.getResource(); }
 */

/*
 * @Value("${redis.nodes}") private String nodes; //node,node,node
 */
/**
 * redis分片
 */
/*
 * @Bean
 *
 * @Scope("prototype") public ShardedJedis shardedJedis() { List<JedisShardInfo>
 * shards = new ArrayList<JedisShardInfo>(); String[] redisNodes =
 * nodes.split(","); for (String redisNode : redisNodes) { //redisNode=IP:PORT
 * String[] hostAndPort = redisNode.split(":"); String host = hostAndPort[0];
 * int port = Integer.parseInt(hostAndPort[1]); JedisShardInfo info = new
 * JedisShardInfo(host, port); shards.add(info); } return new
 * ShardedJedis(shards); }
 */

/*
 * @Value("${redis.host}") private String host;
 *
 * @Value("${redis.port}") private Integer port;
 *
 * @Bean
 *
 * @Scope("prototype") //设置为多例 当用户使用时创建 public Jedis jedis() {
 *
 * return new Jedis(host, port); }
 */
