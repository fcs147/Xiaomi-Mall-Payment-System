package com.fz.pay.service.impl;

import com.fz.pay.service.IPayService;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: FZ
 * @date: 2021/5/15 18:14
 * @description:
 */
@Slf4j
@Service
public class PayService implements IPayService {
    @Autowired
    BestPayService bestPayService;
    @Override
    public PayResponse create(String orderId, BigDecimal amount,BestPayTypeEnum bestPayTypeEnum) {

        //写入数据库
        PayRequest request = new PayRequest( );
        request.setOrderName("9645034-最好的支付sdk");
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue( ));
        request.setPayTypeEnum(bestPayTypeEnum);

        PayResponse response = bestPayService.pay(request);
        log.info("response={}",response);
        return response;
    }

    @Override
    public String asyncNotify(String notifyData) {
        //校验签名
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("payResponse={}",payResponse);
        //金额校验（从数据库中查）

        //修改订单状态

        //告诉微信不要再通知了
        if(payResponse.getPayPlatformEnum()== BestPayPlatformEnum.WX){
            return "<xml>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        }else if (payResponse.getPayPlatformEnum()==BestPayPlatformEnum.ALIPAY){
            return "success";
        }
        throw new RuntimeException( "异步通知错误的支付平台" );
    }
}
