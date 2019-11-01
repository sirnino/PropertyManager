.. _usersapis:

APIs
==================

PropertyManager is very simple to use. The basic usage costs only of the following public API

.. code-block:: java
	
	public static Optional<String> get(String key);

that can be used as follows

.. code-block:: java
	
	Properties.get("mypropertyname");

Automatically the PropertyManager will implement all the fallback procedure accordingly to the supplied configuration.

The returned value is a :code:`java.util.Optional` over which it's a good practice to perform some verification.


Advanced usage
--------------

PropertyManager provides also a configuration method that allows to inject external dependencies, like other :code:`RemotePropertyGetter`.

The method is

.. code-block:: java
	
	public static void configure(PropertyGetter fileGetter, PropertyGetter envGetter, RemotePropertyGetter remoteGetter);

that can be used as follows

.. code-block:: java
	
	Properties.configure(myFileGetter, myEnvGetter, myRemoteGetter);

Such a method can be used to configure **the entire PropertyManager instance**.