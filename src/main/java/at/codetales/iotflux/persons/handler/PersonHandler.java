package at.codetales.iotflux.persons.handler;

import at.codetales.iotflux.persons.model.Person;
import at.codetales.iotflux.persons.repository.PersonRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PersonHandler {

	private final PersonRepository repository;

	public PersonHandler(PersonRepository personRepository) {
		this.repository = personRepository;
	}

	public Mono<ServerResponse> listAllPersons(ServerRequest req) {
		return ServerResponse.ok().body(repository.findAll(), Person.class);
	}

	public Mono<ServerResponse> findById(ServerRequest req) {
		return ServerResponse.ok().body(repository.findById(req.pathVariable("id")), Person.class);
	}

	public Mono<ServerResponse> addPerson(ServerRequest req) {
		return ServerResponse.ok().body(repository.save(Person.builder().name("generated").build()), Person.class);
	}

	public Mono<ServerResponse> clearPeople(ServerRequest req) {
		return ServerResponse.ok().body(repository.deleteAll(), Void.class);
	}
}
