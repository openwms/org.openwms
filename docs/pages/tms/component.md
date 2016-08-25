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

{% include inline_image.html file="tms_bom.png" alt="TMS  BOM" %}

# Persistent Object Model (POM)
