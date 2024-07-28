//package redis.v70;
//
//import cn.hutool.core.util.IdUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.locks.Lock;
//
///**
// * @author 晓风残月Lx
// * @date 2023/4/1 22:14
// */
//@Component
//public class DistributedLockFactory {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    private String lockName;
//    private String uuidValue;
//
//    public DistributedLockFactory() {
//        this.uuidValue = IdUtil.simpleUUID();
//    }
//
//    public Lock getDistributedLock(String lockType) {
//        if (lockType == null) {
//            return null;
//        }
//        if (lockType.equalsIgnoreCase("REDIS")) {
//            this.lockName = "chandlerRedisLock";
//            return new RedisDistributedLock(stringRedisTemplate, lockName, uuidValue);
//        }else if (lockType.equalsIgnoreCase("ZOOKEEPER")) {
//            this.lockName = "xfcyZookeeperLock";
//            // TODO zoookeeper 版本的分布式锁
//            return null;
//        }else if (lockType.equalsIgnoreCase("MYSQL")){
//            this.lockName = "xfcyMysqlLock";
//            // TODO MYSQL 版本的分布式锁
//            return null;
//        }
//        return null;
//    }
//
//}
//
