package com.microservice.microservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//@Configuration l'annotation permet de faire de cette class comfigure Swagger
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    //Personnalise les Swagger de L’API
    ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title("Une API")
                .description("API pour les opérations CRUD sur les produits.")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .build();
    }


    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)// initialiser un objet Docket
                .select()//initialiser une classe du nom de ApiSelectorBuilder qui donne accès aux méthodes de personnalisation
                .apis(RequestHandlerSelectors.basePackage("com.microservice.microservice.web"))//(.apis)Permet de filtre des parties du code
                /*
                    RequestHandlerSelectors offre plusieurs autres méthodes
                    comme annotationPresent qui vous permet de définir
                    une annotation en paramètre. Swagger ne documentera alors que les classes
                    qu'il utilise.
                    La plus utilisée est basePackage qui permet de trier selon le Package

                    Nous utilisons la méthode basePackage du prédicat RequestHandlerSelectors
                    afin de demander à Swagger de ne rien documenter en dehors du package "web"
                */
                .paths(PathSelectors.regex("/Produits.*")) //Cette méthode donne accès à une autre façon de filtrer : selon l'URI des requêtes. Ainsi, vous pouvez, par exemple, demander à Swagger de ne documenter que les méthodes qui répondent à des requêtes commençant par "/public" .
                /*
                    PathSelectors.regex("/Produits.*")
                    permet de passer une expression régulière
                    qui n'accepte que les URI commençant par /Produits.
                */
                .build()
                .apiInfo(apiInfo()); //Permet de prendre en compte la modification des infos de l'API
    }
}