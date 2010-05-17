package com.motlin.dominion.card.action

import com.motlin.dominion.card.Action
import com.motlin.dominion.Player

case object Woodcutter extends Action
{
	val cost = 3

	def apply(turn: Player#Turn)
	{
		turn.buys += 1
		turn.coins += 2
	}
}