package com.fz.pay.controller;

import com.fz.pay.service.impl.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: FZ
 * @date: 2021/5/15 21:15
 * @description:
 */
@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {
    @Autowired
        PayService payService;
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount){
        PayResponse response = payService.create(orderId, amount);

        Map map = new HashMap<>( );
        map.put("codeUrl",response.getCodeUrl());
        return new ModelAndView( "create" ,map);
    }
    @PostMapping("/notify")
    public void asyncNotify(@RequestBody String notifyData){
        log.info("notifyData={}",notifyData);
    }
}
