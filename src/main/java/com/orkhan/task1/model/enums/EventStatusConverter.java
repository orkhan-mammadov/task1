package com.orkhan.task1.model.enums;

import com.orkhan.task1.exception.SportEventException;

public class EventStatusConverter {

    public static EventStatus convert(String text) {
        if (text != null) {
            try {
                return EventStatus.valueOf(text);
            } catch (Exception e) {
                throw new SportEventException(ErrorConstants.INCORRECT_STATUS.getMsg());
            }
        } else {
            return null;
        }
    }
}
