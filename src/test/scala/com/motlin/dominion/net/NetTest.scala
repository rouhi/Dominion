package com.motlin.dominion.net

import client.ServerConnection
import java.net.{Socket, ServerSocket}
import org.junit.{After, Test}
import server.ConnectedClient
import java.util.concurrent.CountDownLatch
import actors.{Future, Futures}
import org.slf4j.LoggerFactory

object NetTest
{
	val LOGGER = LoggerFactory.getLogger(classOf[NetTest])
}

class NetTest
{
	val serverSocket: ServerSocket = new ServerSocket(10000)
	val socket: Socket = new Socket("localhost", 10000)

	@After
	def tearDown
	{
		socket.close()
		serverSocket.close()
	}

	@Test
	def smoke_test
	{
		val latch = new CountDownLatch(2)
		NetTest.LOGGER.trace("")
		val futureSocket: Future[Socket] = Futures.future
		{
			NetTest.LOGGER.trace("")
			val socket1: Socket = serverSocket.accept
			NetTest.LOGGER.trace("")
			socket1
		}

		Thread.sleep(1)

		NetTest.LOGGER.trace("")
		val serverConnection = new ServerConnection(socket, new SocketOutputHandler(socket))
		{
			override def exit() =
			{
				NetTest.LOGGER.trace("")
				latch.countDown()
				super.exit()
			}
		}
		serverConnection.start()
		NetTest.LOGGER.trace("")

		val clientSocket = futureSocket()

		NetTest.LOGGER.trace("")
		val connectedClient = new ConnectedClient(clientSocket)
		{
			override def exit() =
			{
				NetTest.LOGGER.trace("")
				latch.countDown()
				super.exit()
			}
		}
		connectedClient.start()

		NetTest.LOGGER.trace("")
		latch.await()
		NetTest.LOGGER.trace("")
	}
}