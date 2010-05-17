package com.motlin.dominion.card

import com.motlin.dominion.Player
import collection.mutable.{ArrayBuffer, Undoable}

trait Action extends Card
{
	def apply(player: Player): Undoable =
	{
		val turn = player.turn.get
		require(turn.actions >= 1, "Player has no actions left.")
		turn.actions -= 1

		turn.table ::= this

		val undoable = new Undoable
		{
			val buys = turn.buys
			val coins = turn.coins
			val table = turn.table.tail
			val hand = ArrayBuffer(player.deck.hand: _*)

			def undo()
			{
				assert(turn.table.head == Action.this)
				turn.buys = buys
				turn.coins = coins
				turn.table = table
				player.deck.hand.clear()
				player.deck.hand += Action.this
				player.deck.hand ++= hand
			}
		}

		apply(turn)

		undoable
	}

	def apply(turn: Player#Turn): Unit
}