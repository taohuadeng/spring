package com.thd.spring.tx.account.service;

import com.thd.spring.tx.account.model.Account;
import com.thd.spring.tx.apply.model.Apply;
import com.thd.spring.tx.apply.service.ApplyService;
import com.thd.spring.tx.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 账户管理持久化层
 */
//@Service
public class AccountService {
    private static final Log LOG = LogFactory.getLog(AccountService.class);

    @Resource
    private BaseDao baseDao;
    @Resource
    private ApplyService applyService;

    public String saveAccountTx1(Account account) {
        baseDao.save(account);
        throw new RuntimeException("saveApply");
    }

    public String addAccount(Account account) {
        Long accountId = null;
        try {
            accountId = saveAccount(account);
        } catch (Exception e) {
            LOG.error("吞掉了异常信息，很可怕！", e);
        }
        return "SOA " + account.getAccountName() + "主键：" + accountId;
    }

    @Transactional
    public Long saveAccount(Account account) {
        baseDao.save(account);
        Apply apply = new Apply();
        apply.setApplyName("remoteApply");
//        try {
        applyService.saveApply(apply);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        throw new RuntimeException("saveAccount");
        return null;
    }

    @Transactional
    public Long saveAccountTx(Account account) {
        Long accountId = null;
        Apply apply = new Apply();
        apply.setApplyName("remoteApply");
        try {
            //accountId = saveAccount(account);
            accountId = applyService.saveApplyTx(apply);
        } catch (Exception e) {
            LOG.error("吞掉了异常信息，很可怕！", e);
        }
        return accountId;
    }
}
