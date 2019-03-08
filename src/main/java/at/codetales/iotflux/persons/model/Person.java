package at.codetales.iotflux.persons.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Builder
@Data
public class Person {
	@Id
	private String id;
	private String name;
}
