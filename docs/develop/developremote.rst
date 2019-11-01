.. _developremote:

Develop a custom Remote Getter
==============================

PropertyManager provides, out of the box, native support for Zookeeper and Etcd configuration server.

In case these two configuration servers are not enough, but would arise the need to implement the support for 
a custom Remote Getter, it's definitely possible.

The extension with a custom Remote Getter costs of only two steps:

1. Create a custom implementation of Remote Getter
2. Configure PropertyManager instance for using it

Create a custom implementation of Remote Getter
```````````````````````````````````````````````

After having included PropertyManager in the 3rd party application (now on named *MyApp*), create a class
named :code:`MyCustomRemotePropertyGetter` as follows:

.. code-block:: java

	import java.util.Optional;
	import io.github.sirnino.utils.properties.getters.RemotePropertyGetter;
	
	public class MyCustomRemotePropertyGetter implements RemotePropertyGetter {
	
		@Override
		public Optional<String> get(String key) {
			MyClient myclient = buildClient();
			// Implement the actual logic for getting the requested property
			// through the built MyClient instance
		}
	
		@Override
		public MyClient buildClient() {
			// Implement the logic for building the client 
			//   for connecting to the Remote Configuration Server
		}
	
	}


Implement both the **buildClient** and the **get** methods, and that's it.

.. note:: Here **MyClient** class represents any Client you may use for the integration with the external system.
	It could be a simple **HttpClient**, or even a custom client implementation. 
	
	In the latter case, it must be implemented as well.

Configure PropertyManager instance
``````````````````````````````````

In order to say PropertyManager to use the just created Remote Getter, it's necessary to invoke the following:

.. code-block:: java

	Properties.configure(null, null, new MyCustomRemotePropertyGetter());
 

This can be done just once, at the application startup.

Now the PropertyManager has been properly configured to use the Custom Remote Getter and can be used as usual.
 