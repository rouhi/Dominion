package com.motlin.dominion

import card.vp._
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._

class SupplyTest
{
	@Test
	def starts_with_8_vp_cards_for_two_players
	{
		assertVictoryCount(new Supply(2), 8)
	}

	@Test
	def starts_with_12_vp_cards_for_three_players
	{
		assertVictoryCount(new Supply(3), 12)
	}

	@Test
	def starts_with_8_vp_cards_for_four_players
	{
		assertVictoryCount(new Supply(4), 12)
	}

	private def assertVictoryCount(supply: Supply, count: Int)
	{
		assert(supply.count(Duchy) === count)
		assert(supply.count(Estate) === count)
		assert(supply.count(Province) === count)
	}
}