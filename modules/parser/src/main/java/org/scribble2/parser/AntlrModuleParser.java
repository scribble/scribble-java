package org.scribble2.parser;

import java.io.IOException;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.parser.antlr.Scribble2Lexer;
import org.scribble.parser.antlr.Scribble2Parser;
import org.scribble2.model.ModelNode;
import org.scribble2.model.Module;
import org.scribble2.parser.AntlrConstants.AntlrNodeType;
import org.scribble2.parser.ast.AntlrArgumentInstantiation;
import org.scribble2.parser.ast.AntlrArgumentInstantiationList;
import org.scribble2.parser.ast.AntlrMessageSignature;
import org.scribble2.parser.ast.AntlrModule;
import org.scribble2.parser.ast.AntlrModuleDecl;
import org.scribble2.parser.ast.AntlrParameterDecl;
import org.scribble2.parser.ast.AntlrParameterDeclList;
import org.scribble2.parser.ast.AntlrPayload;
import org.scribble2.parser.ast.AntlrPayloadElement;
import org.scribble2.parser.ast.AntlrRoleDecl;
import org.scribble2.parser.ast.AntlrRoleDeclList;
import org.scribble2.parser.ast.AntlrRoleInstantiation;
import org.scribble2.parser.ast.AntlrRoleInstantiationList;
import org.scribble2.parser.ast.global.AntlrGlobalChoice;
import org.scribble2.parser.ast.global.AntlrGlobalContinue;
import org.scribble2.parser.ast.global.AntlrGlobalDo;
import org.scribble2.parser.ast.global.AntlrGlobalInteractionSequence;
import org.scribble2.parser.ast.global.AntlrGlobalInterrupt;
import org.scribble2.parser.ast.global.AntlrGlobalInterruptible;
import org.scribble2.parser.ast.global.AntlrGlobalMessageTransfer;
import org.scribble2.parser.ast.global.AntlrGlobalParallel;
import org.scribble2.parser.ast.global.AntlrGlobalProtocolBlock;
import org.scribble2.parser.ast.global.AntlrGlobalProtocolDecl;
import org.scribble2.parser.ast.global.AntlrGlobalProtocolDefinition;
import org.scribble2.parser.ast.global.AntlrGlobalProtocolHeader;
import org.scribble2.parser.ast.global.AntlrGlobalRecursion;
import org.scribble2.parser.util.Pair;
import org.scribble2.parser.util.Util;

/*import scribble2.ast.*;
import scribble2.ast.global.*;
import scribble2.ast.name.*;
import scribble2.main.Job;
import scribble2.parser.ast.*;
import scribble2.parser.ast.global.*;
import scribble2.sesstype.name.*;
import scribble2.util.Util;*/

// Parses a Antlr tree into a scribble.ast.Node tree
public class AntlrModuleParser
{
	public AntlrModuleParser()
	{
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
			}*/
			
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
	}

	public ModelNode parse(CommonTree ct)
	{
		AntlrNodeType type = Util.getAntlrNodeType(ct);
		switch (type)
		{
			case MODULE: 
				return AntlrModule.parseModule(this, ct);
			case MODULEDECL:
				return AntlrModuleDecl.parseModuleDecl(this, ct);
			/*case PAYLOADTYPEDECL:
				return AntlrPayloadTypeDecl.parsePayloadTypeDecl(this, ct);
			case MESSAGESIGNATUREDECL:
				return AntlrMessageSignatureDecl.parseMessageSignatureDecl(this, ct);
			case IMPORTMODULE:
				return AntlrImportModule.parseImportModule(this, ct);*/
			case GLOBALPROTOCOLDECL:
				return AntlrGlobalProtocolDecl.parseGlobalPrototocolDecl(this, ct);
			case ROLEDECLLIST:
				return AntlrRoleDeclList.parseRoleDeclList(this, ct);
			case ROLEDECL:
				return AntlrRoleDecl.parseRoleDecl(this, ct);
			case PARAMETERDECLLIST:
			//case EMPTY_PARAMETERDECLLST:
				return AntlrParameterDeclList.parseParameterDeclList(this, ct);
			case PARAMETERDECL:
				return AntlrParameterDecl.parseParameterDecl(this, ct);
			case GLOBALPROTOCOLHEADER:
				return AntlrGlobalProtocolHeader.parseGlobalProtocolHeader(this, ct);
			case GLOBALPROTOCOLDEF:
				return AntlrGlobalProtocolDefinition.parseGlobalProtocolDefinition(this, ct);
			case GLOBALPROTOCOLBLOCK:
				return AntlrGlobalProtocolBlock.parseGlobalProtocolBlock(this, ct);
			case GLOBALINTERACTIONSEQUENCE:
				return AntlrGlobalInteractionSequence.parseGlobalInteractionSequence(this, ct);
			case MESSAGESIGNATURE:
				return AntlrMessageSignature.parseMessageSignature(this, ct);
			case PAYLOAD:
				return AntlrPayload.parsePayload(this, ct);
			case PAYLOADELEMENT:
				return AntlrPayloadElement.parsePayloadElement(this, ct);
			case GLOBALMESSAGETRANSFER:
				return AntlrGlobalMessageTransfer.parseGlobalMessageTransfer(this, ct);
			case GLOBALCHOICE:
				return AntlrGlobalChoice.parseGlobalChoice(this, ct);
			case GLOBALRECURSION:
				return AntlrGlobalRecursion.parseGlobalRecursion(this, ct);
			case GLOBALCONTINUE:
				return AntlrGlobalContinue.parseGlobalContinue(this, ct);
			case GLOBALPARALLEL:
				return AntlrGlobalParallel.parseGlobalParallel(this, ct);
			case GLOBALINTERRUPTIBLE:
				return AntlrGlobalInterruptible.parseGlobalInterruptible(this, ct);
			case GLOBALINTERRUPT:
				return AntlrGlobalInterrupt.parseGlobalInterrupt(this, ct);
			case GLOBALDO:
				return AntlrGlobalDo.parseGlobalDo(this, ct);
			case ROLEINSTANTIATIONLIST:
				return AntlrRoleInstantiationList.parseRoleInstantiationList(this, ct);
			case ROLEINSTANTIATION:
				return AntlrRoleInstantiation.parseRoleInstantiation(this, ct);
			case ARGUMENTINSTANTIATIONLIST:
				return AntlrArgumentInstantiationList.parseArgumentInstantiationList(this, ct);
			case ARGUMENTINSTANTIATION:
				return AntlrArgumentInstantiation.parseArgumentInstantiation(this, ct);
			default:
				throw new RuntimeException("Unknown ANTLR node type: " + type);
		}
	}
}
