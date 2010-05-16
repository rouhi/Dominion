package com.motlin.dominion

import card.vp.Estate
import card.Copper
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._

class BuyTest
{
	val player = new Player
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
		player.deck.hand ++= List(Estate, Copper, Estate, Copper, Estate)
		player.play(Copper)
		player.play(Copper)
		player.buy(Estate)
		assert(player.deck.discard === List(Estate))
	}
}