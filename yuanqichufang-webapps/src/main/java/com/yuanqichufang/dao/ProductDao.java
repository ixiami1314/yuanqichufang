package com.yuanqichufang.dao;

import com.yuanqichufang.model.Product;
import oracle.jrockit.jfr.openmbean.ProducerDescriptorType;
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
 * Time: 下午8:55
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    private String query_products = "SELECT p FROM Product p";
    private String query_product_by_short_char = "SELECT p FROM Product p WHERE p.shortChar = ";


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

    /**
     * 通过shortChar查找产品
     * @param shortChar
     * @return
     */
    public Product findProductByShortChar (String shortChar) {
        Product product = null;
        Query query = this.entityManager.createQuery (query_product_by_short_char + "'" + shortChar + "'");
        //query.setParameter ("shortChar", shortChar);
        List <Product> result = query.getResultList();
        if (result.size() > 0)
            product = result.get (0);
        return product;
    }

    /**
     * 获取所有的产品
     * @return
     */
    public List <Product> findProducts () {
        Query query = this.entityManager.createQuery (query_products);
        return query.getResultList();
    }
}
