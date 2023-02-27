package com.orkhan.task1.service.impl;

import com.orkhan.task1.exception.SportEventException;
import com.orkhan.task1.exception.SportEventNotFoundException;
import com.orkhan.task1.mapper.EventMapper;
import com.orkhan.task1.model.EventCriteria;
import com.orkhan.task1.model.dto.CreateSportEventDTO;
import com.orkhan.task1.model.dto.UpdateEventDTO;
import com.orkhan.task1.model.entity.SportEvent;
import com.orkhan.task1.model.enums.ErrorConstants;
import com.orkhan.task1.model.enums.EventStatus;
import com.orkhan.task1.repo.EventSpecification;
import com.orkhan.task1.repo.SportEventRepo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@RunWith(MockitoJUnitRunner.class)
class SportEventServiceImplTest {

    @Mock
    private SportEventRepo repo;

    @Mock
    private EventMapper mapper;


    @InjectMocks
    SportEventServiceImpl service;

    private SportEvent event;

    private List<SportEvent> eventList;

    private CreateSportEventDTO dto;


    private EventCriteria criteria;

    private long id = 1L;

    @BeforeEach
    public void setUp() {
        dto = dto();
        event = generateEvent("type1", EventStatus.INACTIVE);
        eventList = new ArrayList<>();
        criteria = new EventCriteria();
    }

    @Test
    void create() {
        when(repo.save(event)).thenReturn(event);
        when(mapper.toEntity(dto)).thenReturn(event);
        SportEvent expected = service.create(dto);
        assertThat(expected).isEqualTo(event);
        verify(mapper).toEntity(dto);
        verify(repo).save(event);
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getEventList() {
        when(repo.findAll(any(EventSpecification.class))).thenReturn(eventList);
        assertThat(service.getEventList(criteria)).isEqualTo(eventList);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getEventList_invalidValue() {
        when(repo.findAll(any(EventSpecification.class))).thenReturn(eventList);
        assertThat(service.getEventList(criteria)).isEqualTo(eventList);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getById_valid() {
        when(repo.findById(1L)).thenReturn(Optional.ofNullable(event));
        assertThat(service.getById(1L)).isEqualTo(event);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getById_invalid() {
        Exception exception = Assert.assertThrows(SportEventNotFoundException.class, () -> service.getById(100L));
        assertThat(exception.getLocalizedMessage()).isEqualTo(String.format(ErrorConstants.NOT_FOUND.getMsg(), 100L));
    }

    @Test
    void updateStatus_valid() {
        when(repo.save(event)).thenReturn(event);
        UpdateEventDTO request = new UpdateEventDTO(1L, EventStatus.ACTIVE);
        when(repo.findById(1L)).thenReturn(Optional.ofNullable(event));
        SportEvent expected = service.updateStatus(request);
        assertThat(expected.getStatus()).isEqualTo(EventStatus.ACTIVE);
    }


    @Test
    void updateStatus_invalid_startTime() {
        UpdateEventDTO request = new UpdateEventDTO(1L, EventStatus.ACTIVE);
        event.setStartTime(LocalDateTime.now().minusDays(1));
        when(repo.findById(1L)).thenReturn(Optional.ofNullable(event));
        Exception exception = Assert.assertThrows(SportEventException.class, () -> service.updateStatus(request));
        assertThat(exception.getLocalizedMessage()).isEqualTo(ErrorConstants.PAST_EVENT.getMsg());
        verifyNoMoreInteractions(repo);
    }

    @Test
    void updateStatus_invalid_statusOrder() {
        UpdateEventDTO request = new UpdateEventDTO(1L, EventStatus.INACTIVE);
        event.setStatus(EventStatus.FINISHED);
        when(repo.findById(1L)).thenReturn(Optional.ofNullable(event));
        Exception exception = Assert.assertThrows(SportEventException.class, () -> service.updateStatus(request));
        assertThat(exception.getLocalizedMessage()).isEqualTo(ErrorConstants.STATUS_ORDER.getMsg());
        verifyNoMoreInteractions(repo);
    }

    private CreateSportEventDTO dto() {
        return new CreateSportEventDTO();
    }

    private SportEvent generateEvent(String sportType, EventStatus status) {
        SportEvent temp = new SportEvent();
        temp.setId(getId());
        temp.setName("test");
        temp.setSportType(sportType);
        temp.setStatus(status);
        temp.setStartTime(LocalDateTime.of(2025, 12, 19, 18, 0, 0));
        return temp;
    }


    private long getId() {
        return id++;
    }
}