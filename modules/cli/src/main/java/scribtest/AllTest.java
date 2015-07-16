package scribtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import scribtest.ExecUtil.ProcessSummary;

/**
 * Runs good and bad tests in Scribble.
 * 
 */
@RunWith(value = Parameterized.class)
public class AllTest {
	private static final int TIMEOUT = 1000; // Milliseconds
	private String example;
	private boolean hasErrors;

	public AllTest(String example, boolean hasErrors) {
		this.example = example;
		this.hasErrors = hasErrors;
	}

	@Parameters()//name = "{0}")
	public static Collection<Object[]> data() {
		List<Object[]> result = new ArrayList<>();
		Harness harness = new Harness();
		for (String file : harness.getGoodExamples()) {
			result.add(new Object[] { file, false });
		}
		/*for (String file : harness.getBadExamples()) {
			result.add(new Object[] { file, true });
		}*/
		return result;
	}

	@Test
	public void tests() throws IOException, InterruptedException, ExecutionException {
		//JavaProcessBuilder java = Harness.java("scribble2.main.Main");
		JavaProcessBuilder java = Harness.java("org.scribble.cli.CommandLine");
		java.appendProgramArgument(example);
		ProcessSummary result = ExecUtil.execUntil(TIMEOUT, java.build());
		String output = result.stderr + "\n" + result.stdout;
		String message = java.toString() + "\n";
		message += String.format("%s found stdout+stderr (exit value=%d):\n",
				hasErrors ? "No errors" : "Errors", result.exitValue);
		message += output;
		
		if (hasErrors) {
			assertTrue(message, output.contains("ScribbleException: "));
		} else {
			assertEquals(message, "\n", output);  // FIXME: \n is added into output above
		}
	}

}
