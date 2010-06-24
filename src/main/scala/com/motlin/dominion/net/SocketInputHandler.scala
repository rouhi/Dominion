package com.motlin.dominion.net

import java.net.Socket
import java.io.{IOException, ObjectInputStream, BufferedInputStream}
import org.slf4j.LoggerFactory

object SocketInputHandler
{
	val LOGGER = LoggerFactory.getLogger(classOf[SocketInputHandler])
}

abstract class SocketInputHandler(socket: Socket) extends Runnable
{
	var inputStream: ObjectInputStream = _

	def handle(readObject: AnyRef)

	def run
	{
		try
		{
			inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream))
			while(true)
			{
				handle(inputStream.readObject)
			}
		}
		catch
		{
			case ignored: IOException =>
			{
				SocketInputHandler.LOGGER.info("", ignored)
			}
		}
		finally
		{
			this.close()
		}
	}

	def close()
	{
		inputStream.close()
		socket.close()
	}

	// TODO: consider creating a finalizer to call close()	
}