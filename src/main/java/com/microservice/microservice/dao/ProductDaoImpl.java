package com.microservice.microservice.dao;

import com.microservice.microservice.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
/*
//@Repository indique à Spring qu'il s'agit d'une class qui gère les données
@Repository
public class ProductDaoImpl implements ProductDao
{
    //Simulation de BDD
    public static List<Product> products = new ArrayList<>();
    static
    {
        products.add(new Product(1, "Ordinateur portable", 350,120));
        products.add(new Product(2, "Aspirateur Robot", 500,200));
        products.add(new Product(3, "Table de Ping Pong", 750,400));
    }
    //Return tous les produits
    @Override
    public List<Product> findAll()
    {
        return products;
    }

    //Return un produit spécifique en fonction de l'ID
    @Override
    public Product findById(int id)
    {
        for (Product product: products)
        {
            if (product.getId() == id)
            {
                return product;
            }
        }
        return null;
    }

    //Ajoute un produit
    @Override
    public Product save(Product product)
    {
        products.add(product);
        return product;
    }
}
*/