/**
 * N.B. in Eclipse do Package Explorer, right click -> Open With -> Java Editor at least once for .g file association and syntax highlighting to work properly
 * 
 *  > scribble-java
 * $ java -cp scribble-parser/lib/antlr-3.5.2-complete.jar org.antlr.Tool -o scribble-parser/target/generated-sources/antlr3 scribble-parser/src/main/antlr3/org/scribble/parser/antlr/Scribble.g
 * 
 * Cygwin/Windows
 * > scribble-java
 * $ java -cp scribble-parser/lib/antlr-3.5.2-complete.jar org.antlr.Tool -o scribble-parser/target/generated-sources/antlr3/org/scribble/parser/antlr scribble-parser/src/main/antlr3/org/scribble/parser/antlr/Scribble.g
 * $ mv scribble-parser/target/generated-sources/antlr3/org/scribble/parser/antlr/Scribble.tokens scribble-parser/target/generated-sources/antlr3/
 */

/* * -- ** not allowed here.....
 * Pattern: most nodes have "imaginary token types".  
 * Where token attributes are not "inherited" from a concrete token, the default is to use textual name of the token type field as its text
 * e.g., gprotodecl: ... -> ^(GPROTODECL ...).  I.e., each token will be equivalent to, e.g., new CommonToken(ScribbleParser.GPROTODECL, "GPROTODECL").
 * The actual info of every imaginary node is in its children (i.e., its token text can be disregarded).
 * The exceptions are for ID/EXTID, where the text is the ID value, i.e., new CommonToken(ScribbleParser.ID, "...").
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

  // Special cases
  EMPTY_OP = '__EMPTY_OP';

	// Simple names "constructed directly", e.g., t=ID -> ID<...Node>[$t] 

	// Compound names
  GPROTO_NAME = 'GPROTO_NAME';  // Parse specifically as GProto, for ScribTreeAdaptor.create
  LPROTO_NAME = 'LPROTO_NAME';
  MODULE_NAME = 'MODULE_NAME';
  DATA_NAME = 'DATA_NAME';   // N.B. distinct from DATAPARAM_NAME
  SIG_NAME = 'SIG_NAME';   // N.B. distinct from SIGPARAM_NAME

	// Sig literals
  SIG_LIT = 'SIG_LIT';
  PAYELEM_LIST = 'PAYELEM_LIST';
  UNARY_PAYELEM = 'UNARY_PAYELEM';
  GDELEG_PAYELEM = 'GDELEG_PAYELEM';

	// Scribble "language" nodes, i.e., the nodes that are not "session nodes" (see below)
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
  DATAPARAMDECL = 'DATAPARAMDECL';
  SIGPARAMDECL = 'SIGPARAMDECL';
  
  GPROTODEF = 'GPROTODEF';
  GPROTOBLOCK = 'GPROTOBLOCK';
  
 	// Scribble "session nodes" -- cf. org.scribble.core.type.session vs. org.scribble.core.lang
  GINTERSEQ = 'GINTERSEQ';

  GMSGTRANSFER = 'GMSGTRANSFER';
  GCONNECT = 'GCONNECT';
  GDCONN = 'GDCONN';  // TODO: rename GDISCONN
  GWRAP = 'GWRAP';

  GCONTINUE = 'GCONTINUE';
  GDO = 'GDO';

  ROLEARG_LIST = 'ROLEARG_LIST';  // Cf. ROLEDECL
  ROLEARG = 'ROLEARG';
  NONROLEARG_LIST = 'NONROLEARG_LIST';  // Cf. ...PARAMDECL
  NONROLEARG = 'NONROLEARG';

  GCHOICE = 'GCHOICE';
  GRECURSION = 'GRECURSION';

  // Locals: currently not directly parsed, but needed for, e.g., projection
  LPROTODECL = 'LPROTODECL';

  LPROTOHEADER = 'LPROTOHEADER';
  LSELFROLEDECL = 'LSELFROLEDECL';
  
  LPROTODEF = 'LPROTODEF';
  LPROTOBLOCK = 'LPROTOBLOCK';
  
  LINTERSEQ = 'LINTERSEQ';

  LSEND = 'LSEND';
  LRECV = 'LRECV';
  LACC = 'LACC';
  LREQ = 'LREQ';
  LDCONN = 'LDCONN';
  LCLIENTWRAP = 'LCLIENTWRAP';
  LSERVERWRAP = 'LSERVERWRAP';

  LCONTINUE = 'LCONTINUE';
  LDO = 'LDO';

  LCHOICE = 'LCHOICE';
  LRECURSION = 'LRECURSION';
}


@lexer::header
{
  package org.scribble.parser.antlr;
}


@lexer::members
{
  @Override    
  public void displayRecognitionError(String[] tokenNames, 
  		RecognitionException e)
  {
    super.displayRecognitionError(tokenNames, e);
    System.exit(1);
  }
}


// Must come after tokens?
@parser::header
{
  package org.scribble.parser.antlr;
  
  import org.scribble.ast.NonRoleArg;
  import org.scribble.ast.ScribNodeBase;
  import org.scribble.ast.UnaryPayElem;
  import org.scribble.ast.name.simple.AmbigNameNode;
  import org.scribble.ast.name.simple.IdNode;
  import org.scribble.ast.name.simple.OpNode;
  import org.scribble.ast.name.simple.RecVarNode;
  import org.scribble.ast.name.simple.RoleNode;
  import org.scribble.ast.name.simple.SigParamNode;
  import org.scribble.ast.name.simple.DataParamNode;
  import org.scribble.ast.name.qualified.DataNameNode;
}


@parser::members
{
  // Abort tool run on parsing errors (and display user-friendly message) -- obsoletes CommonErrorNode check?
  @Override    
  public void displayRecognitionError(String[] tokenNames, 
  		RecognitionException e)
  {
    super.displayRecognitionError(tokenNames, e);
    System.exit(1);
  }

	// Currently unused -- TODO: check later in intermed translation, instead of parsing
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



/****************************************************************************
 * Chapter 2 Lexical Structure (Lexer rules)
 ***************************************************************************/

