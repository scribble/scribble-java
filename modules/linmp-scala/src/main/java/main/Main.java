package main;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.del.global.GProtocolDefDel;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.Resource;
import org.scribble.parser.AntlrParser;
import org.scribble.parser.ScribParser;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.visit.Job;

import ast.global.GlobalTypeParser;

public class Main
{
	public static void main(String[] args) throws ScribbleException
	{
		AntlrParser antlrParser = new AntlrParser();
		ScribParser scribParser = new ScribParser();
		//ResourceLocator locator = new DirectoryResourceLocator(Collections.emptyList()); 
		//this.loader = new ScribModuleLoader(this.locator, this.antlrParser, this.scribParser);
		Resource res = DirectoryResourceLocator.getResourceByFullPath(Paths.get(args[0]));
		Module main = (Module) scribParser.parse(antlrParser.parseAntlrTree(res));
		Map<ModuleName, Module> parsed = new HashMap<>();
		parsed.put(main.getFullModuleName(), main);
		Job job = new Job(false, parsed, main.getFullModuleName());
		job.checkWellFormedness();
		
		ProtocolDecl<Global> pd = job.getContext().getMainModule().getProtocolDecl(new GProtocolName("Proto"));
		GProtocolDef inlined = ((GProtocolDefDel) pd.def.del()).getInlinedProtocolDef();
		
		GlobalTypeParser p = new GlobalTypeParser();
		System.out.println("AA: " + p.parse(inlined));
	}
}
