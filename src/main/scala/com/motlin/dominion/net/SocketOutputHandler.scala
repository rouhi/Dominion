package com.motlin.dominion.net

import comm.Close
import java.net.{Socket, SocketException}
import java.io.{ObjectOutputStream, BufferedOutputStream}
import com.google.inject.Inject
import org.slf4j.LoggerFactory

object SocketOutputHandler
{
	val LOGGER = LoggerFactory.getLogger(classOf[SocketOutputHandler])
}

// TODO: consider making this an Actor again
case class SocketOutputHandler @Inject() (socket: Socket)
{
	val outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream))
    var closed = false

	def write(writeObject: Any)
	{
	    if (!this.closed)
        {
        	SocketOutputHandler.LOGGER.info("Sending {}.", writeObject)
		    try
		    {
		        outputStream.writeObject(writeObject)
		        outputStream.flush()
		    }
		    catch
		    {
		        case _: SocketException => socket.close()
		    }
		}
	}

	def close()
	{
	    if (!this.closed)
	    {
    	    write(Close)
	        this.closed = true
			try
			{
				outputStream.close()
			}
			catch
			{
				case e: SocketException => // Deliberately ignored
			}
			socket.close()
		}

		SocketOutputHandler.LOGGER.info("Done closing.")
	}

	// TODO: consider creating a finalizer to call close()
}
