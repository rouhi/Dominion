package com.motlin.dominion

class Player
{
	private[dominion] val deck = new Deck
	private[dominion] val hand = deck.draw(5)
	private[dominion] val discard = List()
}