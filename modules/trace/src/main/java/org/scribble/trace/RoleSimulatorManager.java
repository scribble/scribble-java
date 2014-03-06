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
package org.scribble.trace;

import org.scribble.trace.model.MonitorRoleSimulator;
import org.scribble.trace.model.RoleSimulator;

/**
 * This class provides management of the role simulators.
 *
 */
public class RoleSimulatorManager {

	private static final String MONITOR = "Monitor";
	
	private static java.util.Map<String,Class<? extends RoleSimulator>> _roleSimulatorTypes=
					new java.util.HashMap<String,Class<? extends RoleSimulator>>();
	
	static {
		_roleSimulatorTypes.put(MONITOR, MonitorRoleSimulator.class);
	}
	
	/**
	 * This method registers a role simulator class against a name. This enables the deserialisation
	 * of a trace to instantiate the appropriate role simulator for use in simulating a role.
	 * 
	 * @param name The name of the role simulator
	 * @param type The class of the role simulator
	 */
	public static void registerRoleSimulator(String name, Class<? extends RoleSimulator> type) {
		_roleSimulatorTypes.put(name, type);
	}
	
	/**
	 * This method unregisters a role simulator class associated with the specified name.
	 * 
	 * @param name The name of the role simulator
	 * @param type The class of the role simulator
	 */
	public static void unregisterRoleSimulator(String name) {
		_roleSimulatorTypes.remove(name);
	}
	
	/**
	 * This method returns the role simulator type associated with the supplied name.
	 * 
	 * @param name The name of the role simulator
	 * @return The role simulator's type, or null if not found
	 */
	public static Class<? extends RoleSimulator> getRoleSimulatorType(String name) {
		return (_roleSimulatorTypes.get(name));
	}
	
}
