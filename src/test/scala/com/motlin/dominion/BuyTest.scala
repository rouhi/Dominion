package com.motlin.dominion

import card.vp.Estate
import card.treasure.Copper
import org.junit.{Test, Assert}

class BuyTest
{
	val player = new Player(new Supply(1))
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
		buyEstate()
		Assert.assertEquals(List(Estate), player.deck.discard)
	}

	@Test
	def buy_takes_card_from_supply
	{
		val initialCount = player.supply.count(Estate)
		buyEstate()
		Assert.assertEquals(initialCount - 1, player.supply.count(Estate))
	}

	private def buyEstate()
	{
		player.deck.hand ++= List(Estate, Copper, Estate, Copper, Estate)
		player.play(Copper)
		player.play(Copper)
		player.buy(Estate)
	}
}