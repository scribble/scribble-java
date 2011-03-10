package org.scribble.protocol.parser.osgi;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.scribble.protocol.parser.ProtocolParser;
import org.scribble.protocol.parser.antlr.ANTLRProtocolParser;

public class Activator implements BundleActivator {

	private static final Logger logger=Logger.getLogger(Activator.class.getName());
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
        Properties props = new Properties();
        
        ANTLRProtocolParser pp=new ANTLRProtocolParser();
        
        context.registerService(ProtocolParser.class.getName(), 
				pp, props);

        if (logger.isLoggable(Level.FINE)) {
			logger.fine("Protocol parser registered");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
