package org.scribble2.parser;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelNode;
import org.scribble2.parser.AntlrConstants.AntlrNodeType;
import org.scribble2.parser.ast.AntlrDataTypeDecl;
import org.scribble2.parser.ast.AntlrImportModule;
import org.scribble2.parser.ast.AntlrMessageSig;
import org.scribble2.parser.ast.AntlrMessageSigDecl;
import org.scribble2.parser.ast.AntlrModule;
import org.scribble2.parser.ast.AntlrModuleDecl;
import org.scribble2.parser.ast.AntlrNonRoleArgList;
import org.scribble2.parser.ast.AntlrNonRoleParamDecl;
import org.scribble2.parser.ast.AntlrNonRoleParamDeclList;
import org.scribble2.parser.ast.AntlrPayloadElemList;
import org.scribble2.parser.ast.AntlrRoleArg;
import org.scribble2.parser.ast.AntlrRoleArgList;
import org.scribble2.parser.ast.AntlrRoleDecl;
import org.scribble2.parser.ast.AntlrRoleDeclList;
import org.scribble2.parser.ast.global.AntlrGChoice;
import org.scribble2.parser.ast.global.AntlrGContinue;
import org.scribble2.parser.ast.global.AntlrGDo;
import org.scribble2.parser.ast.global.AntlrGInteractionSequence;
import org.scribble2.parser.ast.global.AntlrGInterrupt;
import org.scribble2.parser.ast.global.AntlrGInterruptible;
import org.scribble2.parser.ast.global.AntlrGMessageTransfer;
import org.scribble2.parser.ast.global.AntlrGParallel;
import org.scribble2.parser.ast.global.AntlrGProtocolBlock;
import org.scribble2.parser.ast.global.AntlrGProtocolDecl;
import org.scribble2.parser.ast.global.AntlrGProtocolDefinition;
import org.scribble2.parser.ast.global.AntlrGProtocolHeader;
import org.scribble2.parser.ast.global.AntlrGRecursion;
import org.scribble2.parser.util.Util;

/*import scribble2.ast.*;
import scribble2.ast.global.*;
import scribble2.ast.name.*;
import scribble2.main.Job;
import scribble2.parser.ast.*;
import scribble2.parser.ast.global.*;
import scribble2.sesstype.name.*;
import scribble2.util.Util;*/

// Parses ANTLR Trees into Scribble.Ast.ModelNode trees
public class ScribbleParser
{
	//public static final ScribbleParser parser = new ScribbleParser();
	
	public ScribbleParser()
	{

	}

	/*// Does not use import paths
	public Module parseModuleFromResource(Resource res) //throws ScribbleException
	{
		try
		{
			//CharStream input = isFile ? new ANTLRFileStream(path) : new ANTLRInputStream(System.in);
			CharStream input = new ANTLRFileStream(res.getPath());
			Scribble2Lexer lex = new Scribble2Lexer(input);
      /*InputStream is = res.getInputStream();
      byte[] bs=new byte[is.available()];
      is.read(bs);
      is.close();
      String input=new String(bs);
			Scribble2Lexer lex = new Scribble2Lexer(new ANTLRStringStream(input));* /
			Scribble2Parser parser = new Scribble2Parser(new CommonTokenStream(lex));
			CommonTree ct = (CommonTree) parser.module().getTree();
			Module module = (Module) parse(ct);
			/*if (isFile) {
				String filename = new File(path).getName();
				String obtainedName = filename.substring(0, filename.indexOf("."));
				ModuleName expected = module.getFullModuleName();
				if (!obtainedName.equals(expected.getSimpleName().toString()))
				{
					throw new ScribbleException("Incorrect file \"" + obtainedName + "\" for module declaration: " + expected);
				}
			}* /
			
			// FIXME: check loaded module name correct
			
			return module;
		}
		catch (IOException | RecognitionException e)
		{
			//throw new ScribbleException(e);
			throw new RuntimeException(e);
		}
	}

	// Does not use import paths
	public Module parseModuleFromSource(String path) //throws ScribbleException
	{
		try
		{
			//CharStream input = isFile ? new ANTLRFileStream(path) : new ANTLRInputStream(System.in);
			CharStream input = new ANTLRFileStream(path);
			Scribble2Lexer lex = new Scribble2Lexer(input);
			Scribble2Parser parser = new Scribble2Parser(new CommonTokenStream(lex));
			CommonTree ct = (CommonTree) parser.module().getTree();
			Module module = (Module) parse(ct);
			/*if (isFile) {
				String filename = new File(path).getName();
				String obtainedName = filename.substring(0, filename.indexOf("."));
				ModuleName expected = module.getFullModuleName();
				if (!obtainedName.equals(expected.getSimpleName().toString()))
				{
					throw new ScribbleException("Incorrect file \"" + obtainedName + "\" for module declaration: " + expected);
				}
			}* /
			
			//System.out.println("b: " + module);
			
			return module;
		}
		catch (IOException | RecognitionException e)
		{
			//throw new ScribbleException(e);
			throw new RuntimeException(e);
		}
	}

	//public Module importModule(JobEnv job, ModuleName modname) throws ScribbleException
	public Pair<String, Module> importModule(List<String> importPath, String path) //throws ScribbleException
	{
		for (String ip : importPath)
		{
			String tmp = ip + "/" + path;
			return new Pair<>(tmp, parseModuleFromSource(tmp));
		}
		//throw new ScribbleException("Module not found: " + path);
		throw new RuntimeException("Module not found: " + path);
	}*/

