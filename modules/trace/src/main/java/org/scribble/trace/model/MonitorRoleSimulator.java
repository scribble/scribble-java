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

import org.scribble.context.DefaultModuleContext;
import org.scribble.context.ModuleLoader;
import org.scribble.logging.ConsoleIssueLogger;
import org.scribble.model.Module;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.local.LProtocolDefinition;
import org.scribble.monitor.DefaultMonitor;
import org.scribble.monitor.Message;
import org.scribble.monitor.Monitor;
import org.scribble.monitor.MonitorContext;
import org.scribble.monitor.SessionInstance;
import org.scribble.monitor.export.MonitorExporter;
import org.scribble.monitor.model.SessionType;
import org.scribble.parser.ProtocolModuleLoader;
import org.scribble.parser.ProtocolParser;
import org.scribble.trace.SimulatorContext;

/**
 * This abstract class represents a simulator associated with a
 * role in a message trace.
 *
 */
public class MonitorRoleSimulator extends RoleSimulator {

	private String _module;
	private String _protocol;
	
	private MonitorContext _context;

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
		// TODO: Need to consider different logger impl?
		ModuleLoader loader=new ProtocolModuleLoader(PARSER, context.getResourceLocator(), 
							new ConsoleIssueLogger());
		
		Module module=loader.loadModule(_module);
		
		if (module != null) {
			ProtocolDecl pd=module.getProtocol(_protocol);
			
			if (pd instanceof LProtocolDefinition) {
				DefaultModuleContext mc=new DefaultModuleContext(null, pd.getModule(), loader);
				
	    		_type = EXPORTER.export(mc, (LProtocolDefinition)pd);
	    		
	    		_context = null;
	    		_instance = new SessionInstance();
	    		
	    		MONITOR.initialize(null, _type, _instance);
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
			return (MONITOR.sent(_context, _type, _instance, mesg, toRole));
		}
		return (false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean receive(SimulatorContext context, Message mesg, String fromRole) {
		if (_type != null && _instance != null) {
			return (MONITOR.received(_context, _type, _instance, mesg, fromRole));
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
