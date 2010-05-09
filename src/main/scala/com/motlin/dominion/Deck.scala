package com.motlin.dominion

import card.{Copper, Estate}

class Deck
{
	val estates = List.fill(3)(new Estate)
	val coppers = List.fill(7)(new Copper)
	val cards = estates ++ coppers
}