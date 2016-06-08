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
package io.github.cdelmas.spike.sparkjava;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdelmas.spike.common.Message;
import spark.Request;
import spark.Response;

public class HelloResource {

    private final AtomicLong counter = new AtomicLong(0);
    private ObjectMapper objectMapper;

    public HelloResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Message hello(Request request, Response response) {
        response.header("Content-Type", "application/json");
        return new Message("Hello World " + counter.incrementAndGet());
    }

    public Object createMessage(Request request, Response response) throws IOException {
        Message message = objectMapper.readValue(request.body(), Message.class);
        response.header("Location", request.uri().concat("/").concat(UUID.randomUUID().toString()));
        response.status(201);
        return "";
    }
}
