package scribtest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

// Needs -Dtest.dir=[test root dir] system property -- Eclipse VM arg: -Dtest.dir=${selected_resource_loc} 
@RunWith(value = Parameterized.class)
public class GoodTest extends AllTest
{
	public GoodTest(String example, boolean hasErrors)
	{
		super(example, hasErrors);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data()
	{
		// FIXME: factor out with AllTest and Harness better
		List<Object[]> result = new ArrayList<>();
		Harness harness = new Harness();

		String dir = System.getProperty("test.dir");
		
		for (String file : harness.getExamples(dir))
		{
			result.add(new Object[] { file, false });
		}

		return result;
	}
}