/* *  // Double star here not accepted by ANTLR...
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
ID:
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
EXTID:
	'\"' (LETTER | DIGIT | SYMBOL)* '\"'
;
 //(LETTER | DIGIT | SYMBOL)*  // Not working

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

/* * // Double star here not accepted by ANTLR...
 * Section 3.1 Primitive Names
 */
//simplename: id=ID -> { checkId($id.tree) } ;  // How to integrate with ID<RoleNode>[$t] ?

// "The TreeAdaptor is not called; instead [the] constructors are invoked directly."
// "Note that parameters are not allowed on token references to the left of ->:"
// "Use imaginary nodes as you normally would, but with the addition of the node type:"  // But currently, ID token itself unchanged and ttype int ends up discarded
ambigname: t=ID -> ID<AmbigNameNode>[$t] ;
dataparamname: t=ID -> ID<DataParamNode>[$t] ; 
opname: -> ^(EMPTY_OP) | t=ID -> ID<OpNode>[$t] ;
recvarname: t=ID -> ID<RecVarNode>[$t] ;
rolename: t=ID -> ID<RoleNode>[$t] ;
sigparamname: t=ID -> ID<SigParamNode>[$t] ;


/**
 * Section 3.2.1 Package, Module and Module Member Names
 */
// May be compound or simple
gprotoname: t=ID ('.' ID)* -> ^(GPROTO_NAME[$t] ID+) ;
modulename: t=ID ('.' ID)* -> ^(MODULE_NAME[$t] ID+) ;

// Compound only (cf., e.g., gprotoname; cf. simpledataname)
qualifieddataname: t=ID '.' ID ('.' ID)* -> ^(DATA_NAME[$t] ID+) ;

