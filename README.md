Example TDD with Spring Webflux

## Prerequisites:
- Docker

  https://docs.docker.com/install/
  
- MongoDB

  Navigate to project folder and execute:
  
  docker run -d -p <your wished port - suggested 27017>:27017 -v <your wished foldername - suggested ~/data>:/data/db mongo

## Run with:
  
mvn spring-boot:run

## Paths:

##### Via RestController:

list devices: http://localhost:8080/devices

add a device: http://localhost:8080/addDevice

delete all devices: http://localhost:8080/clearDevices

list people: http://localhost:8080/people

add a person: http://localhost:8080/addPerson

delete all people: http://localhost:8080/clearPeople


##### Via RouterFunction:

list devices: http://localhost:8080/devicesFN

add a device: http://localhost:8080/addDeviceFN

delete all devices: http://localhost:8080/clearDevicesFN

list people: http://localhost:8080/peopleFN

via person detail: http://localhost:8080/peopleFN/{id}

add a person: http://localhost:8080/addPersonFN

delete all people: http://localhost:8080/clearPeopleFN

