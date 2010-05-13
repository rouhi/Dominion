package com.motlin.dominion

import card.{Estate, Copper, Card}
import util.Random

object Deck
{
	val INITIAL_DECK = List.fill(3)(Estate) ++ List.fill(7)(Copper)
}

class Deck
{
	var (hand, drawPile) = Random.shuffle(Deck.INITIAL_DECK).splitAt(5)
	private[dominion] var discard: List[Card] = List()

	def drawOneCard()
	{
		hand = hand ++ drawPile.headOption
		drawPile = drawPile.tail
	}

	def drawFiveCards()
	{
		for (i <- 1 to 5)
			this.drawOneCard()
	}

	def discardHand()
	{
		discard ++= hand
		hand = List()
		this.drawFiveCards()
	}
}