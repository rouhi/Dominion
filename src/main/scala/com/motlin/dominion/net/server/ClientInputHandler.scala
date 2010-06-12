package com.motlin.dominion.net.server

import java.net.Socket
import com.motlin.dominion.net.SocketInputHandler

class ClientInputHandler(clientSocket: Socket) extends SocketInputHandler(clientSocket)
{
	def handle(readObject: AnyRef)
	{
		// TODO
		println(readObject)
	}
}