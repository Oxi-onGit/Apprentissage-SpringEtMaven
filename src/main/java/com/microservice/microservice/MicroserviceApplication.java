package com.microservice.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
@SpringBootApplication encapsule les trois annotations suivantes :

@Configuration : donne à la classe actuelle la possibilité de définir des
configurations qui iront remplacer les traditionnels fichiers XML.
Ces configurations se font via des Beans.

@EnableAutoConfiguration : l'annotation vue précédemment qui permet, au
démarrage de Spring, de générer automatiquement les configurations
nécessaires en fonction des dépendances situées dans notre classpath.

@ComponentScan : Indique qu'il faut scanner les classes de ce package afin de
trouver des Beans de configuration.
*/

//@EnableSwagger2 cette annotation permet d'utiliser Swagger2
//http://localhost:9090/v2/api-docs pour la doc en Json
//http://localhost:9090/swagger-ui.html pour la doc en HTML
@EnableSwagger2
@SpringBootApplication
public class MicroserviceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(MicroserviceApplication.class, args);
    }

}
/*

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(BdsServiceException.class)
    public ResponseEntity handleException(BdsServiceException e) {
        // log exception
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}

*/