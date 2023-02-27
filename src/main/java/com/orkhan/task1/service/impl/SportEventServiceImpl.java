package com.orkhan.task1.service.impl;

import com.orkhan.task1.exception.SportEventException;
import com.orkhan.task1.exception.SportEventNotFoundException;
import com.orkhan.task1.mapper.EventMapper;
import com.orkhan.task1.model.EventCriteria;
import com.orkhan.task1.model.dto.CreateSportEventDTO;
import com.orkhan.task1.model.dto.UpdateEventDTO;
import com.orkhan.task1.model.entity.SportEvent;
import com.orkhan.task1.repo.EventSpecification;
import com.orkhan.task1.repo.SportEventRepo;
import com.orkhan.task1.service.SportEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.orkhan.task1.model.enums.ErrorConstants.*;

@Service
@RequiredArgsConstructor
public class SportEventServiceImpl implements SportEventService {

    private final SportEventRepo repo;

    private final EventMapper mapper;

    @Override
    public SportEvent create(CreateSportEventDTO dto) {
        return repo.save(mapper.toEntity(dto));
    }

    @Override
    public List<SportEvent> getEventList(EventCriteria criteria) {
        EventSpecification specification = new EventSpecification(criteria);
        return repo.findAll(specification);
    }

    @Override
    public SportEvent getById(long id) {
        return repo.findById(id).orElseThrow(() -> new SportEventNotFoundException(String
                .format(NOT_FOUND.getMsg(), id)));
    }

    @Override
    public SportEvent updateStatus(UpdateEventDTO dto) {
        SportEvent event = getById(dto.getId());

        if (ConditionHandler.isPast(event.getStartTime())) {
            throw new SportEventException(PAST_EVENT.getMsg());
        }
        if (ConditionHandler.isNotEditableStatus(event.getStatus(), dto.getStatus())) {
            throw new SportEventException(STATUS_ORDER.getMsg());
        }
        event.setStatus(dto.getStatus());
        return repo.save(event);
    }

}
