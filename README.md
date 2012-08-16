RestExpress Benchmarks
======================
This project is to facilitate performance benchmarking against RestExpress.  It implements a simple set of test routes that can be run simply via:

    ant run

Currently, the implemented routes are:

    localhost:9000/echo?echo=somethingtoecho

Which will produce a response of the form:

    <http_test><value>somethingtoecho</value></http_test>

If the 'echo' query-string parameter is omitted, the response is:

    <http_test><error>no value specified</error></http_test>

Dependencies
============
This test suite utilizes Jackson for JSON processing (serializing and deserializing) and supports Jackson annotations on domain objects.
However, no date processing (for ISO8601 dates) is configured.  Note that because RestExpress currently supports GSON-based marshaling
by default, it still requires the GSON jar even though the Main.java class resets the defaults and implements Jackson marshaling (this
will be fixed in a later release of RestExpress).

MongoDB libraries are present to enable route creation for reading/writing MongoDB.  Morphia is included in case the RepoExpress MongoDB
repository functionality is desired.

Also, while not currently leveraged, the Syntax domain validation library is included also to enable annotations-based validation of
domain object properties.

Configuration
=============
This benchmark suite supports setting of the RestExpress executor pools for NIO worker threads and background controller processing.
In essence, the NIO workers handle all incoming traffic without blocking, passing the request off to the back-end executor, which
handles controller processing--assumed to be blocking (like MongoDB access).

By default, Netty (and therefore RestExpress) uses 2x the number of cores for NIO workers.  This value should be acceptable for most
conditions.

For executor threads, the setting is dependent on how much processing occurs in the controller method (er... how long it takes to
execute a controller method).  I currently recommend setting it to match the number threads set for Tomcat.

Configuration is read from the './config/dev/environment.properties' file.  An example file is below:

    # Default is 8081
port = 9000
#
#Display Output? True impacts performance, but offers debugging via output capability
displayOutput = true
#
# The name of the service suite
name = RestExpress Benchmarks
#
# The default service response format (default is JSON)
defaultFormat = json
#
# Any value above zero will be used to set the number of NIO worker threads:
# Default is 0 => 2 x #cores
# This controls the number of concurrent requests the app can handle.
workerCount = 0
#
# This controls the number of concurrent blocking operations that the app can process concurrently.
executorThreadCount = 200

_workerCount_ being set to zero allows RestExpress to determine the number of cores on the machine and create 2 times the number of cores for NIO workers.

_executorThreadCount_ should simply be set to an arbitrarily-high number, preferably matching the setting for the number of threads within Tomcat. 