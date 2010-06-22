package com.motlin.dominion.state

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._

class ServerStateTest
{
	@Test
	def user_smoke_test
	{
		val serverState = new ServerState()
		val tables = serverState.listTables
		assert(tables === Nil)

		val user = serverState.createUser("test user")
		user.createTable("my table")
		val tables2 = serverState.listTables
		assert(tables.size === 1)
		assert(tables.first.name === "my table")
	}

}
