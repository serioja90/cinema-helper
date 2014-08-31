cinema-helper
=============

A web application developed in Netbeans and that runs over JSP.

The application has a worker that parses information from different cinema sites and insert that information
into a local database. Then, the frotend part of application provides the web UI to access that information.

Installation
------------

If you want to run the application by your self, you will need to configure a Derby ebmeded database and
to run the queries from `scr/java/lib/DDL.sql`.

Then you will have to configure your application by providing information the application needs to connect to
your Derby database. You can edit application configurations in `web/WEB-INF/web.xml`.


Authors
-------
Groza Sergiu
