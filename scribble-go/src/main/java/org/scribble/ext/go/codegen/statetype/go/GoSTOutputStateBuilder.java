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
package org.scribble.ext.go.codegen.statetype.go;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STOutputStateBuilder;
import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.model.endpoint.EState;

public class GoSTOutputStateBuilder extends STOutputStateBuilder
{
	public GoSTOutputStateBuilder(STSendActionBuilder sb)
	{
		super(sb);
	}
	
	@Override
	public String getPreamble(STStateChanAPIBuilder api, EState s)
	{
		return GoSTStateChanAPIBuilder.getStateChanPremable(api, s);
	}
}
