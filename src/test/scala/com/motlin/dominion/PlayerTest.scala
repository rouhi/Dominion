package com.motlin.dominion

import collection.mutable.ListBuffer

import card.treasure.Copper
import card.vp.Estate
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._

class PlayerTest
{
	val player = new Player(new Supply(1))
	player.deck.hand.clear()
	player.startTurn()

	@Test
	def play_copper_for_one_coin
	{
		player.deck.hand ++= List(Estate, Copper, Estate, Copper)
		player.play(Copper)
		assert(player.turn.get.coins === 1)
		assert(player.deck.hand === ListBuffer(Estate, Estate, Copper))
	}

	@Test
	def can_not_play_card_not_held
	{
		player.deck.hand ++= List(Estate, Estate, Estate)
		player.deck.drawPile = List(Copper, Estate)

		intercept[IllegalArgumentException]
		{
			player.play(Copper)
		}
	}

	@Test
	def can_not_play_victory_point_cards
	{
		player.deck.hand ++= List(Estate, Copper, Estate)
		intercept[IllegalArgumentException]
		{
			player.play(Estate)
		}
	}
}