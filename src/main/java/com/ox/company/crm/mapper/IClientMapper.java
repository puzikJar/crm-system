package com.ox.company.crm.mapper;

import com.ox.company.crm.dto.ClientDto;
import com.ox.company.crm.dto.ClientToContactDto;
import com.ox.company.crm.entity.CacheClient;
import com.ox.company.crm.entity.Client;
import com.ox.company.crm.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IClientMapper {
    Client toEntity(ClientDto dto);

    CacheClient toCacheEntity(ClientToContactDto dto);

    ClientDto toDto(Client entity);

    ClientToContactDto toClientToContactDto(CacheClient dto);

    ClientToContactDto toClientToContactDto(Client entity);

    @Mapping(target = "name", defaultExpression = "java(entity.getName())")
    @Mapping(target = "branch", defaultExpression = "java(entity.getBranch())")
    @Mapping(target = "address", defaultExpression = "java(entity.getAddress())")
    void update(ClientDto dto, @MappingTarget Client entity);
}
