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
package io.github.cdelmas.spike.restlet.hello;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import io.github.cdelmas.spike.common.Message;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class HelloResource extends ServerResource {

    private AtomicLong counter = new AtomicLong(0);

    @Get("json")
    public Message hello() {
        return new Message("Hello World " + counter.incrementAndGet());
    }

    @Post("json")
    public void createMessage(Message message) {
        getResponse().setStatus(Status.SUCCESS_CREATED);
        getResponse().setLocationRef(getRequest().getResourceRef().getBaseRef().addSegment(UUID.randomUUID().toString()));
    }

}
