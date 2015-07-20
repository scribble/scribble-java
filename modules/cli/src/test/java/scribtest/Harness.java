package scribtest;

import static scribtest.ExecUtil.joinPath;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public class Harness {
	private static final SuffixFileFilter SCRIBBLE_EXT = new SuffixFileFilter(".scr");
	public static final String GOOD_EXAMPLES_DIR = joinPath("test", "good");
	public static final String BAD_EXAMPLES_DIR = joinPath("test", "bad");
	
	/**
	 * Returns the java builder with the default settings to use JArmus.
	 * @param className
	 * @return
	 */
	public static JavaProcessBuilder java(String className) {
		JavaProcessBuilder java = new JavaProcessBuilder(className);
		//java.appendClasspath("lib/antlr-3.5.2-complete.jar");
		//java.appendClasspath("bin/");
		java.appendClasspath("target/classes/");
		java.appendClasspath("c:/Users/Raymond/.m2/repository/org/antlr/antlr-runtime/3.2/antlr-runtime-3.2.jar/");
		java.appendClasspath("../core/target/classes/");
		java.appendClasspath("../parser/target/classes/");
		/*java.appendClasspath("../validation/target/classes/");
		java.appendClasspath("../projection/target/classes/");
		java.appendClasspath("../trace/target/classes/");*/
		/*java.appendClasspath("C:/Users/Raymond/.m2/repository/org/codehaus/jackson/jackson-mapper-asl/1.9.9/jackson-mapper-asl-1.9.9.jar/");
		java.appendClasspath("C:/Users/Raymond/.m2/repository/org/codehaus/jackson/jackson-core-asl/1.9.9/jackson-core-asl-1.9.9.jar/");*/
		//java.appendClasspath("org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr/");

		return java;
	}
	
	/**
	 * Returns all scribble files in a given directory.
	 * @param directory
	 * @return
	 */
	/*
	private static Collection<String> getScribbleFiles(String directory) {
		TreeSet<String> result = new TreeSet<>();
		for (File file :  FileUtils.listFiles(new File(directory), new String[]{"scr"}, true) ) {
			result.add(file.getPath());
		}
	    return result;
	}
	*/
	private static Collection<String> getScribbleFiles(String directory) {
		TreeSet<String> result = new TreeSet<>();
		NameFileFilter ignore = new NameFileFilter("IGNORE");
		for (File file :  FileUtils.listFiles(new File(directory), SCRIBBLE_EXT, new IgnoredDirectoryWhenFilter(ignore)) ) {
			result.add(file.getPath());
		}
	    return result;
	}

	public Collection<String> getExamples(String path) {
		return getScribbleFiles(path);
	}

	public Collection<String> getGoodExamples() {
		return getExamples(GOOD_EXAMPLES_DIR);
	}

	public Collection<String> getBadExamples() {
		return getExamples(BAD_EXAMPLES_DIR);
	}
	
	public Collection<Object[]> getAllExamples() {
		List<Object[]> result = new ArrayList<>();
		Harness harness = new Harness();
		for (String file : harness.getGoodExamples()) {
			result.add(new Object[] { file, false });
		}
		for (String file : harness.getBadExamples()) {
			result.add(new Object[] { file, true });
		}
		return result;
	}
}
