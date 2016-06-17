package scribtest;

import java.io.File;
import java.util.Collection;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public class Harness
{
	private static final SuffixFileFilter SCRIBBLE_EXT = new SuffixFileFilter(".scr");

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
	 * Returns all scribble files in a given directory.
	 * 
	 * @param directory
	 * @return
	 */
	private static Collection<String> getScribbleFilePaths(String directory)
	{
		NameFileFilter ignore = new NameFileFilter("IGNORE");
		TreeSet<String> result = new TreeSet<>();
		FileUtils.listFiles(new File(directory), SCRIBBLE_EXT, new IgnoredDirectoryWhenFilter(ignore))
				.stream().forEach((file) -> result.add(file.getPath()));
		return result;
	}

	public Collection<String> findTests(String path)
	{
		return getScribbleFilePaths(path);
	}
}
