package org.scribble.net.session;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.Set;

import org.scribble.sesstype.name.Role;

public class ScribInputSelector extends Thread
{
	private SessionEndpoint se;
	private final Selector sel;

	private volatile boolean paused = false;
	private volatile boolean closed = false;

	public ScribInputSelector(SessionEndpoint se) throws IOException
	{
		this.se = se;
		this.sel = Selector.open();
	}

	@Override
	public void run()
	{
		try
		{
			//waitForInit();  
					//..FIXME: reg serversocketchannel with sel -- midsession reg needs wakeup and re-select?
					//..here: do java smtp version using nio

			while (!this.closed)
			{
				this.sel.select();
				if (this.closed)
				{
					return;
				}
				synchronized (this)
				{
					while (paused)
					{
						wait();
					}
				
				Set<SelectionKey> keys = this.sel.selectedKeys();
				for (SelectionKey key : keys)
				{
					//SocketChannel s = (SocketChannel) key.channel();
					if (key.isReadable())
					{
						Role peer = (Role) key.attachment();
						BinaryChannelEndpoint c = this.se.chans.get(peer);
						c.readBytes();
					}
					else
					{
						throw new RuntimeException("TODO: " + key);
					}
				}
			}
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				this.sel.close();
			}
			catch (IOException e)
			{
				// FIXME
				e.printStackTrace();
			}
		}
	}
	
	// synchronize?
	protected SelectionKey register(AbstractSelectableChannel c) throws ClosedChannelException
	{
		return c.register(this.sel, SelectionKey.OP_READ);
	}
	
	protected synchronized void pause()	
	{
		this.paused = true;
		this.sel.wakeup();
	}
	
	protected synchronized void unpause()	
	{
		this.paused = false;
		notify();
	}
	
	protected synchronized void close()
	{
		this.closed = true;
		//for (BinaryChannelEndpoint c : this.se.chans.values())
		for (Role peer : this.se.getPeers())
		{
			try
			{
				BinaryChannelEndpoint c = this.se.getChannelEndpoint(peer);
				c.close();  // dereg from sel?
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}		
		this.sel.wakeup();
	}
}
