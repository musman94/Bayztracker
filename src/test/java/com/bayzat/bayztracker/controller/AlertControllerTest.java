package com.bayzat.bayztracker.controller;

import com.bayzat.bayztracker.dto.request.AddAlertRequestDto;
import com.bayzat.bayztracker.dto.request.UpdateAlertRequestDto;
import com.bayzat.bayztracker.dto.response.AlertResponse;
import com.bayzat.bayztracker.enumeration.AlertStatus;
import com.bayzat.bayztracker.service.AlertService;
import com.bayzat.bayztracker.service.AlertServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.ObjectInputFilter;

import static com.bayzat.bayztracker.constant.MessageConstants.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@IfProfileValue(name = "spring.profiles.active", values = {"dev"})
public class AlertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlertService alertService;

    private AddAlertRequestDto addAlertRequestDto;

    private UpdateAlertRequestDto updateAlertRequestDto;

    private AlertResponse alertResponse;

    @Before
    public void setup() {
        alertResponse = new AlertResponse();
        alertResponse.setId(0L);
        alertResponse.setUserId(1L);
        alertResponse.setCurrencyId(2L);
        alertResponse.setTargetValue(16.0);
        alertResponse.setStatus(AlertStatus.NEW);

        addAlertRequestDto = new AddAlertRequestDto();
        addAlertRequestDto.setUserId(1L);
        addAlertRequestDto.setCurrencyId(2L);
        addAlertRequestDto.setTargetValue(16.0);

        updateAlertRequestDto = new UpdateAlertRequestDto();
        updateAlertRequestDto.setAlertId(0L);
        updateAlertRequestDto.setCurrencyId(2L);
        updateAlertRequestDto.setTargetValue(18.0);

    }

    @Test
    @WithMockUser(username="user",roles={"NORMAL","ADMIN"})
    public void testAddAlert() throws Exception {
        given(alertService.addAlert(addAlertRequestDto)).willReturn(alertResponse);

        this.mockMvc.perform(post("/api/alert/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addAlertRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(alertResponse.getId().intValue())))
                .andExpect(jsonPath("$.data.userId", is(alertResponse.getUserId().intValue())))
                .andExpect( jsonPath("$.data.currencyId", is(alertResponse.getCurrencyId().intValue())))
                .andExpect(jsonPath("$.data.targetValue", is(alertResponse.getTargetValue())))
                .andExpect(jsonPath("$.data.status", is(alertResponse.getStatus().toString())))
                .andExpect(jsonPath("$.message", is(ADD_ALERT_SUCCESSFUL_MESSAGE)))
                .andExpect(jsonPath("$.error", is(false)));
    }

    @Test
    @WithMockUser(username="admin",roles={"NORMAL","ADMIN"})
    public void testAckAlert() throws Exception {
        Long alertId = 0L;

        alertResponse.setStatus(AlertStatus.ACKED);
        given(alertService.ackAlert(alertId)).willReturn(alertResponse);

        this.mockMvc.perform(post("/api/alert/ack").param("id", alertId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(alertResponse.getId().intValue())))
                .andExpect(jsonPath("$.data.userId", is(alertResponse.getUserId().intValue())))
                .andExpect(jsonPath("$.data.currencyId", is(alertResponse.getCurrencyId().intValue())))
                .andExpect(jsonPath("$.data.targetValue", is(alertResponse.getTargetValue())))
                .andExpect(jsonPath("$.data.status", is(alertResponse.getStatus().toString())))
                .andExpect(jsonPath("$.message", is(ACK_ALERT_SUCCESSFUL_MESSAGE)))
                .andExpect(jsonPath("$.error", is(false)));
    }

    @Test
    @WithMockUser(username="admin",roles={"NORMAL","ADMIN"})
    public void testCancelAlert() throws Exception {
        Long alertId = 0L;

        alertResponse.setStatus(AlertStatus.CANCELLED);
        given(alertService.cancelAlert(alertId)).willReturn(alertResponse);

        this.mockMvc.perform(post("/api/alert/cancel").param("id", alertId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(alertResponse.getId().intValue())))
                .andExpect(jsonPath("$.data.userId", is(alertResponse.getUserId().intValue())))
                .andExpect(jsonPath("$.data.currencyId", is(alertResponse.getCurrencyId().intValue())))
                .andExpect(jsonPath("$.data.targetValue", is(alertResponse.getTargetValue())))
                .andExpect(jsonPath("$.data.status", is(alertResponse.getStatus().toString())))
                .andExpect(jsonPath("$.message", is(CANCEL_ALERT_SUCCESSFUL_MESSAGE)))
                .andExpect(jsonPath("$.error", is(false)));
    }

    @Test
    @WithMockUser(username="user",roles={"NORMAL","ADMIN"})
    public void testUpdateAlert() throws Exception {
        given(alertService.updateAlert(updateAlertRequestDto)).willReturn(alertResponse);

        alertResponse.setTargetValue(18.0);

        this.mockMvc.perform(put("/api/alert/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateAlertRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(alertResponse.getId().intValue())))
                .andExpect(jsonPath("$.data.userId", is(alertResponse.getUserId().intValue())))
                .andExpect( jsonPath("$.data.currencyId", is(alertResponse.getCurrencyId().intValue())))
                .andExpect(jsonPath("$.data.targetValue", is(alertResponse.getTargetValue())))
                .andExpect(jsonPath("$.data.status", is(alertResponse.getStatus().toString())))
                .andExpect(jsonPath("$.message", is(UPDATE_ALERT_SUCCESSFUL_MESSAGE)))
                .andExpect(jsonPath("$.error", is(false)));
    }

    @Test
    @WithMockUser(username="admin",roles={"NORMAL","ADMIN"})
    public void testDeleteAlert() throws Exception {
        Long alertId = 0L;

        given(alertService.deleteAlert(alertId)).willReturn(alertResponse);

        this.mockMvc.perform(delete("/api/alert/delete").param("id", alertId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(alertResponse.getId().intValue())))
                .andExpect(jsonPath("$.data.userId", is(alertResponse.getUserId().intValue())))
                .andExpect(jsonPath("$.data.currencyId", is(alertResponse.getCurrencyId().intValue())))
                .andExpect(jsonPath("$.data.targetValue", is(alertResponse.getTargetValue())))
                .andExpect(jsonPath("$.data.status", is(alertResponse.getStatus().toString())))
                .andExpect(jsonPath("$.message", is(DELETE_ALERT_SUCCESSFUL_MESSAGE)))
                .andExpect(jsonPath("$.error", is(false)));
    }
}
