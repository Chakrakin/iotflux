package at.codetales.iotflux.devices.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Builder
@Data
public class Device {
	@Id
	private String id;
	private String name;
}
