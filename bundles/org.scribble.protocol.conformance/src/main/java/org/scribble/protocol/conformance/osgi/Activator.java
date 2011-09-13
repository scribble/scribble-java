package org.scribble.protocol.conformance.osgi;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.scribble.protocol.conformance.ProtocolConformer;
import org.scribble.protocol.conformance.impl.ProtocolConformerImpl;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		Properties props = new Properties();
        
		ProtocolConformer pp=new ProtocolConformerImpl();
        
		context.registerService(ProtocolConformer.class.getName(), 
							pp, props);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
