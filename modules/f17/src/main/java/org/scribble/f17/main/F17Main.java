package org.scribble.f17.main;

import org.scribble.main.ScribbleException;
import org.scribble.util.ScribParserException;
// import ast.binary.Type;

// Args: either
//   -inline "..inline module def.." [proto-name]
//   main-mod-path [proto-name]
@Deprecated
public class F17Main
{
	public static void main(String[] args) throws ScribbleException, ScribParserException
	{
		/*F17GType g = null;

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
			F17ScribTranslator spt = new F17ScribTranslator();
			g = spt.parseAndCheck(F17Main.newMainContext(inline, mainpath), new GProtocolName(simpname));  // merge is for projection of "delegation payload types"
		}
		catch (ScribParserException | ScribbleException e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}
		//System.out.println("Translated:\n" + "    " + g);
	*/
	}

	/*
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

		boolean f17 = true;

		/*List<Path> impaths = this.args.containsKey(ArgFlag.PATH)
				? CommandLine.parseImportPaths(this.args.get(ArgFlag.PATH)[0])
				: Collections.emptyList();* /
		List<Path> impaths = Collections.emptyList();  // FIXME: get from Main args
		ResourceLocator locator = new DirectoryResourceLocator(impaths);
		return (inline == null)
				? new MainContext(debug, locator, mainpath, useOldWF, noLiveness, minEfsm, fair,
							noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, f17)
				: new MainContext(debug, locator, inline, useOldWF, noLiveness, minEfsm, fair,
							noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, f17);
	}*/
}
