package com.ox.company.crm.service;

import com.ox.company.crm.dto.ContactDto;
import com.ox.company.crm.dto.ContactToTasksDto;
import com.ox.company.crm.entity.Client;
import com.ox.company.crm.entity.Contact;
import com.ox.company.crm.exception.ClientNotFoundException;
import com.ox.company.crm.mapper.IContactMapper;
import com.ox.company.crm.repository.IClientRepository;
import com.ox.company.crm.repository.IContactRepository;
import com.ox.company.crm.repository.ITaskRepository;
import com.ox.company.crm.service.interfaces.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class ContactService implements IContactService {

    private final IContactRepository contactRepository;
    private final IClientRepository clientRepository;
    private final ITaskRepository taskRepository;
    private final IContactMapper mapper;

    @Autowired
    public ContactService(IContactRepository contactRepository,
                          IClientRepository clientRepository,
                          ITaskRepository taskRepository,
                          IContactMapper mapper) {
        this.contactRepository = contactRepository;
        this.clientRepository = clientRepository;
        this.taskRepository = taskRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ContactToTasksDto> getAll() {
        List<Contact> contacts = contactRepository.findAllContacts();

        return contacts.stream()
                .map(mapper::toContactToTasksDto)
                .toList();
    }

    @Override
    public ContactDto create(Long clientId, ContactDto dto) {
        Client client = clientRepository
                .findClientById(clientId)
                .orElseThrow(() ->
                        new ClientNotFoundException(format("No client found by ID=", clientId)));

        Contact contact = mapper.toEntity(dto);
        contact.setClient(client);

        Contact createdContact = contactRepository.save(contact);
        return mapper.toDto(createdContact);
    }

    @Override
    public ContactDto updateById(Long id, ContactDto dto) {
        Contact existingContact = contactRepository.findContactById(id);

        mapper.update(dto, existingContact);

        var client = contactRepository.save(existingContact);
        return mapper.toDto(client);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteRelatedTasksByContactId(id);
        contactRepository.deleteById(id);
    }
}
