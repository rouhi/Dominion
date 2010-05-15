package com.motlin.dominion

import card.Copper
import card.vp.Estate
import org.junit.{Test, Assert}
import collection.mutable.ListBuffer

class PlayerTest
{
	val player = new Player
	player.deck.hand.clear()

	@Test
	def play_copper_for_one_coin
	{
		player.deck.hand ++= List(Estate, Copper, Estate, Copper)
		player.play(Copper)
		Assert.assertEquals(1, player.coins)
		Assert.assertEquals(ListBuffer(Estate, Estate, Copper), player.deck.hand)
	}

	// TODO This syntax for expecting exceptions doesn't actually work
	@Test(expected = classOf[IllegalArgumentException])
	def can_not_play_card_not_held
	{
		player.deck.hand ++= List(Estate, Estate, Estate)
		player.deck.drawPile = List(Copper, Estate)
		player.play(Copper)
	}

	// TODO This syntax for expecting exceptions doesn't actually work
	@Test(expected = classOf[IllegalArgumentException])
	def can_not_play_victory_point_cards
	{
		player.deck.hand ++= List(Estate, Copper, Estate)
		player.play(Estate)
	}
}