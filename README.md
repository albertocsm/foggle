[![Build Status](https://travis-ci.org/albertocsm/foggle.svg?branch=master)](https://travis-ci.org/albertocsm/foggle) [![](https://raw.githubusercontent.com/novoda/novoda/master/assets/btn_apache_lisence.png)](LICENSE.txt)

# Description
This is a small and simple project conceived to be used as a service by other systems or services.
Enables managing and querying feature toggles associated to external systems.

It currently supports global and non global toggles with the ability of a specific external system overriding an already existing global toggle value. 

**NOTE: this project is a work in progress, it is not production ready and quite a few thing are not yet implemented at all -- contributions are very welcome**

# TLDR
* just run it with the public image and start using the [RESTful API](#api)

  `docker run -p 8080:8080 --name foggle -d albertocsm/foggle`
  
# Requirements

* JDK 8
* Maven 3.x
* Docker 1.x.x
  
# Build and Run

 - build the sources and run it with

    `mvn clean package && java -jar ./target/foggle-1.0-SNAPSHOT.jar`
* **or** you can build the docker image yourself and run it that way

    `mvn clean package`    
    `docker build --rm -t=foggle .`   
    `docker run -p 8080:8080 --name foggle -d foggle`

# ToDo

* functional 
  * add support for authentication and RBAC using JWT and spring (boot) security
  * add support for notifying a external system when a Toggle value changes using a subscription model and web hooks
  * replace H2 by PostgreSQL for runtime
  * add PK, FK and UC to schema
  * add some extra search capabilities to Toggle management (eg. search by toggle description, sorting, etc)

* non-functional  
  * move artifacts to sub modules
  * improve exception handling on all layers    
  * add some more test cases for edge conditions (eg. test unable to find deleted items)  
  * add automatic documentation to REST

* other
  * add GRPC layer as a REST alternative


# API

#### get current system/service toggles
        
* find all
```
verb
    GET
url
    {host}/sys
query params
    s [<external system id>]
    v [<external system version>]
```

#### manage toggles

* create
```
verb
     POST
url
    {host}/dashboard/toggle
body ex:
    {
        "description": "postman_toggle_1",
        "active": true,
        "global": true
    }
```

* update
```
verb
     PUT
url
    {host}/dashboard/toggle/{toggleId}
body
    {
        "description": "postman_toggle_11",
        "active": false,
        "global": false
    }
```

* delete
```
verb
    DELETE
url
    {host}/dashboard/toggle/{toggleId}
```

* find
```
verb
    GET
url
    {host}/dashboard/toggle/{toggleId}
```

* search
```
verb
    GET
url
    {host}/dashboard/toggle
query params
    active [<True>|<False>]
    global [<True>|<False>]
```



#### manage toggle references
        
* create
```
verb
     POST
url
    {host}/dashboard/toggle{toggleId}/reference
body ex:
    [
        {
            "sysId": "s1",
            "sysVersion": "v1",
            "active": true
        },
        {
            "sysId": "s2",
            "sysVersion": "v1",
            "active": true
        }
    ]
```
* update
```
verb
     PUT
url
    {host}/dashboard/toggle{toggleId}/reference
body
    [
        {
            "resourceId": "4eb481b6-c2f5-45c0-b8e5-d64266fd9d3c",
            "sysId": "s1",
            "sysVersion": "v1",
            "active": true
        }
    ]
```
  
* delete
```
verb
    DELETE
url
    {host}/dashboard/toggle{toggleId}/reference
body
    [
        "6bd4e204-f73b-4938-9cb9-ee1cb16aeb5e"
    ]
```
  
* find
```
verb
    GET
url
    {host}/dashboard/toggle{toggleId}/reference/{referenceId}
```
  
* search
```
verb
    GET
url
    {host}/dashboard/toggle{toggleId}/reference
```


# Useful Links

    https://www.togglz.org/documentation/advanced-config.html
    https://github.com/marinho/bipolar-server
    https://github.com/jOOQ/jOOQ/tree/master/jOOQ-examples/jOOQ-spring-boot-example
    https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-jooq
