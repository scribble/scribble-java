/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.codegen.java.statechanapi.ioifaces;

import java.util.Map;

import org.scribble.codegen.java.statechanapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.util.ScribException;

public class SelectIfaceGen extends IOStateIfaceGen
{
	public SelectIfaceGen(StateChannelApiGenerator apigen, Map<EAction, InterfaceBuilder> actions, EState curr)
	{
		super(apigen, actions, curr);
	}
	
	@Override
	public InterfaceBuilder generateType() throws ScribException
	{
		if (this.curr.getActions().stream().anyMatch((a) -> !a.isSend())) // TODO (connect/disconnect)
		{
			//return null;
			throw new RuntimeException("TODO: " + this.curr);
		}
		return super.generateType();
	}
}
