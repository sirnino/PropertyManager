# PropertyManager

This Microservices oriented Java library is intended to robustly retrieve application properties in your application.

The retrieval is performed accordingly to the following priority list:
1. Environment variables
2. Zookeeper Configuration Manager
3. Configuration file


## Getting Started

### Prerequisites

This package can be used in Java 8 projects.

In order to have all the functionalities it's highly recommended to have an instance of Zookeeper Configuration Manager up and running.

Documentation on how to run a Zookeeper instance from scratch can be found [here](https://zookeeper.apache.org/doc/r3.1.2/zookeeperStarted.html).
Anyway I kindly recommended to use the [netflixoss/exhibitor](https://hub.docker.com/r/netflixoss/exhibitor/) docker image to make Zookeper up and running easily for development and testing purposes.

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
			    <version>1.1.0</version>
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
				<version>1.0.0</version>
			</dependency>

### Best practices

I strongly recommend to use:
* Environment variables for application-specific configurations, e.g. database connection properties
* Zookeeper for the service discovery
* Local configuration file only for unchangable configurations and/or default values

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Eclipse](https://www.eclipse.org/) -  IDE

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/sirnino/PropertyManager/tags). 

## Authors

* **Antonino Sirchia** - *Initial work* - [sirnino](https://github.com/sirnino)

See also the list of [contributors](https://github.com/sirnino/PropertyManager/contributors) who participated in this project.

## License

See the [LICENSE.md](LICENSE.md) file for details
