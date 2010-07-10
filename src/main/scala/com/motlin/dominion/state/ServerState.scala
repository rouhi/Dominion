package com.motlin.dominion.state

import collection._
import com.motlin.dominion.Player
import com.motlin.dominion.card.Action

class ServerState
{
	var tables: List[Table] = Nil

	def listTables = tables

	val users = mutable.Map[String, User]()

	def createUser(userName: String) = users.getOrElseUpdate(userName, new User(userName))

	case class User(userName: String)
	{
		var hostedTables: List[Table] = Nil
		var seats: List[Player] = Nil

		def createTable(tableName: String, actions: List[Action]) =
		{
			val (table, player) = Table.host(tableName, this, actions)
			hostedTables ::= table
			tables ::= table
			seats ::= player
		}
	}
}
