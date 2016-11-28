package scribtest;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

// For running all tests under the specified directory as good tests
// Needs -Dtest.dir=[test root dir] system property -- Eclipse VM arg: -Dtest.dir=${selected_resource_loc} 
@RunWith(value = Parameterized.class)
public class GoodTest extends AllTest
{
	public GoodTest(String example, boolean isBadTest)
	{
		super(example, isBadTest);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data()
	{
		//String dir = ClassLoader.getSystemResource(AllTest.GOOD_ROOT).getFile();  // No: checkTestDirProperty does this
		String dir = AllTest.GOOD_ROOT;
		return Harness.checkTestDirProperty(AllTest.GOOD_TEST, dir);
	}
}
