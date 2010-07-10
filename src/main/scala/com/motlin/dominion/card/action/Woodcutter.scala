package com.motlin.dominion.card.action

import com.motlin.dominion.card.Action
import com.motlin.dominion.{Game, Player}

case object Woodcutter extends Action
{
	val cost = 3

	def apply(game: Game)
	{
		val turn = game.currentTurn.get
		turn.buys += 1
		turn.coins += 2
	}
}
