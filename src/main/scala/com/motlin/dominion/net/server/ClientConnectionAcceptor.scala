package com.motlin.dominion.net.server

import java.net.ServerSocket
import actors.threadpool.{TimeUnit, Executors}
import com.google.inject.{Inject, Singleton}
import com.motlin.dominion.net.SocketOutputHandler

@Singleton
case class ClientConnectionAcceptor @Inject() (serverSocket: ServerSocket) extends Runnable
{
	val executorService = Executors.newCachedThreadPool
	var closed = false
	var connectedClients: List[ConnectedClient] = Nil

	def run()
	{
		while(!closed)
		{
			val socket = serverSocket.accept
			val clientInputHandler = new ClientInputHandler(socket)
			val socketOutputHandler = new SocketOutputHandler(socket)
			val connectedClient = new ConnectedClient(clientInputHandler, socketOutputHandler)
			connectedClients ::= connectedClient
			executorService.execute(clientInputHandler)
		}

		executorService.shutdown()
		executorService.awaitTermination(5L, TimeUnit.SECONDS)		
	}

	def close()
	{
		closed = true
		serverSocket.close()
	}

	// TODO: consider creating a finalizer to call close()
}