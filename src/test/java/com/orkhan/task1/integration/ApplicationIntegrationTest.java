package com.orkhan.task1.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orkhan.task1.model.dto.CreateSportEventDTO;
import com.orkhan.task1.model.dto.UpdateEventDTO;
import com.orkhan.task1.model.entity.SportEvent;
import com.orkhan.task1.model.enums.ErrorConstants;
import com.orkhan.task1.model.enums.EventStatus;
import com.orkhan.task1.repo.SportEventRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SportEventRepo repo;

    @Autowired
    private ObjectMapper objectMapper;

    private LocalDateTime defaultStartTime = LocalDateTime.of(2090, 12, 19, 18, 0, 0);

    private SportEvent event;
    private SportEvent pastEvent;

    List<SportEvent> eventList;

    @BeforeEach
    void setUp() {
        event = generateEvent(null, "type1", defaultStartTime);
        pastEvent = generateEvent(null, "type1", defaultStartTime.minusYears(100));
        eventList = generateEventList();
        repo.save(event);
        repo.save(pastEvent);
        repo.saveAll(eventList);
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    void createEvent_success() throws Exception {
        CreateSportEventDTO dto = new CreateSportEventDTO();
        dto.setName("first");
        dto.setSportType("type");
        dto.setStartTime(defaultStartTime);

        ResultActions response = mockMvc.perform(post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.name", is("first")))
                .andExpect(jsonPath("$.response.status", is("INACTIVE")));
    }

    @Test
    void createEvent_fail() throws Exception {
        CreateSportEventDTO dto = new CreateSportEventDTO();
        dto.setName("first");
        dto.setSportType("type");
        dto.setStartTime(null);

        ResultActions response = mockMvc.perform(post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getEvent_success() throws Exception {
        SportEvent se = generateEvent(null, "type", defaultStartTime);
        se = repo.save(se);
        ResultActions response = mockMvc.perform(get("/api/get?id={id}", se.getId()));

        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getEvent_fail() throws Exception {

        ResultActions response = mockMvc.perform(get("/api/get?id={id}", 100L));

        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.response", containsString("Cannot find event by id: 100")));
    }

    @Test
    @Order(1)
    void getEventList_success_filter() throws Exception {
        String type = "type2";
        EventStatus status = EventStatus.FINISHED;

        ResultActions response = mockMvc
                .perform(get("/api/list?sportType={type}&status={status}", type, status.name()));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.size()", is((int) eventList
                        .stream().filter(se -> type.equals(se.getSportType())
                                && se.getStatus() == status).count())));
    }

    @Test
    @Order(3)
    void updateStatus_fail_statusOrder() throws Exception {
        SportEvent se = generateEvent(null, "t", defaultStartTime);
        se = repo.save(se);
        UpdateEventDTO dto = new UpdateEventDTO();
        dto.setId(se.getId());
        dto.setStatus(EventStatus.FINISHED);
        ResultActions response = mockMvc.perform(post("/api/updateStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response", is(ErrorConstants.STATUS_ORDER.getMsg())));
    }

    @Test
    void updateStatus_success() throws Exception {
        SportEvent se = generateEvent(null, null, defaultStartTime);
        se = repo.save(se);
        UpdateEventDTO dto = new UpdateEventDTO();
        dto.setId(se.getId());
        dto.setStatus(EventStatus.ACTIVE);
        ResultActions response = mockMvc.perform(post("/api/updateStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.status", is("ACTIVE")));
    }

    @Test
    @Order(2)
    void getEventList_success() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/list"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.size()", is(eventList.size() + 2)));
    }


    @Test
    void updateStatus_fail_pasEvent() throws Exception {
        SportEvent se = generateEvent(null, null, defaultStartTime.minusYears(100));
        se = repo.save(se);
        UpdateEventDTO dto = new UpdateEventDTO();
        dto.setId(se.getId());
        dto.setStatus(EventStatus.ACTIVE);
        ResultActions response = mockMvc.perform(post("/api/updateStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response", is(ErrorConstants.PAST_EVENT.getMsg())));
    }

    @Test
    void getEventList_fail() throws Exception {
        String type = "type2";
        String incorrectStatus = "asd";

        ResultActions response = mockMvc
                .perform(get("/api/list?sportType={type}&status={status}", type, incorrectStatus));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response", is(ErrorConstants.INCORRECT_STATUS.getMsg())));
    }

    private List<SportEvent> generateEventList() {
        List<SportEvent> list = new ArrayList<>();
        list.add(generateEvent(EventStatus.INACTIVE, "type2", defaultStartTime));
        list.add(generateEvent(EventStatus.ACTIVE, "type2", defaultStartTime));
        list.add(generateEvent(EventStatus.FINISHED, "type2", defaultStartTime));
        list.add(generateEvent(EventStatus.ACTIVE, "type3", defaultStartTime));
        list.add(generateEvent(EventStatus.FINISHED, "type3", defaultStartTime));
        return list;
    }

    private SportEvent generateEvent(EventStatus status, String type, LocalDateTime startTime) {
        SportEvent event = new SportEvent();
        if (status != null) {
            event.setStatus(status);
        }
        event.setName("test");
        event.setSportType(type);
        event.setStartTime(startTime);
        return event;
    }

}
