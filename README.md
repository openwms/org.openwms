OpenWMS.org
=====================

Is a free to use and extensible Warehouse Management System to control the material flow within automated and manual warehouses. 

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
more business oriented architecture using Spring Boot and Netflix OSS components. Documentation of previous released versions can be found on [SourceForge.net](http://openwms2005.sourceforge.net/).

# Current Architecture

Instead of applying a technical layered architecture (like we did with OSGi and before that with J2EE1.4) the current architecture focuses on business components. Business functions with a high degree of cohesion are kept together within
a deployable software component. Each component has it's own development lifecycle with an API evolution roadmap and an isolated data store. The following sketch shows all
currently existing as well as planned components of the OpenWMS.org system together with all potential surrounding systems.

![Architecture][1]

Beside the user interface, several other systems interact with the OpenWMS.org system. On top, we have ERP systems.
 They send high-level tasks to OpenWMS.org, e.g. a   customer order with order positions where each position refers to a product that is known by the `Inventory Service`. 
OpenWMS.org fulfills these tasks by orchestrating the underlying subsystem. The communication
between OpenWMS.org and an ERP system may not be unidirectional only, OpenWMS.org can although send status messages to the ERP or may request product catalog updates, this depends on project needs.
On the bottom we have devices that are close to actors and sensors in automated warehouse projecty. Those devices are almost all limited in hardware resources and protocol stacks. Typically [PLC](https://en.wikipedia.org/wiki/Programmable_logic_controller) (Programmable Logic
Controllers) are used to interact with field sensors and to control actors. OpenWMS.org is open source software and therefore promotes the usage of open source hardware components over commercial PLC products.
The first choice of supported devices are boards, like [Arduino](https://www.arduino.cc) or [Raspberry Pi](https://www.raspberrypi.org/), with an open microcontroller architecture, free to use. All these subsystems in the field area have one thing in common: They are close
to the hardware and expect to get responses from the server in no time to control motors and switch gates to the right direction. They although have the power to bring a serving component down just by sending requests all the time. Typical web applications are different in that
the infrastructure takes care of DoS attacks and the application server pools incoming traffic for us.

Read more about each components architecture and design on the components Github page.

# Previous Architectures

The project started in 2005 with an J2EE server approach based on EJB2.1 with XDoclets, Hibernate and JavaServer Faces (JSF). In more than 10 years we've seen a bunch of technologies that all address the same problems.
 
A POC has been implemented with EJB2.1, but the project actually started with EJB3.0. Since about 2007 OpenWMS.org is on the Spring Framework and this is fine. Spring in combination with
OSGi seemed to be the right choice to build a modular and extensible project base. Unfortunately Spring stopped there efforts on OSGi, in particular on Spring dmServer and Spring Dynamic
Modules. In a transition to the current microservice architecture, we started to put all the OSGi bundles into a fat JavaEE WAR deployment unit to run the application on a servlet container
like Apache Tomcat.

# Technologies

In addition to a bunch of Spring Framework projects, OpenWMS.org uses [Activiti](http://activiti.org) as embedded workflow engine to take routing decisions in the TMS layer. RDBMS access is shielded with the Java Persistence API.
Some components might use NoSQL databases, like MongoDB, solely. RabbitMQ is used in combination with Spring Integration as notification and event broker. All hexagon components are Spring Boot applications designed to
run on a PaaS, currently deployed to [Heroku](https://www.heroku.com)

# Microservices

| [![11]][2g] [![12]][2w]  | [![11]][3g] [![12]][3w] | [![11]][4g] [![12]][4w] | [![11]][5g] [![12]][5w] | [![11]][6g] [![12]][6w] | [![11]][7g] [![12]][7w] | [![11]][8g] [![12]][8w] | [![11]][9g] [![12]][9w] |
| :------------- | :------------- | :------------- | :------------- | :------------- | :------------- | :------------- | :------------- |
| ![2]      | ![3]      | ![4]      | ![5]      | ![6]      | ![7]      | ![8]      | ![9]      | 


| [![11]][10g] [![12]][10w] |
| :------------- | 
|  ![10]         | 
 
[1]: src/docs/res/microservice_architecture.jpeg
[2]: src/docs/res/srv-uaa.png
[2g]: https://github.com/openwms/org.openwms.core.uaa
[2w]: https://www.interface21.io/docs/core/uaa/index.html
[3]: src/docs/res/srv-prefs.png
[3g]: https://github.com/openwms/org.openwms.core.preferences
[3w]: https://www.interface21.io/docs/core/preferences/index.html
[4]: src/docs/res/srv-location.png
[4g]: https://github.com/openwms/org.openwms.common.service
[4w]: https://www.interface21.io/docs/common/base/index.html
[5]: src/docs/res/srv-tcp.png
[5g]: https://github.com/openwms/org.openwms.common.comm
[5w]: https://www.interface21.io/docs/common/comm/index.html
[6]: src/docs/res/srv-transportation.png
[6g]: https://github.com/openwms/org.openwms.tms.transportation
[6w]: https://www.interface21.io/docs/tms/transportation/index.html
[7]: src/docs/res/srv-routing.png
[7g]: https://github.com/openwms/org.openwms.tms.routing
[7w]: https://www.interface21.io/docs/tms/routing/index.html
[8]: src/docs/res/srv-inventory.png
[8g]: https://github.com/openwms/org.openwms.wms.inventory
[8w]: https://www.interface21.io/docs/wms/inventory/index.html
[9]: src/docs/res/srv-shipping.png
[9g]: https://github.com/openwms/org.openwms.wms.shipping
[9w]: https://www.interface21.io/docs/wms/shipping/index.html
[10]: src/docs/res/srv-receiving.png
[10g]: https://github.com/openwms/org.openwms.wms.receiving
[10w]: https://www.interface21.io/docs/wms/receiving/index.html
[11]: src/docs/res/github-b.png
[12]: src/docs/res/www.png