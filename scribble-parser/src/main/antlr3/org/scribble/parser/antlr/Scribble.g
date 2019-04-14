/*
 * > scribble-java
 * $ java -cp scribble-parser/lib/antlr-3.5.2-complete.jar org.antlr.Tool -o scribble-parser/target/generated-sources/antlr3 scribble-parser/src/main/antlr3/org/scribble/parser/antlr/Scribble.g
 * 
 * Cygwin/Windows
 * > scribble-java
 * $ java -cp scribble-parser/lib/antlr-3.5.2-complete.jar org.antlr.Tool -o scribble-parser/target/generated-sources/antlr3/org/scribble/parser/antlr scribble-parser/src/main/antlr3/org/scribble/parser/antlr/Scribble.g
 * $ mv scribble-parser/target/generated-sources/antlr3/org/scribble/parser/antlr/Scribble.tokens scribble-parser/target/generated-sources/antlr3/
 */

/**
 * Pattern: each node type must give its "node type constant" as its text, e.g., module: ... -> ^(MODULE ...) -- all info in its children
 * i.e. Each Token will be equivalent to, e.g., new CommonToken(ScribbleParser.MODULE, "MODULE")
 * Except for IDENTIFIER/IdNode: text is the IDENTIFIER value, i.e., new CommonToken(ScribbleParser.IDENTIFIER, "...")
 */

grammar Scribble;


options
{
  language = Java;
  output = AST;
  ASTLabelType = ScribNodeBase;
}


// CHECKME: match tokens to keywords?  // Use KW as token types? But many more types than keywords

tokens
{
  /*
   * Parser "input" constants (lexer output; keywords, Section 2.4)
   */

  MODULE_KW = 'module';
  IMPORT_KW = 'import';
  SIG_KW = 'sig';
  TYPE_KW = 'type';
  PROTOCOL_KW = 'protocol';
  AS_KW = 'as';

  GLOBAL_KW = 'global';
  LOCAL_KW = 'local';
  EXPLICIT_KW = 'explicit';
  AUX_KW = 'aux';

  ROLE_KW = 'role';
  SELF_KW = 'self';

  FROM_KW = 'from';
  TO_KW = 'to';
  CONNECT_KW = 'connect';
  WRAP_KW = 'wrap';

  DISCONNECT_KW = 'disconnect';
  AND_KW = 'and';

  CHOICE_KW = 'choice';
  AT_KW = 'at';
  OR_KW = 'or';

  REC_KW = 'rec';
  CONTINUE_KW = 'continue';
  DO_KW = 'do';
  

  /*
   * Parser "output" node types (corresponding to the various syntactic
   * categories), i.e. the labels used to distinguish resulting AST nodes. The
   * specific value of these tokens doesn't matter (because not parsed
   * themselves).
   */
  
  // Special cases
  EMPTY_OPERATOR = '__EMPTY_OPERATOR';
  QUALIFIEDNAME = '__QUALIFIEDNAME';  
      // N.B. an intermediate node type, not an actual ScribNode -- "re-parsed" internally by parsePayloadElem/NonRoleArg
      // No relation to abstract AST class, QualifiedName


  // Names
  AMBIGUOUSNAME = 'AMBIGUOUSNAME';
  MODULENAME = 'MODULENAME';
  GPROTOCOLNAME = 'GPROTOCOLNAME';
  OPNAME = 'OPNAME';
  RECURSIONVAR = 'RECURSIONVAR';
  ROLENAME = 'ROLENAME';
  SIGNAME = 'SIGNAME';
  SIGPARAMNAME = 'sigparamnamenode';  // N.B. distinct from SIGNAME
  TYPENAME = 'TYPENAME';
  TYPEPARAMNAME = 'typeparamnamenode';  // N.B. distinct from TYPENAME


  // "Node type" constants -- parsed "directly" by AntlrToScribParser
  // TODO: split naming of type int constant from String label ?
  // TODO: split imaginary node types

  MODULE = 'MODULE';
  MODULEDECL = 'MODULEDECL';
  IMPORTMODULE = 'IMPORTMODULE';

  PAYLOADTYPEDECL = 'PAYLOADTYPEDECL';
  MESSAGESIGNATUREDECL = 'MESSAGESIGNATUREDECL';

  GLOBALPROTOCOLDECL = 'GLOBALPROTOCOLDECL';
  GLOBALPROTOCOLDECLMODS = 'GLOBALPROTOCOLDECLMODS';
  GLOBALPROTOCOLHEADER = 'GLOBALPROTOCOLHEADER';

  ROLEDECLLIST = 'ROLEDECLLIST';
  ROLEDECL = 'ROLEDECL';
  PARAMETERDECLLIST = 'PARAMETERDECLLIST';
  TYPEPARAMDECL = 'TYPEPARAMDECL';
  SIGPARAMDECL = 'SIGPARAMDECL';
  
  GLOBALPROTOCOLDEF = 'GLOBALPROTOCOLDEF';
  GLOBALPROTOCOLBLOCK = 'GLOBALPROTOCOLBLOCK';
  GLOBALINTERACTIONSEQUENCE = 'GLOBALINTERACTIONSEQUENCE';

  MESSAGESIGNATURE = 'MESSAGESIGNATURE';
  PAYLOAD = 'PAYLOAD';
  UNARYPAYLOADELEM = 'UNARYPAYLOADELEM';
  DELEGATION = 'DELEGATION';

  GLOBALMESSAGETRANSFER = 'GLOBALMESSAGETRANSFER';
  GLOBALCONNECT = 'GLOBALCONNECT';
  GLOBALWRAP = 'GLOBALWRAP';
  GLOBALDISCONNECT = 'GLOBALDISCONNECT';
  GLOBALCONTINUE = 'GLOBALCONTINUE';
  GLOBALDO = 'GLOBALDO';

  ROLEINSTANTIATIONLIST = 'ROLEINSTANTIATIONLIST';
  ROLEINSTANTIATION = 'ROLEINSTANTIATION';  // FIXME: not consistent with arginstas/payloadeles
  ARGUMENTINSTANTIATIONLIST = 'ARGUMENTINSTANTIATIONLIST';  // FIXME: token name inconsistent with class name (NonRoleArgList)
  NONROLEARG = 'NONROLEARG';

  GLOBALCHOICE = 'GLOBALCHOICE';
  GLOBALRECURSION = 'GLOBALRECURSION';
}


