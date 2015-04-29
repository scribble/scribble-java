package org.scribble2.cli;

import java.io.IOException;
import java.util.List;

import org.scribble.resources.DirectoryResourceLocator;
import org.scribble.resources.Resource;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.util.ScribbleException;

public class CliJob
{
	private static final AntlrModuleParser PARSER = new AntlrModuleParser();
	public final AntlrModuleParser parser = CliJob.PARSER;

	public final CliJobContext jcontext;  // Mutable (Visitor passes replace modules)

	private DirectoryResourceLocator _locator;
	
	public CliJob(List<String> importPath, String mainpath) throws IOException, ScribbleException
	{
		//initLoader(CommandLine.args.get(Arg.PATH));
		initLoader(importPath);

		this.jcontext = new CliJobContext(this, importPath, mainpath);
	}

	//private void initLoader(String paths)
	private void initLoader(List<String> paths)
	{
		this._locator = new DirectoryResourceLocator(paths);
		//_loader = new ProtocolModuleLoader(PARSER, _locator, LOGGER);
	}

	//protected Resource getResource(String moduleName) {
	public Resource getResource(String filename) {
		//String relativePath = moduleName.replace('.', java.io.File.separatorChar) + ".scr";
		String relativePath = filename;  // RAY
		return (this._locator.getResource(relativePath));
	}
}
