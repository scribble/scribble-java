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
package org.scribble.protocol.conformance.impl;

import org.scribble.protocol.conformance.ConformanceHandler;
import org.scribble.protocol.conformance.ProtocolConformer;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.ProtocolModel;

public class ProtocolConformerImpl implements ProtocolConformer {

	@Override
	public void conforms(ProtocolModel model, ProtocolModel ref,
			ConformanceHandler handler) {
		
		// Normalise the models
		model = ProtocolNormalizer.normalize(model);
		ref = ProtocolNormalizer.normalize(ref);
		
		// Compare the protocols
		compare(model.getProtocol(), ref.getProtocol(), handler);
	}

	protected void compare(ModelObject model, ModelObject ref, ConformanceHandler handler) {
	}
}