// Must come after tokens?
@parser::header
{
  package org.scribble.parser.antlr;
  
  //import org.scribble.main.RuntimeScribbleException;
  //import org.scribble.parser.scribble.ast.tree.MyCommonTree;
  import org.scribble.ast.ScribNodeBase;
  import org.scribble.ast.UnaryPayloadElem;
  import org.scribble.ast.NonRoleArg;
  import org.scribble.ast.name.qualified.DataTypeNode;
  import org.scribble.ast.name.simple.IdNode;
  import org.scribble.ast.name.simple.AmbigNameNode;
  import org.scribble.ast.name.simple.RecVarNode;
  import org.scribble.ast.name.simple.*;
  import org.scribble.ast.*;
}

@lexer::header
{
  package org.scribble.parser.antlr;
  
  //import org.scribble.main.RuntimeScribbleException;
}

/*// Was swallowing parser error messages
@members {
  //private org.scribble.logging.IssueLogger _logger=null;
  private String _document=null;
  private boolean _errorOccurred=false;
  
    /*public void setLogger(org.scribble.logging.IssueLogger logger) {
      _logger = logger;
    }* /
    
    public void setDocument(String doc) {
      _document = doc;
    }
    
    public void emitErrorMessage(String mesg) {
      /*if (_logger == null) {
        super.emitErrorMessage(mesg);
      } else {
        _logger.error(org.scribble.parser.antlr.ANTLRMessageUtil.getMessageText(mesg),
              org.scribble.parser.antlr.ANTLRMessageUtil.getProperties(mesg, _document));
      }* /
      _errorOccurred = true;
    }
    
    public boolean isErrorOccurred() {
      return(_errorOccurred);
    }
}*/

