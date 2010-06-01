package com.motlin.dominion

import java.util.NoSuchElementException

import card.action.Woodcutter
import card.vp._

import org.scalatest.junit.AssertionsForJUnit._
import org.junit.{Ignore, Test}

class SupplyTest
{
	@Test
	def starts_with_8_vp_cards_for_two_players
	{
		assertVictoryCount(new Supply(2, Nil), 8)
	}

	@Test
	def starts_with_12_vp_cards_for_three_players
	{
		assertVictoryCount(new Supply(3, Nil), 12)
	}

	@Test
	def starts_with_8_vp_cards_for_four_players
	{
		assertVictoryCount(new Supply(4, Nil), 12)
	}

	private def assertVictoryCount(supply: Supply, count: Int)
	{
		assert(supply.count(Duchy) === count)
		assert(supply.count(Estate) === count)
		assert(supply.count(Province) === count)
	}

	@Test
	def take_decrements_count
	{
		val supply = new Supply(2, Nil)
		val initialCount = supply.count(Estate)
		val card = supply.take(Estate)
		assert(supply.count(Estate) === initialCount - 1)
		assert(card === Estate)
	}

	@Test
	def cannot_take_from_empty_pile
	{
		val supply = new Supply(2, Nil)
		for (i <- 1 to 8) supply.take(Estate)
		intercept[IllegalStateException]
		{
			supply.take(Estate)
		}
	}

	@Test
	def action_card_present
	{
		val supply = new Supply(2, List(Woodcutter))
		assert(supply.count(Woodcutter) === 10)
	}

	@Test
	def action_card_missing
	{
		val supply = new Supply(2, Nil)
		intercept[NoSuchElementException]
		{
			supply.count(Woodcutter)
		}
	}

	@Ignore
	@Test
	def ten_action_cards
	{
		intercept[IllegalArgumentException]
		{
			new Supply(2, List(Woodcutter))
		}
	}
}