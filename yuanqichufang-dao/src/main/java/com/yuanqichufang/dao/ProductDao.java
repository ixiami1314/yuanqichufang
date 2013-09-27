package com.yuanqichufang.dao;

import com.yuanqichufang.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-21
 * Time: 下午8:55
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save (Product product) {
        if (product.getId() == null)
            this.entityManager.persist (product);
        else
            this.entityManager.merge (product);
    }

    public Product find (Long id) {
        return this.entityManager.find (Product.class, id);
    }
}
