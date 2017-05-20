package org.scribble.codegen.statetype.go;

import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.codegen.statetype.STCaseBuilder;
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
				+ "type " + api.getStateChanName(s) + "_Cases interface {\n"
			  + casefunc + "()\n"
			  + "}"
			  + s.getActions().stream().map(a ->
			  		  "\n\ntype " + getOpTypeName(a.mid) + " struct{}\n"
			  		+ "\n"
			  	  + "func (" + getOpTypeName(a.mid) + ") " + casefunc + "() {}"
			  	).collect(Collectors.joining(""));
	}
	
	protected static String getOpTypeName(MessageId<?> mid)
	{
		return mid.toString().toUpperCase() + "_";  // FIXME: maybe need more mangling?
	}
}
