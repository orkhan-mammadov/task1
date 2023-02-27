package com.orkhan.task1.controller;

import com.orkhan.task1.model.EventCriteria;
import com.orkhan.task1.model.ResponseModel;
import com.orkhan.task1.model.dto.CreateSportEventDTO;
import com.orkhan.task1.model.dto.UpdateEventDTO;
import com.orkhan.task1.service.SportEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SportEventController {

    private final SportEventService eventService;

    @PostMapping("/create")
    public ResponseEntity<ResponseModel> create(@Valid @RequestBody CreateSportEventDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseModel.builder()
                .response(eventService.create(dto))
                .build());
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseModel> getEvent(@RequestParam long id) {
        return ResponseEntity.ok(ResponseModel.builder()
                .response(eventService.getById(id))
                .build());
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseModel> getEventList(
            @RequestParam(name = "sportType", required = false) String sportType,
            @RequestParam(name = "status", required = false) String status) {
        return ResponseEntity.ok(ResponseModel.builder()
                .response(eventService.getEventList(new EventCriteria(sportType, status)))
                .build());
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<ResponseModel> updateStatus(@RequestBody UpdateEventDTO dto) {
        return ResponseEntity.ok(ResponseModel.builder()
                .response(eventService.updateStatus(dto))
                .build());
    }

}
