package com.motlin.dominion

import card.treasure.Copper
import card.vp.Estate
import card.Card
import util.Random
import scala.collection.mutable.ArrayBuffer

object Deck
{
	val INITIAL_DECK: List[Card] = List.fill(3)(Estate) ++ List.fill(7)(Copper)
}

class Deck extends DeckTrait
{
	private val (handList, drawPileList) = Random.shuffle(Deck.INITIAL_DECK).splitAt(5)
	val hand = ArrayBuffer(handList: _*)
	var drawPile = drawPileList

}