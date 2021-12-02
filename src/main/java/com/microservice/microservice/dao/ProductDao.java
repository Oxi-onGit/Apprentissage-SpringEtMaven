package com.microservice.microservice.dao;

import com.microservice.microservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface ProductDao extends JpaRepository<Product, Integer>
{
    //Spring a créé une requête SQL en fonction du nom de la méthode
    //doc ici : https://docs.spring.io/spring-data/data-jpa/docs/1.1.x/reference/html/#jpa.query-methods.query-creation
    Product findById(int id);

    //return les produit en fonction de leur prix
    /*
        logique du nom de la méthode
        findBy : indique que l'opération à exécuter est un SELECT ;

        Prix : fournit le nom de la propriété sur laquelle le SELECT s'applique

        GreaterThan : définit une condition "plus grand que"
    */
    List<Product> findByPrixGreaterThan(int prixLimit);

    //idem que la méthode ci-dessus
    List<Product> findByNomLike(String recherche);

    //L'annotation @Query permet de créer des requête custom
    @Query("SELECT id, nom, prix FROM Product p WHERE p.prix > :prix")
    List<Product> chercherUnProduitCher(@Param("prix") int prixLimit);

    //--------------------- Début tuto libre ---------------------
    //Comme son nom l'indique cette méthode retour tous les produits trier par leur nom
    List<Product> findAllByOrderByNom();
}


/*
public interface ProductDao
{
    public List<Product> findAll();
    public Product findById(int id);
    public Product save(Product product);
}*/
