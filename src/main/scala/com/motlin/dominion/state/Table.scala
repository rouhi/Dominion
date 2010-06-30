package com.motlin.dominion.state

import com.motlin.dominion.Game
import com.motlin.dominion.card.Card

case class Table(val name: String, val host: ServerState#User)
{
	var users: Set[ServerState#User] = Set(host)
	var game: Option[Game] = None

	def sit(user: ServerState#User)
	{
		require(!users.contains(user))
		users += user
	}

	def gameInProgress = game.isDefined

	def startGame(cards: List[Card])
	{
		game = Some(new Game(users))
	}
}
