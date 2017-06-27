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

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STCaseActionBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class GSTCaseActionBuilder extends STCaseActionBuilder
{

	@Override
	public String getActionName(STStateChanAPIBuilder api, EAction a)
	{
		return "Recv_" + a.peer + "_" + a.mid;
	}

	@Override
	public String buildArgs(EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> "arg" + i + " *" + a.payload.elems.get(i)).collect(Collectors.joining(", "));
	}

	@Override
	public String buildBody(STStateChanAPIBuilder api, EState curr, EAction a, EState succ)
	{
		return 
				  IntStream.range(0, a.payload.elems.size())
							.mapToObj(i -> 
										//"val" + i + " := " + api.getChannelName(api, a) + ".Read()\n"
									  "val" + i + " := s.ep.Read(s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + ")\n"
									+ "if s.ep.Err != nil {\n"
									+ "return nil\n"
								  + "}\n"
									+ "*arg" + i + " = val" + i + ".(" + a.payload.elems.get(i) + ")"
							).collect(Collectors.joining("\n")) + "\n"
				+ buildReturn(api, curr, succ);
	}
	
	@Override
	public String getStateChanType(STStateChanAPIBuilder api, EState curr, EAction a)
	{
		return GSTCaseBuilder.getOpTypeName(api, curr, a.mid);
	}
}
