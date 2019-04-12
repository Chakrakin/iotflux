## Prerequisites:
- Docker

  https://docs.docker.com/install/

- KeyCloak

  docker run -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -p 9001:8080 jboss/keycloak
  
  configured with a new realm named "dev" and a new client in dev realm
  
  plus a user with name user1 (with password set) and an assigned role "user" to the new client in dev realm
  
- MongoDB

  Navigate to project folder and execute:
  
  docker run -d -p <your wished port - suggested 27017>:27017 -v <your wished foldername - suggested ~/data>:/data/db mongo

## Run with:
  
mvn spring-boot:run

## Helping lecture:
https://docs.spring.io/spring-security/site/docs/current/reference/html/webflux-oauth2.html

http://projects.spring.io/spring-security-oauth/docs/oauth2.html

https://github.com/thomasdarimont/spring-boot-2-keycloak-oauth-example/blob/master/src/main/resources/application.yml

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



## Security relevant

Token Example:

sub="639c4ad8-4f59-41c8-8308-825c208bc38d" resource_access="{"keycloak-client-name":{"roles":["user"]},"account":{"roles":["manage-account","manage-account-links","view-profile"]}}" email_verified="false" user_name="user1" iss="http://localhost:9001/auth/realms/dev" typ="Bearer" preferred_username="user1" aud="[account]" acr="0" nbf="Thu Jan 01 01:00:00 CET 1970" realm_access="{"roles":["offline_access","uma_authorization"]}" azp="keycloak-client-name" auth_time="1555061885" scope="openid email profile" exp="Fri Apr 12 21:38:05 CEST 2019" session_state="5419202e-28da-4c33-97a0-404f24eb96ee" iat="Fri Apr 12 12:28:11 CEST 2019" jti="d16a1fbe-d391-4555-8339-e037418c127f"
