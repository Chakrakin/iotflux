package at.codetales.iotflux.persons.controller;

import at.codetales.iotflux.persons.model.Person;
import at.codetales.iotflux.persons.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@RestController
public class PersonReactiveController {

	@Autowired
	private PersonRepository personRepository;

	@GetMapping(path = "/people", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Flux<Person> persons() {
		return personRepository.findAll();
	}

	@PostMapping(path = "/addPerson")
	public Mono<Person> addPerson() {
		return personRepository.save(Person.builder().id(String.valueOf(new Random().nextInt())).name("test").build());
	}

	@DeleteMapping(path = "/clearPeople")
	public Mono<Void> clearPersons() {
		return personRepository.deleteAll().then();
	}
}
