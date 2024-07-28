//package redis.v70;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.script.DefaultRedisScript;
//
//import java.util.Arrays;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.Lock;
//
///**
// * @author 晓风残月Lx
// * @date 2023/4/1 21:38
// * 自研的redis分布式锁，实现 Lock 接口
// */
//// @Component 引入DistributedLockFactory工厂模式，从工厂获得即可
//public class RedisDistributedLock implements Lock {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    private String lockName;    // KEYS[1]
//    private String uuidValue;   // ARGV[1]
//    private long expireTime;    // ARGV[2]
//
//    public RedisDistributedLock(StringRedisTemplate stringRedisTemplate, String lockName, String uuidValue) {
//        this.stringRedisTemplate = stringRedisTemplate;
//        this.lockName = lockName;
//        this.uuidValue = uuidValue + ":" + Thread.currentThread().getId();
//        this.expireTime = 30L;
//    }
//
//    @Override
//    public void lock() {
//        tryLock();
//    }
//
//    @Override
//    public boolean tryLock() {
//        try {
//            tryLock(-1L, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    @Override
//    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
//        if (time == -1L) {
//            String script = "if redis.call('exists',KEYS[1]) == 0 or redis.call('hexists',KEYS[1],ARGV[1]) == 1 then " +
//                    "redis.call('hincrby',KEYS[1],ARGV[1],1)  " +
//                    "redis.call('expire',KEYS[1],ARGV[2]) " +
//                    "return 1 " +
//                    "else " +
//                    "return 0 " +
//                    "end";
//            System.out.println("lockName = " + lockName +"\t" + "uuidValue = " + uuidValue);
//            while (!stringRedisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), Arrays.asList(lockName), uuidValue, String.valueOf(expireTime))) {
//                // 暂停 60ms
//                Thread.sleep(60);
//            }
//            // 新建一个后台扫描程序，来监视key目前的ttl，是否到我们规定的 1/2 1/3 来实现续期
////            resetExpire();
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void unlock() {
//        String script = "if redis.call('HEXISTS',KEYS[1],ARGV[1]) == 0 then " +
//                "return nil " +
//                "elseif redis.call('HINCRBY',KEYS[1],ARGV[1],-1) == 0 then  " +
//                "return redis.call('del',KEYS[1]) " +
//                "else  " +
//                "return 0 " +
//                "end";
//        // nil = false  1 = true  0 = false
//        Long flag = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(lockName), uuidValue, String.valueOf(expireTime));
//        if (null == flag) {
//            throw new RuntimeException("this lock doesn't exists0");
//        }
//    }
//
//    private void resetExpire() {
//        String script = "if redis.call('HEXISTS',KEYS[1],ARGV[1]) == 1 then " +
//                "return redis.call('expire',KEYS[1],ARGV[2]) " +
//                "else " +
//                "return 0 " +
//                "end";
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (stringRedisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), Arrays.asList(lockName), uuidValue, String.valueOf(expireTime))) {
//                    resetExpire();
//                }
//            }
//        }, (this.expireTime * 1000) / 3);
//    }
//
//
//    // 下面两个用不上
//    // 下面两个用不上
//    // 下面两个用不上
//
//    @Override
//    public void lockInterruptibly() throws InterruptedException {
//
//    }
//
//    @Override
//    public Condition newCondition() {
//        return null;
//    }
//}
//
