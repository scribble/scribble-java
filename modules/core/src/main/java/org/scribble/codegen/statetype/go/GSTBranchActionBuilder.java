package org.scribble.codegen.statetype.go;

import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STBranchActionBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;

public class GSTBranchActionBuilder extends STBranchActionBuilder
{
	@Override
	public String build(STStateChanAPIBuilder api, EState curr, EAction a)  // FIXME: "overriding" GSTStateChanAPIBuilder.buildAction to hack around *interface return  // FIXME: factor out
	{
		EState succ = curr.getSuccessor(a); 
		return
				  "func (s *" + getStateChanType(api, curr, a) + ") " + getActionName(api, a) + "(" 
				+ buildArgs(a)
				+ ") " + getReturnType(api, curr, succ) + " {\n"  // HACK: Return type is interface, so no need for *return (unlike other state chans)
				+ "s.state.Use()\n"
				+ buildBody(api, curr, a, succ) + "\n"
				+ "}";
	}

	@Override
	public String getActionName(STStateChanAPIBuilder api, EAction a)
	{
		return "Branch_" + a.peer;
	}

	@Override
	public String buildArgs(EAction a)
	{
		return "";
	}

	@Override
	public String getReturnType(STStateChanAPIBuilder api, EState curr, EState succ)
	{
		return api.cb.getCaseStateChanName(api, curr);
	}

	@Override
	public String buildBody(STStateChanAPIBuilder api, EState curr, EAction a, EState succ)
	{
		return 
				  "tmp := " + api.getChannelName(api, a) + ".Read()\n"
				//+ "op := tmp.(" + GSTBranchStateBuilder.getBranchEnumType(api, curr) + ")\n"
				+ "op := tmp.(string)\n"
				+ "switch op {\n"
				+ curr.getActions().stream().map(x -> 
						  //"case " + GSTBranchStateBuilder.getBranchEnumValue(x.mid) + ":\n"
						  "case \"" + x.mid + "\":\n"
						+ "return &" + GSTCaseBuilder.getOpTypeName(api, curr, x.mid) +"{ ep: s.ep, state: &net.LinearResource {} }\n"  // FIXME: factor out
					).collect(Collectors.joining(""))
				+ "default: panic(\"Shouldn't get in here: \" + op)\n"
				+ "}\n"
				+ "return nil";  // FIXME: panic instead
	}

}
