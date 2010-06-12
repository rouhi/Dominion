package com.motlin.dominion.net.server

import com.google.inject.Guice
import com.motlin.dominion.net.guice.NetModule

object Server
{
	def main(args: String*)
	{
		val injector = Guice.createInjector(new NetModule())
		val clientConnectionAcceptor = injector.getInstance(classOf[ClientConnectionAcceptor])
		new Thread(clientConnectionAcceptor).start()
	}
}