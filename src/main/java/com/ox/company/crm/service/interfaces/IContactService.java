package com.ox.company.crm.service.interfaces;

import com.ox.company.crm.dto.ContactDto;
import com.ox.company.crm.dto.ContactToTasksDto;

import java.util.List;

public interface IContactService {
    ContactDto create(Long clientId, ContactDto dto);
    ContactDto updateById(Long id, ContactDto dto);
    void deleteById(Long id);
    List<ContactToTasksDto> getAll();
}
