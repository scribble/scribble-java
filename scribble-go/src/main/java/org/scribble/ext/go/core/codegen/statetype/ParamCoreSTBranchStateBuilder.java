package org.scribble.ext.go.core.codegen.statetype;

import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STBranchStateBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

public class ParamCoreSTBranchStateBuilder extends STBranchStateBuilder
{
	public ParamCoreSTBranchStateBuilder(ParamCoreSTBranchActionBuilder bb)
	{
		super(bb);
	}

	@Override
	public String getPreamble(STStateChanApiBuilder api, EState s)
	{
		//return ((ParamCoreSTStateChanApiBuilder) api).getStateChanPremable(s);
		ParamCoreSTStateChanApiBuilder apigen = (ParamCoreSTStateChanApiBuilder) api;
		Role r = apigen.actual.getName();
		GProtocolName simpname = apigen.apigen.proto.getSimpleName();
		String tname = apigen.getStateChanName(s);
		String epType = ParamCoreSTEndpointApiGenerator.getGeneratedEndpointType(simpname, r); 
		String res =
				  apigen.apigen.generateRootPackageDecl() + "\n"
				+ "\n"
				+ apigen.apigen.generateScribbleRuntimeImports() + "\n"
				+ "\n"
				+ "type " + tname + " struct{\n"
				//+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + ParamCoreSTApiGenConstants.GO_ENDPOINT_TYPE + "\n" 
				+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epType + "\n" 
				+ ParamCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + ParamCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE +"\n"
				+ s.getActions().stream().map(a -> "_" + a.mid + "_Chan chan *" + apigen.getStateChanName(s.getSuccessor(a)) + "\n")
						.collect(Collectors.joining(""))
				+ "}\n";

		res += "\n"
				+ "func (ep *" + epType + ") New" 
						+ tname
						+ "() *" + tname + " {\n"  // FIXME: factor out
				+ "s := &" + tname + " { " + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": ep"
						+ ", " + ParamCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + ": new(" + ParamCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "), "
						+ s.getActions().stream().map(a -> "_" + a.mid + "_Chan: make(chan *" + apigen.getStateChanName(s.getSuccessor(a))+ ")")
								.collect(Collectors.joining(", "))
						+ "}\n"
				+ "go s.foo()\n"
				+ "return s\n"
				+ "}\n";

		res += "\n"
				+ "func (s *" + tname + ") foo() {\n"
				+ "s." + ParamCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + "." + ParamCoreSTApiGenConstants.GO_LINEARRESOURCE_USE + "()\n"
				+ "var op string = \"\"\n"
				+ "if "
				+ s.getActions().stream().map(a ->
				{
					String sEp = 
							ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
					return
							  "op == \"" + a.mid + "\" {\n"
							+ "s." + "_" + a.mid + "_Chan <- " 
									+ apigen.getSuccStateChan(this.bb, s, s.getSuccessor(a), sEp) + "\n"
							+ "}";
				}).collect(Collectors.joining(" else if ")) + "\n"
				+ "}\n";
		return res;
	}
	
	@Override
	public String build(STStateChanApiBuilder api, EState s)
	{
		String out = getPreamble(api, s);
		
		for (EAction a : s.getActions())
		{
			out += "\n\n";
			if (a instanceof EReceive)  // FIXME: factor out action kind
			{
				out += this.bb.build(api, s, a);  // Getting 1 checks non-unary
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
		}

		return out;
	}
}
