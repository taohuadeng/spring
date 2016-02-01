package com.thd.spring.tx;

import com.thd.spring.tx.account.model.Account;
import com.thd.spring.tx.account.service.AccountService;
import com.thd.spring.tx.apply.model.Apply;
import com.thd.spring.tx.apply.service.ApplyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-tx/spring-tx-configuration_2.xml")
public class SpringTX_2Test {
    @Resource
    private AccountService accountService;
    @Resource
    private ApplyService applyService;

    //回滚
    @Test
    public void testSaveAccount() {
        Account account = new Account();
        account.setAccountName("account2");
        accountService.saveAccountTx2(account);
    }

    //不回滚
    @Test
    public void testSaveApply() {
        Apply apply = new Apply();
        apply.setApplyName("apply");
        applyService.saveApplyTx2NoTx(apply);
    }
}
