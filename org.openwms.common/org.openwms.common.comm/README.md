# Intention

This standalone and runnable Spring Boot application is an implementation of the OSIP specification that communicates in OSIP TCP/IP message format
with subsystems underneath. Those subsystems almost lack of resources and do not provide a higher level protocol on top of TCP/IP. The
implementation is aware of multiple tenants (e.g. projects) and may run in the cloud with different port settings. Note: This does not allow
to instantiate multiple instances of the same driver component at one time. Each instance must have it's own configuration, in particular
TCP/IP port settings. A project (tenant) may have multiple drivers deployed, with all running on different ports.

# Requirements

## Functional Requirements

ID | Name | Priority | Description
--- | --- | --- | ---
FR001 | Support OSIP 1.0 | HIGH | All by OSIP defined functionality must be implemented

## Non-functional Requirements

ID | Group | Priority | Description
--- | --- | --- | ---
NR001 | Performance | HIGH | All expected responses to OSIP requests must be sent within **150 milliseconds** from message arrival.
NR002 | Scalability | MEDIUM | The component must be capable to **scale out horizontally** within a projects scope (same tenant).
NR003 | Extendability | MEDIUM | New telegram types (OSIP versions) must be integrated in a very encapsulated fashion. At best a new library can be dropped onto the classpath, at minimum  all artifacts of the new telegram implementation must be located in the same Java package without the need to touch existing other packages.

# Architecture

![Architecture](https://github.com/openwms/org.openwms/tree/%23141/org.openwms.common/org.openwms.common.comm/src/main/docs/res/integration_patterns.png)


# Glossary

