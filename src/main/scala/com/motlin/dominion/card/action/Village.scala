package com.motlin.dominion.card.action

import com.motlin.dominion.Player
import com.motlin.dominion.card.Action

case object Village extends Action
{
	val cost = 3

	def apply(turn: Player#Turn)
	{
		turn.actions += 1
		turn. += 2
	}
}
