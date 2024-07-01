package com.ox.company.crm.controller;

import com.ox.company.crm.dto.TaskDto;
import com.ox.company.crm.service.interfaces.ITaskService;
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
@RequestMapping("/tasks")
public class TaskController {
    private final ITaskService service;

    @Autowired
    public TaskController(ITaskService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    public TaskDto save(@PathVariable Long id, @RequestBody TaskDto task) {
        return service.create(id, task);
    }

    @PutMapping("/{id}")
    public TaskDto update(@PathVariable Long id, @RequestBody TaskDto dto) {
        return service.updateById(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping
    public List<TaskDto> getAll() {
        return service.getAll();
    }

}
