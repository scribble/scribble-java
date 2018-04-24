package org.scribble.ext.go.core.codegen.statetype3;

import java.util.HashMap;
import java.util.Map;

import org.scribble.codegen.statetype.STCaseBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.MessageSigName;

public class RPCoreSTCaseBuilder extends STCaseBuilder
{
	public RPCoreSTCaseBuilder(RPCoreSTCaseActionBuilder cb)
	{
		super(cb);
	}

	protected static String getCaseActionName(STStateChanApiBuilder api, EState s)
	{
		return api.getStateChanName(s) + "_Case";
	}

	@Override
	public String getCaseStateChanName(STStateChanApiBuilder api, EState s)
	{
		String name = api.getStateChanName(s) + "_Cases";
		return name;
	}
	
	@Override
	public String getPreamble(STStateChanApiBuilder api, EState s)
	{
		RPCoreSTStateChanApiBuilder rpapi = (RPCoreSTStateChanApiBuilder) api;
		String casename = getCaseActionName(api, s);
		String res = "package " + RPCoreSTApiGenerator.getEndpointKindPackageName(rpapi.variant) + "\n"
				+ "\n"
				+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n"
				//+ "import \"log\"\n"
				+ ((RPCoreSTStateChanApiBuilder) api).makeMessageImports(s)
				+ "\n"
				
				// Case object interface
				+ "type " + getCaseStateChanName(api, s) + " interface {\n"
			  + casename + "()\n"
			  + "}\n";
			  
		// Case object types
		for (EAction a: s.getAllActions()) 
		{
			String extName = (a.mid.isOp()) ? rpapi.getExtName((DataType) a.mid) : rpapi.getExtName((MessageSigName) a.mid);
			res += "\ntype " + getOpTypeName(api, s, a.mid) + " struct {\n"
						+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + RPCoreSTApiGenerator.getEndpointKindTypeName(null, rpapi.variant) + "\n" 
						+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "\n"
						+ (a.mid.isMessageSigName() ? "msg *" + extName + "\n" : "")
			  		+ "}\n"
			  		+ "\n"
			  	  + "func (*" + getOpTypeName(api, s, a.mid) + ") " + casename + "() {}\n";
		}

		return res;
	}
	
	private static Map<String, Integer> seen = new HashMap<>(); // FIXME HACK
	
	// For type switch on Ops as types ("enum")
	protected static String getOpTypeName(STStateChanApiBuilder api, EState s, MessageId<?> mid)
	{
		String op = mid.toString();
		char c = op.charAt(0);
		if (c < 'A' || c > 'Z')
		{
			throw new RuntimeScribbleException("[rp-core] Branch message op should start with a capital letter: " + op);  // FIXME:
		}
		if (RPCoreSTCaseBuilder.seen.containsKey(op))
		{
			if (RPCoreSTCaseBuilder.seen.get(op) != s.id)
			{
				String n = api.getStateChanName(s);  // HACK
				op = op + "_" + api.role + "_" + n.substring(n.lastIndexOf('_') + 1);
			}
		}
		else
		{
			RPCoreSTCaseBuilder.seen.put(op, s.id);
		}
		return op;
	}
}
