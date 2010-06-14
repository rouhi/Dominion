package com.motlin.dominion.net

import actors.threadpool.{Executors, ThreadFactory}

class NamedThreadFactory(name: String) extends ThreadFactory
{
	val threadFactory = Executors.defaultThreadFactory()

	def newThread(runnable: Runnable) =
	{
		val thread = threadFactory.newThread(runnable)
		thread.setName(name)
		thread.setDaemon(true)
		thread
	}
}