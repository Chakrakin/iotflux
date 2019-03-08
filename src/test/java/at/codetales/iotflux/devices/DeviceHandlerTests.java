package at.codetales.iotflux.devices;

import at.codetales.iotflux.devices.handler.DeviceHandler;
import at.codetales.iotflux.devices.model.Device;
import at.codetales.iotflux.devices.repository.DeviceRepository;
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
@ContextConfiguration(classes = { DeviceEndpointConfiguration.class, DeviceHandler.class })
@WebFluxTest
public class DeviceHandlerTests {

	@Autowired
	private WebTestClient wtc;

	@SpyBean
	private DeviceHandler handler; // spyBean to check if method was called

	@MockBean
	private DeviceRepository repository;

	private List<Device> devices = Arrays.asList(Device.builder().id("1234").name("test").build(),
		Device.builder().id("4321").name("tset").build());

	@Test
	public void shouldReturnAllDevices() {
		Mockito.when(this.repository.findAll()).thenReturn(Flux.fromIterable(devices));
		wtc.get().uri("/devicesFN").exchange().expectStatus().isOk().expectBodyList(Device.class).hasSize(2);
		// check if method is called
		verify(handler).listDevices(any());
	}

	@Test
	public void shouldCreateADeviceAndReturnIt() {
		Mockito.when(this.repository.save(notNull()))
			.thenReturn(Mono.just(Device.builder().id("test").name("test").build()));
		wtc.post()
			.uri("/addDeviceFN")
			.exchange()
			.expectBody(Device.class)
			.isEqualTo(Device.builder().id("test").name("test").build());
		verify(handler).addDevice(any());
	}

	@Test
	public void shouldClearAllDevicesFromStoreAndRespondWithStatus200() {
		Mockito.when(this.repository.deleteAll()).thenReturn(Mono.just("d").then());
		wtc.delete().uri("/clearDevicesFN").exchange().expectStatus().is2xxSuccessful();
		verify(handler).clearDevices(any());
	}
}
