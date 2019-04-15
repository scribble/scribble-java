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
 * i.e. Each token will be equivalent to, e.g., new CommonToken(ScribbleParser.MODULE, "MODULE")
 * Except for IDENTIFIER/IdNode: text is the IDENTIFIER value, i.e., new CommonToken(ScribbleParser.IDENTIFIER, "...")
 */

grammar Scribble;


options
{
  language = Java;
  output = AST;
  ASTLabelType = ScribNodeBase;
}


tokens
{
  /* Parser "input" constants (lexer output; keywords, Section 2.4)
   */
  MODULE_KW = 'module';
  IMPORT_KW = 'import';
	DATA_KW = 'data';
  SIG_KW = 'sig';
  TYPE_KW = 'type';
  PROTOCOL_KW = 'protocol';
  AS_KW = 'as';

  GLOBAL_KW = 'global';
  LOCAL_KW = 'local';  // Currently not parsed, but may be generated
  EXPLICIT_KW = 'explicit';
  AUX_KW = 'aux';

  ROLE_KW = 'role';
  SELF_KW = 'self';  // Currently not parsed, but may be generated

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
  

  /* Scribble AST token types (corresponding to the Scribble BNF).  
   * These token types are used by ScribTreeAdaptor to create the output nodes
   * using the org.scribble.ast classes.
   * (Trying to construct those classes directly from here doesn't seem to work
   * well for most cases.)
   * These tokens are ANTLR "imaginary tokens": they are derived by the ANTLR
   * "rewrite rules" on the actual source tokens.
   * The specific value of these tokens aren't important (the constants are
   * accessed via fields of ScribbleParser).
   * As a naming convention, we use a few "_" suffixes: _KW, _NAME, _LIT and
   * _LIST.
   */
  // CHECKME: split naming of type int constant from String label ?
  // CHECKME: split imaginary node types ?

  // Special cases
  EMPTY_OP = '__EMPTY_OP';
  COMPOUND_NAME = '__COMPOUND_NAME';  
      // N.B. an intermediate node type, not a concrete ScribNode -- "re-parsed" internally by parsePayloadElem/NonRoleArg

  // Simple names
  AMBIG_NAME = 'AMBIG_NAME';
  // Other simple names are 

	// Compound names
  GPROTO_NAME = 'GPROTO_NAME';
  MODULE_NAME = 'MODULE_NAME';
  SIG_NAME = 'SIG_NAME';   // N.B. distinct from SIGPARAM_NAME
  TYPE_NAME = 'TYPE_NAME';   // N.B. distinct from TYPEPARAM_NAME

	// Sig literals
  SIG_LIT = 'SIG_LIT';
  PAYELEM_LIST = 'PAYELEM_LIST';
  UNARY_PAYELEM = 'UNARY_PAYELEM';
  DELEG_PAYELEM = 'DELEG_PAYELEM';

  MODULE = 'MODULE';
  MODULEDECL = 'MODULEDECL';
  IMPORTMODULE = 'IMPORTMODULE';

  DATADECL = 'DATADECL';
  SIGDECL = 'SIGDECL';
  GPROTODECL = 'GPROTODECL';
  PROTOMOD_LIST = 'PROTOMOD_LIST';

  GPROTOHEADER = 'GPROTOHEADER';
  ROLEDECL_LIST = 'ROLEDECL_LIST';
  ROLEDECL = 'ROLEDECL';
  PARAMDECL_LIST = 'PARAMDECL_LIST';
  TYPEPARAMDECL = 'TYPE_PARAMDECL';
  SIGPARAMDECL = 'SIG_PARAMDECL';
  
  GPROTODEF = 'GPROTODEF';
  GPROTOBLOCK = 'GPROTOBLOCK';
  GACTIONSEQ = 'GACTIONSEQ';

  GCONNECT = 'GCONNECT';
  GCONTINUE = 'GCONTINUE';
  GDCONN = 'GDCONN';
  GDO = 'GDO';
  GMSGTRANSFER = 'GMSGTRANSFER';
  GWRAP = 'GWRAP';

  ROLEARG_LIST = 'ROLEARG_LIST';  // Cf. ROLEDECL
  ROLEARG = 'ROLEARG_LIST';
  ARG_LIST = 'ARG_LIST';  // Cf. PARAM_DECL
  ARG = 'ARG';

  GCHOICE = 'GCHOICE';
  GRECURSION = 'GRECURSION';
}


