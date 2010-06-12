package com.motlin.dominion.net

import java.net.Socket
import java.io.{ObjectOutputStream, BufferedOutputStream}
import com.google.inject.Inject

case class SocketOutputHandler @Inject() (socket: Socket)
{
	val outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream))

	def send(writeObject: AnyRef)
	{
		this.outputStream.writeObject(writeObject)
		this.outputStream.flush()
	}

	def close()
	{
		outputStream.close()
		socket.close()
	}

	// TODO: consider creating a finalizer to call close()
}