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
package scribtest;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

// For running all tests under the specified directory as bad tests
// Needs -Dtest.dir=[test root dir] system property -- Eclipse VM arg: -Dtest.dir=${selected_resource_loc} 
//@RunWith(value = Parameterized.class)
@RunWith(Parameterized.class)
public class BadTest extends AllTest
{
	public BadTest(String example, boolean isBadTest)
	{
		super(example, isBadTest);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data()
	{
		return Harness.checkTestDirProperty(AllTest.BAD_TEST, BAD_ROOT);
		/*String dir_good = ClassLoader.getSystemResource(GoodTest.GOOD_ROOT).getFile();
		String dir_bad = ClassLoader.getSystemResource(BadTest.BAD_ROOT).getFile();
		List<Object[]> result = new LinkedList<>();
		//result.add(new Object[] { "C:\\cygwin\\home\\Raymond\\code\\scribble\\github-rhu1\\scribble-java\\modules\\cli\\target\\test-classes\\good\\efsm\\grecursion\\Test03.scr", false });
		result.add(new Object[] { "C:\\cygwin\\home\\Raymond\\code\\scribble\\github-rhu1\\scribble-java\\modules\\cli\\target\\test-classes\\bad\\efsm\\grecursion\\unfair\\Test01.scr", true });
		result.add(new Object[] { "C:\\cygwin\\home\\Raymond\\code\\scribble\\github-rhu1\\scribble-java\\modules\\cli\\target\\test-classes\\bad\\efsm\\grecursion\\unfair\\Test06.scr", true });
		//result.addAll(Harness.makeTests(GOOD_TEST, dir_good));
		//result.addAll(Harness.makeTests(BAD_TEST, dir_bad));
		return result;
		//*/
	}
}
