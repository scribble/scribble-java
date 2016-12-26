package org.scribble.f17.main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.scribble.f17.ast.ScribProtocolTranslator;
import org.scribble.f17.ast.global.F17GType;
import org.scribble.main.MainContext;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.util.ScribParserException;
// import ast.binary.Type;

// Args: either
//   -inline "..inline module def.." [proto-name]
//   main-mod-path [proto-name]
public class F17Main
{
	public static void main(String[] args) throws ScribbleException, ScribParserException
	{
		F17GType g = null;

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
			ScribProtocolTranslator spt = new ScribProtocolTranslator();
			g = spt.parseAndCheck(F17Main.newMainContext(inline, mainpath), new GProtocolName(simpname));  // merge is for projection of "delegation payload types"
		}
		catch (ScribParserException | ScribbleException e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Translated:\n" + "    " + g);
		
		/*GlobalType gs = ast.global.ops.Sanitizer.apply(g);
		System.out.println("\nSanitized:\n" + "    " + gs);
		
		Map<Role, LocalType> projs = ast.global.ops.Projector.apply(gs, merge);
		for (Entry<Role, LocalType> rl: projs.entrySet())
		{
			LocalType l = rl.getValue();
			Role r = rl.getKey();
			System.out.println("\nLocal projection for " + r + ":\n    " + l);
//			Map<Role, Type> p = ast.local.ops.Projector.apply(l, ast.binary.ops.Merge::full);
//			for (Role r: l.roles())
//			{
//				Type b = p.get(r);
//				System.out.println("Binary type towards " + r + ":\n    " + b);
//				ast.linear.Type bl = ast.binary.ops.LinearEncoder.apply(b);
//				System.out.println("    Linear encoding:\n        " + bl);
//				String scalaProt = ast.linear.ops.ScalaProtocolExtractor.apply(bl);
//				System.out.println("    Scala protocol classes:\n" + scalaProt);
//			}
			String scalaMPProt = ast.local.ops.ScalaEncoder.apply(l, "test.proto." + r);
			System.out.println("    Scala protocol classes for local type:\n" +
					"-----------------------------------------------------\n" +
					scalaMPProt +
					"-----------------------------------------------------\n");
		}
		*/
	}

	// Duplicated from CommandLine for convenience
	// Pre: one of inline/mainpath is null
	protected static MainContext newMainContext(String inline, Path mainpath) throws ScribParserException, ScribbleException
	{
		boolean debug = false;
		boolean useOldWF = false;
		boolean noLiveness = false;
		boolean minEfsm = false;
		boolean fair = false;
		boolean noLocalChoiceSubjectCheck = false;
		boolean noAcceptCorrelationCheck = true;
		boolean noValidation = true;  // FIXME: deprecate -- redundant due to hardcoded Job.checkLinearMPScalaWellFormedness
		boolean noModuleNameCheck = true;  // For webapp to bypass MainContext.checkMainModuleName

		/*List<Path> impaths = this.args.containsKey(ArgFlag.PATH)
				? CommandLine.parseImportPaths(this.args.get(ArgFlag.PATH)[0])
				: Collections.emptyList();*/
		List<Path> impaths = Collections.emptyList();  // FIXME: get from Main args
		ResourceLocator locator = new DirectoryResourceLocator(impaths);
		return (inline == null)
				? new MainContext(debug, locator, mainpath, useOldWF, noLiveness, minEfsm, fair,
							noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, noModuleNameCheck)
				: new MainContext(debug, locator, inline, useOldWF, noLiveness, minEfsm, fair,
							noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, noModuleNameCheck);
	}
}
