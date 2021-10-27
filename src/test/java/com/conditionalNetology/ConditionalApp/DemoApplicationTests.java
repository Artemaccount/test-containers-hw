package com.conditionalNetology.ConditionalApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    private static int portDev = 8080;
    private static int portProd = 8081;
    @Autowired
    TestRestTemplate restTemplate;
    public static GenericContainer<?> devapp = new GenericContainer<>("devapp").withExposedPorts(portDev);
    public static GenericContainer<?> prodapp = new GenericContainer<>("prodapp").withExposedPorts(portProd);

    @BeforeAll
    public static void setUp() {
        devapp.start();
        prodapp.start();
    }

    @Test
    void contextLoadsDevapp() {
        final String expected = "Current profile is dev";
        String url = String.format("http://localhost:%d/profile",devapp.getMappedPort(portDev));
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(expected, forEntity.getBody());
    }
    @Test
    void contextLoadsProdapp() {
        final String expected = "Current profile is production";
        String url = String.format("http://localhost:%d/profile",prodapp.getMappedPort(portProd));
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(expected, forEntity.getBody());
    }

}