// Cf. primitive names, above
simpledataname: t=ID -> ^(DATA_NAME[$t] ID) ;
simplegprotoname: t=ID -> ^(GPROTO_NAME[$t] ID) ;
simplemodulename: t=ID -> ^(MODULE_NAME[$t] ID) ;
simplesigname: t=ID -> ^(SIG_NAME[$t] ID) ;


/**
 * Section 3.2.2 Top-level Module Structure
 * Section 3.2.3 Module Declarations
 */
// "References to tokens with rewrite not found on left of -> are imaginary tokens."
// Inlined moduledecl to make token label work
module:
	t=MODULE_KW modulename ';' importmodule* nonprotodecl* protodecl* EOF
->
	^(MODULE[$t] ^(MODULEDECL[$t] modulename) importmodule* nonprotodecl*
	protodecl*)
;
// moduledecl: MODULE_KW<ModuleDecl>^ modulename ';'  
		// "Become root" ^ cannot be on rhs? -- so "manually" rewrite to Scribble AST token types


/**
 * Section 3.3 Import Declarations
 */
importmodule:
	t=IMPORT_KW modulename (AS_KW alias=simplemodulename)? ';'
->
	^(IMPORTMODULE[$t] modulename $alias?)
;


/**
 * Section 3.4 "Non Protocol" Declarations 
 */
nonprotodecl:
	datadecl | sigdecl ;

datadecl:
	// Deprecate TYPE_KW ?
	t=TYPE_KW '<' schema=ID '>' extName=EXTID FROM_KW
	extSource=EXTID AS_KW alias=simpledataname ';'
->
	// alias first to be uniform with other NameDeclNode (getRawNameNodeChild)
	^(DATADECL[$t] $alias $schema $extName $extSource)
|
	// CHECKME: duplicated above, because t=(TYPE_KW | DATA_KW) *sometimes* causes null token NPEs... 
	t=DATA_KW '<' schema=ID '>' extName=EXTID FROM_KW
	extSource=EXTID AS_KW alias=simpledataname ';'
->
	// alias first to be uniform with other NameDeclNode (getRawNameNodeChild)
	^(DATADECL[$t] $alias $schema $extName $extSource)
;

sigdecl:
	t=SIG_KW '<' schema=ID '>' extName=EXTID FROM_KW extSource=EXTID AS_KW
	alias=simplesigname ';'
->
	// alias first to be uniform with other NameDeclNode (getRawNameNodeChild)
	^(SIGDECL[$t] $alias $schema $extName $extSource)
;


/**
 * Section 3.5 Message Signatures
 */
siglit:
	opname '(' payelems ')' -> ^(SIG_LIT opname payelems)
;
// CHECKME: how to apply [$t] in such situations?

payelems:
	-> ^(PAYELEM_LIST)
|
	payelem (',' payelem)* -> ^(PAYELEM_LIST payelem+)
;
	
payelem:
	// Payload element must be a data kind, cannot be a sig name
	// Qualified name must be a data type name
	// Also subsumes simple names, could be a data *param*
	gprotoname '@' rolename -> ^(GDELEG_PAYELEM gprotoname rolename)
|
	ambigname -> ^(UNARY_PAYELEM ambigname)
|
	qualifieddataname -> ^(UNARY_PAYELEM qualifieddataname)	
;
//	{ parsePayloadElem($qualifiedname.tree) }  // Use ".text" instead of ".tree" for token String 
	



/**
 * Section 3.6 Protocol Declarations
 */
protodecl:
	gprotodecl ;


/**
 * Section 3.7 Global Protocol Declarations
 */
gprotodecl:
	protomods gprotoheader gprotodef
->
	^(GPROTODECL protomods gprotoheader gprotodef)
;
  
// "aux" must come before "explicit"
protomods:
                       -> ^(PROTOMOD_LIST)
| t=AUX_KW             -> ^(PROTOMOD_LIST[$t] AUX_KW)
| t=AUX_KW EXPLICIT_KW -> ^(PROTOMOD_LIST[$t] AUX_KW EXPLICIT_KW)
| t=EXPLICIT_KW        -> ^(PROTOMOD_LIST[$t] EXPLICIT_KW)
;

