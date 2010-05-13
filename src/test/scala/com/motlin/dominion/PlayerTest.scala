package com.motlin.dominion

import card.{Estate, Copper}
import org.junit.{Assert, Test}

class PlayerTest
{
	val player = new Deck

	@Test
	def starts_with_hand_of_5_cards
	{
		Assert.assertEquals(5, player.hand.size)
	}

	@Test
	def starts_with_deck_of_5_cards
	{
		Assert.assertEquals(5, player.drawPile.cards.size)
	}

	@Test
	def starts_with_empty_discard_pile
	{
		Assert.assertEquals(0, player.discard.size)
	}

	@Test
	def starts_with_0_to_2_estates
	{
		val estates = player.hand.count(_ == Estate)
		Assert.assertTrue(0 <= estates && estates <= 3)

		val coppers = player.hand.count(_ == Copper)
		Assert.assertTrue(2 <= coppers && coppers <= 5)
	}

	@Test
	def discard_draws_5_new_cards
	{
		val estatesOnFirstTurn = player.hand.count(_ == Estate)
		player.discardHand
		val estatesOnSecondTurn = player.hand.count(_ == Estate)
		Assert.assertEquals(3 - estatesOnFirstTurn, estatesOnSecondTurn)
		Assert.assertEquals(5, player.discard.size)
		Assert.assertEquals(0, player.drawPile.cards.size)
	}
}