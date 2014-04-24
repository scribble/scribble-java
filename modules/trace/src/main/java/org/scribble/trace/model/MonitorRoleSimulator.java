/*
 * Copyright 2009-14 www.scribble.org
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
package org.scribble.trace.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.scribble.context.DefaultModuleContext;
import org.scribble.context.ModuleLoader;
import org.scribble.logging.ConsoleIssueLogger;
import org.scribble.model.Module;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.Role;
import org.scribble.model.local.LProtocolDefinition;
import org.scribble.monitor.DefaultMonitor;
import org.scribble.monitor.Message;
import org.scribble.monitor.Monitor;
import org.scribble.monitor.SessionInstance;
import org.scribble.monitor.export.MonitorExporter;
import org.scribble.monitor.model.SessionType;
import org.scribble.parser.ProtocolModuleLoader;
import org.scribble.parser.ProtocolParser;
import org.scribble.projection.ProtocolProjector;
import org.scribble.trace.simulation.SimulatorContext;

/**
 * This abstract class represents a simulator associated with a
 * role in a message trace.
 *
 */
public class MonitorRoleSimulator extends RoleSimulator {
	
	private static final Logger LOG=Logger.getLogger(MonitorRoleSimulator.class.getName());

	private String _module;
	private String _role;
	private String _protocol;
	
	private SessionType _type;
	private SessionInstance _instance;
	
	private static final ProtocolParser PARSER=new ProtocolParser();
	private static final MonitorExporter EXPORTER=new MonitorExporter();
	private static final Monitor MONITOR=new DefaultMonitor();
	
	/**
	 * This method returns the local module to be monitored.
	 * 
	 * @return The local module
	 */
	public String getModule() {
		return (_module);
	}
	
	/**
	 * This method sets the local module to be monitored.
	 * 
	 * @param module The local module
	 * @return The simulator
	 */
	public MonitorRoleSimulator setModule(String module) {
		_module = module;
		return (this);
	}

	/**
	 * This method returns the role within the module to be monitored.
	 * 
	 * @return The role
	 */
	public String getRole() {
		return (_role);
	}
	
	/**
	 * This method sets the role within the module to be monitored.
	 * 
	 * @param role The role
	 * @return The simulator
	 */
	public MonitorRoleSimulator setRole(String role) {
		_role = role;
		return (this);
	}

	/**
	 * This method returns the protocol to be monitored.
	 * 
	 * @return The protocol
	 */
	public String getProtocol() {
		return (_protocol);
	}
	
	/**
	 * This method sets the protocol to be monitored.
	 * 
	 * @param protocol The protocol
	 * @return The simulator
	 */
	public MonitorRoleSimulator setProtocol(String protocol) {
		_protocol = protocol;
		return (this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(SimulatorContext context) {
		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("Init monitor role simulator for module="+_module+", role="
							+_role+" and protocol="+_protocol);
		}
		
		// TODO: Need to consider different logger impl?
		ModuleLoader loader=new ProtocolModuleLoader(PARSER, context.getResourceLocator(), 
							new ConsoleIssueLogger());
		
		String localModule=_module+"_"+_role;
		
		Module module=loader.loadModule(localModule);
		
		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("Local module="+module);
		}
		
		if (module == null) {
			// Attempt to project the local module from a global module
			Module global=loader.loadModule(_module);
			
			if (LOG.isLoggable(Level.FINE)) {
				LOG.fine("Global module="+global);
			}
			
			if (global != null) {
				// Project
				ProtocolProjector projector=new ProtocolProjector();
				
				DefaultModuleContext mc=new DefaultModuleContext(null, global, loader);

				java.util.Set<Module> locals=projector.project(mc, global, new ConsoleIssueLogger());
				
				for (Module lm : locals) {
					Role located=lm.getLocatedRole();

					if (LOG.isLoggable(Level.FINE)) {
						LOG.fine("Check module="+lm+" with located role="+located);
					}
					
					if (located != null && located.getName().equals(_role)) {
						module = lm;
						break;
					}
				}
			}
		}
		
		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("Module to simulate="+module);
		}

		if (module != null) {
			ProtocolDecl pd=module.getProtocol(_protocol);
			
			if (pd instanceof LProtocolDefinition) {
				DefaultModuleContext mc=new DefaultModuleContext(null, pd.getModule(), loader);
				
	    		_type = EXPORTER.export(mc, (LProtocolDefinition)pd);
	    		
	    		_instance = new SessionInstance();
	    		
	    		MONITOR.initializeInstance(_type, _instance);
			}
		} else {
			_type = null;
			_instance = null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean send(SimulatorContext context, Message mesg, String toRole) {
		if (_type != null && _instance != null) {
			return (MONITOR.sent(_type, _instance, mesg, toRole));
		}
		return (false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean receive(SimulatorContext context, Message mesg, String fromRole) {
		if (_type != null && _instance != null) {
			return (MONITOR.received(_type, _instance, mesg, fromRole));
		}
		return (false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close(SimulatorContext context) {
		_instance = null;
		_type = null;
	}
	
}
