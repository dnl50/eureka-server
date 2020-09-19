# Simple Netflix Eureka Service Discovery Server

This is very a basic Spring Boot Application using [Spring Cloud Netflix][spring-cloud-netflix-ref] and
[Spring Security][spring-security-ref] under the hood.

The default port of this Spring Boot Application is `8761` (which is the default port of a Eureka Server).

## Docker Examples

After using these examples below, the Eureka Server will be accessible on port `8761`. When you execute these commands 
locally, the Eureka Server can be reached under [http://localhost:8761/](http://localhost:8761/)

**HTTP Basic enabled:**

`docker run -p 8761:8761 -d -e APP_EUREKA_SECURITY_BASIC_ENABLED=true -e APP_EUREKA_SECURITY_BASIC_USERNAME=daniel -e APP_EUREKA_SECURITY_BASIC_PASSWORD=secret --name eureka-server dnl50/eureka-server:latest`

**HTTP Basic disabled:**

`docker run -p 8761:8761 -d --name eureka-server dnl50/eureka-server:latest`

## Common Configuration Properties

Here is a list of the most important configuration properties:

| Spring Property                    | Environment Property Name          | Type    | Default Value | Description                                                            |
|------------------------------------|------------------------------------|---------|---------------|------------------------------------------------------------------------|
| app.eureka.security.basic.enabled  | APP_EUREKA_SECURITY_BASIC_ENABLED  | boolean | false         | Enables or disables HTTP basic authentication.                         |
| app.eureka.security.basic.username | APP_EUREKA_SECURITY_BASIC_USERNAME | String  | username      | The username to authenticate with. Must be at least 3 characters long. |
| app.eureka.security.basic.password | APP_EUREKA_SECURITY_BASIC_PASSWORD | String  | password      | The password to authenticate with. Must be at least 5 characters long. |

Since this application is a simple Spring Boot Application under the hood, you can also configure the underlying Tomcat 
and other Spring features using their respective properties. Take a look at  [Spring Boot's Docs][spring-boot-ext-conf-ref]
for more details. 

## Enabling HTTP basic authentication

HTTP Basic authentication can be enabled by setting the `app.eureka.security.basic.enabled` to `true`. The username
and password can be configured via properties with the same prefix as well. HTTP basic is not enabled by default.

HTTP Basic authentication only affects Eureka's API Endpoints. Other endpoints like Spring Boots Actuator Endpoint 
do not require authentication.

### Registration at a HTTP Basic authenticated Eureka Server

Eureka Clients support HTTP Basic authentication out-of-the-box. The credentials must be included in the zone URL
of the Eureka Client:

`eureka.client.service-url.defaultZone=http://{username}:{password}@{domain}:{port}/eureka/`

[spring-cloud-netflix-ref]: https://docs.spring.io/spring-cloud-netflix/docs/2.2.4.RELEASE/reference/html/
[spring-security-ref]: https://docs.spring.io/spring-security/site/docs/5.3.4.RELEASE/reference/html5/
[spring-boot-ext-conf-ref]: https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/html/spring-boot-features.html#boot-features-external-config
