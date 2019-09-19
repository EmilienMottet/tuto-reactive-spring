package com.emottet.reactivewebserver;

import com.emottet.reactivewebserver.rest.PersonApi;
import com.emottet.reactivewebserver.services.PersonService;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.ipc.netty.http.server.HttpServer;

class Main {

  public static void main(String[] args) {
    final PersonApi personApi = new PersonApi(new PersonService());
    final RouterFunction<ServerResponse> apiRoot = RouterFunctions.nest(RequestPredicates.path("/persons"),
        personApi.routerFunction);
    final HttpHandler httpHandler = RouterFunctions.toHttpHandler(apiRoot);
    final ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);

    HttpServer.create(8080).startAndAwait(adapter);
  }
}
