package com.shopp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@TestPropertySource("classpath:application.yml")
@SpringBootTest
@AutoConfigureMockMvc
public abstract class ControllerIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected BookController bookController;

    @Autowired
    protected ObjectMapper mapper;

}
