package com.motlin.dominion.net.test

import com.motlin.dominion.{Player, Supply, Deck}

class MockPlayer(override val deck: Deck, supply: Supply) extends Player(supply)
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
