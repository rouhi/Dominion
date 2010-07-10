package com.motlin.dominion

import card.{Action, Treasure, Card}
import collection.mutable.Undoable
import collection.mutable.Stack

class Turn(val player: Player)
{
	var actions = 1
	var buys = 1
	var coins = 0
	var table = List[Card]()
	var undoStack = Stack[AnyRef]()

	def play(card: Card)
	{
		player.play(card)

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
				val undoable = action(this)
				undoStack.push(undoable)
			}
		}
	}

	def buy(card: Card)
	{
		require(coins >= card.cost, "Cannot afford card: " + card.toString)

		coins -= card.cost
		player.deck.discard ::= player.table.game.get.supply.take(card)

		buys -= 1
		if (buys == 0)
		{
			player.cleanUp()
			player.deck.discard ++= table
		}
	}
	
	def undo()
	{
		val card = undoStack.top
		require(card.isInstanceOf[Undoable], "Card must be undoable: " + card.toString)
		undoStack.pop().asInstanceOf[Undoable].undo()
	}
}
