grammar ScribbleProtocol;

// CHANGES:
// EXTIDENTIFIER still not working, so changed it to be same as StringLiteral rule in previous version of grammer
// importdecl - didn't work with modulename, was having problem with ';' expecting '.'
// messageoperator rule
// globalinteraction rule, comment end misplaced
// globaldo - used membername instead of packagename '.' IDENTIFIER, which caused problems, but is same


// Had to remove rewrite rules as was throwing exception on 'rule packagename'
// Add ! to ';' so don't need to consume - but could explicitly consume
// payloadlist rule had an empty cause with rewrite rule, as a optional path - so when removing rewrite, had to remove that path

options {
	output=AST;
	backtrack=true;
}

/* 2.4  Keywords
 *
 * These values are stored in the Tokens created by the Lexer. Can be used as
 * (AST) Tree node "types".
 */
tokens
{
	/*EMPTY_MESSAGE_OP = '__empty_message_op';  // Implementation specific
	EMPTY_ANNOTATION = '__empty_annotation';
	EMPTY_ARGUMENT_LIST = '__empty_argument_list';
	EMPTY_PACKAGE_NAME = '__empty_package_name';*/

	PACKAGEKW = 'package';
	IMPORTKW = 'import';
	TYPEKW = 'type';
	PROTOCOLKW = 'protocol';
	GLOBALKW = 'global';
	LOCALKW = 'local';
	ROLEKW = 'role';
	SIGKW = 'sig';
	INSTANTIATESKW = 'instantiates';

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
	BYKW = 'by'; // from for interrupts is more expected, but from is not good
	             // for multiple roles (generally, the comma in interrupt message
	             // list and role
	             // list looks like "and" rather than "or")
	DOKW = 'do';
	ASKW = 'as';
	SPAWNKW = 'spawn';
}

/*------------------------------------------------------------------
 * JAVA SPECIFIC DEFINITIONS
 *------------------------------------------------------------------*/
 
@header {
package org.scribble.protocol.parser.antlr;
}
   
@lexer::header {
package org.scribble.protocol.parser.antlr;
}
   
@members {
	private org.scribble.protocol.parser.ParserLogger _logger=null;
	private String _document=null;
	private boolean _errorOccurred=false;
	
    public void setParserLogger(org.scribble.protocol.parser.ParserLogger logger) {
    	_logger = logger;
    }
    
    public void setDocument(String doc) {
    	_document = doc;
    }
    
    public void emitErrorMessage(String mesg) {
    	if (_logger == null) {
    		super.emitErrorMessage(mesg);
    	} else {
    		_logger.error(org.scribble.protocol.parser.antlr.ANTLRMessageUtil.getMessageText(mesg),
    					org.scribble.protocol.parser.antlr.ANTLRMessageUtil.getProperties(mesg, _document));
    	}
    	_errorOccurred = true;
    }
    
    public boolean isErrorOccurred() {
    	return(_errorOccurred);
    }
}


/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/


/* 2.1  White Space
 */
WHITESPACE:  // Name not important: tokens are thrown away
	( '\t' | ' ' | '\r' | '\n'| '\u000C' )+
	{
		$channel = HIDDEN;  // Throw away tokens
	}
;


/* 2.2  Comments
 */

COMMENT:
	'/*' .* '*/'
	{
		$channel=HIDDEN;
	}
;

LINE_COMMENT:  // FIXME: underscores not used for other category names
	'//' ~('\n'|'\r')* '\r'? '\n'
	{
		$channel=HIDDEN;
	}
;


/* 2.3  Identifiers
 */

IDENTIFIER:
	(LETTER | UNDERSCORE) (LETTER | DIGIT | UNDERSCORE)*
;

EXTIDENTIFIER:
	'"' ( ~('\\'|'"') )* '"'
	//'"' (LETTER | UNDERSCORE | SYMBOL) (LETTER | DIGIT | UNDERSCORE | SYMBOL) '"'
;

fragment LETTER:
	'a'..'z' | 'A'..'Z'
;

fragment DIGIT:
	'0'..'9'
;

fragment SYMBOL:
	'{' | '}' | '(' | ')' | '[' | ']' | ':' | '/' | '\\' | '.'
|
	'\#' | '&' | '?' | '!' | UNDERSCORE
;

fragment UNDERSCORE:
	'_'
;


/* 3.4  Message Signatures
 */

/*
MESSAGEOPERATOR: // rule "subsumed" by identifier, lexer doesn't like it (also because it is a potentially empty token)  // Parser rule instead?
	(LETTER | DIGIT | UNDERSCORE)*
	//IDENTIFIER  // Usage is not like this
;
*/
messageoperator:
	IDENTIFIER
