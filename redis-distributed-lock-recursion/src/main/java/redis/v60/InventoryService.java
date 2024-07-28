//package redis.v60;
//
//import cn.hutool.core.util.IdUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.script.DefaultRedisScript;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.concurrent.TimeUnit;
//
//@Service
//@Slf4j
//public class InventoryService
//{
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @Value("${server.port}")
//    private String port;
//
//    /*
//    * 在超时时间内没有完成正常的业务逻辑，导致其他线程成功获取到分布式锁，而此时业务完成，删除分布式锁时误删除其他线程持有的锁
//    * */
//    public String sale() {
//        String retMessage = "";
//        String key = "chandlerRedisLock";
//        String value = IdUtil.simpleUUID() + ":" + Thread.currentThread().getId();
//
//        while (!stringRedisTemplate.opsForValue().setIfAbsent(key, value, 30L, TimeUnit.SECONDS)) {
//            try {
//                Thread.sleep(20);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // 原子性无法保证
////        stringRedisTemplate.expire(key, 30L, TimeUnit.SECONDS);
//
//        try {
//            //1 查询库存信息
//            String result = stringRedisTemplate.opsForValue().get("inventory001");
//            //2 判断库存是否足够
//            Integer inventoryNumber = result == null ? 0 : Integer.parseInt(result);
//            //3 扣减库存
//            if (inventoryNumber > 0) {
//                stringRedisTemplate.opsForValue().set("inventory001", String.valueOf(--inventoryNumber));
//                retMessage = "成功卖出一个商品，库存剩余: " + inventoryNumber;
//                System.out.println(retMessage);
//            } else {
//                retMessage = "商品卖完了，o(╥﹏╥)o";
//            }
//        } finally {
//            // 改进点，修改为Lua脚本的Redis分布式锁调用，必须保证原子性
//            String luaScript =
//                    "if (redis.call('get',KEYS[1]) == ARGV[1]) then " +
//                            "return redis.call('del',KEYS[1]) " +
//                            "else " +
//                            "return 0 " +
//                            "end";
//            stringRedisTemplate.execute(new DefaultRedisScript<>(luaScript, Boolean.class), Arrays.asList(key), value);
//        }
//        return retMessage + "\t" + "服务端口号：" + port;
//    }
//}
//
