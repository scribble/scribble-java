/*
 * > scribble-java
 * $ java -cp modules/parser/lib/antlr-3.5.2-complete.jar org.antlr.Tool -o modules/parser/target/generated-sources/antlr3 modules/parser/src/main/antlr3/org/scribble/parser/antlr/Scribble.g
 */


grammar Scribble;


options
{
	language = Java;
	output = AST;
	ASTLabelType = CommonTree;
	backtrack = true;  // backtracking disabled by default? Is it bad to require this option?
	//memoize = true;
}


tokens
{
	/*
	 * Parser input constants (lexer output; keywords, Section 2.4)
	 */
	MODULEKW = 'module';
	IMPORTKW = 'import';
	TYPEKW = 'type';
	PROTOCOLKW = 'protocol';
	GLOBALKW = 'global';
	LOCALKW = 'local';
	ROLEKW = 'role';
	SELFKW = 'self';
	SIGKW = 'sig';
	INSTANTIATESKW = 'instantiates';
	ASKW = 'as';

	FROMKW = 'from';
	TOKW = 'to';
	CHOICEKW = 'choice';
	ATKW = 'at';
	ORKW = 'or';
	RECKW = 'rec';
	CONTINUEKW = 'continue';
	PARKW = 'par';
	ANDKW = 'and';
	INTERRUPTIBLEKW = 'interruptible';
	WITHKW = 'with';
	BYKW = 'by';  /* from for interrupts is more expected, but from is
	                 not good for multiple roles (generally, the comma
	                 in interrupt message list and role list looks like
	                 "and" rather than "or") */
	THROWSKW = 'throws';
	CATCHESKW = 'catches';
	DOKW = 'do';
	//SPAWNKW = 'spawn';

	EMPTY_ALIAS = '__empty_alias';
	EMPTY_SCOPENAME = '__empty_scopename';
	NO_SCOPE = '__no_scope';
	//EMPTY_PACKAGENAME = '__empty_packagebame';
	EMPTY_OPERATOR = '__empty_operator';

	//EMPTY_PARAMETERDECLLIST = '__empty_parameterdecllist';
	//EMPTY_ARGUMENTINSTANTIATIONLIST = '__empty_argumentinstantiationlist';
	EMPTY_LOCALTHROW = '__empty_localthrow';
	//EMPTY_LOCAL_CATCHES = '__empty_local_catch';

	KIND_MESSAGESIGNATURE = 'KIND_MESSAGESIGNATURE';
	KIND_PAYLOADTYPE = 'KIND_PAYLOADTYPE';
	
	
	/*
	 * Parser output "node types" (corresponding to the various syntactic
	 * categories) i.e. the labels used to distinguish resulting AST nodes.
	 * The value of these token variables doesn't matter, only the token
	 * (i.e. variable) names themselves are used (for AST node root text
	 * field)
	 */
	//NAME = 'name';
	AMBIGUOUSNAME = 'ambiguous-name';
	QUALIFIEDNAME = 'qualified-name';
	//PACKAGENAME = 'package-name';
	//FULLMODULENAME = 'full-module-name';
	//SIMPLEMEMBERNAME = 'simple-member-name';
	//QUALIFIEDMEMBERNAME = 'qualified-member-name';
	
	
	//MODULE = 'module';
	MODULE = 'modul';
	//PACKAGEDECL = 'package-decl';
	MODULEDECL = 'module-decl';
	//IMPORTDECL = 'import-decl';
	//FROMIMPORTDECL = 'from-import-decl';
	IMPORTMODULE = 'import-module';
	IMPORTMEMBER = 'import-member';
	PAYLOADTYPEDECL = 'payload-type-decl';
	MESSAGESIGNATUREDECL = 'message-signature-decl';
	PARAMETERDECLLIST = 'parameter-decl-list';
	PARAMETERDECL = 'parameter-decl';
	MESSAGESIGNATURE = 'message-signature';
	ROLEDECLLIST = 'role-decl-list';
	ROLEDECL = 'role-decl';
	ARGUMENTINSTANTIATIONLIST = 'argument-instantiation-list';
	//ARGUMENTINSTANTIATION = 'argument-instantiation';
	PAYLOAD = 'payload';
	//PAYLOADELEMENT = 'payloadelement';
	ROLEINSTANTIATIONLIST = 'role-instantiation-list';
	ROLEINSTANTIATION = 'role-instantiation';  // FIXME: not consistent with arginstas/payloadeles

	GLOBALPROTOCOLDECL = 'global-protocol-decl';
	GLOBALPROTOCOLHEADER = 'global-protocol-header';
	GLOBALPROTOCOLDEF = 'global-protocol-def';
	GLOBALPROTOCOLBLOCK = 'global-protocol-block';
	GLOBALINTERACTIONSEQUENCE = 'global-interaction-sequence';
	GLOBALMESSAGETRANSFER = 'global-message-transfer';
	GLOBALCHOICE = 'global-choice';
	GLOBALRECURSION = 'global-recursion';
	GLOBALCONTINUE = 'global-continue';
	GLOBALPARALLEL = 'global-parallel';
	GLOBALINTERRUPTIBLE = 'global-interruptible';
	GLOBALINTERRUPT = 'global-interrupt';
	GLOBALDO = 'global-do';

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
	
	PAYLOADHACK = 'payload-hack';  // FIXME
}


