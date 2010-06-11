package com.motlin.dominion.server

import java.net.ServerSocket
import actors.threadpool.{TimeUnit, Executors}
import com.google.inject.{Inject, Singleton}

@Singleton
class ClientConnectionAcceptor @Inject() (serverSocket: ServerSocket) extends Runnable
{
	val executorService = Executors.newCachedThreadPool
	var closed = false

	def run()
	{
		while(!closed)
		{
			val connectedClient = new ConnectedClient(serverSocket.accept)
			executorService.execute(connectedClient)
		}
	}

	def close()
	{
		closed = true
		executorService.shutdown()
		executorService.awaitTermination(5L, TimeUnit.SECONDS)
		serverSocket.close()
	}

	// TODO: consider creating a finalizer to call close()
}