package at.codetales.iotflux.devices;

import at.codetales.iotflux.devices.controller.DeviceReactiveController;
import at.codetales.iotflux.devices.model.Device;
import at.codetales.iotflux.devices.repository.DeviceRepository;
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
@WebFluxTest(DeviceReactiveController.class)
public class DeviceReactiveControllerTests {

	@Autowired
	private WebTestClient wtc;

	@MockBean
	private DeviceRepository deviceRepository;

	@Before
	public void before() {
		Mockito.when(this.deviceRepository.findAll())
			.thenReturn(Flux.just(Device.builder().id("abc1234").name("test").build()));
		Mockito.when(this.deviceRepository.save(notNull()))
			.thenReturn(Mono.just(Device.builder().id("test").name("test").build()));
		Mockito.when(this.deviceRepository.deleteAll()).thenReturn(Mono.just("d").then());
	}

	@Test
	public void listAllDevicesInStore() {
		wtc.get()
			.uri("/devices")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectHeader()
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBodyList(Device.class)
			.hasSize(1);
	}

	@Test
	public void createADeviceAndReturnItAfterwards() {
		wtc.post()
			.uri("/addDevice")
			.exchange()
			.expectBody(Device.class)
			.isEqualTo(Device.builder().id("test").name("test").build());
	}

	@Test
	public void clearAllDevicesAndRespondWithStatus200() {
		wtc.delete().uri("/clearDevices").exchange().expectStatus().is2xxSuccessful();
	}
}
