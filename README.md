org.openwms
=====================

Open Warehouse Management System

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

# Resources

Wiki at [Atlassian Confluence](https://openwms.atlassian.net/wiki/display/OPENWMS)

# Current state of development

All components are currently under development. From an technical point of view they are moved from OSGi towards a
microservice architecture with Spring Boot and Netflix OSS components.

# What kind of service architecture did we already have in the past?

The project started in 2005 with an J2EE server approach based on EJB2.1 with XDoclets, Hibernate and JavaServer Faces (JSF).
In more than 10 years we've seen a bunch of technologies to solve the same problems.
 
A POC was implemented with EJB2.1, but the project started with EJB3.0. Since about 2007 OpenWMS.org is on the Spring Framework and this is fine. Spring in combination with
OSGi seemed to be the right choice to build a modular and extensible base project. Unfortunately Spring stopped there efforts on OSGi, in particular on Spring dmServer and Spring Dynamic
Modules. 

# Current Architecture

Instead of applying a technical layered architecture the current architecture 


