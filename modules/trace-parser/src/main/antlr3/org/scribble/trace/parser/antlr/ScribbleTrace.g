grammar ScribbleTrace;

options
{
	output=AST;
	backtrack=true;
}

tokens
{
	TRACEKW = 'trace';
	BYKW = 'by';
	SHOWSKW = 'shows';
	
	ROLEKW = 'role';
	SIMULATINGKW = 'simulating';
	PROTOCOLKW = 'protocol';
	ASKW = 'as';

	FROMKW = 'from';
	TOKW = 'to';
}


/*------------------------------------------------------------------
 * JAVA SPECIFIC DEFINITIONS
 *------------------------------------------------------------------*/
 
@header {
package org.scribble.trace.parser.antlr;
}
   
@lexer::header {
package org.scribble.trace.parser.antlr;
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
    		_logger.error(org.scribble.trace.parser.antlr.ANTLRMessageUtil.getMessageText(mesg),
    					org.scribble.trace.parser.antlr.ANTLRMessageUtil.getProperties(mesg, _document));
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

sentence: IDENTIFIER+;

trace: tracedefn ( roledefn )* ( stepdefn )* ;

tracedefn: TRACEKW IDENTIFIER ( BYKW sentence ( SHOWSKW sentence )? )? ';' ;

module: IDENTIFIER ( '.' IDENTIFIER )* ;

roledefn: ROLEKW IDENTIFIER ( SIMULATINGKW module PROTOCOLKW IDENTIFIER ( ASKW IDENTIFIER )? )? ';' ;

stepdefn: messagetransfer ;

messagetransfer: IDENTIFIER '(' parameter ( ',' parameter )* ')' FROMKW IDENTIFIER TOKW IDENTIFIER ( ',' IDENTIFIER )* ';' ;

parameter: EXTIDENTIFIER ( '=' EXTIDENTIFIER )? ;

