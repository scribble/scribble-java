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

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STCaseBuilder;
import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.model.endpoint.EState;
import org.scribble.type.name.MessageId;

public class GoSTCaseBuilder extends STCaseBuilder
{
	public GoSTCaseBuilder(GoSTCaseActionBuilder cb)
	{
		super(cb);
	}

	protected static String getCaseActionName(STStateChanAPIBuilder api, EState s)
	{
		return api.getStateChanName(s) + "_Case";
	}

	@Override
	public String getCaseStateChanName(STStateChanAPIBuilder api, EState s)
	{
		String name = api.getStateChanName(s) + "_Cases";
		// Should be "private" or not, corresponding to GSTStateChanAPIBuilder.makeSTStateName
		//return ((s.id == api.graph.init.id) ? "_" : "") + name;  // Case state channel itself cannot be the initial state chan
		return name;
	}
	
	@Override
	public String getPreamble(STStateChanAPIBuilder api, EState s)
	{
		String casename = getCaseActionName(api, s);
		return GoSTStateChanAPIBuilder.getPackageDecl(api) + "\n"
				+ "\n"
				+ "import \"org/scribble/runtime/net\"\n"  // Some parts duplicated from GSTStateChanAPIBuilder
				+ "\n"
				+ "type " + getCaseStateChanName(api, s) + " interface {\n"
			  + casename + "()\n"
			  + "}"
			  + s.getActions().stream().map(a ->
			  		  "\n\ntype " + getOpTypeName(api, s, a.mid) + " struct{\n"
						+ "ep *net.MPSTEndpoint\n"  // FIXME: factor out
						+ "state *net.LinearResource\n"  // FIXME: EndSocket special case?  // FIXME: only seems to work as a pointer (something to do with method calls via value recievers?  is it copying the value before calling the function?)
			  		+ "}\n"
			  		+ "\n"
			  	  + "func (*" + getOpTypeName(api, s, a.mid) + ") " + casename + "() {}"
			  	).collect(Collectors.joining(""));
	}
	
	private static Map<String, Integer> seen = new HashMap<>(); // FIXME HACK
	
	protected static String getOpTypeName(STStateChanAPIBuilder api, EState s, MessageId<?> mid)
	{
		String op = mid.toString();
		char c = op.charAt(0);
		if (c < 'A' || c > 'Z')
		{
			throw new RuntimeScribbleException("[go-api-gen] Branch message op should start with a capital letter: " + op);  // FIXME:
		}
		if (GoSTCaseBuilder.seen.containsKey(op))
		{
			if (GoSTCaseBuilder.seen.get(op) != s.id)
			{
				String n = api.getStateChanName(s);  // HACK
				op = op + "_" + api.role + "_" + n.substring(n.lastIndexOf('_') + 1);
			}
		}
		else
		{
			GoSTCaseBuilder.seen.put(op, s.id);
		}
		return op;
	}
}
