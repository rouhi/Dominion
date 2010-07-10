package com.motlin.dominion

import card.Action

class Game(players: Set[Player], actions: List[Action])
{
	val supply = new Supply(players.size, actions)
	var currentTurn: Option[Turn] = None
}
