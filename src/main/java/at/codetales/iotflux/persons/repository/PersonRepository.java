package at.codetales.iotflux.persons.repository;

import at.codetales.iotflux.persons.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {

	Mono<Person> findById(String id);
}