gprotoheader:
	t=GLOBAL_KW PROTOCOL_KW simplegprotoname paramdecls roledecls
->
	^(GPROTOHEADER[$t] simplegprotoname paramdecls roledecls)
;
// N.B. intermed translation uses full proto name

roledecls: 
	t='(' roledecl (',' roledecl)* ')' -> ^(ROLEDECL_LIST[$t] roledecl+) ;

roledecl:
	t=ROLE_KW rolename -> ^(ROLEDECL[$t] rolename) ;

paramdecls:
	-> ^(PARAMDECL_LIST)
|
	t='<' (paramdecl (',' paramdecl)*)? '>' -> ^(PARAMDECL_LIST[$t] paramdecl*)
;

paramdecl: dataparamdecl | sigparamdecl ;

dataparamdecl: 
	t=TYPE_KW dataparamname -> ^(DATAPARAMDECL[$t] dataparamname)
|
	t=DATA_KW dataparamname -> ^(DATAPARAMDECL[$t] dataparamname)
			// TODO: refactor -- cf. datadecl
;

sigparamdecl:  
	t=SIG_KW sigparamname -> ^(SIGPARAMDECL[$t] sigparamname) ;


/**
 * Section 3.7.1 Global Protocol Definitions
 */
gprotodef:
	gprotoblock -> ^(GPROTODEF gprotoblock) ;


/**
 * Section 3.7.3 Global Interaction Sequences and Blocks
 */
gprotoblock:
	t='{' gseq '}' -> ^(GPROTOBLOCK[$t] gseq)
;

gseq:
	ginteraction* -> ^(GINTERSEQ ginteraction*)
;

ginteraction:
	// Simple session node: directed interaction
  gconnect | gmsgtransfer

	// Simple session node: basic interaction
	| gwrap | gdisconnect 

	// Simple session node (other)
	| gcontinue | gdo 

	// Compound session node
	| gchoice | grecursion
; 


/**
 * Section 3.7.4 Global Message Transfer
 */
message:
	siglit | ambigname  // ambigname = sig name or sig param name
;  

// TODO: qualified (messagesig) names -- although qualified signame subsumes parametername case
gmsgtransfer:
	message FROM_KW rolename TO_KW rolename (',' rolename )* ';'
->
	^(GMSGTRANSFER message rolename+)
;
// TODO: multisend

gconnect:
	message CONNECT_KW rolename TO_KW rolename ';'
->
	^(GCONNECT message rolename rolename)
|
	t=CONNECT_KW rolename TO_KW rolename ';'
->
	^(GCONNECT[$t] ^(SIG_LIT ^(EMPTY_OP) ^(PAYELEM_LIST)) rolename rolename)
      // CHECKME: deprecate? i.e., require "()" as for message transfers?  i.e., simply delete this rule?
;

gdisconnect:
	t=DISCONNECT_KW rolename AND_KW rolename ';'
->
	^(GDCONN[$t] rolename rolename)
;

gwrap:
	t=WRAP_KW rolename TO_KW rolename ';'
->
	^(GWRAP[$t] rolename rolename)
;


/**
 * Section 3.7.5 Global Choice
 */
gchoice:
	t=CHOICE_KW AT_KW rolename gprotoblock (OR_KW gprotoblock)*
->
	^(GCHOICE[$t] rolename gprotoblock+)
;


/**
 * Section 3.7.6 Global Recursion
 */
grecursion:
	t=REC_KW recvarname gprotoblock
->
	^(GRECURSION[$t] recvarname gprotoblock)
;

gcontinue:
	t=CONTINUE_KW recvarname ';'
->
	^(GCONTINUE[$t] recvarname)
;


/**
 * Section 3.7.9 Global Do
 */
gdo:
	DO_KW gprotoname nonroleargs roleargs ';'
->
	^(GDO gprotoname nonroleargs roleargs)
;

