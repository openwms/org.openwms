OpenWMS.org
=====================

Is a free to use and extensible Warehouse Management System (WMS) with a Material Flow Control (MFC) system for automatic and manual
warehouses. 

Find further documentation in the [Wiki](https://wiki.openwms.cloud/projects/openwms/wiki/00-dot-02-business-services)

# Architecture
Instead of applying a technical layered architecture (like with OSGi and before that with J2EE1.4) the current architecture focuses on
business components. Business functions with a high degree of cohesion kept together as small deployable software components. Each
component has its own development lifecycle with its roadmap of the API evolution, and a separate data store. The following sketch shows
all currently existing components of the OpenWMS.org system together with all potential surrounding systems.

![Architecture][1]

Beside the user interface, several other systems interact with the OpenWMS.org system. ERP systems on top are sending high-level tasks
to OpenWMS.org, e.g. a customer orders with order lines that refer to products managed by the `Inventory Service`.
OpenWMS.org fulfills these tasks by orchestrating the underlying subsystems. The communication between OpenWMS.org and an ERP system might 
be in both directions, OpenWMS.org although sends status messages back to the ERP or might request product catalog updates, depending on the
project needs. On the bottom of the above graphic the system if connected to devices that are close to actors and sensors in automatic
warehouses. Those devices are almost limited in hardware resources and protocol stacks. Typical [PLC](https://en.wikipedia.org/wiki/Programmable_logic_controller)
(Programmable Logic Controllers) are used to interact with field sensors and to control actors. OpenWMS.org is an open source software and
therefore promotes the usage of open source hardware components over commercial PLC products. The first choice of supported devices
are boards, like [Raspberry Pi](https://www.raspberrypi.org/) or industrial [Revolution Pi](https://revolution.kunbus.com/), with an open
microcontroller architecture. But nevertheless also closed proprietary systems are supported as well. All this kind of subsystems have one
thing in common: They are close to the hardware and expect response times in the range of milliseconds to control motors and switch gates
very quickly. They have the power to bring down a serving component down just by repeating requests all the time. Typical web application
clients are different in that the infrastructure takes care of DoS attacks, and the application server pools incoming traffic.

Read more about each components architecture and design on the components corresponding GitHub page.

# Technologies
In addition to a bunch of Spring Framework subprojects, OpenWMS.org supports popular BPMN workflow engines like [Activiti](https://www.activiti.org),
[Flowable](https://www.flowable.org) and [Camunda](https://www.camunda.org) to take routing decisions on the transport layer.
RDBMS access is most often realised with the [Jakarta Persistence API](https://de.wikipedia.org/wiki/Jakarta_Persistence_API). Some
components might also use NoSQL databases, like MongoDB. [RabbitMQ](https://rabbitmq.com) in combination with the Spring Integration project
is used as an event broker for asynchronous event and command propagation. Currently, all microservices are realised as Spring Boot 
applications designed to run on any modern PaaS cloud platforms, like the [Azure Kubernetes Service](https://azure.microsoft.com/de-de/services/kubernetes-service), [AWS EKS](https://aws.amazon.com/eks) or [Redhat OpenShift](https://www.redhat.com/en/technologies/cloud-computing/openshift).

# Microservices
Due to OpenWMS.org is built on a modern distributed microservice architecture that follows the [Twelve-Factor](https://12factor.net)
methodology, all functional business components are managed within their own SDLC (Software Development Life Cycle) and own source code
repositories.

| Service Name          | Repository                                                                                    | Accessibility        | License           |
|-----------------------|-----------------------------------------------------------------------------------------------|----------------------|-------------------|
| Service Registry      | [org.openwms.services](https://github.com/spring-labs/org.openwms.services)                   | Public               | Apache License v2 |
| Configuration Service | [org.openwms.configuration](https://github.com/spring-labs/org.openwms.configuration)         | Public               | Apache License v2 |
| Gateway Service       | [org.openwms.gateway](https://github.com/spring-labs/org.openwms.gateway)                     | Public               | Apache License v2 |
| Auth Service          | [org.openwms.auth](https://github.com/spring-labs/org.openwms.auth)                           | Private              | GPLv3             |
| UAA Service           | [org.openwms.core.uaa](https://github.com/openwms/org.openwms.core.uaa)                       | Public               | Apache License v2 |
| Preference Service    | [org.openwms.core.preferences](https://github.com/openwms/org.openwms.core.preferences)       | Public               | Apache License v2 |
| Printing Service      | [org.openwms.core.printing](https://github.com/openwms/org.openwms.core.printing)             | Private              | GPLv3             |
| Translation Service   | [org.openwms.core.lang](https://github.com/openwms/org.openwms.core.lang)                     | Private Preview      | Apache License v2 |
| Common Service        | [org.openwms.common.service](https://github.com/openwms/org.openwms.common.service)           | Public               | Apache License v2 |
| OSIP/TCP Driver       | [org.openwms.common.comm](https://github.com/openwms/org.openwms.common.comm)                 | Public               | Apache License v2 |
| OPCUA Driver          | [org.openwms.common.opcua](https://github.com/interface21-io/org.openwms.common.opcua)        | Private Preview      | Apache License v2 |
| Transaction Service   | [org.openwms.common.transactions](https://github.com/openwms/org.openwms.common.transactions) | Private Preview      | Apache License v2 |
| Common Tasks Service  | [org.openwms.common.tasks](https://github.com/openwms/org.openwms.common.tasks)               | Public               | Apache License v2 |
| Transportation Service | [org.openwms.tms.transportation](https://github.com/openwms/org.openwms.tms.transportation)   | Public               | Apache License v2 |
| TMS Routing           | [org.openwms.tms.routing](https://github.com/openwms/org.openwms.tms.routing)                 | Public               | Apache License v2 |
| Receiving Service     | [org.openwms.wms.receiving](https://github.com/openwms/org.openwms.wms.receiving)             | Public               | Apache License v2 |
| Inventory Service     | [org.openwms.wms.inventory](https://github.com/interface21-io/org.openwms.wms.inventory)      | Private Preview      | Apache License v2 |
| Picking Library       | [org.openwms.wms.picking](https://github.com/openwms/org.openwms.wms.picking)                 | Private              | GPLv3             |
| Movements Service     | [org.openwms.wms.movements](https://github.com/openwms/org.openwms.wms.movements)             | Public               | Apache License v2 |
| WMS Tasks Service     | [org.openwms.wms.tasks](https://github.com/openwms/org.openwms.wms.tasks)                     | Private Preview      | Apache License v2 |
| Partner Service       | [org.openwms.wms.partners](https://github.com/interface21-io/org.openwms.wms.partners)        | Private Preview      | Apache License v2 |
| Trucks Service        | [org.openwms.wms.trucks](https://github.com/openwms/org.openwms.wms.trucks)                   | Private Preview      | Apache License v2 |
| Shipping Service      | [org.openwms.wms.shipping](https://github.com/openwms/org.openwms.wms.shipping)               | Private Preview      | Apache License v2 |
| Putaway Library       | [org.openwms.wms.putaway](https://github.com/openwms/org.openwms.wms.putaway)                 | Private              | GPLv3             |
| SAP Adapter           | [org.openwms.wms.sap](https://github.com/openwms/org.openwms.wms.sap)                         | Private              | GPLv3             |
| Dynamics Adapter      | [org.openwms.wms.msdynamics](https://github.com/openwms/org.openwms.wms.msdynamics)           | Private              | GPLv3             |
| NetSuite Adapter      | [org.openwms.wms.netsuite](https://github.com/openwms/org.openwms.wms.netsuite)               | Private              | GPLv3             |

[1]: src/docs/res/microservice_architecture.jpeg

# Current state of development
Most components are under active development. In 2016 the whole product has been migrated from the technical structured OSGi architecture
towards a business oriented architecture with Spring Boot microservices and Netflix OSS components. Documentation of previously released
versions does still exist on [SourceForge.net](http://openwms2005.sourceforge.net/).

# Previous Architectures
The project started in 2005 with an J2EE server approach based on EJB2.1 with XDoclets, Hibernate and JavaServer Faces (JSF). In more than
15 years we've seen a bunch of technologies that all addresses the same problems.
 
A POC has been implemented with EJB2.1, but the project actually started with EJB3.0. Since about 2007 OpenWMS.org is on the Spring
Framework and this is still fine, and the right choice. Spring in combination with OSGi seemed to be the perfect match to build a modular
and extensible base project. Unfortunately Spring stopped their efforts on OSGi, in particular on Spring dmServer and Spring Dynamic Modules
. In a transition step to the current microservice architecture, we put all the OSGi bundles into a fat JavaEE WAR deployment unit to run
the application on a servlet container like Apache Tomcat. After that we redesigned all services and business functions and applied a
microservice architecture.
