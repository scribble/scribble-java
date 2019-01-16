package org.scribble.ext.go.core.codegen.statetype.nested;

import java.util.HashMap;
import java.util.Map;

import org.scribble.codegen.statetype.STCaseBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTStateChanApiBuilder;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.MessageSigName;

public class RPCoreNCaseBuilder extends STCaseBuilder
{
	public RPCoreNCaseBuilder(RPCoreNCaseActionBuilder cb)
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
		return api.getStateChanName(s) + "_Cases";
	}
	
	@Override
	public String getPreamble(STStateChanApiBuilder apib, EState s)
	{
		RPCoreSTStateChanApiBuilder rpapib = (RPCoreSTStateChanApiBuilder) apib;
		String casename = getCaseActionName(apib, s);
		String res = "package " + rpapib.parent.namegen.getEndpointKindPackageName(rpapib.variant) + "\n"
				+ "\n";
		
		// FIXME: factor out
		switch (rpapib.parent.mode)
		{
			case Int:  res += "import \"" + RPCoreSTApiGenConstants.INT_RUNTIME_SESSION_PACKAGE + "\"\n";  break;
			case IntPair:  res += "import \"" + RPCoreSTApiGenConstants.INTPAIR_RUNTIME_SESSION_PACKAGE + "\"\n";  break;
			default:  throw new RuntimeException("Shouldn't get in here: " + rpapib.parent.mode);
		}

		res += 
				"import \"log\"\n"
				+ ((RPCoreSTStateChanApiBuilder) apib).makeMessageImports(s, true)
				+ "\n"
				+ "var _ = log.Fatal\n"
				+ "\n"
				
				// Case object interface
				+ "type " + getCaseStateChanName(apib, s) + " interface {\n"
			  + casename + "()\n"
			  + "}\n";
			  
		// Case object types
		for (EAction a: s.getAllActions()) 
		{
			String extName = (a.mid.isOp()) ? //rpapi.getExtName((DataType) a.mid) 
					  getOpTypeName(apib, s, a.mid)
					: rpapib.getExtName((MessageSigName) a.mid);
			res += "\ntype " + getOpTypeName(apib, s, a.mid) + " struct {\n"
						+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " error\n"  // FIXME: never set -- branch action won't return an actual case object upon error
						+ RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + " *" + rpapib.parent.namegen.getEndpointKindTypeName(rpapib.variant) + "\n" 
						+ RPCoreSTApiGenConstants.SCHAN_RES_FIELD + " *" + RPCoreSTApiGenConstants.LINEARRESOURCE_TYPE + "\n"
						+ (a.mid.isMessageSigName() ? "msg *" + extName + "\n" : "")
						+ (rpapib.parent.job.parForeach ? "Thread int\n" : "")
			  		+ "}\n"

			  		+ "\n"

			  	  + "func (*" + getOpTypeName(apib, s, a.mid) + ") " + casename + "() {}\n";
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
		if (RPCoreNCaseBuilder.seen.containsKey(op))
		{
			if (RPCoreNCaseBuilder.seen.get(op) != s.id)
			{
				String n = api.getStateChanName(s);  // HACK
				op = op + "_" + api.role + "_" + n.substring(n.lastIndexOf('_') + 1);
			}
		}
		else
		{
			RPCoreNCaseBuilder.seen.put(op, s.id);
		}
		return op;
	}
}
