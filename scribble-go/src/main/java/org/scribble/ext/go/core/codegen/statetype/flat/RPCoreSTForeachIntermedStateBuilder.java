package org.scribble.ext.go.core.codegen.statetype.flat;

import java.util.HashMap;
import java.util.Map;

import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.codegen.statetype.STStateChanBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenerator.Mode;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;

public class RPCoreSTForeachIntermedStateBuilder extends STStateChanBuilder
{
	public RPCoreSTForeachIntermedStateBuilder()
	{
	}
	
	@Override
	public String getPreamble(STStateChanApiBuilder scb, EState outer)
	{
		return ((RPCoreSTStateChanApiBuilder) scb).getStateChanPremable(s);
	}
	
	@Override
	public String build(STStateChanApiBuilder scb, EState outer)
	{
		String out = getPreamble(scb, outer);
		
		String scTypeName = this.names.get(outer.id);  //this.getStateChanName(s);  //this.getIntermediaryStateChanName(s);
		String epkindTypeName = this.parent.namegen.getEndpointKindTypeName(this.variant); 
		
		Map<String, String> res = new HashMap<>();
		
		String feach =
				  "package " + this.parent.namegen.getEndpointKindPackageName(this.variant) + "\n"
				+ "\n";
	
		// FIXME: factor out
		if (this.parent.mode == Mode.IntPair)
		{
			feach += "import \"" + RPCoreSTApiGenConstants.INT_RUNTIME_SESSION_PACKAGE + "\"\n";
		}
		else if (this.parent.job.parForeach)
		{
			feach += "import \"" + RPCoreSTApiGenConstants.INTPAIR_RUNTIME_SESSION_PACKAGE + "\"\n";
		}

		// State channel type
		feach += "\n"
				+ "type " + scTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " error\n";

		if (this.parent.job.parForeach)
		{
			feach += RPCoreSTApiGenConstants.SCHAN_RES_FIELD + " *" + RPCoreSTApiGenConstants.LINEARRESOURCE_TYPE + "\n";
		}
		else
		{
			feach += "id uint64\n";
		}

		feach += RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + " *" + epkindTypeName + "\n";
		
		if (this.parent.job.parForeach)
		{
			feach += "Thread int\n";
		}

		feach += "}\n";
		
		// Foreach method -- cf. RPCoreSTSessionApiBuilder, top-level Run
		String sEp = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_EPT_FIELD;
		RPCoreEState init = outer.getNested();
		RPCoreEState term = (RPCoreEState) MState.getTerminal(init);
		String initName = this.names.get(init.id); //"Init_" + init.id;
		String termName = (term == null) ? "End" : this.names.get(term.id);
		String succName = outer.isTerminal() ? "End" : getStateChanName(outer);  // FIXME: factor out
			//getIntermediaryStateChanName(s);  // Functionality subsumed by getStateChanName
		//RPForeachVar p = s.getParam();
		RPIndexVar p = outer.getParam();

		this.fvars.add(p); // HACK FIXME: state visiting order not guaranteed (w.r.t. lexical var scope)

		// TODO: factor out with send, receive, etc.
		String lte;
		String inc;
		switch (this.parent.mode)
		{
			case Int:  
			{
				lte = " <= " + generateIndexExpr(outer.getInterval().end);  
				inc = p + "+1";
				break;
			}
			case IntPair:  
			{
				lte = ".Lte(" + generateIndexExpr(outer.getInterval().end) + ")";  
				inc = p + ".Inc(" + generateIndexExpr(outer.getInterval().end) + ")";
				break;
			}
			default:  throw new RuntimeException("Shouldn't get in here: " + this.parent.mode);
		}

    String initState = sEp + "._" + initName;
		feach += "\n"
				+ "func (" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + " *" + scTypeName
						+ ") Foreach(f func(*" + initName + ") " + termName + ") *" + succName + " {\n";
						
		// Duplicated from buildAction
		feach +=
				  "if " + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " != nil {\n"
				+ "panic(" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + ")\n"
				+ "}\n";

		if (this.parent.job.parForeach)
		{
			feach += RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_RES_FIELD
								+ "." + RPCoreSTApiGenConstants.LINEARRESOURCE_USE + "()\n";
		}
		else
		{
			feach += "if " + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + "id != "  // Not using atomic.LoadUint64 on id for now
										//+ "atomic.LoadUint64(&" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + "lin)"
										+ sEp + "." + "lin"
										+ " {\n"
								+ "panic(\"Linear resource already used\")\n" // + reflect.TypeOf(" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "))\n"
								+ "}\n";
		}
	
		feach +=
						  "for " + p + " := " + generateIndexExpr(outer.getInterval().start) + "; "  // FIXME: general interval expressions
								+ p + lte + "; " + p + " = " + inc + "{\n";
						//+ sEp + "." + s.getParam() + "=" + s.getParam() + "\n"  // FIXME: nested Endpoint type/struct?
		
		if (this.parent.job.parForeach)
		{
			feach += sEp + ".Params[" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + ".Thread][\"" + p + "\"] = " + p + " \n";
		}
		else
		{
			feach += sEp + ".Params[\"" + p + "\"] = " + p + "\n";  // FIXME: nested Endpoint type/struct?
		}

		if (this.parent.job.parForeach)
		{
			feach += "nested := " + this.parent.makeStateChanInstance(initName, sEp, RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + ".Thread");
			//feach += "nested.id = " + sEp + ".lin\n";
			feach += "f(nested)\n";
		}
		else
		{
			feach +=
					// Duplicated from RPCoreSTSessionApiBuilder  // FIXME: factor out with makeReturnSuccStateChan
					/*+ ((this.apigen.job.selectApi && init.getStateKind() == EStateKind.POLY_INPUT)
							? "f(newBranch" + initName + "(ini))\n"
							: "f(&" + initName + "{ nil, new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "), " + sEp + " })\n")  // cf. state chan builder  // FIXME: chan struct reuse*/
							sEp + ".lin = "  // FIXME: sync
						+ sEp + ".lin + 1\n";
			feach += initState +".id = " + sEp + ".lin\n";
			feach += "f(" + initState + ")\n";
		}

		feach += "}\n";
		
		feach += //+ "return " + makeCreateSuccStateChan(s, succName) + "\n"
				  makeReturnSuccStateChan(outer, succName) + "\n"
				+ "}\n";
		
		// Parallel method
		if (this.parent.job.parForeach)
		{
			feach += "\n"
					+ "func (" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + " *" + scTypeName
							+ ") Parallel(f func(*" + initName + ") " + termName + ") *" + succName + " {\n";
							
			// Duplicated from buildAction
			feach +=
						"if " + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " != nil {\n"
					+ "panic(" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + ")\n"
					+ "}\n";

			feach += RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_RES_FIELD
								+ "." + RPCoreSTApiGenConstants.LINEARRESOURCE_USE + "()\n";
		
			feach += "chs := make(map[int]chan int)\n";
							
			feach +=
								"for " + p + " := " + generateIndexExpr(outer.getInterval().start) + "; "  // FIXME: general interval expressions
									+ p + lte + "; " + p + " = " + inc + "{\n";
							//+ sEp + "." + s.getParam() + "=" + s.getParam() + "\n"  // FIXME: nested Endpoint type/struct?
			
			// inc Ept thread
			// copy Ept params for new thread
			// make new nested init with new thread
			// spawn
			feach += "tid := " + sEp + ".Thread + 1\n";  // FIXME: sync (factor up as a sync function in the ep) -- e.g., if a thread spawns more subthreads
			feach += sEp + ".Thread = tid\n";
			feach += "tmp := make(map[string]int)\n";
			// FIXME: sync (factor up as a sync function in the ep) -- e.g., if a thread spawns more subthreads
			feach += "for k,v := range " + sEp + ".Params[" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + ".Thread]" + "{\n";
			feach += "tmp[k] = v\n";
			feach += "}\n";
			feach += sEp + ".Params[tid] = tmp\n";
			
			feach += "tmp[\"" + p + "\"] = " + p + "\n";

			feach += "nested := " + this.parent.makeStateChanInstance(initName, sEp, "tid");
			feach += "chs[" + p + "] = make(chan int)\n";
			feach += "go func(ch chan int) {\n";
			feach += "f(nested)\n";
			feach += "ch <- 0\n";
			feach += "}(chs[" + p + "])\n";

			feach += "}\n";
		
			feach +=
								"for " + p + " := " + generateIndexExpr(outer.getInterval().start) + "; "  // FIXME: general interval expressions
									+ p + lte + "; " + p + " = " + inc + "{\n";
			feach += "<- chs[" + p + "]\n";
			feach += "}\n";
			
			feach += //+ "return " + makeCreateSuccStateChan(s, succName) + "\n"
						makeReturnSuccStateChan(outer, succName) + "\n"
					+ "}\n";
		}

		res.put(getStateChannelFilePath(scTypeName), feach);
		
		//RPCoreEState term = (RPCoreEState) MState.getTerminal(init);
		//res.putAll(new RPCoreSTStateChanApiBuilder(this.apigen, this.variant, new EGraph(init, term)).build());
		if (term != null)
		{
			this.todo.add(new EGraph(init, term));
		}

		return res;
	}
}