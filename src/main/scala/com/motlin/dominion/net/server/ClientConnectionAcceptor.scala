package com.motlin.dominion.net.server

import com.motlin.dominion.net.{NamedThreadFactory, SocketOutputHandler}

import java.net.{SocketException, ServerSocket}

import actors.threadpool.{TimeUnit, Executors}

import com.google.inject.{Inject, Singleton}
import org.slf4j.LoggerFactory

object ClientConnectionAcceptor
{
	val LOGGER = LoggerFactory.getLogger(classOf[ClientConnectionAcceptor])
}

// TODO don't pass in server, we need a ConnectedClient factory instead
@Singleton
case class ClientConnectionAcceptor @Inject() (serverSocket: ServerSocket) extends Runnable
{
	val executorService = Executors.newCachedThreadPool(new NamedThreadFactory(classOf[ClientConnectionAcceptor].getSimpleName))
	var closed = false
	var connectedClients: List[ConnectedClient] = Nil

	def run()
	{
		try
		{
			while(!closed)
			{
				val socket = serverSocket.accept
				val connectedClient = new ConnectedClient(socket)
				connectedClients ::= connectedClient
				connectedClient.start
			}
		}
		catch
		{
			case e: SocketException =>
			{
				ClientConnectionAcceptor.LOGGER.debug("", e)
				connectedClients.foreach(_.close())
			}
		}
		finally
		{
			executorService.shutdown()
			executorService.awaitTermination(5L, TimeUnit.SECONDS)
		}
	}

	def close()
	{
		closed = true
		serverSocket.close()
		// TODO: shut down all connectedClients
	}

	// TODO: consider creating a finalizer to call close()
}
