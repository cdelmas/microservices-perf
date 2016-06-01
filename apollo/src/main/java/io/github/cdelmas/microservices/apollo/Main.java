package io.github.cdelmas.microservices.apollo;

import com.spotify.apollo.Environment;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import com.spotify.apollo.route.Route;

/**
 * Created by c.delmas on 01/06/2016.
 */
public class Main {
    public static void main(String[] args) throws LoadingException {
        HttpService.boot(Main::init, "apollo", args);
    }

    static void init(Environment environment) {
        environment.routingEngine()
                .registerAutoRoute(Route.sync("GET", "/", rc -> "hello world"));
    }
}
