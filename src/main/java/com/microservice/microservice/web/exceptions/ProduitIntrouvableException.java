package com.microservice.microservice.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//L’annotation permet que Spring revoie le bon code HTML dans ce cas 404
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProduitIntrouvableException extends Exception
{
    //Cette méthode fait appel au constructeur de la class parent pour créer une exception avec le message passer en paramètre
    public ProduitIntrouvableException(String s)
    {
        super(s);
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.BAD_REQUEST;
    }
}
