/*
   Copyright 2016 Cyril Delmas

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.cdelmas.microservices.perf

import scala.concurrent.duration._


class PerfSimulation extends Simulation {

  private val serviceUri = System.getenv("SERVICE_URI")

  val httpConf = http
    .baseURL(serviceUri)

  val sayHello = scenario("Get to say Hello")
    .repeat(1000) {
      exec(http("Hello on " + serviceUri)
        .acceptHeader("application/json")
        .get("/hello")
        .check(status.is(200))
      ).pause(5 millisecond)
    }

  val postMessage = scenario("Post a message")
    .repeat(1000) {
      exec(http("post message " + serviceUri)
        .header("Content-Type", "application/json")
        .post("/hello")
        .body(StringBody("{\"content\": \"Hello World 1\"}"))
        .check(status.is(201))
      ).pause(5 millisecond)
    }

  setUp(
    sayHello.inject(atOnceUsers(200)),
    postMessage.inject(atOnceUsers(200))
  )
    .assertions(global.successfulRequests.percent.greaterThan(90))
    .protocols(httpConf)
    .maxDuration(10 minutes)

}
