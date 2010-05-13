package com.motlin.dominion

import card.{Estate, Copper}
import org.junit.{Test, Assert}

class BuyTest
{
	val player = new Player
	player.deck.hand.clear()


	@Test
	def player_with_0_can_afford_copper
	{
		player.deck.drawPile = List()
		player.buy(Copper)

		Assert.assertEquals(List(), player.deck.drawPile)
		Assert.assertEquals(List(Copper), player.deck.discard)
		Assert.assertTrue(player.deck.hand.isEmpty)
	}

	// TODO expect specific exception
	@Test(expected = classOf[Exception])
	def player_with_0_can_not_afford_estate
	{
		player.buy(Estate)
	}

	@Test
	def player_with_2_can_afford_estate
	{
		player.deck.hand ++= List(Estate, Copper, Estate, Copper, Estate)
		player.play(Copper)
		player.play(Copper)
		player.buy(Estate)
		Assert.assertEquals(List(Estate), player.deck.discard)
	}
}