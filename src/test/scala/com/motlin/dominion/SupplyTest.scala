package com.motlin.dominion

import card.vp._
import org.junit.{Test, Assert}

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
		Assert.assertEquals(count, supply.count(Duchy))
		Assert.assertEquals(count, supply.count(Estate))
		Assert.assertEquals(count, supply.count(Province))
	}

	@Test
	def take_decrements_count
	{
		val supply = new Supply(2)
		val initialCount = supply.count(Estate)
		val card = supply.take(Estate)
		Assert.assertEquals(initialCount - 1, supply.count(Estate))
		Assert.assertEquals(Estate, card)
	}

	// TODO expect specific exception
	@Test(expected = classOf[Exception])
	def cannot_take_from_empty_pile
	{
		val supply = new Supply(2)
		for (i <- 1 to 8) supply.take(Estate)
		supply.take(Estate)
	}
}