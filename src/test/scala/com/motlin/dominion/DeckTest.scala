package com.motlin.dominion

import org.junit.{Assert, Test}

class DeckTest
{
	val deck = new Deck

	@Test
	def deck_starts_with_10_cards
	{
		Assert.assertEquals(10, deck.cards.size)
	}
}