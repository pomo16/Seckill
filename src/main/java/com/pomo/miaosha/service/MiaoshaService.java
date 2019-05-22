package com.pomo.miaosha.service;

import com.pomo.miaosha.domain.MiaoshaUser;
import com.pomo.miaosha.domain.OrderInfo;
import com.pomo.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单(事务)
        goodsService.reduceStock(goods);
        //order_info miaosha_order
        return orderService.createOrder(user, goods);
    }
}
