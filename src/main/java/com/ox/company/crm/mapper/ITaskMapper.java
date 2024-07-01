package com.ox.company.crm.mapper;

import com.ox.company.crm.dto.TaskDto;
import com.ox.company.crm.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITaskMapper {
    Task toEntity(TaskDto dto);

    TaskDto toDto(Task entity);

    @Mapping(target = "description", defaultExpression = "java(entity.getDescription())")
    @Mapping(target = "status", defaultExpression = "java(entity.getStatus())")
    @Mapping(target = "dueDate", defaultExpression = "java(entity.getDueDate())")
    void update(TaskDto dto, @MappingTarget Task entity);
}
