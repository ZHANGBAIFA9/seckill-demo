package com.xxxx.seckill.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/3/2 16:12
 * @Description:
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    /**
     * 测试页面跳转
     *
     * @param model
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "xxxxx");
        return "hello";
    }

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private Redisson redisson ;

    @RequestMapping("/destuck")
    @ResponseBody
    public String destuck() {
//        synchronized (this){
        //分布式锁
        String lockKey = "product_01" ;
        String clientId = UUID.randomUUID().toString() ; //解决自己的锁被别人释放掉的场景
//        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, "baifa");
//        redisTemplate.expire(lockKey,10,TimeUnit.SECONDS) ;
        //是上面两行代码的合并
        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey,clientId,10,TimeUnit.SECONDS);
        if(!result){
            return "error_code" ;
        }
        RLock redissonLock = redisson.getLock(lockKey);
        try {
            redissonLock.lock();
            int stock = Integer.parseInt(redisTemplate.opsForValue().get(lockKey));
            if (stock > 0) {
                int realStock = stock - 1;
                redisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存：" + realStock);
            } else {
                System.out.println("扣减失败，库存不足");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {//释放锁
            //判断当前锁是否是自己拥有的锁
//            if(clientId.equals(redisTemplate.opsForValue().get(lockKey))){
//                //程序卡顿仍然会出现释放掉别人的锁问题
//                redisTemplate.delete(lockKey) ;
//            }
            redissonLock.unlock();
        }
//        }
        return "stock：";
    }

}
