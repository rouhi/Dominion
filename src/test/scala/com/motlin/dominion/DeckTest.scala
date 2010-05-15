package com.motlin.dominion

import card.Copper
import card.vp.Estate
import org.junit.{Assert, Test}

class DeckTest
{
	val deck = new Deck
	val initialCards = (deck.drawPile ++ deck.hand)

	@Test
	def starts_with_hand_of_5_cards
	{
		Assert.assertEquals(5, deck.hand.size)
	}

	@Test
	def starts_with_deck_of_5_cards
	{
		Assert.assertEquals(5, deck.drawPile.size)
	}

	@Test
	def starts_with_empty_discard_pile
	{
		Assert.assertEquals(0, deck.discard.size)
	}

	@Test
	def starts_with_7_copper_and_3_estates
	{

		Assert.assertEquals(3, initialCards.count(_ == Estate))
		Assert.assertEquals(7, initialCards.count(_ == Copper))
	}

	@Test
	def starts_with_0_to_2_estates
	{
		val estates = deck.hand.count(_ == Estate)
		Assert.assertTrue(0 <= estates && estates <= 3)

		val coppers = deck.hand.count(_ == Copper)
		Assert.assertTrue(2 <= coppers && coppers <= 5)
	}

	@Test
	def discard_draws_5_new_cards
	{
		val estatesOnFirstTurn = deck.hand.count(_ == Estate)
		deck.discardHand
		val estatesOnSecondTurn = deck.hand.count(_ == Estate)
		Assert.assertEquals(3 - estatesOnFirstTurn, estatesOnSecondTurn)
		Assert.assertEquals(5, deck.discard.size)
		Assert.assertEquals(0, deck.drawPile.size)
	}

	@Test
	def draw_from_empty_deck_shuffles_back_in_discard_pile
	{
		deck.discardHand
		deck.discardHand
		Assert.assertEquals(5, deck.hand.size)
	}
}