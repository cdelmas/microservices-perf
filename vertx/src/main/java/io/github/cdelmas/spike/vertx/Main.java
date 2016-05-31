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
package io.github.cdelmas.spike.vertx;

import com.fasterxml.jackson.databind.DeserializationFeature;
import io.github.cdelmas.spike.vertx.hello.HelloResource;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;

public class Main {

    public static void main(String[] args) {

        Json.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);

        HelloResource helloResource = new HelloResource();
        router.get("/vertx/hello").produces("application/json").handler(helloResource::hello);

        HttpServerOptions serverOptions = new HttpServerOptions()
                .setPort(8085);
        HttpServer server = vertx.createHttpServer(serverOptions);
        server.requestHandler(router::accept).listen();
    }
}
