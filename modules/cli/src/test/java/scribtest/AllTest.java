package scribtest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.scribble.cli.CommandLine;
import org.scribble.main.RuntimeScribbleException;

/**
 * Runs good and bad tests in Scribble.
 * 
 */
@RunWith(value = Parameterized.class)
public class AllTest {
	private String example;
	private boolean hasErrors;

	public AllTest(String example, boolean hasErrors) {
		this.example = example;
		this.hasErrors = hasErrors;
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		Harness harness = new Harness();
		List<Object[]> result = new ArrayList<>();

		URL url=ClassLoader.getSystemResource("good");
		String dir = url.getFile();
		
		for (String file : harness.getExamples(dir))
		{
			result.add(new Object[] { file, false });
		}

		url=ClassLoader.getSystemResource("bad");
		dir = url.getFile();
		
		for (String file : harness.getExamples(dir))
		{
			result.add(new Object[] { file, true });
		}

		return result;
	}

	@Test
	public void tests() throws IOException, InterruptedException, ExecutionException {
		
		try {
			// TODO: For now just locate classpath for resources - later maybe directly execute job
			URL url=ClassLoader.getSystemResource("good");
			String dir = url.getFile().substring(0, url.getFile().length()-5);

			CommandLine cl=new CommandLine(example, "-ip", dir);
			cl.run();
			assertFalse("Expecting exception", hasErrors);
		} catch (RuntimeScribbleException e) {
			assertTrue("Unexpected exception '"+e.getCause().getMessage()+"'", hasErrors);
		}
	}
}
