package com.motlin.dominion

import card.{Estate, Copper, Card}
import util.Random
import scala.collection.mutable.ArrayBuffer

object Deck
{
	val INITIAL_DECK: List[Card] = List.fill(3)(Estate) ++ List.fill(7)(Copper)
}

class Deck
{
	private val (handList, drawPileList) = Random.shuffle(Deck.INITIAL_DECK).splitAt(5)
	val hand = ArrayBuffer(handList: _*)
	var drawPile = drawPileList
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
		this.drawFiveCards()
	}
}