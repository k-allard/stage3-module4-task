package com.mjc.school.controller.impl;

import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.ServiceAuthorRequestDto;
import com.mjc.school.service.dto.ServiceAuthorResponseDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthorControllerTest {

    @MockBean
    BaseService<ServiceAuthorRequestDto, ServiceAuthorResponseDto, Long> authorService;

    @LocalServerPort
    private int port;

    @Test
    public void getAllAuthors() {
        RestAssured.given().port(port).contentType("application/json").get("/authors")
                .then()
                .body(notNullValue())
                .statusCode(200);
    }
}