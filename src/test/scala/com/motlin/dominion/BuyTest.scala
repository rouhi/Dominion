package com.motlin.dominion

import card.vp.Estate
import card.treasure.Copper
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._
import java.lang.IllegalArgumentException

class BuyTest
{
	val player = new Player(new Supply(1))
	player.deck.hand.clear()
	player.deck.drawPile = List()
	player.startTurn()

	@Test
	def player_with_0_can_afford_copper
	{
		player.buy(Copper)

		assert(player.deck.drawPile === List())
		assert(player.deck.discard === List())
		assert(player.deck.hand === List(Copper))
		assert(player.turn === None)
	}

	@Test
	def player_with_0_can_not_afford_estate
	{
		val exception = intercept[IllegalArgumentException]
		{
			player.buy(Estate)
		}
		assert(exception.getMessage.contains("Cannot afford card: Estate"))
	}

	@Test
	def player_with_2_can_afford_estate
	{
		buyEstate()
		assert(player.deck.drawPile === List())
		assert(player.deck.discard === List())
		assert(player.deck.hand.sortBy(_.toString) === List(Copper, Copper, Estate, Estate))
	}

	@Test
	def buy_takes_card_from_supply
	{
		val initialCount = player.supply.count(Estate)
		buyEstate()
		assert(player.supply.count(Estate) === initialCount - 1)
	}

	private def buyEstate()
	{
		player.deck.hand ++= List(Copper, Estate, Copper)
		player.play(Copper)
		player.play(Copper)
		player.buy(Estate)
	}
}