---
title: "TMS - Transportation Microservice"
search: exclude
permalink: tms-component.html
sidebar: tms_transportation_sb
folder: tms
toc: false
---

The TMS software component is implemented in a polyglot microservice architecture. In particular the TMS Transportation component
is a self-contained module that targets a business functionality and contains all application layers necessary to implement that
function. The only accessible interface is the public API defined and implemented by the microservice implementation.

# Functional Scope

The Transportation component offers essential functionality used to monitor and control transport orders executed in a warehouse.

# Business Object Model (BOM)

Basically it is all about moving TransportUnits between Locations within a Warehouse. Such a movement is expressed by a TransportOrder
 that references the TransportUnit to move. A TransportOrder must always have a final Target where to move to. 

{% include inline_image.html file="tms_bom.png" alt="TMS BOM" %}

## Use Cases

 - Move TransportUnits between Locations using TransportOrders
 
 - Redirect existing TransportOrders to new Targets
 
 - Request a TransportOrder to be started, cancelled or finished.

# Persistent Object Model (POM)

The implementation stores TransportOrders in a relational database (RDBMS) using the Java Persistence API as an abstraction. 

{% include inline_image.html file="tms_pom.png" alt="TMS POM" %}
