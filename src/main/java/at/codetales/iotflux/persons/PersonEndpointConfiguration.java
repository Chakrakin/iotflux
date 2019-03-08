package at.codetales.iotflux.persons;

import at.codetales.iotflux.persons.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PersonEndpointConfiguration {

	@Bean
	RouterFunction<ServerResponse> peopleRoutes(PersonHandler handler) {
		return route(GET("/peopleFN"), handler::listAllPersons)
			.andRoute(GET("/peopleFN/{id}"), handler::findById)
			.andRoute(POST("/addPersonFN"), handler::addPerson)
			.andRoute(DELETE("/clearPeopleFN"), handler::clearPeople);
	}
}
