package redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.v80.DistributedLockFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Service
@Slf4j
public class InventoryService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${server.port}")
    private String port;

    @Autowired
    private DistributedLockFactory distributedLockFactory;

    @Autowired
    private Redisson redisson;


    public String saleByRedisson() {
        String retMessage = "";
        RLock redisLock = redisson.getLock("chandlerRedisLock");
        redisLock.lock();
        try {
            //1 查询库存信息
            String result = stringRedisTemplate.opsForValue().get("inventory001");
            //2 判断库存是否足够
            Integer inventoryNumber = result == null ? 0 : Integer.parseInt(result);
            //3 扣减库存
            if (inventoryNumber > 0) {
                stringRedisTemplate.opsForValue().set("inventory001", String.valueOf(--inventoryNumber));
                retMessage = "成功卖出一个商品，库存剩余: " + inventoryNumber;
                System.out.println(retMessage);
            } else {
                retMessage = "商品卖完了，o(╥﹏╥)o";
            }
        } finally {
            if (redisLock.isLocked() && redisLock.isHeldByCurrentThread()) {
                redisLock.unlock();
            }
        }
        return retMessage + "\t" + "服务端口号：" + port;
    }
    /*
     * V7.0
     * 通过lua脚本实现支持可重入的lock，实现Lock接口，
     * */
    public String sale() {
        String retMessage = "";

        Lock redisLock = distributedLockFactory.getDistributedLock("REDIS");
        redisLock.lock();

        //redisDistributedLock.lock();
        try {
            //1 查询库存信息
            String result = stringRedisTemplate.opsForValue().get("inventory001");
            //2 判断库存是否足够
            Integer inventoryNumber = result == null ? 0 : Integer.parseInt(result);
            //3 扣减库存
            if (inventoryNumber > 0) {
                stringRedisTemplate.opsForValue().set("inventory001", String.valueOf(--inventoryNumber));
                retMessage = "成功卖出一个商品，库存剩余: " + inventoryNumber;
                System.out.println(retMessage);

                // 演示自动续期的的功能
                try {
                    TimeUnit.SECONDS.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                retMessage = "商品卖完了，o(╥﹏╥)o";
            }
        } finally {
            redisLock.unlock();
            //redisDistributedLock.unlock();
        }
        return retMessage + "\t" + "服务端口号：" + port;
    }

    private void testReEntry() {
        Lock redisLock = distributedLockFactory.getDistributedLock("redis");
        redisLock.lock();

        //redisDistributedLock.lock();
        try {
            System.out.println("测试可重入锁");
        } finally {
            redisLock.unlock();
            //redisDistributedLock.unlock();
        }
    }
}