@parser::members
{
  /*@Override
  public void reportError(RecognitionException e)
  {
    super.reportError(e);
    //throw new RuntimeScribbleException(e.getMessage()); 
    //System.exit(1);
  }*/

  // Abort tool run on parsing errors (and display user-friendly message) -- obsoletes CommonErrorNode check?
  @Override    
  public void displayRecognitionError(String[] tokenNames, RecognitionException e)
  {
    /*String hdr = getErrorHeader(e);
    String msg = getErrorMessage(e, tokenNames);
    //throw new RuntimeException(hdr + ":" + msg);
    System.err.println(hdr + ":" + msg);*/
    super.displayRecognitionError(tokenNames, e);
    System.exit(1);
  }

  // qn is an IdNode "holder" for a qualifiedname 
  // CHECKME: do the returns of these "bypass" ScribTreeAdaptor?  specifically AmbigNode
  public static CommonTree parsePayloadElem(CommonTree qn) throws RecognitionException
  {
    if (qn.getChildCount() > 1)  // qn has IdNode children, elements of the qualifiedname
    {
      DataTypeNode dt = new DataTypeNode(new CommonToken(TYPENAME, "TYPENAME"));
      ((List<?>) qn.getChildren()).forEach(x -> 
          dt.addChild(new IdNode(new CommonToken(IDENTIFIER, ((CommonTree) x).getText()))));
      UnaryPayloadElem pe = 
          new UnaryPayloadElem(new CommonToken(UNARYPAYLOADELEM, "UNARYPAYLOADELEM"));
      pe.addChild(dt);
      return pe;  // N.B. "re-parsed" by ScribTreeAdaptor?  Could return CommonTree here?
    }
    else
    {
      // Similarly to NonRoleArg: cannot syntactically distinguish right now between SimplePayloadTypeNode and ParameterNode
      String text = qn.getChild(0).getText();
      IdNode id = new IdNode(new CommonToken(IDENTIFIER, text));
      /*AmbigNameNode an = 
          new AmbigNameNode(new CommonToken(AMBIGUOUSNAME, "AMBIGUOUSNAME"));
      an.addChild(id);*/
      AmbigNameNode an = 
          new AmbigNameNode(AMBIGUOUSNAME, new CommonToken(IDENTIFIER, text));
      UnaryPayloadElem e = new UnaryPayloadElem(
          new CommonToken(UNARYPAYLOADELEM, "UNARYPAYLOADELEM"));
      e.addChild(an);
      return e;
    }
  }

  // Only for QualifiedName (DataTypeNode or AmbigNameNode), not messagesignature literal 
  // qn is an IdNode "holder" for a qualifiedname 
  public static CommonTree parseNonRoleArg(CommonTree qn) throws RecognitionException
  {
    if (qn.getChildCount() > 1)  // qn has IdNode children, elements of the qualifiedname
    {
      DataTypeNode dt = new DataTypeNode(new CommonToken(TYPENAME, "TYPENAME"));
      ((List<?>) qn.getChildren()).forEach(x -> 
          dt.addChild(new IdNode(new CommonToken(IDENTIFIER, ((CommonTree) x).getText()))));
      NonRoleArg a = 
          new NonRoleArg(new CommonToken(NONROLEARG, "NONROLEARG"));
      a.addChild(dt);
      return a;
    }
    else
    {
      String text = qn.getChild(0).getText();
      IdNode id = new IdNode(new CommonToken(IDENTIFIER, text));
      /*AmbigNameNode an = 
          new AmbigNameNode(new CommonToken(AMBIGUOUSNAME, "AMBIGUOUSNAME"));
      an.addChild(id);*/
      AmbigNameNode an = 
          new AmbigNameNode(AMBIGUOUSNAME, new CommonToken(IDENTIFIER, text));
      NonRoleArg a = new NonRoleArg(new CommonToken(NONROLEARG, "NONROLEARG"));
      a.addChild(an);
      return a;
    }
  }
}

@lexer::members
{
  /*@Override
  public void reportError(RecognitionException e)
  {
    super.reportError(e);
    //throw new RuntimeScribbleException(e.getMessage()); 
    //System.exit(1);
  }*/

  @Override    
  public void displayRecognitionError(String[] tokenNames, RecognitionException e)
  {
    /*String hdr = getErrorHeader(e);
    String msg = getErrorMessage(e, tokenNames);
    //throw new RuntimeException(hdr + ":" + msg);
    System.err.println(hdr + ":" + msg);*/
    super.displayRecognitionError(tokenNames, e);
    System.exit(1);
  }
}


/****************************************************************************
 * Chapter 2 Lexical Structure (Lexer rules)
 ***************************************************************************/

/*
 * Section 2.1 White space (Section 2.1)
 */

// Not referred to explicitly, deals with whitespace implicitly (don't delete this)
WHITESPACE:
  ('\t' | ' ' | '\r' | '\n'| '\u000C')+
  {
    $channel = HIDDEN;
  }

;

/**
 * Section 2.2 Comments
 */
COMMENT:
  '/*' .* '*/'
  {
    $channel=HIDDEN;
  }
;

LINE_COMMENT:
  '//' ~('\n'|'\r')* '\r'? '\n'
  {
    $channel=HIDDEN;
  }
;

/**
 * Section 2.3 Identifiers
 */
