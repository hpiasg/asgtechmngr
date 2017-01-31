ASGtechmngr
-----------

ASGtechmnrg is a tool for managing available technology files for the ASGtools suite.

### Installation ###

Download and unpack the ASGtechmngr package. You don't have to install anything or make changes to environment variables. To run it you will need a Java runtime environment (JRE) v1.7 (or later).

### Usage ###

For the following example command it is assumed that your current working directory is the ASGtechmngr main directory. If you want run ASGtechmngr from another directory you have to add the path to the ASGtechmngr main directory in front of the following commands (or you could add the `bin/` directory to your `PATH` variable).

    bin/ASGtechmngr_gui

### Build instructions ###

To build ASGtechmngr, Apache Maven v3 (or later) and the Java Development Kit (JDK) v1.7 (or later) are required.

1. Execute `mvn clean install -DskipTests`
