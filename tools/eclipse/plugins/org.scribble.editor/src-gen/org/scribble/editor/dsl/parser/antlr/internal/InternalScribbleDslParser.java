package org.scribble.editor.dsl.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.scribble.editor.dsl.services.ScribbleDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalScribbleDslParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'module'", "';'", "'.'", "'import'", "'as'", "'from'", "'type'", "'<'", "'>'", "'('", "','", "')'", "':'", "'global'", "'protocol'", "'instantiates'", "'role'", "'sig'", "'{'", "'}'", "'to'", "'choice'", "'at'", "'or'", "'rec'", "'continue'", "'par'", "'and'", "'interruptible'", "'with'", "'by'", "'do'", "'local'", "'throw'", "'catches'"
    };
    public static final int RULE_ID=4;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int EOF=-1;
    public static final int T__19=19;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_INT=6;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_SL_COMMENT=8;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_STRING=5;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int RULE_WS=9;

    // delegates
    // delegators


        public InternalScribbleDslParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalScribbleDslParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalScribbleDslParser.tokenNames; }
    public String getGrammarFileName() { return "../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g"; }



     	private ScribbleDslGrammarAccess grammarAccess;
     	
        public InternalScribbleDslParser(TokenStream input, ScribbleDslGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Module";	
       	}
       	
       	@Override
       	protected ScribbleDslGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleModule"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:67:1: entryRuleModule returns [EObject current=null] : iv_ruleModule= ruleModule EOF ;
    public final EObject entryRuleModule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModule = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:68:2: (iv_ruleModule= ruleModule EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:69:2: iv_ruleModule= ruleModule EOF
            {
             newCompositeNode(grammarAccess.getModuleRule()); 
            pushFollow(FOLLOW_ruleModule_in_entryRuleModule75);
            iv_ruleModule=ruleModule();

            state._fsp--;

             current =iv_ruleModule; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModule85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModule"


    // $ANTLR start "ruleModule"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:76:1: ruleModule returns [EObject current=null] : (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' ( (lv_imports_3_0= ruleImportDecl ) )* ( (lv_types_4_0= rulePayloadTypeDecl ) )* ( ( (lv_globals_5_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_6_0= ruleLocalProtocolDecl ) ) )* ) ;
    public final EObject ruleModule() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_imports_3_0 = null;

        EObject lv_types_4_0 = null;

        EObject lv_globals_5_0 = null;

        EObject lv_locals_6_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:79:28: ( (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' ( (lv_imports_3_0= ruleImportDecl ) )* ( (lv_types_4_0= rulePayloadTypeDecl ) )* ( ( (lv_globals_5_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_6_0= ruleLocalProtocolDecl ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:80:1: (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' ( (lv_imports_3_0= ruleImportDecl ) )* ( (lv_types_4_0= rulePayloadTypeDecl ) )* ( ( (lv_globals_5_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_6_0= ruleLocalProtocolDecl ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:80:1: (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' ( (lv_imports_3_0= ruleImportDecl ) )* ( (lv_types_4_0= rulePayloadTypeDecl ) )* ( ( (lv_globals_5_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_6_0= ruleLocalProtocolDecl ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:80:3: otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' ( (lv_imports_3_0= ruleImportDecl ) )* ( (lv_types_4_0= rulePayloadTypeDecl ) )* ( ( (lv_globals_5_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_6_0= ruleLocalProtocolDecl ) ) )*
            {
            otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleModule122); 

                	newLeafNode(otherlv_0, grammarAccess.getModuleAccess().getModuleKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:84:1: ( (lv_name_1_0= ruleModuleName ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:85:1: (lv_name_1_0= ruleModuleName )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:85:1: (lv_name_1_0= ruleModuleName )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:86:3: lv_name_1_0= ruleModuleName
            {
             
            	        newCompositeNode(grammarAccess.getModuleAccess().getNameModuleNameParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleModuleName_in_ruleModule143);
            lv_name_1_0=ruleModuleName();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getModuleRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ModuleName");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleModule155); 

                	newLeafNode(otherlv_2, grammarAccess.getModuleAccess().getSemicolonKeyword_2());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:106:1: ( (lv_imports_3_0= ruleImportDecl ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==14||LA1_0==16) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:107:1: (lv_imports_3_0= ruleImportDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:107:1: (lv_imports_3_0= ruleImportDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:108:3: lv_imports_3_0= ruleImportDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getModuleAccess().getImportsImportDeclParserRuleCall_3_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleImportDecl_in_ruleModule176);
            	    lv_imports_3_0=ruleImportDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getModuleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"imports",
            	            		lv_imports_3_0, 
            	            		"ImportDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:124:3: ( (lv_types_4_0= rulePayloadTypeDecl ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==17) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:125:1: (lv_types_4_0= rulePayloadTypeDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:125:1: (lv_types_4_0= rulePayloadTypeDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:126:3: lv_types_4_0= rulePayloadTypeDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getModuleAccess().getTypesPayloadTypeDeclParserRuleCall_4_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePayloadTypeDecl_in_ruleModule198);
            	    lv_types_4_0=rulePayloadTypeDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getModuleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"types",
            	            		lv_types_4_0, 
            	            		"PayloadTypeDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:142:3: ( ( (lv_globals_5_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_6_0= ruleLocalProtocolDecl ) ) )*
            loop3:
            do {
                int alt3=3;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==24) ) {
                    alt3=1;
                }
                else if ( (LA3_0==43) ) {
                    alt3=2;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:142:4: ( (lv_globals_5_0= ruleGlobalProtocolDecl ) )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:142:4: ( (lv_globals_5_0= ruleGlobalProtocolDecl ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:143:1: (lv_globals_5_0= ruleGlobalProtocolDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:143:1: (lv_globals_5_0= ruleGlobalProtocolDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:144:3: lv_globals_5_0= ruleGlobalProtocolDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getModuleAccess().getGlobalsGlobalProtocolDeclParserRuleCall_5_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalProtocolDecl_in_ruleModule221);
            	    lv_globals_5_0=ruleGlobalProtocolDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getModuleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"globals",
            	            		lv_globals_5_0, 
            	            		"GlobalProtocolDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:161:6: ( (lv_locals_6_0= ruleLocalProtocolDecl ) )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:161:6: ( (lv_locals_6_0= ruleLocalProtocolDecl ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:162:1: (lv_locals_6_0= ruleLocalProtocolDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:162:1: (lv_locals_6_0= ruleLocalProtocolDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:163:3: lv_locals_6_0= ruleLocalProtocolDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getModuleAccess().getLocalsLocalProtocolDeclParserRuleCall_5_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLocalProtocolDecl_in_ruleModule248);
            	    lv_locals_6_0=ruleLocalProtocolDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getModuleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"locals",
            	            		lv_locals_6_0, 
            	            		"LocalProtocolDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModule"


    // $ANTLR start "entryRuleModuleName"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:187:1: entryRuleModuleName returns [String current=null] : iv_ruleModuleName= ruleModuleName EOF ;
    public final String entryRuleModuleName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleModuleName = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:188:2: (iv_ruleModuleName= ruleModuleName EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:189:2: iv_ruleModuleName= ruleModuleName EOF
            {
             newCompositeNode(grammarAccess.getModuleNameRule()); 
            pushFollow(FOLLOW_ruleModuleName_in_entryRuleModuleName287);
            iv_ruleModuleName=ruleModuleName();

            state._fsp--;

             current =iv_ruleModuleName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleName298); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModuleName"


    // $ANTLR start "ruleModuleName"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:196:1: ruleModuleName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleModuleName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:199:28: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:200:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:200:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:200:6: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleModuleName338); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getModuleNameAccess().getIDTerminalRuleCall_0()); 
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:207:1: (kw= '.' this_ID_2= RULE_ID )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==13) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:208:2: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,13,FOLLOW_13_in_ruleModuleName357); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getModuleNameAccess().getFullStopKeyword_1_0()); 
            	        
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleModuleName372); 

            	    		current.merge(this_ID_2);
            	        
            	     
            	        newLeafNode(this_ID_2, grammarAccess.getModuleNameAccess().getIDTerminalRuleCall_1_1()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModuleName"


    // $ANTLR start "entryRuleImportDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:228:1: entryRuleImportDecl returns [EObject current=null] : iv_ruleImportDecl= ruleImportDecl EOF ;
    public final EObject entryRuleImportDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImportDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:229:2: (iv_ruleImportDecl= ruleImportDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:230:2: iv_ruleImportDecl= ruleImportDecl EOF
            {
             newCompositeNode(grammarAccess.getImportDeclRule()); 
            pushFollow(FOLLOW_ruleImportDecl_in_entryRuleImportDecl419);
            iv_ruleImportDecl=ruleImportDecl();

            state._fsp--;

             current =iv_ruleImportDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportDecl429); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleImportDecl"


    // $ANTLR start "ruleImportDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:237:1: ruleImportDecl returns [EObject current=null] : (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember ) ;
    public final EObject ruleImportDecl() throws RecognitionException {
        EObject current = null;

        EObject this_ImportModule_0 = null;

        EObject this_ImportMember_1 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:240:28: ( (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:241:1: (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:241:1: (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==14) ) {
                alt5=1;
            }
            else if ( (LA5_0==16) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:242:5: this_ImportModule_0= ruleImportModule
                    {
                     
                            newCompositeNode(grammarAccess.getImportDeclAccess().getImportModuleParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleImportModule_in_ruleImportDecl476);
                    this_ImportModule_0=ruleImportModule();

                    state._fsp--;

                     
                            current = this_ImportModule_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:252:5: this_ImportMember_1= ruleImportMember
                    {
                     
                            newCompositeNode(grammarAccess.getImportDeclAccess().getImportMemberParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleImportMember_in_ruleImportDecl503);
                    this_ImportMember_1=ruleImportMember();

                    state._fsp--;

                     
                            current = this_ImportMember_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleImportDecl"


    // $ANTLR start "entryRuleImportModule"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:268:1: entryRuleImportModule returns [EObject current=null] : iv_ruleImportModule= ruleImportModule EOF ;
    public final EObject entryRuleImportModule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImportModule = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:269:2: (iv_ruleImportModule= ruleImportModule EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:270:2: iv_ruleImportModule= ruleImportModule EOF
            {
             newCompositeNode(grammarAccess.getImportModuleRule()); 
            pushFollow(FOLLOW_ruleImportModule_in_entryRuleImportModule538);
            iv_ruleImportModule=ruleImportModule();

            state._fsp--;

             current =iv_ruleImportModule; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportModule548); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleImportModule"


    // $ANTLR start "ruleImportModule"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:277:1: ruleImportModule returns [EObject current=null] : (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' ) ;
    public final EObject ruleImportModule() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_alias_3_0=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:280:28: ( (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:281:1: (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:281:1: (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:281:3: otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,14,FOLLOW_14_in_ruleImportModule585); 

                	newLeafNode(otherlv_0, grammarAccess.getImportModuleAccess().getImportKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:285:1: ( (lv_name_1_0= ruleModuleName ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:286:1: (lv_name_1_0= ruleModuleName )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:286:1: (lv_name_1_0= ruleModuleName )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:287:3: lv_name_1_0= ruleModuleName
            {
             
            	        newCompositeNode(grammarAccess.getImportModuleAccess().getNameModuleNameParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleModuleName_in_ruleImportModule606);
            lv_name_1_0=ruleModuleName();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getImportModuleRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ModuleName");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:303:2: (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==15) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:303:4: otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,15,FOLLOW_15_in_ruleImportModule619); 

                        	newLeafNode(otherlv_2, grammarAccess.getImportModuleAccess().getAsKeyword_2_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:307:1: ( (lv_alias_3_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:308:1: (lv_alias_3_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:308:1: (lv_alias_3_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:309:3: lv_alias_3_0= RULE_ID
                    {
                    lv_alias_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleImportModule636); 

                    			newLeafNode(lv_alias_3_0, grammarAccess.getImportModuleAccess().getAliasIDTerminalRuleCall_2_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getImportModuleRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"alias",
                            		lv_alias_3_0, 
                            		"ID");
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleImportModule655); 

                	newLeafNode(otherlv_4, grammarAccess.getImportModuleAccess().getSemicolonKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleImportModule"


    // $ANTLR start "entryRuleImportMember"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:337:1: entryRuleImportMember returns [EObject current=null] : iv_ruleImportMember= ruleImportMember EOF ;
    public final EObject entryRuleImportMember() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImportMember = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:338:2: (iv_ruleImportMember= ruleImportMember EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:339:2: iv_ruleImportMember= ruleImportMember EOF
            {
             newCompositeNode(grammarAccess.getImportMemberRule()); 
            pushFollow(FOLLOW_ruleImportMember_in_entryRuleImportMember691);
            iv_ruleImportMember=ruleImportMember();

            state._fsp--;

             current =iv_ruleImportMember; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportMember701); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleImportMember"


    // $ANTLR start "ruleImportMember"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:346:1: ruleImportMember returns [EObject current=null] : (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' ) ;
    public final EObject ruleImportMember() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_member_3_0=null;
        Token otherlv_4=null;
        Token lv_alias_5_0=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:349:28: ( (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:350:1: (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:350:1: (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:350:3: otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_16_in_ruleImportMember738); 

                	newLeafNode(otherlv_0, grammarAccess.getImportMemberAccess().getFromKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:354:1: ( (lv_name_1_0= ruleModuleName ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:355:1: (lv_name_1_0= ruleModuleName )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:355:1: (lv_name_1_0= ruleModuleName )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:356:3: lv_name_1_0= ruleModuleName
            {
             
            	        newCompositeNode(grammarAccess.getImportMemberAccess().getNameModuleNameParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleModuleName_in_ruleImportMember759);
            lv_name_1_0=ruleModuleName();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getImportMemberRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ModuleName");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,14,FOLLOW_14_in_ruleImportMember771); 

                	newLeafNode(otherlv_2, grammarAccess.getImportMemberAccess().getImportKeyword_2());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:376:1: ( (lv_member_3_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:377:1: (lv_member_3_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:377:1: (lv_member_3_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:378:3: lv_member_3_0= RULE_ID
            {
            lv_member_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleImportMember788); 

            			newLeafNode(lv_member_3_0, grammarAccess.getImportMemberAccess().getMemberIDTerminalRuleCall_3_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getImportMemberRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"member",
                    		lv_member_3_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:394:2: (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==15) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:394:4: otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) )
                    {
                    otherlv_4=(Token)match(input,15,FOLLOW_15_in_ruleImportMember806); 

                        	newLeafNode(otherlv_4, grammarAccess.getImportMemberAccess().getAsKeyword_4_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:398:1: ( (lv_alias_5_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:399:1: (lv_alias_5_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:399:1: (lv_alias_5_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:400:3: lv_alias_5_0= RULE_ID
                    {
                    lv_alias_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleImportMember823); 

                    			newLeafNode(lv_alias_5_0, grammarAccess.getImportMemberAccess().getAliasIDTerminalRuleCall_4_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getImportMemberRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"alias",
                            		lv_alias_5_0, 
                            		"ID");
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,12,FOLLOW_12_in_ruleImportMember842); 

                	newLeafNode(otherlv_6, grammarAccess.getImportMemberAccess().getSemicolonKeyword_5());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleImportMember"


    // $ANTLR start "entryRulePayloadTypeDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:428:1: entryRulePayloadTypeDecl returns [EObject current=null] : iv_rulePayloadTypeDecl= rulePayloadTypeDecl EOF ;
    public final EObject entryRulePayloadTypeDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePayloadTypeDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:429:2: (iv_rulePayloadTypeDecl= rulePayloadTypeDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:430:2: iv_rulePayloadTypeDecl= rulePayloadTypeDecl EOF
            {
             newCompositeNode(grammarAccess.getPayloadTypeDeclRule()); 
            pushFollow(FOLLOW_rulePayloadTypeDecl_in_entryRulePayloadTypeDecl878);
            iv_rulePayloadTypeDecl=rulePayloadTypeDecl();

            state._fsp--;

             current =iv_rulePayloadTypeDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePayloadTypeDecl888); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePayloadTypeDecl"


    // $ANTLR start "rulePayloadTypeDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:437:1: rulePayloadTypeDecl returns [EObject current=null] : (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' ) ;
    public final EObject rulePayloadTypeDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_schema_2_0=null;
        Token otherlv_3=null;
        Token lv_type_4_0=null;
        Token otherlv_5=null;
        Token lv_location_6_0=null;
        Token otherlv_7=null;
        Token lv_alias_8_0=null;
        Token otherlv_9=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:440:28: ( (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:441:1: (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:441:1: (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:441:3: otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_17_in_rulePayloadTypeDecl925); 

                	newLeafNode(otherlv_0, grammarAccess.getPayloadTypeDeclAccess().getTypeKeyword_0());
                
            otherlv_1=(Token)match(input,18,FOLLOW_18_in_rulePayloadTypeDecl937); 

                	newLeafNode(otherlv_1, grammarAccess.getPayloadTypeDeclAccess().getLessThanSignKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:449:1: ( (lv_schema_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:450:1: (lv_schema_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:450:1: (lv_schema_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:451:3: lv_schema_2_0= RULE_ID
            {
            lv_schema_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadTypeDecl954); 

            			newLeafNode(lv_schema_2_0, grammarAccess.getPayloadTypeDeclAccess().getSchemaIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPayloadTypeDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"schema",
                    		lv_schema_2_0, 
                    		"ID");
            	    

            }


            }

            otherlv_3=(Token)match(input,19,FOLLOW_19_in_rulePayloadTypeDecl971); 

                	newLeafNode(otherlv_3, grammarAccess.getPayloadTypeDeclAccess().getGreaterThanSignKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:471:1: ( (lv_type_4_0= RULE_STRING ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:472:1: (lv_type_4_0= RULE_STRING )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:472:1: (lv_type_4_0= RULE_STRING )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:473:3: lv_type_4_0= RULE_STRING
            {
            lv_type_4_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rulePayloadTypeDecl988); 

            			newLeafNode(lv_type_4_0, grammarAccess.getPayloadTypeDeclAccess().getTypeSTRINGTerminalRuleCall_4_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPayloadTypeDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"type",
                    		lv_type_4_0, 
                    		"STRING");
            	    

            }


            }

            otherlv_5=(Token)match(input,16,FOLLOW_16_in_rulePayloadTypeDecl1005); 

                	newLeafNode(otherlv_5, grammarAccess.getPayloadTypeDeclAccess().getFromKeyword_5());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:493:1: ( (lv_location_6_0= RULE_STRING ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:494:1: (lv_location_6_0= RULE_STRING )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:494:1: (lv_location_6_0= RULE_STRING )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:495:3: lv_location_6_0= RULE_STRING
            {
            lv_location_6_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rulePayloadTypeDecl1022); 

            			newLeafNode(lv_location_6_0, grammarAccess.getPayloadTypeDeclAccess().getLocationSTRINGTerminalRuleCall_6_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPayloadTypeDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"location",
                    		lv_location_6_0, 
                    		"STRING");
            	    

            }


            }

            otherlv_7=(Token)match(input,15,FOLLOW_15_in_rulePayloadTypeDecl1039); 

                	newLeafNode(otherlv_7, grammarAccess.getPayloadTypeDeclAccess().getAsKeyword_7());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:515:1: ( (lv_alias_8_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:516:1: (lv_alias_8_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:516:1: (lv_alias_8_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:517:3: lv_alias_8_0= RULE_ID
            {
            lv_alias_8_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadTypeDecl1056); 

            			newLeafNode(lv_alias_8_0, grammarAccess.getPayloadTypeDeclAccess().getAliasIDTerminalRuleCall_8_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPayloadTypeDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"alias",
                    		lv_alias_8_0, 
                    		"ID");
            	    

            }


            }

            otherlv_9=(Token)match(input,12,FOLLOW_12_in_rulePayloadTypeDecl1073); 

                	newLeafNode(otherlv_9, grammarAccess.getPayloadTypeDeclAccess().getSemicolonKeyword_9());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePayloadTypeDecl"


    // $ANTLR start "entryRuleMessageSignature"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:545:1: entryRuleMessageSignature returns [EObject current=null] : iv_ruleMessageSignature= ruleMessageSignature EOF ;
    public final EObject entryRuleMessageSignature() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageSignature = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:546:2: (iv_ruleMessageSignature= ruleMessageSignature EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:547:2: iv_ruleMessageSignature= ruleMessageSignature EOF
            {
             newCompositeNode(grammarAccess.getMessageSignatureRule()); 
            pushFollow(FOLLOW_ruleMessageSignature_in_entryRuleMessageSignature1109);
            iv_ruleMessageSignature=ruleMessageSignature();

            state._fsp--;

             current =iv_ruleMessageSignature; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessageSignature1119); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMessageSignature"


    // $ANTLR start "ruleMessageSignature"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:554:1: ruleMessageSignature returns [EObject current=null] : ( () ( (lv_operator_1_0= RULE_ID ) )? otherlv_2= '(' ( ( (lv_types_3_0= rulePayloadElement ) ) (otherlv_4= ',' ( (lv_types_5_0= rulePayloadElement ) ) )* )? otherlv_6= ')' ) ;
    public final EObject ruleMessageSignature() throws RecognitionException {
        EObject current = null;

        Token lv_operator_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_types_3_0 = null;

        EObject lv_types_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:557:28: ( ( () ( (lv_operator_1_0= RULE_ID ) )? otherlv_2= '(' ( ( (lv_types_3_0= rulePayloadElement ) ) (otherlv_4= ',' ( (lv_types_5_0= rulePayloadElement ) ) )* )? otherlv_6= ')' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:558:1: ( () ( (lv_operator_1_0= RULE_ID ) )? otherlv_2= '(' ( ( (lv_types_3_0= rulePayloadElement ) ) (otherlv_4= ',' ( (lv_types_5_0= rulePayloadElement ) ) )* )? otherlv_6= ')' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:558:1: ( () ( (lv_operator_1_0= RULE_ID ) )? otherlv_2= '(' ( ( (lv_types_3_0= rulePayloadElement ) ) (otherlv_4= ',' ( (lv_types_5_0= rulePayloadElement ) ) )* )? otherlv_6= ')' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:558:2: () ( (lv_operator_1_0= RULE_ID ) )? otherlv_2= '(' ( ( (lv_types_3_0= rulePayloadElement ) ) (otherlv_4= ',' ( (lv_types_5_0= rulePayloadElement ) ) )* )? otherlv_6= ')'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:558:2: ()
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:559:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getMessageSignatureAccess().getMessageSignatureAction_0(),
                        current);
                

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:564:2: ( (lv_operator_1_0= RULE_ID ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_ID) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:565:1: (lv_operator_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:565:1: (lv_operator_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:566:3: lv_operator_1_0= RULE_ID
                    {
                    lv_operator_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMessageSignature1170); 

                    			newLeafNode(lv_operator_1_0, grammarAccess.getMessageSignatureAccess().getOperatorIDTerminalRuleCall_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMessageSignatureRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"operator",
                            		lv_operator_1_0, 
                            		"ID");
                    	    

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,20,FOLLOW_20_in_ruleMessageSignature1188); 

                	newLeafNode(otherlv_2, grammarAccess.getMessageSignatureAccess().getLeftParenthesisKeyword_2());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:586:1: ( ( (lv_types_3_0= rulePayloadElement ) ) (otherlv_4= ',' ( (lv_types_5_0= rulePayloadElement ) ) )* )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_ID) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:586:2: ( (lv_types_3_0= rulePayloadElement ) ) (otherlv_4= ',' ( (lv_types_5_0= rulePayloadElement ) ) )*
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:586:2: ( (lv_types_3_0= rulePayloadElement ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:587:1: (lv_types_3_0= rulePayloadElement )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:587:1: (lv_types_3_0= rulePayloadElement )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:588:3: lv_types_3_0= rulePayloadElement
                    {
                     
                    	        newCompositeNode(grammarAccess.getMessageSignatureAccess().getTypesPayloadElementParserRuleCall_3_0_0()); 
                    	    
                    pushFollow(FOLLOW_rulePayloadElement_in_ruleMessageSignature1210);
                    lv_types_3_0=rulePayloadElement();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMessageSignatureRule());
                    	        }
                           		add(
                           			current, 
                           			"types",
                            		lv_types_3_0, 
                            		"PayloadElement");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:604:2: (otherlv_4= ',' ( (lv_types_5_0= rulePayloadElement ) ) )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==21) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:604:4: otherlv_4= ',' ( (lv_types_5_0= rulePayloadElement ) )
                    	    {
                    	    otherlv_4=(Token)match(input,21,FOLLOW_21_in_ruleMessageSignature1223); 

                    	        	newLeafNode(otherlv_4, grammarAccess.getMessageSignatureAccess().getCommaKeyword_3_1_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:608:1: ( (lv_types_5_0= rulePayloadElement ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:609:1: (lv_types_5_0= rulePayloadElement )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:609:1: (lv_types_5_0= rulePayloadElement )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:610:3: lv_types_5_0= rulePayloadElement
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageSignatureAccess().getTypesPayloadElementParserRuleCall_3_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePayloadElement_in_ruleMessageSignature1244);
                    	    lv_types_5_0=rulePayloadElement();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMessageSignatureRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"types",
                    	            		lv_types_5_0, 
                    	            		"PayloadElement");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,22,FOLLOW_22_in_ruleMessageSignature1260); 

                	newLeafNode(otherlv_6, grammarAccess.getMessageSignatureAccess().getRightParenthesisKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessageSignature"


    // $ANTLR start "entryRulePayloadElement"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:638:1: entryRulePayloadElement returns [EObject current=null] : iv_rulePayloadElement= rulePayloadElement EOF ;
    public final EObject entryRulePayloadElement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePayloadElement = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:639:2: (iv_rulePayloadElement= rulePayloadElement EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:640:2: iv_rulePayloadElement= rulePayloadElement EOF
            {
             newCompositeNode(grammarAccess.getPayloadElementRule()); 
            pushFollow(FOLLOW_rulePayloadElement_in_entryRulePayloadElement1296);
            iv_rulePayloadElement=rulePayloadElement();

            state._fsp--;

             current =iv_rulePayloadElement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePayloadElement1306); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePayloadElement"


    // $ANTLR start "rulePayloadElement"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:647:1: rulePayloadElement returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) ) ;
    public final EObject rulePayloadElement() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_type_2_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:650:28: ( ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:651:1: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:651:1: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:651:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:651:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_ID) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==23) ) {
                    alt11=1;
                }
            }
            switch (alt11) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:651:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:651:3: ( (lv_name_0_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:652:1: (lv_name_0_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:652:1: (lv_name_0_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:653:3: lv_name_0_0= RULE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadElement1349); 

                    			newLeafNode(lv_name_0_0, grammarAccess.getPayloadElementAccess().getNameIDTerminalRuleCall_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getPayloadElementRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_0_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_1=(Token)match(input,23,FOLLOW_23_in_rulePayloadElement1366); 

                        	newLeafNode(otherlv_1, grammarAccess.getPayloadElementAccess().getColonKeyword_0_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:673:3: ( (lv_type_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:674:1: (lv_type_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:674:1: (lv_type_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:675:3: lv_type_2_0= RULE_ID
            {
            lv_type_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadElement1385); 

            			newLeafNode(lv_type_2_0, grammarAccess.getPayloadElementAccess().getTypeIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPayloadElementRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"type",
                    		lv_type_2_0, 
                    		"ID");
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePayloadElement"


    // $ANTLR start "entryRuleGlobalProtocolDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:699:1: entryRuleGlobalProtocolDecl returns [EObject current=null] : iv_ruleGlobalProtocolDecl= ruleGlobalProtocolDecl EOF ;
    public final EObject entryRuleGlobalProtocolDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalProtocolDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:700:2: (iv_ruleGlobalProtocolDecl= ruleGlobalProtocolDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:701:2: iv_ruleGlobalProtocolDecl= ruleGlobalProtocolDecl EOF
            {
             newCompositeNode(grammarAccess.getGlobalProtocolDeclRule()); 
            pushFollow(FOLLOW_ruleGlobalProtocolDecl_in_entryRuleGlobalProtocolDecl1426);
            iv_ruleGlobalProtocolDecl=ruleGlobalProtocolDecl();

            state._fsp--;

             current =iv_ruleGlobalProtocolDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalProtocolDecl1436); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGlobalProtocolDecl"


    // $ANTLR start "ruleGlobalProtocolDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:708:1: ruleGlobalProtocolDecl returns [EObject current=null] : (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) ) ;
    public final EObject ruleGlobalProtocolDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token lv_instantiates_15_0=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        Token otherlv_26=null;
        EObject lv_parameters_4_0 = null;

        EObject lv_parameters_6_0 = null;

        EObject lv_roles_9_0 = null;

        EObject lv_roles_11_0 = null;

        EObject lv_block_13_0 = null;

        EObject lv_arguments_17_0 = null;

        EObject lv_arguments_19_0 = null;

        EObject lv_roleInstantiations_22_0 = null;

        EObject lv_roleInstantiations_24_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:711:28: ( (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:712:1: (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:712:1: (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:712:3: otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) )
            {
            otherlv_0=(Token)match(input,24,FOLLOW_24_in_ruleGlobalProtocolDecl1473); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalProtocolDeclAccess().getGlobalKeyword_0());
                
            otherlv_1=(Token)match(input,25,FOLLOW_25_in_ruleGlobalProtocolDecl1485); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalProtocolDeclAccess().getProtocolKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:720:1: ( (lv_name_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:721:1: (lv_name_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:721:1: (lv_name_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:722:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1502); 

            			newLeafNode(lv_name_2_0, grammarAccess.getGlobalProtocolDeclAccess().getNameIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalProtocolDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:738:2: (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==18) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:738:4: otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>'
                    {
                    otherlv_3=(Token)match(input,18,FOLLOW_18_in_ruleGlobalProtocolDecl1520); 

                        	newLeafNode(otherlv_3, grammarAccess.getGlobalProtocolDeclAccess().getLessThanSignKeyword_3_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:742:1: ( (lv_parameters_4_0= ruleParameterDecl ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:743:1: (lv_parameters_4_0= ruleParameterDecl )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:743:1: (lv_parameters_4_0= ruleParameterDecl )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:744:3: lv_parameters_4_0= ruleParameterDecl
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1541);
                    lv_parameters_4_0=ruleParameterDecl();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_4_0, 
                            		"ParameterDecl");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:760:2: (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==21) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:760:4: otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) )
                    	    {
                    	    otherlv_5=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1554); 

                    	        	newLeafNode(otherlv_5, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_3_2_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:764:1: ( (lv_parameters_6_0= ruleParameterDecl ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:765:1: (lv_parameters_6_0= ruleParameterDecl )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:765:1: (lv_parameters_6_0= ruleParameterDecl )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:766:3: lv_parameters_6_0= ruleParameterDecl
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_3_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1575);
                    	    lv_parameters_6_0=ruleParameterDecl();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"parameters",
                    	            		lv_parameters_6_0, 
                    	            		"ParameterDecl");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,19,FOLLOW_19_in_ruleGlobalProtocolDecl1589); 

                        	newLeafNode(otherlv_7, grammarAccess.getGlobalProtocolDeclAccess().getGreaterThanSignKeyword_3_3());
                        

                    }
                    break;

            }

            otherlv_8=(Token)match(input,20,FOLLOW_20_in_ruleGlobalProtocolDecl1603); 

                	newLeafNode(otherlv_8, grammarAccess.getGlobalProtocolDeclAccess().getLeftParenthesisKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:790:1: ( (lv_roles_9_0= ruleRoleDecl ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:791:1: (lv_roles_9_0= ruleRoleDecl )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:791:1: (lv_roles_9_0= ruleRoleDecl )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:792:3: lv_roles_9_0= ruleRoleDecl
            {
             
            	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1624);
            lv_roles_9_0=ruleRoleDecl();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
            	        }
                   		add(
                   			current, 
                   			"roles",
                    		lv_roles_9_0, 
                    		"RoleDecl");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:808:2: (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==21) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:808:4: otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) )
            	    {
            	    otherlv_10=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1637); 

            	        	newLeafNode(otherlv_10, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_6_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:812:1: ( (lv_roles_11_0= ruleRoleDecl ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:813:1: (lv_roles_11_0= ruleRoleDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:813:1: (lv_roles_11_0= ruleRoleDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:814:3: lv_roles_11_0= ruleRoleDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_6_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1658);
            	    lv_roles_11_0=ruleRoleDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"roles",
            	            		lv_roles_11_0, 
            	            		"RoleDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            otherlv_12=(Token)match(input,22,FOLLOW_22_in_ruleGlobalProtocolDecl1672); 

                	newLeafNode(otherlv_12, grammarAccess.getGlobalProtocolDeclAccess().getRightParenthesisKeyword_7());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:834:1: ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==29) ) {
                alt18=1;
            }
            else if ( (LA18_0==26) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:834:2: ( (lv_block_13_0= ruleGlobalProtocolBlock ) )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:834:2: ( (lv_block_13_0= ruleGlobalProtocolBlock ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:835:1: (lv_block_13_0= ruleGlobalProtocolBlock )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:835:1: (lv_block_13_0= ruleGlobalProtocolBlock )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:836:3: lv_block_13_0= ruleGlobalProtocolBlock
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getBlockGlobalProtocolBlockParserRuleCall_8_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalProtocolDecl1694);
                    lv_block_13_0=ruleGlobalProtocolBlock();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                    	        }
                           		set(
                           			current, 
                           			"block",
                            		lv_block_13_0, 
                            		"GlobalProtocolBlock");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:853:6: (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:853:6: (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:853:8: otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';'
                    {
                    otherlv_14=(Token)match(input,26,FOLLOW_26_in_ruleGlobalProtocolDecl1713); 

                        	newLeafNode(otherlv_14, grammarAccess.getGlobalProtocolDeclAccess().getInstantiatesKeyword_8_1_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:857:1: ( (lv_instantiates_15_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:858:1: (lv_instantiates_15_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:858:1: (lv_instantiates_15_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:859:3: lv_instantiates_15_0= RULE_ID
                    {
                    lv_instantiates_15_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1730); 

                    			newLeafNode(lv_instantiates_15_0, grammarAccess.getGlobalProtocolDeclAccess().getInstantiatesIDTerminalRuleCall_8_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getGlobalProtocolDeclRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"instantiates",
                            		lv_instantiates_15_0, 
                            		"ID");
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:875:2: (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==18) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:875:4: otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>'
                            {
                            otherlv_16=(Token)match(input,18,FOLLOW_18_in_ruleGlobalProtocolDecl1748); 

                                	newLeafNode(otherlv_16, grammarAccess.getGlobalProtocolDeclAccess().getLessThanSignKeyword_8_1_2_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:879:1: ( (lv_arguments_17_0= ruleArgument ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:880:1: (lv_arguments_17_0= ruleArgument )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:880:1: (lv_arguments_17_0= ruleArgument )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:881:3: lv_arguments_17_0= ruleArgument
                            {
                             
                            	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_8_1_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1769);
                            lv_arguments_17_0=ruleArgument();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"arguments",
                                    		lv_arguments_17_0, 
                                    		"Argument");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:897:2: (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )*
                            loop15:
                            do {
                                int alt15=2;
                                int LA15_0 = input.LA(1);

                                if ( (LA15_0==21) ) {
                                    alt15=1;
                                }


                                switch (alt15) {
                            	case 1 :
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:897:4: otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) )
                            	    {
                            	    otherlv_18=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1782); 

                            	        	newLeafNode(otherlv_18, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_8_1_2_2_0());
                            	        
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:901:1: ( (lv_arguments_19_0= ruleArgument ) )
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:902:1: (lv_arguments_19_0= ruleArgument )
                            	    {
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:902:1: (lv_arguments_19_0= ruleArgument )
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:903:3: lv_arguments_19_0= ruleArgument
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_8_1_2_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1803);
                            	    lv_arguments_19_0=ruleArgument();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"arguments",
                            	            		lv_arguments_19_0, 
                            	            		"Argument");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop15;
                                }
                            } while (true);

                            otherlv_20=(Token)match(input,19,FOLLOW_19_in_ruleGlobalProtocolDecl1817); 

                                	newLeafNode(otherlv_20, grammarAccess.getGlobalProtocolDeclAccess().getGreaterThanSignKeyword_8_1_2_3());
                                

                            }
                            break;

                    }

                    otherlv_21=(Token)match(input,20,FOLLOW_20_in_ruleGlobalProtocolDecl1831); 

                        	newLeafNode(otherlv_21, grammarAccess.getGlobalProtocolDeclAccess().getLeftParenthesisKeyword_8_1_3());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:927:1: ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:928:1: (lv_roleInstantiations_22_0= ruleRoleInstantiation )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:928:1: (lv_roleInstantiations_22_0= ruleRoleInstantiation )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:929:3: lv_roleInstantiations_22_0= ruleRoleInstantiation
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_8_1_4_0()); 
                    	    
                    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1852);
                    lv_roleInstantiations_22_0=ruleRoleInstantiation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                    	        }
                           		add(
                           			current, 
                           			"roleInstantiations",
                            		lv_roleInstantiations_22_0, 
                            		"RoleInstantiation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:945:2: (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==21) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:945:4: otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) )
                    	    {
                    	    otherlv_23=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1865); 

                    	        	newLeafNode(otherlv_23, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_8_1_5_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:949:1: ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:950:1: (lv_roleInstantiations_24_0= ruleRoleInstantiation )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:950:1: (lv_roleInstantiations_24_0= ruleRoleInstantiation )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:951:3: lv_roleInstantiations_24_0= ruleRoleInstantiation
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_8_1_5_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1886);
                    	    lv_roleInstantiations_24_0=ruleRoleInstantiation();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"roleInstantiations",
                    	            		lv_roleInstantiations_24_0, 
                    	            		"RoleInstantiation");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop17;
                        }
                    } while (true);

                    otherlv_25=(Token)match(input,22,FOLLOW_22_in_ruleGlobalProtocolDecl1900); 

                        	newLeafNode(otherlv_25, grammarAccess.getGlobalProtocolDeclAccess().getRightParenthesisKeyword_8_1_6());
                        
                    otherlv_26=(Token)match(input,12,FOLLOW_12_in_ruleGlobalProtocolDecl1912); 

                        	newLeafNode(otherlv_26, grammarAccess.getGlobalProtocolDeclAccess().getSemicolonKeyword_8_1_7());
                        

                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGlobalProtocolDecl"


    // $ANTLR start "entryRuleRoleDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:983:1: entryRuleRoleDecl returns [EObject current=null] : iv_ruleRoleDecl= ruleRoleDecl EOF ;
    public final EObject entryRuleRoleDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRoleDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:984:2: (iv_ruleRoleDecl= ruleRoleDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:985:2: iv_ruleRoleDecl= ruleRoleDecl EOF
            {
             newCompositeNode(grammarAccess.getRoleDeclRule()); 
            pushFollow(FOLLOW_ruleRoleDecl_in_entryRuleRoleDecl1950);
            iv_ruleRoleDecl=ruleRoleDecl();

            state._fsp--;

             current =iv_ruleRoleDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRoleDecl1960); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRoleDecl"


    // $ANTLR start "ruleRoleDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:992:1: ruleRoleDecl returns [EObject current=null] : (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) ;
    public final EObject ruleRoleDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token lv_alias_3_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:995:28: ( (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:996:1: (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:996:1: (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:996:3: otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,27,FOLLOW_27_in_ruleRoleDecl1997); 

                	newLeafNode(otherlv_0, grammarAccess.getRoleDeclAccess().getRoleKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1000:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1001:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1001:1: (lv_name_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1002:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleDecl2014); 

            			newLeafNode(lv_name_1_0, grammarAccess.getRoleDeclAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getRoleDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1018:2: (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==15) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1018:4: otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,15,FOLLOW_15_in_ruleRoleDecl2032); 

                        	newLeafNode(otherlv_2, grammarAccess.getRoleDeclAccess().getAsKeyword_2_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1022:1: ( (lv_alias_3_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1023:1: (lv_alias_3_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1023:1: (lv_alias_3_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1024:3: lv_alias_3_0= RULE_ID
                    {
                    lv_alias_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleDecl2049); 

                    			newLeafNode(lv_alias_3_0, grammarAccess.getRoleDeclAccess().getAliasIDTerminalRuleCall_2_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getRoleDeclRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"alias",
                            		lv_alias_3_0, 
                            		"ID");
                    	    

                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRoleDecl"


    // $ANTLR start "entryRuleParameterDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1048:1: entryRuleParameterDecl returns [EObject current=null] : iv_ruleParameterDecl= ruleParameterDecl EOF ;
    public final EObject entryRuleParameterDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1049:2: (iv_ruleParameterDecl= ruleParameterDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1050:2: iv_ruleParameterDecl= ruleParameterDecl EOF
            {
             newCompositeNode(grammarAccess.getParameterDeclRule()); 
            pushFollow(FOLLOW_ruleParameterDecl_in_entryRuleParameterDecl2092);
            iv_ruleParameterDecl=ruleParameterDecl();

            state._fsp--;

             current =iv_ruleParameterDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleParameterDecl2102); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameterDecl"


    // $ANTLR start "ruleParameterDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1057:1: ruleParameterDecl returns [EObject current=null] : ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) ) ;
    public final EObject ruleParameterDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token lv_alias_3_0=null;
        Token otherlv_4=null;
        Token lv_name_5_0=null;
        Token otherlv_6=null;
        Token lv_alias_7_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1060:28: ( ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1061:1: ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1061:1: ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==17) ) {
                alt22=1;
            }
            else if ( (LA22_0==28) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1061:2: (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1061:2: (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1061:4: otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
                    {
                    otherlv_0=(Token)match(input,17,FOLLOW_17_in_ruleParameterDecl2140); 

                        	newLeafNode(otherlv_0, grammarAccess.getParameterDeclAccess().getTypeKeyword_0_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1065:1: ( (lv_name_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1066:1: (lv_name_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1066:1: (lv_name_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1067:3: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2157); 

                    			newLeafNode(lv_name_1_0, grammarAccess.getParameterDeclAccess().getNameIDTerminalRuleCall_0_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getParameterDeclRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1083:2: (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==15) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1083:4: otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) )
                            {
                            otherlv_2=(Token)match(input,15,FOLLOW_15_in_ruleParameterDecl2175); 

                                	newLeafNode(otherlv_2, grammarAccess.getParameterDeclAccess().getAsKeyword_0_2_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1087:1: ( (lv_alias_3_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1088:1: (lv_alias_3_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1088:1: (lv_alias_3_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1089:3: lv_alias_3_0= RULE_ID
                            {
                            lv_alias_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2192); 

                            			newLeafNode(lv_alias_3_0, grammarAccess.getParameterDeclAccess().getAliasIDTerminalRuleCall_0_2_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getParameterDeclRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"alias",
                                    		lv_alias_3_0, 
                                    		"ID");
                            	    

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1106:6: (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1106:6: (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1106:8: otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )?
                    {
                    otherlv_4=(Token)match(input,28,FOLLOW_28_in_ruleParameterDecl2219); 

                        	newLeafNode(otherlv_4, grammarAccess.getParameterDeclAccess().getSigKeyword_1_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1110:1: ( (lv_name_5_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1111:1: (lv_name_5_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1111:1: (lv_name_5_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1112:3: lv_name_5_0= RULE_ID
                    {
                    lv_name_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2236); 

                    			newLeafNode(lv_name_5_0, grammarAccess.getParameterDeclAccess().getNameIDTerminalRuleCall_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getParameterDeclRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_5_0, 
                            		"ID");
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1128:2: (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==15) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1128:4: otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) )
                            {
                            otherlv_6=(Token)match(input,15,FOLLOW_15_in_ruleParameterDecl2254); 

                                	newLeafNode(otherlv_6, grammarAccess.getParameterDeclAccess().getAsKeyword_1_2_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1132:1: ( (lv_alias_7_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1133:1: (lv_alias_7_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1133:1: (lv_alias_7_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1134:3: lv_alias_7_0= RULE_ID
                            {
                            lv_alias_7_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2271); 

                            			newLeafNode(lv_alias_7_0, grammarAccess.getParameterDeclAccess().getAliasIDTerminalRuleCall_1_2_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getParameterDeclRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"alias",
                                    		lv_alias_7_0, 
                                    		"ID");
                            	    

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameterDecl"


    // $ANTLR start "entryRuleRoleInstantiation"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1158:1: entryRuleRoleInstantiation returns [EObject current=null] : iv_ruleRoleInstantiation= ruleRoleInstantiation EOF ;
    public final EObject entryRuleRoleInstantiation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRoleInstantiation = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1159:2: (iv_ruleRoleInstantiation= ruleRoleInstantiation EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1160:2: iv_ruleRoleInstantiation= ruleRoleInstantiation EOF
            {
             newCompositeNode(grammarAccess.getRoleInstantiationRule()); 
            pushFollow(FOLLOW_ruleRoleInstantiation_in_entryRuleRoleInstantiation2315);
            iv_ruleRoleInstantiation=ruleRoleInstantiation();

            state._fsp--;

             current =iv_ruleRoleInstantiation; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRoleInstantiation2325); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRoleInstantiation"


    // $ANTLR start "ruleRoleInstantiation"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1167:1: ruleRoleInstantiation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) ;
    public final EObject ruleRoleInstantiation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_alias_2_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1170:28: ( ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1171:1: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1171:1: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1171:2: ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1171:2: ( (lv_name_0_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1172:1: (lv_name_0_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1172:1: (lv_name_0_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1173:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleInstantiation2367); 

            			newLeafNode(lv_name_0_0, grammarAccess.getRoleInstantiationAccess().getNameIDTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getRoleInstantiationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1189:2: (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==15) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1189:4: otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) )
                    {
                    otherlv_1=(Token)match(input,15,FOLLOW_15_in_ruleRoleInstantiation2385); 

                        	newLeafNode(otherlv_1, grammarAccess.getRoleInstantiationAccess().getAsKeyword_1_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1193:1: ( (lv_alias_2_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1194:1: (lv_alias_2_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1194:1: (lv_alias_2_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1195:3: lv_alias_2_0= RULE_ID
                    {
                    lv_alias_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleInstantiation2402); 

                    			newLeafNode(lv_alias_2_0, grammarAccess.getRoleInstantiationAccess().getAliasIDTerminalRuleCall_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getRoleInstantiationRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"alias",
                            		lv_alias_2_0, 
                            		"ID");
                    	    

                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRoleInstantiation"


    // $ANTLR start "entryRuleArgument"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1219:1: entryRuleArgument returns [EObject current=null] : iv_ruleArgument= ruleArgument EOF ;
    public final EObject entryRuleArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgument = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1220:2: (iv_ruleArgument= ruleArgument EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1221:2: iv_ruleArgument= ruleArgument EOF
            {
             newCompositeNode(grammarAccess.getArgumentRule()); 
            pushFollow(FOLLOW_ruleArgument_in_entryRuleArgument2445);
            iv_ruleArgument=ruleArgument();

            state._fsp--;

             current =iv_ruleArgument; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleArgument2455); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArgument"


    // $ANTLR start "ruleArgument"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1228:1: ruleArgument returns [EObject current=null] : ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) ) ;
    public final EObject ruleArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_alias_2_0=null;
        Token lv_name_3_0=null;
        Token otherlv_4=null;
        Token lv_alias_5_0=null;
        EObject lv_signature_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1231:28: ( ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1232:1: ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1232:1: ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==RULE_ID) ) {
                int LA26_1 = input.LA(2);

                if ( (LA26_1==EOF||LA26_1==15||LA26_1==19||LA26_1==21) ) {
                    alt26=2;
                }
                else if ( (LA26_1==20) ) {
                    alt26=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA26_0==20) ) {
                alt26=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1232:2: ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1232:2: ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1232:3: ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1232:3: ( (lv_signature_0_0= ruleMessageSignature ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1233:1: (lv_signature_0_0= ruleMessageSignature )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1233:1: (lv_signature_0_0= ruleMessageSignature )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1234:3: lv_signature_0_0= ruleMessageSignature
                    {
                     
                    	        newCompositeNode(grammarAccess.getArgumentAccess().getSignatureMessageSignatureParserRuleCall_0_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleMessageSignature_in_ruleArgument2502);
                    lv_signature_0_0=ruleMessageSignature();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getArgumentRule());
                    	        }
                           		set(
                           			current, 
                           			"signature",
                            		lv_signature_0_0, 
                            		"MessageSignature");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1250:2: (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==15) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1250:4: otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) )
                            {
                            otherlv_1=(Token)match(input,15,FOLLOW_15_in_ruleArgument2515); 

                                	newLeafNode(otherlv_1, grammarAccess.getArgumentAccess().getAsKeyword_0_1_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1254:1: ( (lv_alias_2_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1255:1: (lv_alias_2_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1255:1: (lv_alias_2_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1256:3: lv_alias_2_0= RULE_ID
                            {
                            lv_alias_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleArgument2532); 

                            			newLeafNode(lv_alias_2_0, grammarAccess.getArgumentAccess().getAliasIDTerminalRuleCall_0_1_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getArgumentRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"alias",
                                    		lv_alias_2_0, 
                                    		"ID");
                            	    

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1273:6: ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1273:6: ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1273:7: ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )?
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1273:7: ( (lv_name_3_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1274:1: (lv_name_3_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1274:1: (lv_name_3_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1275:3: lv_name_3_0= RULE_ID
                    {
                    lv_name_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleArgument2564); 

                    			newLeafNode(lv_name_3_0, grammarAccess.getArgumentAccess().getNameIDTerminalRuleCall_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getArgumentRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_3_0, 
                            		"ID");
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1291:2: (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==15) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1291:4: otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) )
                            {
                            otherlv_4=(Token)match(input,15,FOLLOW_15_in_ruleArgument2582); 

                                	newLeafNode(otherlv_4, grammarAccess.getArgumentAccess().getAsKeyword_1_1_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1295:1: ( (lv_alias_5_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1296:1: (lv_alias_5_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1296:1: (lv_alias_5_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1297:3: lv_alias_5_0= RULE_ID
                            {
                            lv_alias_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleArgument2599); 

                            			newLeafNode(lv_alias_5_0, grammarAccess.getArgumentAccess().getAliasIDTerminalRuleCall_1_1_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getArgumentRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"alias",
                                    		lv_alias_5_0, 
                                    		"ID");
                            	    

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArgument"


    // $ANTLR start "entryRuleGlobalProtocolBlock"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1321:1: entryRuleGlobalProtocolBlock returns [EObject current=null] : iv_ruleGlobalProtocolBlock= ruleGlobalProtocolBlock EOF ;
    public final EObject entryRuleGlobalProtocolBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalProtocolBlock = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1322:2: (iv_ruleGlobalProtocolBlock= ruleGlobalProtocolBlock EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1323:2: iv_ruleGlobalProtocolBlock= ruleGlobalProtocolBlock EOF
            {
             newCompositeNode(grammarAccess.getGlobalProtocolBlockRule()); 
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_entryRuleGlobalProtocolBlock2643);
            iv_ruleGlobalProtocolBlock=ruleGlobalProtocolBlock();

            state._fsp--;

             current =iv_ruleGlobalProtocolBlock; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalProtocolBlock2653); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGlobalProtocolBlock"


    // $ANTLR start "ruleGlobalProtocolBlock"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1330:1: ruleGlobalProtocolBlock returns [EObject current=null] : ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' ) ;
    public final EObject ruleGlobalProtocolBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_activities_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1333:28: ( ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1334:1: ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1334:1: ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1334:2: () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1334:2: ()
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1335:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getGlobalProtocolBlockAccess().getGlobalProtocolBlockAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,29,FOLLOW_29_in_ruleGlobalProtocolBlock2699); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalProtocolBlockAccess().getLeftCurlyBracketKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1344:1: ( (lv_activities_2_0= ruleGlobalInteraction ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==RULE_ID||LA27_0==20||LA27_0==32||(LA27_0>=35 && LA27_0<=37)||LA27_0==39||LA27_0==42) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1345:1: (lv_activities_2_0= ruleGlobalInteraction )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1345:1: (lv_activities_2_0= ruleGlobalInteraction )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1346:3: lv_activities_2_0= ruleGlobalInteraction
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalProtocolBlockAccess().getActivitiesGlobalInteractionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalInteraction_in_ruleGlobalProtocolBlock2720);
            	    lv_activities_2_0=ruleGlobalInteraction();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolBlockRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"activities",
            	            		lv_activities_2_0, 
            	            		"GlobalInteraction");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

            otherlv_3=(Token)match(input,30,FOLLOW_30_in_ruleGlobalProtocolBlock2733); 

                	newLeafNode(otherlv_3, grammarAccess.getGlobalProtocolBlockAccess().getRightCurlyBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGlobalProtocolBlock"


    // $ANTLR start "entryRuleGlobalInteraction"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1374:1: entryRuleGlobalInteraction returns [EObject current=null] : iv_ruleGlobalInteraction= ruleGlobalInteraction EOF ;
    public final EObject entryRuleGlobalInteraction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalInteraction = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1375:2: (iv_ruleGlobalInteraction= ruleGlobalInteraction EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1376:2: iv_ruleGlobalInteraction= ruleGlobalInteraction EOF
            {
             newCompositeNode(grammarAccess.getGlobalInteractionRule()); 
            pushFollow(FOLLOW_ruleGlobalInteraction_in_entryRuleGlobalInteraction2769);
            iv_ruleGlobalInteraction=ruleGlobalInteraction();

            state._fsp--;

             current =iv_ruleGlobalInteraction; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInteraction2779); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGlobalInteraction"


    // $ANTLR start "ruleGlobalInteraction"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1383:1: ruleGlobalInteraction returns [EObject current=null] : (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo ) ;
    public final EObject ruleGlobalInteraction() throws RecognitionException {
        EObject current = null;

        EObject this_GlobalMessageTransfer_0 = null;

        EObject this_GlobalChoice_1 = null;

        EObject this_GlobalRecursion_2 = null;

        EObject this_GlobalContinue_3 = null;

        EObject this_GlobalParallel_4 = null;

        EObject this_GlobalInterruptible_5 = null;

        EObject this_GlobalDo_6 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1386:28: ( (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1387:1: (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1387:1: (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo )
            int alt28=7;
            switch ( input.LA(1) ) {
            case RULE_ID:
            case 20:
                {
                alt28=1;
                }
                break;
            case 32:
                {
                alt28=2;
                }
                break;
            case 35:
                {
                alt28=3;
                }
                break;
            case 36:
                {
                alt28=4;
                }
                break;
            case 37:
                {
                alt28=5;
                }
                break;
            case 39:
                {
                alt28=6;
                }
                break;
            case 42:
                {
                alt28=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1388:5: this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalMessageTransferParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleGlobalMessageTransfer_in_ruleGlobalInteraction2826);
                    this_GlobalMessageTransfer_0=ruleGlobalMessageTransfer();

                    state._fsp--;

                     
                            current = this_GlobalMessageTransfer_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1398:5: this_GlobalChoice_1= ruleGlobalChoice
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalChoiceParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleGlobalChoice_in_ruleGlobalInteraction2853);
                    this_GlobalChoice_1=ruleGlobalChoice();

                    state._fsp--;

                     
                            current = this_GlobalChoice_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1408:5: this_GlobalRecursion_2= ruleGlobalRecursion
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalRecursionParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleGlobalRecursion_in_ruleGlobalInteraction2880);
                    this_GlobalRecursion_2=ruleGlobalRecursion();

                    state._fsp--;

                     
                            current = this_GlobalRecursion_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1418:5: this_GlobalContinue_3= ruleGlobalContinue
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalContinueParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_ruleGlobalContinue_in_ruleGlobalInteraction2907);
                    this_GlobalContinue_3=ruleGlobalContinue();

                    state._fsp--;

                     
                            current = this_GlobalContinue_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1428:5: this_GlobalParallel_4= ruleGlobalParallel
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalParallelParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_ruleGlobalParallel_in_ruleGlobalInteraction2934);
                    this_GlobalParallel_4=ruleGlobalParallel();

                    state._fsp--;

                     
                            current = this_GlobalParallel_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1438:5: this_GlobalInterruptible_5= ruleGlobalInterruptible
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalInterruptibleParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleGlobalInterruptible_in_ruleGlobalInteraction2961);
                    this_GlobalInterruptible_5=ruleGlobalInterruptible();

                    state._fsp--;

                     
                            current = this_GlobalInterruptible_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1448:5: this_GlobalDo_6= ruleGlobalDo
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalDoParserRuleCall_6()); 
                        
                    pushFollow(FOLLOW_ruleGlobalDo_in_ruleGlobalInteraction2988);
                    this_GlobalDo_6=ruleGlobalDo();

                    state._fsp--;

                     
                            current = this_GlobalDo_6; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGlobalInteraction"


    // $ANTLR start "entryRuleGlobalMessageTransfer"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1464:1: entryRuleGlobalMessageTransfer returns [EObject current=null] : iv_ruleGlobalMessageTransfer= ruleGlobalMessageTransfer EOF ;
    public final EObject entryRuleGlobalMessageTransfer() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalMessageTransfer = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1465:2: (iv_ruleGlobalMessageTransfer= ruleGlobalMessageTransfer EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1466:2: iv_ruleGlobalMessageTransfer= ruleGlobalMessageTransfer EOF
            {
             newCompositeNode(grammarAccess.getGlobalMessageTransferRule()); 
            pushFollow(FOLLOW_ruleGlobalMessageTransfer_in_entryRuleGlobalMessageTransfer3023);
            iv_ruleGlobalMessageTransfer=ruleGlobalMessageTransfer();

            state._fsp--;

             current =iv_ruleGlobalMessageTransfer; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalMessageTransfer3033); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGlobalMessageTransfer"


    // $ANTLR start "ruleGlobalMessageTransfer"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1473:1: ruleGlobalMessageTransfer returns [EObject current=null] : ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRoles_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )* otherlv_7= ';' ) ;
    public final EObject ruleGlobalMessageTransfer() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_fromRole_2_0=null;
        Token otherlv_3=null;
        Token lv_toRoles_4_0=null;
        Token otherlv_5=null;
        Token lv_toRoles_6_0=null;
        Token otherlv_7=null;
        EObject lv_message_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1476:28: ( ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRoles_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )* otherlv_7= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1477:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRoles_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )* otherlv_7= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1477:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRoles_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )* otherlv_7= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1477:2: ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRoles_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )* otherlv_7= ';'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1477:2: ( (lv_message_0_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1478:1: (lv_message_0_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1478:1: (lv_message_0_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1479:3: lv_message_0_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getGlobalMessageTransferAccess().getMessageMessageParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleGlobalMessageTransfer3079);
            lv_message_0_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalMessageTransferRule());
            	        }
                   		set(
                   			current, 
                   			"message",
                    		lv_message_0_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,16,FOLLOW_16_in_ruleGlobalMessageTransfer3091); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalMessageTransferAccess().getFromKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1499:1: ( (lv_fromRole_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1500:1: (lv_fromRole_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1500:1: (lv_fromRole_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1501:3: lv_fromRole_2_0= RULE_ID
            {
            lv_fromRole_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3108); 

            			newLeafNode(lv_fromRole_2_0, grammarAccess.getGlobalMessageTransferAccess().getFromRoleIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalMessageTransferRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"fromRole",
                    		lv_fromRole_2_0, 
                    		"ID");
            	    

            }


            }

            otherlv_3=(Token)match(input,31,FOLLOW_31_in_ruleGlobalMessageTransfer3125); 

                	newLeafNode(otherlv_3, grammarAccess.getGlobalMessageTransferAccess().getToKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1521:1: ( (lv_toRoles_4_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1522:1: (lv_toRoles_4_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1522:1: (lv_toRoles_4_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1523:3: lv_toRoles_4_0= RULE_ID
            {
            lv_toRoles_4_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3142); 

            			newLeafNode(lv_toRoles_4_0, grammarAccess.getGlobalMessageTransferAccess().getToRolesIDTerminalRuleCall_4_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalMessageTransferRule());
            	        }
                   		addWithLastConsumed(
                   			current, 
                   			"toRoles",
                    		lv_toRoles_4_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1539:2: (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==21) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1539:4: otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) )
            	    {
            	    otherlv_5=(Token)match(input,21,FOLLOW_21_in_ruleGlobalMessageTransfer3160); 

            	        	newLeafNode(otherlv_5, grammarAccess.getGlobalMessageTransferAccess().getCommaKeyword_5_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1543:1: ( (lv_toRoles_6_0= RULE_ID ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1544:1: (lv_toRoles_6_0= RULE_ID )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1544:1: (lv_toRoles_6_0= RULE_ID )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1545:3: lv_toRoles_6_0= RULE_ID
            	    {
            	    lv_toRoles_6_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3177); 

            	    			newLeafNode(lv_toRoles_6_0, grammarAccess.getGlobalMessageTransferAccess().getToRolesIDTerminalRuleCall_5_1_0()); 
            	    		

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getGlobalMessageTransferRule());
            	    	        }
            	           		addWithLastConsumed(
            	           			current, 
            	           			"toRoles",
            	            		lv_toRoles_6_0, 
            	            		"ID");
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);

            otherlv_7=(Token)match(input,12,FOLLOW_12_in_ruleGlobalMessageTransfer3196); 

                	newLeafNode(otherlv_7, grammarAccess.getGlobalMessageTransferAccess().getSemicolonKeyword_6());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGlobalMessageTransfer"


    // $ANTLR start "entryRuleMessage"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1573:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1574:2: (iv_ruleMessage= ruleMessage EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1575:2: iv_ruleMessage= ruleMessage EOF
            {
             newCompositeNode(grammarAccess.getMessageRule()); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage3232);
            iv_ruleMessage=ruleMessage();

            state._fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage3242); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMessage"


    // $ANTLR start "ruleMessage"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1582:1: ruleMessage returns [EObject current=null] : ( ( (lv_signature_0_0= ruleMessageSignature ) ) | ( (lv_parameter_1_0= RULE_ID ) ) ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

        Token lv_parameter_1_0=null;
        EObject lv_signature_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1585:28: ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) | ( (lv_parameter_1_0= RULE_ID ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1586:1: ( ( (lv_signature_0_0= ruleMessageSignature ) ) | ( (lv_parameter_1_0= RULE_ID ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1586:1: ( ( (lv_signature_0_0= ruleMessageSignature ) ) | ( (lv_parameter_1_0= RULE_ID ) ) )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==RULE_ID) ) {
                int LA30_1 = input.LA(2);

                if ( (LA30_1==EOF||LA30_1==16||LA30_1==21||LA30_1==31||LA30_1==41) ) {
                    alt30=2;
                }
                else if ( (LA30_1==20) ) {
                    alt30=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA30_0==20) ) {
                alt30=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1586:2: ( (lv_signature_0_0= ruleMessageSignature ) )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1586:2: ( (lv_signature_0_0= ruleMessageSignature ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1587:1: (lv_signature_0_0= ruleMessageSignature )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1587:1: (lv_signature_0_0= ruleMessageSignature )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1588:3: lv_signature_0_0= ruleMessageSignature
                    {
                     
                    	        newCompositeNode(grammarAccess.getMessageAccess().getSignatureMessageSignatureParserRuleCall_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleMessageSignature_in_ruleMessage3288);
                    lv_signature_0_0=ruleMessageSignature();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMessageRule());
                    	        }
                           		set(
                           			current, 
                           			"signature",
                            		lv_signature_0_0, 
                            		"MessageSignature");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1605:6: ( (lv_parameter_1_0= RULE_ID ) )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1605:6: ( (lv_parameter_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1606:1: (lv_parameter_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1606:1: (lv_parameter_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1607:3: lv_parameter_1_0= RULE_ID
                    {
                    lv_parameter_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMessage3311); 

                    			newLeafNode(lv_parameter_1_0, grammarAccess.getMessageAccess().getParameterIDTerminalRuleCall_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMessageRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"parameter",
                            		lv_parameter_1_0, 
                            		"ID");
                    	    

                    }


                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessage"


    // $ANTLR start "entryRuleGlobalChoice"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1631:1: entryRuleGlobalChoice returns [EObject current=null] : iv_ruleGlobalChoice= ruleGlobalChoice EOF ;
    public final EObject entryRuleGlobalChoice() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalChoice = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1632:2: (iv_ruleGlobalChoice= ruleGlobalChoice EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1633:2: iv_ruleGlobalChoice= ruleGlobalChoice EOF
            {
             newCompositeNode(grammarAccess.getGlobalChoiceRule()); 
            pushFollow(FOLLOW_ruleGlobalChoice_in_entryRuleGlobalChoice3352);
            iv_ruleGlobalChoice=ruleGlobalChoice();

            state._fsp--;

             current =iv_ruleGlobalChoice; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalChoice3362); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGlobalChoice"


    // $ANTLR start "ruleGlobalChoice"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1640:1: ruleGlobalChoice returns [EObject current=null] : (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* ) ;
    public final EObject ruleGlobalChoice() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_role_2_0=null;
        Token otherlv_4=null;
        EObject lv_blocks_3_0 = null;

        EObject lv_blocks_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1643:28: ( (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1644:1: (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1644:1: (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1644:3: otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )*
            {
            otherlv_0=(Token)match(input,32,FOLLOW_32_in_ruleGlobalChoice3399); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalChoiceAccess().getChoiceKeyword_0());
                
            otherlv_1=(Token)match(input,33,FOLLOW_33_in_ruleGlobalChoice3411); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalChoiceAccess().getAtKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1652:1: ( (lv_role_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1653:1: (lv_role_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1653:1: (lv_role_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1654:3: lv_role_2_0= RULE_ID
            {
            lv_role_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalChoice3428); 

            			newLeafNode(lv_role_2_0, grammarAccess.getGlobalChoiceAccess().getRoleIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalChoiceRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"role",
                    		lv_role_2_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1670:2: ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1671:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1671:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1672:3: lv_blocks_3_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalChoiceAccess().getBlocksGlobalProtocolBlockParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3454);
            lv_blocks_3_0=ruleGlobalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalChoiceRule());
            	        }
                   		add(
                   			current, 
                   			"blocks",
                    		lv_blocks_3_0, 
                    		"GlobalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1688:2: (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==34) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1688:4: otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) )
            	    {
            	    otherlv_4=(Token)match(input,34,FOLLOW_34_in_ruleGlobalChoice3467); 

            	        	newLeafNode(otherlv_4, grammarAccess.getGlobalChoiceAccess().getOrKeyword_4_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1692:1: ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1693:1: (lv_blocks_5_0= ruleGlobalProtocolBlock )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1693:1: (lv_blocks_5_0= ruleGlobalProtocolBlock )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1694:3: lv_blocks_5_0= ruleGlobalProtocolBlock
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalChoiceAccess().getBlocksGlobalProtocolBlockParserRuleCall_4_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3488);
            	    lv_blocks_5_0=ruleGlobalProtocolBlock();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalChoiceRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"blocks",
            	            		lv_blocks_5_0, 
            	            		"GlobalProtocolBlock");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGlobalChoice"


    // $ANTLR start "entryRuleGlobalRecursion"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1718:1: entryRuleGlobalRecursion returns [EObject current=null] : iv_ruleGlobalRecursion= ruleGlobalRecursion EOF ;
    public final EObject entryRuleGlobalRecursion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalRecursion = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1719:2: (iv_ruleGlobalRecursion= ruleGlobalRecursion EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1720:2: iv_ruleGlobalRecursion= ruleGlobalRecursion EOF
            {
             newCompositeNode(grammarAccess.getGlobalRecursionRule()); 
            pushFollow(FOLLOW_ruleGlobalRecursion_in_entryRuleGlobalRecursion3526);
            iv_ruleGlobalRecursion=ruleGlobalRecursion();

            state._fsp--;

             current =iv_ruleGlobalRecursion; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalRecursion3536); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGlobalRecursion"


    // $ANTLR start "ruleGlobalRecursion"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1727:1: ruleGlobalRecursion returns [EObject current=null] : (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) ) ;
    public final EObject ruleGlobalRecursion() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_label_1_0=null;
        EObject lv_block_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1730:28: ( (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1731:1: (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1731:1: (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1731:3: otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) )
            {
            otherlv_0=(Token)match(input,35,FOLLOW_35_in_ruleGlobalRecursion3573); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalRecursionAccess().getRecKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1735:1: ( (lv_label_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1736:1: (lv_label_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1736:1: (lv_label_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1737:3: lv_label_1_0= RULE_ID
            {
            lv_label_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalRecursion3590); 

            			newLeafNode(lv_label_1_0, grammarAccess.getGlobalRecursionAccess().getLabelIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalRecursionRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"label",
                    		lv_label_1_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1753:2: ( (lv_block_2_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1754:1: (lv_block_2_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1754:1: (lv_block_2_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1755:3: lv_block_2_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalRecursionAccess().getBlockGlobalProtocolBlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalRecursion3616);
            lv_block_2_0=ruleGlobalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalRecursionRule());
            	        }
                   		set(
                   			current, 
                   			"block",
                    		lv_block_2_0, 
                    		"GlobalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGlobalRecursion"


    // $ANTLR start "entryRuleGlobalContinue"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1779:1: entryRuleGlobalContinue returns [EObject current=null] : iv_ruleGlobalContinue= ruleGlobalContinue EOF ;
    public final EObject entryRuleGlobalContinue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalContinue = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1780:2: (iv_ruleGlobalContinue= ruleGlobalContinue EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1781:2: iv_ruleGlobalContinue= ruleGlobalContinue EOF
            {
             newCompositeNode(grammarAccess.getGlobalContinueRule()); 
            pushFollow(FOLLOW_ruleGlobalContinue_in_entryRuleGlobalContinue3652);
            iv_ruleGlobalContinue=ruleGlobalContinue();

            state._fsp--;

             current =iv_ruleGlobalContinue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalContinue3662); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGlobalContinue"


    // $ANTLR start "ruleGlobalContinue"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1788:1: ruleGlobalContinue returns [EObject current=null] : (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' ) ;
    public final EObject ruleGlobalContinue() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_label_1_0=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1791:28: ( (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1792:1: (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1792:1: (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1792:3: otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';'
            {
            otherlv_0=(Token)match(input,36,FOLLOW_36_in_ruleGlobalContinue3699); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalContinueAccess().getContinueKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1796:1: ( (lv_label_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1797:1: (lv_label_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1797:1: (lv_label_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1798:3: lv_label_1_0= RULE_ID
            {
            lv_label_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalContinue3716); 

            			newLeafNode(lv_label_1_0, grammarAccess.getGlobalContinueAccess().getLabelIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalContinueRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"label",
                    		lv_label_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleGlobalContinue3733); 

                	newLeafNode(otherlv_2, grammarAccess.getGlobalContinueAccess().getSemicolonKeyword_2());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGlobalContinue"


    // $ANTLR start "entryRuleGlobalParallel"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1826:1: entryRuleGlobalParallel returns [EObject current=null] : iv_ruleGlobalParallel= ruleGlobalParallel EOF ;
    public final EObject entryRuleGlobalParallel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalParallel = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1827:2: (iv_ruleGlobalParallel= ruleGlobalParallel EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1828:2: iv_ruleGlobalParallel= ruleGlobalParallel EOF
            {
             newCompositeNode(grammarAccess.getGlobalParallelRule()); 
            pushFollow(FOLLOW_ruleGlobalParallel_in_entryRuleGlobalParallel3769);
            iv_ruleGlobalParallel=ruleGlobalParallel();

            state._fsp--;

             current =iv_ruleGlobalParallel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalParallel3779); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGlobalParallel"


    // $ANTLR start "ruleGlobalParallel"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1835:1: ruleGlobalParallel returns [EObject current=null] : (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* ) ;
    public final EObject ruleGlobalParallel() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_blocks_1_0 = null;

        EObject lv_blocks_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1838:28: ( (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1839:1: (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1839:1: (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1839:3: otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )*
            {
            otherlv_0=(Token)match(input,37,FOLLOW_37_in_ruleGlobalParallel3816); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalParallelAccess().getParKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1843:1: ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1844:1: (lv_blocks_1_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1844:1: (lv_blocks_1_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1845:3: lv_blocks_1_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalParallelAccess().getBlocksGlobalProtocolBlockParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3837);
            lv_blocks_1_0=ruleGlobalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalParallelRule());
            	        }
                   		add(
                   			current, 
                   			"blocks",
                    		lv_blocks_1_0, 
                    		"GlobalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1861:2: (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==38) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1861:4: otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) )
            	    {
            	    otherlv_2=(Token)match(input,38,FOLLOW_38_in_ruleGlobalParallel3850); 

            	        	newLeafNode(otherlv_2, grammarAccess.getGlobalParallelAccess().getAndKeyword_2_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1865:1: ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1866:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1866:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1867:3: lv_blocks_3_0= ruleGlobalProtocolBlock
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalParallelAccess().getBlocksGlobalProtocolBlockParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3871);
            	    lv_blocks_3_0=ruleGlobalProtocolBlock();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalParallelRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"blocks",
            	            		lv_blocks_3_0, 
            	            		"GlobalProtocolBlock");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGlobalParallel"


    // $ANTLR start "entryRuleGlobalInterruptible"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1891:1: entryRuleGlobalInterruptible returns [EObject current=null] : iv_ruleGlobalInterruptible= ruleGlobalInterruptible EOF ;
    public final EObject entryRuleGlobalInterruptible() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalInterruptible = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1892:2: (iv_ruleGlobalInterruptible= ruleGlobalInterruptible EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1893:2: iv_ruleGlobalInterruptible= ruleGlobalInterruptible EOF
            {
             newCompositeNode(grammarAccess.getGlobalInterruptibleRule()); 
            pushFollow(FOLLOW_ruleGlobalInterruptible_in_entryRuleGlobalInterruptible3909);
            iv_ruleGlobalInterruptible=ruleGlobalInterruptible();

            state._fsp--;

             current =iv_ruleGlobalInterruptible; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInterruptible3919); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGlobalInterruptible"


    // $ANTLR start "ruleGlobalInterruptible"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1900:1: ruleGlobalInterruptible returns [EObject current=null] : (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' ) ;
    public final EObject ruleGlobalInterruptible() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_scope_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_block_3_0 = null;

        EObject lv_interrupts_6_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1903:28: ( (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1904:1: (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1904:1: (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1904:3: otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,39,FOLLOW_39_in_ruleGlobalInterruptible3956); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalInterruptibleAccess().getInterruptibleKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1908:1: ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==RULE_ID) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1908:2: ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1908:2: ( (lv_scope_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1909:1: (lv_scope_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1909:1: (lv_scope_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1910:3: lv_scope_1_0= RULE_ID
                    {
                    lv_scope_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalInterruptible3974); 

                    			newLeafNode(lv_scope_1_0, grammarAccess.getGlobalInterruptibleAccess().getScopeIDTerminalRuleCall_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getGlobalInterruptibleRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"scope",
                            		lv_scope_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleGlobalInterruptible3991); 

                        	newLeafNode(otherlv_2, grammarAccess.getGlobalInterruptibleAccess().getColonKeyword_1_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1930:3: ( (lv_block_3_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1931:1: (lv_block_3_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1931:1: (lv_block_3_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1932:3: lv_block_3_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalInterruptibleAccess().getBlockGlobalProtocolBlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalInterruptible4014);
            lv_block_3_0=ruleGlobalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalInterruptibleRule());
            	        }
                   		set(
                   			current, 
                   			"block",
                    		lv_block_3_0, 
                    		"GlobalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,40,FOLLOW_40_in_ruleGlobalInterruptible4026); 

                	newLeafNode(otherlv_4, grammarAccess.getGlobalInterruptibleAccess().getWithKeyword_3());
                
            otherlv_5=(Token)match(input,29,FOLLOW_29_in_ruleGlobalInterruptible4038); 

                	newLeafNode(otherlv_5, grammarAccess.getGlobalInterruptibleAccess().getLeftCurlyBracketKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1956:1: ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==RULE_ID||LA34_0==20) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1957:1: (lv_interrupts_6_0= ruleGlobalInterrupt )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1957:1: (lv_interrupts_6_0= ruleGlobalInterrupt )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1958:3: lv_interrupts_6_0= ruleGlobalInterrupt
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalInterruptibleAccess().getInterruptsGlobalInterruptParserRuleCall_5_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalInterrupt_in_ruleGlobalInterruptible4059);
            	    lv_interrupts_6_0=ruleGlobalInterrupt();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalInterruptibleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"interrupts",
            	            		lv_interrupts_6_0, 
            	            		"GlobalInterrupt");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);

            otherlv_7=(Token)match(input,30,FOLLOW_30_in_ruleGlobalInterruptible4072); 

                	newLeafNode(otherlv_7, grammarAccess.getGlobalInterruptibleAccess().getRightCurlyBracketKeyword_6());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGlobalInterruptible"


    // $ANTLR start "entryRuleGlobalInterrupt"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1986:1: entryRuleGlobalInterrupt returns [EObject current=null] : iv_ruleGlobalInterrupt= ruleGlobalInterrupt EOF ;
    public final EObject entryRuleGlobalInterrupt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalInterrupt = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1987:2: (iv_ruleGlobalInterrupt= ruleGlobalInterrupt EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1988:2: iv_ruleGlobalInterrupt= ruleGlobalInterrupt EOF
            {
             newCompositeNode(grammarAccess.getGlobalInterruptRule()); 
            pushFollow(FOLLOW_ruleGlobalInterrupt_in_entryRuleGlobalInterrupt4108);
            iv_ruleGlobalInterrupt=ruleGlobalInterrupt();

            state._fsp--;

             current =iv_ruleGlobalInterrupt; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInterrupt4118); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGlobalInterrupt"


    // $ANTLR start "ruleGlobalInterrupt"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1995:1: ruleGlobalInterrupt returns [EObject current=null] : ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' ) ;
    public final EObject ruleGlobalInterrupt() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token lv_role_4_0=null;
        Token otherlv_5=null;
        EObject lv_messages_0_0 = null;

        EObject lv_messages_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1998:28: ( ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1999:1: ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1999:1: ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1999:2: ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1999:2: ( (lv_messages_0_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2000:1: (lv_messages_0_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2000:1: (lv_messages_0_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2001:3: lv_messages_0_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getGlobalInterruptAccess().getMessagesMessageParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleGlobalInterrupt4164);
            lv_messages_0_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalInterruptRule());
            	        }
                   		add(
                   			current, 
                   			"messages",
                    		lv_messages_0_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2017:2: (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==21) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2017:4: otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) )
            	    {
            	    otherlv_1=(Token)match(input,21,FOLLOW_21_in_ruleGlobalInterrupt4177); 

            	        	newLeafNode(otherlv_1, grammarAccess.getGlobalInterruptAccess().getCommaKeyword_1_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2021:1: ( (lv_messages_2_0= ruleMessage ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2022:1: (lv_messages_2_0= ruleMessage )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2022:1: (lv_messages_2_0= ruleMessage )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2023:3: lv_messages_2_0= ruleMessage
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalInterruptAccess().getMessagesMessageParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMessage_in_ruleGlobalInterrupt4198);
            	    lv_messages_2_0=ruleMessage();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalInterruptRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"messages",
            	            		lv_messages_2_0, 
            	            		"Message");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);

            otherlv_3=(Token)match(input,41,FOLLOW_41_in_ruleGlobalInterrupt4212); 

                	newLeafNode(otherlv_3, grammarAccess.getGlobalInterruptAccess().getByKeyword_2());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2043:1: ( (lv_role_4_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2044:1: (lv_role_4_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2044:1: (lv_role_4_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2045:3: lv_role_4_0= RULE_ID
            {
            lv_role_4_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalInterrupt4229); 

            			newLeafNode(lv_role_4_0, grammarAccess.getGlobalInterruptAccess().getRoleIDTerminalRuleCall_3_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalInterruptRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"role",
                    		lv_role_4_0, 
                    		"ID");
            	    

            }


            }

            otherlv_5=(Token)match(input,12,FOLLOW_12_in_ruleGlobalInterrupt4246); 

                	newLeafNode(otherlv_5, grammarAccess.getGlobalInterruptAccess().getSemicolonKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGlobalInterrupt"


    // $ANTLR start "entryRuleGlobalDo"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2073:1: entryRuleGlobalDo returns [EObject current=null] : iv_ruleGlobalDo= ruleGlobalDo EOF ;
    public final EObject entryRuleGlobalDo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalDo = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2074:2: (iv_ruleGlobalDo= ruleGlobalDo EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2075:2: iv_ruleGlobalDo= ruleGlobalDo EOF
            {
             newCompositeNode(grammarAccess.getGlobalDoRule()); 
            pushFollow(FOLLOW_ruleGlobalDo_in_entryRuleGlobalDo4282);
            iv_ruleGlobalDo=ruleGlobalDo();

            state._fsp--;

             current =iv_ruleGlobalDo; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalDo4292); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGlobalDo"


    // $ANTLR start "ruleGlobalDo"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2082:1: ruleGlobalDo returns [EObject current=null] : (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' ) ;
    public final EObject ruleGlobalDo() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_scope_1_0=null;
        Token otherlv_2=null;
        Token lv_member_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        EObject lv_arguments_5_0 = null;

        EObject lv_arguments_7_0 = null;

        EObject lv_roles_10_0 = null;

        EObject lv_roles_12_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2085:28: ( (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2086:1: (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2086:1: (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2086:3: otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';'
            {
            otherlv_0=(Token)match(input,42,FOLLOW_42_in_ruleGlobalDo4329); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalDoAccess().getDoKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2090:1: ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==RULE_ID) ) {
                int LA36_1 = input.LA(2);

                if ( (LA36_1==23) ) {
                    alt36=1;
                }
            }
            switch (alt36) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2090:2: ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2090:2: ( (lv_scope_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2091:1: (lv_scope_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2091:1: (lv_scope_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2092:3: lv_scope_1_0= RULE_ID
                    {
                    lv_scope_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalDo4347); 

                    			newLeafNode(lv_scope_1_0, grammarAccess.getGlobalDoAccess().getScopeIDTerminalRuleCall_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getGlobalDoRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"scope",
                            		lv_scope_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleGlobalDo4364); 

                        	newLeafNode(otherlv_2, grammarAccess.getGlobalDoAccess().getColonKeyword_1_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2112:3: ( (lv_member_3_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2113:1: (lv_member_3_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2113:1: (lv_member_3_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2114:3: lv_member_3_0= RULE_ID
            {
            lv_member_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalDo4383); 

            			newLeafNode(lv_member_3_0, grammarAccess.getGlobalDoAccess().getMemberIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalDoRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"member",
                    		lv_member_3_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2130:2: (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==18) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2130:4: otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>'
                    {
                    otherlv_4=(Token)match(input,18,FOLLOW_18_in_ruleGlobalDo4401); 

                        	newLeafNode(otherlv_4, grammarAccess.getGlobalDoAccess().getLessThanSignKeyword_3_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2134:1: ( (lv_arguments_5_0= ruleArgument ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2135:1: (lv_arguments_5_0= ruleArgument )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2135:1: (lv_arguments_5_0= ruleArgument )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2136:3: lv_arguments_5_0= ruleArgument
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalDoAccess().getArgumentsArgumentParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleArgument_in_ruleGlobalDo4422);
                    lv_arguments_5_0=ruleArgument();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getGlobalDoRule());
                    	        }
                           		add(
                           			current, 
                           			"arguments",
                            		lv_arguments_5_0, 
                            		"Argument");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2152:2: (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )*
                    loop37:
                    do {
                        int alt37=2;
                        int LA37_0 = input.LA(1);

                        if ( (LA37_0==21) ) {
                            alt37=1;
                        }


                        switch (alt37) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2152:4: otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) )
                    	    {
                    	    otherlv_6=(Token)match(input,21,FOLLOW_21_in_ruleGlobalDo4435); 

                    	        	newLeafNode(otherlv_6, grammarAccess.getGlobalDoAccess().getCommaKeyword_3_2_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2156:1: ( (lv_arguments_7_0= ruleArgument ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2157:1: (lv_arguments_7_0= ruleArgument )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2157:1: (lv_arguments_7_0= ruleArgument )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2158:3: lv_arguments_7_0= ruleArgument
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getGlobalDoAccess().getArgumentsArgumentParserRuleCall_3_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleArgument_in_ruleGlobalDo4456);
                    	    lv_arguments_7_0=ruleArgument();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getGlobalDoRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"arguments",
                    	            		lv_arguments_7_0, 
                    	            		"Argument");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop37;
                        }
                    } while (true);

                    otherlv_8=(Token)match(input,19,FOLLOW_19_in_ruleGlobalDo4470); 

                        	newLeafNode(otherlv_8, grammarAccess.getGlobalDoAccess().getGreaterThanSignKeyword_3_3());
                        

                    }
                    break;

            }

            otherlv_9=(Token)match(input,20,FOLLOW_20_in_ruleGlobalDo4484); 

                	newLeafNode(otherlv_9, grammarAccess.getGlobalDoAccess().getLeftParenthesisKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2182:1: ( (lv_roles_10_0= ruleRoleInstantiation ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2183:1: (lv_roles_10_0= ruleRoleInstantiation )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2183:1: (lv_roles_10_0= ruleRoleInstantiation )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2184:3: lv_roles_10_0= ruleRoleInstantiation
            {
             
            	        newCompositeNode(grammarAccess.getGlobalDoAccess().getRolesRoleInstantiationParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4505);
            lv_roles_10_0=ruleRoleInstantiation();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalDoRule());
            	        }
                   		add(
                   			current, 
                   			"roles",
                    		lv_roles_10_0, 
                    		"RoleInstantiation");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2200:2: (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==21) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2200:4: otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) )
            	    {
            	    otherlv_11=(Token)match(input,21,FOLLOW_21_in_ruleGlobalDo4518); 

            	        	newLeafNode(otherlv_11, grammarAccess.getGlobalDoAccess().getCommaKeyword_6_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2204:1: ( (lv_roles_12_0= ruleRoleInstantiation ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2205:1: (lv_roles_12_0= ruleRoleInstantiation )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2205:1: (lv_roles_12_0= ruleRoleInstantiation )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2206:3: lv_roles_12_0= ruleRoleInstantiation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalDoAccess().getRolesRoleInstantiationParserRuleCall_6_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4539);
            	    lv_roles_12_0=ruleRoleInstantiation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalDoRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"roles",
            	            		lv_roles_12_0, 
            	            		"RoleInstantiation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);

            otherlv_13=(Token)match(input,22,FOLLOW_22_in_ruleGlobalDo4553); 

                	newLeafNode(otherlv_13, grammarAccess.getGlobalDoAccess().getRightParenthesisKeyword_7());
                
            otherlv_14=(Token)match(input,12,FOLLOW_12_in_ruleGlobalDo4565); 

                	newLeafNode(otherlv_14, grammarAccess.getGlobalDoAccess().getSemicolonKeyword_8());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGlobalDo"


    // $ANTLR start "entryRuleLocalProtocolDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2238:1: entryRuleLocalProtocolDecl returns [EObject current=null] : iv_ruleLocalProtocolDecl= ruleLocalProtocolDecl EOF ;
    public final EObject entryRuleLocalProtocolDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalProtocolDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2239:2: (iv_ruleLocalProtocolDecl= ruleLocalProtocolDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2240:2: iv_ruleLocalProtocolDecl= ruleLocalProtocolDecl EOF
            {
             newCompositeNode(grammarAccess.getLocalProtocolDeclRule()); 
            pushFollow(FOLLOW_ruleLocalProtocolDecl_in_entryRuleLocalProtocolDecl4601);
            iv_ruleLocalProtocolDecl=ruleLocalProtocolDecl();

            state._fsp--;

             current =iv_ruleLocalProtocolDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalProtocolDecl4611); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalProtocolDecl"


    // $ANTLR start "ruleLocalProtocolDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2247:1: ruleLocalProtocolDecl returns [EObject current=null] : (otherlv_0= 'local' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'at' ( (lv_role_4_0= RULE_ID ) ) (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )? otherlv_10= '(' ( (lv_roles_11_0= ruleRoleDecl ) ) (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )* otherlv_14= ')' ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) ) ) ;
    public final EObject ruleLocalProtocolDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token lv_role_4_0=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token lv_instantiates_17_0=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        EObject lv_parameters_6_0 = null;

        EObject lv_parameters_8_0 = null;

        EObject lv_roles_11_0 = null;

        EObject lv_roles_13_0 = null;

        EObject lv_block_15_0 = null;

        EObject lv_arguments_19_0 = null;

        EObject lv_arguments_21_0 = null;

        EObject lv_roleInstantiations_24_0 = null;

        EObject lv_roleInstantiations_26_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2250:28: ( (otherlv_0= 'local' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'at' ( (lv_role_4_0= RULE_ID ) ) (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )? otherlv_10= '(' ( (lv_roles_11_0= ruleRoleDecl ) ) (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )* otherlv_14= ')' ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2251:1: (otherlv_0= 'local' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'at' ( (lv_role_4_0= RULE_ID ) ) (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )? otherlv_10= '(' ( (lv_roles_11_0= ruleRoleDecl ) ) (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )* otherlv_14= ')' ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2251:1: (otherlv_0= 'local' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'at' ( (lv_role_4_0= RULE_ID ) ) (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )? otherlv_10= '(' ( (lv_roles_11_0= ruleRoleDecl ) ) (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )* otherlv_14= ')' ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2251:3: otherlv_0= 'local' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'at' ( (lv_role_4_0= RULE_ID ) ) (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )? otherlv_10= '(' ( (lv_roles_11_0= ruleRoleDecl ) ) (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )* otherlv_14= ')' ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) )
            {
            otherlv_0=(Token)match(input,43,FOLLOW_43_in_ruleLocalProtocolDecl4648); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalProtocolDeclAccess().getLocalKeyword_0());
                
            otherlv_1=(Token)match(input,25,FOLLOW_25_in_ruleLocalProtocolDecl4660); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalProtocolDeclAccess().getProtocolKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2259:1: ( (lv_name_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2260:1: (lv_name_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2260:1: (lv_name_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2261:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalProtocolDecl4677); 

            			newLeafNode(lv_name_2_0, grammarAccess.getLocalProtocolDeclAccess().getNameIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalProtocolDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"ID");
            	    

            }


            }

            otherlv_3=(Token)match(input,33,FOLLOW_33_in_ruleLocalProtocolDecl4694); 

                	newLeafNode(otherlv_3, grammarAccess.getLocalProtocolDeclAccess().getAtKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2281:1: ( (lv_role_4_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2282:1: (lv_role_4_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2282:1: (lv_role_4_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2283:3: lv_role_4_0= RULE_ID
            {
            lv_role_4_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalProtocolDecl4711); 

            			newLeafNode(lv_role_4_0, grammarAccess.getLocalProtocolDeclAccess().getRoleIDTerminalRuleCall_4_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalProtocolDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"role",
                    		lv_role_4_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2299:2: (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==18) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2299:4: otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>'
                    {
                    otherlv_5=(Token)match(input,18,FOLLOW_18_in_ruleLocalProtocolDecl4729); 

                        	newLeafNode(otherlv_5, grammarAccess.getLocalProtocolDeclAccess().getLessThanSignKeyword_5_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2303:1: ( (lv_parameters_6_0= ruleParameterDecl ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2304:1: (lv_parameters_6_0= ruleParameterDecl )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2304:1: (lv_parameters_6_0= ruleParameterDecl )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2305:3: lv_parameters_6_0= ruleParameterDecl
                    {
                     
                    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_5_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleParameterDecl_in_ruleLocalProtocolDecl4750);
                    lv_parameters_6_0=ruleParameterDecl();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_6_0, 
                            		"ParameterDecl");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2321:2: (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )*
                    loop40:
                    do {
                        int alt40=2;
                        int LA40_0 = input.LA(1);

                        if ( (LA40_0==21) ) {
                            alt40=1;
                        }


                        switch (alt40) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2321:4: otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) )
                    	    {
                    	    otherlv_7=(Token)match(input,21,FOLLOW_21_in_ruleLocalProtocolDecl4763); 

                    	        	newLeafNode(otherlv_7, grammarAccess.getLocalProtocolDeclAccess().getCommaKeyword_5_2_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2325:1: ( (lv_parameters_8_0= ruleParameterDecl ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2326:1: (lv_parameters_8_0= ruleParameterDecl )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2326:1: (lv_parameters_8_0= ruleParameterDecl )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2327:3: lv_parameters_8_0= ruleParameterDecl
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_5_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParameterDecl_in_ruleLocalProtocolDecl4784);
                    	    lv_parameters_8_0=ruleParameterDecl();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"parameters",
                    	            		lv_parameters_8_0, 
                    	            		"ParameterDecl");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop40;
                        }
                    } while (true);

                    otherlv_9=(Token)match(input,19,FOLLOW_19_in_ruleLocalProtocolDecl4798); 

                        	newLeafNode(otherlv_9, grammarAccess.getLocalProtocolDeclAccess().getGreaterThanSignKeyword_5_3());
                        

                    }
                    break;

            }

            otherlv_10=(Token)match(input,20,FOLLOW_20_in_ruleLocalProtocolDecl4812); 

                	newLeafNode(otherlv_10, grammarAccess.getLocalProtocolDeclAccess().getLeftParenthesisKeyword_6());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2351:1: ( (lv_roles_11_0= ruleRoleDecl ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2352:1: (lv_roles_11_0= ruleRoleDecl )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2352:1: (lv_roles_11_0= ruleRoleDecl )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2353:3: lv_roles_11_0= ruleRoleDecl
            {
             
            	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_7_0()); 
            	    
            pushFollow(FOLLOW_ruleRoleDecl_in_ruleLocalProtocolDecl4833);
            lv_roles_11_0=ruleRoleDecl();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
            	        }
                   		add(
                   			current, 
                   			"roles",
                    		lv_roles_11_0, 
                    		"RoleDecl");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2369:2: (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==21) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2369:4: otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) )
            	    {
            	    otherlv_12=(Token)match(input,21,FOLLOW_21_in_ruleLocalProtocolDecl4846); 

            	        	newLeafNode(otherlv_12, grammarAccess.getLocalProtocolDeclAccess().getCommaKeyword_8_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2373:1: ( (lv_roles_13_0= ruleRoleDecl ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2374:1: (lv_roles_13_0= ruleRoleDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2374:1: (lv_roles_13_0= ruleRoleDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2375:3: lv_roles_13_0= ruleRoleDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_8_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRoleDecl_in_ruleLocalProtocolDecl4867);
            	    lv_roles_13_0=ruleRoleDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"roles",
            	            		lv_roles_13_0, 
            	            		"RoleDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);

            otherlv_14=(Token)match(input,22,FOLLOW_22_in_ruleLocalProtocolDecl4881); 

                	newLeafNode(otherlv_14, grammarAccess.getLocalProtocolDeclAccess().getRightParenthesisKeyword_9());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2395:1: ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) )
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==29) ) {
                alt46=1;
            }
            else if ( (LA46_0==26) ) {
                alt46=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }
            switch (alt46) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2395:2: ( (lv_block_15_0= ruleLocalProtocolBlock ) )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2395:2: ( (lv_block_15_0= ruleLocalProtocolBlock ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2396:1: (lv_block_15_0= ruleLocalProtocolBlock )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2396:1: (lv_block_15_0= ruleLocalProtocolBlock )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2397:3: lv_block_15_0= ruleLocalProtocolBlock
                    {
                     
                    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getBlockLocalProtocolBlockParserRuleCall_10_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalProtocolDecl4903);
                    lv_block_15_0=ruleLocalProtocolBlock();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                    	        }
                           		set(
                           			current, 
                           			"block",
                            		lv_block_15_0, 
                            		"LocalProtocolBlock");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2414:6: (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2414:6: (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2414:8: otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';'
                    {
                    otherlv_16=(Token)match(input,26,FOLLOW_26_in_ruleLocalProtocolDecl4922); 

                        	newLeafNode(otherlv_16, grammarAccess.getLocalProtocolDeclAccess().getInstantiatesKeyword_10_1_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2418:1: ( (lv_instantiates_17_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2419:1: (lv_instantiates_17_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2419:1: (lv_instantiates_17_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2420:3: lv_instantiates_17_0= RULE_ID
                    {
                    lv_instantiates_17_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalProtocolDecl4939); 

                    			newLeafNode(lv_instantiates_17_0, grammarAccess.getLocalProtocolDeclAccess().getInstantiatesIDTerminalRuleCall_10_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLocalProtocolDeclRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"instantiates",
                            		lv_instantiates_17_0, 
                            		"ID");
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2436:2: (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )?
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==18) ) {
                        alt44=1;
                    }
                    switch (alt44) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2436:4: otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>'
                            {
                            otherlv_18=(Token)match(input,18,FOLLOW_18_in_ruleLocalProtocolDecl4957); 

                                	newLeafNode(otherlv_18, grammarAccess.getLocalProtocolDeclAccess().getLessThanSignKeyword_10_1_2_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2440:1: ( (lv_arguments_19_0= ruleArgument ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2441:1: (lv_arguments_19_0= ruleArgument )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2441:1: (lv_arguments_19_0= ruleArgument )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2442:3: lv_arguments_19_0= ruleArgument
                            {
                             
                            	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_10_1_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleArgument_in_ruleLocalProtocolDecl4978);
                            lv_arguments_19_0=ruleArgument();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"arguments",
                                    		lv_arguments_19_0, 
                                    		"Argument");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2458:2: (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )*
                            loop43:
                            do {
                                int alt43=2;
                                int LA43_0 = input.LA(1);

                                if ( (LA43_0==21) ) {
                                    alt43=1;
                                }


                                switch (alt43) {
                            	case 1 :
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2458:4: otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) )
                            	    {
                            	    otherlv_20=(Token)match(input,21,FOLLOW_21_in_ruleLocalProtocolDecl4991); 

                            	        	newLeafNode(otherlv_20, grammarAccess.getLocalProtocolDeclAccess().getCommaKeyword_10_1_2_2_0());
                            	        
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2462:1: ( (lv_arguments_21_0= ruleArgument ) )
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2463:1: (lv_arguments_21_0= ruleArgument )
                            	    {
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2463:1: (lv_arguments_21_0= ruleArgument )
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2464:3: lv_arguments_21_0= ruleArgument
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_10_1_2_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleArgument_in_ruleLocalProtocolDecl5012);
                            	    lv_arguments_21_0=ruleArgument();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"arguments",
                            	            		lv_arguments_21_0, 
                            	            		"Argument");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop43;
                                }
                            } while (true);

                            otherlv_22=(Token)match(input,19,FOLLOW_19_in_ruleLocalProtocolDecl5026); 

                                	newLeafNode(otherlv_22, grammarAccess.getLocalProtocolDeclAccess().getGreaterThanSignKeyword_10_1_2_3());
                                

                            }
                            break;

                    }

                    otherlv_23=(Token)match(input,20,FOLLOW_20_in_ruleLocalProtocolDecl5040); 

                        	newLeafNode(otherlv_23, grammarAccess.getLocalProtocolDeclAccess().getLeftParenthesisKeyword_10_1_3());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2488:1: ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2489:1: (lv_roleInstantiations_24_0= ruleRoleInstantiation )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2489:1: (lv_roleInstantiations_24_0= ruleRoleInstantiation )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2490:3: lv_roleInstantiations_24_0= ruleRoleInstantiation
                    {
                     
                    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_10_1_4_0()); 
                    	    
                    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleLocalProtocolDecl5061);
                    lv_roleInstantiations_24_0=ruleRoleInstantiation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                    	        }
                           		add(
                           			current, 
                           			"roleInstantiations",
                            		lv_roleInstantiations_24_0, 
                            		"RoleInstantiation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2506:2: (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )*
                    loop45:
                    do {
                        int alt45=2;
                        int LA45_0 = input.LA(1);

                        if ( (LA45_0==21) ) {
                            alt45=1;
                        }


                        switch (alt45) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2506:4: otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) )
                    	    {
                    	    otherlv_25=(Token)match(input,21,FOLLOW_21_in_ruleLocalProtocolDecl5074); 

                    	        	newLeafNode(otherlv_25, grammarAccess.getLocalProtocolDeclAccess().getCommaKeyword_10_1_5_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2510:1: ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2511:1: (lv_roleInstantiations_26_0= ruleRoleInstantiation )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2511:1: (lv_roleInstantiations_26_0= ruleRoleInstantiation )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2512:3: lv_roleInstantiations_26_0= ruleRoleInstantiation
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_10_1_5_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleLocalProtocolDecl5095);
                    	    lv_roleInstantiations_26_0=ruleRoleInstantiation();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"roleInstantiations",
                    	            		lv_roleInstantiations_26_0, 
                    	            		"RoleInstantiation");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop45;
                        }
                    } while (true);

                    otherlv_27=(Token)match(input,22,FOLLOW_22_in_ruleLocalProtocolDecl5109); 

                        	newLeafNode(otherlv_27, grammarAccess.getLocalProtocolDeclAccess().getRightParenthesisKeyword_10_1_6());
                        
                    otherlv_28=(Token)match(input,12,FOLLOW_12_in_ruleLocalProtocolDecl5121); 

                        	newLeafNode(otherlv_28, grammarAccess.getLocalProtocolDeclAccess().getSemicolonKeyword_10_1_7());
                        

                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalProtocolDecl"


    // $ANTLR start "entryRuleLocalProtocolBlock"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2544:1: entryRuleLocalProtocolBlock returns [EObject current=null] : iv_ruleLocalProtocolBlock= ruleLocalProtocolBlock EOF ;
    public final EObject entryRuleLocalProtocolBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalProtocolBlock = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2545:2: (iv_ruleLocalProtocolBlock= ruleLocalProtocolBlock EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2546:2: iv_ruleLocalProtocolBlock= ruleLocalProtocolBlock EOF
            {
             newCompositeNode(grammarAccess.getLocalProtocolBlockRule()); 
            pushFollow(FOLLOW_ruleLocalProtocolBlock_in_entryRuleLocalProtocolBlock5159);
            iv_ruleLocalProtocolBlock=ruleLocalProtocolBlock();

            state._fsp--;

             current =iv_ruleLocalProtocolBlock; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalProtocolBlock5169); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalProtocolBlock"


    // $ANTLR start "ruleLocalProtocolBlock"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2553:1: ruleLocalProtocolBlock returns [EObject current=null] : ( () otherlv_1= '{' ( (lv_activities_2_0= ruleLlobalInteraction ) )* otherlv_3= '}' ) ;
    public final EObject ruleLocalProtocolBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_activities_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2556:28: ( ( () otherlv_1= '{' ( (lv_activities_2_0= ruleLlobalInteraction ) )* otherlv_3= '}' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2557:1: ( () otherlv_1= '{' ( (lv_activities_2_0= ruleLlobalInteraction ) )* otherlv_3= '}' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2557:1: ( () otherlv_1= '{' ( (lv_activities_2_0= ruleLlobalInteraction ) )* otherlv_3= '}' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2557:2: () otherlv_1= '{' ( (lv_activities_2_0= ruleLlobalInteraction ) )* otherlv_3= '}'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2557:2: ()
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2558:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getLocalProtocolBlockAccess().getLocalProtocolBlockAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,29,FOLLOW_29_in_ruleLocalProtocolBlock5215); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalProtocolBlockAccess().getLeftCurlyBracketKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2567:1: ( (lv_activities_2_0= ruleLlobalInteraction ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==RULE_ID||LA47_0==20||LA47_0==32||(LA47_0>=35 && LA47_0<=37)||LA47_0==39||LA47_0==42) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2568:1: (lv_activities_2_0= ruleLlobalInteraction )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2568:1: (lv_activities_2_0= ruleLlobalInteraction )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2569:3: lv_activities_2_0= ruleLlobalInteraction
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalProtocolBlockAccess().getActivitiesLlobalInteractionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLlobalInteraction_in_ruleLocalProtocolBlock5236);
            	    lv_activities_2_0=ruleLlobalInteraction();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalProtocolBlockRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"activities",
            	            		lv_activities_2_0, 
            	            		"LlobalInteraction");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);

            otherlv_3=(Token)match(input,30,FOLLOW_30_in_ruleLocalProtocolBlock5249); 

                	newLeafNode(otherlv_3, grammarAccess.getLocalProtocolBlockAccess().getRightCurlyBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalProtocolBlock"


    // $ANTLR start "entryRuleLlobalInteraction"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2597:1: entryRuleLlobalInteraction returns [EObject current=null] : iv_ruleLlobalInteraction= ruleLlobalInteraction EOF ;
    public final EObject entryRuleLlobalInteraction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLlobalInteraction = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2598:2: (iv_ruleLlobalInteraction= ruleLlobalInteraction EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2599:2: iv_ruleLlobalInteraction= ruleLlobalInteraction EOF
            {
             newCompositeNode(grammarAccess.getLlobalInteractionRule()); 
            pushFollow(FOLLOW_ruleLlobalInteraction_in_entryRuleLlobalInteraction5285);
            iv_ruleLlobalInteraction=ruleLlobalInteraction();

            state._fsp--;

             current =iv_ruleLlobalInteraction; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLlobalInteraction5295); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLlobalInteraction"


    // $ANTLR start "ruleLlobalInteraction"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2606:1: ruleLlobalInteraction returns [EObject current=null] : (this_LocalSend_0= ruleLocalSend | this_LocalReceive_1= ruleLocalReceive | this_LocalChoice_2= ruleLocalChoice | this_LocalParallel_3= ruleLocalParallel | this_LocalRecursion_4= ruleLocalRecursion | this_LocalContinue_5= ruleLocalContinue | this_LocalInterruptible_6= ruleLocalInterruptible | this_LocalDo_7= ruleLocalDo ) ;
    public final EObject ruleLlobalInteraction() throws RecognitionException {
        EObject current = null;

        EObject this_LocalSend_0 = null;

        EObject this_LocalReceive_1 = null;

        EObject this_LocalChoice_2 = null;

        EObject this_LocalParallel_3 = null;

        EObject this_LocalRecursion_4 = null;

        EObject this_LocalContinue_5 = null;

        EObject this_LocalInterruptible_6 = null;

        EObject this_LocalDo_7 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2609:28: ( (this_LocalSend_0= ruleLocalSend | this_LocalReceive_1= ruleLocalReceive | this_LocalChoice_2= ruleLocalChoice | this_LocalParallel_3= ruleLocalParallel | this_LocalRecursion_4= ruleLocalRecursion | this_LocalContinue_5= ruleLocalContinue | this_LocalInterruptible_6= ruleLocalInterruptible | this_LocalDo_7= ruleLocalDo ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2610:1: (this_LocalSend_0= ruleLocalSend | this_LocalReceive_1= ruleLocalReceive | this_LocalChoice_2= ruleLocalChoice | this_LocalParallel_3= ruleLocalParallel | this_LocalRecursion_4= ruleLocalRecursion | this_LocalContinue_5= ruleLocalContinue | this_LocalInterruptible_6= ruleLocalInterruptible | this_LocalDo_7= ruleLocalDo )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2610:1: (this_LocalSend_0= ruleLocalSend | this_LocalReceive_1= ruleLocalReceive | this_LocalChoice_2= ruleLocalChoice | this_LocalParallel_3= ruleLocalParallel | this_LocalRecursion_4= ruleLocalRecursion | this_LocalContinue_5= ruleLocalContinue | this_LocalInterruptible_6= ruleLocalInterruptible | this_LocalDo_7= ruleLocalDo )
            int alt48=8;
            alt48 = dfa48.predict(input);
            switch (alt48) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2611:5: this_LocalSend_0= ruleLocalSend
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalSendParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleLocalSend_in_ruleLlobalInteraction5342);
                    this_LocalSend_0=ruleLocalSend();

                    state._fsp--;

                     
                            current = this_LocalSend_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2621:5: this_LocalReceive_1= ruleLocalReceive
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalReceiveParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleLocalReceive_in_ruleLlobalInteraction5369);
                    this_LocalReceive_1=ruleLocalReceive();

                    state._fsp--;

                     
                            current = this_LocalReceive_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2631:5: this_LocalChoice_2= ruleLocalChoice
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalChoiceParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleLocalChoice_in_ruleLlobalInteraction5396);
                    this_LocalChoice_2=ruleLocalChoice();

                    state._fsp--;

                     
                            current = this_LocalChoice_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2641:5: this_LocalParallel_3= ruleLocalParallel
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalParallelParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_ruleLocalParallel_in_ruleLlobalInteraction5423);
                    this_LocalParallel_3=ruleLocalParallel();

                    state._fsp--;

                     
                            current = this_LocalParallel_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2651:5: this_LocalRecursion_4= ruleLocalRecursion
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalRecursionParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_ruleLocalRecursion_in_ruleLlobalInteraction5450);
                    this_LocalRecursion_4=ruleLocalRecursion();

                    state._fsp--;

                     
                            current = this_LocalRecursion_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2661:5: this_LocalContinue_5= ruleLocalContinue
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalContinueParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleLocalContinue_in_ruleLlobalInteraction5477);
                    this_LocalContinue_5=ruleLocalContinue();

                    state._fsp--;

                     
                            current = this_LocalContinue_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2671:5: this_LocalInterruptible_6= ruleLocalInterruptible
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalInterruptibleParserRuleCall_6()); 
                        
                    pushFollow(FOLLOW_ruleLocalInterruptible_in_ruleLlobalInteraction5504);
                    this_LocalInterruptible_6=ruleLocalInterruptible();

                    state._fsp--;

                     
                            current = this_LocalInterruptible_6; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 8 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2681:5: this_LocalDo_7= ruleLocalDo
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalDoParserRuleCall_7()); 
                        
                    pushFollow(FOLLOW_ruleLocalDo_in_ruleLlobalInteraction5531);
                    this_LocalDo_7=ruleLocalDo();

                    state._fsp--;

                     
                            current = this_LocalDo_7; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLlobalInteraction"


    // $ANTLR start "entryRuleLocalSend"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2697:1: entryRuleLocalSend returns [EObject current=null] : iv_ruleLocalSend= ruleLocalSend EOF ;
    public final EObject entryRuleLocalSend() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalSend = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2698:2: (iv_ruleLocalSend= ruleLocalSend EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2699:2: iv_ruleLocalSend= ruleLocalSend EOF
            {
             newCompositeNode(grammarAccess.getLocalSendRule()); 
            pushFollow(FOLLOW_ruleLocalSend_in_entryRuleLocalSend5566);
            iv_ruleLocalSend=ruleLocalSend();

            state._fsp--;

             current =iv_ruleLocalSend; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalSend5576); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalSend"


    // $ANTLR start "ruleLocalSend"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2706:1: ruleLocalSend returns [EObject current=null] : ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'to' ( (lv_toRoles_2_0= RULE_ID ) ) (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )* otherlv_5= ';' ) ;
    public final EObject ruleLocalSend() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_toRoles_2_0=null;
        Token otherlv_3=null;
        Token lv_toRoles_4_0=null;
        Token otherlv_5=null;
        EObject lv_message_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2709:28: ( ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'to' ( (lv_toRoles_2_0= RULE_ID ) ) (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )* otherlv_5= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2710:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'to' ( (lv_toRoles_2_0= RULE_ID ) ) (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )* otherlv_5= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2710:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'to' ( (lv_toRoles_2_0= RULE_ID ) ) (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )* otherlv_5= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2710:2: ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'to' ( (lv_toRoles_2_0= RULE_ID ) ) (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )* otherlv_5= ';'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2710:2: ( (lv_message_0_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2711:1: (lv_message_0_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2711:1: (lv_message_0_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2712:3: lv_message_0_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getLocalSendAccess().getMessageMessageParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleLocalSend5622);
            lv_message_0_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalSendRule());
            	        }
                   		set(
                   			current, 
                   			"message",
                    		lv_message_0_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,31,FOLLOW_31_in_ruleLocalSend5634); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalSendAccess().getToKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2732:1: ( (lv_toRoles_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2733:1: (lv_toRoles_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2733:1: (lv_toRoles_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2734:3: lv_toRoles_2_0= RULE_ID
            {
            lv_toRoles_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalSend5651); 

            			newLeafNode(lv_toRoles_2_0, grammarAccess.getLocalSendAccess().getToRolesIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalSendRule());
            	        }
                   		addWithLastConsumed(
                   			current, 
                   			"toRoles",
                    		lv_toRoles_2_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2750:2: (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==21) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2750:4: otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) )
            	    {
            	    otherlv_3=(Token)match(input,21,FOLLOW_21_in_ruleLocalSend5669); 

            	        	newLeafNode(otherlv_3, grammarAccess.getLocalSendAccess().getCommaKeyword_3_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2754:1: ( (lv_toRoles_4_0= RULE_ID ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2755:1: (lv_toRoles_4_0= RULE_ID )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2755:1: (lv_toRoles_4_0= RULE_ID )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2756:3: lv_toRoles_4_0= RULE_ID
            	    {
            	    lv_toRoles_4_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalSend5686); 

            	    			newLeafNode(lv_toRoles_4_0, grammarAccess.getLocalSendAccess().getToRolesIDTerminalRuleCall_3_1_0()); 
            	    		

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getLocalSendRule());
            	    	        }
            	           		addWithLastConsumed(
            	           			current, 
            	           			"toRoles",
            	            		lv_toRoles_4_0, 
            	            		"ID");
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);

            otherlv_5=(Token)match(input,12,FOLLOW_12_in_ruleLocalSend5705); 

                	newLeafNode(otherlv_5, grammarAccess.getLocalSendAccess().getSemicolonKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalSend"


    // $ANTLR start "entryRuleLocalReceive"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2784:1: entryRuleLocalReceive returns [EObject current=null] : iv_ruleLocalReceive= ruleLocalReceive EOF ;
    public final EObject entryRuleLocalReceive() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalReceive = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2785:2: (iv_ruleLocalReceive= ruleLocalReceive EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2786:2: iv_ruleLocalReceive= ruleLocalReceive EOF
            {
             newCompositeNode(grammarAccess.getLocalReceiveRule()); 
            pushFollow(FOLLOW_ruleLocalReceive_in_entryRuleLocalReceive5741);
            iv_ruleLocalReceive=ruleLocalReceive();

            state._fsp--;

             current =iv_ruleLocalReceive; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalReceive5751); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalReceive"


    // $ANTLR start "ruleLocalReceive"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2793:1: ruleLocalReceive returns [EObject current=null] : ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= ';' ) ;
    public final EObject ruleLocalReceive() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_fromRole_2_0=null;
        Token otherlv_3=null;
        EObject lv_message_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2796:28: ( ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2797:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2797:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2797:2: ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= ';'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2797:2: ( (lv_message_0_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2798:1: (lv_message_0_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2798:1: (lv_message_0_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2799:3: lv_message_0_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getLocalReceiveAccess().getMessageMessageParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleLocalReceive5797);
            lv_message_0_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalReceiveRule());
            	        }
                   		set(
                   			current, 
                   			"message",
                    		lv_message_0_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,16,FOLLOW_16_in_ruleLocalReceive5809); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalReceiveAccess().getFromKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2819:1: ( (lv_fromRole_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2820:1: (lv_fromRole_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2820:1: (lv_fromRole_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2821:3: lv_fromRole_2_0= RULE_ID
            {
            lv_fromRole_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalReceive5826); 

            			newLeafNode(lv_fromRole_2_0, grammarAccess.getLocalReceiveAccess().getFromRoleIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalReceiveRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"fromRole",
                    		lv_fromRole_2_0, 
                    		"ID");
            	    

            }


            }

            otherlv_3=(Token)match(input,12,FOLLOW_12_in_ruleLocalReceive5843); 

                	newLeafNode(otherlv_3, grammarAccess.getLocalReceiveAccess().getSemicolonKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalReceive"


    // $ANTLR start "entryRuleLocalChoice"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2849:1: entryRuleLocalChoice returns [EObject current=null] : iv_ruleLocalChoice= ruleLocalChoice EOF ;
    public final EObject entryRuleLocalChoice() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalChoice = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2850:2: (iv_ruleLocalChoice= ruleLocalChoice EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2851:2: iv_ruleLocalChoice= ruleLocalChoice EOF
            {
             newCompositeNode(grammarAccess.getLocalChoiceRule()); 
            pushFollow(FOLLOW_ruleLocalChoice_in_entryRuleLocalChoice5879);
            iv_ruleLocalChoice=ruleLocalChoice();

            state._fsp--;

             current =iv_ruleLocalChoice; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalChoice5889); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalChoice"


    // $ANTLR start "ruleLocalChoice"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2858:1: ruleLocalChoice returns [EObject current=null] : (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )* ) ;
    public final EObject ruleLocalChoice() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_role_2_0=null;
        Token otherlv_4=null;
        EObject lv_blocks_3_0 = null;

        EObject lv_blocks_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2861:28: ( (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2862:1: (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2862:1: (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2862:3: otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )*
            {
            otherlv_0=(Token)match(input,32,FOLLOW_32_in_ruleLocalChoice5926); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalChoiceAccess().getChoiceKeyword_0());
                
            otherlv_1=(Token)match(input,33,FOLLOW_33_in_ruleLocalChoice5938); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalChoiceAccess().getAtKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2870:1: ( (lv_role_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2871:1: (lv_role_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2871:1: (lv_role_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2872:3: lv_role_2_0= RULE_ID
            {
            lv_role_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalChoice5955); 

            			newLeafNode(lv_role_2_0, grammarAccess.getLocalChoiceAccess().getRoleIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalChoiceRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"role",
                    		lv_role_2_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2888:2: ( (lv_blocks_3_0= ruleLocalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2889:1: (lv_blocks_3_0= ruleLocalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2889:1: (lv_blocks_3_0= ruleLocalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2890:3: lv_blocks_3_0= ruleLocalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getLocalChoiceAccess().getBlocksLocalProtocolBlockParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalChoice5981);
            lv_blocks_3_0=ruleLocalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalChoiceRule());
            	        }
                   		add(
                   			current, 
                   			"blocks",
                    		lv_blocks_3_0, 
                    		"LocalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2906:2: (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==34) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2906:4: otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) )
            	    {
            	    otherlv_4=(Token)match(input,34,FOLLOW_34_in_ruleLocalChoice5994); 

            	        	newLeafNode(otherlv_4, grammarAccess.getLocalChoiceAccess().getOrKeyword_4_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2910:1: ( (lv_blocks_5_0= ruleLocalProtocolBlock ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2911:1: (lv_blocks_5_0= ruleLocalProtocolBlock )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2911:1: (lv_blocks_5_0= ruleLocalProtocolBlock )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2912:3: lv_blocks_5_0= ruleLocalProtocolBlock
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalChoiceAccess().getBlocksLocalProtocolBlockParserRuleCall_4_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalChoice6015);
            	    lv_blocks_5_0=ruleLocalProtocolBlock();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalChoiceRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"blocks",
            	            		lv_blocks_5_0, 
            	            		"LocalProtocolBlock");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop50;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalChoice"


    // $ANTLR start "entryRuleLocalRecursion"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2936:1: entryRuleLocalRecursion returns [EObject current=null] : iv_ruleLocalRecursion= ruleLocalRecursion EOF ;
    public final EObject entryRuleLocalRecursion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalRecursion = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2937:2: (iv_ruleLocalRecursion= ruleLocalRecursion EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2938:2: iv_ruleLocalRecursion= ruleLocalRecursion EOF
            {
             newCompositeNode(grammarAccess.getLocalRecursionRule()); 
            pushFollow(FOLLOW_ruleLocalRecursion_in_entryRuleLocalRecursion6053);
            iv_ruleLocalRecursion=ruleLocalRecursion();

            state._fsp--;

             current =iv_ruleLocalRecursion; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalRecursion6063); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalRecursion"


    // $ANTLR start "ruleLocalRecursion"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2945:1: ruleLocalRecursion returns [EObject current=null] : (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleLocalProtocolBlock ) ) ) ;
    public final EObject ruleLocalRecursion() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_label_1_0=null;
        EObject lv_block_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2948:28: ( (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleLocalProtocolBlock ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2949:1: (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleLocalProtocolBlock ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2949:1: (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleLocalProtocolBlock ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2949:3: otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleLocalProtocolBlock ) )
            {
            otherlv_0=(Token)match(input,35,FOLLOW_35_in_ruleLocalRecursion6100); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalRecursionAccess().getRecKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2953:1: ( (lv_label_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2954:1: (lv_label_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2954:1: (lv_label_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2955:3: lv_label_1_0= RULE_ID
            {
            lv_label_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalRecursion6117); 

            			newLeafNode(lv_label_1_0, grammarAccess.getLocalRecursionAccess().getLabelIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalRecursionRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"label",
                    		lv_label_1_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2971:2: ( (lv_block_2_0= ruleLocalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2972:1: (lv_block_2_0= ruleLocalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2972:1: (lv_block_2_0= ruleLocalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2973:3: lv_block_2_0= ruleLocalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getLocalRecursionAccess().getBlockLocalProtocolBlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalRecursion6143);
            lv_block_2_0=ruleLocalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalRecursionRule());
            	        }
                   		set(
                   			current, 
                   			"block",
                    		lv_block_2_0, 
                    		"LocalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalRecursion"


    // $ANTLR start "entryRuleLocalContinue"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2997:1: entryRuleLocalContinue returns [EObject current=null] : iv_ruleLocalContinue= ruleLocalContinue EOF ;
    public final EObject entryRuleLocalContinue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalContinue = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2998:2: (iv_ruleLocalContinue= ruleLocalContinue EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2999:2: iv_ruleLocalContinue= ruleLocalContinue EOF
            {
             newCompositeNode(grammarAccess.getLocalContinueRule()); 
            pushFollow(FOLLOW_ruleLocalContinue_in_entryRuleLocalContinue6179);
            iv_ruleLocalContinue=ruleLocalContinue();

            state._fsp--;

             current =iv_ruleLocalContinue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalContinue6189); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalContinue"


    // $ANTLR start "ruleLocalContinue"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3006:1: ruleLocalContinue returns [EObject current=null] : (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' ) ;
    public final EObject ruleLocalContinue() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_label_1_0=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3009:28: ( (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3010:1: (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3010:1: (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3010:3: otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';'
            {
            otherlv_0=(Token)match(input,36,FOLLOW_36_in_ruleLocalContinue6226); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalContinueAccess().getContinueKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3014:1: ( (lv_label_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3015:1: (lv_label_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3015:1: (lv_label_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3016:3: lv_label_1_0= RULE_ID
            {
            lv_label_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalContinue6243); 

            			newLeafNode(lv_label_1_0, grammarAccess.getLocalContinueAccess().getLabelIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalContinueRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"label",
                    		lv_label_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleLocalContinue6260); 

                	newLeafNode(otherlv_2, grammarAccess.getLocalContinueAccess().getSemicolonKeyword_2());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalContinue"


    // $ANTLR start "entryRuleLocalParallel"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3044:1: entryRuleLocalParallel returns [EObject current=null] : iv_ruleLocalParallel= ruleLocalParallel EOF ;
    public final EObject entryRuleLocalParallel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalParallel = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3045:2: (iv_ruleLocalParallel= ruleLocalParallel EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3046:2: iv_ruleLocalParallel= ruleLocalParallel EOF
            {
             newCompositeNode(grammarAccess.getLocalParallelRule()); 
            pushFollow(FOLLOW_ruleLocalParallel_in_entryRuleLocalParallel6296);
            iv_ruleLocalParallel=ruleLocalParallel();

            state._fsp--;

             current =iv_ruleLocalParallel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalParallel6306); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalParallel"


    // $ANTLR start "ruleLocalParallel"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3053:1: ruleLocalParallel returns [EObject current=null] : (otherlv_0= 'par' ( (lv_blocks_1_0= ruleLocalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )* ) ;
    public final EObject ruleLocalParallel() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_blocks_1_0 = null;

        EObject lv_blocks_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3056:28: ( (otherlv_0= 'par' ( (lv_blocks_1_0= ruleLocalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3057:1: (otherlv_0= 'par' ( (lv_blocks_1_0= ruleLocalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3057:1: (otherlv_0= 'par' ( (lv_blocks_1_0= ruleLocalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3057:3: otherlv_0= 'par' ( (lv_blocks_1_0= ruleLocalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )*
            {
            otherlv_0=(Token)match(input,37,FOLLOW_37_in_ruleLocalParallel6343); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalParallelAccess().getParKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3061:1: ( (lv_blocks_1_0= ruleLocalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3062:1: (lv_blocks_1_0= ruleLocalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3062:1: (lv_blocks_1_0= ruleLocalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3063:3: lv_blocks_1_0= ruleLocalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getLocalParallelAccess().getBlocksLocalProtocolBlockParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalParallel6364);
            lv_blocks_1_0=ruleLocalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalParallelRule());
            	        }
                   		add(
                   			current, 
                   			"blocks",
                    		lv_blocks_1_0, 
                    		"LocalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3079:2: (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==38) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3079:4: otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) )
            	    {
            	    otherlv_2=(Token)match(input,38,FOLLOW_38_in_ruleLocalParallel6377); 

            	        	newLeafNode(otherlv_2, grammarAccess.getLocalParallelAccess().getAndKeyword_2_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3083:1: ( (lv_blocks_3_0= ruleLocalProtocolBlock ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3084:1: (lv_blocks_3_0= ruleLocalProtocolBlock )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3084:1: (lv_blocks_3_0= ruleLocalProtocolBlock )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3085:3: lv_blocks_3_0= ruleLocalProtocolBlock
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalParallelAccess().getBlocksLocalProtocolBlockParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalParallel6398);
            	    lv_blocks_3_0=ruleLocalProtocolBlock();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalParallelRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"blocks",
            	            		lv_blocks_3_0, 
            	            		"LocalProtocolBlock");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalParallel"


    // $ANTLR start "entryRuleLocalInterruptible"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3109:1: entryRuleLocalInterruptible returns [EObject current=null] : iv_ruleLocalInterruptible= ruleLocalInterruptible EOF ;
    public final EObject entryRuleLocalInterruptible() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalInterruptible = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3110:2: (iv_ruleLocalInterruptible= ruleLocalInterruptible EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3111:2: iv_ruleLocalInterruptible= ruleLocalInterruptible EOF
            {
             newCompositeNode(grammarAccess.getLocalInterruptibleRule()); 
            pushFollow(FOLLOW_ruleLocalInterruptible_in_entryRuleLocalInterruptible6436);
            iv_ruleLocalInterruptible=ruleLocalInterruptible();

            state._fsp--;

             current =iv_ruleLocalInterruptible; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalInterruptible6446); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalInterruptible"


    // $ANTLR start "ruleLocalInterruptible"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3118:1: ruleLocalInterruptible returns [EObject current=null] : (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleLocalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_throw_6_0= ruleLocalThrow ) )? ( (lv_catches_7_0= ruleLocalCatch ) )* otherlv_8= '}' ) ;
    public final EObject ruleLocalInterruptible() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_scope_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        EObject lv_block_3_0 = null;

        EObject lv_throw_6_0 = null;

        EObject lv_catches_7_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3121:28: ( (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleLocalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_throw_6_0= ruleLocalThrow ) )? ( (lv_catches_7_0= ruleLocalCatch ) )* otherlv_8= '}' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3122:1: (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleLocalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_throw_6_0= ruleLocalThrow ) )? ( (lv_catches_7_0= ruleLocalCatch ) )* otherlv_8= '}' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3122:1: (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleLocalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_throw_6_0= ruleLocalThrow ) )? ( (lv_catches_7_0= ruleLocalCatch ) )* otherlv_8= '}' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3122:3: otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleLocalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_throw_6_0= ruleLocalThrow ) )? ( (lv_catches_7_0= ruleLocalCatch ) )* otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,39,FOLLOW_39_in_ruleLocalInterruptible6483); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalInterruptibleAccess().getInterruptibleKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3126:1: ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==RULE_ID) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3126:2: ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3126:2: ( (lv_scope_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3127:1: (lv_scope_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3127:1: (lv_scope_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3128:3: lv_scope_1_0= RULE_ID
                    {
                    lv_scope_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalInterruptible6501); 

                    			newLeafNode(lv_scope_1_0, grammarAccess.getLocalInterruptibleAccess().getScopeIDTerminalRuleCall_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLocalInterruptibleRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"scope",
                            		lv_scope_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleLocalInterruptible6518); 

                        	newLeafNode(otherlv_2, grammarAccess.getLocalInterruptibleAccess().getColonKeyword_1_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3148:3: ( (lv_block_3_0= ruleLocalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3149:1: (lv_block_3_0= ruleLocalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3149:1: (lv_block_3_0= ruleLocalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3150:3: lv_block_3_0= ruleLocalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getLocalInterruptibleAccess().getBlockLocalProtocolBlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalInterruptible6541);
            lv_block_3_0=ruleLocalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalInterruptibleRule());
            	        }
                   		set(
                   			current, 
                   			"block",
                    		lv_block_3_0, 
                    		"LocalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,40,FOLLOW_40_in_ruleLocalInterruptible6553); 

                	newLeafNode(otherlv_4, grammarAccess.getLocalInterruptibleAccess().getWithKeyword_3());
                
            otherlv_5=(Token)match(input,29,FOLLOW_29_in_ruleLocalInterruptible6565); 

                	newLeafNode(otherlv_5, grammarAccess.getLocalInterruptibleAccess().getLeftCurlyBracketKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3174:1: ( (lv_throw_6_0= ruleLocalThrow ) )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==44) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3175:1: (lv_throw_6_0= ruleLocalThrow )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3175:1: (lv_throw_6_0= ruleLocalThrow )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3176:3: lv_throw_6_0= ruleLocalThrow
                    {
                     
                    	        newCompositeNode(grammarAccess.getLocalInterruptibleAccess().getThrowLocalThrowParserRuleCall_5_0()); 
                    	    
                    pushFollow(FOLLOW_ruleLocalThrow_in_ruleLocalInterruptible6586);
                    lv_throw_6_0=ruleLocalThrow();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLocalInterruptibleRule());
                    	        }
                           		set(
                           			current, 
                           			"throw",
                            		lv_throw_6_0, 
                            		"LocalThrow");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3192:3: ( (lv_catches_7_0= ruleLocalCatch ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==45) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3193:1: (lv_catches_7_0= ruleLocalCatch )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3193:1: (lv_catches_7_0= ruleLocalCatch )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3194:3: lv_catches_7_0= ruleLocalCatch
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalInterruptibleAccess().getCatchesLocalCatchParserRuleCall_6_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLocalCatch_in_ruleLocalInterruptible6608);
            	    lv_catches_7_0=ruleLocalCatch();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalInterruptibleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"catches",
            	            		lv_catches_7_0, 
            	            		"LocalCatch");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);

            otherlv_8=(Token)match(input,30,FOLLOW_30_in_ruleLocalInterruptible6621); 

                	newLeafNode(otherlv_8, grammarAccess.getLocalInterruptibleAccess().getRightCurlyBracketKeyword_7());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalInterruptible"


    // $ANTLR start "entryRuleLocalThrow"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3222:1: entryRuleLocalThrow returns [EObject current=null] : iv_ruleLocalThrow= ruleLocalThrow EOF ;
    public final EObject entryRuleLocalThrow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalThrow = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3223:2: (iv_ruleLocalThrow= ruleLocalThrow EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3224:2: iv_ruleLocalThrow= ruleLocalThrow EOF
            {
             newCompositeNode(grammarAccess.getLocalThrowRule()); 
            pushFollow(FOLLOW_ruleLocalThrow_in_entryRuleLocalThrow6657);
            iv_ruleLocalThrow=ruleLocalThrow();

            state._fsp--;

             current =iv_ruleLocalThrow; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalThrow6667); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalThrow"


    // $ANTLR start "ruleLocalThrow"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3231:1: ruleLocalThrow returns [EObject current=null] : (otherlv_0= 'throw' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'to' ( (lv_toRoles_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_toRoles_7_0= RULE_ID ) ) )* otherlv_8= ';' ) ;
    public final EObject ruleLocalThrow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token lv_toRoles_5_0=null;
        Token otherlv_6=null;
        Token lv_toRoles_7_0=null;
        Token otherlv_8=null;
        EObject lv_messages_1_0 = null;

        EObject lv_messages_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3234:28: ( (otherlv_0= 'throw' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'to' ( (lv_toRoles_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_toRoles_7_0= RULE_ID ) ) )* otherlv_8= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3235:1: (otherlv_0= 'throw' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'to' ( (lv_toRoles_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_toRoles_7_0= RULE_ID ) ) )* otherlv_8= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3235:1: (otherlv_0= 'throw' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'to' ( (lv_toRoles_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_toRoles_7_0= RULE_ID ) ) )* otherlv_8= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3235:3: otherlv_0= 'throw' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'to' ( (lv_toRoles_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_toRoles_7_0= RULE_ID ) ) )* otherlv_8= ';'
            {
            otherlv_0=(Token)match(input,44,FOLLOW_44_in_ruleLocalThrow6704); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalThrowAccess().getThrowKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3239:1: ( (lv_messages_1_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3240:1: (lv_messages_1_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3240:1: (lv_messages_1_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3241:3: lv_messages_1_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getLocalThrowAccess().getMessagesMessageParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleLocalThrow6725);
            lv_messages_1_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalThrowRule());
            	        }
                   		add(
                   			current, 
                   			"messages",
                    		lv_messages_1_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3257:2: (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==21) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3257:4: otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) )
            	    {
            	    otherlv_2=(Token)match(input,21,FOLLOW_21_in_ruleLocalThrow6738); 

            	        	newLeafNode(otherlv_2, grammarAccess.getLocalThrowAccess().getCommaKeyword_2_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3261:1: ( (lv_messages_3_0= ruleMessage ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3262:1: (lv_messages_3_0= ruleMessage )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3262:1: (lv_messages_3_0= ruleMessage )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3263:3: lv_messages_3_0= ruleMessage
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalThrowAccess().getMessagesMessageParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMessage_in_ruleLocalThrow6759);
            	    lv_messages_3_0=ruleMessage();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalThrowRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"messages",
            	            		lv_messages_3_0, 
            	            		"Message");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);

            otherlv_4=(Token)match(input,31,FOLLOW_31_in_ruleLocalThrow6773); 

                	newLeafNode(otherlv_4, grammarAccess.getLocalThrowAccess().getToKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3283:1: ( (lv_toRoles_5_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3284:1: (lv_toRoles_5_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3284:1: (lv_toRoles_5_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3285:3: lv_toRoles_5_0= RULE_ID
            {
            lv_toRoles_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalThrow6790); 

            			newLeafNode(lv_toRoles_5_0, grammarAccess.getLocalThrowAccess().getToRolesIDTerminalRuleCall_4_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalThrowRule());
            	        }
                   		addWithLastConsumed(
                   			current, 
                   			"toRoles",
                    		lv_toRoles_5_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3301:2: (otherlv_6= ',' ( (lv_toRoles_7_0= RULE_ID ) ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==21) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3301:4: otherlv_6= ',' ( (lv_toRoles_7_0= RULE_ID ) )
            	    {
            	    otherlv_6=(Token)match(input,21,FOLLOW_21_in_ruleLocalThrow6808); 

            	        	newLeafNode(otherlv_6, grammarAccess.getLocalThrowAccess().getCommaKeyword_5_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3305:1: ( (lv_toRoles_7_0= RULE_ID ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3306:1: (lv_toRoles_7_0= RULE_ID )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3306:1: (lv_toRoles_7_0= RULE_ID )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3307:3: lv_toRoles_7_0= RULE_ID
            	    {
            	    lv_toRoles_7_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalThrow6825); 

            	    			newLeafNode(lv_toRoles_7_0, grammarAccess.getLocalThrowAccess().getToRolesIDTerminalRuleCall_5_1_0()); 
            	    		

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getLocalThrowRule());
            	    	        }
            	           		addWithLastConsumed(
            	           			current, 
            	           			"toRoles",
            	            		lv_toRoles_7_0, 
            	            		"ID");
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);

            otherlv_8=(Token)match(input,12,FOLLOW_12_in_ruleLocalThrow6844); 

                	newLeafNode(otherlv_8, grammarAccess.getLocalThrowAccess().getSemicolonKeyword_6());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalThrow"


    // $ANTLR start "entryRuleLocalCatch"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3335:1: entryRuleLocalCatch returns [EObject current=null] : iv_ruleLocalCatch= ruleLocalCatch EOF ;
    public final EObject entryRuleLocalCatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalCatch = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3336:2: (iv_ruleLocalCatch= ruleLocalCatch EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3337:2: iv_ruleLocalCatch= ruleLocalCatch EOF
            {
             newCompositeNode(grammarAccess.getLocalCatchRule()); 
            pushFollow(FOLLOW_ruleLocalCatch_in_entryRuleLocalCatch6880);
            iv_ruleLocalCatch=ruleLocalCatch();

            state._fsp--;

             current =iv_ruleLocalCatch; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalCatch6890); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalCatch"


    // $ANTLR start "ruleLocalCatch"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3344:1: ruleLocalCatch returns [EObject current=null] : (otherlv_0= 'catches' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'from' ( (lv_fromRole_5_0= RULE_ID ) ) otherlv_6= ';' ) ;
    public final EObject ruleLocalCatch() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token lv_fromRole_5_0=null;
        Token otherlv_6=null;
        EObject lv_messages_1_0 = null;

        EObject lv_messages_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3347:28: ( (otherlv_0= 'catches' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'from' ( (lv_fromRole_5_0= RULE_ID ) ) otherlv_6= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3348:1: (otherlv_0= 'catches' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'from' ( (lv_fromRole_5_0= RULE_ID ) ) otherlv_6= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3348:1: (otherlv_0= 'catches' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'from' ( (lv_fromRole_5_0= RULE_ID ) ) otherlv_6= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3348:3: otherlv_0= 'catches' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'from' ( (lv_fromRole_5_0= RULE_ID ) ) otherlv_6= ';'
            {
            otherlv_0=(Token)match(input,45,FOLLOW_45_in_ruleLocalCatch6927); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalCatchAccess().getCatchesKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3352:1: ( (lv_messages_1_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3353:1: (lv_messages_1_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3353:1: (lv_messages_1_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3354:3: lv_messages_1_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getLocalCatchAccess().getMessagesMessageParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleLocalCatch6948);
            lv_messages_1_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalCatchRule());
            	        }
                   		add(
                   			current, 
                   			"messages",
                    		lv_messages_1_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3370:2: (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==21) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3370:4: otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) )
            	    {
            	    otherlv_2=(Token)match(input,21,FOLLOW_21_in_ruleLocalCatch6961); 

            	        	newLeafNode(otherlv_2, grammarAccess.getLocalCatchAccess().getCommaKeyword_2_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3374:1: ( (lv_messages_3_0= ruleMessage ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3375:1: (lv_messages_3_0= ruleMessage )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3375:1: (lv_messages_3_0= ruleMessage )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3376:3: lv_messages_3_0= ruleMessage
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalCatchAccess().getMessagesMessageParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMessage_in_ruleLocalCatch6982);
            	    lv_messages_3_0=ruleMessage();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalCatchRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"messages",
            	            		lv_messages_3_0, 
            	            		"Message");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);

            otherlv_4=(Token)match(input,16,FOLLOW_16_in_ruleLocalCatch6996); 

                	newLeafNode(otherlv_4, grammarAccess.getLocalCatchAccess().getFromKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3396:1: ( (lv_fromRole_5_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3397:1: (lv_fromRole_5_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3397:1: (lv_fromRole_5_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3398:3: lv_fromRole_5_0= RULE_ID
            {
            lv_fromRole_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalCatch7013); 

            			newLeafNode(lv_fromRole_5_0, grammarAccess.getLocalCatchAccess().getFromRoleIDTerminalRuleCall_4_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalCatchRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"fromRole",
                    		lv_fromRole_5_0, 
                    		"ID");
            	    

            }


            }

            otherlv_6=(Token)match(input,12,FOLLOW_12_in_ruleLocalCatch7030); 

                	newLeafNode(otherlv_6, grammarAccess.getLocalCatchAccess().getSemicolonKeyword_5());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalCatch"


    // $ANTLR start "entryRuleLocalDo"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3426:1: entryRuleLocalDo returns [EObject current=null] : iv_ruleLocalDo= ruleLocalDo EOF ;
    public final EObject entryRuleLocalDo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalDo = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3427:2: (iv_ruleLocalDo= ruleLocalDo EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3428:2: iv_ruleLocalDo= ruleLocalDo EOF
            {
             newCompositeNode(grammarAccess.getLocalDoRule()); 
            pushFollow(FOLLOW_ruleLocalDo_in_entryRuleLocalDo7066);
            iv_ruleLocalDo=ruleLocalDo();

            state._fsp--;

             current =iv_ruleLocalDo; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalDo7076); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalDo"


    // $ANTLR start "ruleLocalDo"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3435:1: ruleLocalDo returns [EObject current=null] : (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' ) ;
    public final EObject ruleLocalDo() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_scope_1_0=null;
        Token otherlv_2=null;
        Token lv_member_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        EObject lv_arguments_5_0 = null;

        EObject lv_arguments_7_0 = null;

        EObject lv_roles_10_0 = null;

        EObject lv_roles_12_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3438:28: ( (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3439:1: (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3439:1: (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3439:3: otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';'
            {
            otherlv_0=(Token)match(input,42,FOLLOW_42_in_ruleLocalDo7113); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalDoAccess().getDoKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3443:1: ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==RULE_ID) ) {
                int LA58_1 = input.LA(2);

                if ( (LA58_1==23) ) {
                    alt58=1;
                }
            }
            switch (alt58) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3443:2: ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3443:2: ( (lv_scope_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3444:1: (lv_scope_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3444:1: (lv_scope_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3445:3: lv_scope_1_0= RULE_ID
                    {
                    lv_scope_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalDo7131); 

                    			newLeafNode(lv_scope_1_0, grammarAccess.getLocalDoAccess().getScopeIDTerminalRuleCall_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLocalDoRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"scope",
                            		lv_scope_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleLocalDo7148); 

                        	newLeafNode(otherlv_2, grammarAccess.getLocalDoAccess().getColonKeyword_1_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3465:3: ( (lv_member_3_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3466:1: (lv_member_3_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3466:1: (lv_member_3_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3467:3: lv_member_3_0= RULE_ID
            {
            lv_member_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalDo7167); 

            			newLeafNode(lv_member_3_0, grammarAccess.getLocalDoAccess().getMemberIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalDoRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"member",
                    		lv_member_3_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3483:2: (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==18) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3483:4: otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>'
                    {
                    otherlv_4=(Token)match(input,18,FOLLOW_18_in_ruleLocalDo7185); 

                        	newLeafNode(otherlv_4, grammarAccess.getLocalDoAccess().getLessThanSignKeyword_3_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3487:1: ( (lv_arguments_5_0= ruleArgument ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3488:1: (lv_arguments_5_0= ruleArgument )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3488:1: (lv_arguments_5_0= ruleArgument )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3489:3: lv_arguments_5_0= ruleArgument
                    {
                     
                    	        newCompositeNode(grammarAccess.getLocalDoAccess().getArgumentsArgumentParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleArgument_in_ruleLocalDo7206);
                    lv_arguments_5_0=ruleArgument();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLocalDoRule());
                    	        }
                           		add(
                           			current, 
                           			"arguments",
                            		lv_arguments_5_0, 
                            		"Argument");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3505:2: (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )*
                    loop59:
                    do {
                        int alt59=2;
                        int LA59_0 = input.LA(1);

                        if ( (LA59_0==21) ) {
                            alt59=1;
                        }


                        switch (alt59) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3505:4: otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) )
                    	    {
                    	    otherlv_6=(Token)match(input,21,FOLLOW_21_in_ruleLocalDo7219); 

                    	        	newLeafNode(otherlv_6, grammarAccess.getLocalDoAccess().getCommaKeyword_3_2_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3509:1: ( (lv_arguments_7_0= ruleArgument ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3510:1: (lv_arguments_7_0= ruleArgument )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3510:1: (lv_arguments_7_0= ruleArgument )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3511:3: lv_arguments_7_0= ruleArgument
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getLocalDoAccess().getArgumentsArgumentParserRuleCall_3_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleArgument_in_ruleLocalDo7240);
                    	    lv_arguments_7_0=ruleArgument();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getLocalDoRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"arguments",
                    	            		lv_arguments_7_0, 
                    	            		"Argument");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop59;
                        }
                    } while (true);

                    otherlv_8=(Token)match(input,19,FOLLOW_19_in_ruleLocalDo7254); 

                        	newLeafNode(otherlv_8, grammarAccess.getLocalDoAccess().getGreaterThanSignKeyword_3_3());
                        

                    }
                    break;

            }

            otherlv_9=(Token)match(input,20,FOLLOW_20_in_ruleLocalDo7268); 

                	newLeafNode(otherlv_9, grammarAccess.getLocalDoAccess().getLeftParenthesisKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3535:1: ( (lv_roles_10_0= ruleRoleInstantiation ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3536:1: (lv_roles_10_0= ruleRoleInstantiation )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3536:1: (lv_roles_10_0= ruleRoleInstantiation )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3537:3: lv_roles_10_0= ruleRoleInstantiation
            {
             
            	        newCompositeNode(grammarAccess.getLocalDoAccess().getRolesRoleInstantiationParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleLocalDo7289);
            lv_roles_10_0=ruleRoleInstantiation();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalDoRule());
            	        }
                   		add(
                   			current, 
                   			"roles",
                    		lv_roles_10_0, 
                    		"RoleInstantiation");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3553:2: (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==21) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3553:4: otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) )
            	    {
            	    otherlv_11=(Token)match(input,21,FOLLOW_21_in_ruleLocalDo7302); 

            	        	newLeafNode(otherlv_11, grammarAccess.getLocalDoAccess().getCommaKeyword_6_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3557:1: ( (lv_roles_12_0= ruleRoleInstantiation ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3558:1: (lv_roles_12_0= ruleRoleInstantiation )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3558:1: (lv_roles_12_0= ruleRoleInstantiation )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3559:3: lv_roles_12_0= ruleRoleInstantiation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalDoAccess().getRolesRoleInstantiationParserRuleCall_6_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleLocalDo7323);
            	    lv_roles_12_0=ruleRoleInstantiation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalDoRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"roles",
            	            		lv_roles_12_0, 
            	            		"RoleInstantiation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop61;
                }
            } while (true);

            otherlv_13=(Token)match(input,22,FOLLOW_22_in_ruleLocalDo7337); 

                	newLeafNode(otherlv_13, grammarAccess.getLocalDoAccess().getRightParenthesisKeyword_7());
                
            otherlv_14=(Token)match(input,12,FOLLOW_12_in_ruleLocalDo7349); 

                	newLeafNode(otherlv_14, grammarAccess.getLocalDoAccess().getSemicolonKeyword_8());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalDo"

    // Delegated rules


    protected DFA48 dfa48 = new DFA48(this);
    static final String DFA48_eotS =
        "\23\uffff";
    static final String DFA48_eofS =
        "\23\uffff";
    static final String DFA48_minS =
        "\1\4\1\20\1\4\10\uffff\1\25\1\20\2\4\2\25\1\4\1\25";
    static final String DFA48_maxS =
        "\1\52\1\37\1\26\10\uffff\1\27\1\37\2\4\1\26\1\27\1\4\1\26";
    static final String DFA48_acceptS =
        "\3\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\2\1\1\10\uffff";
    static final String DFA48_specialS =
        "\23\uffff}>";
    static final String[] DFA48_transitionS = {
            "\1\1\17\uffff\1\2\13\uffff\1\3\2\uffff\1\5\1\6\1\4\1\uffff\1"+
            "\7\2\uffff\1\10",
            "\1\11\3\uffff\1\2\12\uffff\1\12",
            "\1\13\21\uffff\1\14",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\16\1\14\1\15",
            "\1\11\16\uffff\1\12",
            "\1\17",
            "\1\20",
            "\1\16\1\14",
            "\1\16\1\14\1\21",
            "\1\22",
            "\1\16\1\14"
    };

    static final short[] DFA48_eot = DFA.unpackEncodedString(DFA48_eotS);
    static final short[] DFA48_eof = DFA.unpackEncodedString(DFA48_eofS);
    static final char[] DFA48_min = DFA.unpackEncodedStringToUnsignedChars(DFA48_minS);
    static final char[] DFA48_max = DFA.unpackEncodedStringToUnsignedChars(DFA48_maxS);
    static final short[] DFA48_accept = DFA.unpackEncodedString(DFA48_acceptS);
    static final short[] DFA48_special = DFA.unpackEncodedString(DFA48_specialS);
    static final short[][] DFA48_transition;

    static {
        int numStates = DFA48_transitionS.length;
        DFA48_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA48_transition[i] = DFA.unpackEncodedString(DFA48_transitionS[i]);
        }
    }

    class DFA48 extends DFA {

        public DFA48(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 48;
            this.eot = DFA48_eot;
            this.eof = DFA48_eof;
            this.min = DFA48_min;
            this.max = DFA48_max;
            this.accept = DFA48_accept;
            this.special = DFA48_special;
            this.transition = DFA48_transition;
        }
        public String getDescription() {
            return "2610:1: (this_LocalSend_0= ruleLocalSend | this_LocalReceive_1= ruleLocalReceive | this_LocalChoice_2= ruleLocalChoice | this_LocalParallel_3= ruleLocalParallel | this_LocalRecursion_4= ruleLocalRecursion | this_LocalContinue_5= ruleLocalContinue | this_LocalInterruptible_6= ruleLocalInterruptible | this_LocalDo_7= ruleLocalDo )";
        }
    }
 

    public static final BitSet FOLLOW_ruleModule_in_entryRuleModule75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModule85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleModule122 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleName_in_ruleModule143 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleModule155 = new BitSet(new long[]{0x0000080001034002L});
    public static final BitSet FOLLOW_ruleImportDecl_in_ruleModule176 = new BitSet(new long[]{0x0000080001034002L});
    public static final BitSet FOLLOW_rulePayloadTypeDecl_in_ruleModule198 = new BitSet(new long[]{0x0000080001020002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolDecl_in_ruleModule221 = new BitSet(new long[]{0x0000080001000002L});
    public static final BitSet FOLLOW_ruleLocalProtocolDecl_in_ruleModule248 = new BitSet(new long[]{0x0000080001000002L});
    public static final BitSet FOLLOW_ruleModuleName_in_entryRuleModuleName287 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleName298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleModuleName338 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_13_in_ruleModuleName357 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleModuleName372 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_ruleImportDecl_in_entryRuleImportDecl419 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportDecl429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportModule_in_ruleImportDecl476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportMember_in_ruleImportDecl503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportModule_in_entryRuleImportModule538 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportModule548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleImportModule585 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleName_in_ruleImportModule606 = new BitSet(new long[]{0x0000000000009000L});
    public static final BitSet FOLLOW_15_in_ruleImportModule619 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleImportModule636 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleImportModule655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportMember_in_entryRuleImportMember691 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportMember701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleImportMember738 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleName_in_ruleImportMember759 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleImportMember771 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleImportMember788 = new BitSet(new long[]{0x0000000000009000L});
    public static final BitSet FOLLOW_15_in_ruleImportMember806 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleImportMember823 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleImportMember842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePayloadTypeDecl_in_entryRulePayloadTypeDecl878 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePayloadTypeDecl888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rulePayloadTypeDecl925 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_rulePayloadTypeDecl937 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadTypeDecl954 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_rulePayloadTypeDecl971 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_rulePayloadTypeDecl988 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_rulePayloadTypeDecl1005 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_rulePayloadTypeDecl1022 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_rulePayloadTypeDecl1039 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadTypeDecl1056 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_rulePayloadTypeDecl1073 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_entryRuleMessageSignature1109 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessageSignature1119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMessageSignature1170 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleMessageSignature1188 = new BitSet(new long[]{0x0000000000400010L});
    public static final BitSet FOLLOW_rulePayloadElement_in_ruleMessageSignature1210 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleMessageSignature1223 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulePayloadElement_in_ruleMessageSignature1244 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleMessageSignature1260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePayloadElement_in_entryRulePayloadElement1296 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePayloadElement1306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadElement1349 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_rulePayloadElement1366 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadElement1385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolDecl_in_entryRuleGlobalProtocolDecl1426 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalProtocolDecl1436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleGlobalProtocolDecl1473 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleGlobalProtocolDecl1485 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1502 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleGlobalProtocolDecl1520 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1541 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1554 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1575 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleGlobalProtocolDecl1589 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleGlobalProtocolDecl1603 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1624 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1637 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1658 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleGlobalProtocolDecl1672 = new BitSet(new long[]{0x0000000024000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalProtocolDecl1694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleGlobalProtocolDecl1713 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1730 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleGlobalProtocolDecl1748 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1769 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1782 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1803 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleGlobalProtocolDecl1817 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleGlobalProtocolDecl1831 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1852 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1865 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1886 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleGlobalProtocolDecl1900 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalProtocolDecl1912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_entryRuleRoleDecl1950 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRoleDecl1960 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_ruleRoleDecl1997 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleDecl2014 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleRoleDecl2032 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleDecl2049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_entryRuleParameterDecl2092 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParameterDecl2102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleParameterDecl2140 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2157 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleParameterDecl2175 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_ruleParameterDecl2219 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2236 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleParameterDecl2254 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_entryRuleRoleInstantiation2315 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRoleInstantiation2325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleInstantiation2367 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleRoleInstantiation2385 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleInstantiation2402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArgument_in_entryRuleArgument2445 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArgument2455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_ruleArgument2502 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleArgument2515 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleArgument2532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleArgument2564 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleArgument2582 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleArgument2599 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_entryRuleGlobalProtocolBlock2643 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalProtocolBlock2653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_ruleGlobalProtocolBlock2699 = new BitSet(new long[]{0x000004B940100010L});
    public static final BitSet FOLLOW_ruleGlobalInteraction_in_ruleGlobalProtocolBlock2720 = new BitSet(new long[]{0x000004B940100010L});
    public static final BitSet FOLLOW_30_in_ruleGlobalProtocolBlock2733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInteraction_in_entryRuleGlobalInteraction2769 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInteraction2779 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalMessageTransfer_in_ruleGlobalInteraction2826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalChoice_in_ruleGlobalInteraction2853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalRecursion_in_ruleGlobalInteraction2880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalContinue_in_ruleGlobalInteraction2907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalParallel_in_ruleGlobalInteraction2934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterruptible_in_ruleGlobalInteraction2961 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalDo_in_ruleGlobalInteraction2988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalMessageTransfer_in_entryRuleGlobalMessageTransfer3023 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalMessageTransfer3033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleGlobalMessageTransfer3079 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleGlobalMessageTransfer3091 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3108 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleGlobalMessageTransfer3125 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3142 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalMessageTransfer3160 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3177 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalMessageTransfer3196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage3232 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage3242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_ruleMessage3288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMessage3311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalChoice_in_entryRuleGlobalChoice3352 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalChoice3362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleGlobalChoice3399 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_ruleGlobalChoice3411 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalChoice3428 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3454 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_ruleGlobalChoice3467 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3488 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_ruleGlobalRecursion_in_entryRuleGlobalRecursion3526 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalRecursion3536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleGlobalRecursion3573 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalRecursion3590 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalRecursion3616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalContinue_in_entryRuleGlobalContinue3652 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalContinue3662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_ruleGlobalContinue3699 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalContinue3716 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalContinue3733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalParallel_in_entryRuleGlobalParallel3769 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalParallel3779 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_ruleGlobalParallel3816 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3837 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_ruleGlobalParallel3850 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3871 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterruptible_in_entryRuleGlobalInterruptible3909 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInterruptible3919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_ruleGlobalInterruptible3956 = new BitSet(new long[]{0x0000000020000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalInterruptible3974 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleGlobalInterruptible3991 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalInterruptible4014 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_ruleGlobalInterruptible4026 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_ruleGlobalInterruptible4038 = new BitSet(new long[]{0x0000000040100010L});
    public static final BitSet FOLLOW_ruleGlobalInterrupt_in_ruleGlobalInterruptible4059 = new BitSet(new long[]{0x0000000040100010L});
    public static final BitSet FOLLOW_30_in_ruleGlobalInterruptible4072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterrupt_in_entryRuleGlobalInterrupt4108 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInterrupt4118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleGlobalInterrupt4164 = new BitSet(new long[]{0x0000020000200000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalInterrupt4177 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleGlobalInterrupt4198 = new BitSet(new long[]{0x0000020000200000L});
    public static final BitSet FOLLOW_41_in_ruleGlobalInterrupt4212 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalInterrupt4229 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalInterrupt4246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalDo_in_entryRuleGlobalDo4282 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalDo4292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_ruleGlobalDo4329 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalDo4347 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleGlobalDo4364 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalDo4383 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleGlobalDo4401 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalDo4422 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalDo4435 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalDo4456 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleGlobalDo4470 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleGlobalDo4484 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4505 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalDo4518 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4539 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleGlobalDo4553 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalDo4565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalProtocolDecl_in_entryRuleLocalProtocolDecl4601 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalProtocolDecl4611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_ruleLocalProtocolDecl4648 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleLocalProtocolDecl4660 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalProtocolDecl4677 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_ruleLocalProtocolDecl4694 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalProtocolDecl4711 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleLocalProtocolDecl4729 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_ruleLocalProtocolDecl4750 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleLocalProtocolDecl4763 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_ruleLocalProtocolDecl4784 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleLocalProtocolDecl4798 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleLocalProtocolDecl4812 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_ruleLocalProtocolDecl4833 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleLocalProtocolDecl4846 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_ruleLocalProtocolDecl4867 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleLocalProtocolDecl4881 = new BitSet(new long[]{0x0000000024000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalProtocolDecl4903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleLocalProtocolDecl4922 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalProtocolDecl4939 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleLocalProtocolDecl4957 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleLocalProtocolDecl4978 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleLocalProtocolDecl4991 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleLocalProtocolDecl5012 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleLocalProtocolDecl5026 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleLocalProtocolDecl5040 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleLocalProtocolDecl5061 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleLocalProtocolDecl5074 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleLocalProtocolDecl5095 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleLocalProtocolDecl5109 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleLocalProtocolDecl5121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_entryRuleLocalProtocolBlock5159 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalProtocolBlock5169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_ruleLocalProtocolBlock5215 = new BitSet(new long[]{0x000004B940100010L});
    public static final BitSet FOLLOW_ruleLlobalInteraction_in_ruleLocalProtocolBlock5236 = new BitSet(new long[]{0x000004B940100010L});
    public static final BitSet FOLLOW_30_in_ruleLocalProtocolBlock5249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLlobalInteraction_in_entryRuleLlobalInteraction5285 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLlobalInteraction5295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalSend_in_ruleLlobalInteraction5342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalReceive_in_ruleLlobalInteraction5369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalChoice_in_ruleLlobalInteraction5396 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalParallel_in_ruleLlobalInteraction5423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalRecursion_in_ruleLlobalInteraction5450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalContinue_in_ruleLlobalInteraction5477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalInterruptible_in_ruleLlobalInteraction5504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalDo_in_ruleLlobalInteraction5531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalSend_in_entryRuleLocalSend5566 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalSend5576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalSend5622 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleLocalSend5634 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalSend5651 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_21_in_ruleLocalSend5669 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalSend5686 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_12_in_ruleLocalSend5705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalReceive_in_entryRuleLocalReceive5741 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalReceive5751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalReceive5797 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleLocalReceive5809 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalReceive5826 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleLocalReceive5843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalChoice_in_entryRuleLocalChoice5879 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalChoice5889 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleLocalChoice5926 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_ruleLocalChoice5938 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalChoice5955 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalChoice5981 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_ruleLocalChoice5994 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalChoice6015 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_ruleLocalRecursion_in_entryRuleLocalRecursion6053 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalRecursion6063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleLocalRecursion6100 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalRecursion6117 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalRecursion6143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalContinue_in_entryRuleLocalContinue6179 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalContinue6189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_ruleLocalContinue6226 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalContinue6243 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleLocalContinue6260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalParallel_in_entryRuleLocalParallel6296 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalParallel6306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_ruleLocalParallel6343 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalParallel6364 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_ruleLocalParallel6377 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalParallel6398 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_ruleLocalInterruptible_in_entryRuleLocalInterruptible6436 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalInterruptible6446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_ruleLocalInterruptible6483 = new BitSet(new long[]{0x0000000020000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalInterruptible6501 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleLocalInterruptible6518 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalInterruptible6541 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_ruleLocalInterruptible6553 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_ruleLocalInterruptible6565 = new BitSet(new long[]{0x0000300040000000L});
    public static final BitSet FOLLOW_ruleLocalThrow_in_ruleLocalInterruptible6586 = new BitSet(new long[]{0x0000200040000000L});
    public static final BitSet FOLLOW_ruleLocalCatch_in_ruleLocalInterruptible6608 = new BitSet(new long[]{0x0000200040000000L});
    public static final BitSet FOLLOW_30_in_ruleLocalInterruptible6621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalThrow_in_entryRuleLocalThrow6657 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalThrow6667 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_ruleLocalThrow6704 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalThrow6725 = new BitSet(new long[]{0x0000000080200000L});
    public static final BitSet FOLLOW_21_in_ruleLocalThrow6738 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalThrow6759 = new BitSet(new long[]{0x0000000080200000L});
    public static final BitSet FOLLOW_31_in_ruleLocalThrow6773 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalThrow6790 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_21_in_ruleLocalThrow6808 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalThrow6825 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_12_in_ruleLocalThrow6844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalCatch_in_entryRuleLocalCatch6880 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalCatch6890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_ruleLocalCatch6927 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalCatch6948 = new BitSet(new long[]{0x0000000000210000L});
    public static final BitSet FOLLOW_21_in_ruleLocalCatch6961 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalCatch6982 = new BitSet(new long[]{0x0000000000210000L});
    public static final BitSet FOLLOW_16_in_ruleLocalCatch6996 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalCatch7013 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleLocalCatch7030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalDo_in_entryRuleLocalDo7066 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalDo7076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_ruleLocalDo7113 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalDo7131 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleLocalDo7148 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalDo7167 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleLocalDo7185 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleLocalDo7206 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleLocalDo7219 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleLocalDo7240 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleLocalDo7254 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleLocalDo7268 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleLocalDo7289 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleLocalDo7302 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleLocalDo7323 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleLocalDo7337 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleLocalDo7349 = new BitSet(new long[]{0x0000000000000002L});

}