IDENTIFIER:
  (LETTER | DIGIT | UNDERSCORE)*  
      /* Underscore currently can cause ambiguities in the API generation naming
       * scheme But maybe only consecutive underscores are the problem -- cannot
       * completely disallow underscores as needed for projection naming scheme
       * Or disallow underscores only for role/op/messagesig names
       */
;

fragment SYMBOL:
  '{' | '}' | '(' | ')' | '[' | ']' | ':' | '/' | '\\' | '.' | '\#'
|
  '&' | '?' | '!'  | UNDERSCORE
;

// Comes after SYMBOL due to an ANTLR syntax highlighting issue involving
// quotes.
// Parser doesn't work without quotes here (e.g. if inlined into parser rules)
EXTIDENTIFIER:
  '\"' (LETTER | DIGIT | SYMBOL)* '\"'
  //(LETTER | DIGIT | SYMBOL)*
;

fragment LETTER:
  'a'..'z' | 'A'..'Z'
;

fragment DIGIT:
  '0'..'9'
;

fragment UNDERSCORE:
  '_'
;


/****************************************************************************
 * Chapter 3 Syntax (Parser rules)
 ***************************************************************************/

/*
 * Section 3.1 Primitive Names
 */



simplename: IDENTIFIER;

//parametername:    simplename;

//simplename;
recursionvarname: IDENTIFIER ;
//  IDENTIFIER<RecVarNode>^


rolename:         simplename;
simpleprotocolname:         simplename;
simplemembername:           simplename;  // Only for member declarations

//rolenamenode: rolename -> ^(ROLENAME rolename) ;
//recursionvarnamenode: recursionvarname -> ^(RECURSIONVAR recursionvarname) ;
//ambiguousnamenode: simplename -> ^(AMBIGUOUSNAME simplename);
//opnamenode: IDENTIFIER -> ^(OPNAME IDENTIFIER) ;
//typeparamnamenode: parametername -> ^(typeparamnamenode parametername) ; 
//sigparamnamenode: parametername -> ^(sigparamnamenode parametername) ;

//emptyopnamenode: ^(EMPTY_OPERATOR);  // simp name nodes use token text for id value  // FIXME: can't seem to use this "out of nowhere" in rewrite rules

// "The TreeAdaptor is not called; instead for constructors are invoked directly."
// "Note that parameters are not allowed on token references to the left of ->:"
// "Use imaginary nodes as you normally would, but with the addition of the node type:"  // Pointless?  token itself unchanged and ttype int ends up unused
rolenamenode: IDENTIFIER -> IDENTIFIER<RoleNode>[$IDENTIFIER] ;
recursionvarnamenode: IDENTIFIER -> IDENTIFIER<RecVarNode>[$IDENTIFIER];
ambiguousnamenode: IDENTIFIER -> IDENTIFIER<AmbigNameNode>[$IDENTIFIER];
opnamenode:
IDENTIFIER -> IDENTIFIER<OpNode>[$IDENTIFIER]
| -> ^(EMPTY_OPERATOR);
//emptyopnamenode: IDENTIFIER<OpNode>[EMPTY_OPERATOR];  // empty "left" of rewrite not allowed
typeparamnamenode: IDENTIFIER -> IDENTIFIER<TypeParamNode>[$IDENTIFIER]; 
sigparamnamenode: IDENTIFIER -> IDENTIFIER<SigParamNode>[$IDENTIFIER];



/**
 * Section 3.2.1 Package, Module and Module Member Names
 */

// Using qualified to rep simple
simplemodulename: IDENTIFIER -> ^(MODULENAME IDENTIFIER) ;
simplepayloadtypename: IDENTIFIER -> ^(TYPENAME IDENTIFIER) ; 
    // TODO factor out with typedecl?  NO: params distinct from "literals"
simplemessagesignaturename: IDENTIFIER -> ^(SIGNAME IDENTIFIER) ;
    // TODO factor out with sigparamdecl?  NO: params distinct from "literals"


qualifiedname:
  IDENTIFIER ('.' IDENTIFIER)*
->
  ^(QUALIFIEDNAME IDENTIFIER+)
;

packagename:          qualifiedname;
membername:           qualifiedname;

modulename:
  IDENTIFIER ('.' IDENTIFIER)*
->
  ^(MODULENAME IDENTIFIER+)
;

protocolname:         membername;
payloadtypename:      membername;

messagesignaturename:
  IDENTIFIER ('.' IDENTIFIER)*
->
  ^(SIGNAME IDENTIFIER+)
;

