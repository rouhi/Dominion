package com.motlin.dominion

import card.{Card, Treasure, Action}
import state.{Table, ServerState}

case class Player(user: ServerState#User, table: Table)
{
	val deck: Deck = Deck()

	def play(card: Card)
	{
		require(deck.hand.contains(card), "Hand must contain card: " + card.toString)
		require(card.isInstanceOf[Treasure] || card.isInstanceOf[Action], "May only play treasures and actions: " + card.toString)

		deck.hand -= card
	}

	def cleanUp()
	{
		deck.discardHand()
		deck.drawFiveCards()
	}
}
