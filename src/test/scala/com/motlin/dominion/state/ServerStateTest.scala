package com.motlin.dominion.state

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._

class ServerStateTest
{
	@Test
	def user_smoke_test
	{
		val serverState = new ServerState()
		val tables: List[Table] = serverState.listTables
		assert(tables === Nil)

		val user: ServerState#User = serverState.createUser("test user")
		user.createTable("my table")
		val tables2 = serverState.listTables
		assert(tables2.size === 1)
		assert(tables2.head.name === "my table")
	}

}
