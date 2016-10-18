= OpenWMS.org OSIP TCP/IP Driver

This standalone and runnable Spring Boot application is an implementation of the OSIP specification that communicates in OSIP TCP/IP message format
with subsystems underneath. Those subsystems almost lack of resources and do not provide a higher level protocol on top of TCP/IP. The
implementation is aware of multiple tenants (e.g. projects) and may run in the cloud with different port settings. Note: This does not allow
to instantiate multiple instances of the same driver component at one time. Each instance must have it's own configuration, in particular
TCP/IP port settings. A project (tenant) may have multiple drivers deployed, with all running on different ports.