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

import org.scribble.command.conforms.ConformsCommand;
import org.scribble.command.parse.ParseCommand;
import org.scribble.command.project.ProjectCommand;
import org.scribble.command.validate.ValidateCommand;
import org.scribble.command.simulate.SimulateCommand;
import org.scribble.command.*;
import org.scribble.common.logging.Journal;
import org.scribble.protocol.conformance.ProtocolConformer;
import org.scribble.protocol.export.ProtocolExportManager;
import org.scribble.protocol.monitor.ProtocolMonitor;
import org.scribble.protocol.parser.ProtocolParserManager;
import org.scribble.protocol.projection.ProtocolProjector;
import org.scribble.protocol.validation.ProtocolValidationManager;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
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
        ServiceListener sl1 = new ServiceListener() {
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
              
        ServiceListener sl2 = new ServiceListener() {
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
              
        ServiceListener sl3 = new ServiceListener() {
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
              
        ServiceListener sl4 = new ServiceListener() {
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
              
        ServiceListener sl5 = new ServiceListener() {
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
              
        ServiceListener sl6 = new ServiceListener() {
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
              
        ServiceListener sl7 = new ServiceListener() {
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
              
        String filter1 = "(objectclass=" + ProtocolValidationManager.class.getName() + ")";
        String filter2 = "(objectclass=" + Journal.class.getName() + ")";
        String filter3 = "(objectclass=" + ProtocolParserManager.class.getName() + ")";
        String filter4 = "(objectclass=" + ProtocolConformer.class.getName() + ")";
        String filter5 = "(objectclass=" + ProtocolProjector.class.getName() + ")";
        String filter6 = "(objectclass=" + ProtocolExportManager.class.getName() + ")";
        String filter7 = "(objectclass=" + ProtocolMonitor.class.getName() + ")";

        
        try {
            ServiceReference sref=context.getServiceReference(ProtocolValidationManager.class.getName());
            ProtocolValidationManager pvm=(ProtocolValidationManager)context.getService(sref);

    		if (pvm != null) {
    			commands.setProtocolValidationManager(pvm);
    		} else {
            	context.addServiceListener(sl1, filter1);
    		}
            
    		sref=context.getServiceReference(Journal.class.getName());         
    		Journal journal=(Journal)context.getService(sref);

    		if (journal != null) {
    			commands.setJournal(journal);
    		} else {
            	context.addServiceListener(sl2, filter2);
    		}
    		
    		sref=context.getServiceReference(ProtocolParserManager.class.getName());         
    		ProtocolParserManager pp=null;
    		
    		if (sref != null) {
    			pp = (ProtocolParserManager)context.getService(sref);
    		}

    		if (pp != null) {
    			commands.setProtocolParserManager(pp);
    		} else {
            	context.addServiceListener(sl3, filter3);
    		}
    		
    		sref=context.getServiceReference(ProtocolConformer.class.getName());         
    		ProtocolConformer pc=null;
    		
    		if (sref != null) {
    			pc = (ProtocolConformer)context.getService(sref);
    		}

    		if (pc != null) {
    			commands.setProtocolConformer(pc);
    		} else {
            	context.addServiceListener(sl4, filter4);
    		}
    		
    		sref=context.getServiceReference(ProtocolProjector.class.getName());         
    		ProtocolProjector ppj=null;
    		
    		if (sref != null) {
    			ppj = (ProtocolProjector)context.getService(sref);
    		}

    		if (ppj != null) {
    			commands.setProtocolProjector(ppj);
    		} else {
            	context.addServiceListener(sl5, filter5);
    		}
    		
    		sref=context.getServiceReference(ProtocolExportManager.class.getName());
    		ProtocolExportManager pem=null;
    		
    		if (sref != null) {
    			pem = (ProtocolExportManager)context.getService(sref);
    		}

    		if (pem != null) {
    			commands.setProtocolExportManager(pem);
    		} else {
            	context.addServiceListener(sl6, filter6);
    		}
    		
    		sref=context.getServiceReference(ProtocolMonitor.class.getName());         
    		ProtocolMonitor pm=null;
    		
    		if (sref != null) {
    			pm = (ProtocolMonitor)context.getService(sref);
    		}

    		if (pm != null) {
    			commands.setProtocolMonitor(pm);
    		} else {
            	context.addServiceListener(sl7, filter7);
    		}

        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) {
 	}

	protected class Commands {
		
		public void setProtocolValidationManager(ProtocolValidationManager vm) {
			m_validateCommand.setValidationManager(vm);
		}
		
		public void setJournal(Journal journal) {
			m_parseCommand.setJournal(journal);
			m_validateCommand.setJournal(journal);
			m_conformsCommand.setJournal(journal);
			m_projectCommand.setJournal(journal);
			m_simulateCommand.setJournal(journal);			
		}
		
		public void setProtocolParserManager(ProtocolParserManager pp) {
			m_parseCommand.setProtocolParserManager(pp);
			m_validateCommand.setProtocolParserManager(pp);
			m_conformsCommand.setProtocolParserManager(pp);
			m_projectCommand.setProtocolParserManager(pp);
			m_simulateCommand.setProtocolParserManager(pp);
		}
		
		public void setProtocolConformer(ProtocolConformer pc) {
			m_conformsCommand.setConformer(pc);
		}
		
		public void setProtocolProjector(ProtocolProjector pp) {
			m_projectCommand.setProtocolProjector(pp);
		}

		public void setProtocolExportManager(ProtocolExportManager em) {
   			m_projectCommand.setProtocolExportManager(em);
			m_simulateCommand.setProtocolExportManager(em);
		}
		
		public void setProtocolMonitor(ProtocolMonitor pm) {
			m_simulateCommand.setProtocolMonitor(pm);
		}
		
		public void setParseCommand(ParseCommand command) {
			m_parseCommand = command;
		}
		
		public ParseCommand getParseCommand() {
			return(m_parseCommand);
		}

		public void setValidateCommand(ValidateCommand command) {
			m_validateCommand = command;
		}
		
		public ValidateCommand getValidateCommand() {
			return(m_validateCommand);
		}

		public void setConformsCommand(ConformsCommand command) {
			m_conformsCommand = command;
		}
		
		public ConformsCommand getConformsCommand() {
			return(m_conformsCommand);
		}

		public void setProjectCommand(ProjectCommand command) {
			m_projectCommand = command;
		}
		
		public ProjectCommand getProjectCommand() {
			return(m_projectCommand);
		}

		public void setSimulateCommand(SimulateCommand command) {
			m_simulateCommand = command;
		}
		
		public SimulateCommand getSimulateCommand() {
			return(m_simulateCommand);
		}
		
		private ParseCommand m_parseCommand=null;
		private ValidateCommand m_validateCommand=null;
		private ConformsCommand m_conformsCommand=null;
		private ProjectCommand m_projectCommand=null;		
		private SimulateCommand m_simulateCommand=null;
	}
}
