package scribtest;

import static org.junit.Assert.fail;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;













//import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.scribble.main.MainContext;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.util.ScribParserException;
import org.scribble.visit.Job;

/**
 * Runs good and bad tests in Scribble.
 * 
 */
@Deprecated  // Not currently used (via AllTest instead)
@RunWith(value = Parameterized.class)
public class TestWellFormedness {
	private String filename;
	private boolean hasErrors;

	public TestWellFormedness(String example, boolean hasErrors) {
		this.filename = example;
		this.hasErrors = hasErrors;
	}

	@Parameters(name = "{0} bad={1}")
	public static Collection<Object[]> data() {
		Harness harness = new Harness();
		List<Object[]> result = new ArrayList<>();

		URL url=ClassLoader.getSystemResource("good");
		String dir = url.getFile();
		
		for (String file : harness.findTests(dir))
		{
			result.add(new Object[] { file, false });
		}

		url=ClassLoader.getSystemResource("bad");
		dir = url.getFile();
		
		for (String file : harness.findTests(dir))
		{
			result.add(new Object[] { file, true });
		}

		return result;
	}

	@Test
	public void tests() throws Exception {
		/*try {
			MainContext mc = newMainContext();
			Job job = new Job(mc.debug, mc.getParsedModules(), mc.main, mc.useOldWF, mc.noLiveness);

			job.checkWellFormedness();
			if (hasErrors) fail("Should throw an error.");
		} catch (ScribbleException| RuntimeException e) {// | RecognitionException e) {
			if (!hasErrors) {
				throw e;
			}
		}*/
		MainContext mc = newMainContext();
		//Job job = new Job(mc.debug, mc.getParsedModules(), mc.main, mc.useOldWF, mc.noLiveness, mc.minEfsm, mc.fair);
		Job job = mc.newJob();
		ScribbleException x = job.testWellFormednessCheck();
		if (!hasErrors && x != null)
		{
			throw x;
		}
		else if (hasErrors && x == null)
		{
			fail("Should throw an error.");
		}
	}

	// Duplicated from CommandLine
	private MainContext newMainContext() throws ScribbleException
	{
		boolean debug = false;
		boolean useOldWF = false;
		boolean noLiveness = false;
		boolean minEfsm = false;
		boolean fair = true;  // FIXME?
		boolean noLocalChoiceSubjectCheck = false;

		Path mainpath = Paths.get(filename);
		List<Path> impaths = new ArrayList<Path>();
		
		URL url=ClassLoader.getSystemResource("good");
		String dir = url.getFile().substring(0, url.getFile().length()-5);
		impaths.add(Paths.get(dir));
		
		ResourceLocator locator = new DirectoryResourceLocator(impaths);
		try
		{
			return new MainContext(debug, locator, mainpath, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck);
		}
		catch (ScribParserException e)
		{
			throw new RuntimeException(e);
		}
	}
}
