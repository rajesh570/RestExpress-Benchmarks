RestExpress Benchmarks
======================
This project is to facilitate performance benchmarking against RestExpress.  It implements a simple set of test routes that can be run simply via

    ant run

Currently, the implemented routes are:

    localhost:9000/echo?echo=somethingtoecho

Which will produce a response of the form:

    <http_test><value>somethingtoecho</value></http_test>

If the 'echo' query-string parameter is omitted, the response is:

   <http_test><error>no value specified</error></http_test>

This test suite utilizes Jackson for JSON processing (serializing and deserializing) and supports Jackson annotations on domain objects.  However, no date processing (for ISO8601 dates) is configured.
