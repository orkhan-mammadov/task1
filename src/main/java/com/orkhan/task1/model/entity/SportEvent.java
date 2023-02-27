package com.orkhan.task1.model.entity;

import com.orkhan.task1.model.enums.EventStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sport_event")
public class SportEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "sport_type")
    private String sportType;

    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.INACTIVE;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

}
