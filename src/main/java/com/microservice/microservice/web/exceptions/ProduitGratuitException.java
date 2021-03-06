package com.microservice.microservice.web.exceptions;

import org.springframework.http.HttpStatus;

public class ProduitGratuitException extends Exception
{
    public ProduitGratuitException(String s)
    {
        super(s);
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.BAD_REQUEST;
    }
}
