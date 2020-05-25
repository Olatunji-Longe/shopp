package com.shopp.services;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application.yml")
@SpringBootTest
public abstract class ServiceUnitTest {
}
