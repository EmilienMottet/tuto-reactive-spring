package com.emottet.reactivewebserver.services;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.emottet.reactivewebserver.Config;
import com.emottet.reactivewebserver.domain.Person;

import reactor.core.publisher.Flux;

public class PersonService {
  private final List<Person> persons;

  public PersonService() {
    this.persons = Stream.of(
        // Create new instance of Person here, as many as you wish
        new Person("id", "foo", "bar", 1)).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
  }

  public Flux<Person> list() {
    return Flux.fromIterable(this.persons).subscribeOn(Config.APPLICATION_SCHEDULER);
  }
}
