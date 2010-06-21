package com.motlin.dominion.net.server

import collection._
import com.google.inject.{Singleton, Inject}

@Singleton
case class ServerState @Inject() () 
{
	val users = mutable.Map[String, User]()
	val loggedIn = mutable.Map[String, ConnectedClient]()

	def login(username: String, connectedClient: ConnectedClient)
	{
		val user = users.getOrElseUpdate(username, new User(username))
		user.connect(connectedClient)
	}
}