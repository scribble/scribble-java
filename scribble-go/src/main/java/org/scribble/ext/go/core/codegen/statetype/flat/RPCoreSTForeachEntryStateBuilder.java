package org.scribble.ext.go.core.codegen.statetype.flat;

import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.codegen.statetype.STStateChanBuilder;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenerator.Mode;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EState;

// Foreach "entry" state has the "base" state name
// Top-level I/O done on "intermediary" state, built via "base" action builders with state name mangling
public class RPCoreSTForeachEntryStateBuilder extends STStateChanBuilder
{
	public RPCoreSTForeachEntryStateBuilder()
	{
	}
	
	@Override
	public String getPreamble(STStateChanApiBuilder scb, EState outer)
	{
		//return ((RPCoreSTStateChanApiBuilder) scb).getStateChanPremable(outer);
		RPCoreSTStateChanApiBuilder rpscb = (RPCoreSTStateChanApiBuilder) scb;

		String feach =
				"package "
						+ rpscb.parent.namegen.getEndpointKindPackageName(rpscb.variant)
						+ "\n\n";
	
		// TODO: factor out
		if (rpscb.parent.mode == Mode.IntPair)
		{
			feach += "import \"" + RPCoreSTApiGenConstants.INT_RUNTIME_SESSION_PACKAGE
					+ "\"\n";
		}
		else if (rpscb.parent.job.parForeach)
		{
			feach += "import \""
					+ RPCoreSTApiGenConstants.INTPAIR_RUNTIME_SESSION_PACKAGE + "\"\n";
		}

		return feach;
	}
	
