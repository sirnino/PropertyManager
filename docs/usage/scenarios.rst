.. _userscenarios:

Usage Examples
==============

This section shows several frequent scenarios where PropertyManager can be useful.
    

Retrieve configuration from file
--------------------------------

This scenario shows how to use PropertyManager for decide the location in the filesystem where
 to store a programmatically generated file.

We have a Java application (now on named *App0*) that upsert a file on some events that may happen at runtime.

*App0* include PropertyManager as described in the :doc:`Installation Guide <installation>`.
For this scenario, PropertyManager doesn't need any additional configuration.

Within *App0* there is the file :code:`/src/main/resources/application.properties` that contains the following line:

.. code-block:: properties

	fs.log.location=/home/sirnino/
	fs.log.filename=events.log

The *App0* contains the following method that allows to store the events in a log file:

.. code-block:: java

	public void store_event(){
	
		FileOutputStream file = new FileOutputStream(Properties.get("fs.log.location") + Proeprties.get("fs.log.filename"));
		
		// Here the actual storing logic
		
	}

When *App0* is launched, the events will be logged in the  :code:`/home/sirnino/events.log` file.


Retrieve configuration from environment (fallback on file)
----------------------------------------------------------

This scenario shows how to use PropertyManager for getting database connection information.

We have a Java application (now on named *App1*) that needs to access a MySQL database.

*App1* include PropertyManager as described in the :doc:`Installation Guide <installation>`.
For this scenario, PropertyManager doesn't need any additional configuration.

Within *App1* there is the file :code:`/src/main/resources/application.properties` that contains the following:

.. code-block:: properties

	db.host=localhost
	db.port=3306
	db.name=app1db
	db.user=root
	db.password=root

The *App1* contains the following method that allows to connect to the DB:

.. code-block:: java

	public void db_connect(){
	
		System.out.println("Database Host: "+Properties.get("db.host"));
		System.out.println("Database Port: "+Properties.get("db.port"));
		System.out.println("Database Name: "+Properties.get("db.name"));
		System.out.println("Database User: "+Properties.get("db.user"));
		System.out.println("Database Password: "+Properties.get("db.password"));
		
		// Here the actual connection logic...
		
	}

If *App1* is launched as follows:

.. code-block:: shell

	java -Ddb.host=192.16.10.10 -Ddb.name=anotherDbName -Ddb.password=$3cr3t -jar app1.jar

or alternatively, with docker as follows:

.. code-block:: shell

	user@host:~# docker build -t app1 .
	user@host:~# docker run -e db.host=192.16.10.10 -e db.name=anotherDbName -e db.password=$3cr3t app1

The execution causes the following output: 

.. code-block:: shell

	Database Host: 192.168.10.10
	Database Port: 3306
	Database Name: anotherDbName
	Database User: root
	Database Password: $3cr3t

The environmental variables have an higher priority, so the host, name and password will be the one provided through the Command Line.

The port and the user are not provided via environmental variables, then PropertyManager performes a fallback on
the application.properties file and takes the default values. 

Multi fallback configuration retrieval process
----------------------------------------------

This scenario shows how to use PropertyManager for getting the URL information about a remote service to invoke.

We have a Java application (now on named *App2*) that needs to invoke an external microservice.

*App2* include PropertyManager as described in the :doc:`Installation Guide <installation>`.

Within *App2* there is the file :code:`/src/main/resources/application.properties` that contains the following:

.. code-block:: properties

	my.external.service.host=localhost
	my.external.service.port=8888
	my.external.service.method=GET


The *App2* contains the following method that allows to invoke the external service:

.. code-block:: java

	public void extServiceInvoker(){
	
		System.out.println("External Service Host: "+Properties.get("my.external.service.host"));
		System.out.println("Database Port: "+Properties.get("my.external.service.port"));
		System.out.println("Database Name: "+Properties.get("my.external.service.method"));
		
		// Here the actual invokation logic...
		
	}


Zookeeper
`````````
To use the Zookeeper Remote Getter, PropertyManager must be configured as described in the :doc:`Configuration guide <configuration>`.

Let's imagine there is a zookeeper available at 192.168.10.20:2181 launched as follows:

.. code-block:: shell

	user@192.168.10.20 ~# docker run   -d --name=exhibitor   -p 2181:2181 -p 8080:8080   netflixoss/exhibitor:1.5.2
	

Such a Zookeeper instance contains the following configuration: 

* /conf/my.external.service.host --> 192.168.10.30


.. note:: Be careful with the **/conf/** prefix. It's mandatory!

If *App2* is launched as follows:

.. code-block:: shell

	java -Dzookeeper.active=true -Dzookeeper.host=192.168.10.20 -Dzookeeper.port=2181 -Dmy.external.service.method=POST -jar app2.jar

or alternatively, with docker as follows:

.. code-block:: shell

	user@host:~# docker build -t app2 .
	user@host:~# docker run -e zookeeper.active=true -e zookeeper.host=192.168.10.20 -e zookeeper.port=2181 -e my.external.service.method=POST app2


The output of the extServiceInvoker method will be:

.. code-block:: shell

		External Service Host: 192.168.10.30
		External Service Port: 8888
		External Service method: POST

The Remote Getter has the higher priority, for this reason the External service host is the one stored in Zookeeper.
If the searched property is not in the remote Configuration server it's searched among the environmental variables,
for this reason the HTTP method is POST, as supplied through the Command Line.
If none of the two above techniques provide a valid result, the last fallback is performed toward the application.properties file;
for this reason the External Service Port is the default one: 8888.


Etcd
````

To use the Etcd Remote Getter, PropertyManager must be configured as described in the :doc:`Configuration guide <configuration>`.

Let's imagine there is an etcd available at 192.168.10.21:2379 launched as follows:

.. code-block:: shell

	user@192.168.10.21 ~# sudo docker run -e ETCDCTL_API=3 -p 2379:2379 --name etcd  gcr.io/etcd-development/etcd:v3.3.13 /usr/local/bin/etcd --listen-client-urls http://0.0.0.0:2379   --advertise-client-urls http://0.0.0.0:2379

Such a Etcd instance contains the following configuration: 

* my.external.service.host --> 192.168.10.31


If *App2* is launched as follows:

.. code-block:: shell

	java -Detcd.active=true -Detcd.host=192.168.10.21 -Detcd.port=2379 -Dmy.external.service.method=DELETE -jar app2.jar

or alternatively, with docker as follows:

.. code-block:: shell

	user@host:~# docker build -t app2 .
	user@host:~# docker run -e etcd.active=true -e etcd.host=192.168.10.21 -e etcd.port=2379 -e my.external.service.method=DELETE app2


The output of the extServiceInvoker method will be:

.. code-block:: shell

		External Service Host: 192.168.10.31
		External Service Port: 8888
		External Service method: DELETE

The Remote Getter has the higher priority, for this reason the External service host is the one stored in Etcd.
If the searched property is not in the remote Configuration server it's searched among the environmental variables,
for this reason the HTTP method is DELETE, as supplied through the Command Line.
If none of the two above techniques provide a valid result, the last fallback is performed toward the application.properties file;
for this reason the External Service Port is the default one: 8888.

