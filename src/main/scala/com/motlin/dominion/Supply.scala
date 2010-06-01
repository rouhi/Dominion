package com.motlin.dominion

import card.treasure._
import card.vp._
import card.{Action, Card}
import collection.mutable.Map

class Supply(numPlayers: Int, actions: List[Action])
{
	private val vpCount = if (numPlayers > 2) 12 else 8

	private val map = Map[Card, Int](
		Estate -> vpCount,
		Duchy -> vpCount,
		Province -> vpCount,
		Copper -> (60 - (numPlayers * 7)),
		Silver -> 40,
		Gold -> 30) ++
		actions.map(_ -> 10)

	def count(card: Card): Int = map(card)

	def take(card: Card): Card =
	{
		if (map(card) == 0)
			throw new IllegalStateException("No " + card.toString + " cards remaining")

		map(card) -= 1
		card
	}
}