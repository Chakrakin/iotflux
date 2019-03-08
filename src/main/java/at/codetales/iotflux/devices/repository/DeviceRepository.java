package at.codetales.iotflux.devices.repository;

import at.codetales.iotflux.devices.model.Device;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends ReactiveMongoRepository<Device, String> {
}
