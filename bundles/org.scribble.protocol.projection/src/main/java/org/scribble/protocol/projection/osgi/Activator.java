package org.scribble.protocol.projection.osgi;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.scribble.protocol.projection.ProtocolProjector;
import org.scribble.protocol.projection.impl.ProtocolProjectorImpl;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
        Properties props = new Properties();
        
        ProtocolProjector pp=new ProtocolProjectorImpl();
        
        context.registerService(ProtocolProjector.class.getName(), 
							pp, props);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
