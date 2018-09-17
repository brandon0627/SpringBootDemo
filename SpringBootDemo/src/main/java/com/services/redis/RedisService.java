package com.services.redis;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    void set(String key, Object value);

    Object get(String key);

    void set(String key, Object value, int number, TimeUnit timeType);
}
