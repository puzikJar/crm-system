package com.ox.company.crm.service.interfaces;

import com.ox.company.crm.dto.ClientDto;
import com.ox.company.crm.dto.ClientToContactDto;

import java.util.List;

public interface IClientService {
    ClientDto create(ClientDto dto);
    ClientDto updateById(Long id, ClientDto dto);
    void deleteById(Long id);
    ClientDto getById(Long id);
    List<ClientToContactDto> getAll();

}
