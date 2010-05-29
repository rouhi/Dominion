package com.motlin.dominion

import card.treasure.Copper
import card.vp.Estate
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit._
import collection.mutable.{ArrayBuffer, ListBuffer}

class PlayerTest
{

	@Test
	def play_copper_for_one_coin
	{
		class FakePlayer(override val supply: Supply) extends Player(supply)
		{
			override val deck = new FakeDeck(ArrayBuffer(Estate, Copper, Estate, Copper), Nil)
			def takeTurn() =
			{
				this.play(Copper)
			}

		}
		val player = new FakePlayer(new Supply(1))
		player.startTurn()
		assert(player.turn.get.coins === 1)
		assert(player.deck.hand === ListBuffer(Estate, Estate, Copper))
	}

	@Test
	def can_not_play_card_not_held
	{
		class FakePlayer(override val supply: Supply) extends Player(supply)
		{
			override val deck = new FakeDeck(ArrayBuffer(Estate, Estate, Estate), List(Copper, Estate))
			def takeTurn() =
			{
				intercept[IllegalArgumentException]
				{
					this.play(Copper)
				}
			}

		}
		val player = new FakePlayer(new Supply(1))
		player.startTurn()
	}

	@Test
	def can_not_play_victory_point_cards
	{
		class FakePlayer(override val supply: Supply) extends Player(supply)
		{
			override val deck = new FakeDeck(ArrayBuffer(Estate, Estate, Copper), Nil)
			def takeTurn() =
			{
				intercept[IllegalArgumentException]
				{
					this.play(Estate)
				}
			}

		}
		val player = new FakePlayer(new Supply(1))
		player.startTurn()
	}
}