@header
{
	package org.scribble.parser.antlr;
}

@lexer::header
{
	package org.scribble.parser.antlr;
}

@members {
	//private org.scribble.logging.IssueLogger _logger=null;
	private String _document=null;
	private boolean _errorOccurred=false;
	
    /*public void setLogger(org.scribble.logging.IssueLogger logger) {
    	_logger = logger;
    }*/
    
    public void setDocument(String doc) {
    	_document = doc;
    }
    
    public void emitErrorMessage(String mesg) {
    	/*if (_logger == null) {
    		super.emitErrorMessage(mesg);
    	} else {
    		_logger.error(org.scribble.parser.antlr.ANTLRMessageUtil.getMessageText(mesg),
    					org.scribble.parser.antlr.ANTLRMessageUtil.getProperties(mesg, _document));
    	}*/
    	_errorOccurred = true;
    }
    
    public boolean isErrorOccurred() {
    	return(_errorOccurred);
    }
}


/****************************************************************************
 * Chapter 2 Lexical Structure (Lexer rules)
 ***************************************************************************/

/*
 * Section 2.1 White space (Section 2.1)
 */
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
	'&' | '?' | '!'	| UNDERSCORE | ',' | ' '
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
	moduledecl importdecl* datatypedecl* protocoldecl*
->
	^(MODULE moduledecl importdecl* datatypedecl* protocoldecl*)
;


/**
 * Section 3.2.3 Module Declarations
 */
moduledecl:
	MODULEKW modulename ';'
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
	IMPORTKW modulename ';'
	->
	^(IMPORTMODULE modulename EMPTY_ALIAS)
|
	IMPORTKW modulename ASKW simplemodulename ';'
->
	^(IMPORTMODULE modulename simplemodulename)
;

importmember:
	FROMKW modulename IMPORTKW simplemembername ';'
	->
	^(IMPORTMEMBER modulename simplemembername EMPTY_ALIAS)
|
	FROMKW modulename IMPORTKW simplemembername ASKW simplemembername ';'
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
	TYPEKW '<' IDENTIFIER '>' EXTIDENTIFIER FROMKW EXTIDENTIFIER ASKW simplepayloadtypename ';'
	->
	^(PAYLOADTYPEDECL IDENTIFIER EXTIDENTIFIER EXTIDENTIFIER simplepayloadtypename)
;

messagesignaturedecl:
	SIGKW '<' IDENTIFIER '>' EXTIDENTIFIER FROMKW EXTIDENTIFIER ASKW simplemessagesignaturename ';'
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
	EXTIDENTIFIER
	->
	^(PAYLOADHACK EXTIDENTIFIER)
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
;

globalprotocolheader:
	GLOBALKW PROTOCOLKW simpleprotocolname roledecllist
	->
	^(GLOBALPROTOCOLHEADER simpleprotocolname ^(PARAMETERDECLLIST) roledecllist)
|
	GLOBALKW PROTOCOLKW simpleprotocolname parameterdecllist roledecllist
	->
	^(GLOBALPROTOCOLHEADER simpleprotocolname parameterdecllist roledecllist)
;

roledecllist:
	'(' roledecl (',' roledecl)* ')'
	->
	^(ROLEDECLLIST roledecl+)
;

roledecl:
	ROLEKW rolename
	->
	^(ROLEDECL rolename)
;

parameterdecllist:
	'<' parameterdecl (',' parameterdecl)* '>'
	->
	^(PARAMETERDECLLIST parameterdecl+)
;

parameterdecl:
	 TYPEKW parametername
	->
	^(PARAMETERDECL KIND_PAYLOADTYPE parametername)
|
	 SIGKW parametername
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
	globalparallel
|
	globalinterruptible
|
	globaldo
;


/**
 * Section 3.7.4 Global Message Transfer
 */
globalmessagetransfer:
	message FROMKW rolename TOKW rolename (',' rolename )* ';'
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


/**
 * Section 3.7.5 Global Choice
 */
globalchoice:
	CHOICEKW ATKW rolename globalprotocolblock (ORKW globalprotocolblock)*
	->
	^(GLOBALCHOICE rolename globalprotocolblock+)
;


/**
 * Section 3.7.6 Global Recursion
 */
globalrecursion:
	RECKW recursionvarname globalprotocolblock
	->
	^(GLOBALRECURSION recursionvarname globalprotocolblock)
;

globalcontinue:
	CONTINUEKW recursionvarname ';'
	->
	^(GLOBALCONTINUE recursionvarname)
