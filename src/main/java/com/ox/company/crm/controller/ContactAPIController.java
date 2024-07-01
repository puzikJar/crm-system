package com.ox.company.crm.controller;

import com.ox.company.crm.dto.ContactDto;
import com.ox.company.crm.dto.ContactToTasksDto;
import com.ox.company.crm.service.interfaces.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactAPIController {
    private final IContactService service;

    @Autowired
    public ContactAPIController(IContactService service) {
        this.service = service;
    }

    @GetMapping
    public List<ContactToTasksDto> getAll() {
        return service.getAll();
    }

    @PostMapping("/{clientId}")
    public ContactDto save(@PathVariable Long clientId, @RequestBody ContactDto contact) {
       return service.create(clientId, contact);
    }

    @PutMapping("/{id}")
    public ContactDto update(@PathVariable Long id, @RequestBody ContactDto dto) {
        return service.updateById(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
       service.deleteById(id);
    }
}
