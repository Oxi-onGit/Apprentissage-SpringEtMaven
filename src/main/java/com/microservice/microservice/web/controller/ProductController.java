package com.microservice.microservice.web.controller;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.microservice.microservice.dao.ProductDao;
import com.microservice.microservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController
{
    //l'anotation @Autowired fait que Spring crée une instance de ProductDao
    @Autowired
    private ProductDao productDao;

    //annotations pour permettre à Spring de savoir quelle méthode activer en fonction de la
    //requête reçus dans ce cas Get avec /Produits dans l'url
    @RequestMapping(value="/Produits", method= RequestMethod.GET)
    public MappingJacksonValue listeProduits()
    {
        List<Product> produits = productDao.findAll();

        //SimpleBeanPropertyFilter permet d'établir les règles de filtrage sur un Bean
        //serializeAllExcept exclue les propriétés passer en paramètre
        //filterOutAllExcept exclue tout sauf les propriétés passer en paramètre
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        //SimpleFilterProvider permet d'indiquer à Jackson (une des LIB de Spring)
        //d'apliquer se filtre a tout les Bean qui on été annotées avec @JsonFilter("monFiltreDynamique")
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        //MappingJacksonValue permet d'avoir par la suite accès aux données avec les méthodes de Jackson
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        //setFilters permet d'appliquer le filtre précédemment crée
        produitsFiltres.setFilters(listDeNosFiltres);

        //Return d'un conteneur qui contient les données avec le filtre
        return produitsFiltres;
    }

    //(@GetMapping)Même utilité que @RequestMapping mais directement pour GET
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id)
    {
        return productDao.findById(id);
    }

    //Tester avec Postman
    //(@PostMapping)Même utilité que @RequestMapping mais directement pour  POST
    //L'annotation @RequestBody est là pour convertir les données format JSON en objet Java
    //ResponseEntity hérite de HttpEntity pour permettre de personnaliser personnaliser le code html à retourner
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@RequestBody Product product)
    {
       Product productAdded = productDao.save(product);

       if (productAdded == null)
       {
           //S'il y a une erreur à l'ajout du produit en BDD on retourne le code html 204 NO Content
           //avec la méthode noContent() puis build()
           return ResponseEntity.noContent().build();
       }

        URI location = ServletUriComponentsBuilder //Construction de l'URI
                .fromCurrentRequest()//URL de la requete reçus
                .path("/{id}") //Paramètre
                .buildAndExpand(productAdded.getId()) //Ajout de l'id du produit
                .toUri();

        //Return de la ResponseEntity avec en paramètre l'URI
        return ResponseEntity.created(location).build();
    }
}
