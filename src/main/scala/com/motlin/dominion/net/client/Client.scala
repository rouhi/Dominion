package com.motlin.dominion.net.client

import com.google.inject.Guice
import com.motlin.dominion.net.guice.NetModule

object Client
{
	def main(args: Array[String])
	{
		val injector = Guice.createInjector(new NetModule())
		val serverConnection = injector.getInstance(classOf[ServerConnection])
		serverConnection.start()
	}
}