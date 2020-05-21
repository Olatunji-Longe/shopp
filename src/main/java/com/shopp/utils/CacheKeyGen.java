package com.shopp.utils;

import java.util.UUID;

public final class CacheKeyGen {

    public static String key(String seed){
        return UUID.nameUUIDFromBytes(seed.getBytes()).toString();
    }
}
