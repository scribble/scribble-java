package ast;

import java.nio.file.Path;
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

import ast.global.GlobalType;
import ast.global.GlobalTypeTranslator;

public class ScribProtocolTranslator
{
	public GlobalType parse(Path mainmod, String simplename) throws ScribbleException
	{
		Module main = parseMainScribModule(mainmod);
		ProtocolDecl<Global> pd = main.getProtocolDecl(new GProtocolName(simplename));
		GProtocolDef inlined = ((GProtocolDefDel) pd.def.del()).getInlinedProtocolDef();
		return new GlobalTypeTranslator().translate(inlined);
	}

	// TODO: doesn't support Scribble module imports yet
	private Module parseMainScribModule(Path mainmod) throws ScribbleException
	{
		AntlrParser antlrParser = new AntlrParser();
		ScribParser scribParser = new ScribParser();
		//ResourceLocator locator = new DirectoryResourceLocator(Collections.emptyList()); 
		//this.loader = new ScribModuleLoader(this.locator, this.antlrParser, this.scribParser);
		Resource res = DirectoryResourceLocator.getResourceByFullPath(mainmod);
		Module main = (Module) scribParser.parse(antlrParser.parseAntlrTree(res));
		Map<ModuleName, Module> parsed = new HashMap<>();
		parsed.put(main.getFullModuleName(), main);
		Job job = new Job(false, parsed, main.getFullModuleName());
		job.checkLinearMPScalaWellFormedness();  // FIXME TODO
		return job.getContext().getMainModule();
	}
}
