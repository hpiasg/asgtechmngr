ASGtechmngr
-----------

ASGtechmnrg is a tool for managing available technology files for the ASGtools suite.

### Installation ###

Download and unpack the ASGtechmngr package. You don't have to install anything or make changes to environment variables. To run it you will need a Java runtime environment (JRE) v1.7 (or later).

### Usage ###

For the following example command it is assumed that your current working directory is the ASGtechmngr main directory. If you want run ASGtechmngr from another directory you have to add the path to the ASGtechmngr main directory in front of the following commands (or you could add the `bin/` directory to your `PATH` variable).

    bin/ASGtechmngr_gui

#### Main Dialog ####

The main dialog lists all currently in the `ASGtechmngr_DIR/tech` directory installed technologies. You can export or delete them.

You can also create new technologies or import (exported) ones.

#### Create new technology ####

In order to create a technology description for the ASGtools you have to supply the appropriate data to the following fields:

* Name: String to identify the technology in the tools
* Balsa technology directory: The path to the directory containing a Balsa startup.scm and the other Balsa-specific technology files. See the [Balsa manual](http://apt.cs.manchester.ac.uk/ftp/pub/amulet/balsa/3.5/BalsaManual3.5.pdf) for an explanation how to generate these. 
* Genlib file: The path to a file in [Genlib format](https://www.ece.cmu.edu/~ee760/760docs/genlib.pdf).
* Liberty file: The path to a file in Liberty format.
* Additional info file: The path to a file with additional gate information used by ASGdrivestrength. See [ASGdrivestrength](https://github.com/hpiasg/asgdrivestrength) for details.

* Search path: The technology search path for remote operations. This value is appended to Design Compiler's `search_path`.
* Libraries: While using Design Compiler `link_library` and `target_library` are set to this value. Thus you can define multiple libraries by separating them with a space character.
* TCL file for layouting: Currently not used.

### Build instructions ###

To build ASGtechmngr, Apache Maven v3 (or later) and the Java Development Kit (JDK) v1.7 (or later) are required.

1. Build [ASGcommon](https://github.com/hpiasg/asgcommon)
2. Execute `mvn clean install -DskipTests`