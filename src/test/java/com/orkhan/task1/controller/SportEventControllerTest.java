package com.orkhan.task1.controller;

import com.orkhan.task1.model.ResponseModel;
import com.orkhan.task1.model.dto.CreateSportEventDTO;
import com.orkhan.task1.model.dto.UpdateEventDTO;
import com.orkhan.task1.model.entity.SportEvent;
import com.orkhan.task1.service.SportEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class SportEventControllerTest {

    @Mock
    private SportEventService service;

    @InjectMocks
    private SportEventController controller;

    private CreateSportEventDTO createDto;

    private UpdateEventDTO updateDto;

    private SportEvent event;
    @BeforeEach
    void setUp() {
        createDto = new CreateSportEventDTO();
        event = new SportEvent();
        updateDto = new UpdateEventDTO();
    }

    @Test
    void create() {
        when(service.create(createDto)).thenReturn(event);
        ResponseEntity<ResponseModel> response = controller.create(createDto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(Objects.requireNonNull(response.getBody()).getResponse()).isEqualTo(event);
    }

    @Test
    void getEvent() {
        when(service.getById(1L)).thenReturn(event);
        ResponseEntity<ResponseModel> response = controller.getEvent(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(Objects.requireNonNull(response.getBody()).getResponse()).isEqualTo(event);
    }

    @Test
    void getEventList_success() {
        when(service.getEventList(any())).thenReturn(Collections.singletonList(event));
        ResponseEntity<ResponseModel> response = controller.getEventList(any(),"INACTIVE");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(Objects.requireNonNull(response.getBody()).getResponse()).isEqualTo(Collections.singletonList(event));
    }

    @Test
    void updateStatus() {
        when(service.updateStatus(updateDto)).thenReturn(event);
        ResponseEntity<ResponseModel> response = controller.updateStatus(updateDto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(Objects.requireNonNull(response.getBody()).getResponse()).isEqualTo(event);
    }
}