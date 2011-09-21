/*
 * Copyright 2009-11 www.scribble.org
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.protocol.projection.osgi;

import java.util.Properties;
import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.scribble.protocol.projection.ProtocolProjector;
import org.scribble.protocol.projection.impl.ProtocolProjectorImpl;

/**
 * Activator.
 *
 */
public class Activator implements BundleActivator {

    private static final Logger LOG=Logger.getLogger(Activator.class.getName());

    private org.osgi.util.tracker.ServiceTracker _protocolValidationManagerTracker=null;

    /**
     * {@inheritDoc}
     */
    public void start(BundleContext context) throws Exception {
        Properties props = new Properties();
        
        final ProtocolProjector pp=new ProtocolProjectorImpl();
        
        context.registerService(ProtocolProjector.class.getName(), 
                            pp, props);
        
        // Detect protocol validation manager
        _protocolValidationManagerTracker = new ServiceTracker(context,
                org.scribble.protocol.validation.ProtocolValidationManager.class.getName(),
                        null) {
            
            public Object addingService(ServiceReference ref) {
                Object ret=super.addingService(ref);
                
                LOG.fine("Validation manager has been added to projector: "+ret);
                
                pp.setProtocolValidationManager((org.scribble.protocol.validation.ProtocolValidationManager)ret);
                
                return (ret);
            }
        };
        
        _protocolValidationManagerTracker.open();

    }

    /**
     * {@inheritDoc}
     */
    public void stop(BundleContext context) throws Exception {
    }

}
