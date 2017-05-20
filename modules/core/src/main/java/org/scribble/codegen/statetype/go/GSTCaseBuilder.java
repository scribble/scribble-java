package org.scribble.codegen.statetype.go;

import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STAPIBuilder;
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
	public String getPreamble(STAPIBuilder api, EState s)
	{
		String casefunc = api.getSTStateName(s) + "_Case";  // FIXME: factor out with branch action builder
		return GSTOutputStateBuilder.getPackageDecl(api) + "\n"
				+ "\n"
				+ "type " + api.getSTStateName(s) + "_Cases interface {\n"
			  + casefunc + "()\n"
			  + "}\n"
			  + s.getActions().stream().map(a ->
			  		  "\ntype " + getOpTypeName(a.mid) + " struct{}\n"
			  		+ "\n"
			  	  + "func (" + getOpTypeName(a.mid) + ") " + casefunc + "() {}\n"
			  	).collect(Collectors.joining("")) + "\n";
	}
	
	protected static String getOpTypeName(MessageId<?> mid)
	{
		return mid.toString().toUpperCase() + "_";  // FIXME: maybe need more mangling?
	}
}
