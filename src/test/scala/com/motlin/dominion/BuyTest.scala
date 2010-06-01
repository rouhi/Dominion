package com.motlin.dominion

import collection.mutable.ArrayBuffer

import card.treasure.Copper
import card.vp.Estate
import net.test.MockPlayer

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._

class BuyTest
{
	@Test
	def player_with_0_can_afford_copper
	{
		val supply = new Supply(1, Nil)
		val player = new MockPlayer(Deck(ArrayBuffer(), Nil, Nil), supply)
		player.queueAction(_.buy(Copper))
		player.startTurn()

		assert(player.deck.drawPile === List())
		assert(player.deck.discard === List())
		assert(player.deck.hand === List(Copper))
		assert(player.turn === None)
	}

	@Test
	def player_with_0_can_not_afford_estate
	{
		var exception = new Exception
		class FakePlayer(override val supply: Supply) extends Player(supply)
		{
			override val deck = Deck(ArrayBuffer(), Nil, Nil)
			def takeTurn() =
			{
				exception = intercept[IllegalArgumentException]
				{
					this.buy(Estate)
				}
			}

		}
		val player = new FakePlayer(new Supply(1, Nil))
		player.startTurn()

		assert(exception.getMessage.contains("Cannot afford card: Estate"))
	}

	@Test
	def player_with_2_can_afford_estate
	{
		val player = buildEstateBuyingPlayer
		player.startTurn()

		assert(player.deck.drawPile === List())
		assert(player.deck.discard === List())
		assert(player.deck.hand.sortBy(_.toString) === List(Copper, Copper, Estate, Estate))
	}

	@Test
	def buy_takes_card_from_supply
	{
		val player = buildEstateBuyingPlayer
		val initialCount = player.supply.count(Estate)
		player.startTurn()
		assert(player.supply.count(Estate) === initialCount - 1)
	}

	private def buildEstateBuyingPlayer(): Player =
	{
		class FakePlayer(override val supply: Supply) extends Player(supply)
		{
			override val deck = Deck (ArrayBuffer(Copper, Estate, Copper), Nil, Nil)
			def takeTurn() =
			{
				this.play(Copper)
				this.play(Copper)
				this.buy(Estate)
			}
		}
		new FakePlayer(new Supply(1, Nil))
	}
}
