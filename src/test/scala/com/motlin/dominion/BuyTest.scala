package com.motlin.dominion

import card.vp.Estate
import card.treasure.Copper
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._

class BuyTest
{
	val player = new Player(new Supply(1))
	player.deck.hand.clear()

	@Test
	def player_with_0_can_afford_copper
	{
		player.deck.drawPile = List()
		player.buy(Copper)

		assert(player.deck.drawPile === List())
		assert(player.deck.discard === List(Copper))
		assert(player.deck.hand.isEmpty)
	}

	@Test
	def player_with_0_can_not_afford_estate
	{
		intercept[IllegalStateException]
		{
			player.buy(Estate)
		}
	}

	@Test
	def player_with_2_can_afford_estate
	{
		buyEstate()
		assert(player.deck.discard === List(Estate))
	}

	@Test
	def buy_takes_card_from_supply
	{
		val initialCount = player.supply.count(Estate)
		buyEstate()
		assert(player.supply.count(Estate) === initialCount - 1)
	}

	private def buyEstate()
	{
		player.deck.hand ++= List(Estate, Copper, Estate, Copper, Estate)
		player.play(Copper)
		player.play(Copper)
		player.buy(Estate)
	}
}