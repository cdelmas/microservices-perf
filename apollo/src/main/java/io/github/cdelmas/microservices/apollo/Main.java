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
package io.github.cdelmas.microservices.apollo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.apollo.Environment;
import com.spotify.apollo.Response;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import com.spotify.apollo.route.AsyncHandler;
import com.spotify.apollo.route.JsonSerializerMiddlewares;
import com.spotify.apollo.route.Middleware;
import com.spotify.apollo.route.Route;
import com.spotify.apollo.route.SyncHandler;
import io.github.cdelmas.spike.common.Message;
import okio.ByteString;

public class Main {
    public static void main(String[] args) throws LoadingException {
        HttpService.boot(Main::init, "apollo", args);
    }

    static void init(Environment environment) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        HelloResource helloResource = new HelloResource();
        environment.routingEngine()
                .registerAutoRoute(Route.async("GET", "/apollo/hello", helloResource::hello)
                        .withMiddleware(JsonSerializerMiddlewares.jsonSerializeResponse(objectMapper.writer())));
    }

}
