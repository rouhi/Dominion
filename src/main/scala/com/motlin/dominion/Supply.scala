package com.motlin.dominion

import card.Card
import card.vp.{Province, Duchy, Estate}
import collection.mutable.ListBuffer
import collection.mutable.Map

class Supply(numPlayers: Int)
{
	private val map: Map[Card, List[Card]] = Map()
	map += (Estate -> cardList(vpCount, Estate))
	map += (Duchy -> cardList(vpCount, Duchy))
	map += (Province -> cardList(vpCount, Province))


	private def cardList(count: Int, card: Card): List[Card] =
	{
		val buffer = new ListBuffer[Card]
	 	for(i <- 1 to count) buffer += card
		buffer.toList
	}

	private def vpCount: Int = { if (numPlayers > 2) 12 else 8 }

	def count(card: Card): Int = { map(card).length }

}