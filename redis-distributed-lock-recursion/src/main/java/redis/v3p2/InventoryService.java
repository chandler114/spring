//package redis.v3p2;
//
//import cn.hutool.core.util.IdUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
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
//    public String sale() {
//        String retMessage = "";
//        String key = "chandlerRedisLock";
//        String value = IdUtil.simpleUUID() + ":" + Thread.currentThread().getId();
//
//        while(!stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
//            try {
//                Thread.sleep(20);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
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
//            stringRedisTemplate.delete(key);
//        }
//        return retMessage + "\t" + "服务端口号：" + port;
//    }
//}
//
