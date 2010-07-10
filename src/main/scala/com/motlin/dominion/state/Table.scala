package com.motlin.dominion.state

import com.motlin.dominion.{Player, Game}
import com.motlin.dominion.card.{Action, Card}

object Table
{
	def host(name: String, host: ServerState#User, actions: List[Action]) =
	{
		val table = new Table(name, host, actions)
		val player = table.sit(host)
		(table, player)
	}
}

case class Table(val name: String, val host: ServerState#User, actions: List[Action])
{
	var players = Set[Player]()
	var game: Option[Game] = None

	def sit(user: ServerState#User) =
	{
		val player = new Player(user, this)
		require(!players.contains(player))
		players += player
		player
	}

	def gameInProgress = game.isDefined

	def startGame(cards: List[Card])
	{
		game = Some(new Game(players, actions))
	}
}
