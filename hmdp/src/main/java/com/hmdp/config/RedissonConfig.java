package com.hmdp.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConfig {
    /**
      * 从yml文件中读取主机名
     */
    private String host;
    /**
     * 从yml文件中读取端口名
     */
    private String port;
    /**
     * 设置redis密码
     */
    private String password;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bean
    public RedissonClient redissonClient() {
        // 创建配置
        Config config = new Config();

        String redisAddress = String.format("redis://%s:%s", host, port);
        config.useSingleServer().setAddress(redisAddress).setDatabase(1).setPassword(password);

        // 创建实例
        RedissonClient redissonClient = Redisson.create(config);

        return redissonClient;
    }
}
