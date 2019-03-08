package at.codetales.iotflux.persons;

import at.codetales.iotflux.persons.handler.PersonHandler;
import at.codetales.iotflux.persons.model.Person;
import at.codetales.iotflux.persons.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { PersonEndpointConfiguration.class, PersonHandler.class })
@WebFluxTest
public class PersonHandlerTests {

	private final String testId = "1234";
	private List<Person> people = Arrays.asList(Person.builder().id(testId).name("test").build(),
		Person.builder().id("4321").name("tset").build());

	@Autowired
	WebTestClient wtc;

	@SpyBean
	PersonHandler handler;

	@MockBean
	PersonRepository repository;

	@Test
	public void shouldReturnAllPeople() {
		Mockito.when(repository.findAll()).thenReturn(Flux.fromIterable(people));
		wtc.get().uri("/peopleFN").exchange().expectStatus().isOk().expectBodyList(Person.class).hasSize(2);
		verify(handler).listAllPersons(any());
	}

	@Test
	public void shouldCreateAPersonAndReturnIt() {
		Mockito.when((repository.save(notNull()))).thenReturn(Mono.just(Person.builder().id("test").name("test").build()));
		wtc.post()
			.uri("/addPersonFN")
			.exchange()
			.expectBody(Person.class)
			.isEqualTo(Person.builder().id("test").name("test").build());
		verify(handler).addPerson(any());
	}

	@Test
	public void shouldReturnSpecificPerson() {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Mono.just(getPersonFromPeopleList(testId)));
		wtc.get()
			.uri(new StringBuilder("/peopleFN/").append(testId).toString())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(Person.class)
			.isEqualTo(getPersonFromPeopleList(testId));
		verify(handler).findById(any());
	}

	@Test
	public void shouldClearAllPeopleFromStoreAndRespondWithStatus200() {
		Mockito.when(repository.deleteAll()).thenReturn(Mono.just("p").then());
		wtc.delete().uri("/clearPeopleFN").exchange().expectStatus().is2xxSuccessful();
		verify(handler).clearPeople(any());
	}

	private Person getPersonFromPeopleList(String id) {
		return people.stream().filter(person -> person.getId().equals(id)).findFirst().orElse(null);
	}
}
