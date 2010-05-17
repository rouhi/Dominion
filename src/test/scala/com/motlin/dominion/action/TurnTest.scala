package com.motlin.dominion.action

import com.motlin.dominion.{Player, Supply}
import com.motlin.dominion.card.action.Woodcutter
import com.motlin.dominion.card.treasure.Copper
import com.motlin.dominion.card.vp.Estate
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._

class TurnTest
{
	val player = new Player(new Supply(1))
	player.deck.hand.clear()
	player.deck.hand ++= List(Woodcutter, Estate, Copper, Estate, Copper)
	player.deck.drawPile = List()
	player.deck.discard = List()
	player.startTurn()

	@Test
	def one_turn_smoke_test
	{
		player.startTurn()
		player.play(Woodcutter)
		player.buy(Estate)
		player.play(Copper)
		player.play(Copper)
		player.buy(Estate)

		val allCards = player.deck.hand ++ player.deck.drawPile

		assert(allCards.size === 7)
		assert(allCards.count(_ == Estate) === 4)
	}

	@Test
	def undo_action_smoke_test
	{
		player.startTurn()
		val handBefore = List(player.deck.hand: _*)

		player.play(Woodcutter)
		assert(!player.deck.hand.contains(Woodcutter))
		assert(player.turn.get.table === List(Woodcutter))

		player.undo()
		val handAfter = List(player.deck.hand: _*)
		assert(handAfter === handBefore)
	}

	@Test
	def undo_after_failed_action_due_to_missing_card
	{
		player.startTurn()
		val handBefore = List(player.deck.hand: _*)

		player.play(Woodcutter)

		val exception = intercept[IllegalArgumentException]
		{
			player.play(Woodcutter)
		}
		assert(exception.getMessage === "requirement failed: Hand must contain card: Woodcutter")

		assert(player.turn.get.table === List(Woodcutter))
		player.undo()
		val handAfter = List(player.deck.hand: _*)
		assert(handAfter === handBefore)
	}


	@Test
	def undo_after_failed_action_due_to_one_action
	{
		player.deck.hand.clear()
		player.deck.hand ++= List(Woodcutter, Estate, Copper, Woodcutter, Copper)
		player.startTurn()
		val handBefore = List(player.deck.hand: _*)

		player.play(Woodcutter)

		val exception = intercept[IllegalArgumentException]
		{
			player.play(Woodcutter)
		}
		assert(exception.getMessage === "requirement failed: Player has no actions left.")

		assert(player.turn.get.table === List(Woodcutter))
		player.undo()
		val handAfter = List(player.deck.hand: _*)
		assert(handAfter === handBefore)
	}
}