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
import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class GoSTSendActionBuilder extends STSendActionBuilder
{

	@Override
	public String getActionName(STStateChanAPIBuilder api, EAction a)
	{
		return "Send_" + a.peer + "_" + a.mid;
	}

	@Override
	public String buildArgs(EAction a)
	{
		return IntStream.range(0, a.payload.elems.size()) 
					.mapToObj(i -> "arg" + i + " " + a.payload.elems.get(i)).collect(Collectors.joining(", "));
	}

	@Override
	public String buildBody(STStateChanAPIBuilder api, EState curr, EAction a, EState succ)
	{
		//String chan = api.getChannelName(api, a);
		return 
				  ////chan + ".Write(" + GSTBranchStateBuilder.getBranchEnumValue(a.mid) + ")\n"
				  //chan + ".Write(\"" + a.mid + "\")\n"
				  "s.ep.Write(s.ep.Proto.(*" + api.gpn.getSimpleName() +")." + a.peer + ", \"" + a.mid + "\")\n"
				+ IntStream.range(0, a.payload.elems.size())
				      //.mapToObj(i -> chan + ".Write(arg" + i + ")").collect(Collectors.joining("\n")) + "\n"
				      .mapToObj(i -> "s.ep.Write(s.ep.Proto.(*" + api.gpn.getSimpleName() +")." + a.peer + ", arg" + i + ")").collect(Collectors.joining("\n")) + "\n"
				+ "if s.ep.Err != nil {\n"
				+ "return nil"
				+ "}\n"
				+ buildReturn(api, curr, succ);
	}
}
