package at.codetales.iotflux.devices.controller;

import at.codetales.iotflux.devices.model.Device;
import at.codetales.iotflux.devices.repository.DeviceRepository;
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
public class DeviceReactiveController {

	@Autowired
	private DeviceRepository deviceRepository;

	@GetMapping(path = "/devices", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Flux<Device> devices() {
		return deviceRepository.findAll();
	}

	@PostMapping(path = "/addDevice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Mono<Device> addDevice() {
		return deviceRepository.save(Device.builder().id(String.valueOf(new Random().nextInt())).name("test").build());
	}

	@DeleteMapping(path = "/clearDevices")
	public Mono<Void> clearDevices() {
		return deviceRepository.deleteAll().then();
	}
}
