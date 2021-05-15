package com.fz.pay.service;

import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * @author: FZ
 * @date: 2021/5/15 18:06
 * @description:
 */
public interface IPayService {
    /**
     * 创建支付
     */
    PayResponse create(String orderId , BigDecimal amount);
    /**
     * 异步通知处理
     */
    void asyncNotify(String notifyData);
}
