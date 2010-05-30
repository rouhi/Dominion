package com.motlin.dominion

import card.treasure.Copper
import card.vp.Estate
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._

class DeckTest
{
	val deck = Deck()
	val initialCards = (deck.drawPile ++ deck.hand)

	@Test
	def starts_with_hand_of_5_cards
	{
		assert(deck.hand.size === 5)
	}

	@Test
	def starts_with_deck_of_5_cards
	{
		assert(deck.drawPile.size === 5)
	}

	@Test
	def starts_with_empty_discard_pile
	{
		assert(deck.discard.size === 0)
	}

	@Test
	def starts_with_7_copper_and_3_estates
	{
		assert(initialCards.count(_ == Estate) === 3)
		assert(initialCards.count(_ == Copper) === 7)
	}

	@Test
	def starts_with_0_to_2_estates
	{
		val estates = deck.hand.count(_ == Estate)
		assert(0 <= estates && estates <= 3)

		val coppers = deck.hand.count(_ == Copper)
		assert(2 <= coppers && coppers <= 5)
	}

	@Test
	def discard_draws_5_new_cards
	{
		val estatesOnFirstTurn = deck.hand.count(_ == Estate)
		deck.discardHand()
		deck.drawFiveCards()
		val estatesOnSecondTurn = deck.hand.count(_ == Estate)
		assert(estatesOnFirstTurn + estatesOnSecondTurn === 3)
		assert(deck.discard.size === 5)
		assert(deck.drawPile.size === 0)
	}

	@Test
	def draw_from_empty_deck_shuffles_back_in_discard_pile
	{
		deck.discardHand()
		deck.drawFiveCards()
		deck.discardHand()
		deck.drawFiveCards()
		assert(deck.hand.size === 5)
	}
}