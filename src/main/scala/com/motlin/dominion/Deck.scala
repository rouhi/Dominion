package com.motlin.dominion

import card.treasure.Copper
import card.vp.Estate
import card.Card
import util.Random
import scala.collection.mutable.ArrayBuffer

object Deck
{
	val INITIAL_DECK: List[Card] = List.fill(3)(Estate) ++ List.fill(7)(Copper)

	def apply() =
	{
		val (handList, drawPileList) = Random.shuffle(Deck.INITIAL_DECK).splitAt(5)
		new Deck(ArrayBuffer(handList: _*), drawPileList, Nil)		
	}
}

case class Deck(val hand: ArrayBuffer[Card], var drawPile: List[Card], var discard: List[Card])
{
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