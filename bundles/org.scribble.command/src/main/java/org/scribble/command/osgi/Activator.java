/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.command.osgi;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.scribble.command.Command;
import org.scribble.command.conforms.ConformsCommand;
import org.scribble.command.parse.ParseCommand;
import org.scribble.command.project.ProjectCommand;
import org.scribble.command.simulate.SimulateCommand;
import org.scribble.command.validate.ValidateCommand;
import org.scribble.common.logging.Journal;
import org.scribble.protocol.conformance.ProtocolConformer;
import org.scribble.protocol.export.ProtocolExportManager;
import org.scribble.protocol.monitor.ProtocolMonitor;
import org.scribble.protocol.parser.ProtocolParserManager;
import org.scribble.protocol.projection.ProtocolProjector;
import org.scribble.protocol.validation.ProtocolValidationManager;

/**
 * Activator.
 *
 */
public class Activator implements BundleActivator {

    /**
     * Start the bundle.
     * 
     * @param context The context
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(final BundleContext context) {
        final Commands commands=new Commands();
        Properties props = new Properties();

        // Register parse command
        commands.setParseCommand(new ParseCommand());
        
        context.registerService(Command.class.getName(), 
                commands.getParseCommand(), props);
        
        // Register validate command
        commands.setValidateCommand(new ValidateCommand());
        
        context.registerService(Command.class.getName(), 
                commands.getValidateCommand(), props);

        // Register validate command
        commands.setConformsCommand(new ConformsCommand());
        
        context.registerService(Command.class.getName(), 
                commands.getConformsCommand(), props);

        // Register project command
        commands.setProjectCommand(new ProjectCommand());
        
        context.registerService(Command.class.getName(), 
                commands.getProjectCommand(), props);

        // Register simulate command
        commands.setSimulateCommand(new SimulateCommand());
        
        context.registerService(Command.class.getName(), 
                commands.getSimulateCommand(), props);

        // Register service listeners to establish dependent
        // components
        ServiceListener[] sl=new ServiceListener[7];
        
        sl[0] = new ServiceListener() {
            public void serviceChanged(ServiceEvent ev) {
                ServiceReference sr = ev.getServiceReference();
                switch(ev.getType()) {
                case ServiceEvent.REGISTERED:
                    ProtocolValidationManager vm=
                        (ProtocolValidationManager)context.getService(sr);
                    commands.setProtocolValidationManager(vm);
                    break;
                case ServiceEvent.UNREGISTERING:
                    break;
                }
            }
        };
              
        sl[1] = new ServiceListener() {
            public void serviceChanged(ServiceEvent ev) {
                ServiceReference sr = ev.getServiceReference();
                switch(ev.getType()) {
                case ServiceEvent.REGISTERED:
                    Journal journal=
                        (Journal)context.getService(sr);
                    commands.setJournal(journal);
                    break;
                case ServiceEvent.UNREGISTERING:
                    break;
                }
            }
        };
              
        sl[2] = new ServiceListener() {
            public void serviceChanged(ServiceEvent ev) {
                ServiceReference sr = ev.getServiceReference();
                switch(ev.getType()) {
                case ServiceEvent.REGISTERED:
                    ProtocolParserManager pp=
                        (ProtocolParserManager)context.getService(sr);
                    commands.setProtocolParserManager(pp);
                    break;
                case ServiceEvent.UNREGISTERING:
                    break;
                }
            }
        };
              
        sl[3] = new ServiceListener() {
            public void serviceChanged(ServiceEvent ev) {
                ServiceReference sr = ev.getServiceReference();
                switch(ev.getType()) {
                case ServiceEvent.REGISTERED:
                    ProtocolConformer conformer=
                        (ProtocolConformer)context.getService(sr);
                    commands.setProtocolConformer(conformer);
                    break;
                case ServiceEvent.UNREGISTERING:
                    break;
                }
            }
        };
              
        sl[4] = new ServiceListener() {
            public void serviceChanged(ServiceEvent ev) {
                ServiceReference sr = ev.getServiceReference();
                switch(ev.getType()) {
                case ServiceEvent.REGISTERED:
                    ProtocolProjector projector=
                        (ProtocolProjector)context.getService(sr);
                    commands.setProtocolProjector(projector);
                    break;
                case ServiceEvent.UNREGISTERING:
                    break;
                }
            }
        };
              
        sl[5] = new ServiceListener() {
            public void serviceChanged(ServiceEvent ev) {
                ServiceReference sr = ev.getServiceReference();
                switch(ev.getType()) {
                case ServiceEvent.REGISTERED:
                    ProtocolExportManager em=
                        (ProtocolExportManager)context.getService(sr);
                    commands.setProtocolExportManager(em);
                    break;
                case ServiceEvent.UNREGISTERING:
                    break;
                }
            }
        };
              
        sl[6] = new ServiceListener() {
            public void serviceChanged(ServiceEvent ev) {
                ServiceReference sr = ev.getServiceReference();
                switch(ev.getType()) {
                case ServiceEvent.REGISTERED:
                    ProtocolMonitor pm=
                        (ProtocolMonitor)context.getService(sr);
                    commands.setProtocolMonitor(pm);
                    break;
                case ServiceEvent.UNREGISTERING:
                    break;
                }
            }
        };

        initCommands(context, commands, sl);  
    }
    
    /**
     * This method initializes the commands.
     * 
     * @param context The context
     * @param commands The commands
     * @param sl The service listeners
     */
    protected void initCommands(BundleContext context, Commands commands, ServiceListener[] sl) {
        
        String filter1 = "(objectclass=" + ProtocolValidationManager.class.getName() + ")";
        String filter2 = "(objectclass=" + Journal.class.getName() + ")";
        String filter3 = "(objectclass=" + ProtocolParserManager.class.getName() + ")";
        String filter4 = "(objectclass=" + ProtocolConformer.class.getName() + ")";
        String filter5 = "(objectclass=" + ProtocolProjector.class.getName() + ")";
        String filter6 = "(objectclass=" + ProtocolExportManager.class.getName() + ")";
        String filter7 = "(objectclass=" + ProtocolMonitor.class.getName() + ")";

        
        try {
            ServiceReference sref=context.getServiceReference(ProtocolValidationManager.class.getName());
            ProtocolValidationManager pvm=null;
            
            if (sref != null) {
                pvm = (ProtocolValidationManager)context.getService(sref);
            }

            if (pvm != null) {
                commands.setProtocolValidationManager(pvm);
            } else {
                context.addServiceListener(sl[0], filter1);
            }
            
            sref=context.getServiceReference(Journal.class.getName());         
            Journal journal=null;
            
            if (sref != null) {
                journal = (Journal)context.getService(sref);
            }

            if (journal != null) {
                commands.setJournal(journal);
            } else {
                context.addServiceListener(sl[1], filter2);
            }
            
            sref=context.getServiceReference(ProtocolParserManager.class.getName());         
            ProtocolParserManager pp=null;
            
            if (sref != null) {
                pp = (ProtocolParserManager)context.getService(sref);
            }

            if (pp != null) {
                commands.setProtocolParserManager(pp);
            } else {
                context.addServiceListener(sl[2], filter3);
            }
            
            sref=context.getServiceReference(ProtocolConformer.class.getName());         
            ProtocolConformer pc=null;
            
            if (sref != null) {
                pc = (ProtocolConformer)context.getService(sref);
            }

            if (pc != null) {
                commands.setProtocolConformer(pc);
            } else {
                context.addServiceListener(sl[3], filter4);
            }
            
            sref=context.getServiceReference(ProtocolProjector.class.getName());         
            ProtocolProjector ppj=null;
            
            if (sref != null) {
                ppj = (ProtocolProjector)context.getService(sref);
            }

            if (ppj != null) {
                commands.setProtocolProjector(ppj);
            } else {
                context.addServiceListener(sl[4], filter5);
            }
            
            sref=context.getServiceReference(ProtocolExportManager.class.getName());
            ProtocolExportManager pem=null;
            
            if (sref != null) {
                pem = (ProtocolExportManager)context.getService(sref);
            }

            if (pem != null) {
                commands.setProtocolExportManager(pem);
            } else {
                context.addServiceListener(sl[5], filter6);
            }
            
            sref=context.getServiceReference(ProtocolMonitor.class.getName());         
            ProtocolMonitor pm=null;
            
            if (sref != null) {
                pm = (ProtocolMonitor)context.getService(sref);
            }

            if (pm != null) {
                commands.setProtocolMonitor(pm);
            } else {
                context.addServiceListener(sl[6], filter7);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Stop the bundle.
     * 
     * @param context The context
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) {
    }

    /**
     * Container class for the commands.
     *
     */
    protected class Commands {
        
        private ParseCommand _parseCommand=null;
        private ValidateCommand _validateCommand=null;
        private ConformsCommand _conformsCommand=null;
        private ProjectCommand _projectCommand=null;        
        private SimulateCommand _simulateCommand=null;
         
        /**
         * This method sets the validation manager.
         * 
         * @param vm The validation manager
         */
        public void setProtocolValidationManager(ProtocolValidationManager vm) {
            _validateCommand.setValidationManager(vm);
        }
        
        /**
         * This method sets the journal.
         * 
         * @param journal The journal
         */
        public void setJournal(Journal journal) {
            _parseCommand.setJournal(journal);
            _validateCommand.setJournal(journal);
            _conformsCommand.setJournal(journal);
            _projectCommand.setJournal(journal);
            _simulateCommand.setJournal(journal);            
        }
        
        /**
         * This method sets the parser manager.
         * 
         * @param pp Parser manager
         */
        public void setProtocolParserManager(ProtocolParserManager pp) {
            _parseCommand.setProtocolParserManager(pp);
            _validateCommand.setProtocolParserManager(pp);
            _conformsCommand.setProtocolParserManager(pp);
            _projectCommand.setProtocolParserManager(pp);
            _simulateCommand.setProtocolParserManager(pp);
        }
        
        /**
         * This method sets the conformer.
         * 
         * @param pc The conformer
         */
        public void setProtocolConformer(ProtocolConformer pc) {
            _conformsCommand.setConformer(pc);
        }
        
        /**
         * This method sets the projector.
         * 
         * @param pp The projector
         */
        public void setProtocolProjector(ProtocolProjector pp) {
            _projectCommand.setProtocolProjector(pp);
            _validateCommand.setProtocolProjector(pp);
        }

        /**
         * This method sets the export manager.
         * 
         * @param em The export manager
         */
        public void setProtocolExportManager(ProtocolExportManager em) {
               _projectCommand.setProtocolExportManager(em);
            _simulateCommand.setProtocolExportManager(em);
        }
        
        /**
         * This method sets the protocol monitor.
         * 
         * @param pm The protocol monitor
         */
        public void setProtocolMonitor(ProtocolMonitor pm) {
            _simulateCommand.setProtocolMonitor(pm);
        }
        
        /**
         * This method sets the parser command.
         * 
         * @param command The parser command
         */
        public void setParseCommand(ParseCommand command) {
            _parseCommand = command;
        }
        
        /**
         * This method gets the parser command.
         * 
         * @return The parser command
         */
        public ParseCommand getParseCommand() {
            return (_parseCommand);
        }

        /**
         * This method sets the validation command.
         * 
         * @param command The validation command
         */
        public void setValidateCommand(ValidateCommand command) {
            _validateCommand = command;
        }
        
        /**
         * This method gets the validation command.
         * 
         * @return The validation command
         */
        public ValidateCommand getValidateCommand() {
            return (_validateCommand);
        }

        /**
         * This method sets the conformance command.
         * 
         * @param command The conformance command
         */
        public void setConformsCommand(ConformsCommand command) {
            _conformsCommand = command;
        }
        
        /**
         * This method gets the conformance command.
         * 
         * @return The conformance command
         */
        public ConformsCommand getConformsCommand() {
            return (_conformsCommand);
        }

        /**
         * This method sets the projection command.
         * 
         * @param command The projection command
         */
        public void setProjectCommand(ProjectCommand command) {
            _projectCommand = command;
        }
        
        /**
         * This method gets the projection command.
         * 
         * @return The projection command
         */
        public ProjectCommand getProjectCommand() {
            return (_projectCommand);
        }

        /**
         * This method sets the simulate command.
         * 
         * @param command The simulate command
         */
        public void setSimulateCommand(SimulateCommand command) {
            _simulateCommand = command;
        }
        
        /**
         * This method gets the simulate command.
         * 
         * @return The simulate command
         */
        public SimulateCommand getSimulateCommand() {
            return (_simulateCommand);
        }
   }
}
