package com.ox.company.crm.dto;

import java.util.List;

public record ContactToTasksDto(Long id,
                                String firstName,
                                String lastName,
                                String email,
                                String phone,
                                List<TaskDto> tasks) {
}
