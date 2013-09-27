package com.yuanqichufang.dao;

import com.yuanqichufang.model.Passport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-21
 * Time: 下午7:58
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PassportDao {

    @PersistenceContext
    private EntityManager entityManager;


    public Passport find (Long id) {
        return this.entityManager.find (Passport.class, id);
    }

    @Transactional
    public void save (Passport passport) {
        if (passport.getId() == null)
            this.entityManager.persist (passport);
        else
            this.entityManager.merge (passport);
    }

}
