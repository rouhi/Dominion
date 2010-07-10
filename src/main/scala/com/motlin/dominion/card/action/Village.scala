package com.motlin.dominion.card.action

import com.motlin.dominion.card.Action
import com.motlin.dominion.{Game, Player}

case object Village extends Action
{
	val cost = 3

	def apply(game: Game)
	{
		game.currentTurn.get.actions += 2
		game.currentTurn.get.player.deck.drawOneCard()
	}
}
