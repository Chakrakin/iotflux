package at.codetales.iotflux.devices.handler;

import at.codetales.iotflux.devices.model.Device;
import at.codetales.iotflux.devices.repository.DeviceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Random;

@Log4j2
@Component
public class DeviceHandler {

	private final DeviceRepository repository;

	public DeviceHandler(DeviceRepository deviceRepository) {
		this.repository = deviceRepository;
	}

	public Mono<ServerResponse> listDevices(ServerRequest req) {
		log.info("list devices called");
		return ServerResponse.ok().body(repository.findAll(), Device.class);
	}

	public Mono<ServerResponse> addDevice(ServerRequest req) {
		log.info("attempt to add device");
		return ServerResponse.ok().body(repository.save(Device.builder().name(String.valueOf(new Random().nextInt())).build()), Device.class);
	}

	public Mono<ServerResponse> clearDevices(ServerRequest req) {
		log.info("clearing all devices");
		return ServerResponse.ok().body(repository.deleteAll(), Void.class);
	}
}