gprotocolnamenode:
    IDENTIFIER ('.' IDENTIFIER)*
  ->
  ^(GPROTOCOLNAME IDENTIFIER+)
;


/**
 * Section 3.2.2 Top-level Module Structure
 * Section 3.2.3 Module Declarations
 */
// "References to tokens with rewrite not found on left of -> are imaginary tokens."
// Inlined moduledecl to make token info ref work
module:
  t=MODULE_KW modulename ';' importmodule* datatypedecl* protocoldecl* EOF
->
  ^(MODULE[$t] ^(MODULEDECL[$t] modulename) importmodule* datatypedecl*
  protocoldecl*)
;
// TODO: add token arg to other imag nodes 
// moduledecl: MODULE_KW<ModuleDecl>^ modulename ';'  // "Become root" ^ cannot be on rhs?


/**
 * Section 3.3 Import Declarations
 */
importmodule:
   t=IMPORT_KW modulename (AS_KW alias=simplemodulename)? ';'
-> ^(IMPORTMODULE[$t] modulename $alias?)
;


/**
 * Section 3.4 Payload Type Declarations 
 * // TODO: rename type as data
 */
datatypedecl: payloadtypedecl | messagesignaturedecl ;

payloadtypedecl:
  t=TYPE_KW '<' schema=IDENTIFIER '>' extName=EXTIDENTIFIER FROM_KW
  extSource=EXTIDENTIFIER AS_KW alias=simplepayloadtypename ';'
->
  // alias first to be uniform with other NameDeclNode (getRawNameNodeChild)
  ^(PAYLOADTYPEDECL[$t] $alias $schema $extName $extSource)
;

messagesignaturedecl:
  t=SIG_KW '<' schema=IDENTIFIER '>' extName=EXTIDENTIFIER FROM_KW
  extSource=EXTIDENTIFIER AS_KW alias=simplemessagesignaturename ';'
->
  // alias first to be uniform with other NameDeclNode (getRawNameNodeChild)
  ^(MESSAGESIGNATUREDECL[$t] $alias $schema $extName $extSource)
;



/**
 * Section 3.5 Message Signatures
 */
messagesignature:
   opnamenode '(' payload ')'
-> ^(MESSAGESIGNATURE opnamenode payload)
;
// CHECKME: how to apply [$t] in these situations?

payload:
-> ^(PAYLOAD)
 | payloadelement (',' payloadelement)*
-> ^(PAYLOAD payloadelement+)
;

payloadelement:
  // Payload element must be a data kind, cannot be a sig name
  // Qualified name must be a data type name
  // Also subsumes simple names, could be a data *param*
  qualifiedname
-> { parsePayloadElem($qualifiedname.tree) }  // Use ".text" instead of ".tree" for token String 
 | protocolname '@' rolename
-> ^(DELEGATION rolename protocolname)
;



/**
 * Section 3.6 Protocol Declarations
 */
protocoldecl: globalprotocoldecl ;


/**
 * Section 3.7 Global Protocol Declarations
 */
globalprotocoldecl:
  globalprotocoldeclmodifiers globalprotocolheader globalprotocoldefinition
->
  ^(GLOBALPROTOCOLDECL globalprotocoldeclmodifiers globalprotocolheader globalprotocoldefinition)
;
  
// "aux" must come before "explicit"
globalprotocoldeclmodifiers:
                        -> ^(GLOBALPROTOCOLDECLMODS)
 | t=AUX_KW             -> ^(GLOBALPROTOCOLDECLMODS[$t] AUX_KW)
 | t=AUX_KW EXPLICIT_KW -> ^(GLOBALPROTOCOLDECLMODS[$t] AUX_KW EXPLICIT_KW)
 | t=EXPLICIT_KW        -> ^(GLOBALPROTOCOLDECLMODS[$t] EXPLICIT_KW)
;

globalprotocolheader:
  GLOBAL_KW PROTOCOL_KW gprotocolnamenode parameterdecllist roledecllist
->
  ^(GLOBALPROTOCOLHEADER gprotocolnamenode parameterdecllist roledecllist)
;

roledecllist: 
  t='(' roledecl (',' roledecl)* ')' -> ^(ROLEDECLLIST[$t] roledecl+) ;
roledecl:     t=ROLE_KW rolenamenode -> ^(ROLEDECL[$t] rolenamenode) ;

parameterdecllist:
-> ^(PARAMETERDECLLIST)
 | t='<' parameterdecl (',' parameterdecl)* '>'
-> ^(PARAMETERDECLLIST[$t] parameterdecl+)
;

