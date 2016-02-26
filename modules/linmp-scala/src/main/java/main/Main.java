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

import ast.global.GlobalTypeTranslator;

public class Main
{
	public static void main(String[] args) throws ScribbleException
	{
		String mod = args[0];
		String proto = "Proto";
		Module main = parseMainScribModule(mod);
		
		ProtocolDecl<Global> pd = main.getProtocolDecl(new GProtocolName(proto));
		GProtocolDef inlined = ((GProtocolDefDel) pd.def.del()).getInlinedProtocolDef();
		
		System.out.println("AA: " + new GlobalTypeTranslator().translate(inlined));
	}

	private static Module parseMainScribModule(String mod) throws ScribbleException
	{
		AntlrParser antlrParser = new AntlrParser();
		ScribParser scribParser = new ScribParser();
		//ResourceLocator locator = new DirectoryResourceLocator(Collections.emptyList()); 
		//this.loader = new ScribModuleLoader(this.locator, this.antlrParser, this.scribParser);
		Resource res = DirectoryResourceLocator.getResourceByFullPath(Paths.get(mod));
		Module main = (Module) scribParser.parse(antlrParser.parseAntlrTree(res));
		Map<ModuleName, Module> parsed = new HashMap<>();
		parsed.put(main.getFullModuleName(), main);
		Job job = new Job(false, parsed, main.getFullModuleName());
		job.checkWellFormedness();
		return job.getContext().getMainModule();
	}
}
