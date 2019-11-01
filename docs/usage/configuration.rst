.. _userconfiguration:

Configuration
==================

Out of the box, PropertyManager supports the configuration retrieval from Environmental variables 
and, as a fallback, from a configuration file.

Since the retrieval from file is the "safe harbor" for the library, it's a best practice to have a file containing 
all the default values for all the configurations. Such a file should be named :code:`application.properties` 
and should be placed at root level of your BuildPath. Usually, a good place to put the properties file
is the :code:`/src/main/resources` folder of the java application that is using ProeprtyManager.

In order to use a remote configuration server, it's necessary to enable the proper server client.
PropertyManager natively supports both **Etcd** and **Zookeeper**..

Please note that only one remote configuration server can be configured at a time. 

Etcd integration 
-----------------

To enable the Etcd integration it's necessary to add the following environmental variables while running the 
application with PropertyManager:

* **etcd.active**: *true*
* **etcd.host**: *localhost* (or whichever is the host of your running Etcd server)  
* **etcd.port**: *2379* (or whichever is the port your running Etcd server is listening for clients)

Simply adding this three environmental variables the library will search for a property on Etcd first, if 
the requested property is not found there it will fallback onto the other environmental variables, if 
the requested property is still not available it will fallback onto the default value in the :code:`application.properties` file.


Zookeeper integration
---------------------

To enable the Etcd integration it's necessary to add the following environmental variables while running the 
application with PropertyManager:

* **zookeeper.active**: *true*
* **zookeeper.host**: *localhost* (or whichever is the host of your running Zookeeper server)  
* **zookeeper.port**: *2181* (or whichever is the port your running Zookeeper server is listening for clients)

Simply adding this three environmental variables the library will search for a property on Zookeeper first, if 
the requested property is not found there it will fallback onto the other environmental variables, if 
the requested property is still not available it will fallback onto the default value in the :code:`application.properties` file.


Best practices
--------------

Is recommended to to use:

* *Environment variables* for application-specific configurations, e.g. database connection properties.
* *Remote configuration servers* for the service discovery or configuration shared among several distributed services.
* *Local configuration file* only for unchangeble configuration and/or defauilt values.

