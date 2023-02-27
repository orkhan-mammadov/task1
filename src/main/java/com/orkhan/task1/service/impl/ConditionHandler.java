package com.orkhan.task1.service.impl;

import com.orkhan.task1.model.enums.EventStatus;

import java.time.LocalDateTime;

public class ConditionHandler {
    public static boolean isPast(LocalDateTime startTime) {
        return LocalDateTime.now().isAfter(startTime);
    }

    public static boolean isNotEditableStatus(EventStatus current, EventStatus requested) {
        return difference(current, requested) != 1;
    }

    private static int difference(EventStatus current, EventStatus requested) {
        return requested.getIndex() - current.getIndex();
    }

}
