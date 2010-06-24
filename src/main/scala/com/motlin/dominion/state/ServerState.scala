package com.motlin.dominion.state

import collection._

class ServerState
{
	var tables: List[Table] = Nil

	def listTables = tables

	val users = mutable.Map[String, User]()

	def createUser(userName: String) = users.getOrElseUpdate(userName, new User(userName))

	case class User(userName: String)
	{
		var hostedTables: List[Table] = Nil

		def createTable(tableName: String)
		{
			val table = new Table(tableName, this)
			hostedTables ::= table
			tables ::= table
		}
	}
}
