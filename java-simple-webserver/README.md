This is a very small standalone web server written in Java. It is packaged in a jar file and may also be used within your own Java programs. It is a fork of the "Java Mini Web Server" (http://www.jibble.org/miniwebserver) written by Paul Mutton. 

I have made some small but useful enhancements, like the hability to easily stop the webserver if you have it embedded in your project, as well to change its root dir without stopping the server. 

## Starting the Web Server (standalone mode)
The web server is packaged in a single small jar file for ease of use. You will need Java runtime version 1.5 or later installed in order to execute the web server.

To execute the web server, issue the following command:

    java -jar SimpleWebServer.jar

This will start the web server on the default port (80) and your web root directory will be the current directory. You should now be able to request web pages from the web server with your web browser.

## Embedding it in your program
The package may also be used as a Java HTTP Web Server Library. Simply add the jar file to your classpath and import org.jibble.simplewebserver.*;. You can then construct a new SimpleWebServer object by supplying two arguments to its constructor:

    SimpleWebServer server = new SimpleWebServer(new File("./"), 80);

This starts up the web server within your program so that users may request web pages.

## Features
Considering the fact that this very small web server is less than 4kb in size, it still has a fair few useful features:-

* Very small executable.
* Can deal with multiple requests at the same time.
* Support for a small variety of content-types (images, HTML, text, etc).
* Directory browsing features.
* Index page retrieval without specifying full path.

## Licensing
This software product is OSI Certified Open Source Software, available under the GNU General Public License (GPL). If you need a commercial license, please contact Paul Mutton at http://www.jibble.org/licenses