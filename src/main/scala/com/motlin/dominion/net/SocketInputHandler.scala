package com.motlin.dominion.net

import java.net.Socket
import java.io.{IOException, ObjectInputStream, BufferedInputStream}

abstract class SocketInputHandler(socket: Socket) extends Runnable
{
	val inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream))
	@volatile var closed = false

	def handle(readObject: AnyRef)

	def run
	{
		try
		{
			while(!closed)
			{
				handle(inputStream.readObject)
			}
		}
		catch
		{
			case ignored: IOException =>
		}
		finally
		{
			this.cleanUp()
		}
	}

	def close()
	{
		this.closed = true
	}

	def cleanUp()
	{
		inputStream.close()
		socket.close()
	}

	// TODO: consider creating a finalizer to call close()	
}