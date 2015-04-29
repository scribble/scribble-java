package org.scribble2.cli;

import java.io.IOException;
import java.util.List;

import org.scribble.resources.DirectoryResourceLocator;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.util.ScribbleException;

public class CliJob
{
	private static final AntlrModuleParser PARSER = new AntlrModuleParser();

	public final AntlrModuleParser parser = CliJob.PARSER;
	public final DirectoryResourceLocator loader;

	public final CliJobContext jcontext;  // Mutable (Visitor passes replace modules)

	
	// FIXME: path and other command line args should be job parameters stored here
	
	public CliJob(List<String> importPath, String mainpath) throws IOException, ScribbleException
	{
		//initLoader(CommandLine.args.get(Arg.PATH));
		//initLoader(importPath);

		this.loader = new DirectoryResourceLocator(importPath);

		this.jcontext = new CliJobContext(this, importPath, mainpath);
	}

	/*//private void initLoader(String paths)
	private void initLoader(List<String> paths)
	{
		this._locator = new DirectoryResourceLocator(paths);
		//_loader = new ProtocolModuleLoader(PARSER, _locator, LOGGER);
	}*/

	/*//protected Resource getResource(String moduleName) {
	public Resource getResource(String path) {
		//String relativePath = moduleName.replace('.', java.io.File.separatorChar) + ".scr";  // RAY
		return (this._locator.getResource(path));
	}*/
}
