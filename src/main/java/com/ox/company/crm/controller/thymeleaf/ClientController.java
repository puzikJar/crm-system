package com.ox.company.crm.controller.thymeleaf;

import com.ox.company.crm.dto.ClientDto;
import com.ox.company.crm.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/client/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new ClientDto());
        return "create-client";
    }

    @PostMapping("/client/save")
    public String saveClient(ClientDto dto) {
        var clientDto = clientService.create(dto);
        return "redirect:/contact/new?clientId=" + clientDto.getId();
//        return "string";
    }
}
