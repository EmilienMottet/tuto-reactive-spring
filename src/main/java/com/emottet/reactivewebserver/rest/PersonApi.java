package com.emottet.reactivewebserver.rest;

import com.emottet.reactivewebserver.json.JsonWriter;
import com.emottet.reactivewebserver.services.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class PersonApi {
  public final RouterFunction<ServerResponse> routerFunction;
  private final PersonService personService;

  public PersonApi(PersonService personService) {
    this.personService = personService;
    this.routerFunction = RouterFunctions.route(RequestPredicates.GET(""), this::getAllPersons);
  }

  private Mono<ServerResponse> getAllPersons(ServerRequest request) {
    return this.personService.list() // returns a Flux<Person>
        .collectList()
        // turns the flux into a Mono<List<T>> to allow sending a single response
        .flatMap(JsonWriter::write).flatMap((json) -> ServerResponse.ok().body(Mono.just(json), String.class))
        .onErrorResume(JsonProcessingException.class,
            (e) -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.just(e.getMessage()), String.class));
  }
}
