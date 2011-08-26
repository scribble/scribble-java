grammar ScribbleProtocol;

options {
	output=AST;
	backtrack=true;
}

tokens {
	INTERACTION = 'interaction' ;
	PLUS 	= '+' ;
	MINUS	= '-' ;
	MULT	= '*' ;
	DIV	= '/' ;
	FULLSTOP = '.' ;
}

@header {
package org.scribble.protocol.parser.antlr;
}
   
@lexer::header {
package org.scribble.protocol.parser.antlr;
}
   
@members {
	private org.scribble.common.logging.Journal m_journal=null;
	private String m_document=null;
	private boolean m_errorOccurred=false;
	
	public static void main(String[] args) throws Exception {
        ScribbleProtocolLexer lex = new ScribbleProtocolLexer(new ANTLRFileStream(args[0]));
       	CommonTokenStream tokens = new CommonTokenStream(lex);

		ScribbleProtocolParser parser = new ScribbleProtocolParser(tokens);

		ProtocolTreeAdaptor adaptor=new ProtocolTreeAdaptor(null, null);
		adaptor.setParser(parser);
		
		parser.setTreeAdaptor(adaptor);
		
        try {
            ScribbleProtocolParser.description_return r=parser.description();
            
            //CommonTree t=(CommonTree)r.getTree();
            
            //Tree t=(Tree)r.getTree();
            
            //System.out.println(t.toStringTree());
            
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
    }
    
    public void setJournal(org.scribble.common.logging.Journal journal) {
    	m_journal = journal;
    }
    
    public void setDocument(String doc) {
    	m_document = doc;
    }
    
    public void emitErrorMessage(String mesg) {
    	if (m_journal == null) {
    		super.emitErrorMessage(mesg);
    	} else {
    		m_journal.error(ANTLRMessageUtil.getMessageText(mesg),
    					ANTLRMessageUtil.getProperties(mesg, m_document));
    	}
    	m_errorOccurred = true;
    }
    
    public boolean isErrorOccurred() {
    	return(m_errorOccurred);
    }
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

description: ( ( ANNOTATION )* ( importProtocolStatement | importTypeStatement ) )* ( ANNOTATION )* protocolDef ;

importProtocolStatement: 'import' 'protocol' importProtocolDef ( ','! importProtocolDef )* ';'! ;

importProtocolDef: ID 'from'! StringLiteral;
						
importTypeStatement: 'import' ( simpleName )? importTypeDef ( ','! importTypeDef )* ( 'from'! StringLiteral )? ';'! ;

importTypeDef: ( dataTypeDef 'as'! )? ID ;

dataTypeDef: StringLiteral ;

simpleName: ID ;

protocolDef: 'protocol'^ protocolName ( 'at' roleName )? ( parameterDefs )? blockDef ;

protocolName: ID ;

parameterDefs: '('! parameterDef ( ','! parameterDef )* ')'! ;

parameterDef: ( typeReferenceDef | 'role' ) simpleName ;

blockDef: '{'! activityListDef '}'! ;

activityListDef: ( ( ANNOTATION )* activityDef )* ;

activityDef: ( introducesDef | interactionDef | inlineDef | runDef | recursionDef | endDef ) ';'! | 
			choiceDef | directedChoiceDef | parallelDef | repeatDef | unorderedDef |
			recBlockDef | globalEscapeDef | protocolDef ;

introducesDef: roleDef 'introduces' roleDef ( ','! roleDef )* ;

roleDef: ID ;

roleName: ID ;

typeReferenceDef: ID ;

interactionSignatureDef: ( typeReferenceDef | ID '('! ( typeReferenceDef ( ','! typeReferenceDef )* )? ')'! ) ;

interactionDef: interactionSignatureDef ( 'from' roleName ( 'to' roleName ( ','! roleName )* )? |
							'to' roleName ( ','! roleName )* ) ;

choiceDef: 'choice'^ ( 'at' roleName )? blockDef ( 'or' blockDef )* ;

directedChoiceDef: ( 'from' roleName )? ( 'to' roleName ( ','! roleName )* )? '{' ( onMessageDef )+ '}' ;

onMessageDef: interactionSignatureDef ':' activityList ; 

activityList: ( ( ANNOTATION )* activityDef )* ;

repeatDef: 'repeat'^ ( 'at' roleName ( ','! roleName )* )? blockDef ;

recBlockDef: 'rec'^ labelName blockDef ;

labelName: ID ;

recursionDef: labelName ;

endDef: 'end'^ ;

runDef: 'run'^ protocolRefDef ( '('! parameter ( ','! parameter )* ')'! )? 'from' roleName ;

protocolRefDef: ID ( 'at' roleName )? ;

declarationName: ID ;

parameter: declarationName ;

inlineDef: 'inline'^ protocolRefDef ( '('! parameter ( ','! parameter )* ')'! )? ;

parallelDef: 'parallel'^ blockDef ( 'and' blockDef )* ;

globalEscapeDef: 'do'^ blockDef ( interruptDef )+ ;

interruptDef: 'interrupt'^ blockDef ;

unorderedDef: 'unordered'^ blockDef ;


/*-----------------------------------------------
TO DO:
Declaration (variables) possibly - but that may need
lookahead to avoid conflict with interactions.
-------------------------------------------------*/

expr	: term ( ( PLUS | MINUS )  term )* ;

term	: factor ( ( MULT | DIV ) factor )* ;

factor	: NUMBER ;


/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

ID : ('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;

NUMBER	: (DIGIT)+ ;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ 	{ $channel = HIDDEN; } ;

fragment DIGIT	: '0'..'9' ;

ANNOTATION : '[[' (options {greedy=false;} : .)* ']]' ;

ML_COMMENT
    :   '/*' (options {greedy=false;} : .)* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT : '//' (options {greedy=false;} : .)* '\n' {$channel=HIDDEN;} ;

StringLiteral: '"' ( ~('\\'|'"') )* '"' ;
