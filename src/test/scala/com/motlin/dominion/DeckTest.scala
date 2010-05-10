package com.motlin.dominion

import card.{Copper, Estate}
import org.junit.{Assert, Test}

class DeckTest
{
	val deck = new Deck

	@Test
	def starts_with_10_cards
	{
		Assert.assertEquals(10, deck.cards.size)
	}

	@Test
	def starts_with_7_copper_and_3_estates
	{
		val estates = deck.cards.count(_ == Estate)
		Assert.assertEquals(3, estates)

		val coppers = deck.cards.count(_ == Copper)
		Assert.assertEquals(7, coppers)
	}

	@Test
	def cannot_draw_when_no_cards
	{
		for (i <- 1 to 10)
		{
			val card = deck.draw(1).first
			Assert.assertTrue(card == Estate || card == Copper)
		}

		Assert.assertEquals(List(), deck.draw(1))
	}
}