// Must come after tokens?
@parser::header
{
  package org.scribble.parser.antlr;
  
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
      DataTypeNode dt = new DataTypeNode(new CommonToken(TYPE_NAME, "TYPE_NAME"));
      ((List<?>) qn.getChildren()).forEach(x -> 
          dt.addChild(new IdNode(new CommonToken(IDENTIFIER, ((CommonTree) x).getText()))));
      UnaryPayloadElem pe = 
          new UnaryPayloadElem(new CommonToken(UNARY_PAYELEM, "UNARYPAYLOADELEM"));
      pe.addChild(dt);
      return pe;  // N.B. "re-parsed" by ScribTreeAdaptor?  Could return CommonTree here?
    }
    else
    {
      // Similarly to NonRoleArg: cannot syntactically distinguish right now between SimplePayloadTypeNode and ParameterNode
      String text = qn.getChild(0).getText();
      IdNode id = new IdNode(new CommonToken(IDENTIFIER, text));
      /*AmbigNameNode an = 
          new AmbigNameNode(new CommonToken(AMBIG_NAME, "AMBIG_NAME"));
      an.addChild(id);*/
      AmbigNameNode an = 
          new AmbigNameNode(AMBIG_NAME, new CommonToken(IDENTIFIER, text));
      UnaryPayloadElem e = new UnaryPayloadElem(
          new CommonToken(UNARY_PAYELEM, "UNARYPAYLOADELEM"));
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
      DataTypeNode dt = new DataTypeNode(new CommonToken(TYPE_NAME, "TYPE_NAME"));
      ((List<?>) qn.getChildren()).forEach(x -> 
          dt.addChild(new IdNode(new CommonToken(IDENTIFIER, ((CommonTree) x).getText()))));
      NonRoleArg a = 
          new NonRoleArg(new CommonToken(ARG, "ARG"));
      a.addChild(dt);
      return a;
    }
    else
    {
      String text = qn.getChild(0).getText();
      IdNode id = new IdNode(new CommonToken(IDENTIFIER, text));
      /*AmbigNameNode an = 
          new AmbigNameNode(new CommonToken(AMBIG_NAME, "AMBIG_NAME"));
      an.addChild(id);*/
      AmbigNameNode an = 
          new AmbigNameNode(AMBIG_NAME, new CommonToken(IDENTIFIER, text));
      NonRoleArg a = new NonRoleArg(new CommonToken(ARG, "ARG"));
      a.addChild(an);
      return a;
    }
  }

  public static CommonTree checkId(CommonTree id)
  {
  	if (id.getText().contains("__"))
  	{
			System.err.println("Double underscores are reserved: " + id);
			System.exit(1);
  	}
  	return id;
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
//simplename: id=IDENTIFIER -> { checkId($id.tree) } ;  // How to integrate with IDENTIFIER<RoleNode>[$t] ?

// "The TreeAdaptor is not called; instead [the] constructors are invoked directly."
// "Note that parameters are not allowed on token references to the left of ->:"
// "Use imaginary nodes as you normally would, but with the addition of the node type:"  // But currently, ID token itself unchanged and ttype int ends up discarded
ambiguousnamenode: t=IDENTIFIER -> IDENTIFIER<AmbigNameNode>[$t];
recursionvarnamenode: t=IDENTIFIER -> IDENTIFIER<RecVarNode>[$t];
rolenamenode: t=IDENTIFIER -> IDENTIFIER<RoleNode>[$t];
sigparamnamenode: t=IDENTIFIER -> IDENTIFIER<SigParamNode>[$t];
typeparamnamenode: t=IDENTIFIER -> IDENTIFIER<TypeParamNode>[$t]; 
opnamenode:
	-> ^(EMPTY_OP)
|
	t=IDENTIFIER -> IDENTIFIER<OpNode>[$t]
;

/**
 * Section 3.2.1 Package, Module and Module Member Names
 */

// Using qualified to rep simple
simplemodulename: t=IDENTIFIER -> ^(MODULE_NAME[$t] IDENTIFIER) ;
simplepayloadtypename: t=IDENTIFIER -> ^(TYPE_NAME[$t] IDENTIFIER) ; 
    // TODO factor out with typedecl?  NO: params distinct from "literals"
simplemessagesignaturename: t=IDENTIFIER -> ^(SIG_NAME[$t] IDENTIFIER) ;
    // TODO factor out with sigparamdecl?  NO: params distinct from "literals"
simpleprotocolname: t=IDENTIFIER -> ^(GPROTO_NAME[$t] IDENTIFIER) ;

qualifiedname:
  t=IDENTIFIER ('.' IDENTIFIER)*
->
  ^(COMPOUND_NAME[$t] IDENTIFIER+)
;

packagename:          qualifiedname;
membername:           qualifiedname;

modulename:
  t=IDENTIFIER ('.' IDENTIFIER)*
->
  ^(MODULE_NAME[$t] IDENTIFIER+)
;

protocolname:         membername;
payloadtypename:      membername;

messagesignaturename:
  t=IDENTIFIER ('.' IDENTIFIER)*
->
  ^(SIG_NAME[$t] IDENTIFIER+)
;

gprotocolnamenode:
    t=IDENTIFIER ('.' IDENTIFIER)*
  ->
  ^(GPROTO_NAME[$t] IDENTIFIER+)
;


/**
 * Section 3.2.2 Top-level Module Structure
 * Section 3.2.3 Module Declarations
 */
// "References to tokens with rewrite not found on left of -> are imaginary tokens."
// Inlined moduledecl to make token label work
module:
  t=MODULE_KW modulename ';' importmodule* datatypedecl* protocoldecl* EOF
->
  ^(MODULE[$t] ^(MODULEDECL[$t] modulename) importmodule* datatypedecl*
  protocoldecl*)
;
// moduledecl: MODULE_KW<ModuleDecl>^ modulename ';'  
		// "Become root" ^ cannot be on rhs? -- so "manually" rewrite to Scribble AST token types


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
  ^(DATADECL[$t] $alias $schema $extName $extSource)
;

messagesignaturedecl:
  t=SIG_KW '<' schema=IDENTIFIER '>' extName=EXTIDENTIFIER FROM_KW
  extSource=EXTIDENTIFIER AS_KW alias=simplemessagesignaturename ';'
->
  // alias first to be uniform with other NameDeclNode (getRawNameNodeChild)
  ^(SIGDECL[$t] $alias $schema $extName $extSource)
;



/**
 * Section 3.5 Message Signatures
 */
messagesignature:
   opnamenode '(' payload ')'
-> ^(SIG_LIT opnamenode payload)
;
// CHECKME: how to apply [$t] in these situations?

payload:
-> ^(PAYELEM_LIST)
 | payloadelement (',' payloadelement)*
-> ^(PAYELEM_LIST payloadelement+)
;

payloadelement:
  // Payload element must be a data kind, cannot be a sig name
  // Qualified name must be a data type name
  // Also subsumes simple names, could be a data *param*
  qualifiedname
-> { parsePayloadElem($qualifiedname.tree) }  // Use ".text" instead of ".tree" for token String 
 | gprotocolnamenode '@' rolenamenode
-> ^(DELEG_PAYELEM rolenamenode gprotocolnamenode)
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
  ^(GPROTODECL globalprotocoldeclmodifiers globalprotocolheader
  globalprotocoldefinition)
;
  
// "aux" must come before "explicit"
globalprotocoldeclmodifiers:
                        -> ^(PROTOMOD_LIST)
 | t=AUX_KW             -> ^(PROTOMOD_LIST[$t] AUX_KW)
 | t=AUX_KW EXPLICIT_KW -> ^(PROTOMOD_LIST[$t] AUX_KW EXPLICIT_KW)
 | t=EXPLICIT_KW        -> ^(PROTOMOD_LIST[$t] EXPLICIT_KW)
;

globalprotocolheader:
  t=GLOBAL_KW PROTOCOL_KW simpleprotocolname parameterdecllist roledecllist
->
  ^(GPROTOHEADER[$t] simpleprotocolname parameterdecllist roledecllist)
;

roledecllist: 
  t='(' roledecl (',' roledecl)* ')' -> ^(ROLEDECL_LIST[$t] roledecl+) ;

roledecl:     t=ROLE_KW rolenamenode -> ^(ROLEDECL[$t] rolenamenode) ;

parameterdecllist:
-> ^(PARAMDECL_LIST)
 | t='<' parameterdecl (',' parameterdecl)* '>'
-> ^(PARAMDECL_LIST[$t] parameterdecl+)
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
  globalprotocolblock -> ^(GPROTODEF globalprotocolblock) ;


/**
 * Section 3.7.3 Global Interaction Sequences and Blocks
 */
globalprotocolblock:
  t='{' globalinteractionsequence '}'
->
  ^(GPROTOBLOCK[$t] globalinteractionsequence)
;

globalinteractionsequence:
  globalinteraction*
->
  ^(GACTIONSEQ globalinteraction*)
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
-> ^(GMSGTRANSFER message rolenamenode+)
;
// TODO: multisend

message:
  messagesignature   // Sig literal
| ambiguousnamenode  // Sig name or sig param name
;  
// TODO: qualified (messagesig) names  // Although qualified messagesignaturename subsumes parametername case

globalconnect:
   message CONNECT_KW rolenamenode TO_KW rolenamenode ';'
-> ^(GCONNECT message rolenamenode rolenamenode)
 | t=CONNECT_KW rolenamenode TO_KW rolenamenode ';'
-> ^(GCONNECT[$t] ^(SIG_LIT ^(EMPTY_OP) ^(PAYELEM_LIST))
   rolenamenode rolenamenode)
      // CHECKME: deprecate? i.e., require "()" as for message transfers?  i.e., simply delete this rule?
;


globaldisconnect:
   t=DISCONNECT_KW rolenamenode AND_KW rolenamenode ';'
-> ^(GDCONN[$t] rolenamenode rolenamenode)
;

globalwrap:
   t=WRAP_KW rolenamenode TO_KW rolenamenode ';'
-> ^(GWRAP[$t] rolenamenode rolenamenode)
;


/**
 * Section 3.7.5 Global Choice
 */
globalchoice:
   t=CHOICE_KW AT_KW rolenamenode globalprotocolblock (OR_KW
   globalprotocolblock)*
-> ^(GCHOICE[$t] rolenamenode globalprotocolblock+)
;


/**
 * Section 3.7.6 Global Recursion
 */
globalrecursion:
   t=REC_KW recursionvarnamenode globalprotocolblock
-> ^(GRECURSION[$t] recursionvarnamenode globalprotocolblock)
;

globalcontinue:
   t=CONTINUE_KW recursionvarnamenode ';'
-> ^(GCONTINUE recursionvarnamenode)
;


/**
 * Section 3.7.9 Global Do
 */
globaldo:
   DO_KW gprotocolnamenode argumentinstantiationlist roleinstantiationlist ';'
-> ^(GDO gprotocolnamenode argumentinstantiationlist roleinstantiationlist)
;

roleinstantiationlist:
   t='(' roleinstantiation (',' roleinstantiation)* ')'
-> ^(ROLEARG_LIST[$t] roleinstantiation+)
;

roleinstantiation: rolenamenode -> ^(ROLEARG rolenamenode) ;
    // CHECKME: inconsistent with arginstas/payloadeles

argumentinstantiationlist:
-> ^(ARG_LIST)
|  t='<' argumentinstantiation (',' argumentinstantiation)* '>'
-> ^(ARG_LIST[$t] argumentinstantiation+)
;

// Grammatically same as message, but qualifiedname case may also be a payload type
argumentinstantiation:
   messagesignature
-> ^(ARG messagesignature)
|  qualifiedname
-> { parseNonRoleArg($qualifiedname.tree) }  // Like PayloadElement, simple names need disambiguation
;

