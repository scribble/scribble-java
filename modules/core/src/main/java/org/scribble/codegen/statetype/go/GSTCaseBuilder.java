package org.scribble.codegen.statetype.go;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STCaseBuilder;
import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.model.endpoint.EState;
import org.scribble.sesstype.name.MessageId;

public class GSTCaseBuilder extends STCaseBuilder
{
	public GSTCaseBuilder(GSTCaseActionBuilder cb)
	{
		super(cb);
	}

	@Override
	public String getPreamble(STStateChanAPIBuilder api, EState s)
	{
		String casefunc = api.getStateChanName(s) + "_Case";  // FIXME: factor out with branch action builder
		return GSTStateChanAPIBuilder.getPackageDecl(api) + "\n"
				+ "\n"
				+ "import \"org/scribble/runtime/net\"\n"  // Some parts duplicated from GSTStateChanAPIBuilder
				+ "\n"
				+ "type " + api.getStateChanName(s) + "_Cases interface {\n"
			  + casefunc + "()\n"
			  + "}"
			  + s.getActions().stream().map(a ->
			  		  "\n\ntype " + getOpTypeName(api, s, a.mid) + " struct{\n"
						+ "ep *net.MPSTEndpoint\n"  // FIXME: factor out
						+ "state *net.LinearResource\n"  // FIXME: EndSocket special case?  // FIXME: only seems to work as a pointer (something to do with method calls via value recievers?  is it copying the value before calling the function?)
			  		+ "}\n"
			  		+ "\n"
			  	  + "func (" + getOpTypeName(api, s, a.mid) + ") " + casefunc + "() {}"
			  	).collect(Collectors.joining(""));
	}
	
	private static Map<String, Integer> seen = new HashMap<>(); // FIXME HACK
	
	protected static String getOpTypeName(STStateChanAPIBuilder api, EState s, MessageId<?> mid)
	{
		String op = mid.toString();
		char c = op.charAt(0);
		if (c < 'A' || c > 'Z')
		{
			throw new RuntimeScribbleException("[go-api-gen] Message op should start with a captial letter, not: " + c);  // FIXME:
		}
		if (GSTCaseBuilder.seen.containsKey(op))
		{
			if (GSTCaseBuilder.seen.get(op) != s.id)
			{
				String n = api.getStateChanName(s);  // HACK
				op = op + "_" + n.substring(n.lastIndexOf('_') + 1);
			}
		}
		else
		{
			GSTCaseBuilder.seen.put(op, s.id);
		}
		return op;
	}
}
