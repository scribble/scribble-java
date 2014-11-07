package org.scribble2.cli;

import java.io.IOException;
import java.util.List;

import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.util.ScribbleException;

public class CliJob
{
	private static final AntlrModuleParser PARSER = new AntlrModuleParser();
	public final AntlrModuleParser parser = CliJob.PARSER;
	
	public final CliJobContext jcontext;  // Mutable (Visitor passes replace modules)
	
	public CliJob(List<String> importPath, String mainpath) throws IOException, ScribbleException
	{
		this.jcontext = new CliJobContext(this, importPath, mainpath);
	}
}
