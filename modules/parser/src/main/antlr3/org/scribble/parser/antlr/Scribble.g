/*
 * Raymond@HZHL2 ~/code/python/scribble-tools
 * $ java -cp lib/antlr-3.1.3.jar org.antlr.Tool -o bin src/scribble/Scribble.g
*/

// Use language actions to directly create lists etc.? but ties the grammar source to that language?

// Garbage at the end of input file seems to just get ignored, no error raised. but also for e.g. two package declarations -- check if this is still the case?


grammar Scribble;

options
{
	output=AST;
	backtrack=true;
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


	/*
	 * Parser output node types (corresponding to the various syntactic
	 * categories) i.e. the labels used to distinguish resulting AST nodes.
	 * The value of these token variables doesn't matter, only the token
	 * (i.e. variable) names themselves are used (for AST node root text
	 * field)
     */
	//MODULE = 'module';
	MODULE = 'modul';
	//PACKAGEDECL = 'package-decl';
	MODULEDECL = 'module-decl';
	//IMPORTDECL = 'import-decl';
	//FROMIMPORTDECL = 'from-import-decl';
	IMPORTMODULE = 'import-module';
	IMPORTMEMBER = 'import-member';
	PAYLOADTYPEDECL = 'payload-type-decl';
	PARAMETERDECLLIST = 'parameter-decl-list';
	PARAMETERDECL = 'parameter-decl';
	MESSAGESIGNATURE = 'message-signature';
	ROLEDECLLIST = 'role-decl-list';
	ROLEDECL = 'role-decl';
	ARGUMENTLIST = 'argument-list';
	ARGUMENT = 'argument';
	PAYLOAD = 'payload';
	PAYLOADELEMENT = 'payloadelement';
	ROLEINSTANTIATIONLIST = 'role-instantiation-list';
	ROLEINSTANTIATION = 'role-instantiation';

	GLOBALPROTOCOLDECL = 'global-protocol-decl';
	GLOBALPROTOCOLDEF = 'global-protocol-def';
	GLOBALPROTOCOLINSTANCE = 'global-protocol-instance';
	//GLOBALPROTOCOLBODY = 'global-protocol-body';
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
	//GLOBALSPAWN = 'global-spawn';

	LOCALPROTOCOLDECL = 'local-protocol-decl';
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
	LOCALTHROW = 'local-throw';
	LOCALCATCH = 'local-catch';
	LOCALSEND = 'local-send';
	LOCALRECEIVE = 'local-receive';


	/*
	 * Some utility constants; AST token values are the variable names (not
	 * the variable values).
	 * (N.B. Some grammar rules use dummy placeholder values, but others do not)
	 */
	EMPTY_MESSAGE_OP = '__empty_message_op';
	EMPTY_ANNOTATION = '__empty_annotation';
	EMPTY_PARAMETER_DECL_LIST = '__empty_parameter_decl_list';
	EMPTY_ARGUMENT_LIST = '__empty_argument_list';
	EMPTY_MODULE_NAME = '__empty_module_name';
	EMPTY_SCOPE_NAME = '__empty_scope_name';
	EMPTY_LOCAL_THROW = '__empty_local_throw';
	EMPTY_LOCAL_CATCHES = '__empty_local_catch';

	KIND_MESSAGE_SIGNATURE = 'KIND_MESSAGE_SIGNATURE';
	KIND_PAYLOAD_TYPE = 'KIND_PAYLOAD_TYPE';
}


/*------------------------------------------------------------------
 * JAVA SPECIFIC DEFINITIONS
 *------------------------------------------------------------------*/
 
@header {
package org.scribble.parser.antlr;
}
   
@lexer::header {
package org.scribble.parser.antlr;
}
   
@members {
	private org.scribble.logging.IssueLogger _logger=null;
	private String _document=null;
	private boolean _errorOccurred=false;
	
    public void setLogger(org.scribble.logging.IssueLogger logger) {
    	_logger = logger;
    }
    
    public void setDocument(String doc) {
    	_document = doc;
    }
    
    public void emitErrorMessage(String mesg) {
    	if (_logger == null) {
    		super.emitErrorMessage(mesg);
    	} else {
    		_logger.error(org.scribble.parser.antlr.ANTLRMessageUtil.getMessageText(mesg),
    					org.scribble.parser.antlr.ANTLRMessageUtil.getProperties(mesg, _document));
    	}
    	_errorOccurred = true;
    }
    
    public boolean isErrorOccurred() {
    	return(_errorOccurred);
    }
}