parameterdecl: typedecl | sigdecl ;
typedecl: 
  t=TYPE_KW typeparamnamenode -> ^(TYPEPARAMDECL[$t] typeparamnamenode) ;
sigdecl:  
  t=SIG_KW sigparamnamenode -> ^(SIGPARAMDECL[$t] sigparamnamenode) ;


/**
 * Section 3.7.1 Global Protocol Definitions
 */
globalprotocoldefinition:
  globalprotocolblock -> ^(GLOBALPROTOCOLDEF globalprotocolblock) ;


/**
 * Section 3.7.3 Global Interaction Sequences and Blocks
 */
globalprotocolblock:
  t='{' globalinteractionsequence '}'
->
  ^(GLOBALPROTOCOLBLOCK[$t] globalinteractionsequence)
;

globalinteractionsequence:
  globalinteraction*
->
  ^(GLOBALINTERACTIONSEQUENCE globalinteraction*)
;

globalinteraction:
  globalmessagetransfer
| globalchoice
| globalrecursion
| globalcontinue
| globaldo
| globalconnect
| globaldisconnect
| globalwrap
;


/**
 * Section 3.7.4 Global Message Transfer
 */
globalmessagetransfer:
   message FROM_KW rolenamenode TO_KW rolenamenode (',' rolenamenode )* ';'
-> ^(GLOBALMESSAGETRANSFER message rolenamenode+)
;
// TODO: multisend

message:
  messagesignature   // Sig literal
| ambiguousnamenode  // Sig name or sig param name
;  
// TODO: qualified (messagesig) names  // Although qualified messagesignaturename subsumes parametername case

globalconnect:
   message CONNECT_KW rolenamenode TO_KW rolenamenode ';'
-> ^(GLOBALCONNECT message rolenamenode rolenamenode)
 | t=CONNECT_KW rolenamenode TO_KW rolenamenode ';'
-> ^(GLOBALCONNECT[$t] ^(MESSAGESIGNATURE ^(EMPTY_OPERATOR) ^(PAYLOAD))
   rolenamenode rolenamenode)
      // CHECKME: deprecate? i.e., require "()" as for message transfers?  i.e., simply delete this rule?
;


globaldisconnect:
   t=DISCONNECT_KW rolenamenode AND_KW rolenamenode ';'
-> ^(GLOBALDISCONNECT[$t] rolenamenode rolenamenode)
;

globalwrap:
   t=WRAP_KW rolenamenode TO_KW rolenamenode ';'
-> ^(GLOBALWRAP[$t] rolenamenode rolenamenode)
;


/**
 * Section 3.7.5 Global Choice
 */
globalchoice:
   t=CHOICE_KW AT_KW rolenamenode globalprotocolblock (OR_KW
   globalprotocolblock)*
-> ^(GLOBALCHOICE[$t] rolenamenode globalprotocolblock+)
;


/**
 * Section 3.7.6 Global Recursion
 */
globalrecursion:
   t=REC_KW recursionvarnamenode globalprotocolblock
-> ^(GLOBALRECURSION[$t] recursionvarnamenode globalprotocolblock)
;


globalcontinue:
   t=CONTINUE_KW recursionvarnamenode ';'
-> ^(GLOBALCONTINUE recursionvarnamenode)
;


/**
 * Section 3.7.9 Global Do
 */
globaldo:
   DO_KW gprotocolnamenode argumentinstantiationlist roleinstantiationlist ';'
-> ^(GLOBALDO gprotocolnamenode argumentinstantiationlist roleinstantiationlist)
;

roleinstantiationlist:
   t='(' roleinstantiation (',' roleinstantiation)* ')'
-> ^(ROLEINSTANTIATIONLIST[$t] roleinstantiation+)
;
roleinstantiation: rolenamenode -> ^(ROLEINSTANTIATION rolenamenode) ;
    // CHECKME: inconsistent with arginstas/payloadeles

argumentinstantiationlist:
-> ^(ARGUMENTINSTANTIATIONLIST)
|  t='<' argumentinstantiation (',' argumentinstantiation)* '>'
-> ^(ARGUMENTINSTANTIATIONLIST[$t] argumentinstantiation+)
;

// Grammatically same as message, but qualifiedname case may also be a payload type
argumentinstantiation:
   messagesignature
-> ^(NONROLEARG messagesignature)
|  qualifiedname
-> { parseNonRoleArg($qualifiedname.tree) }  // Like PayloadElement, simple names need disambiguation
;

