package com.motlin.dominion.net

import comm.Close
import java.net.Socket
import java.io.{ObjectOutputStream, BufferedOutputStream}
import com.google.inject.Inject
import actors.Actor
import org.slf4j.LoggerFactory

object SocketOutputHandler
{
	val LOGGER = LoggerFactory.getLogger(classOf[SocketOutputHandler])
}

case class SocketOutputHandler @Inject() (socket: Socket) extends Actor
{
	val outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream))

	def write(writeObject: Any)
	{
		SocketOutputHandler.LOGGER.info("Sending {}.", writeObject)
		outputStream.writeObject(writeObject)
		outputStream.flush()
	}

	def act()
	{
		loop
		{
			react
			{
				case Close =>
				{
					write(Close)
					outputStream.close()
					socket.close()
					exit
				}
				case writeObject =>
				{
					write(writeObject)
				}
			}
		}
	}

	// TODO: consider creating a finalizer to call close()
}