/****************************************************************************
 * Chapter 2 Lexical Strucure (Lexer rules)
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
	(LETTER | UNDERSCORE) (LETTER | DIGIT | UNDERSCORE)*
;

fragment SYMBOL:
	'{' | '}' | '(' | ')' | '[' | ']' | ':' | '/' | '\\' | '.' | '\#'
|
	'&' | '?' | '!'	| UNDERSCORE
;

// Comes after SYMBOL due to an ANTLR syntax highlighting issue
// involving quotes
EXTIDENTIFIER:
	'"' ( ~('\\'|'"') )* '"'
	//'\"' (LETTER | UNDERSCORE) (LETTER | DIGIT | SYMBOL)* '\"'
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
rolename: IDENTIFIER;
payloadtypename: IDENTIFIER;
protocolname: IDENTIFIER;
parametername: IDENTIFIER;
annotationname: IDENTIFIER;
recursionlabelname: IDENTIFIER;
scopename: IDENTIFIER;


/**
 * Section 3.2.1 Package, Module and Module Member Names
 */
/*packagename:
	IDENTIFIER ('.' IDENTIFIER)*
;*/


modulename:
	//packagename '.' IDENTIFIER  // Not working
	IDENTIFIER ('.' IDENTIFIER)* '.' IDENTIFIER
|
	IDENTIFIER
;

membername:
	simplemembername
|
	fullmembername
;

simplemembername:
	payloadtypename
|
	protocolname  /* Generates an ANTLR warning since both are IDENTIFIER
	                 if no backtrack (makes ANTLR think this alternative
	                 is reachable, but actually it isn't) */
;

fullmembername:
	modulename '.' simplemembername  /* Needs backtrack=true
	                                    since the simplemembername can/will
										be eagerly consumed by modulename */
;


/**
 * Section 3.2.2 Top-level Module Structure
 */
module:
	moduledecl (importdecl)* (payloadtypedecl)* (protocoldecl)*
;


/**
 * Section 3.2.3 Module Declarations
 */
moduledecl:
	MODULEKW modulename ';'
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
|
	IMPORTKW modulename ASKW IDENTIFIER ';'
;

importmember:
	FROMKW modulename IMPORTKW simplemembername ';'
|
	FROMKW modulename IMPORTKW simplemembername ASKW IDENTIFIER ';'
;


/**
 * Section 3.4 Payload Type Declarations
 */
payloadtypedecl:
	TYPEKW '<' IDENTIFIER '>' EXTIDENTIFIER FROMKW EXTIDENTIFIER ASKW payloadtypename ';'
;


/**
 * Section 3.5 Message Signatures
 */
messageoperator:
	(LETTER | DIGIT | UNDERSCORE)+
;

messagesignature:
	'(' payload ')'
|
	//messageoperator '(' payload ')'  // Doesn't work (conflict with IDENTIFIER?)
	IDENTIFIER '(' ( payload )+ ')'
;

payload:
	payloadelement (',' payloadelement)*
;

payloadelement:
	payloadtypename
|
	parametername  /* An IDENTIFIER, like payloadtypename, so generates warnings
	                  (unless backtrack=true) */
|
	annotationname ':' payloadtypename
|
	annotationname ':' parametername
;


/**
 * Section 3.6 Protocol Declarations
 */
protocoldecl:
	globalprotocoldecl
|
	localprotocoldecl
;


/**
 * Section 3.7 Global Protocol Declarations
 */
globalprotocoldecl:
	 globalprotocolheader globalprotocoldefinition
|
	globalprotocolheader globalprotocolinstance
;

globalprotocolheader:  // Currently, header is not an explicit category
	GLOBALKW PROTOCOLKW protocolname roledecllist
|
	GLOBALKW PROTOCOLKW protocolname parameterdecllist roledecllist
;

roledecllist:
	'(' roledecl (',' roledecl)* ')'
;

roledecl:
	ROLEKW rolename
|
	ROLEKW rolename ASKW rolename
;

parameterdecllist:
	'<' parameterdecl (',' parameterdecl)* '>'
;

parameterdecl:
	 TYPEKW parametername
|
	 TYPEKW parametername ASKW parametername
|
	 SIGKW parametername
|
	 SIGKW parametername ASKW parametername
;


/**
 * Section 3.7.1 Global Protocol Definitions
 */
globalprotocoldefinition:
	globalprotocolblock
;


/**
 * Section 3.7.2 Global Protocol Instantiation
 */
globalprotocolinstance:
	INSTANTIATESKW membername roleinstantiationlist ';'
|
	INSTANTIATESKW membername argumentlist roleinstantiationlist ';'
;

roleinstantiationlist:
	'(' roleinstantiation (',' roleinstantiation)* ')'
;

roleinstantiation:
	rolename
|
	rolename ASKW rolename
;

argumentlist:
	'<' argument (',' argument)* '>'
;

argument:
	messagesignature
|
	messagesignature ASKW parametername
|
	payloadtypename
|
	payloadtypename ASKW parametername
|
	parametername  // generates warnings unless backtrack=true
|
	parametername ASKW parametername
