# Simple Eureka Server

The default port of a Eureka Server is `8761` (which is the configured default port of this Spring application).

## Enabling HTTP basic authentication

HTTP Basic authentication can be enabled by setting the `app.eureka.security.basic.enabled` to `true`. The username
and password can be configured via properties with the same prefix as well. HTTP basic is not enabled by default!

### Registration at a HTTP Basic authenticated Eureka Server

Eureka Clients support HTTP Basic authentication out-of-the-box. The credentials must only be included in the zone URL:

`eureka.client.service-url.defaultZone=http://{username}:{password}@{domain}:{port}/eureka/` 