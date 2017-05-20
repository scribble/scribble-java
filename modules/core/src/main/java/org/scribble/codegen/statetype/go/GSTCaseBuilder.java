package org.scribble.codegen.statetype.go;

import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STAPIBuilder;
import org.scribble.codegen.statetype.STCaseBuilder;
import org.scribble.model.endpoint.EState;

public class GSTCaseBuilder extends STCaseBuilder
{
	public GSTCaseBuilder(GSTCaseActionBuilder cb)
	{
		super(cb);
	}

	@Override
	public String getPreamble(STAPIBuilder api, EState s)
	{
		return GSTOutputStateBuilder.getPackageDecl(api) + "\n"
				+ "\n"
				+ "type " + api.getSTStateName(s) + "_Cases interface {\n"
			  + api.getSTStateName(s) + "_Case()\n"  // FIXME: factor out
			  + "}\n"
			  + s.getActions().stream().map(a ->
			  		  "\ntype " + a.mid + "_ struct{}\n"  // FIXME: factor out
			  		+ "\n"
			  	  + "func (" + a.mid + "_ " + ") " + api.getSTStateName(s) + "_Case() {}\n"  // FIXME: factor out
			  	).collect(Collectors.joining("")) + "\n";
	}
}
