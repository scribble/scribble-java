package scribtest;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
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

	private void foo()
	{
		//FileUtils.listFiles(null, null, null);
		//new SuffixFileFilter("fsfsdfs");
		//FileUtils.listFiles(null, new SuffixFileFilter("fsfsdfs"), null);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data()
	{
		//return GoodTest.checkTestDirArg(AllTest.BAD_TEST, AllTest.BAD_ROOT);

		List<Object[]> tmp = new LinkedList<>();
		tmp.add(new Object[] {
				"C:\\cygwin\\home\\Raymond\\code\\scribble\\github-rhu1\\scribble-java\\modules\\cli\\target\\test-classes\\bad\\efsm\\grecursion\\unfair\\Test01.scr",
				true });
		return tmp;
	}
}
