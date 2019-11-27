

# PropertyManager

[![Documentation Status](https://readthedocs.org/projects/propertymanager/badge/?version=latest)](https://propertymanager.readthedocs.io/en/latest/?badge=latest)
[![Code Coverage](https://img.shields.io/codecov/c/github/pvorb/property-providers/develop.svg)](https://codecov.io/github/pvorb/property-providers?branch=develop)
[![Build Status](https://travis-ci.org/sirnino/PropertyManager.svg?branch=master)](https://travis-ci.org/sirnino/PropertyManager)

This Microservices oriented Java library is intended to robustly retrieve application properties in your application.

The retrieval is performed accordingly to the following priority list:
1. Environment variables
2. Zookeeper/Etcd Configuration Server
3. Configuration file

Read more on [ReadTheDocs](https://propertymanager.readthedocs.io).

## Getting Started

### Prerequisites

This package can be used in Java >=8 projects.

In order to have all the functionalities it's highly recommended to have an instance of either Zookeeper or Etcd Configuration Managers up and running.

### Installing

The developer who wants to use the library has two choices:

#### Compile the code

1) Clone the source code: 

    		git clone https://github.com/sirnino/PropertyManager.git

2) Run the maven build

3) Import the produced jar into your project:

		    <dependency>
			    <groupId>it.asirchia.utils</groupId>
			    <artifactId>property-manager</artifactId>
			    <version>1.3.0</version>
		    </dependency>
    
#### Use jitpack
[Jitpack](https://jitpack.io) allows the developer to import the dependency directly from github.

1) Add the jitpack repository in your pom.xml file

			<repositories>
				<repository>
					<id>jitpack.io</id>
					<url>https://jitpack.io</url>
				</repository>
			</repositories>
	
2) Add the dependency:

			<dependency>
				<groupId>com.github.sirnino</groupId>
				<artifactId>PropertyManager</artifactId>
				<version>1.3.0</version>
			</dependency>

Be sure to set the following environmental variables in the project that includes the library.

If you have a running instance of Etcd:
* **etcd.active:** *true*
* **etcd.host:** *localhost* (or whichever is the host of your running Etcd server)
* **etcd.port:** *2379*  (or whichever is the port your running Etcd server is listening for clients)

If you have a running instance of Zookeeper:
* **zookeeper.active:** *true*
* **zookeeper.host:** *localhost* (or whichever is the host of your running Zookeeper server)
* **zookeeper.port:** *2181* (or whichever is the port  your running Zookeeper server is listening for clients)

### Best practices

I strongly recommend to use:
* *Environment variables* for application-specific configurations, e.g. database connection properties
* *Remote configuration servers* for the service discovery
* *Local configuration file* only for unchangable configurations and/or default values

### Testing

If you plan using this library only for getting properties from local files and/or environmental variables you don't need any other external tool.

If you instead need also to use an external configuration server like Zookeeper or Etcd, you must have an up and running instance for the tests.
The following instructions show you how to setup running instances of Etcd and Zookeeper through [Docker](https://www.docker.com/).

* **Etcd:**
In order to have an up and running instance of Etcd configuration server you can simply run the following command:

      sudo docker run 
          -e ETCDCTL_API=3  
          -p 2379:2379 
          --name etcd  
          gcr.io/etcd-development/etcd:v3.3.13 /usr/local/bin/etcd --listen-client-urls http://0.0.0.0:2379   --advertise-client-urls http://0.0.0.0:2379

  An up and running instance of Etcd will be available on port 2379.
Now you have to put a property within the just created Etcd server. You can do it as follows:
			
			sudo docker exec -ti etcd etcdctl put user.name "etcdusername"

  To check that the property has been properly published on Etcd launch:
			
       sudo docker exec -ti etcd etcdctl get username

  The following response should appear:
		
			user.name
			etcdusername

* **Zookeeper**
In order to have an up and running instance of Zookeeper configuration server you can simply run the following command:

      sudo docker run   -d --name=exhibitor   -p 2181:2181 -p 8080:8080   netflixoss/exhibitor:1.5.2

  An up and running instance of Zookeeper will be available on port 2181.
  Moreover you can add your configurations via Web GUI on port 8080. Through such a GUI add the */conf/test/user.name* node with value *UsernameFromZookeeper*.

Once the configuration servers have been installed and populated, you are ready to launch the tests.

Only for testing purposes set also:
* **user.name**: *UsernameFromEnvironment*

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Eclipse](https://www.eclipse.org/) -  IDE
* [Junit5](https://junit.org/junit5/) - Test framework

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/sirnino/PropertyManager/tags). 

## Authors

* **Antonino Sirchia** - *Initial work* - [sirnino](https://github.com/sirnino)

See also the list of [contributors](https://github.com/sirnino/PropertyManager/contributors) who participated in this project.

## License

See the [LICENSE.md](LICENSE.md) file for details