	// Pre: outer.hasNested
	@Override
	public String build(STStateChanApiBuilder scb, EState outer)
	{
		RPCoreEState s = (RPCoreEState) outer;
		RPCoreSTStateChanApiBuilder rpscb = (RPCoreSTStateChanApiBuilder) scb;

		String scTypeName = rpscb.getStateChanBaseName(s.id);  // rpscb.getStateChanName returns "_" suffix
		String epkindTypeName = rpscb.parent.namegen
				.getEndpointKindTypeName(rpscb.variant);
	
		//Map<String, String> res = new HashMap<>();
		String feach = getPreamble(rpscb, outer);
		
		// State channel type
		feach += "\n"
				+ "type " + scTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " error\n";

		if (rpscb.parent.job.parForeach)
		{
			feach += RPCoreSTApiGenConstants.SCHAN_RES_FIELD + " *"
					+ RPCoreSTApiGenConstants.LINEARRESOURCE_TYPE + "\n";
		}
		else
		{
			feach += "id uint64\n";
		}

		feach += RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + " *" + epkindTypeName
				+ "\n";
		
		if (rpscb.parent.job.parForeach)
		{
			feach += "Thread int\n";
		}

		feach += "}\n";
		
		// Foreach method -- cf. RPCoreSTSessionApiBuilder, top-level Run
		String sEp = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
				+ RPCoreSTApiGenConstants.SCHAN_EPT_FIELD;
		RPCoreEState init = s.getNested();
		RPCoreEState term = (RPCoreEState) MState.getTerminal(init);
		String initName = rpscb.getStateChanBaseName(init.id); //"Init_" + init.id;
		String termName = (term == null) ? "End" : rpscb.getStateChanBaseName(term.id);
		String succName = s.isTerminal() ? "End" : rpscb.getStateChanName(s);   
				// succ of "entry" state (i.e., return of Foreach) is the intermed state
				// TODO: factor out ("End"?)

		RPIndexVar p = s.getParam();

		// TODO: factor out with send, receive, etc.
		String lte;
		String inc;
		switch (rpscb.parent.mode)
		{
			case Int:  
			{
				lte = " <= " + rpscb.generateIndexExpr(s.getInterval().end);  
				inc = p + "+1";
				break;
			}
			case IntPair:  
			{
				lte = ".Lte(" + rpscb.generateIndexExpr(s.getInterval().end) + ")";
				inc = p + ".Inc(" + rpscb.generateIndexExpr(s.getInterval().end) + ")";
				break;
			}
			default:
				throw new RuntimeException(
						"Shouldn't get in here: " + rpscb.parent.mode);
		}

    String initState = sEp + "._" + initName;
		feach += "\n"
				+ "func (" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + " *"
					+ scTypeName + ") Foreach(f func(*" + initName + ") " + termName + ") *"
					+ succName + " {\n";
						
		// Duplicated from buildAction
		feach += "if " + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
						+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " != nil {\n" 
							+ "panic(" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
						+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + ")\n" + "}\n";

		if (rpscb.parent.job.parForeach)
		{
			feach += RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
					+ RPCoreSTApiGenConstants.SCHAN_RES_FIELD + "."
					+ RPCoreSTApiGenConstants.LINEARRESOURCE_USE + "()\n";
		}
		else
		{
			feach += "if " + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + "id != "  
					// Not using atomic.LoadUint64 on id for now
						+ sEp + "." + "lin"
						+ " {\n"
					+ "panic(\"Linear resource already used\")\n" // + reflect.TypeOf(" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "))\n"
					+ "}\n";
		}
	
		feach += "for " + p + " := " + rpscb.generateIndexExpr(s.getInterval().start) + "; "  // FIXME: general interval expressions
				+ p + lte + "; " + p + " = " + inc + "{\n";
		
		if (rpscb.parent.job.parForeach)
		{
			feach += sEp + ".Params[" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER
					+ ".Thread][\"" + p + "\"] = " + p + " \n";
		}
		else
		{
			feach += sEp + ".Params[\"" + p + "\"] = " + p + "\n";  // FIXME: nested Endpoint type/struct?
		}

		if (rpscb.parent.job.parForeach)
		{
			feach += "nested := " + rpscb.parent.makeStateChanInstance(initName, sEp,
					RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + ".Thread");
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
				  rpscb.makeReturnSuccStateChan(succName) + "\n"
				+ "}\n";
		
		// Parallel method
		if (rpscb.parent.job.parForeach)
		{
			feach += "\n"
					+ "func (" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + " *"
						+ scTypeName + ") Parallel(f func(*" + initName + ") " + termName
						+ ") *" + succName + " {\n";
							
			// Duplicated from buildAction
			feach += "if " + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
					+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " != nil {\n" + "panic("
						+ RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
					+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + ")\n" + "}\n";

			feach += RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
					+ RPCoreSTApiGenConstants.SCHAN_RES_FIELD + "."
					+ RPCoreSTApiGenConstants.LINEARRESOURCE_USE + "()\n";
		
			feach += "chs := make(map[int]chan int)\n";
							
			feach += "for " + p + " := " + rpscb.generateIndexExpr(s.getInterval().start) + "; "  // FIXME: general interval expressions
					+ p + lte + "; " + p + " = " + inc + "{\n";
			
			// inc Ept thread
			// copy Ept params for new thread
			// make new nested init with new thread
			// spawn
			// FIXME: race condition on map between goroutines and parent
			feach += "tid := " + sEp + ".Thread + 1\n";  // FIXME: sync (factor up as a sync function in the ep) -- e.g., if a thread spawns more subthreads
			feach += sEp + ".Thread = tid\n";
			feach += "tmp := make(map[string]int)\n";
			// FIXME: sync (factor up as a sync function in the ep) -- e.g., if a thread spawns more subthreads
			feach += "for k,v := range " + sEp + ".Params["
					+ RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + ".Thread]" + "{\n";
			feach += "tmp[k] = v\n";
			feach += "}\n";
			feach += sEp + ".Params[tid] = tmp\n";
			
			feach += "tmp[\"" + p + "\"] = " + p + "\n";

			feach += "nested := "
					+ rpscb.parent.makeStateChanInstance(initName, sEp, "tid");
			feach += "chs[" + p + "] = make(chan int)\n";
			feach += "go func(ch chan int) {\n";
			feach += "f(nested)\n";
			feach += "ch <- 0\n";
			feach += "}(chs[" + p + "])\n";

			feach += "}\n";
		
			feach += "for " + p + " := " + rpscb.generateIndexExpr(s.getInterval().start) + "; "  // FIXME: general interval expressions
					+ p + lte + "; " + p + " = " + inc + "{\n";
			feach += "<- chs[" + p + "]\n";
			feach += "}\n";
			
			feach += //+ "return " + makeCreateSuccStateChan(s, succName) + "\n"
						rpscb.makeReturnSuccStateChan(succName) + "\n"
					+ "}\n";
		}

		//res.put(rpscb.getStateChannelFilePath(scTypeName), feach);
		
		/*if (term != null)
		{
			this.todo.add(new EGraph(init, term));
		}*/

		return feach;
	}
}