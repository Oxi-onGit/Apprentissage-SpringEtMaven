package com.microservice.microservice.model;

import com.fasterxml.jackson.annotation.JsonFilter;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Comme l'annotation @JsonIgnore l'annotation @JsonIgnoreProperties est là pour donner une indication à
// Jackson (une des LIB de Spring) pour qu'il ne tienne pas compte de certaine propriété
//@JsonIgnoreProperties(value = {"prixAchat","id"})


//Permet de filtrer les propriétés avec des conditions dynamiques
@JsonFilter("monFiltreDynamique")
public class Product
{
    private int id;
    private String nom;
    private int prix;

    //info priver
    //L'annotation @JsonIgnore permet d'ignorer la propriété en dessous de l'annotation
    //@JsonIgnore
    private int prixAchat;

    public Product() {}

    public Product(int id, String nom, int prix,int prixAchat)
    {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public int getPrix()
    {
        return prix;
    }

    public void setPrix(int prix)
    {
        this.prix = prix;
    }

    public int getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    /*
      pour retourner les donnée du produit en Json grace a Jackson qui est une LIB contenue dans Spring

      rapel  @Override singifie au compilateur que l'on réécrite le méthode
    */
    @Override
    public String toString()
    {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                '}';
    }
}
