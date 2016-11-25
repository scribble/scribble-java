package scribtest;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

// For running all tests under the specified directory as bad tests
// Needs -Dtest.dir=[test root dir] system property -- Eclipse VM arg: -Dtest.dir=${selected_resource_loc} 
@RunWith(value = Parameterized.class)
public class BadTest extends AllTest
{
	public BadTest(String example, boolean isBadTest)
	{
		super(example, isBadTest);
	}

	/*@Parameters(name = "{0}")
	public static Collection<Object[]> data()
	{
		//String dir = ClassLoader.getSystemResource(AllTest.BAD_ROOT).getFile();  // No: checkTestDirProperty does this
		String dir = AllTest.BAD_ROOT;
		return Harness.checkTestDirProperty(AllTest.BAD_TEST, dir);
	}*/
}
