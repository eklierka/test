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

package com.example.samplerestservice;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  private static final String TEMPLATE = "Hello, %s!";

  private final AtomicLong counter = new AtomicLong();

  /**
   * Endpoint greeting users.
   */
  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    String content = String.format(TEMPLATE, name);
    blah(name);
    return new Greeting(counter.incrementAndGet(), content);
  }

  // This method is added to demonstrate SonarQube analysis
  private void blah(@RequestParam(value = "name", defaultValue = "World") String name) {
    if (name.equals("World")) {
      System.out.println("Name param is not set");
    }
    if (name.equals("")) {
      // todo
    }
  }
}