/*
 * > scribble-java
 * $ java -cp scribble-parser/lib/antlr-3.5.2-complete.jar org.antlr.Tool -o scribble-parser/target/generated-sources/antlr3 scribble-parser/src/main/antlr3/org/scribble/parser/antlr/Scribble.g
 * 
 * Cygwin/Windows
 * $ java -cp scribble-parser/lib/antlr-3.5.2-complete.jar org.antlr.Tool -o scribble-parser/target/generated-sources/antlr3/org/scribble/parser/antlr scribble-parser/src/main/antlr3/org/scribble/parser/antlr/Scribble.g
 * $ mv scribble-parser/target/generated-sources/antlr3/org/scribble/parser/antlr/Scribble.tokens scribble-parser/target/generated-sources/antlr3/
 */


grammar Scribble;


options
{
	language = Java;
	output = AST;
	ASTLabelType = CommonTree;
	//backtrack = true;  // backtracking disabled by default? Is it bad to require this option?
	//memoize = true;
}


tokens
{
	/*
	 * Parser input constants (lexer output; keywords, Section 2.4)
	 */

	MODULE_KW = 'module';
	IMPORT_KW = 'import';
	TYPE_KW = 'type';
	PROTOCOL_KW = 'protocol';
	GLOBAL_KW = 'global';
	LOCAL_KW = 'local';
	EXPLICIT_KW = 'explicit';
	AUX_KW = 'aux';
	ROLE_KW = 'role';
	ACCEPT_KW = 'accept';
	SELF_KW = 'self';
	SIG_KW = 'sig';
	//INSTANTIATES_KW = 'instantiates';
	AS_KW = 'as';

	CONNECT_KW = 'connect';
	DISCONNECT_KW = 'disconnect';
	WRAP_KW = 'wrap';
	FROM_KW = 'from';
	TO_KW = 'to';
	CHOICE_KW = 'choice';
	AT_KW = 'at';
	OR_KW = 'or';
	REC_KW = 'rec';
	CONTINUE_KW = 'continue';
	//PAR_KW = 'par';
	AND_KW = 'and';  // Needed for disconnect
	/*INTERRUPTIBLE_KW = 'interruptible';
	WITH_KW = 'with';
	BY_KW = 'by';  /* from for interrupts is more expected, but from is
	                 not good for multiple roles (generally, the comma
	                 in interrupt message list and role list looks like
	                 "and" rather than "or") * /
	THROWS_KW = 'throws';
	CATCHES_KW = 'catches';*/
	DO_KW = 'do';
	//SPAWN_KW = 'spawn';
	

	/*
	 * Parser output "node types" (corresponding to the various syntactic
	 * categories) i.e. the labels used to distinguish resulting AST nodes.
	 * The value of these token variables doesn't matter, only the token
	 * (i.e. variable) names themselves are used (for AST node root text
	 * field)
	 */
	
	// Purely util constants -- not parsed as node types

	KIND_MESSAGESIGNATURE = 'KIND_MESSAGESIGNATURE';
	KIND_PAYLOADTYPE = 'KIND_PAYLOADTYPE';
	
	
	// "Node type" constants -- but not parsed "directly" by AntlrToScribParser

	EMPTY_ALIAS = 'EMTPY_ALIAS';
	/*EMPTY_SCOPENAME = '__empty_scopename';
	NO_SCOPE = '__no_scope';*/
	//EMPTY_PACKAGENAME = '__empty_packagebame';
	EMPTY_OPERATOR = 'EMPTY_OPERATOR';

	//EMPTY_PARAMETERDECLLIST = '__empty_parameterdecllist';
	//EMPTY_ARGUMENTINSTANTIATIONLIST = '__empty_argumentinstantiationlist';
	/*EMPTY_LOCALTHROW = '__empty_localthrow';
	EMPTY_LOCAL_CATCHES = '__empty_local_catch';*/
	
	//NAME = 'name';
	AMBIGUOUSNAME = 'AMBIGUOUSNAME';
	QUALIFIEDNAME = 'QUALIFIEDNAME';
	//PACKAGENAME = 'package-name';
	//FULLMODULENAME = 'full-module-name';
	//SIMPLEMEMBERNAME = 'simple-member-name';
	//QUALIFIEDMEMBERNAME = 'qualified-member-name';

	MESSAGESIGNATURE = 'MESSAGESIGNATURE';
	DELEGATION = 'DELEGATION';
	

	// Parsed "directly" by AntlrToScribParser

	PAYLOAD = 'PAYLOAD';
	//PAYLOADELEMENT = 'payloadelement';

	//MODULE = 'module';  // Probably a keyword clash
	MODULE = 'MODULE';
	//PACKAGEDECL = 'package-decl';
	MODULEDECL = 'MODULEDECL';
	//IMPORTDECL = 'import-decl';
	//FROMIMPORTDECL = 'from-import-decl';
	IMPORTMODULE = 'IMPORTMODULE';
	IMPORTMEMBER = 'IMPORTMEMBER';
	PAYLOADTYPEDECL = 'PAYLOADTYPEDECL';
	MESSAGESIGNATUREDECL = 'MESSAGESIGNATUREDECL';
	ROLEDECLLIST = 'ROLEDECLLIST';
	ROLEDECL = 'ROLEDECL';
	PARAMETERDECLLIST = 'PARAMETERDECLLIST';
	PARAMETERDECL = 'PARAMETERDECL';
	ROLEINSTANTIATIONLIST = 'ROLEINSTANTIATIONLIST';
	ROLEINSTANTIATION = 'ROLEINSTANTIATION';  // FIXME: not consistent with arginstas/payloadeles
	ARGUMENTINSTANTIATIONLIST = 'ARGUMENTINSTANTIATIONLIST';
	//ARGUMENTINSTANTIATION = 'argument-instantiation';
	//CONNECTDECL = 'connect-decl';

	GLOBALPROTOCOLDECL = 'GLOBALPROTOCOLDECL';
	GLOBALPROTOCOLDECLMODS = 'GLOBALPROTOCOLDECLMODS';
	GLOBALPROTOCOLHEADER = 'GLOBALPROTOCOLHEADER';
	GLOBALPROTOCOLDEF = 'GLOBALPROTOCOLDEF';
	GLOBALPROTOCOLBLOCK = 'GLOBALPROTOCOLBLOCK';
	GLOBALINTERACTIONSEQUENCE = 'GLOBALINTERACTIONSEQUENCE';
	GLOBALMESSAGETRANSFER = 'GLOBALMESSAGETRANSFER';
	GLOBALCONNECT = 'GLOBALCONNECT';
	GLOBALDISCONNECT = 'GLOBALDISCONNECT';
	GLOBALWRAP = 'GLOBALWRAP';
	GLOBALCHOICE = 'GLOBALCHOICE';
	GLOBALRECURSION = 'GLOBALRECURSION';
	GLOBALCONTINUE = 'GLOBALCONTINUE';
	/*GLOBALPARALLEL = 'GLOBALPARALLEL';
	GLOBALINTERRUPTIBLE = 'GLOBALINTERRUPTIBLE';
	GLOBALINTERRUPT = 'GLOBALINTERRUPT';*/
	GLOBALDO = 'GLOBALDO';

	/*LOCALPROTOCOLDECL = 'local-protocol-decl';
	LOCALROLEDECLLIST = 'local-role-decl-list';
	LOCALROLEDECL = 'local-role-decl';
	SELFDECL = 'self-decl';
	LOCALPROTOCOLDEF = 'local-protocol-def';
	LOCALPROTOCOLINSTANCE = 'local-protocol-instance';
	LOCALPROTOCOLBLOCK = 'local-protocol-block';
	LOCALINTERACTIONSEQUENCE = 'local-interaction-sequence';
	LOCALMESSAGETRANSFER = 'local-message-transfer';
	LOCALCHOICE = 'local-choice';
	LOCALRECURSION = 'local-recursion';
	LOCALCONTINUE = 'local-continue';
	LOCALPARALLEL = 'local-parallel';
	LOCALINTERRUPTIBLE = 'local-interruptible';
	LOCALINTERRUPT = 'local-interrupt';
	LOCALDO = 'local-do';
	LOCALTHROWS = 'local-throws';
	LOCALCATCHES = 'local-catches';
	LOCALSEND = 'local-send';
	LOCALRECEIVE = 'local-receive';*/
}


