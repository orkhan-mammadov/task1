package com.orkhan.task1.model.dto;

import com.orkhan.task1.model.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventDTO {
    private long id;
    private EventStatus status;
}
