package com.pomo.miaosha.controller;

import com.pomo.miaosha.domain.User;
import com.pomo.miaosha.rabbitmq.MQSender;
import com.pomo.miaosha.redis.RedisService;
import com.pomo.miaosha.redis.UserKey;
import com.pomo.miaosha.result.CodeMsg;
import com.pomo.miaosha.result.Result;
import com.pomo.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;

    @RequestMapping("/mq/header")
    @ResponseBody
    public Result<String> header() {
        sender.sendHeader("hello, i am mq!");
        return Result.success("yes");
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> topic() {
        sender.sendTopic("hello, i am mq!");
        return Result.success("yes");
    }

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("pomonb");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        User user = redisService.get(UserKey.getById, "" + 1, User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById, "" + 1, user);
        return Result.success(true);
    }
}
