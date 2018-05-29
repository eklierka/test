/*
 * Copyright 2018 Evgeniy Khyst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
