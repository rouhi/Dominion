package com.motlin.dominion.card

import collection.mutable.{ArrayBuffer, Undoable}
import com.motlin.dominion.{Turn, Game, Player}

trait Action extends Card
{
	def apply(game: Game): Undoable =
	{
		val turn = game.currentTurn.get
		require(turn.actions >= 1, "Player has no actions left.")
		turn.actions -= 1

		turn.table ::= this

		val undoable = new Undoable
		{
			val buys = turn.buys
			val coins = turn.coins
			val table = turn.table.tail
			val hand = ArrayBuffer(turn.player.deck.hand: _*)

			def undo()
			{
				assert(turn.table.head == Action.this)
				turn.buys = buys
				turn.coins = coins
				turn.table = table
				turn.player.deck.hand.clear()
				turn.player.deck.hand += Action.this
				turn.player.deck.hand ++= hand
			}
		}

		apply(turn)

		undoable
	}

	def apply(turn: Turn): Undoable
}
