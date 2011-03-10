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
package org.scribble.protocol.conformance;

import org.scribble.common.logging.Journal;

/**
 * This interface represents a conformance checking capability for the Protocol
 * description.
 *
 */
public interface ProtocolConformer {

	/**
	 * This method checks whether the supplied model conforms to
	 * the reference model.
	 * 
	 * @param model The model to be verified
	 * @param ref The reference model
	 * @param logger The logger
	 * @return Whether the protocol models conform to each other
	 */
	public boolean conforms(org.scribble.protocol.model.ProtocolModel model,
			org.scribble.protocol.model.ProtocolModel ref,
				Journal logger);
	
}
