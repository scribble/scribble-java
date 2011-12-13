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
import org.scribble.protocol.export.text.TextProtocolExporterRule;
import org.scribble.protocol.parser.DefaultProtocolParserManager;
import org.scribble.protocol.parser.ProtocolParser;
import org.scribble.protocol.parser.ProtocolParserManager;
import org.scribble.protocol.projection.ProtocolProjector;
import org.scribble.protocol.validation.DefaultProtocolValidationManager;
import org.scribble.protocol.validation.ProtocolValidationManager;
import org.scribble.protocol.validation.ProtocolValidator;
import org.scribble.protocol.validation.rules.DefaultProtocolComponentValidator;

/**
 * Activator.
 *
 */
public class Activator implements BundleActivator {

    private static final Logger LOG=Logger.getLogger(Activator.class.getName());

    private org.osgi.util.tracker.ServiceTracker _protocolParserTracker=null;
    private org.osgi.util.tracker.ServiceTracker _protocolValidatorTracker=null;
    private org.osgi.util.tracker.ServiceTracker _protocolProjectorTracker=null;
    private org.osgi.util.tracker.ServiceTracker _protocolExporterTracker=null;
    private org.osgi.util.tracker.ServiceTracker _protocolTextExporterRuleTracker=null;

    /**
     * Start the bundle.
     * 
     * @param context The context
     * @throws Exception Failed to start
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {

        Properties props = new Properties();

        // Register parser manager
        final ProtocolParserManager pm=new DefaultProtocolParserManager();
        
        context.registerService(ProtocolParserManager.class.getName(), 
                            pm, props);
        
        LOG.fine("Registered Parser Manager");
        
        _protocolParserTracker = new ServiceTracker(context,
                org.scribble.protocol.parser.ProtocolParser.class.getName(),
                        null) {
            
            public Object addingService(ServiceReference ref) {
                Object ret=super.addingService(ref);
                
                LOG.fine("Parser has been added: "+ret);
                
                pm.getParsers().add((ProtocolParser)ret);
                
                return (ret);
            }
        };
        
        _protocolParserTracker.open();

        // Register validation manager
        final ProtocolValidationManager vm=new DefaultProtocolValidationManager();
        
        context.registerService(ProtocolValidationManager.class.getName(), 
                            vm, props);
        
        LOG.fine("Registered Validation Manager");
        
        _protocolValidatorTracker = new ServiceTracker(context,
                org.scribble.protocol.validation.ProtocolValidator.class.getName(),
                        null) {
            
            public Object addingService(ServiceReference ref) {
                Object ret=super.addingService(ref);
                
                LOG.fine("Validator has been added: "+ret);
                
                vm.getValidators().add((ProtocolValidator)ret);
                
                return (ret);
            }
        };
        
        _protocolValidatorTracker.open();

        _protocolProjectorTracker = new ServiceTracker(context,
                org.scribble.protocol.projection.ProtocolProjector.class.getName(),
                        null) {
            
            public Object addingService(ServiceReference ref) {
                Object ret=super.addingService(ref);
                
                LOG.fine("Projector has been added to validator manager: "+ret);
                
                vm.setProtocolProjector((ProtocolProjector)ret);
                
                return (ret);
            }
        };
        
        _protocolProjectorTracker.open();

        // Register export manager
        final ProtocolExportManager em=new DefaultProtocolExportManager();
        
        context.registerService(ProtocolExportManager.class.getName(), 
                            em, props);
        
        LOG.fine("Registered Export Manager");
        
        _protocolExporterTracker = new ServiceTracker(context,
                org.scribble.protocol.export.ProtocolExporter.class.getName(),
                        null) {
            
            public Object addingService(ServiceReference ref) {
                Object ret=super.addingService(ref);
                
                LOG.fine("Exporter has been added: "+ret);
                
                em.getExporters().add((ProtocolExporter)ret);
                
                return (ret);
            }
        };
        
        _protocolExporterTracker.open();

        // Register console journal
        context.registerService(Journal.class.getName(), 
                new ConsoleJournal(), props);

        // Register protocol validator
        props = new Properties();

        context.registerService(ProtocolValidator.class.getName(), 
                new DefaultProtocolComponentValidator(), props);        

        // Register text based exporter
        props = new Properties();

        final TextProtocolExporter tpe=new TextProtocolExporter();
        
        context.registerService(ProtocolExporter.class.getName(), 
                new TextProtocolExporter(), props);        

        LOG.fine("Registered Text Protocol Exporter");
        
        _protocolTextExporterRuleTracker = new ServiceTracker(context,
                org.scribble.protocol.export.text.TextProtocolExporterRule.class.getName(),
                        null) {
            
            public Object addingService(ServiceReference ref) {
                Object ret=super.addingService(ref);
                
                LOG.fine("Text Exporter Rule has been added: "+ret);
                
                tpe.register((TextProtocolExporterRule)ret);
                
                return (ret);
            }
        };
        
        _protocolTextExporterRuleTracker.open();

    }

    /**
     * Stop the bundle.
     * 
     * @param context The context
     * @throws Exception Failed to stop
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        
        //context.ungetService(arg0);
    }
}
