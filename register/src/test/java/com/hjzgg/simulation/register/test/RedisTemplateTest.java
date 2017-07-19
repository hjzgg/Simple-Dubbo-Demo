package com.hjzgg.simulation.register.test;

import com.hjzgg.simulation.common.cache.RedisCacheTemplate;
import com.hjzgg.simulation.register.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class RedisTemplateTest {

    @Autowired
    private RedisCacheTemplate redisCacheTemplate;

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    @Test
    public void test() {
        redisCacheTemplate.hPut("hjz", "xxx","1");
        redisCacheTemplate.hPut("hjz", "hhh", "2");
//        System.out.println(redisCacheTemplate.hGet("hjz", "xxx"));
//        System.out.println(redisCacheTemplate.hGet("hjz", "hhh"));

        System.out.println(redisCacheTemplate.hKeys("hjz").size());
    }

}