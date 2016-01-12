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
	protected static final String TEST_DIR_FLAG = "test.dir";
	
	public GoodTest(String example, boolean isBadTest)
	{
		super(example, isBadTest);
	}

	// root is used as a default if the test.dir arg is not given
	protected static Collection<Object[]> checkTestDirArg(boolean isBadTest, String root)
	{
		String dir = System.getProperty(TEST_DIR_FLAG);
		if (dir == null)
		{
			dir = ClassLoader.getSystemResource(root).getFile();
		}
		return AllTest.makeTests(isBadTest, dir);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data()
	{
		return checkTestDirArg(AllTest.GOOD_TEST, AllTest.GOOD_ROOT);
	}
}
