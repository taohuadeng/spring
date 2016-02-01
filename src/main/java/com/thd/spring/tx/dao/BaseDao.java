package com.thd.spring.tx.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Spring的注解形式：@Repository、@Service、@Controller，
 * 它们分别对应存储层Bean，业务层Bean，和展示层Bean。
 *
 * @author taohuadeng
 * @since 2013-11-15
 */
@Repository("baseDao")
@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseDao {
    private HibernateTemplate hibernateTemplate;

    @Autowired
    public BaseDao(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public BaseDao() {
    }

    public Session getSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public int countRecordsNumber(DetachedCriteria dc, String countDistinctProjections) {
        dc.setProjection(Projections.countDistinct(countDistinctProjections));
        List list = this.getHibernateTemplate().findByCriteria(dc);
        int result = 0;
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Integer item = Integer.parseInt(it.next() + "");
            result += item;
        }
        dc.setProjection(null);// 避免对dc.setProjection影响到其它地方
        return result;
    }

    public List findByCriteria(DetachedCriteria dc, int... firstResultAndMaxResults) {
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (firstResultAndMaxResults != null && firstResultAndMaxResults.length == 2) {
            return this.getHibernateTemplate().findByCriteria(dc, firstResultAndMaxResults[0],
                    firstResultAndMaxResults[1]);
        }

        return getHibernateTemplate().findByCriteria(dc);
    }

    public List findByCriteria(DetachedCriteria dc) {
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(dc);
    }

    /**
     * 求传入的QBC查询总记录条数
     *
     * @param criteria QBC对象
     * @return
     */
    public int getTotalSizeForCriteria(DetachedCriteria criteria) {
        // 获取根据条件分页查询的总行数
        int totalSize = 0;
        criteria.setProjection(Projections.rowCount());
        List list = this.findByCriteria(criteria);
        if (list.isEmpty()) {
            return totalSize;
        } else {
            totalSize = Integer.parseInt(list.get(0).toString());
        }
        criteria.setProjection(null);// 清除count函数
        return totalSize;
    }

    /**
     * 使用HSQL语句检索数据
     *
     * @param queryString
     * @return
     */
    public List find(String queryString) {
        return getHibernateTemplate().find(queryString);
    }

    /**
     * 使用带参数的HSQL语句检索数据
     *
     * @param queryString
     * @param value
     * @return
     */
    public List find(String queryString, Object value) {
        return getHibernateTemplate().find(queryString, value);
    }

    /**
     * 使用带参数的HSQL语句检索数据
     *
     * @param queryString
     * @param value
     * @return
     */
    public List find(String queryString, Object[] value) {
        return getHibernateTemplate().find(queryString, value);
    }

    /**
     * 使用HSQL语句直接增加、更新、删除实体
     *
     * @param queryString
     * @param values
     * @return
     */
    public int bulkUpdate(String queryString, Object[] values) {
        return getHibernateTemplate().bulkUpdate(queryString, values);
    }

    public <T> T get(Class<T> entityClass, Serializable id) {
        Object result = getHibernateTemplate().get(entityClass, id);
        Assert.notNull(result, "Class[" + entityClass + "] with id[" + id + "] not found!");
        return (T) result;
    }

    public <T> T load(Class<T> entityClass, Serializable id) {
        return getHibernateTemplate().load(entityClass, id);
    }

    public <T> T merge(T model) {
        return getHibernateTemplate().merge(model);
    }

    public void saveOrUpdate(Object model) {
        getHibernateTemplate().saveOrUpdate(model);
    }

    public Long save(Object model) {
        return (Long) getHibernateTemplate().save(model);
    }

    public <T> void delete(Class<T> entityClass, Serializable id) {
        getHibernateTemplate().delete(get(entityClass, id));
    }

    public <T> void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    public void flush() {
        getHibernateTemplate().flush();
    }

    /**
     * 查询某列的最大值id
     *
     * @param criteria QBC对象
     * @return
     */
    public int getMaxIdForCriteria(DetachedCriteria criteria, String propertyName) {
        int maxSize = 0;
        criteria.setProjection(Projections.max(propertyName));
        List list = new ArrayList();
        list = this.findByCriteria(criteria);
        if (list.isEmpty()) {
            return maxSize;
        } else {
            if (list.get(0) == null) {
                return maxSize;
            } else {
                maxSize = Integer.parseInt(list.get(0).toString());
            }
        }
        criteria.setProjection(null);// 清除count函数
        return maxSize;
    }
    //TODO ADD JdbcTemplate
}