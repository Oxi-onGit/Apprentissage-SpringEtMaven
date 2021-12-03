package com.microservice.microservice.web.exceptions;

import org.springframework.http.HttpStatus;

public class Exception extends RuntimeException
{

    public Exception(String s)
    {
        super(s);
    }

    public HttpStatus getHttpStatus()
    {
        return null;
    }
}