;


/**
 * Section 3.7.7 Global Parallel
 */
globalparallel:
	PARKW globalprotocolblock (ANDKW globalprotocolblock)*
	->
	^(GLOBALPARALLEL globalprotocolblock+)
;


/**
 * Section 3.7.8 Global Interruptible
 */
globalinterruptible:
	INTERRUPTIBLEKW globalprotocolblock WITHKW '{' globalinterrupt* '}'
	->
	^(GLOBALINTERRUPTIBLE EMPTY_SCOPENAME globalprotocolblock globalinterrupt*)
|
	INTERRUPTIBLEKW scopename globalprotocolblock WITHKW '{' (globalinterrupt)* '}'
	->
	^(GLOBALINTERRUPTIBLE scopename globalprotocolblock globalinterrupt*)
;

globalinterrupt:
	message (',' message)* BYKW rolename ';'
	->
	^(GLOBALINTERRUPT rolename message+)
;


/**
 * Section 3.7.9 Global Do
 */
globaldo:
	DOKW protocolname roleinstantiationlist ';'
	->
	^(GLOBALDO protocolname ^(ARGUMENTINSTANTIATIONLIST) roleinstantiationlist)
|
	DOKW protocolname argumentinstantiationlist roleinstantiationlist ';'
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
	LOCALKW PROTOCOLKW simpleprotocolname localroledecllist
	->
	//simpleprotocolname EMPTY_PARAMETERDECLLIST localroledecllist
	simpleprotocolname ^(PARAMETERDECLLIST) localroledecllist
|
	LOCALKW PROTOCOLKW simpleprotocolname parameterdecllist localroledecllist
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
	SELFKW rolename
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
	message TOKW rolename (',' rolename)* ';'
	->
	^(LOCALSEND message rolename+)
;

localreceive:
	message FROMKW IDENTIFIER ';'
	->
	^(LOCALRECEIVE message IDENTIFIER)
;


/**
 * Section 3.8.5 Local Choice
 * /
localchoice:
	CHOICEKW ATKW rolename localprotocolblock (ORKW localprotocolblock)*
	->
	^(LOCALCHOICE rolename localprotocolblock+)
;


/**
 * Section 3.8.6 Local Recursion
 * /
localrecursion:
	RECKW recursionvarname localprotocolblock
	->
	^(LOCALRECURSION recursionvarname localprotocolblock)
;

localcontinue:
	CONTINUEKW recursionvarname ';'
	->
	^(LOCALCONTINUE recursionvarname)
;


/**
 * Section 3.8.7 Local Parallel
 * /
localparallel:
	PARKW localprotocolblock (ANDKW localprotocolblock)*
	->
	^(LOCALPARALLEL localprotocolblock+)
;


/**
 * Section 3.8.8 Local Interruptible
 * /
localinterruptible:
	INTERRUPTIBLEKW scopename localprotocolblock WITHKW '{' localcatches* '}'
	->
	^(LOCALINTERRUPTIBLE scopename localprotocolblock EMPTY_LOCALTHROW localcatches*)
|
	INTERRUPTIBLEKW scopename localprotocolblock WITHKW '{' localthrows localcatches* '}'
	->
	^(LOCALINTERRUPTIBLE scopename localprotocolblock localthrows localcatches*)
;

/*localthrowandorcatch:
	localthrow (localcatch)*
|
	(localcatch)+
;* /

localthrows:
	THROWSKW message (',' message)* TOKW rolename (',' rolename)* ';'
	->
	^(LOCALTHROWS rolename+ TOKW message+)
;

localcatches:
	CATCHESKW message (',' message)* FROMKW rolename ';'
	->
	^(LOCALCATCHES rolename message+)
;


/**
 * Section 3.8.9 Local Do
 * /
localdo:
	DOKW protocolname roleinstantiationlist ';'
	->
	//^(LOCALDO NO_SCOPE protocolname EMPTY_ARGUMENTINSTANTIATIONLIST roleinstantiationlist)
	^(LOCALDO NO_SCOPE protocolname ^(ARGUMENTINSTANTIATIONLIST) roleinstantiationlist)
|
	DOKW protocolname argumentinstantiationlist roleinstantiationlist ';'
	->
	^(LOCALDO NO_SCOPE protocolname argumentinstantiationlist roleinstantiationlist)
|
	DOKW scopename ':' protocolname roleinstantiationlist ';'
	->
	//^(LOCALDO scopename protocolname EMPTY_ARGUMENTINSTANTIATIONLIST roleinstantiationlist)
	^(LOCALDO scopename protocolname ^(ARGUMENTINSTANTIATIONLIST) roleinstantiationlist)
|
	DOKW scopename ':' protocolname argumentinstantiationlist roleinstantiationlist ';'
	->
	^(LOCALDO scopename protocolname argumentinstantiationlist roleinstantiationlist)
;
*/
