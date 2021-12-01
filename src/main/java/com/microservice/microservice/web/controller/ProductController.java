package com.microservice.microservice.web.controller;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.microservice.microservice.dao.ProductDao;
import com.microservice.microservice.model.Product;
import com.microservice.microservice.web.exceptions.ProduitIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
        //Avant JPA findAll étais définis dans la class ProductDaoImpl mais avec JPA l’interface ProductDao
        // hérite de JpaRepository et contient donc déjà une méthode findall
        List<Product> produits = productDao.findAll();
        /*
            Une sélection des autres méthodes de JpaRepository en plus de findall :
            -delete(int id) : supprime le produit correspondant à l'id passée en argument

            -count() : calcule le nombre de produits

            -save(Product produit) : ajoute le produit  passé en argument.
            Cette méthode peut également recevoir un Iterable<> de produits pour ajouter plusieurs produits.

             la liste complète ici : https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
        */

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
    //(@PathVariable) Permet que Spring de récupère le paramètre dans L’URI
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id)
    {
        Product produit = productDao.findById(id);

        if(produit==null)
        {
            throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE");
        }

        return produit;
    }

    //(@GetMapping)Même utilité que @RequestMapping mais directement pour GET
    //(@PathVariable) Permet que Spring de récupère le paramètre dans L’URI
    @GetMapping(value = "test/produits/{recherche}")
    public List<Product> testeDeRequetes2(@PathVariable String recherche)
    {
        return productDao.findByNomLike("%"+recherche+"%");
    }

    //(@GetMapping)Même utilité que @RequestMapping mais directement pour GET
    //(@RequestParam(value="prixLimit")) Permet que Spring passe en paramètre de la méthode le paramètre de la requête avec le nom prixLimit dans notre cas
    @GetMapping(value = "test/produits")
    public List<Product> testeDeRequetes1(@RequestParam(value="prixLimit") int prixLimit)
    {
        return productDao.findByPrixGreaterThan(400);
    }

    //Tester avec Postman
    //(@PostMapping)Même utilité que @RequestMapping mais directement pour  POST
    //L'annotation @RequestBody est là pour convertir les données contenues dans le corps de la requête du format JSON en objet Java
    //ResponseEntity hérite de HttpEntity pour permettre de personnaliser personnaliser le code html à retourner
    //(@Valid)Permet de vérifier qui qui le produit passer en paramètre est valide
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product)
    {
       //La méthode Save fait partie des méthodes de SpringData donc même sans l'implémentation crée la méthode fonctionne
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

    //(@DeleteMapping)Même utilité que @RequestMapping mais directement pour Delete
    //(@PathVariable) Permet que Spring de récupère le paramètre dans L’URI
    @DeleteMapping (value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id)
    {
        //La méthode deleteById fait partie des méthodes de SpringData donc même sans l'implémentation crée la méthode fonctionne
        //Comme son nom l'indique cette méthode supprime une entre de la BDD en fonction de l'id passer en paramètre
        productDao.deleteById(id);
    }

    //(@PutMapping)Même utilité que @RequestMapping mais directement pour Put
    //L'annotation @RequestBody est là pour convertir les données contenues dans le corps de la requête du format JSON en objet Java
    @PutMapping (value = "/Produits")
    public void updateProduit(@Valid @RequestBody Product product) {

        //La méthode save fait partie des méthodes de SpringData donc même sans l'implémentation crée la méthode fonctionne
        productDao.save(product);
    }

    //(@PostMapping)Même utilité que @RequestMapping mais directement pour Post
    //(@PathVariable) Permet que Spring de récupère le paramètre dans L’URI
    @PostMapping(value = "/Produits/{prix}")
    public List<Product> chercherUnProduitCher(@PathVariable int prix)
    {
       return productDao.chercherUnProduitCher(prix);
    }
}
