/*
 * Copyright 2009 www.scribble.org
 * 
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
package org.scribble.protocol.osgi;

import java.util.Properties;
import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.scribble.common.logging.ConsoleJournal;
import org.scribble.common.logging.Journal;
import org.scribble.protocol.export.DefaultProtocolExportManager;
import org.scribble.protocol.export.ProtocolExportManager;
import org.scribble.protocol.export.ProtocolExporter;
import org.scribble.protocol.export.text.TextProtocolExporter;
import org.scribble.protocol.parser.DefaultProtocolParserManager;
import org.scribble.protocol.parser.ProtocolParser;
import org.scribble.protocol.parser.ProtocolParserManager;
import org.scribble.protocol.validation.*;
import org.scribble.protocol.validation.rules.DefaultProtocolComponentValidator;

public class Activator implements BundleActivator {

	private static final Logger _log=Logger.getLogger(Activator.class.getName());

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {

        Properties props = new Properties();

        // Register parser manager
        final ProtocolParserManager pm=new DefaultProtocolParserManager();
        
        context.registerService(ProtocolParserManager.class.getName(), 
        					pm, props);
        
        _log.fine("Registered Parser Manager");
        
        m_protocolParserTracker = new ServiceTracker(context,
        		org.scribble.protocol.parser.ProtocolParser.class.getName(),
        				null) {
        	
			public Object addingService(ServiceReference ref) {
				Object ret=super.addingService(ref);
				
				_log.fine("Parser has been added: "+ret);
				
				pm.getParsers().add((ProtocolParser)ret);
				
				return(ret);
			}
        };
        
        m_protocolParserTracker.open();

        // Register validation manager
        final ProtocolValidationManager vm=new DefaultProtocolValidationManager();
        
        context.registerService(ProtocolValidationManager.class.getName(), 
							vm, props);
        
        _log.fine("Registered Validation Manager");
        
        m_protocolValidatorTracker = new ServiceTracker(context,
        		org.scribble.protocol.validation.ProtocolValidator.class.getName(),
        				null) {
        	
			public Object addingService(ServiceReference ref) {
				Object ret=super.addingService(ref);
				
				_log.fine("Validator has been added: "+ret);
				
				vm.getValidators().add((ProtocolValidator)ret);
				
				return(ret);
			}
        };
        
        m_protocolValidatorTracker.open();

        // Register export manager
        final ProtocolExportManager em=new DefaultProtocolExportManager();
        
        context.registerService(ProtocolExportManager.class.getName(), 
							em, props);
        
        _log.fine("Registered Export Manager");
        
        m_protocolExporterTracker = new ServiceTracker(context,
        		org.scribble.protocol.export.ProtocolExporter.class.getName(),
        				null) {
        	
			public Object addingService(ServiceReference ref) {
				Object ret=super.addingService(ref);
				
				_log.fine("Exporter has been added: "+ret);
				
				em.getExporters().add((ProtocolExporter)ret);
				
				return(ret);
			}
        };
        
        m_protocolExporterTracker.open();

        // Register console journal
        context.registerService(Journal.class.getName(), 
				new ConsoleJournal(), props);

		// Register protocol validator
        props = new Properties();

        context.registerService(ProtocolValidator.class.getName(), 
				new DefaultProtocolComponentValidator(), props);        

		// Register text based exporter
        props = new Properties();

        context.registerService(ProtocolExporter.class.getName(), 
				new TextProtocolExporter(), props);        

	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		
		//context.ungetService(arg0);
	}

	private org.osgi.util.tracker.ServiceTracker m_protocolParserTracker=null;
	private org.osgi.util.tracker.ServiceTracker m_protocolValidatorTracker=null;
	private org.osgi.util.tracker.ServiceTracker m_protocolExporterTracker=null;
}