roleargs:
	t='(' rolearg (',' rolearg)* ')' -> ^(ROLEARG_LIST[$t] rolearg+)
;

rolearg:
	rolename -> ^(ROLEARG rolename) ;

nonroleargs:
	-> ^(NONROLEARG_LIST)
|
	t='<' (nonrolearg (',' nonrolearg)*)? '>' -> ^(NONROLEARG_LIST[$t] nonrolearg*)
;

// Grammatically same as message, but qualifiedname case may also be a payload type
nonrolearg:
	siglit -> ^(NONROLEARG siglit)
|
	ambigname -> ^(NONROLEARG ambigname)
|
	qualifieddataname -> ^(NONROLEARG qualifieddataname)  // FIXME: sig name -- need an ambig qualified name
;
//	{ parseNonRoleArg($qualifiedname.tree) }  // Like payelem, simple names need disambiguation























/*
@parser::members
{
  // qn is an IdNode "holder" for a "qualifiedname" COMPOUND_NAME -- see ScribTreeAdaptor
  // CHECKME: do the returns of these "bypass" ScribTreeAdaptor?  specifically AmbigNode
	//	{ parsePayloadElem($qualifiedname.tree) }  // Use ".text" instead of ".tree" for token String 
  public static CommonTree parsePayloadElem(CommonTree qn) throws RecognitionException
  {
    if (qn.getChildCount() > 1)  // qn has IdNode children, elements of the qualifiedname
    {
			// Cf. AstFactoryImpl, token creation
      DataNameNode dt = new DataNameNode(new CommonToken(DATA_NAME, "DATA_NAME"));
      ((List<?>) qn.getChildren()).forEach(x -> 
          dt.addChild(new IdNode(new CommonToken(ID, ((CommonTree) x).getText()))));
      UnaryPayElem pe = 
          new UnaryPayElem(new CommonToken(UNARY_PAYELEM, "UNARYPAYLOADELEM"));
      pe.addChild(dt);
      return pe;
    }
    else //if (qn.getChildCount() == 1)
    {
      // Similar to NonRoleArg: cannot syntactically distinguish right now between a simple data name and a param name
			// Cf. AstFactoryImpl, token creation
      String text = qn.getChild(0).getText();
      AmbigNameNode an = 
          new AmbigNameNode(AMBIG_NAME, new CommonToken(ID, text));
      UnaryPayElem e = new UnaryPayElem(
          new CommonToken(UNARY_PAYELEM, "UNARYPAYLOADELEM"));
      e.addChild(an);
      return e;
    }
  }

  // qn is an IdNode "holder" for a "qualifiedname" COMPOUND_NAME -- see ScribTreeAdaptor
  // Only for "qualifiedName" (DataNameNode or AmbigNameNode), not sig literals
	// Use by: { parseNonRoleArg($qualifiedname.tree) }
  public static CommonTree parseNonRoleArg(CommonTree qn) throws RecognitionException
  {
    if (qn.getChildCount() > 1)  // qn has IdNode children, elements of the qualifiedname
    {
			// Cf. AstFactoryImpl, token creation
      DataNameNode dt =
      		new DataNameNode(new CommonToken(DATA_NAME, "DATA_NAME"));  // FIXME: could be a sig name arg...
      ((List<?>) qn.getChildren()).forEach(x -> 
          dt.addChild(new IdNode(new CommonToken(ID, ((CommonTree) x).getText()))));
      NonRoleArg a = 
          new NonRoleArg(new CommonToken(ARG, "ARG"));
      a.addChild(dt);
      return a;
    }
    else //if (qn.getChildCount() == 1)
    {
			// Cf. AstFactoryImpl, token creation
      String text = qn.getChild(0).getText();
      AmbigNameNode an = 
          new AmbigNameNode(AMBIG_NAME, new CommonToken(ID, text));
      NonRoleArg a = new NonRoleArg(new CommonToken(ARG, "ARG"));
      a.addChild(an);
      return a;
    }
  }
}
//*/