;


/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

/* 3.1 Basic Names
 */

rolename:
	IDENTIFIER
;

parametername:  // FIXME: in langref
	IDENTIFIER
;

annotationname:  // Move ealier? (collect all names together? in a name (sub)section)
	IDENTIFIER
;

recursionlabelname:
	IDENTIFIER
;


/* 3.2.1  Names
 */
packagename:  // i.e. implicit fully qualified package name
	//(IDENTIFIER '.')* simplepackagename
	(IDENTIFIER '.')* IDENTIFIER
;

/*simplepackagename:
	IDENTIFIER
;*/

modulename:  // Simple name not needed?
	packagename '.' IDENTIFIER  // Needs backtrack
	//(IDENTIFIER '.')* simplepackagename '.' IDENTIFIER
	//(IDENTIFIER '.')* IDENTIFIER '.' IDENTIFIER
;

membername:  // Separate into protocol/type names?
	simplemembername
|
	fullyqualifiedmembername
;

simplemembername:
	IDENTIFIER
;

fullyqualifiedmembername:
	modulename '.' simplemembername
;

/*payloadtypename:
	simplemembername
;

protocolname:
	simplemembername
;*/


/* 3.2.2  Modules
 */
module:
	packagedecl (importdecl)* (payloadtypedecl)* (protocoldecl)*
;


/* 3.2.3  Package Declarations
 */

packagedecl:
	PACKAGEKW packagename ';'//!  // In principle ! is implementation specific(?)
;


/* 3.2.4  Import Declarations
 */

importdecl:
	IMPORTKW IDENTIFIER ('.' IDENTIFIER)* ';'//!
|
	// Use a simple modulename?
	IMPORTKW IDENTIFIER ('.' IDENTIFIER)* ASKW IDENTIFIER ';'//!
|
	FROMKW modulename IMPORTKW simplemembername ';'//!
|
	FROMKW modulename IMPORTKW simplemembername ASKW simplemembername ';'//!
;


/* 3.3  Payload Type Declarations
 */

/*payloadtypename:  // simple name
	simplemembername
;*/

payloadtypedecl:
	TYPEKW '<' IDENTIFIER '>' EXTIDENTIFIER FROMKW EXTIDENTIFIER ASKW simplemembername ';'//!
;


/* 3.4  Message Signatures
 */

messagesignature:
	'(' payloadlist ')'
|
	messageoperator '(' payloadlist ')'
;

payloadlist:
	payload (',' payload)*
;

payload:  // FIXME: in langref, category name should be just payload, not type
	membername
	// payloadtypename
|
	annotationname ':' membername
;


/* 3.5  Protocol Declarations
 */

protocoldecl:
	globalprotocoldecl
|
	localprotocoldecl
;


/* 3.6  Global Protocols
 */

globalprotocoldecl:
	GLOBALKW PROTOCOLKW simplemembername (globalprotocoldefinition | globalprotocolinstance)
	/*GLOBALKW PROTOCOLKW simplemembername globalprotocoldefinition
|
	GLOBALKW PROTOCOLKW simplemembername globalprotocolinstance*/
;

globalprotocoldefinition:
// Seems better to do optional elements as alternative rules rather
// than using "?", maybe due to the rule rewriting reference to the element
	'(' rolelist ')' globalprotocolbody
|
	'<' parameterlist '>' '(' rolelist ')' globalprotocolbody
;

globalprotocolbody:
	globalprotocolblock
;

globalprotocolinstance:
	'(' rolelist ')' INSTANTIATESKW membername ';'//!
|
	'(' rolelist ')' INSTANTIATESKW membername '<' argumentlist '>' ';'//!
;

rolelist:
	roledecl (',' roledecl)*
;

roledecl:
	ROLEKW rolename
|
	ROLEKW rolename ASKW rolename
;

parameterlist:
	parameterdecl (',' parameterdecl)*
;

parameterdecl:
	SIGKW parametername
|
	SIGKW parametername ASKW parametername
;

argumentlist:
	argument (',' argument)*
;

argument:
	argumentval
|
	argumentval ASKW parametername
;

argumentval:
	parametername  // FIXME: in langref
|
	messagesignature
;


/* 3.6.1  Global Interaction Blocks and Sequences
 */

globalprotocolblock:
	'{' globalinteractionsequence '}'
;

globalinteractionsequence:
	(globalinteraction)*
;

globalinteraction:
	globalmessagetransfer
|
	globalchoice
|
	globalparallel
|
	globalrecursion
|
	globalcontinue
