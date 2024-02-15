OpenWMS.org
=====================

Is a free to use and extensible Warehouse Management System (WMS) with a Material Flow Control (MFC) system for automatic and manual
warehouses. 

Find further documentation in the [Wiki](https://wiki.openwms.cloud/projects/openwms/wiki/00-dot-02-business-services)

# Current Architecture
Instead of applying a technical layered architecture (like with OSGi and before that with J2EE1.4) the current architecture focuses on
business components. Business functions with a high degree of cohesion kept together as small deployable software components. Each
component has its own development lifecycle with its roadmap of the API evolution, and a separate data store. The following sketch shows
all currently existing components of the OpenWMS.org system together with all potential surrounding systems.

![Architecture][1]

Beside the user interface, several other systems interact with the OpenWMS.org system. On top, we find ERP systems sending high-level tasks
to OpenWMS.org, e.g. a customer order with order positions where each refers to a product that is managed by the `Inventory Service`.
OpenWMS.org fulfills these tasks by orchestrating the underlying subsystem. The communication between OpenWMS.org and an ERP system may not 
be exclusively unidirectional, OpenWMS.org does although send status messages back to the ERP or may request product catalog updates, this
depends on the project needs. On the bottom we have devices that are close to actors and sensors in automatic warehouses. Those devices are
almost limited in hardware resources and protocol stacks. Typical [PLC](https://en.wikipedia.org/wiki/Programmable_logic_controller)
(Programmable Logic Controllers) are used to interact with field sensors and to control actors. OpenWMS.org is an open source software and
therefore promotes the usage of open source hardware components over commercial PLC products as well. The first choice of supported devices
are boards, like [Arduino](https://www.arduino.cc), [Raspberry Pi](https://www.raspberrypi.org/) or the industrial [Revolution Pi](https://revolution.kunbus.com/)
version, with an open microcontroller architecture, free to use. All these subsystems in the field area have one thing in common: They are
close to the hardware and expect responses from the server in no time to control motors and switch gates to the right direction. They
although have the power to bring a serving component down just by sending requests all the time. Typical web applications are different in
that the infrastructure takes care of DoS attacks, and the application server pools incoming traffic.

Read more about each components architecture and design on the components corresponding Github page.

# Previous Architectures
The project started in 2005 with an J2EE server approach based on EJB2.1 with XDoclets, Hibernate and JavaServer Faces (JSF). In more than
15 years we've seen a bunch of technologies that all addresses the same problems.
 
A POC has been implemented with EJB2.1, but the project actually started with EJB3.0. Since about 2007 OpenWMS.org is on the Spring
Framework and this is still fine, and the right choice. Spring in combination with OSGi seemed to be the perfect match to build a modular
and extensible base project. Unfortunately Spring stopped their efforts on OSGi, in particular on Spring dmServer and Spring Dynamic Modules
. In a transition step to the current microservice architecture, we put all the OSGi bundles into a fat JavaEE WAR deployment unit to run
the application on a servlet container like Apache Tomcat. After that we redesigned all services and business functions and applied a
microservice architecture.

# Technologies
In addition to a bunch of Spring Framework subprojects, OpenWMS.org uses one of the popular BPMN workflow engines [Activiti](https://www.activiti.org),
[Flowable](https://www.flowable.org) or [Camunda](https://www.camunda.org) as embedded engine to take routing decisions in the TMS layer.
RDBMS access is most of time realised with the Java Persistence API. Some components might use NoSQL databases, like MongoDB, solely.
RabbitMQ in combination with Spring Integration as notification is used as an event broker. All hexagon components are Spring Boot
applications designed to run on any modern PaaS cloud platforms, like [Heroku](https://www.heroku.com), [Azure Kubernetes Service](https://azure.microsoft.com/de-de/services/kubernetes-service/)
or [Redhat OpenShift](https://www.redhat.com/en/technologies/cloud-computing/openshift).

# Microservices

| [![11]][2g] [![12]][2w]  | [![11]][3g] [![12]][3w] | [![11]][4g] [![12]][4w] | [![11]][5g] [![12]][5w] | [![11]][6g] [![12]][6w] | [![11]][7g] [![12]][7w] | [![11]][8g] [![12]][8w] | [![11]][9g] [![12]][9w] |
| :------------- | :------------- | :------------- | :------------- | :------------- | :------------- | :------------- | :------------- |
| ![2]      | ![3]      | ![4]      | ![5]      | ![6]      | ![7]      | ![8]      | ![9]      | 


| [![11]][10g] [![12]][10w] | ![11] ![12]               | ![11] ![12]               | ![11] ![12]               | ![11] ![12]               | ![11] ![12]
| :------------------------ | :------------------------ | :------------------------ | :------------------------ | :------------------------ | :------------------------ 
|  ![10]                    |  ![13]                    |  ![14]                    |  ![15]                    |  ![16]                    |  ![17]                   

[1]: src/docs/res/microservice_architecture.jpeg
[2]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-uaa.png
[2g]: https://github.com/openwms/org.openwms.core.uaa
[2w]: https://openwms.github.io/org.openwms.core.uaa/index.html
[3]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-prefs.png
[3g]: https://github.com/openwms/org.openwms.core.preferences
[3w]: https://openwms.github.io/org.openwms.core.preferences/index.html
[4]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-location.png
[4g]: https://github.com/openwms/org.openwms.common.service
[4w]: https://openwms.github.io/org.openwms.common.service/index.html
[5]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-tcp.png
[5g]: https://github.com/openwms/org.openwms.common.comm
[5w]: https://www.interface21.io/docs/common/comm/index.html
[6]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-transportation.png
[6g]: https://github.com/openwms/org.openwms.tms.transportation
[6w]: https://openwms.github.io/org.openwms.tms.transportation/index.html
[7]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-routing.png
[7g]: https://github.com/openwms/org.openwms.tms.routing
[7w]: https://openwms.github.io/org.openwms.tms.routing/index.html
[8]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-inventory.png
[8g]: https://github.com/openwms/org.openwms.wms.inventory
[8w]: https://openwms.github.io/org.openwms.wms.inventory/index.html
[9]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-shipping.png
[9g]: https://github.com/openwms/org.openwms.wms.shipping
[9w]: https://openwms.github.io/org.openwms.wms.shipping/index.html
[10]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-receiving.png
[10g]: https://github.com/openwms/org.openwms.wms.receiving
[10w]: https://openwms.github.io/org.openwms.wms.receiving/index.html
[11]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/github-b.png
[12]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/website-b.png
[13]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-portal.png
[14]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-i18n.png
[15]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-movement.png
[16]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-picking.png
[17]: https://raw.githubusercontent.com/openwms/org.openwms/gh-pages/src/docs/res/srv-putaway.png

# Current state of development
Most components are under active development. In 2016 the whole product has been migrated from the technical structured OSGi architecture
towards a business oriented architecture with Spring Boot microservices and Netflix OSS components. Documentation of previously released
versions does still exist on [SourceForge.net](http://openwms2005.sourceforge.net/).
