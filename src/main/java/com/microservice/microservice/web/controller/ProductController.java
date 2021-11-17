package com.microservice.microservice.web.controller;
import com.microservice.microservice.dao.ProductDao;
import com.microservice.microservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController
{
    //l'anotation @Autowired fait que Spring crée une instance de ProductDao
    @Autowired
    private ProductDao productDao;

    @RequestMapping(value="/Produits", method= RequestMethod.GET)
    public List<Product> listeProduits()
    {
        return productDao.findAll();
    }

    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id)
    {
        return productDao.findById(id);
    }

}
