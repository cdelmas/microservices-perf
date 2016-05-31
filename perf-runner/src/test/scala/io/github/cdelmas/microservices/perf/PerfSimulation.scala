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

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._


class PerfSimulation extends Simulation {

  private val serviceUri = System.getenv("SERVICE_URI")

  val httpConf = http
    .baseURL(serviceUri)
    .acceptHeader("application/json")

  val scn = scenario("Get to say Hello")
    .repeat(1000) {
      exec(http("Hello on " + serviceUri)
        .get("/hello")
        .check(status.is(200))
      ).pause(5 millisecond)
    }

  setUp(scn.inject(atOnceUsers(200)))
    .assertions(global.successfulRequests.percent.greaterThan(90))
    .protocols(httpConf)
    .maxDuration(5 minutes)

}
