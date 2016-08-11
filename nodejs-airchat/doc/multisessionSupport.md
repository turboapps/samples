# Securely Supporting Multiple Connections #

The objective of multiple session support is to allow for aIRChat to easily maintain
multiple active sets of IRC clients so that more than one user could connect to an
established aIRChat server at a time.  The challenge, like in any authenticated system,
is to be able to associate information unique to a given user with whatever information
the server is maintaining for that user, while restricting access to anyone else.

Ideally, aIRChat would not require any of the standard username + password authentication
and registration common to larger systems.  The data aIRChat maintains for users does not
need to be maintained across instances of aIRChat itself, so involving the use of a
database system should be avoided.

# Threats #

## MITM ##

The first and most obvious threat is an adversary who can record information sent between
the aIRChat server and the client.  Since we are dealing with Javascript here, and cannot
reasonably expect to implement strong cryptographic authentication in the browser.  As such,
the design of this system will assume that TLS is available in situations where it matters
(when aIRChat is serving to more than just one user or on a LAN).

## Brute Force ##

This subsystem will only rely on one piece of identifying information from users.  As such,
if an attacker were only concerned with hijacking any other session, they might try to do
so by sending authentication requests to the server hoping to generate a key that maps to
a user’s set of IRC clients (their “session”).

# Assumptions #

1. In situations where aIRChat is expected to serve multiple users, we assume TLS is available.
2. The system running aIRChat can produce cryptographically secure random bits.

The first assumption is required to be able to securely transport data to and from the user,
and without it, it would be easy for a MITM to record a user’s authentication data in transit
to use in a replay attack to hijack that user’s session.

The second assumption is required to be able to produce data that can be used to uniquely
identify a user and also be infeasible for an attacker to produce.

# Architecture #

The implementation of this multiple session feature is simple.  When a user connects, they
are to be prompted to provide a key that identifies them, which would allow them to continue
their active session (connection to IRC networks).  If the user has no such information, the
server can generate it (in the form of a random 32-byte/256-bit string) and sends it to the
user.  After sending this key, the server creates a new session for the user. The user 
stores their random key locally and can use it later to reestablish their access privilege.

# Pseudocode #

## Server ##

    on `connect` do
      send `auth_req`

    on `authenticate <key>` do
      if sessions[key] and queues[key] and inactive[key] then
        send `backlog <queues[key].messages()>`
      else
        send `bad_auth`

    on `create_session` do
      key <- random_bytes(32)
      while sessions[key] do
        key <- random_bytes(32)
      sessions[key] <- {}       // Object maps IRC network names to IRC clients
      queues[key] <- new Queue()// Message queue stores messages while the user is away
      inactive[key] <- false
      send `new_id <key>`

    on `disconnect` do
      key <- socket.key         // User key associated with their socket for automatic retrieval
      if not inactive[key] then
        inactive[key] <- true   // The server will now queue incoming messages for this user.

## Client ##

    on `auth_req` do
      if storage[‘key’] then
        send `authenticate <storage[‘key’]>`
      else
        send `create_session`

    on `bad_auth` do
      notifyUser(REAUTH_ERROR_MSG)
      send `create_session`

    on `new_id <key>` do
      storage[‘key’] = key
