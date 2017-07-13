/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.cli;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Test;
import org.scribble.main.ScribbleException;

/*
 * Packaging following pattern of putting tests into same package but different directory as classes being tested:
 * (in this case, testing org.scribble.cli.CommandLine -- but this essentially tests most of core/parser)
 */
public abstract class ScribTest
{
	protected static final boolean GOOD_TEST = false;
	protected static final boolean BAD_TEST = !GOOD_TEST;

	protected final String example;
	protected final boolean isBadTest;

	// relative to scribble-test/src/test/resources (or target/test-classes/)
	protected static final String TEST_ROOT_DIR = ".";  // FIXME: make relative to scribble-java root (for subclasses in extension modules)

	public ScribTest(String example, boolean isBadTest)
	{
		this.example = example;
		this.isBadTest = isBadTest;
	}
	
	protected String getTestRootDir()
	{
		return ScribTest.TEST_ROOT_DIR;
	}
	
	protected void runTest(String dir) throws CommandLineException, ScribbleException
	{
		new CommandLine(this.example, CLArgParser.JUNIT_FLAG, CLArgParser.IMPORT_PATH_FLAG, dir).run();
					// Added JUNIT flag -- but for some reason only bad DoArgList01.scr was breaking without it...
	}

	@Test
	public void tests() throws IOException, InterruptedException, ExecutionException
	{
		try
		{
			// TODO: For now just locate classpath for resources -- later maybe directly execute job
			/*URL url = ClassLoader.getSystemResource(AllTest.GOOD_ROOT);  // Assume good/bad have same parent
			String dir = url.getFile().substring(0, url.getFile().length() - ("/" + AllTest.GOOD_ROOT).length());*/
			String dir = ClassLoader.getSystemResource(getTestRootDir()).getFile();

			if (File.separator.equals("\\")) // HACK: Windows
			{
				dir = dir.substring(1).replace("/", "\\");
			}
			
			// FIXME: read runtime arguments from a config file, e.g. -oldwf, -fair, etc
			// Also need a way to specify expected tool output (e.g. projections/EFSMs for good, errors for bad)
			//new CommandLine(this.example, CLArgParser.JUNIT_FLAG, CLArgParser.IMPORT_PATH_FLAG, dir).run();
					// Added JUNIT flag -- but for some reason only bad DoArgList01.scr was breaking without it...
			runTest(dir);
			Assert.assertFalse("Expecting exception", this.isBadTest);
		}
		catch (ScribbleException e)
		{
			Assert.assertTrue("Unexpected exception '" + e.getMessage() + "'", this.isBadTest);
		}
		catch (CommandLineException e)
		{
			throw new RuntimeException(e);
		}
	}
}
