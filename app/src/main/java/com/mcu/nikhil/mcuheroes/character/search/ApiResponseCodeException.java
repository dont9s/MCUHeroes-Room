package com.mcu.nikhil.mcuheroes.character.search;

/**
 * Created by Mohsen on 11/11/2016.
 */

public class ApiResponseCodeException extends Exception {
    private long code;
    private String status;

    public ApiResponseCodeException(long code, String status) {
        this.code = code;
        this.status = status;
    }

    public long getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

}
