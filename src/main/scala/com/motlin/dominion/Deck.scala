package com.motlin.dominion

import card.{Copper, Estate}
import scala.util.Random

class Deck
{
	private[dominion] val estates = List.fill(3)(Estate)
	private[dominion] val coppers = List.fill(7)(Copper)
	private[dominion] var cards = estates ++ coppers
	Random.shuffle(cards)

	def draw(num: Int) =
	{
		val (drawn, left) = cards.splitAt(num)
		cards = left
		drawn
	}
}