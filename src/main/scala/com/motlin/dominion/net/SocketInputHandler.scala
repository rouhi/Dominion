package com.motlin.dominion.net

import java.net.Socket
import java.io.{IOException, ObjectInputStream, BufferedInputStream}

abstract class SocketInputHandler(socket: Socket) extends Runnable
{
	@volatile var closed = false
	var inputStream: ObjectInputStream = _

	def handle(readObject: AnyRef)

	def run
	{
		try
		{
			inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream))
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
		if (inputStream != null)
		{
			inputStream.close()
		}
		socket.close()
	}

	// TODO: consider creating a finalizer to call close()	
}