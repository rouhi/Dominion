package com.motlin.dominion.net.server

class User(name: String)
{
	var connection: Option[ConnectedClient] = None

	def connect(connectedClient: ConnectedClient)
	{
		connection.foreach(_.close())
		connection = Some(connectedClient)
	}
}