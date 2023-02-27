package com.orkhan.task1.model.enums;

import com.orkhan.task1.exception.SportEventException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class EventStatusConverterTest {

    @Test
    void convert_success() {
        EventStatus active = EventStatusConverter.convert("ACTIVE");
        EventStatus inactive = EventStatusConverter.convert("INACTIVE");
        EventStatus finished = EventStatusConverter.convert("FINISHED");
        assertThat(active).isEqualTo(EventStatus.ACTIVE);
        assertThat(inactive).isEqualTo(EventStatus.INACTIVE);
        assertThat(finished).isEqualTo(EventStatus.FINISHED);
    }

    @Test
    void convert_fail() {
        Exception exception = Assert.assertThrows(SportEventException.class,
                () -> EventStatusConverter.convert(""));
        assertThat(exception.getLocalizedMessage()).isEqualTo(ErrorConstants.INCORRECT_STATUS.getMsg());
    }
}