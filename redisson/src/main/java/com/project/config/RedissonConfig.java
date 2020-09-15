package com.project.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @program: extension
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-09-14 17:12
 *
 * 配置参考链接
 * https://github.com/redisson/redisson/wiki/2.-%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95#21-%E7%A8%8B%E5%BA%8F%E5%8C%96%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95
 **/
@Configuration
@ConditionalOnClass(Config.class)
public class RedissonConfig {

    /**
     * 单机模式自动装配
     * @return
     */
    @Bean

    RedissonClient redisson() {
        Config config = null;
        try {
            config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("REdisson.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Redisson.create(config);

    }

}
