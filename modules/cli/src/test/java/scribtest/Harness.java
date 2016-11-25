package scribtest;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Harness
{
	//private static final SuffixFileFilter SCRIBBLE_EXT = new SuffixFileFilter(".scr");
	private static final String SCRIBBLE_EXT = ".scr";

	protected static final String DIR_IGNORE_FILE = "IGNORE";
	protected static final String TEST_DIR_FLAG = "test.dir";
	
	/**
	 * Returns the java builder with the default settings to use JArmus.
	 * 
	 * @param className
	 * @return
	 */
	// Currently unused
	@Deprecated
	public static JavaProcessBuilder java(String className)
	{
		JavaProcessBuilder java = new JavaProcessBuilder(className);
		// java.appendClasspath("lib/antlr-3.5.2-complete.jar");
		// java.appendClasspath("bin/");
		java.appendClasspath("target/classes/");
		java.appendClasspath("c:/Users/Raymond/.m2/repository/org/antlr/antlr-runtime/3.2/antlr-runtime-3.2.jar/");
		java.appendClasspath("../core/target/classes/");
		java.appendClasspath("../parser/target/classes/");
		/*java.appendClasspath("../validation/target/classes/");
		java.appendClasspath("../projection/target/classes/");
		java.appendClasspath("../trace/target/classes/");*/
		/*java.appendClasspath("C:/Users/Raymond/.m2/repository/org/codehaus/jackson/jackson-mapper-asl/1.9.9/jackson-mapper-asl-1.9.9.jar/");
		java.appendClasspath("C:/Users/Raymond/.m2/repository/org/codehaus/jackson/jackson-core-asl/1.9.9/jackson-core-asl-1.9.9.jar/");*/
		// java.appendClasspath("org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr/");
		return java;
	}

	/**
	 * Returns all Scribble files in a given directory.
	 * 
	 * @param dir
	 * @return
	 */
	private static Collection<String> getScribbleFilePaths(String dir)
	{
		/* // Broken: somehow interfering with JUnit results (e.g., bad.efsm.grecursion.unfair.Test01)
		 NameFileFilter ignore = new NameFileFilter("IGNORE");
		TreeSet<String> result = new TreeSet<>();
		FileUtils.listFiles(new File(directory), SCRIBBLE_EXT, new IgnoredDirectoryWhenFilter(ignore)).stream().forEach((file) -> result.add(file.getPath()));
		*/
		return getAllScrFilesUnderDirectory(dir).stream()
				.map((f) -> f.getAbsolutePath().toString())
				.collect(Collectors.toList());	
	}
	
	private static List<File> getAllScrFilesUnderDirectory(String path)
	{
		List<File> res = new LinkedList<>();
		for (File f : new File(path).listFiles())
		{
			if (f.isFile())
			{
				if (f.getName().equals(DIR_IGNORE_FILE))
				{
					return Collections.emptyList();
				}
				else if (f.getName().endsWith(SCRIBBLE_EXT))
				{
					res.add(f);
				}
			}
			else if (f.isDirectory())
			{
				res.addAll(getAllScrFilesUnderDirectory(f.getAbsolutePath()));
			}
		}
		return res;
	}

	public static Collection<String> findTests(String path)
	{
		return getScribbleFilePaths(path);
	}

	// dir should be full path
	public static Collection<Object[]> makeTests(boolean isBadTest, String dir)
	{
		return findTests(dir).stream()
				.map((e) -> new Object[] { e, isBadTest })
				.collect(Collectors.toList());
	}

	// root is relative path from cli/src/test/resources
	// This is for "Good/BarTest (test.dir)", i.e., running single test from inside Eclipse
	// root is used as a default if the test.dir property is not present
	public static Collection<Object[]> checkTestDirProperty(boolean isBadTest, String root)
	{
		String dir = System.getProperty(TEST_DIR_FLAG);
		if (dir == null)
		{
			dir = ClassLoader.getSystemResource(root).getFile();
		}
		return makeTests(isBadTest, dir);
	}
}
