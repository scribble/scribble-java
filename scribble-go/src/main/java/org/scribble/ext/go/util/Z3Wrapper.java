package org.scribble.ext.go.util;

import java.io.File;
import java.util.Arrays;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ext.go.main.GoJob;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.util.ScribUtil;

// "Native" Z3 -- not Z3 Java API
public class Z3Wrapper
{

	// Based on CommandLine::runDot, JobContext::runAut, etc
	public static boolean checkSat(GoJob job, ProtocolDecl<Global> gpd, String smt2) //throws ScribbleException
	{
		String tmpName = gpd.header.name + ".smt2.tmp";
		File tmp = new File(tmpName);
		if (tmp.exists())  // Factor out with CommandLine.runDot (file exists check)
		{
			throw new RuntimeException("Cannot overwrite: " + tmpName);
		}
		smt2 = smt2 + "\n(check-sat)\n(exit)";
		try
		{
			ScribUtil.writeToFile(tmpName, smt2);
			String[] res = ScribUtil.runProcess("z3", tmpName);
			String trim = res[0].trim();
			if (trim.equals("sat"))  // FIXME: factor out
			{
				return true;
			}
			else if (trim.equals("unsat"))
			{
				return false;
			}
			else
			{
				throw new RuntimeException("[assrt] Z3 error: " + Arrays.toString(res));
			}
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			tmp.delete();
		}
	}
}
