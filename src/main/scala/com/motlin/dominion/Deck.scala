package com.motlin.dominion

import card.Card

class Deck
{
	private[dominion] val drawPile = new DrawPile
	private[dominion] var hand: List[Card] = drawPile.draw(5)
	private[dominion] var discard: List[Card] = List()

	def discardHand
	{
		discard ++= hand
		hand = drawPile.draw(5)
	}
}