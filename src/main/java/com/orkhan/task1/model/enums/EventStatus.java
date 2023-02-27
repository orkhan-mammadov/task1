package com.orkhan.task1.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum EventStatus {
    INACTIVE(0),
    ACTIVE(1),
    FINISHED(2);

    private final int index;

}
