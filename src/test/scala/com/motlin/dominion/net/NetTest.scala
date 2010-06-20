package com.motlin.dominion.net

import client.ServerConnection
import guice.NetModule
import com.google.inject.Guice
import org.junit.Test
import server.ClientConnectionAcceptor

class NetTest
{
	@Test
	def smoke_test
	{
		val injector = Guice.createInjector(new NetModule())

		val clientConnectionAcceptor = injector.getInstance(classOf[ClientConnectionAcceptor])
		val clientConnectionAcceptorThread = new Thread(clientConnectionAcceptor)
		clientConnectionAcceptorThread.start()

		val serverConnection = injector.getInstance(classOf[ServerConnection])
		serverConnection.start()
		serverConnection.trapExit
	}
}