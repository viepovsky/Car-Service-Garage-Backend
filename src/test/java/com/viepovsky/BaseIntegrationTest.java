package com.viepovsky;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

import com.viepovsky.utility.scheduler.ApplicationScheduler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockBean(ApplicationScheduler.class)
@DisplayName("Integration Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = "classpath:init-admin.sql", executionPhase = BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:init-vehicle-integration.sql", executionPhase = BEFORE_TEST_CLASS)
public abstract class BaseIntegrationTest {}
