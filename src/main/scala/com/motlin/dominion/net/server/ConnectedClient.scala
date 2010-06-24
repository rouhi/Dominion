package com.motlin.dominion.net.server

import java.net.Socket
import actors.Actor
import com.motlin.dominion.net.comm._
import actors.threadpool.{TimeUnit, Executors}
import org.slf4j.LoggerFactory
import com.motlin.dominion.net.{NamedThreadFactory, SocketInputHandler, SocketOutputHandler}

object ConnectedClient
{
	val LOGGER = LoggerFactory.getLogger(classOf[ConnectedClient])
}

case class ConnectedClient(clientSocket: Socket) extends Actor
{
	val socketInputHandler = new SocketInputHandler(clientSocket)
	{
		def handle(readObject: AnyRef)
		{
			ConnectedClient.this ! readObject
		}
	}

	val executorService = Executors.newSingleThreadExecutor(new NamedThreadFactory(classOf[ConnectedClient].getSimpleName))
	executorService.execute(socketInputHandler)

	val socketOutputHandler: SocketOutputHandler = new SocketOutputHandler(clientSocket)
	socketOutputHandler.write(Welcome)

	def act()
	{
		loop
		{
			react
			{
				case Login(username) =>
				{
					ConnectedClient.LOGGER.info("Got login request from {}.", username)
					socketOutputHandler.write(LoggedIn(true))
				}
				case Ping =>
				{
					ConnectedClient.LOGGER.info("Got Ping, sending Pong.")
					socketOutputHandler.write(Pong)
				}
				case Close =>
				{
					this.close()
				}
				case other =>
				{
					ConnectedClient.LOGGER.info("Unexpected message: {}", other.toString)
				}
			}
		}
	}

	def close()
	{
		ConnectedClient.LOGGER.info("Client closing.")
		socketOutputHandler.close()
		socketInputHandler.close()
		executorService.shutdown()
		executorService.awaitTermination(10L, TimeUnit.SECONDS)
		this.exit()
	}
}
