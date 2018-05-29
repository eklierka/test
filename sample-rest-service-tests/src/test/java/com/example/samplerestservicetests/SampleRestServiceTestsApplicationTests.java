package com.example.samplerestservicetests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

public class SampleRestServiceTestsApplicationTests {

  private static RequestSpecification spec;

  @BeforeClass
  public static void initSpec() {
    spec = new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .setBaseUri(System.getProperty("sampleRestService.baseUri"))
        .addFilter(new ResponseLoggingFilter())
        .addFilter(new RequestLoggingFilter())
        .build();
  }

  @Test
  public void shouldReturnDefaultMessage() {
    String content = given()
        .spec(spec)
        .when()
        .get("/greeting")
        .then()
        .statusCode(200)
        .extract().path("content");
    assertThat(content, is("Hello, World!"));
  }

  @Test
  public void shouldReturnMessageWithName() {
    String content = given()
        .spec(spec)
        .param("name", "User")
        .when()
        .get("/greeting")
        .then()
        .statusCode(200)
        .extract().path("content");
    assertThat(content, is("Hello, User!"));
  }
}
