package com.thd.spring.tx.apply.service;

import com.thd.spring.tx.account.service.AccountService;
import com.thd.spring.tx.apply.model.Apply;
import com.thd.spring.tx.dao.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 申请管理持久化层
 *
 * @author Fish
 * @since 2104-9-15
 */
@Service
public class ApplyService {
    @Resource
    private BaseDao baseDao;
    @Resource
    private AccountService accountService;

    public Long saveApplyTx1NoTx(Apply apply) {
        baseDao.save(apply);
        throw new RuntimeException("saveApply");
    }

    public Long saveApplyTx2NoTx(Apply apply) {
        baseDao.save(apply);
        throw new RuntimeException("saveApply");
    }

    public Long saveApply(Apply apply) {
        baseDao.save(apply);
        throw new RuntimeException("saveApply");
    }

    //@Transactional(propagation = Propagation.NESTED)
    @Transactional
    public Long saveApplyTx(Apply apply) {
        baseDao.save(apply);
//        try {
        int a = 5 / 0;
//        } catch (Exception e) {
//            //throw new RuntimeException("asdfasdf");
//            e.printStackTrace();
//        }

//        throw new RuntimeException("saveApply");
//        apply.setApplyName("asdfasdfa");
//        baseDao.save(apply);
        return null;
    }
}
