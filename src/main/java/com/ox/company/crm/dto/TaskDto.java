package com.ox.company.crm.dto;

import com.ox.company.crm.entity.Status;

import java.time.LocalDate;

public record TaskDto(Long id,
                      String description,
                      Status status,
                      LocalDate dueDate) {
}
