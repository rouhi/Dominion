package com.motlin.dominion

import card._
import collection.mutable.{Undoable, Stack}

abstract class Player(val supply: Supply)
{
	class Turn
	{
		var actions = 1
		var buys = 1
		var coins = 0
		var table = List[Card]()
		var undoStack = Stack[AnyRef]()

		def play(card: Card)
		{
			card match
			{
				case treasure: Treasure =>
				{
					table ::= treasure
					coins += treasure.value
					undoStack.push(treasure)
				}
				case action: Action =>
				{
					val undoable = action(Player.this)
					undoStack.push(undoable)
				}
			}
		}

		def undo()
		{
			val card = undoStack.top
			require(card.isInstanceOf[Undoable], "Card must be undoable: " + card.toString)
			undoStack.pop().asInstanceOf[Undoable].undo()
		}
	}

	val deck: DeckTrait = new Deck
	var turn: Option[Turn] = None

	def startTurn()
	{
		turn = Some(new Turn)
		takeTurn()
	}

	def takeTurn() : Unit
	
	def play(card: Card)
	{
		require(deck.hand.contains(card), "Hand must contain card: " + card.toString)
		require(card.isInstanceOf[Treasure] || card.isInstanceOf[Action], "May only play treasures and actions: " + card.toString)

		deck.hand -= card
		turn.get.play(card)
	}

	def buy(card: Card)
	{
		require(turn.get.coins >= card.cost, "Cannot afford card: " + card.toString)

		turn.get.coins -= card.cost
		deck.discard ::= supply.take(card)

		turn.get.buys -= 1
		if (turn.get.buys == 0)
		{
			endTurn()
		}
	}

	def endTurn()
	{
		deck.discardHand()
		deck.discard ++= turn.get.table
		turn = None
		deck.drawFiveCards()
	}

	def undo()
	{
		this.turn.get.undo()
	}
}