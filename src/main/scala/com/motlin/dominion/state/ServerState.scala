package com.motlin.dominion.state

import collection._

class ServerState
{
	val users = mutable.Map[String, User]()

	var tables: List[Table] = Nil


	def createUser(userName: String)
	{
		val user = users.getOrElseUpdate(userName, new User(userName))
	}

	def listTables = tables
}
