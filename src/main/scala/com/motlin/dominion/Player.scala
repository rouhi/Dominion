package com.motlin.dominion

import card._

class Player
{
	val deck = new Deck
	var coins = 0

	def play(card: Card)
	{
		require(deck.hand.contains(card))
		deck.hand -= card
		
		card match {
			case treasure: Treasure => coins += treasure.value
			case vp: VictoryPoints => throw new IllegalArgumentException("May not play a victory point card: " + card.toString)
			case _ => throw new IllegalArgumentException("Unknown card: " + card.toString)
		}
	}

	def buy(card: Card)
	{
		if (card.cost > coins)
			throw new IllegalStateException("Cannot afford card: " + card.toString)

		coins -= card.cost
		deck.discard ::= card 
	}
}