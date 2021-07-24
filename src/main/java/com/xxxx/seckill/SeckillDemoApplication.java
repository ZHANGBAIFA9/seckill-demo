package com.xxxx.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.xxxx.seckill.pojo")
public class SeckillDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillDemoApplication.class, args);
    }


//    @Autowired
//    private Environment env;
//
//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redissonClient() throws IOException {
//        String[] profiles = env.getActiveProfiles();
//        String profile = "";
//        if(profiles.length > 0) {
//            profile = "-" + profiles[0];
//        }
//        return Redisson.create(
//                Config.fromYAML(new ClassPathResource("redisson" + profile + ".yml").getInputStream())
//        );
//    }
    @Bean()
    public Redisson redisson(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379").setDatabase(0);
        return (Redisson) Redisson.create(config);
    }

}
