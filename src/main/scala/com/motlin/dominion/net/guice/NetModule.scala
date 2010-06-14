package com.motlin.dominion.net.guice


import java.util.Properties
import com.google.inject.{Provides, AbstractModule, Singleton}
import com.google.inject.name.{Named, Names}
import java.net.{Socket, ServerSocket}

class NetModule extends AbstractModule
{
	def configure
	{
		val properties = new Properties()
		properties.load(this.getClass.getClassLoader.getResourceAsStream("server.properties"))
		Names.bindProperties(this.binder, properties)
	}

	@Provides @Singleton def serverSocket(@Named("port") port: Int) = new ServerSocket(port)

	@Provides @Singleton def clientSocket(@Named("host") host: String, @Named("port") port: Int) = new Socket(host, port)
}