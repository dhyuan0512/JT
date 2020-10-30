package com.jt;

import org.junit.Test;
import org.junit.Before;
import redis.clients.jedis.Jedis;

public class TestRedis {
    /**
     * spring链接redis入门案例
     *
     * @throws InterruptedException
     */
    @Test
    public void testString() throws InterruptedException {
        String host = "101.200.204.106";
        int port = 6379;
        Jedis jedis = new Jedis(host, port);
        jedis.auth("abcdefg");
        jedis.set("1907", "redis的入门案例!!!");
        System.out.println(jedis.get("1907"));

        //批量操作
        jedis.mset("111", "222");
        System.out.println(jedis.mset("a", "a"));

        //清空数据库
        jedis.flushDB();

        //自增
        jedis.set("num", "1");
        Long result = jedis.incr("num");
        System.out.println("获取的自增结果是:" + result);

        //设定超时时间
        jedis.expire("num", 30);
        Thread.sleep(2000);
        System.out.println(jedis.ttl("num"));

        //撤销超时时间
        jedis.persist("num");
    }

    public Jedis jedis;

    //说明:添加before注解的作用,当@test注解执行时,先执行@before
    @Before
    public void init() {
        String host = "101.200.204.106";
        int port = 6379;
        jedis = new Jedis(host, port);
    }

    /**
     *
     */
    @Test
    public void testString2() {
		jedis.auth("abcdefg");
		if (jedis.exists("abc")) {
            jedis.set("abc", "aaaa");
            System.out.println("入库成功!!");
        } else {
            System.out.println("入库失败!!数据应经存在");
        }
        //利用redisApi实现上述功能
        Long result = jedis.setnx("abc", "bbbb");
        System.out.println("返回数值结果:" + result);
    }

    /**
     * 为数据添加超时时间 原子性操作
     */
    @Test
    public void testString3() {
		jedis.auth("abcdefg");
        jedis.setex("time", 10, "asdf");
        //jedis.psetex(key, milliseconds, value)
        //NX:不允许修改 xx可以修改 ex秒 px毫秒
        jedis.set("1907", "设定redis值", "NX", "EX", 10);
    }

    /**
     *
     */
    @Test
    public void testList() {
		jedis.auth("abcdefg");
        jedis.lpush("list", "1", "2", "3", "4", "5");
        System.out.println(jedis.rpop("list"));
    }

}