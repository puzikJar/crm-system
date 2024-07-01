package com.ox.company.crm.cache;

import com.ox.company.crm.dto.ClientToContactDto;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public abstract class CacheService {
    protected final RedisTemplate<String, Object> redisTemplate;

    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public abstract void updateCachedClients(List<ClientToContactDto> dbClientsIds, String cacheName);

    public abstract void cacheClients(List<ClientToContactDto> clients);

    public abstract List<ClientToContactDto> getCachedClients(String cacheName);

    public boolean cacheExist(String cacheName) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(cacheName));
    }
}
