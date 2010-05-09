package com.motlin.dominion

import card.{Copper, Estate}
import org.junit.{Assert, Test}

class DeckTest
{
	val deck = new Deck

	@Test
	def deck_starts_with_10_cards
	{
		Assert.assertEquals(10, deck.cards.size)
	}

	@Test
	def deck_starts_with_7_copper_and_3_estates
	{
		val multimap = deck.cards.groupBy(_.getClass)
		Assert.assertEquals(7, multimap(classOf[Copper]).size)
		Assert.assertEquals(3, multimap(classOf[Estate]).size)
	}
}