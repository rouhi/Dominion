package com.motlin.dominion.server

import com.google.inject.Guice
import guice.ServerModule
import java.net.ServerSocket

object Server
{
	def apply() =
	{
		val injector = Guice.createInjector(new ServerModule())
		injector.getInstance(classOf[ClientConnectionAcceptor])
	}
}