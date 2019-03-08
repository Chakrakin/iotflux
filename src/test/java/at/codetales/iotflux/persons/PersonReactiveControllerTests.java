package at.codetales.iotflux.persons;

import at.codetales.iotflux.persons.controller.PersonReactiveController;
import at.codetales.iotflux.persons.model.Person;
import at.codetales.iotflux.persons.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.notNull;

@RunWith(SpringRunner.class)
@WebFluxTest(PersonReactiveController.class)
public class PersonReactiveControllerTests {

	@Autowired
	private WebTestClient wtc;

	@MockBean
	private PersonRepository personRepository;

	@Before
	public void before() {
		Mockito.when(this.personRepository.findAll())
			.thenReturn(Flux.just(Person.builder().id("abcd1234").name("test").build()));
		Mockito.when(this.personRepository.save(notNull()))
			.thenReturn(Mono.just(Person.builder().id("test").name("test").build()));
		Mockito.when(this.personRepository.deleteAll()).thenReturn(Mono.just("p").then());
	}

	@Test
	public void listAllPeopleInStore() {
		wtc.get()
			.uri("/people")
			.exchange()
			.expectHeader()
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBodyList(Person.class)
			.hasSize(1);
	}

	@Test
	public void createAPersonAndReturnItAfterwards() {
		wtc.post()
			.uri("/addPerson")
			.exchange()
			.expectBody(Person.class)
			.isEqualTo(Person.builder().id("test").name("test").build());
	}

	@Test
	public void clearAllPeopleAndRespondWithStatus200() {
		wtc.delete().uri("/clearPeople").exchange().expectStatus().is2xxSuccessful();
	}
}
