package com.fz.pay.controller;

import com.fz.pay.service.impl.PayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
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
                               @RequestParam("amount") BigDecimal amount,
                               @RequestParam("payType") BestPayTypeEnum bestPayTypeEnum){

        PayResponse response = payService.create(orderId, amount,bestPayTypeEnum);

        Map<String,String> map = new HashMap<>( );
        //支付方式不同渲染不同 微信支付native 往前端传codeUrl 然后再用jq转二维码
        //支付宝pc 返回一个 body表单
        if(bestPayTypeEnum==BestPayTypeEnum.WXPAY_NATIVE){
            map.put("codeUrl",response.getCodeUrl());
            return new ModelAndView("createForWxNative",map);
        }else if (bestPayTypeEnum==BestPayTypeEnum.ALIPAY_PC){
            map.put("body",response.getBody());
            return new ModelAndView("createForAlipayPc",map);
        }

        throw new RuntimeException( "暂不支持的支付类型" );
    }
    @PostMapping("/notify")
    public String asyncNotify(@RequestBody String notifyData){
       return payService.asyncNotify(notifyData);

    }
}
