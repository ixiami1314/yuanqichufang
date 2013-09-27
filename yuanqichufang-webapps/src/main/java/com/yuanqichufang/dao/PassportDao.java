package com.yuanqichufang.dao;

import com.yuanqichufang.model.Passport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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
    private String query_passport_by_name = "SELECT p FROM Passport p WHERE p.passport = ";


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

    /**
     * 通过名字定位人员
     * @param passportName
     * @return
     */
    public Passport findPassportByName (String passportName) {
        Passport passport = null;
        Query query = this.entityManager.createQuery (query_passport_by_name + "'" + passportName + "'");
        //query.setParameter (1, passportName);
        List <Passport> result = query.getResultList ();
        if (result != null)
            passport = result.get (0);
        return passport;
    }

}
