package com.mcu.nikhil.core_lib.util;

public abstract class Constants {
    public static final String BASE_URL = "http://gateway.marvel.com";

    public static final String PUBLIC_KEY = "2ac9046cf8c6131a3df52142cf965b00";
    public static final String PRIVATE_KEY = "dbe6957ab0b0817977e06438c83cf7939444ded7";

    public static final int NETWORK_CONNECTION_TIMEOUT = 30; // 30 sec
    public static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    public static final int CACHE_MAX_AGE = 2; // 2 min
    public static final int CACHE_MAX_STALE = 7; // 7 day

    public static final int CODE_OK = 200;

    public static final String PORTRAIT_XLARGE = "portrait_xlarge";
    public static final String STANDARD_XLARGE = "standard_xlarge";
}
