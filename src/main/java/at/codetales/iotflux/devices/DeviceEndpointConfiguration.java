package at.codetales.iotflux.devices;

import at.codetales.iotflux.devices.handler.DeviceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DeviceEndpointConfiguration {

	@Bean
	RouterFunction<ServerResponse> deviceRoutes(DeviceHandler handler) {
		return route(GET("/devicesFN"), handler::listDevices)
			.andRoute(POST("/addDeviceFN"), handler::addDevice)
			.andRoute(DELETE("/clearDevicesFN"), handler::clearDevices);
	}
}
