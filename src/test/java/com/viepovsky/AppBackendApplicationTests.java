package com.viepovsky;

import com.viepovsky.scheduler.ApplicationScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
@TestPropertySource(locations = "classpath:application.properties")
class AppBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
