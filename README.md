org.openwms
=====================

Is a free to use and extend software Warehouse Management System to control 

# Resources

Wiki at [Atlassian Confluence](https://openwms.atlassian.net/wiki/display/OPENWMS)

[![Build status][travis-image]][travis-url]
[![License][license-image]][license-url]
[![Quality][codacy-image]][codacy-url]
[![Join the chat at https://gitter.im/openwms/org.openwms](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/openwms/org.openwms?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[travis-image]: https://img.shields.io/travis/openwms/org.openwms.svg?style=flat-square
[travis-url]: https://travis-ci.org/openwms/org.openwms
[license-image]: http://img.shields.io/:license-GPLv3-blue.svg?style=flat-square
[license-url]: LICENSE
[codacy-image]: https://img.shields.io/codacy/1081cebbe27b40a8be16b6524f246b6b.svg?style=flat-square
[codacy-url]: https://www.codacy.com/app/openwms/org.openwms

# Current state of development

All components are currently under development. From a technical point of view they are moved from OSGi bundles towards a
more business oriented architecture with Spring Boot and Netflix OSS components. Documentation of previous released versions can be found on [SourceForge.net](http://openwms2005.sourceforge.net/).

# Current Architecture

Instead of applying a technical layered architecture (like we had with OSGi) the current architecture focus on business components. Business functions with a high degree of cohesion are kept together within
a deployable software component. Each component has it's own development lifecycle with an API evolution roadmap and an isolated data store. The following sketch shows all
currently existing as well as planned components of the OpenWMS.org system together with all potential surrounding systems.

![Architecture][1]

Beside an user interface several other systems interact with the OpenWMS.org system. On top, we have ERP systems.
Those systems send high-level tasks to OpenWMS.org, e.g. a customer order with order positions where each position refers to a product that is known by the `Inventory Service`. 
OpenWMS.org fulfills these tasks by orchestrating the underlying subsystem. The communication
between OpenWMS.org and an ERP system may not be unidirectional only, OpenWMS.org can although send status messages to the ERP or may request product catalog updates, this depends on the project needs.
On the bottom we have devices that are close to actors and sensors in an automated warehouse project. Those devices are almost all limited in hardware resources and protocol stacks. Typically [PLC](https://en.wikipedia.org/wiki/Programmable_logic_controller) (Programmable Logic
Controllers) are used to interact with field sensors and to control actors. OpenWMS.org is open source software and therefore promotes the usage of open source hardware components over commercial PLC products.
Therefor the first choice of supported devices are boards, like Arduino or Raspberry Pi, with an open microcontroller architecture, free to use. All these subsystems in the field area have one thing in common: They are close
to the hardware and expect to get responses from the server in no time to control motors and gates to the right direction. They although have the power to bring a serving component down just by sending requests all the time. Typical web applications are different in that
the infrastructure takes care of DoS attacks and the application server pools incoming traffic for us.

Read more about the architecture and design of each component on the components Github page.

# What kind of service architecture did we already have in the past?

The project started in 2005 with an J2EE server approach based on EJB2.1 with XDoclets, Hibernate and JavaServer Faces (JSF).
In more than 10 years we've seen a bunch of technologies to solve the same problems.
 
A POC was implemented with EJB2.1, but the project started with EJB3.0. Since about 2007 OpenWMS.org is on the Spring Framework and this is fine. Spring in combination with
OSGi seemed to be the right choice to build a modular and extensible base project. Unfortunately Spring stopped there efforts on OSGi, in particular on Spring dmServer and Spring Dynamic
Modules. 

[1]: src/docs/res/microservice_architecture.jpeg
