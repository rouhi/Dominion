package com.motlin.dominion.net.client

import com.motlin.dominion.net.SocketInputHandler
import java.net.Socket

class ServerInputHandler(serverSocket: Socket) extends SocketInputHandler(serverSocket)
{
	def handle(readObject: AnyRef)
	{
		// TODO
		println(readObject)
	}
}