package com.thd.spring.tx.apply.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 申请类实体
 */
@Entity
@Table(name = "T_APPLY")
public class Apply {
    @Id
    @GeneratedValue
    private Long applyId;

    private String applyName;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }
}
