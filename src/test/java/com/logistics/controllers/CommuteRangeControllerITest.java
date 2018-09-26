package com.logistics.controllers;

import com.logistics.repository.RouteRepository;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Slf4j
public class CommuteRangeControllerITest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RouteRepository routeRepository;

    @Test
    public void commuteRangeCalculation() throws Exception {
        this.mockMvc.perform(post("/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startItemId\":1,\"rangeLimit\":11}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].name", is("C")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("B")))
                .andExpect(jsonPath("$[2].id", is(4)))
                .andExpect(jsonPath("$[2].name", is("D")))
                .andExpect(jsonPath("$[3].id", is(5)))
                .andExpect(jsonPath("$[3].name", is("E")))
                .andExpect(jsonPath("$[4].id", is(6)))
                .andExpect(jsonPath("$[4].name", is("F")));

        this.mockMvc.perform(post("/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startItemId\":6,\"rangeLimit\":9}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[0]name", is("D")))
                .andExpect(jsonPath("$[1].id", is(7)))
                .andExpect(jsonPath("$[1].name", is("G")))
                .andExpect(jsonPath("$[2].id", is(5)))
                .andExpect(jsonPath("$[2].name", is("E")))
                .andExpect(jsonPath("$[3].id", is(2)))
                .andExpect(jsonPath("$[3].name", is("B")));
    }

    @Test
    public void commuteRangeCalculationWithError() throws Exception {
        this.mockMvc.perform(post("/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startItemId\":99,\"rangeLimit\":11}"))
                .andExpect(status().isBadRequest());
    }

}
