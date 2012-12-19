grammar ScribbleProtocol;

options {
	output=AST;
	backtrack=true;
}

tokens {
	PLUS 	= '+' ;
	MINUS	= '-' ;
	MULT	= '*' ;
	DIV	= '/' ;
	FULLSTOP = '.' ;
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
	private org.scribble.protocol.parser.IssueLogger _logger=null;
	private String _document=null;
	private boolean _errorOccurred=false;
	
	public static void main(String[] args) throws Exception {
        ScribbleProtocolLexer lex = new ScribbleProtocolLexer(new ANTLRFileStream(args[0]));
       	CommonTokenStream tokens = new CommonTokenStream(lex);

		ScribbleProtocolParser parser = new ScribbleProtocolParser(tokens);

		ProtocolTreeAdaptor adaptor=new ProtocolTreeAdaptor(null);
		adaptor.setParser(parser);
		
		parser.setTreeAdaptor(adaptor);
		
        try {
            ScribbleProtocolParser.module_return r=parser.module();
            
            //CommonTree t=(CommonTree)r.getTree();
            
            //Tree t=(Tree)r.getTree();
            
            //System.out.println(t.toStringTree());
            
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
    }
    
    public void setIssueLogger(org.scribble.protocol.parser.IssueLogger logger) {
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
 
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ 	{ $channel = HIDDEN; } ;

ML_COMMENT
    :   '/*' (options {greedy=false;} : .)* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT : '//' (options {greedy=false;} : .)* '\n' {$channel=HIDDEN;} ;


LETTER : ('a'..'z'|'A'..'Z') ;

DIGIT : '0'..'9' ;

SYMBOL : '{' | '}' | '[' | ']' | ':' | '/' | '\\' | '.' | '#' | '&' | '?' | '!' ;
//SYMBOL : '{' | '}' | '(' | ')' | '[' | ']' | ':' | '/' | '\\' | '.' | '#' | '&' | '?' | '!' ;    // Issue is that causes problems with protocol name with '(' and no space

IDENTIFIER : (LETTER | '_')(LETTER|DIGIT|'_')* ;

EXTIDENTIFIER : (LETTER | '_' | SYMBOL)(LETTER | DIGIT | '_' | SYMBOL)* ;



/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
 
 
module: packageDecl ( importDecl )* ( payloadTypeDecl )* ( protocolDecl )* ;

packageName: IDENTIFIER ;  // ( '.' IDENTIFIER )* ;

simpleName: IDENTIFIER ;

packageDecl: 'package'^ packageName ';'! ;

importDecl: 'import'^ packageName | 'from' packageName 'import' IDENTIFIER ( 'as' IDENTIFIER )? ';'! ;		// Added ;

payloadTypeDecl: 'type'^ '<'! IDENTIFIER '>'! EXTIDENTIFIER 'from' EXTIDENTIFIER 'as' IDENTIFIER ';'! ;		// Added ;

protocolDecl: globalProtocolDecl | localProtocolDecl ;



messageOperator: ( LETTER  | DIGIT | '_' )* ; // Shouldn't this be IDENTIFIER?

messageSignature: messageOperator '('! ( payloadType ( ','! payloadType )* )? ')'! | payloadType ;		// Changed, so could just be payloadType, instead of putting ID on message
 
payloadType: ( IDENTIFIER ':' )? IDENTIFIER ; 



globalProtocolDecl: 'global'^ 'protocol' simpleName roleDefs globalProtocolBody ;

//globalProtocolDefinition: '('! ( 'role'^ roleName ( ','! 'role'^ roleName )*  )? ')'! globalProtocolBody ;		// Simplified as does not match local protocol definition

//roleList: 'role' roleName ( ','! 'role' roleName )* ;

roleDefs: '('! roleDef ( ','! roleDef )* ')'! ;

roleDef: 'role'^ simpleName ;


roleName: IDENTIFIER ;

parameter: 'sig' IDENTIFIER  ;

parameterList: parameter ( ','! parameter )* ;				// created parameter rule to make easier to parse

argumentList: messageSignature ( ',' messageSignature )* ;


globalProtocolBody: globalInteractionBlock ;

globalInteractionBlock: '{'! globalInteractionSequence '}'! ;
 
globalInteractionSequence: ( globalInteraction )* ;

globalInteraction: message
	| choice 
	| parallel 
	| recursion 
	| continueDef
	| interruptible 
	| doDef
	| spawn ;



message: messageSignature 'from' roleName 'to' roleName ';'! ;		// Should include a ; at the end, and what about multicast?
																	// should not have optional message sig - and not sure about identifier, so changed message sig

choice: 'choice'^ 'at' roleName globalInteractionBlock ( 'or' globalInteractionBlock )* ;  // Should this not be + ?

parallel: 'par'^ globalInteractionBlock ( 'and' globalInteractionBlock )* ;   // Should this not be + ?

recursion: 'rec'^ IDENTIFIER globalInteractionBlock ;

continueDef: 'continue'^ IDENTIFIER ;		// Rule name changed as caused java compilation error

interruptible: 'interruptible'^ globalInteractionBlock 'with' '{'! ( interrupt )+ '}'! ;		// In the sending role, what determines at the point where the interrupting message can be sent?

interrupt: messageSignature ( ','! messageSignature )* 'by' roleName ( ','! roleName )* ;

doDef: 'do'^ IDENTIFIER ( '<'! argumentList '>'! )? '('! roleInstantiationList ')'! ';'! ;		// Rule name changed as caused java compilation error

roleInstantiationList: roleName 'as' roleName ( ','! roleName 'as' roleName )* ;
	
spawn: roleName 'spawns' IDENTIFIER '<'! ( argumentList )? '>'! '('! roleInstantiationList ')'! ';'! ;



localProtocolDecl: 'local'^ 'protocol' IDENTIFIER 'at' roleName roleDefs localProtocolBody ;


localProtocolBody: localInteractionBlock ;

localInteractionBlock: '{'! localInteractionSequence '}'! ;
 
localInteractionSequence: ( localInteraction )* ;

localInteraction: send 
	| receive 
	| localChoice 
	| localParallel 
	| localRecursion 
	| localContinue 
	| localLnterruptible
	| create				// Not shown in spec
	| enter ;				// Not shown in spec - also refers to 'calls' construct but assume means spawn?


send: messageSignature 'to' roleName ';'! ;		// Does not say terminated by ; in spec

receive: messageSignature 'from' roleName ';'! ;		// Does not say terminated by ; in spec

localChoice: 'choice'^ 'at' roleName localInteractionBlock ( 'or' localInteractionBlock )* ;		// Should be + ?

localParallel: 'par'^ localInteractionBlock ( 'and' localInteractionBlock )* ;		// Should be + ?

localRecursion: 'rec'^ IDENTIFIER localInteractionBlock ;

localContinue: 'continue'^ IDENTIFIER ';'! ;		// Does not say terminated by ; in spec
	
localLnterruptible: 'interruptible'^ localInteractionBlock ( throwDef )? ( catchDef )? ;		// interruptible in spec - have changed structure slightly

throwDef: 'throw'^ messageSignature ( ','! messageSignature )* ;		// Have removed initial bracket and rule name changed as caused java compilation error

catchDef: 'catch'^ messageSignature ( ','! messageSignature )* ;		// Have removed initial brakcet


create: 'create'^ IDENTIFIER '<'! parameterList '>'! '('! ( roleInstantiationList )? ')'! ';'! ;		// Does not say terminated by ; in spec

enter: 'enter'^ IDENTIFIER 'as' roleName ';'! ;		// Does not say terminated by ; in spec

