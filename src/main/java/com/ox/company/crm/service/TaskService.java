package com.ox.company.crm.service;

import com.ox.company.crm.dto.TaskDto;
import com.ox.company.crm.entity.Contact;
import com.ox.company.crm.entity.Task;
import com.ox.company.crm.event.EventType;
import com.ox.company.crm.event.TaskAssignEvent;
import com.ox.company.crm.mapper.ITaskMapper;
import com.ox.company.crm.repository.IContactRepository;
import com.ox.company.crm.repository.ITaskRepository;
import com.ox.company.crm.service.interfaces.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService {
    private final ApplicationEventPublisher eventPublisher;
    private final IContactRepository contactRepository;
    private final ITaskRepository taskRepository;
    private final ITaskMapper mapper;

    @Autowired
    public TaskService(IContactRepository contactRepository,
                       ITaskRepository taskRepository,
                       ITaskMapper mapper,
                       ApplicationEventPublisher eventPublisher) {
        this.contactRepository = contactRepository;
        this.taskRepository = taskRepository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<TaskDto> getAll() {
        List<Task> clients = taskRepository.findAll();

        return clients.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public TaskDto create(Long contactId, TaskDto dto) {
        Contact contact = contactRepository.findContactById(contactId);
        Task task = mapper.toEntity(dto);

        contact.addTask(task);

        Task createdTask = taskRepository.save(task);

        eventPublisher.publishEvent(new TaskAssignEvent(
                this,
                createdTask.getId(),
                createdTask.getDueDate(),
                EventType.TASK_ATTACHED_EVENT,
                contact.getFirstName()));
        return mapper.toDto(createdTask);
    }

    @Override
    public TaskDto updateById(Long id, TaskDto dto) {
        Task existingTask = taskRepository.findTaskById(id);

        mapper.update(dto, existingTask);

        var task = taskRepository.save(existingTask);
        return mapper.toDto(task);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
