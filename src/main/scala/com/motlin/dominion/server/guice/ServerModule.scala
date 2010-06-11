package com.motlin.dominion.server.guice


import java.util.Properties
import com.google.inject.{Provides, AbstractModule, Singleton}
import com.google.inject.name.{Named, Names}
import java.net.ServerSocket
import java.io.{InputStream, FileReader}

class ServerModule extends AbstractModule
{
	def configure
	{
		val properties = new Properties()
		properties.load(this.getClass.getClassLoader.getResourceAsStream("server.properties"))
		Names.bindProperties(this.binder, properties)
	}

	@Provides @Singleton def serverSocket(@Named("port") port: Int) = new ServerSocket(port)
}