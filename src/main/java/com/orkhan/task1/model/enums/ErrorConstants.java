package com.orkhan.task1.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorConstants {

    PAST_EVENT("Cannot update past event status!"),
    NOT_FOUND("Cannot find event by id: %d"),
    STATUS_ORDER("Status order must be kept! Cannot update status!"),
    INCORRECT_STATUS("Incorrect event status!");

    private final String msg;

}