// Has to come after tokens?
@parser::header
{
	package org.scribble.parser.antlr;
	
	//import org.scribble.main.RuntimeScribbleException;
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
			/* Underscore currently can cause ambiguities in the API generation naming scheme
			 * But maybe only consecutive underscores are the problem
			 * -- cannot completely disallow underscores as needed for projection naming scheme
			 * Or disallow underscores only for role/op/messagesig names
			 */
//	(LETTER | DIGIT)*
;

fragment SYMBOL:
	'{' | '}' | '(' | ')' | '[' | ']' | ':' | '/' | '\\' | '.' | '\#'
|
	'&' | '?' | '!'	| UNDERSCORE
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

simplename:
	IDENTIFIER
/*->
	^(SIMPLENAME IDENTIFIER)*/
;

//annotationname:   simplename;
parametername:    simplename;
recursionvarname: simplename;
rolename:         simplename;
scopename:        simplename;

ambiguousname:
	simplename
->
	^(AMBIGUOUSNAME simplename)
;


/**
 * Section 3.2.1 Package, Module and Module Member Names
 */

simplemodulename:           simplename;
simplepayloadtypename:      simplename;
simplemessagesignaturename: simplename;
simpleprotocolname:         simplename;
simplemembername:           simplename;  // Only for member declarations

qualifiedname:
	IDENTIFIER ('.' IDENTIFIER)*
->
	^(QUALIFIEDNAME IDENTIFIER+)
;

packagename:          qualifiedname;
modulename:           qualifiedname;
membername:           qualifiedname;

protocolname:         membername;
payloadtypename:      membername;
messagesignaturename: membername;


/**
 * Section 3.2.2 Top-level Module Structure
 */
module:
	moduledecl importdecl* datatypedecl* protocoldecl* EOF
->
	^(MODULE moduledecl importdecl* datatypedecl* protocoldecl*)
;


/**
 * Section 3.2.3 Module Declarations
 */
moduledecl:
	MODULE_KW modulename ';'
->
	^(MODULEDECL modulename)
;


/**
 * Section 3.3 Import Declarations
 */
importdecl:
	importmodule
|
	importmember
;

importmodule:
	IMPORT_KW modulename ';'
->
	^(IMPORTMODULE modulename EMPTY_ALIAS)
|
	IMPORT_KW modulename AS_KW simplemodulename ';'
->
	^(IMPORTMODULE modulename simplemodulename)
;

importmember:
	FROM_KW modulename IMPORT_KW simplemembername ';'
->
	^(IMPORTMEMBER modulename simplemembername EMPTY_ALIAS)
|
	FROM_KW modulename IMPORT_KW simplemembername AS_KW simplemembername ';'
->
	^(IMPORTMEMBER modulename simplemembername simplemembername)
;


/**
 * //Section 3.4 Payload Type Declarations
 * Data Declarations
 */
datatypedecl:
	payloadtypedecl
|
	messagesignaturedecl
;

payloadtypedecl:
	TYPE_KW '<' IDENTIFIER '>' EXTIDENTIFIER FROM_KW EXTIDENTIFIER AS_KW simplepayloadtypename ';'
->
	^(PAYLOADTYPEDECL IDENTIFIER EXTIDENTIFIER EXTIDENTIFIER simplepayloadtypename)
;

messagesignaturedecl:
	SIG_KW '<' IDENTIFIER '>' EXTIDENTIFIER FROM_KW EXTIDENTIFIER AS_KW simplemessagesignaturename ';'
->
	^(MESSAGESIGNATUREDECL IDENTIFIER EXTIDENTIFIER EXTIDENTIFIER simplemessagesignaturename)
;


/**
 * Section 3.5 Message Signatures
 */
/*messageoperator:
	IDENTIFIER
;*/

messagesignature:
	'(' payload ')'
->
	^(MESSAGESIGNATURE EMPTY_OPERATOR payload)
|
	//messageoperator '(' payload ')'  // Doesn't work (conflict with IDENTIFIER?)
	IDENTIFIER '(' payload ')'
->
	^(MESSAGESIGNATURE IDENTIFIER payload)
|
	'(' ')'
->
	^(MESSAGESIGNATURE EMPTY_OPERATOR ^(PAYLOAD))
|
	IDENTIFIER '(' ')'
->
	^(MESSAGESIGNATURE IDENTIFIER ^(PAYLOAD))
;

payload:
	payloadelement (',' payloadelement)*
->
	^(PAYLOAD payloadelement+)
;

// Payload type names need disambiguation pass (also do args)
payloadelement:
/*	ambiguousname  // Parser doesn't distinguish simple from qualified properly, even with backtrack
|*/
	qualifiedname  // This case subsumes simple names  // FIXME: ambiguousqualifiedname (or ambiguousname should just be qualified)
|
	protocolname '@' rolename
->
	^(DELEGATION rolename protocolname)
;


/**
 * Section 3.6 Protocol Declarations
 */
protocoldecl:
	globalprotocoldecl
/*|
	localprotocoldecl*/
;


/**
 * Section 3.7 Global Protocol Declarations
 */
globalprotocoldecl:
	globalprotocolheader globalprotocoldefinition
->
	^(GLOBALPROTOCOLDECL globalprotocolheader globalprotocoldefinition)
|
	globalprotocoldeclmodifiers globalprotocolheader globalprotocoldefinition  // HACK: backwards compat for "implicit" connections 
->
	^(GLOBALPROTOCOLDECL globalprotocolheader globalprotocoldefinition globalprotocoldeclmodifiers)
;
	
globalprotocoldeclmodifiers:
	AUX_KW EXPLICIT_KW 
->
	^(GLOBALPROTOCOLDECLMODS AUX_KW EXPLICIT_KW)
|
	EXPLICIT_KW
->
	^(GLOBALPROTOCOLDECLMODS EXPLICIT_KW)
|
	AUX_KW
->
	^(GLOBALPROTOCOLDECLMODS AUX_KW)
;

globalprotocolheader:
	GLOBAL_KW PROTOCOL_KW simpleprotocolname roledecllist
->
	^(GLOBALPROTOCOLHEADER simpleprotocolname ^(PARAMETERDECLLIST) roledecllist)
|
	GLOBAL_KW PROTOCOL_KW simpleprotocolname parameterdecllist roledecllist
->
	^(GLOBALPROTOCOLHEADER simpleprotocolname parameterdecllist roledecllist)
;

roledecllist:
	'(' roledecl (',' roledecl)* ')'
->
	^(ROLEDECLLIST roledecl+)
;

roledecl:
	ROLE_KW rolename
->
	^(ROLEDECL rolename)
;

parameterdecllist:
	'<' parameterdecl (',' parameterdecl)* '>'
->
	^(PARAMETERDECLLIST parameterdecl+)
;

parameterdecl:
	 TYPE_KW parametername
->
	^(PARAMETERDECL KIND_PAYLOADTYPE parametername)
|
	 SIG_KW parametername
->
	^(PARAMETERDECL KIND_MESSAGESIGNATURE parametername)
;


/**
 * Section 3.7.1 Global Protocol Definitions
 */
globalprotocoldefinition:
	globalprotocolblock
->
	^(GLOBALPROTOCOLDEF globalprotocolblock)
;


/**
 * Section 3.7.3 Global Interaction Sequences and Blocks
 */
globalprotocolblock:
	'{' globalinteractionsequence '}'
->
	^(GLOBALPROTOCOLBLOCK globalinteractionsequence)
/*|
	'(' connectdecl ')' '{' globalinteractionsequence '}'
->
	^(GLOBALPROTOCOLBLOCK globalinteractionsequence connectdecl)*/
;

globalinteractionsequence:
	globalinteraction*
->
	^(GLOBALINTERACTIONSEQUENCE globalinteraction*)
;

globalinteraction:
	globalmessagetransfer
|
	globalchoice
|
	globalrecursion
|
	globalcontinue
|
	globaldo
|
	globalconnect
|
	globaldisconnect
|
	globalwrap
;
/*|
	globalparallel
|
	globalinterruptible*/


/**
 * Section 3.7.4 Global Message Transfer
 */
globalmessagetransfer:
	message FROM_KW rolename TO_KW rolename (',' rolename )* ';'
->
	^(GLOBALMESSAGETRANSFER message rolename rolename+)
;

message:
	messagesignature
|
	ambiguousname  // FIXME: qualified name
/*|
	messagesignaturename  // qualified messagesignaturename subsumes parametername case
|
	parametername*/
;	

globalconnect:
	//message CONNECT_KW rolename TO_KW rolename
	CONNECT_KW rolename TO_KW rolename ';'
->
	^(GLOBALCONNECT rolename rolename ^(MESSAGESIGNATURE EMPTY_OPERATOR ^(PAYLOAD)))  // Empty message sig duplicated from messagesignature
|
	message CONNECT_KW rolename TO_KW rolename ';'
->
	^(GLOBALCONNECT rolename rolename message)
;
/*	'(' connectdecl (',' connectdecl)* ')'
->
	^(CONNECTDECLLIST connectdecl+)
;* /
	'(' connectdecl ')' 
*/	

/*connectdecl:
	CONNECT_KW rolename '->>' rolename
->
	^(CONNECTDECL rolename rolename)
;*/

globaldisconnect:
	DISCONNECT_KW rolename AND_KW rolename ';'
->
	^(GLOBALDISCONNECT rolename rolename )
;

globalwrap:
	//message CONNECT_KW rolename TO_KW rolename
	WRAP_KW rolename TO_KW rolename ';'
->
	^(GLOBALWRAP rolename rolename)
;


/**
 * Section 3.7.5 Global Choice
 */
globalchoice:
	CHOICE_KW AT_KW rolename globalprotocolblock (OR_KW globalprotocolblock)*
->
	^(GLOBALCHOICE rolename globalprotocolblock+)
;


/**
 * Section 3.7.6 Global Recursion
 */
globalrecursion:
	REC_KW recursionvarname globalprotocolblock
->
	^(GLOBALRECURSION recursionvarname globalprotocolblock)
;

globalcontinue:
	CONTINUE_KW recursionvarname ';'
->
	^(GLOBALCONTINUE recursionvarname)
;


/*
 * Section 3.7.7 Global Parallel
 * /
globalparallel:
	PAR_KW globalprotocolblock (AND_KW globalprotocolblock)*
->
	^(GLOBALPARALLEL globalprotocolblock+)
;*/


/*
 * Section 3.7.8 Global Interruptible
 * /
globalinterruptible:
	INTERRUPTIBLE_KW globalprotocolblock WITH_KW '{' globalinterrupt* '}'
->
	^(GLOBALINTERRUPTIBLE EMPTY_SCOPENAME globalprotocolblock globalinterrupt*)
|
	INTERRUPTIBLE_KW scopename globalprotocolblock WITH_KW '{' (globalinterrupt)* '}'
->
	^(GLOBALINTERRUPTIBLE scopename globalprotocolblock globalinterrupt*)
;

globalinterrupt:
	message (',' message)* BY_KW rolename ';'
->
	^(GLOBALINTERRUPT rolename message+)
;*/


/**
 * Section 3.7.9 Global Do
 */
globaldo:
	DO_KW protocolname roleinstantiationlist ';'
->
	^(GLOBALDO protocolname ^(ARGUMENTINSTANTIATIONLIST) roleinstantiationlist)
|
	DO_KW protocolname argumentinstantiationlist roleinstantiationlist ';'
->
	^(GLOBALDO protocolname argumentinstantiationlist roleinstantiationlist)
;

roleinstantiationlist:
	'(' roleinstantiation (',' roleinstantiation)* ')'
->
	^(ROLEINSTANTIATIONLIST roleinstantiation+)
;

roleinstantiation:
	rolename
->
	^(ROLEINSTANTIATION rolename)  // FIXME: not consistent with arginstas/payloadeles
;

argumentinstantiationlist:
	'<' argumentinstantiation (',' argumentinstantiation)* '>'
->
	^(ARGUMENTINSTANTIATIONLIST argumentinstantiation+)
;

// Like PayloadElement, simple names need disambiguation
argumentinstantiation:
	//message
  // Grammatically same as message, but argument case can also be a payload type
	messagesignature
/*|
	ambiguousname  // As for payloadelement: parser doesn't distinguish simple from qualified properly, even with backtrack*/
|
	qualifiedname
;


/*
 * Section 3.8 Local Protocol Declarations
 * /
localprotocoldecl:
	localprotocolheader localprotocoldefinition
->
	^(LOCALPROTOCOLDECL localprotocolheader localprotocoldefinition)
;

localprotocolheader:
	LOCAL_KW PROTOCOL_KW simpleprotocolname localroledecllist
->
	//simpleprotocolname EMPTY_PARAMETERDECLLIST localroledecllist
	simpleprotocolname ^(PARAMETERDECLLIST) localroledecllist
|
	LOCAL_KW PROTOCOL_KW simpleprotocolname parameterdecllist localroledecllist
->
	simpleprotocolname parameterdecllist localroledecllist
;

localroledecllist:
	'(' localroledecl (',' localroledecl)* ')'
->
	^(LOCALROLEDECLLIST localroledecl+)
;

localroledecl:
	roledecl
|
	SELF_KW rolename
->
	^(SELFDECL rolename)
;


/**
 * Section 3.8.1 Local Protocol Definitions
 * /
localprotocoldefinition:
	localprotocolblock
->
	^(LOCALPROTOCOLDEF localprotocolblock)
;


/**
 * Section 3.8.3 Local Interaction Blocks and Sequences
 * /
localprotocolblock:
	'{' localinteractionsequence '}'
->
	^(LOCALPROTOCOLBLOCK localinteractionsequence)
;

localinteractionsequence:
	(localinteraction)*
->
	^(LOCALINTERACTIONSEQUENCE localinteraction*)
;

localinteraction:
	localsend
|
	localreceive
|
	localchoice
|
	localparallel
|
	localrecursion
|
	localcontinue
|
	localinterruptible
|
	localdo
;


/**
 * Section 3.8.4 Local Send and Receive
 * /
localsend:
	message TO_KW rolename (',' rolename)* ';'
->
	^(LOCALSEND message rolename+)
;

localreceive:
	message FROM_KW IDENTIFIER ';'
->
	^(LOCALRECEIVE message IDENTIFIER)
;


/**
 * Section 3.8.5 Local Choice
 * /
localchoice:
	CHOICE_KW AT_KW rolename localprotocolblock (OR_KW localprotocolblock)*
->
	^(LOCALCHOICE rolename localprotocolblock+)
;


/**
 * Section 3.8.6 Local Recursion
 * /
localrecursion:
	REC_KW recursionvarname localprotocolblock
->
	^(LOCALRECURSION recursionvarname localprotocolblock)
;

localcontinue:
	CONTINUE_KW recursionvarname ';'
->
	^(LOCALCONTINUE recursionvarname)
;


/**
 * Section 3.8.7 Local Parallel
 * /
localparallel:
	PAR_KW localprotocolblock (AND_KW localprotocolblock)*
->
	^(LOCALPARALLEL localprotocolblock+)
;


/**
 * Section 3.8.8 Local Interruptible
 * /
localinterruptible:
	INTERRUPTIBLE_KW scopename localprotocolblock WITH_KW '{' localcatches* '}'
->
	^(LOCALINTERRUPTIBLE scopename localprotocolblock EMPTY_LOCALTHROW localcatches*)
|
	INTERRUPTIBLE_KW scopename localprotocolblock WITH_KW '{' localthrows localcatches* '}'
->
	^(LOCALINTERRUPTIBLE scopename localprotocolblock localthrows localcatches*)
;

/*localthrowandorcatch:
	localthrow (localcatch)*
|
	(localcatch)+
;* /

localthrows:
	THROWS_KW message (',' message)* TO_KW rolename (',' rolename)* ';'
->
	^(LOCALTHROWS rolename+ TO_KW message+)
;

localcatches:
	CATCHES_KW message (',' message)* FROM_KW rolename ';'
->
	^(LOCALCATCHES rolename message+)
;


/**
 * Section 3.8.9 Local Do
 * /
localdo:
	DO_KW protocolname roleinstantiationlist ';'
->
	//^(LOCALDO NO_SCOPE protocolname EMPTY_ARGUMENTINSTANTIATIONLIST roleinstantiationlist)
	^(LOCALDO NO_SCOPE protocolname ^(ARGUMENTINSTANTIATIONLIST) roleinstantiationlist)
|
	DO_KW protocolname argumentinstantiationlist roleinstantiationlist ';'
->
	^(LOCALDO NO_SCOPE protocolname argumentinstantiationlist roleinstantiationlist)
|
	DO_KW scopename ':' protocolname roleinstantiationlist ';'
->
	//^(LOCALDO scopename protocolname EMPTY_ARGUMENTINSTANTIATIONLIST roleinstantiationlist)
	^(LOCALDO scopename protocolname ^(ARGUMENTINSTANTIATIONLIST) roleinstantiationlist)
|
	DO_KW scopename ':' protocolname argumentinstantiationlist roleinstantiationlist ';'
->
	^(LOCALDO scopename protocolname argumentinstantiationlist roleinstantiationlist)
;
*/
