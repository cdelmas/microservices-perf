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
import okio.ByteString;

/**
 * Created by c.delmas on 01/06/2016.
 */
public class Main {
    public static void main(String[] args) throws LoadingException {
        HttpService.boot(Main::init, "apollo", args);
    }

    static void init(Environment environment) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        final Middleware<SyncHandler<Object>, AsyncHandler<Response<ByteString>>> jsonSerializer = JsonSerializerMiddlewares.jsonSerializeSync(objectMapper.writer());

        HelloResource helloResource = new HelloResource();
        environment.routingEngine()
                .registerAutoRoute(Route.with(jsonSerializer, "GET", "/apollo/hello", helloResource::hello));
    }

}
