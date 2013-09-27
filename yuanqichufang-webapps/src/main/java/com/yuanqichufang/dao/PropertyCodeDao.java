package com.yuanqichufang.dao;

import com.yuanqichufang.model.Passport;
import com.yuanqichufang.model.PropertyCode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-22
 * Time: 下午8:39
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PropertyCodeDao {

    @PersistenceContext
    private EntityManager entityManager;
    private String query_code_by_code = "SELECT c FROM PropertyCode c WHERE c.code = '";

    /**
     * 持久化
     * @param code
     */
    public void save (PropertyCode code) {
        if (code.getId() == null)
            this.entityManager.persist (code);
        else
            this.entityManager.merge (code);
    }

    /**
     * 生成COUNT个道具码
     * @param count
     */
    @Transactional
    public void generatePropertyCode (int count) {
        PropertyCode pCode = null;
        for (int i = 0; i < count; i ++)  {
            pCode = new PropertyCode();
            pCode.setCode (DigestUtils.md5DigestAsHex (new java.util.Date().toString().getBytes()).toString());
            pCode.setUseable (false);
            this.entityManager.persist (pCode);
        }
    }

    /**
     * 通过CODE获取对象
     * @param code
     * @return
     */
    public PropertyCode findByCode (String code) {
        PropertyCode propertyCode = null;
        Query query = entityManager.createQuery (query_code_by_code + code + "'");
        List <PropertyCode> codes = query.getResultList();
        if (codes.size() > 0)
            propertyCode = codes.get (0);
        return propertyCode;
    }

    /**
     * 道具卡已使用
     * @param pCode
     */
    @Transactional
    public void tobeUse (PropertyCode pCode, Passport passport) {
        if (pCode.getId() == null) return;
        if (passport.getId() == null) return;
        pCode.setUseable (true);
        SimpleDateFormat f = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        pCode.setUsedDate (new Date(new java.util.Date ().getTime ()));
        pCode.setPassport (passport);
        this.entityManager.merge (pCode);
    }
}
