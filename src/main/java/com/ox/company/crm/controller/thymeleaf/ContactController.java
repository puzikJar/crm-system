package com.ox.company.crm.controller.thymeleaf;

import com.ox.company.crm.dto.ClientDto;
import com.ox.company.crm.dto.ContactDto;
import com.ox.company.crm.service.ClientService;
import com.ox.company.crm.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact/new")
    public String showCreateForm(@RequestParam("clientId") String clientId, Model model) {
        ClientDto client = clientService.getById(Long.valueOf(clientId));
        ContactDto contact = new ContactDto();
        contact.setClient(client);
        model.addAttribute("contact", contact);
        return "create-contact";
    }

    @PostMapping("/contact/save")
    public String saveContact(ContactDto dto) {
        contactService.create(dto.getClient().getId(), dto);
        return "redirect:/client/new";
    }
}
