.. _usersinstallation:

Installation
==================

PropertyManager is a library intended for Java project >=8.

Maven projects can use the `jitpack <https://jitpack.io/>`_ repository to import the dependency. This can be done in two easy steps:

1. Add the jitpack repository in the pom.xml file

.. code-block:: xml

	<repositories>
		<repository>
			<id>jitpack.io</id>
			<url>https://jitpack.io</url>
		</repository>
	</repositories>
 	
2. Add the artefact dependency in the same pom.xml file


.. code-block:: xml

	<dependency>
		<groupId>com.github.sirnino</groupId>
		<artifactId>PropertyManager</artifactId>
		<version>1.3.0</version>
	</dependency>
 	

This is enough to have PropertyManager installed in the project. Now it's necessary to configure it.
The PropertyManager configuration is documented :doc:`here <configuration>`.

