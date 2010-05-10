package com.motlin.dominion

import card.Card

class Player
{
	private[dominion] val deck = new Deck
	private[dominion] var hand: List[Card] = deck.draw(5)
	private[dominion] var discard: List[Card] = List()

	def cleanUp
	{
		discard ++= hand
		hand = deck.draw(5)
	}
}