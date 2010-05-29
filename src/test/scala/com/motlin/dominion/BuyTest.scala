package com.motlin.dominion

import card.vp.Estate
import card.treasure.Copper
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._
import java.lang.IllegalArgumentException
import collection.mutable.ArrayBuffer

class BuyTest
{
	@Test
	def player_with_0_can_afford_copper
	{
		class FakePlayer(override val supply: Supply) extends Player(supply)
		{
			override val deck = new FakeDeck(ArrayBuffer(), Nil)
			def takeTurn() =
			{
				this.buy(Copper)
			}

		}
		val player = new FakePlayer(new Supply(1))
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
			override val deck = new FakeDeck(ArrayBuffer(), Nil)
			def takeTurn() =
			{
				exception = intercept[IllegalArgumentException]
				{
					this.buy(Estate)
				}
			}

		}
		val player = new FakePlayer(new Supply(1))
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
			override val deck = new FakeDeck(ArrayBuffer(Copper, Estate, Copper), Nil)
			def takeTurn() =
			{
				this.play(Copper)
				this.play(Copper)
				this.buy(Estate)
			}
		}
		new FakePlayer(new Supply(1))
	}
}