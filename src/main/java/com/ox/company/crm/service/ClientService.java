package com.ox.company.crm.service;

import com.ox.company.crm.cache.CacheService;
import com.ox.company.crm.dto.ClientDto;
import com.ox.company.crm.dto.ClientToContactDto;
import com.ox.company.crm.entity.Client;
import com.ox.company.crm.exception.ClientNotFoundException;
import com.ox.company.crm.mapper.IClientMapper;
import com.ox.company.crm.repository.IClientRepository;
import com.ox.company.crm.service.interfaces.IClientService;
import com.ox.company.crm.util.CacheName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class ClientService implements IClientService {
    private final IClientRepository clientRepository;
    private final CacheService cacheService;
    private final IClientMapper mapper;

    @Autowired
    public ClientService(IClientRepository clientRepository,
                         CacheService cacheService,
                         IClientMapper mapper) {
        this.clientRepository = clientRepository;
        this.cacheService = cacheService;
        this.mapper = mapper;
    }


    @Override
    public ClientDto getById(Long id) {
        return clientRepository.findClientById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ClientNotFoundException(format("No client found by ID=", id)));
    }

    @Override
    public List<ClientToContactDto> getAll() {
        if (cacheService.cacheExist(CacheName.CLIENTS)) {
            return cacheService.getCachedClients(CacheName.CLIENTS);
        }

        var clients = clientRepository.findAll();

        var clientDtos = clients.stream()
                .map(mapper::toClientToContactDto)
                .toList();
        cacheService.cacheClients(clientDtos);
        return clientDtos;
    }

    @Override
    public ClientDto create(ClientDto dto) {
        var entity = mapper.toEntity(dto);

        var client = clientRepository.save(entity);

        return mapper.toDto(client);
    }

    @Override
    public ClientDto updateById(Long id, ClientDto dto) {
        Client existing = clientRepository.findClientById(id)
                .orElseThrow(() ->
                        new ClientNotFoundException(format("No client found by ID=", id)));

        mapper.update(dto, existing);

        var client = clientRepository.save(existing);
        return mapper.toDto(client);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);

        var clients = clientRepository.findAll();
        var clientDtos = clients.stream()
                .map(mapper::toClientToContactDto)
                .toList();
        cacheService.updateCachedClients(clientDtos, CacheName.CLIENTS);
    }
}
