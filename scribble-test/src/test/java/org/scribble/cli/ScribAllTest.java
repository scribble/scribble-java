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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Runs all tests under good and bad root directories in Scribble.
 * 
 * N.B. Currently in exclude list of root pom surefire config.
 */
//@RunWith(value = Parameterized.class)
@RunWith(Parameterized.class)
public class ScribAllTest extends ScribTest
{
	public ScribAllTest(String example, boolean isBadTest)
	{
		super(example, isBadTest);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data()
	{
		// Test all tests under good and bad root directories
		String dir_good = ClassLoader.getSystemResource(ScribGoodTest.GOOD_DIR).getFile();
		String dir_bad = ClassLoader.getSystemResource(ScribBadTest.BAD_DIR).getFile();
		List<Object[]> result = new LinkedList<>();
		result.addAll(Harness.makeTests(ScribTest.GOOD_TEST, dir_good));
		result.addAll(Harness.makeTests(ScribTest.BAD_TEST, dir_bad));
		return result;
	}
}
