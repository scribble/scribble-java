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
package org.scribble.protocol.parser.osgi;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.scribble.protocol.parser.ProtocolParser;
import org.scribble.protocol.parser.antlr.ANTLRProtocolParser;

/**
 * Activator.
 *
 */
public class Activator implements BundleActivator {

    private static final Logger LOG=Logger.getLogger(Activator.class.getName());
    
    /**
     * {@inheritDoc}
     */
    public void start(BundleContext context) throws Exception {
        Properties props = new Properties();
        
        ANTLRProtocolParser pp=new ANTLRProtocolParser();
        
        context.registerService(ProtocolParser.class.getName(), 
                pp, props);

        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Protocol parser registered");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void stop(BundleContext context) throws Exception {
    }

}
