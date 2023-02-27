package com.orkhan.task1.model;

import com.orkhan.task1.model.enums.EventStatus;
import com.orkhan.task1.model.enums.EventStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventCriteria {
    private String sportType;
    private EventStatus status;

    public EventCriteria(String sportType, String status) {
        this.sportType = sportType;
        //Could be added to formatterRegistry but had environmental issues
        this.status = EventStatusConverter.convert(status);
    }

    public boolean hasSportType() {
        return sportType!=null && !sportType.isEmpty();
    }
    public boolean hasStatus() {
        return status!=null;
    }
}
