package com.fz.pay.service.impl;

import com.fz.pay.service.IPayService;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
    @Override
    public PayResponse create(String orderId, BigDecimal amount) {
        WxPayConfig wxPayConfig = new WxPayConfig( );
        wxPayConfig.setAppId("wxd898fcb01713c658");
        wxPayConfig.setMchId("1483469312");
        wxPayConfig.setMchKey("7mdApPMfXddfWWbbP4DUaVYm2wjyh3v3");
        wxPayConfig.setNotifyUrl("http://fzsell.nat300.top/pay/notify");

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);

        PayRequest request = new PayRequest( );
        request.setOrderName("9645034-最好的支付sdk");
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue( ));
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);

        PayResponse response = bestPayService.pay(request);
        log.info("response={}",response);
        return response;
    }

    @Override
    public void asyncNotify(String notifyData) {
        //校验签名
    }
}
