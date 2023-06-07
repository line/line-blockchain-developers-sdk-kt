package com.linecorp.link.developers.client.api

import java.net.InetAddress
import java.util.Random
import javax.net.ServerSocketFactory

// copy from https://github.com/onobc/spring-framework/blob/f2c2374b588e428575f5db347b7aa5cca19ff3e2/spring-test/src/main/java/org/springframework/test/util/TestSocketUtils.java
/**
 * Simple utility methods for finding available ports on `localhost` for
 * use in integration testing scenarios.
 *
 *
 * This is a limited form of `SocketUtils` which is deprecated in Spring
 * Framework 5.3 and removed in Spring Framework 6.0.
 *
 *
 * `SocketUtils` was introduced in Spring Framework 4.0, primarily to
 * assist in writing integration tests which start an external server on an
 * available random port. However, these utilities make no guarantee about the
 * subsequent availability of a given port and are therefore unreliable (the reason
 * for deprecation and removal).
 *
 *
 * Instead of using `TestSocketUtils` to find an available local port for a server,
 * it is recommended that you rely on a server's ability to start on a random port
 * that it selects or is assigned by the operating system. To interact with that
 * server, you should query the server for the port it is currently using.
 *
 * @author Sam Brannen
 * @author Ben Hale
 * @author Arjen Poutsma
 * @author Gunnar Hillert
 * @author Gary Russell
 * @author Chris Bono
 * @since 5.3
 */
object TestSocketUtils {
    /**
     * The minimum value for port ranges used when finding an available TCP port.
     */
    private const val PORT_RANGE_MIN = 1024

    /**
     * The maximum value for port ranges used when finding an available TCP port.
     */
    private const val PORT_RANGE_MAX = 65535
    private const val PORT_RANGE = PORT_RANGE_MAX - PORT_RANGE_MIN
    private const val MAX_ATTEMPTS = 1000
    private val random = Random(System.nanoTime())

    /**
     * Find an available TCP port randomly selected from the range [1024, 65535].
     * @return an available TCP port number
     * @throws IllegalStateException if no available port could be found within max attempts
     */
    @Suppress("MaxLineLength")
    fun findAvailableTcpPort(): Int {
        var candidatePort: Int
        var searchCounter = 0
        do {
            check(searchCounter <= MAX_ATTEMPTS) {
                "Could not find an available TCP port in the range [$PORT_RANGE_MIN, $PORT_RANGE_MAX] after $MAX_ATTEMPTS attempts"
            }
            candidatePort = PORT_RANGE_MIN + random.nextInt(PORT_RANGE + 1)
            searchCounter++
        } while (!isPortAvailable(candidatePort))
        return candidatePort
    }

    /**
     * Determine if the specified TCP port is currently available on `localhost`.
     */
    @Suppress("SwallowedException")
    private fun isPortAvailable(port: Int): Boolean {
        return try {
            val serverSocket = ServerSocketFactory.getDefault().createServerSocket(
                port, 1, InetAddress.getByName("localhost")
            )
            serverSocket.close()
            true
        } catch (ex: Exception) {
            false
        }
    }
}
