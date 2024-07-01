package com.ox.company.crm.mapper;

import com.ox.company.crm.dto.ContactDto;
import com.ox.company.crm.dto.ContactToTasksDto;
import com.ox.company.crm.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IContactMapper {
    Contact toEntity(ContactDto dto);

    ContactDto toDto(Contact entity);

    ContactToTasksDto toContactToTasksDto(Contact entity);

    @Mapping(target = "firstName", defaultExpression = "java(entity.getFirstName())")
    @Mapping(target = "lastName", defaultExpression = "java(entity.getLastName())")
    @Mapping(target = "email", defaultExpression = "java(entity.getEmail())")
    @Mapping(target = "phone", defaultExpression = "java(entity.getPhone())")
    void update(ContactDto dto, @MappingTarget Contact entity);
}

