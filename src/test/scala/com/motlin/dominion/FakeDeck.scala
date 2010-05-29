package com.motlin.dominion

import card.Card
import collection.mutable.ArrayBuffer

class FakeDeck(val hand: ArrayBuffer[Card], var drawPile: List[Card]) extends DeckTrait