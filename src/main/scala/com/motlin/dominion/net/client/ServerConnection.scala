package com.motlin.dominion.net.client

import com.google.inject.Inject
import java.util.concurrent.TimeUnit
import com.motlin.dominion.net.{SocketInputHandler, SocketOutputHandler}
import com.motlin.dominion.net.comm.{Login, Message}

case class ServerConnection @Inject() (socketInputHandler: SocketInputHandler, socketOutputHandler: SocketOutputHandler) extends Runnable
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