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
package io.github.cdelmas.spike.vertx.hello;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import io.github.cdelmas.spike.common.Message;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class HelloResource {

    private final AtomicLong counter = new AtomicLong(0);

    public void hello(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("Content-Type", "application/json")
                .end(Json.encode(new Message("Hello World " + counter.incrementAndGet())));
    }

    public void createMessage(RoutingContext routingContext) {
        final Message message = Json.decodeValue(routingContext.getBody().toString(StandardCharsets.UTF_8), Message.class);
        routingContext.response()
                .putHeader("Location", routingContext.request().absoluteURI() + "/" + UUID.randomUUID().toString())
                .setStatusCode(HttpResponseStatus.CREATED.code())
                .end();
    }
}
