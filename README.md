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


