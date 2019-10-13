.. _developersinstallation:

Installation
==================

This section explains how to get a working copy of the PropertyManager source code.

The PropertyManager source code is available for free downloading through whichever git client.
On the System CLI the command is: 

.. code-block:: shell

   git clone https://github.com/sirnino/PropertyManager.git

Once cloned, the project can be imported in whichever IDE the developer prefers, or can be edited through a simple text editor.

The project has the following structure: 

| **PropertyManager**
| ├── docs
| ├── src
|   └── main
|   └── test
| ├── README
| ├── CONTRIBUTING
| ├── LICENSE
| └── pom.xml

The **docs** folder contains the source code of this ReadTheDocs documentation.

The **src** folder is the one where the source code is: it contains two subfolders: **main** and **test**.
The first one contains the PropertyManager actual code; the second one contains the unit tests.

In the project root folder there are also a **README** file; a **CONTRIBUTING** that describes some contribution guidelines for developers; 
a **LICENSE** that describes the usage rights of the project and the **pom.xml**: PropertyManager is a Maven project that can be packaged with *Maven*.

The next section provides more details about the *src/main* internal structure and the source code.