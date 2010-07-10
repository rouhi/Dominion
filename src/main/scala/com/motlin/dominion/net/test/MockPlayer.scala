package com.motlin.dominion.net.test

import com.motlin.dominion.state.{Table, ServerState}
import com.motlin.dominion.{Supply, Player, Deck}

class MockPlayer(override val deck: Deck) extends Player(user: ServerState#User, table: Table)
{
	def this(supply: Supply)
	{
		this(Deck(), supply)
	}

	var actions = List[(MockPlayer) => Unit]()
	def queueAction(action: (MockPlayer) => Unit)
	{
		actions ::= action
	}

	def takeTurn()
	{
		val action = actions.head
		actions = actions.tail
		action(this)
	}
}
