package org.scribble.ext.f17.main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.scribble.ast.Module;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.del.ModuleDel;
import org.scribble.ext.f17.ast.global.F17GProtocolDeclTranslator;
import org.scribble.ext.f17.ast.global.F17GType;
import org.scribble.ext.f17.ast.local.F17LType;
import org.scribble.ext.f17.ast.local.F17Projector;
import org.scribble.ext.f17.model.endpoint.F17EGraphBuilder;
import org.scribble.ext.f17.model.global.F17ProgressErrors;
import org.scribble.ext.f17.model.global.F17SModel;
import org.scribble.ext.f17.model.global.F17SModelBuilder;
import org.scribble.ext.f17.model.global.F17SafetyErrors;
import org.scribble.main.Job;
import org.scribble.main.MainContext;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribParserException;
import org.scribble.visit.context.RecRemover;

// f17-specific CL frontend
//
// Args: either
//   -inline "..inline module def.." [proto-name]
//   main-mod-path [proto-name]
public class F17Main
{
	public static void main(String[] args) throws ScribbleException, ScribParserException
	{
		//F17GType g = null;

		String inline = null;
		Path mainpath = null;
		String simpname;
		try
		{
			if (args[0].equals("-inline"))
			{
				inline = args[1];
				simpname = (args.length < 3) ? "Proto" : args[2];  // Looks for protocol named "Proto" as default if unspecified
			}
			else
			{
				mainpath = Paths.get(args[0]);
				simpname = (args.length < 2) ? "Proto" : args[1];
			}
			MainContext mc = F17Main.newMainContext(inline, mainpath);
			parseAndCheckWF(mc.newJob(), new GProtocolName(simpname));
		}
		catch (ScribParserException | ScribbleException e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}
		//System.out.println("Translated:\n" + "    " + g);
	}

	// For alternative f17-specific Main
	// Duplicated from CommandLine for convenience
	// Pre: one of inline/mainpath is null
	private static MainContext newMainContext(String inline, Path mainpath) throws ScribParserException, ScribbleException
	{
		boolean debug = false;
		boolean useOldWF = false;
		boolean noLiveness = false;
		boolean minEfsm = false;
		boolean fair = false;
		boolean noLocalChoiceSubjectCheck = false;
		boolean noAcceptCorrelationCheck = false;
		boolean noValidation = false;

		boolean f17 = true;

		/*List<Path> impaths = this.args.containsKey(ArgFlag.PATH)
				? CommandLine.parseImportPaths(this.args.get(ArgFlag.PATH)[0])
				: Collections.emptyList();*/
		List<Path> impaths = Collections.emptyList();  // FIXME: get from Main args
		ResourceLocator locator = new DirectoryResourceLocator(impaths);
		return (inline == null)
				? new MainContext(debug, locator, mainpath, useOldWF, noLiveness, minEfsm, fair,
							noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, f17)
				: new MainContext(debug, locator, inline, useOldWF, noLiveness, minEfsm, fair,
							noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, f17);
	}

	// Used from above and from cli.CommandLine
	public static F17GType parseAndCheckWF(Job job, GProtocolName simpname) throws ScribbleException, ScribParserException
	{
		job.runContextBuildingPasses();
		job.runVisitorPassOnParsedModules(RecRemover.class);  // FIXME: Integrate into main passes?  Do before unfolding?
		
		Module main = job.getContext().getMainModule();
		if (!main.hasProtocolDecl(simpname))
		{
			throw new ScribbleException("Global protocol not found: " + simpname);
		}
		GProtocolDecl gpd = (GProtocolDecl) main.getProtocolDecl(simpname);

		F17GType gt = new F17GProtocolDeclTranslator().translate(job, ((ModuleDel) main.del()).getModuleContext(), gpd);
		
		gt.checkRoleEnabling(new HashSet<>(gpd.header.roledecls.getRoles()));

		//job.debugPrintln
		System.out.println
			("[f17] Translated:\n  " + gt);

		Map<Role, F17LType> P0 = new HashMap<>();
		F17Projector p = new F17Projector();
		for (Role r : gpd.header.roledecls.getRoles())
		{
			F17LType lt = p.project(gt, r, Collections.emptySet());
			P0.put(r, lt);

			//job.debugPrintln
			System.out.println
				("[f17] Projected onto " + r + ":\n  " + lt);
		}

		F17EGraphBuilder builder = new F17EGraphBuilder();
		Map<Role, EState> E0 = new HashMap<>();
		for (Role r : P0.keySet())
		{
			EGraph g = builder.build(P0.get(r));
			E0.put(r, g.init);

			System.out.println("[f17] Built endpoint graph for " + r + ":\n" + g.toDot());
		}
		
		validate(gpd.isExplicitModifier(), E0);

		if (!job.fair)
		{
			Map<Role, EState> U0 = new HashMap<>();
			for (Role r : E0.keySet())
			{
				EState u = E0.get(r).unfairTransform();
				U0.put(r, u);

				System.out.println("[f17] Unfair transform for " + r + ":\n" + u.toDot());
			}
			
			validate(gpd.isExplicitModifier(), U0, true);
		}
		
		/*// Needed for API gen (base projections and EFSMs) -- no: JobContext getters build on demand
		job.runUnfoldingPass();
		job.runWellFormednessPasses();*/
		
		return gt;
	}

	private static void validate(boolean isExplicit, Map<Role, EState> E0, boolean... unfair) throws F17Exception
	{
		F17SModel m = new F17SModelBuilder().build(E0, isExplicit);

		System.out.println("[f17] Built model:\n" + m.toDot());
		
		if (unfair.length == 0)
		{
			F17SafetyErrors serrs = m.getSafetyErrors();
			if (serrs.isSafe())
			{
				System.out.println("[f17] Protocol safe.");
			}
			else
			{
				throw new F17Exception("[f17] Protocol not safe.\n" + serrs);
			}
		}
		
		F17ProgressErrors perrs = m.getProgressErrors();
		if (perrs.satisfiesProgress())
		{
			System.out.println("[f17] " + ((unfair.length == 0) ? "Fair protocol" : "Protocol") + " satisfies progress.");
		}
		else
		{
			throw new F17Exception("[f17] " + ((unfair.length == 0) ? "Fair protocol" : "Protocol") + " violates progress.\n" + perrs);
		}
	}
}
