package com.motlin.dominion.server

import java.io.{ObjectOutputStream, BufferedOutputStream, ObjectInputStream, BufferedInputStream}
import java.net.Socket

class ConnectedClient(clientSocket: Socket) extends Runnable
{
	val outputStream = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream))
	val inputStream = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream))
	var closed = false

	def run()
	{
		while(!closed)
		{
			val readObject = inputStream.readObject
			// TODO
			println(readObject)
		}
	}

	def close()
	{
		outputStream.close()
		inputStream.close()
		clientSocket.close()
	}

	// TODO: consider creating a finalizer to call close()
}