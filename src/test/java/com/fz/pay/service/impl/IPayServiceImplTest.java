package com.fz.pay.service.impl;

import com.fz.pay.PayApplicationTests;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author: FZ
 * @date: 2021/5/15 20:36
 * @description:
 */
public class IPayServiceImplTest extends PayApplicationTests {

@Autowired
PayService payService;
    @Test
    public void create() {
//        new BigDecimal(0.01) 会有精度损失
        payService.create("147145111", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);

    }
}