/*|
	globalinterruptible*/
|
	globaldo
/*|
	globalspawn*/
;


/* 3.6.2  Point-to-point Message Transfer
 */
globalmessagetransfer:
	messagesignature FROMKW rolename TOKW rolename ';'//!
|
	parametername FROMKW rolename TOKW rolename ';'//!
;


/* 3.6.3  Choice
 */
globalchoice:
	CHOICEKW ATKW rolename globalprotocolblock (ORKW globalprotocolblock)*
;


/* 3.6.4  Recursion
 */
globalrecursion:
	RECKW recursionlabelname globalprotocolblock
;

globalcontinue:
	CONTINUEKW recursionlabelname ';'//!
;


/* 3.6.5  Parallel
 */
globalparallel:
	PARKW globalprotocolblock (ANDKW globalprotocolblock)*
;


/* 3.6.6  Interruptible
 *
globalinterruptible:
	INTERRUPTIBLEKW globalprotocolblock WITHKW '{' (globalinterrupt)+ '}'
;

// FIXME: parameterised interrupt
globalinterrupt:
	messagesignature (',' messagesignature)* BYKW IDENTIFIER (',' IDENTIFIER)* ';'!
;
*/


/* 3.6.7  Do
 */
globaldo:
	// packagename needs to be resolved later into full or simple name
	DOKW membername '(' roleinstantiationlist ')' ';'//!
|
	DOKW membername '<' argumentlist '>' '(' roleinstantiationlist ')' ';'//!
;
// argumentlist and roleinstantiationlist not uniform ("as")

roleinstantiationlist:
	roleinstantiation (',' roleinstantiation)*
;

roleinstantiation:
	rolename
|
	rolename ASKW rolename
;


/* 3.5.8
 *
globalspawn:
	SPAWNKW packagename '(' roleinstantiationlist ')' ';'!
|
	SPAWNKW packagename '<' argumentlist '>' '(' roleinstantiationlist ')' ';'!
;
*/


/* 3.7  Local Protocols
 */

// How much of local protocol BNF should be factored out with global?
localprotocoldecl:
	LOCALKW PROTOCOLKW simplemembername ATKW rolename (localprotocoldefinition | localprotocolinstance)
;

localprotocoldefinition:
	'(' rolelist ')' localprotocolbody  // Definition is the same global. But we want to give the AST nodes to be "typed" as "local"
|
	'<' parameterlist '>' '(' rolelist ')' localprotocolbody
;

localprotocolinstance:
	'(' rolelist ')' INSTANTIATESKW membername ';'
|
	'(' rolelist ')' INSTANTIATESKW membername '<' argumentlist '>' ';'
;

localprotocolbody:
	localprotocolblock
;


/* 3.7.1  Local Interaction Blocks and Sequences
 */
localprotocolblock:
	'{' localinteractionsequence '}'
;

localinteractionsequence:
	(localinteraction)*
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
/*|
	localinterruptible
|*/
	localdo
/*|
	localspawn*/
;


/* 3.7.2  Point-to-Point Message Transfer
 */
localsend:
	messagesignature TOKW rolename ';'
|
	parametername TOKW rolename ';'
;

localreceive:
	messagesignature FROMKW rolename ';'
|
	parametername FROMKW rolename ';'
;


/* 3.7.3  Choice
 */
localchoice:
	// FIXME: keeping rolename here? whether subject or not?
	CHOICEKW ATKW rolename localprotocolblock (ORKW localprotocolblock)*
;


/* 3.7.4  Recursion
 */
localrecursion:
	RECKW recursionlabelname localprotocolblock
;

localcontinue:
	CONTINUEKW recursionlabelname ';'
;


/* 3.7.5  Parallel
 */
localparallel:
	PARKW localprotocolblock (ANDKW localprotocolblock)*
;


/* 3.7.6  Interruptible
 *
localinterruptible:
	INTERRUPTIBLEKW localprotocolblock WITHKW '{' localthrowandorcatch '}'
;

localthrowandorcatch:
	localthrow localcatch
|
	localthrow
|
	localcatch
;

localthrow:
	THROWSKW messagesignature (',' messagesignature)* ';'
;

localcatch:
	CATCHESKW messagesignature FROMKW IDENTIFIER (',' messagesignature FROMKW IDENTIFIER)* ';'
;
*/


/* 3.6.7  Do
 */
localdo:
	DOKW membername '(' roleinstantiationlist ')' ';'
|
	DOKW membername '<' argumentlist '>' '(' roleinstantiationlist ')' ';'
;  // argumentlist and roleinstantiationlist not uniform ("as")

