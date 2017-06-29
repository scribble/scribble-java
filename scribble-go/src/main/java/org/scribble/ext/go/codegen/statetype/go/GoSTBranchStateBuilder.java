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

import org.scribble.codegen.statetype.STBranchStateBuilder;
import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.model.endpoint.EState;

public class GoSTBranchStateBuilder extends STBranchStateBuilder
{
	public GoSTBranchStateBuilder(GoSTBranchActionBuilder bb)
	{
		super(bb);
	}

	@Override
	public String getPreamble(STStateChanAPIBuilder api, EState s)
	{
		/*String ename = getBranchEnumType(api, s);
		List<EAction> as = s.getActions();*/
		return GoSTStateChanAPIBuilder.getStateChanPremable(api, s) /*+ "\n"
				+ "\n"
				+ "type " + ename + " int\n"
				+ "\n"
				+ "const (\n"
				+ getBranchEnumValue(as.get(0).mid) + " " + ename + " = iota \n"
				+ as.subList(1, as.size()).stream().map(a -> getBranchEnumValue(a.mid)).collect(Collectors.joining("\n")) + "\n"
				+ ")"*/;
	}
	
	/*protected static String getBranchEnumType(STStateChanAPIBuilder api, EState s)
	{
		return api.getStateChanName(s) + "_Enum";
	}
	
	protected static String getBranchEnumValue(MessageId<?> mid)
	{
		return "_" + mid;
	}*/
}