;


/**
 * Section 3.7.3 Global Interaction Sequences and Blocks
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
	globalrecursion
|
	globalcontinue
|
	globalparallel
|
	globalinterruptible
|
	globaldo
/*|
	globalspawn*/
;


/**
 * Section 3.7.4 Global Message Transfer
 */
globalmessagetransfer:
	message FROMKW rolename TOKW rolename (',' rolename )* ';'
;

message:
	messagesignature
|
	parametername
;


/**
 * Section 3.7.5 Global Choice
 */
globalchoice:
	CHOICEKW ATKW rolename globalprotocolblock (ORKW globalprotocolblock)*
;


/**
 * Section 3.7.6 Global Recursion
 */
globalrecursion:
	RECKW recursionlabelname globalprotocolblock
;

globalcontinue:
	CONTINUEKW recursionlabelname ';'
;


/**
 * Section 3.7.7 Global Parallel
 */
globalparallel:
	PARKW globalprotocolblock (ANDKW globalprotocolblock)*
;


/**
 * Section 3.7.8 Global Interruptible
 */
globalinterruptible:
	INTERRUPTIBLEKW globalprotocolblock WITHKW '{' (globalinterrupt)* '}'
|
	INTERRUPTIBLEKW scopename ':' globalprotocolblock WITHKW '{' (globalinterrupt)* '}'
;

globalinterrupt:
	message (',' message)* BYKW rolename ';'
;


/**
 * Section 3.7.9 Global Do
 */
globaldo:
	DOKW membername roleinstantiationlist ';'
|
	DOKW membername argumentlist roleinstantiationlist ';'
|
	DOKW scopename ':' membername roleinstantiationlist ';'
|
	DOKW scopename ':' membername argumentlist roleinstantiationlist ';'
;


/**
 * TODO: Global Spawn
 */
/*globalspawn:
	SPAWNKW membername '(' roleinstantiationlist ')' ';'
|
	SPAWNKW membername '<' argumentlist '>' '(' roleinstantiationlist ')' ';'
;*/


/*
 * Section 3.8 Local Protocol Declarations
 */
localprotocoldecl:
	localprotocolheader localprotocoldefinition
|
	localprotocolheader localprotocolinstance
;

localprotocolheader:
	LOCALKW PROTOCOLKW protocolname ATKW rolename roledecllist
|
	LOCALKW PROTOCOLKW protocolname ATKW rolename parameterdecllist roledecllist
;


/**
 * Section 3.8.1 Local Protocol Definitions
 */
localprotocoldefinition:
	localprotocolblock
;


/**
 * Section 3.8.2 Local Protocol Instantiation
 */
localprotocolinstance:
	INSTANTIATESKW membername roleinstantiationlist ';'
|
	INSTANTIATESKW membername argumentlist roleinstantiationlist ';'
;


/**
 * Section 3.8.3 Local Interaction Blocks and Sequences
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
|
	localinterruptible
|
	localdo
/*|
	localspawn*/
;


/**
 * Section 3.8.4 Local Send and Receive
 */
localsend:
	message TOKW rolename (',' rolename)* ';'
;

localreceive:
	message FROMKW IDENTIFIER ';'
;


/**
 * Section 3.8.5 Local Choice
 */
localchoice:
	CHOICEKW ATKW rolename localprotocolblock (ORKW localprotocolblock)*
;


/**
 * Section 3.8.6 Local Recursion
 */
localrecursion:
	RECKW recursionlabelname localprotocolblock
;

localcontinue:
	CONTINUEKW recursionlabelname ';'
;


/**
 * Section 3.8.7 Local Parallel
 */
localparallel:
	PARKW localprotocolblock (ANDKW localprotocolblock)*
;


/**
 * Section 3.8.8 Local Interruptible
 */
localinterruptible:
	/*INTERRUPTIBLEKW scopename ':' localprotocolblock WITHKW '{' localthrowandorcatch '}'
|*/
	INTERRUPTIBLEKW scopename ':' localprotocolblock WITHKW '{' (localcatch)* '}'
|
	INTERRUPTIBLEKW scopename ':' localprotocolblock WITHKW '{' localthrow (localcatch)* '}'
;

/*localthrowandorcatch:
	localthrow (localcatch)*
|
	(localcatch)+
;*/

localthrow:
	THROWSKW message (',' message)* TOKW rolename (',' rolename)* ';'
;

localcatch:
	CATCHESKW message (',' message)* FROMKW rolename ';'
;


/**
 * Section 3.8.9 Local Do
 */
localdo:
	DOKW membername roleinstantiationlist ';'
|
	DOKW membername argumentlist roleinstantiationlist ';'
|
	DOKW scopename ':' membername roleinstantiationlist ';'
|
	DOKW scopename ':' membername argumentlist roleinstantiationlist ';'
;

