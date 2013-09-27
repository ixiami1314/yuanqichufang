package com.yuanqichufang.web.controller;

import com.yuanqichufang.dao.ProductDao;
import com.yuanqichufang.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoxia
 * Date: 13-9-7
 * Time: 下午11:53
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping (value = "/administrator/product/")
public class AdminProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping (value = "list", method = RequestMethod.GET)
    public ModelAndView list () {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("administrator/product/list");

        List <Product> result = productDao.findProducts();
        mav.addObject ("products", result);

        return mav;
    }

    @RequestMapping (value = "edit", method = RequestMethod.GET)
    public ModelAndView edit (@RequestParam (value = "id") Long id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("administrator/product/edit");

        if (id != null) {
            Product product = productDao.find (id);
            if (product != null) {
                mav.addObject ("product", product);
            }
        }

        return mav;
    }

    @RequestMapping (value = "edit", method = RequestMethod.POST)
    public ModelAndView editPost (@RequestParam (value = "name") String name,
                                  @RequestParam (value = "description") String des,
                                  @RequestParam (value = "price") Double price,
                                  @RequestParam (value = "id") Long id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName ("administrator/product/list");


        Product product = new Product();
        product.setName (name);
        product.setDescription (des);
        product.setPrice (price);

        productDao.save (product);

        mav.addObject ("product", product);
        return mav;
    }
}
