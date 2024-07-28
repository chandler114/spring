package com.chandler.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chandler.demo.domain.Order;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 订单表 Mapper
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-03-26 13:38
 */
@Component
public interface OrderMapper extends BaseMapper<Order> {
}
