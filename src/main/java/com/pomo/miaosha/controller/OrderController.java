package com.pomo.miaosha.controller;

import com.pomo.miaosha.domain.MiaoshaUser;
import com.pomo.miaosha.domain.OrderInfo;
import com.pomo.miaosha.redis.RedisService;
import com.pomo.miaosha.result.CodeMsg;
import com.pomo.miaosha.result.Result;
import com.pomo.miaosha.service.GoodsService;
import com.pomo.miaosha.service.MiaoshaUserService;
import com.pomo.miaosha.service.OrderService;
import com.pomo.miaosha.vo.GoodsVo;
import com.pomo.miaosha.vo.OrderDetailVo;
import com.sun.tools.javac.jvm.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user, @RequestParam("orderId")long orderId) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setGoods(goods);
        vo.setOrder(order);
        return Result.success(vo);
    }
}
