package com.orkhan.task1.service.impl;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.orkhan.task1.model.enums.EventStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

class ConditionHandlerTest {

    @Test
    void isPast_true() {
        assertThat(ConditionHandler.isPast(LocalDateTime.now().minusDays(1))).isTrue();
    }

    @Test
    void isPast_false() {
        assertThat(ConditionHandler.isPast(LocalDateTime.now().plusDays(1))).isFalse();
    }

    @Test
    void isNotEditableStatus_FALSE() {
        assertThat(ConditionHandler.isNotEditableStatus(INACTIVE,ACTIVE)).isFalse();
        assertThat(ConditionHandler.isNotEditableStatus(ACTIVE,FINISHED)).isFalse();
    }

    @Test
    void isNotEditableStatus_TRUE() {
        assertThat(ConditionHandler.isNotEditableStatus(FINISHED,ACTIVE)).isTrue();
        assertThat(ConditionHandler.isNotEditableStatus(ACTIVE,INACTIVE)).isTrue();
        assertThat(ConditionHandler.isNotEditableStatus(FINISHED,INACTIVE)).isTrue();
        assertThat(ConditionHandler.isNotEditableStatus(INACTIVE,INACTIVE)).isTrue();
        assertThat(ConditionHandler.isNotEditableStatus(ACTIVE,ACTIVE)).isTrue();
        assertThat(ConditionHandler.isNotEditableStatus(FINISHED,FINISHED)).isTrue();
    }
}