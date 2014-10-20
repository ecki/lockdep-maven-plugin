lockdep-maven-plugin
====================

Lock Maven Dependencies by cryptographic hashes

This is work in progress, currently it only builds a
simple maven plugin which logs the dependencies found.

Planned Operation
------------------

* lockdep:verify - **check checksums**.
This will compare the checksums of all dependencies with the file
`.dependencies.lock`. The build is failed if the file is missing
or dependencies mismatch. In both cases a `.dependencies.current`
is created.
* lockdep:create - **create checksums file**. This will compare
a new `.dependencies.current` file.

Options:

* ignoreMissing (boolean) - if a dependency was used but is not contained in the `.dependencies.lock` file this should not fail the build. TODO: also fail if files are listed in the lockfile but not used?
* createCurrent (boolean) - if verify should create a `.dependencies.current` file on mismatch
* lockFile (path) - default: ".dependencies.lock". Location of the lockfile
* currentFile (path) - default: ".dependencies.current". Location of the generated proposed file
* algorithm (string) - default: "SHA-256". Hashing/Checking algorithm to use
* TODO: phase/target - do not check for goals like `clean`?

Building
--------
In order to run the (incomplete) integration tests, you need to modify the POM to contain the path of a maven and java home:

    <mavenHome>C:\devenv\apache-maven-3.0.5</mavenHome> <!-- TODO -->
    <javaHome>C:\Program Files\Java\jdk1.7.0</javaHome> <!--  TODO -->

Then you can use maven to build and test the plugin:

    mvn -Prun-its install
    mvn compile net.eckenfels.mavenplugins:lockdep-maven-plugin:verify

And see the resulting checksums in

    head .dependencies.current
    + B260CA7A23BB0D209771DB7AAE35049899433FE3 org.codehaus.plexus:plexus-interpolation:jar:1.19
    + UNKNOWN org.apache.maven.plugins:maven-clean-plugin:maven-plugin:2.5
    + EAB9A4BAAE8DE96A24C04219236363D0CA73E8A9 org.eclipse.aether:aether-impl:jar:0.9.0.M2
    + DBD94F0744545E17CAA51DB6FC493FC736361837 org.apache.maven:maven-artifact:jar:3.1.1
    + 98FEA8E8C3FB0E8670A69AD6EA445872C9972910 org.codehaus.plexus:plexus-classworlds:jar:2.5.1
    + UNKNOWN org.apache.maven.plugins:maven-site-plugin:maven-plugin:3.3
    ...

(Later on it will resolve the artifacts itself, so you dont need the compile goal).
