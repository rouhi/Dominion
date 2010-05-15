package com.motlin.dominion

import card.Card
import card.vp._
import collection.mutable.Map

class Supply(numPlayers: Int)
{
	private val vpCount = if (numPlayers > 2) 12 else 8

	private val map = Map[Card, Int](
		Estate -> vpCount,
		Duchy -> vpCount,
		Province -> vpCount)

	def count(card: Card): Int = map(card)
}