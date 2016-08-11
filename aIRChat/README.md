aIRChat
=======

A beautiful browser-based IRC client built on Node.

![What it looks like now](https://i.imgur.com/2hrmEw4.png)

Requirements
============

To be able to run aIRChat, you need to have installed [Node.js](http://nodejs.org/).

Configuration
=============

Configuration information for aIRChat is contained in the file `config.js`.

The value of `exports.host` should be the address of the machine running the server.
If you are running the aIRChat server on your personal computer for your exclusive use,
you can leave this value alone.

The value of `exports.sessionTimeout` should be the number of milliseconds you want
to allow user sessions to be maintained for.  The larger this value, the longer
messages and chat clients will be maintained for you and your users.

Downloading
===========

Instructions for installing and running aIRChat are provided on the site.
Note that the site hosts a stable release of aIRChat which may not contain
the newest features (and bugs!) implemented in the master branch of this repository.

Running it yourself
===================

To run aIRChat yourself from the source, after cloning the repository,
inside the Content directory, run:

    npm install

Next, start up the application server.

    node app.js

You're done! You can now visit your aIRChat instance on localhost, port 3000.
That is, simply enter "localhost:3000" into your browser's address bar.

You can stop the aIRChat server at any time by pressing "Ctrl+c" on your keyboard
with the terminal running the node process active.
