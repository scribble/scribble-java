grammar ScribbleProtocol;

// CHANGES:
// Had to remove rewrite rules as was throwing exception on 'rule packagename'
// Add ! to ';' so don't need to consume - but could explicitly consume
// payloadlist rule had an empty cause with rewrite rule, as a optional path - so when removing rewrite, had to remove that path
// EXTIDENTIFIER still not working, so changed it to be same as StringLiteral rule in previous version of grammer

options {
	output=AST;
	backtrack=true;
}

tokens
{
	EMPTY_MESSAGE_OP = '__empty_message_op';
	EMPTY_ANNOTATION = '__empty_annotation';
	EMPTY_ARGUMENT_LIST = '__empty_argument_list';
	EMPTY_PACKAGE_NAME = '__empty_package_name';

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
	PARALLELKW = 'par';  // FIXME: should be PARKW
	ANDKW = 'and';
	INTERRUPTIBLEKW = 'interruptible';
	WITHKW = 'with';
	BYKW = 'by';  // from for interrupts is more expected, but from is not good for multiple roles (generally, the comma in interrupt message list and role list looks like "and" rather than "or")
	DOKW = 'do';
	ASKW = 'as';
	SPAWNKW = 'spawn';

	// The value of these token variables doesn't matter, only the token (i.e. variable) names themselves are used (for AST node root text field)
	MODULE = 'module';
	PACKAGEDECL = 'package-decl';
	IMPORTDECL = 'import-decl';
	PAYLOADTYPEDECL = 'payload-type-decl';  // FIXME: payload types, not message types
	FROMIMPORTDECL = 'from-import-decl';
	PARAMETERLIST = 'parameter-list';
	MESSAGESIGNATURE = 'message-signature';
	ROLELIST = 'role-list';
	ARGUMENTLIST = 'argument-list';
	PAYLOADLIST = 'payload-list';
	PAYLOAD = 'payload';
	ROLEINSTANTIATIONLIST = 'role-instantiation-list';

	GLOBALPROTOCOLDECL = 'global-protocol-decl';
	GLOBALPROTOCOLDEF = 'global-protocol-def';
	GLOBALPROTOCOLINSTANCE = 'global-protocol-instance';
	GLOBALPROTOCOLBODY = 'global-protocol-body';
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
	GLOBALSPAWN = 'global-spawn';
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
 * PARSER RULES
 *------------------------------------------------------------------*/

module:
	packagedecl (importdecl)* (payloadtypedecl)* (protocoldecl)*
;

packagedecl:
	PACKAGEKW packagename ';'!
;

packagename:
	IDENTIFIER ('.' IDENTIFIER)*
;

importdecl:
	IMPORTKW IDENTIFIER ('.' IDENTIFIER)* ';'!
|
	FROMKW packagename '.' IDENTIFIER IMPORTKW IDENTIFIER ';'!
|
	FROMKW packagename '.' IDENTIFIER IMPORTKW IDENTIFIER ASKW IDENTIFIER ';'!
;

payloadtypedecl:  // FIXME: payload types, not message types
	TYPEKW '<' IDENTIFIER '>' EXTIDENTIFIER FROMKW EXTIDENTIFIER ASKW IDENTIFIER ';'!
;  // Seems to go off "element order", no way to label the elements

protocoldecl:
	globalprotocoldecl
|
	localprotocoldecl
;

globalprotocoldecl:
	GLOBALKW PROTOCOLKW IDENTIFIER globalprotocoldefinition
|
	GLOBALKW PROTOCOLKW IDENTIFIER globalprotocolinstance
;

globalprotocoldefinition:
	// seems working better to do optional elements as alternative rules rather than using "?", maybe due to the rule rewriting reference to the element
	'(' rolelist ')' globalprotocolbody
|
	'<' parameterlist '>' '(' rolelist ')' globalprotocolbody
;

globalprotocolinstance:
	'(' rolelist ')' INSTANTIATESKW IDENTIFIER ';'!
|
	'(' rolelist ')' INSTANTIATESKW IDENTIFIER '<' argumentlist '>' ';'!
;

parameterlist:
	SIGKW IDENTIFIER (',' SIGKW IDENTIFIER)*
;

messagesignature:
	'(' payloadlist ')'
|
	IDENTIFIER '(' payloadlist ')'
;

payloadlist:
	payload (',' payload)*
;

payload:
	IDENTIFIER
|
	IDENTIFIER ':' IDENTIFIER
;

rolelist:
	ROLEKW IDENTIFIER (',' ROLEKW IDENTIFIER)*
;

argumentlist:
	argument (',' argument)*
;

argument:
	IDENTIFIER
|
	messagesignature
;

globalprotocolbody:
	globalprotocolblock
;

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
|
	globalinterruptible
|
	globaldo
/*|
	globalspawn*/
;

globalmessagetransfer:
	messagesignature FROMKW IDENTIFIER TOKW IDENTIFIER ';'!
|
	IDENTIFIER FROMKW IDENTIFIER TOKW IDENTIFIER ';'!
;

globalchoice:
	CHOICEKW ATKW IDENTIFIER globalprotocolblock (ORKW globalprotocolblock)*
;

globalrecursion:
	RECKW IDENTIFIER globalprotocolblock
;

globalcontinue:
	CONTINUEKW IDENTIFIER ';'!
;

globalparallel:
	PARALLELKW globalprotocolblock (ANDKW globalprotocolblock)*
;

globalinterruptible:
	INTERRUPTIBLEKW globalprotocolblock WITHKW '{' (globalinterrupt)+ '}'
;

// FIXME: parameterised interrupt
globalinterrupt:
	messagesignature (',' messagesignature)* BYKW IDENTIFIER (',' IDENTIFIER)* ';'!
;

globaldo:
	DOKW packagename '.' IDENTIFIER '(' roleinstantiationlist ')' ';'!
|
	DOKW packagename '.' IDENTIFIER '<' argumentlist '>' '(' roleinstantiationlist ')' ';'!
| // FIXME: factor better (generally, package/module/protocol names)
	DOKW IDENTIFIER '(' roleinstantiationlist ')' ';'!
|
	DOKW IDENTIFIER '<' argumentlist '>' '(' roleinstantiationlist ')' ';'!
;  // argumentlist and roleinstantiationlist not uniform ("as")

/*globalspawn:
	SPAWNKW IDENTIFIER '(' roleinstantiationlist ')' ';'!
	->
	^(GLOBALSPAWN IDENTIFIER roleinstantiationlist)
|
	SPAWNKW IDENTIFIER '<' argumentlist '>' '(' roleinstantiationlist ')' ';'!
	->
	^(GLOBALSPAWN IDENTIFIER argumentlist roleinstantiationlist)
;*/

roleinstantiationlist:
	roleinstantiation (',' roleinstantiation)*
;

roleinstantiation:
	IDENTIFIER ASKW IDENTIFIER
;

localprotocoldecl:
	LOCALKW PROTOCOLKW
;


/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

WHITESPACE:
	( '\t' | ' ' | '\r' | '\n'| '\u000C' )+
	{
		$channel = HIDDEN;
	}
;

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

IDENTIFIER:
	(LETTER | UNDERSCORE) (LETTER | DIGIT | UNDERSCORE)*
;

/*MESSAGEOPERATOR:  // rule "subsumed" by identifier, lexer doesn't like it (also because it is a potentially empty token)
	(LETTER | DIGIT | UNDERSCORE)*
;*/

EXTIDENTIFIER:
	'"' ( ~('\\'|'"') )* '"'
;

fragment SYMBOL:
	'{' | '}' | '(' | ')' | '[' | ']' | ':' | '/' | '\\' | '.' | '\#' | '&' | '?' | '!'	| UNDERSCORE
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

