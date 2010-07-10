package com.motlin.dominion.state

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._
import com.motlin.dominion.card.action.Woodcutter

class ServerStateTest
{
	val serverState = new ServerState()
	val user: ServerState#User = serverState.createUser("test user")

	@Test
	def user_smoke_test
	{
		val tables: List[Table] = serverState.listTables
		assert(tables === Nil)

		val table = user.createTable("my table")
		val tables2 = serverState.listTables
		assert(tables2 === List(table))

		user.createTable("my table 2")
		assert(serverState.listTables === user.hostedTables)
	}

	@Test
	def start_game_with_one_player_fails
	{
		val (table, player) = user.createTable("my table")
//		val user2 = serverState.createUser("second user")
		assert(!table.gameInProgress)
		exception = intercept[IllegalArgumentException]
		{
			table.startGame(List.fill(10)(Woodcutter))
		}
		assert(exception.getMessage === "You may only start the game with 2 to 4 players.  Found 1 player")
	}
}
