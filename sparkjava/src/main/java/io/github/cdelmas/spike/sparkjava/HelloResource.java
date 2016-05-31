package io.github.cdelmas.spike.sparkjava;

import java.util.concurrent.atomic.AtomicLong;

import io.github.cdelmas.spike.common.Message;
import spark.Request;
import spark.Response;

public class HelloResource {

    private final AtomicLong counter = new AtomicLong(0);

    public Message hello(Request request, Response response) {
        response.header("Content-Type", "application/json");
        return new Message("Hello World " + counter.incrementAndGet());
    }

}
