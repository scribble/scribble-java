package scribtest;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

// Needs -Ddir=[test root dir] system property -- Eclipse VM arg: -Ddir=${selected_resource_loc} 
@RunWith(value = Parameterized.class)
public class BadTest extends AllTest
{
	public BadTest(String example, boolean hasErrors)
	{
		super(example, hasErrors);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data()
	{
		// FIXME: factor out with AllTest and Harness better
		List<Object[]> result = new ArrayList<>();
		Harness harness = new Harness();

		URL url=ClassLoader.getSystemResource("bad");
		String dir = url.getFile();
		
		for (String file : harness.getExamples(dir))
		{
			result.add(new Object[] { file, true });
		}

		return result;
	}
}
