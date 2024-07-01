package com.ox.company.crm.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ox.company.crm.dto.ClientToContactDto;
import com.ox.company.crm.entity.CacheClient;
import com.ox.company.crm.mapper.IClientMapper;
import com.ox.company.crm.util.CacheName;
import com.ox.company.crm.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientCacheService extends CacheService {
    private final IClientMapper mapper;
    private final JsonConverter converter;

    @Autowired
    public ClientCacheService(RedisTemplate<String, Object> redisTemplate,
                              IClientMapper mapper,
                              JsonConverter converter) {
        super(redisTemplate);
        this.mapper = mapper;
        this.converter = converter;
    }

    @Override
    public void updateCachedClients(List<ClientToContactDto> clientDtos, String cacheName) {
        var dbClientIds = clientDtos.stream()
                .map(ClientToContactDto::getId)
                .toList();
        var cachedClients = getCachedClients(cacheName);

        cachedClients.removeIf(chdClient -> !dbClientIds.contains(chdClient.getId()));

        cacheClients(cachedClients);
    }

    @Override
    public void cacheClients(List<ClientToContactDto> clientDtos) {
        var clients = clientDtos.stream()
                .map(mapper::toCacheEntity)
                .toList();
        redisTemplate.opsForValue().set(CacheName.CLIENTS, converter.toJson(clients));
    }

    @Override
    public List<ClientToContactDto> getCachedClients(String cacheName) {
        var cachedClientsJson = (String) redisTemplate.opsForValue().get(cacheName);

        var cacheClients = converter.fromJson(cachedClientsJson);
        return cacheClients == null ? List.of()
                : cacheClients.stream()
                .map(mapper::toClientToContactDto)
                .toList();
    }
}
