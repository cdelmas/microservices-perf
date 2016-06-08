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
package io.github.cdelmas.spike.dropwizard.hello;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import io.github.cdelmas.spike.common.Message;

@Path("/hello")
public class HelloWorldResource {
    private final AtomicLong counter = new AtomicLong(0);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Message sayHello() {
        return new Message("Hello World " + counter.incrementAndGet());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMessage(@Context UriInfo uriInfo, Message message) {
        return Response.created(uriInfo.getBaseUriBuilder().path(HelloWorldResource.class).segment(UUID.randomUUID().toString()).build()).build();
    }

}

