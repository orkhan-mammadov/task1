package com.orkhan.task1.service;

import com.orkhan.task1.model.dto.CreateSportEventDTO;
import com.orkhan.task1.model.dto.UpdateEventDTO;
import com.orkhan.task1.model.entity.SportEvent;
import com.orkhan.task1.model.EventCriteria;

import java.util.List;

public interface SportEventService {
    SportEvent create(CreateSportEventDTO dto);

    List<SportEvent> getEventList(EventCriteria criteria);

    SportEvent getById(long id);

    SportEvent updateStatus(UpdateEventDTO dto);
}
