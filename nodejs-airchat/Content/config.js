/** Client Configuration
  * Used to configure information the client needs to create a socket.io
  * connection to the server
  */
exports.host = 'http://localhost';

/** Session Timeout
  * Specifies the number of milliseconds to hold onto a user's session between
  * connections.  To convert from hours to milliseconds, multiply hours (h)
  * by 3,600,000.
  */
exports.sessionTimeout = 7200000; // Two days
