package io.github.cdelmas.microservices.apollo;

import java.util.concurrent.atomic.AtomicLong;

import com.spotify.apollo.RequestContext;
import io.github.cdelmas.spike.common.Message;

/**
 * Created by c.delmas on 02/06/2016.
 */
public class HelloResource {

    private final AtomicLong counter = new AtomicLong(0);

    public Message hello(RequestContext routingContext) {
        return new Message("hello world " + counter.incrementAndGet());
    }
}
