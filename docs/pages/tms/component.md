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

Basically it is all about moving `TransportUnits` between `Locations` within a Warehouse. Such a movement is expressed by a `TransportOrder`
 that references the `TransportUnit` to move. A `TransportOrder` must always have a final `Target` where to move to. 

{% include inline_image.html file="tms_bom.png" alt="TMS BOM" %}

## Use Cases

 - Move `TansportUnits` between `Locations` using `TransportOrders`
 
 - Redirect existing `TransportOrders` to new Targets
 
 - Request a `TransportOrder` to be started, cancelled or finished

# Entity Object Model (EOM)

The implementation stores `TransportOrders` in a relational database (RDBMS) using the Java Persistence API as an abstraction. 

{% include inline_image.html file="tms_pom.png" alt="TMS POM" %}

A `TransportOrderState` is persisted along each `TransportOrder` instance that keeps track of the current state. In addition each `TransportOrder`
 has a `Priority` assigned that is used to order all active orders for processing. The following order attributes are persisted:
  
Attribute | Description
--- | ---
transportUnitBk | The business key of the moved TransportUnit
startDate | When the order has been set into an active state
endDate | When the order has been ended
sourceLocation | The business key of the Location the TransportUnit has been placed when the TransportOrder has been started
targetLocation | The business key of the Location where the TransportOrder should be moved to
targetLocationGroup | The business key of the LocationGroup where the TransportOrder should be moved to

 

