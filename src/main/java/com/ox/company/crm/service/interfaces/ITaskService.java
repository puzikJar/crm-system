package com.ox.company.crm.service.interfaces;

import com.ox.company.crm.dto.TaskDto;

import java.util.List;

public interface ITaskService {
    TaskDto create(Long contactId, TaskDto dto);
    TaskDto updateById(Long id, TaskDto dto);
    void deleteById(Long id);
    List<TaskDto> getAll();
}
