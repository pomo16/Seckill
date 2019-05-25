package com.pomo.miaosha.service;

import com.pomo.miaosha.dao.OrderDao;
import com.pomo.miaosha.domain.MiaoshaOrder;
import com.pomo.miaosha.domain.MiaoshaUser;
import com.pomo.miaosha.domain.OrderInfo;
import com.pomo.miaosha.redis.OrderKey;
import com.pomo.miaosha.redis.RedisService;
import com.pomo.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        //return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        return redisService.get(OrderKey.getMiaoshaOrderByUisGid, ""+userId+"_"+goodsId, MiaoshaOrder.class);
    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderDao.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);

        redisService.set(OrderKey.getMiaoshaOrderByUisGid, ""+user.getId()+"_"+goods.getId(), miaoshaOrder);

        return orderInfo;
    }

    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteMiaoshaOrders();
    }
}
