package com.ox.company.crm.event;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDate;

public class TaskAssignEvent extends ApplicationEvent {
    private final Long taskId;
    private final LocalDate dueDate;
    private final EventType type;
    private final String contactName;

    public TaskAssignEvent(Object source,
                           Long taskId,
                           LocalDate dueDate,
                           EventType type,
                           String contactName) {
        super(source);
        this.taskId = taskId;
        this.dueDate = dueDate;
        this.type = type;
        this.contactName = contactName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public EventType getType() {
        return type;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getContactName() {
        return contactName;
    }
}
