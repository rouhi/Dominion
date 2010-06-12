package com.motlin.dominion.net.client

import com.google.inject.Inject
import java.util.concurrent.TimeUnit
import com.motlin.dominion.net.SocketOutputHandler
import java.io.Serializable
import com.motlin.dominion.net.client.ServerConnection.Message

object ServerConnection
{
	case class Message(embedded: String) extends Serializable
}

case class ServerConnection @Inject() (socketOutputHandler: SocketOutputHandler) extends Runnable
{
	def run()
	{
		for (i <- 1 to 10)
		{
			socketOutputHandler.send(Message("Hello world " + i.toString))
			Thread.sleep(TimeUnit.MILLISECONDS.convert(1L, TimeUnit.SECONDS))
		}

		socketOutputHandler.close()
	}
}