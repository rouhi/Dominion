package com.motlin.dominion.action

import com.motlin.dominion.card.action.Woodcutter
import com.motlin.dominion.card.treasure.Copper
import com.motlin.dominion.card.vp.Estate
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._
import collection.mutable.ArrayBuffer
import com.motlin.dominion.card.Card
import com.motlin.dominion.{Deck, Player, Supply}

class TurnTest
{
	@Test
	def one_turn_smoke_test
	{
		val player = buildPlayer(p =>
		{
			p.play(Woodcutter)
			p.buy(Estate)
			p.play(Copper)
			p.play(Copper)
			p.buy(Estate)
		})

		player.startTurn()

		val allCards = player.deck.hand ++ player.deck.drawPile

		assert(allCards.size === 7)
		assert(allCards.count(_ == Estate) === 4)
	}

	@Test
	def undo_action_smoke_test
	{
		val player = buildPlayer(p =>
		{
			p.play(Woodcutter)
		})
		val handBefore = List(player.deck.hand: _*)
		player.startTurn()

		assert(!player.deck.hand.contains(Woodcutter))
		assert(player.turn.get.table === List(Woodcutter))

		player.undo()
		val handAfter = List(player.deck.hand: _*)
		assert(handAfter === handBefore)
	}

	@Test
	def undo_after_failed_action_due_to_missing_card
	{
		var exception = new Exception
		val player = buildPlayer(p =>
		{
			p.play(Woodcutter)
			exception = intercept[IllegalArgumentException]
			{
				p.play(Woodcutter)
			}
		})


		val handBefore = List(player.deck.hand: _*)
		player.startTurn()

		assert(exception.getMessage === "requirement failed: Hand must contain card: Woodcutter")

		assert(player.turn.get.table === List(Woodcutter))
		player.undo()
		val handAfter = List(player.deck.hand: _*)
		assert(handAfter === handBefore)
	}


	@Test
	def undo_after_failed_action_due_to_one_action
	{
		var exception = new Exception
		val player = buildPlayerWithHand(ArrayBuffer(Woodcutter, Estate, Copper, Woodcutter, Copper), p =>
		{
			p.play(Woodcutter)
			exception = intercept[IllegalArgumentException]
			{
				p.play(Woodcutter)
			}
		})

		val handBefore = List(player.deck.hand: _*)
		player.startTurn()

		assert(exception.getMessage === "requirement failed: Player has no actions left.")

		assert(player.turn.get.table === List(Woodcutter))
		player.undo()
		val handAfter = List(player.deck.hand: _*)
		assert(handAfter === handBefore)
	}

	private def buildPlayer(action: Player => Unit): Player =
	{
		buildPlayerWithHand(ArrayBuffer(Woodcutter, Estate, Copper, Estate, Copper), action)
	}

	private def buildPlayerWithHand(hand: ArrayBuffer[Card], action: Player => Unit): Player =
	{
		class FakePlayer(override val supply: Supply) extends Player(supply)
		{
			override val deck = Deck(hand, Nil, Nil)
			def takeTurn() =
			{
				action(this)
			}
		}
		new FakePlayer(new Supply(1))
	}
}