	public ModelNode parse(CommonTree ct)
	{
		AntlrNodeType type = Util.getAntlrNodeType(ct);
		switch (type)
		{
			case MODULE: 
				return AntlrModule.parseModule(this, ct);
			case MODULEDECL:
				return AntlrModuleDecl.parseModuleDecl(this, ct);
			case MESSAGESIGNATUREDECL:
				return AntlrMessageSigDecl.parseMessageSigDecl(this, ct);
			case PAYLOADTYPEDECL:
				return AntlrDataTypeDecl.parseDataTypeDecl(this, ct);
			case IMPORTMODULE:
				return AntlrImportModule.parseImportModule(this, ct);
			case GLOBALPROTOCOLDECL:
				return AntlrGProtocolDecl.parseGPrototocolDecl(this, ct);
			case ROLEDECLLIST:
				return AntlrRoleDeclList.parseRoleDeclList(this, ct);
			case ROLEDECL:
				return AntlrRoleDecl.parseRoleDecl(this, ct);
			case PARAMETERDECLLIST:
			//case EMPTY_PARAMETERDECLLST:
				return AntlrNonRoleParamDeclList.parseNonRoleParamDeclList(this, ct);
			case PARAMETERDECL:
				return AntlrNonRoleParamDecl.parseNonRoleParamDecl(this, ct);
			case GLOBALPROTOCOLHEADER:
				return AntlrGProtocolHeader.parseGProtocolHeader(this, ct);
			case GLOBALPROTOCOLDEF:
				return AntlrGProtocolDefinition.parseGProtocolDefinition(this, ct);
			case GLOBALPROTOCOLBLOCK:
				return AntlrGProtocolBlock.parseGProtocolBlock(this, ct);
			case GLOBALINTERACTIONSEQUENCE:
				return AntlrGInteractionSequence.parseGInteractionSequence(this, ct);
			case MESSAGESIGNATURE:
				return AntlrMessageSig.parseMessageSig(this, ct);
			case PAYLOAD:
				return AntlrPayloadElemList.parsePayloadElemList(this, ct);
			/*case PAYLOADELEMENT:
				return AntlrPayloadElement.parsePayloadElement(this, ct);*/
			case GLOBALMESSAGETRANSFER:
				return AntlrGMessageTransfer.parseGMessageTransfer(this, ct);
			case GLOBALCHOICE:
				return AntlrGChoice.parseGChoice(this, ct);
			case GLOBALRECURSION:
				return AntlrGRecursion.parseGRecursion(this, ct);
			case GLOBALCONTINUE:
				return AntlrGContinue.parseGContinue(this, ct);
			case GLOBALPARALLEL:
				return AntlrGParallel.parseGParallel(this, ct);
			case GLOBALINTERRUPTIBLE:
				return AntlrGInterruptible.parseGInterruptible(this, ct);
			case GLOBALINTERRUPT:
				return AntlrGInterrupt.parseGInterrupt(this, ct);
			case GLOBALDO:
				return AntlrGDo.parseGDo(this, ct);
			case ROLEINSTANTIATIONLIST:
				return AntlrRoleArgList.parseRoleArgList(this, ct);
			case ROLEINSTANTIATION:
				return AntlrRoleArg.parseRoleArg(this, ct);
			case ARGUMENTINSTANTIATIONLIST:
				return AntlrNonRoleArgList.parseNonRoleArgList(this, ct);
			/*case ARGUMENTINSTANTIATION:
				return AntlrNonRoleArg.parseNonRoleArg(this, ct);*/

			/*case AMBIGUOUSNAME:  // Should this be here?
				System.out.println("c: " + ct);
				//return AntlrNonRoleArg.parseNonRoleArg(this, ct);
				return AntlrAmbiguousName.toAmbiguousNameNode(ct);*/
			default:
				throw new RuntimeException("Unknown ANTLR node type: " + type);
		}
	}
}
