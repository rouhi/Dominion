package com.motlin.dominion

import card.Card
import util.Random
import collection.mutable.ArrayBuffer

trait DeckTrait
{
	val hand: ArrayBuffer[Card]
	var drawPile: List[Card]
	var discard = List[Card]()

	def drawOneCard()
	{
		if (drawPile.isEmpty)
		{
			drawPile = Random.shuffle(discard)
			discard = List()
		}

		if (drawPile.nonEmpty)
		{
			hand += drawPile.head
			drawPile = drawPile.tail
		}
	}

	def drawFiveCards()
	{
		for (i <- 1 to 5)
			this.drawOneCard()
	}

	def discardHand()
	{
		discard ++= hand
		hand.clear()
	}
}