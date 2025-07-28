package com.jake.weather.exception;

import java.io.Serial;

public class ApiException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;

    public ApiException( String fallbackFailed ) {
        super( fallbackFailed );
    }
}